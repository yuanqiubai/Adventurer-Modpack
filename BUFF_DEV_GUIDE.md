# Buff 自定义开发文档

> 适用于 Adventurer Engine (Minecraft 1.6.4 / Forge 9.11.1.916)  
> 最后更新: 2026-08-05

---

## 1. 架构概览

```
atwandaddon/buff/
├── potion/
│   └── PotionBleed.java         # 流血 DOT Potion 实现
├── EntityBuffListener.java      # 核心监听器：伤害数据持久存储
├── DebugLog.java                # 调试日志工具（待添加）
├── BuffMonitor.java             # [已弃用] 旧版伤害累加器（保留代码）
├── NbtDamageListener.java       # [已弃用] 旧版 NBT 属性监听器（保留代码）
└── bleed/                       # [备用] 独立多栈流血系统（未启用）
    ├── BleedStack.java
    ├── BleedManager.java
    └── BleedTickHandler.java

ATcraftAddon.preInit()
  ├── new PotionBleed()          # 注册到 Potion.potionTypes[30]
  ├── EntityBuffListener listener = new EntityBuffListener()
  └── MinecraftForge.EVENT_BUS.register(listener)
```

**核心方案**：自定义 `Potion` 子类 + `EntityBuffListener` 伤害数据持久化。

---

## 2. 数据流全景

```
武器命中
  │
  ├─→ LivingHurtEvent → EntityBuffListener.onLivingHurt()
  │     │  识别攻击玩家 (getSourceOfDamage → getEntity 回退)
  │     │  读取手持武器面板 (basic_damage / ice_damage)
  │     │  读取全身 NBT 属性 (NbtAttributeHelper.readAll)
  │     │  计算理论面板伤害 → 存入 records[entityId]
  │     │
  │     └─→ PotionBleed.performEffect()
  │           读取 EntityBuffListener.getDamage(entityId)
  │           计算 DOT = 1 + totalPanelDamage × 0.1 × (amplifier+1)
  │           attackEntityFrom(DamageSource.generic, damage)
  │           重置 hurtResistantTime = 0  ← 关键！否则后续 DOT 被无敌帧阻挡
  │
  └─→ LivingUpdateEvent → EntityBuffListener.onLivingUpdate()
        检测实体 Buff 状态，无 Buff → 清理伤害记录
```

---

## 3. 核心组件详解

### 3.1 PotionBleed — 流血 DOT

**Potion ID**: 30 | **触发间隔**: 2 tick (0.1s) | **DOT 系数**: 0.1

```java
// DOT 公式
float damage = 1F + (totalPanelDamage * 0.1F * (amplifier + 1));
```

**Pitfall: `hurtResistantTime` 阻挡**  
MC 的 `attackEntityFrom()` 成功后会将 `Entity.hurtResistantTime` 设为 10（约 0.5 秒无敌）。由于 DOT 每 2 tick 触发一次（0.1 秒），第一次成功后后续 4 次全部被无敌帧阻挡，**表现为只扣血一次**。

修复方法：在 `performEffect()` 的 `attackEntityFrom` 之后立即重置：
```java
_entity.attackEntityFrom(DamageSource.generic, damage);
// 必须重置！否则后续 DOT tick 全部被 hurtResistantTime 阻挡
((net.minecraft.entity.Entity)_entity).hurtResistantTime = 0;
```

> **字节码安全**：`_entity` 的编译类型是 `EntityLivingBase`（SRG: `of`），而 `hurtResistantTime` 字段定义在 `Entity`（SRG: `nn`）。用 `Entity` 强转确保字节码 owner = `nn`，命中 mcp2srg.srg 中已有的 `FD: nn/af` 条目。

**`isReady()` 策略**：始终返回 `_duration > 0`，间隔控制交给 `performEffect()` 内部用世界时间 `worldTime % 2 == 0` 判断。避免 duration 被 MC 重新施加 Potion 时打乱节奏。

### 3.2 EntityBuffListener — 伤害数据持久化

**注册方式**：
```java
EntityBuffListener listener = new EntityBuffListener();
MinecraftForge.EVENT_BUS.register(listener);
```

**监听三个事件**：

| 事件 | 方法 | 职责 |
|------|------|------|
| `LivingHurtEvent` | `onLivingHurt()` | 计算理论面板伤害，持久存储（不消费） |
| `LivingUpdateEvent` | `onLivingUpdate()` | 扫描 Buff 状态，无 Buff 时清理记录 |
| `LivingDeathEvent` | `onLivingDeath()` | 实体死亡时清理记录 |

**攻击者识别逻辑**（`onLivingHurt`）：
1. `DamageSource.getSourceOfDamage()` — 间接攻击源（弹丸 → 射击玩家）
2. `DamageSource.getEntity()` — 直接攻击源（近战 → 玩家自身）
3. 非玩家攻击 → `_event.ammount` 兜底

**伤害计算公式**：
```
totalPanelDamage = bowPower.applyTo(basic_damage)    // 物理伤害
                 + icePower.applyTo(ice_damage)      // 穿甲伤害
                 + attackDamage.applyTo(0F)           // 额外 attackDamage
                 + wandDamage.applyTo(0F)             // 法杖属性

其中 applyTo(base) = ((base + op0) × (1 + op1)) × op2
```

> **为什么不用 `_event.ammount`？** `LivingHurtEvent.ammount` 是**已经被护甲/抗性减免**的伤害值，用它计算 DOT 会丢失大量面板伤害。改为从玩家手持武器 + 全身 NBT 属性直接计算**理论面板值**。

**静态访问接口**（供 PotionBleed 调用）：
```java
float totalPanelDmg = EntityBuffListener.getDamage(entityId);  // 持久存储，不消费
```

### 3.3 DamageRecord — 内部数据类

```java
private static class DamageRecord {
    float totalPanelDamage;     // 理论完整面板伤害（v2 核心字段）
    int   attackerEntityId;     // 攻击来源玩家的 entityId
    long  lastDOTTick;          // 上次 DOT 触发时的世界 tick（预留）
}
```

伤害数据**覆盖**而非累加（同一次攻击可能触发多次 `LivingHurtEvent`），在整个 Buff 持续期间保留。

---

## 4. 触发布 Buff

### 4.1 枪械 / 近战 — 通过 Builder

```java
// GunNbtBuilder 或 SwordNbtBuilder
.effectWounded(new int[]{PotionBleed.ID, 200, 5})  // ID, duration(tick), amplifier
.probWounded(new float[]{1.0F})                     // 100% 触发
```

格式: `{potionId, duration, amplifier, ...}` 可串联多个 Buff。

### 4.2 覆写 shootOnce() 的枪械

覆写 `onPlayerStoppedUsing()` 的枪械（如 `BoltActionRifle_2`），引擎的 `shootOnce()` 被绕过，**必须手动注入枪械的 DamagePacket**：

```java
// 弹药 DamagePacket
if (_ammo.onAttack != null) {
    bullet.setDamagePacket(_ammo.onAttack);
}
// 枪械 DamagePacket（包含 effect_wounded / 流血等）
if (this.onAttack != null) {
    bullet.setDamagePacket(this.onAttack);
}
```

> **顺序重要**：枪械的 `this.onAttack` 写在后面才能覆盖弹药的默认效果。

---

## 5. ID 分配

| 范围 | 用途 |
|------|------|
| 0-23 | MC 原版 Potion |
| 24-28 | Adventurer Engine (PotionEasyHurt 等) |
| 30+ | ATcraft Addon 自定义 |

---

## 6. SRG 重映射 (关键)

自定义类中访问 Minecraft/Forge 类的字段或方法时，SpecialSource 按**精确 owner class** 匹配 MD/FD 条目。务必检查 SRG jar 字节码：

```powershell
javap -c -classpath <srg_jar> atwandaddon.buff.potion.PotionBleed
```

**常见需要追加条目的情况**：
- 覆写 MC 方法（`onUpdate`、`hitEntity` 等）→ 需 MD 条目，否则 override 静默失败
- 在非 `Entity` 类型变量上访问 `Entity` 字段（如 `EntityLivingBase.hurtResistantTime`）→ 需 FD 条目或强转
- 调用父类方法 → 需 MD 条目，否则 `NoSuchMethodError`

**已追加的关键条目** (mcp2srg.srg 末尾)：
```
FD: uf/k   net/minecraft/entity/player/EntityPlayer/entityId
FD: of/k   net/minecraft/entity/EntityLivingBase/entityId
FD: nn/af  net/minecraft/entity/Entity/hurtResistantTime
```

详见 SKILL.md Pitfall #4 和 #10。

---

## 7. Markdown Checklist 类型要求

所有传入 `StringMap` 的数值必须是 **`Double`** 类型（引擎 `JsonHelper.getInt/getFloat` 内部做 `(Double)` 强转，Integer/Float 会抛 `ClassCastException`）。

---

## 8. 调试技巧

- **日志输出**：用 `cpw.mods.fml.common.FMLLog.log(Level.INFO, msg)` 代替 `System.out`，输出会写入 `.minecraft/ForgeModLoader-client-0.log`
- **SRG 验证**：每次编译后用 javap 检查 SRG jar 中的字段/方法名是否已变为 SRG 形式
- **DOT 不触发诊断顺序**：(1) performEffect 是否被调用？(2) getDamage 是否返回 >0？(3) attackEntityFrom 返回值是否为 true？(4) hurtResistantTime 是否已重置？

---

## 9. 新增 Buff 检查清单

- [ ] 创建 `PotionXxx extends Potion` 类，分配新 ID（30+）
- [ ] 在 `ATcraftAddon.preInit()` 中 `new PotionXxx()`
- [ ] 在武器/弹药 Builder 中配置 `.effectWounded()` + `.probWounded()`
- [ ] 如需读取玩家面板伤害，在 `EntityBuffListener.onLivingHurt()` 中扩展武器类型判断
- [ ] 如 Potion 涉及 `attackEntityFrom`，确保重置 `hurtResistantTime`
- [ ] 如覆写 MC 方法，追加 SRG 映射条目到 `mcp2srg.srg`
- [ ] 编译 → SpecialSource → 验证 SRG 字节码 → 部署 → 测试

---

## 10. 文件索引

| 文件 | 职责 |
|------|------|
| `buff/potion/PotionBleed.java` | 流血 DOT 实现核心 |
| `buff/EntityBuffListener.java` | 伤害数据持久化 + Buff 生命周期管理 |
| `buff/BuffFactory.java` | Buff 工厂（待扩展） |
| `buff/BuffMonitor.java` | [已弃用] 旧版伤害累加器 |
| `buff/NbtDamageListener.java` | [已弃用] 旧版 NBT 属性监听器 |
| `buff/bleed/` | [备用] 独立多栈流血系统 |
| `weapon/nbt/NbtAttributeHelper.java` | NBT 属性读取 + Operation 计算 |
| `weapon/gun/BoltActionRifle_2.java` | 覆写 shootOnce 的参考示例 |
| `forge-src/forge/mcp/conf/mcp2srg.srg` | SRG 映射表（末尾追加条目） |
