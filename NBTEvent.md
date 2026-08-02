# 枪魂事件系统 (NBTs-1.0.2) — 完整参考文档

> **作者**: ATcraft-Addon 先锋勘探  
> **日期**: 2026-08-02  
> **目标**: 为其他 AI Agent 提供枪魂武器开发的完整知识库

---

## 一、系统三层架构

```
┌──────────────────────────────────────────────────────────┐
│  NBTs-1.0.2 (ASM 字节码注入层)                            │
│  钩入 vanilla ItemStack 方法，读取 NBT 标签自动执行逻辑    │
│  来源: 第三方 mod，通过 Coremod 注入 Minecraft 原版类       │
├──────────────────────────────────────────────────────────┤
│  adventurer_engine (引擎层)                               │
│  BasicRangedWeapon.shootOnce() → 扣耐久                   │
│  BasicItemSword.hitEntity() → 扣耐久                      │
│  EventLoader → dur_delay 计时器                           │
│  CommonConfig → resumeRate / resumeStop                   │
├──────────────────────────────────────────────────────────┤
│  ATcraft-Addon (Addon 层)                                 │
│  NbtEventTags.write() → 写入枪魂标签到 ItemStack NBT       │
│  各武器 onUpdate() → 每 tick 调用 write() 保底            │
└──────────────────────────────────────────────────────────┘
```

---

## 二、NBTs 10 个 ASM 钩入点

NBTs mod 通过 ASM 字节码注入到 Minecraft 原版类中，在特定方法被调用时读取 ItemStack 上的 NBT 标签并执行对应逻辑。

| # | 钩入方法 | 消费的 NBT 标签 | 触发时机 |
|---|---------|----------------|---------|
| 1 | `ItemStack.updateAnimation()` | `onUpdate_HealDamageTimer`、`onUpdate` | **每 tick 自动修复耐久 / 给药水** |
| 2 | `ItemStack.hitEntity()` | `hitEntity_HealDamage`、`hitEntity_PlaySoundEffect`、`hitEntity_Target`、`hitEntity_Attacker` | **近战命中 → 修复耐久 / 音效 / 药水** |
| 3 | `ItemStack.useItemRightClick()` | `onItemRightClick_Disable`、`onItemRightClick_dur`、`onItemRightClick_PlaySoundEffect`、`onItemRightClick` | **右键 → 枪魂检查 / 扣耐久 / 音效 / 药水** |
| 4 | `ItemStack.onPlayerStoppedUsing()` | `onPlayerStoppedUsing_dur`、`onPlayerStoppedUsing` | **松右键 → 耐久变化 / 药水** |
| 5 | `ItemStack.isItemStackDamageable()` | `DisableBreak` | 覆盖是否可损坏（禁止损坏） |
| 6 | `ItemStack.getMaxItemUseDuration()` | `MaxItemUseDuration` | 覆盖最大使用时长（tick） |
| 7 | `ItemStack.onFoodEaten()` | `onEaten` | 吃掉物品 → 给药水效果 |
| 8 | `ItemStack.tryPlaceItemIntoWorld()` | `onItemUse` | 对方块使用 → 给药水效果 |
| 9 | `Item.onEntitySwing()` | `onEntitySwing_PlaySoundEffect`、`onEntitySwing_Weakness` | 挥动 → 音效 / 饥饿虚弱 |
| 10 | `InventoryPlayer.decrementAnimations()` | `onArmorTickUpdate`、`onArmorTickUpdateUnion` | 护甲每 tick / 全套共鸣 |

### 音效约定

所有 `PlaySoundEffect` 标签的值自动加 `"tpcore."` 前缀。例如值 `"blade"` → 实际播放音效 `"tpcore.blade"`。

---

## 三、枪魂系统 4 个核心标签

这 4 个标签实现「枪魂精力」的消耗与恢复闭环。

| NBT Key | 常量名 | 类型 | 含义 |
|---------|--------|------|------|
| `onUpdate_HealDamageTimer` | `TAG_HEAL_TIMER` | int | 每 N tick 自动修复 1 点耐久。`0` = 不自动修复 |
| `onItemRightClick_Disable` | `TAG_DISABLE_THRESHOLD` | int | `damage >= 此值` 时阻止右键（枪魂耗尽）。不设置 = 永远不禁用 |
| `onItemRightClick_dur` | `TAG_RIGHT_CLICK_DUR` | int | 右键时耐久变化量。正值 = 消耗枪魂，负值 = 恢复枪魂，`0` = 不额外消耗（引擎已扣） |
| `hitEntity_HealDamage` | `TAG_HIT_HEAL` | int | 近战命中时耐久修复量。**通常设负值**（`-1` = 回 1 耐久，`-2` = 回 2） |

### 耐久度含义反转

枪魂武器中，`damage` 值与传统 MC 物品含义相反：

```
正常 MC 物品:  damage=0  → 全新      damage=100 → 快坏了
枪魂武器:     damage=0  → 满枪魂    damage=9   → 精力耗尽

自动恢复: damage -= 1  (值变小 = 回枪魂)
近战恢复: damage -= 1  (值变小 = 回枪魂)
开枪消耗: damage += 1  (值变大 = 耗枪魂)
```

---

## 四、药水效果标签

| NBT Key | 常量名 | 触发时机 | 格式 |
|---------|--------|---------|------|
| `onItemRightClick` | `TAG_RIGHT_CLICK_POTION` | 右键时 | `int[] = [id, dur, amp, id, dur, amp, ...]` |
| `onPlayerStoppedUsing` | `TAG_STOP_USING_POTION` | 松右键时 | 同上 |
| `onUpdate` | `TAG_ON_UPDATE_POTION` | 每 tick 手持时 | 同上 |
| `hitEntity_Target` | `TAG_HIT_TARGET` | 命中目标时 | 同上 |
| `hitEntity_Attacker` | `TAG_HIT_ATTACKER` | 命中时给攻击者 | 同上 |
| `onEaten` | `TAG_ON_EATEN` | 吃掉物品时 | 同上 |
| `onItemUse` | `TAG_ON_ITEM_USE` | 对方块使用时 | 同上 |
| `onArmorTickUpdate` | `TAG_ARMOR_TICK` | 护甲每 tick | 同上 |
| `onArmorTickUpdateUnion` | `TAG_ARMOR_UNION` | 全套护甲共鸣 | `NBTTagList<String>` |

---

## 五、其他功能标签

| NBT Key | 常量名 | 含义 |
|---------|--------|------|
| `onPlayerStoppedUsing_dur` | `TAG_STOP_USING_DUR` | 松右键时耐久变化量 |
| `MaxItemUseDuration` | `TAG_MAX_USE_DURATION` | 覆盖最大使用时长（tick） |
| `DisableBreak` | `TAG_DISABLE_BREAK` | 覆盖 `isItemStackDamageable()` → 物品不可损坏 |
| `onEntitySwing_Weakness` | `TAG_SWING_WEAKNESS` | 饥饿 ≤ 0 且非手持时阻止攻击 |
| `onEntitySwing_PlaySoundEffect` | `TAG_SWING_SOUND` | 挥动武器时播放音效 |
| `hitEntity_PlaySoundEffect` | `TAG_HIT_SOUND` | 近战命中时播放音效 |
| `onItemRightClick_PlaySoundEffect` | `TAG_RIGHT_CLICK_SOUND` | 右键时播放音效 |

---

## 六、三套预置配置 (Preset)

```java
// NbtEventTags.java 内置三类武器枪魂预设

/** 速射类：快速恢复（3 秒 / 点），6 发锁住，近战回 1 */
public static final Preset PRESET_PISTOL  = new Preset(60, 6, 1, -1);

/** 狙击类：慢速恢复（6.5 秒 / 点），5 发锁住，引擎自带扣耐久无需额外消耗 */
public static final Preset PRESET_SNIPER  = new Preset(130, 5, 0, -1);

/** 散弹类：中速恢复（5 秒 / 点），3 发锁住，近战回 2 */
public static final Preset PRESET_SHOTGUN = new Preset(100, 3, 1, -2);
```

| 预设 | healTimer | disableThreshold | shootCost | meleeHeal | 自动恢复 | 锁枪阈值 | 右键消耗 | 近战恢复 |
|------|-----------|-----------------|-----------|-----------|---------|---------|---------|---------|
| PISTOL | 60 (3s) | 6 发 | +1 | -1 | 快 | 6 | 追加扣 1 | 回 1 |
| SNIPER | 130 (6.5s) | 5 发 | 0 | -1 | 慢 | 5 | 引擎已扣 | 回 1 |
| SHOTGUN | 100 (5s) | 3 发 | +1 | -2 | 中 | 3 | 追加扣 1 | 回 2 |

---

## 七、自定义 Preset

```java
// 创建自定义枪魂配置
// Preset(healTimer, disableThreshold, shootCost, meleeHeal)
NbtEventTags.Preset myPreset = new NbtEventTags.Preset(
    80,   // 每 4 秒回 1 枪魂
    4,    // 4 发后锁住
    0,    // 引擎已扣耐久，不追加
    -2    // 近战回 2 枪魂
);
NbtEventTags.write(stack, myPreset);
```

### 参数设计指南

| 参数 | 设计建议 |
|------|---------|
| `healTimer` | 20 tick = 1 秒。突击步枪建议 40~60，狙击建议 100~160，散弹建议 80~120 |
| `disableThreshold` | 通常取 `durability / 2` 或更低。阈值低 → 更频繁锁枪 |
| `shootCost` | 引擎已通过 `shootOnce()` 扣 1 点耐久。只有需要**额外消耗**时才设正值 |
| `meleeHeal` | 设负值。`-1` = 枪托攻击回 1，`-2` = 回 2。设 `0` = 近战不回枪魂 |

---

## 八、完整数据流（雪原探索者示例）

```
玩家手持 雪原探索者 (durability=9)

  │
  ├─ onUpdate() 每 tick → NbtEventTags.write(stack, PRESET_SNIPER)
  │   ├─ 仅首次: HEAL_TIMER=130, DISABLE=5, SHOOT_COST=0, MELEE_HEAL=-1
  │   └─ 后续: hasKey 检查 → 跳过写入（保留外部 NBT 编辑器的修改）
  │
  ├─ NBTs::updateAnimation() 每 tick 读取 HEAL_TIMER=130
  │   └─ 每 130 tick (≈6.5 秒) → damage -= 1（回 1 点枪魂）
  │
  ├─ 玩家右键开枪 → 引擎 shootOnce()
  │   ├─ NBTs::useItemRightClick() 检查 DISABLE=5
  │   │   ├─ damage < 5  → 允许开枪
  │   │   └─ damage >= 5 → 阻止开枪（枪魂耗尽！）
  │   ├─ SHOOT_COST=0 → NBTs 不额外扣耐久
  │   └─ 引擎 shootOnce() → damageItem(1, player) → damage += 1
  │
  ├─ 玩家近战枪托攻击 → NBTs::hitEntity()
  │   ├─ 读取 HIT_HEAL=-1 → damage -= 1（回 1 枪魂）
  │   └─ 引擎 BasicItemSword 同时调用 damageItem(1) → damage += 1
  │       → 净效果: -1 + 1 = 0（枪魂不变！）
  │       → 如需净回枪魂，设 MELEE_HEAL=-2
  │
  └─ 5 发后 damage=5 → DISABLE=5 → 禁止右键
      玩家必须近战或等待自动恢复才能继续开枪
```

---

## 九、给 AI Agent 的武器开发指南

### 创建新「枪魂」武器的 4 个步骤

#### Step 1 — 用 `GunNbtBuilder` 构造 NBT

```java
package atwandaddon.weapon.gun;

import java.util.Arrays;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import atwandaddon.weapon.nbt.GunNbtBuilder;
import atwandaddon.weapon.nbt.NbtEventTags;

/**
 * 示例枪魂武器。
 *
 * <p>枪魂机制:
 * <ul>
 * <li>每枪消耗 1 引擎耐久</li>
 * <li>累计 N 发后 onItemRightClick_Disable 阻止开枪</li>
 * <li>onUpdate_HealDamageTimer 自动回枪魂</li>
 * <li>hitEntity_HealDamage 近战回枪魂</li>
 * </ul>
 */
public class MyGun extends BasicRangedWeapon
{
    public MyGun(int _id)
    {
        super(GunNbtBuilder.create(_id, "my_gun")
            // ---- 基础属性 ----
            .icon("my_gun")                  // 纹理: assets/adventurer_engine/textures/items/my_gun.png
            .maxStack(1)
            .durability(100)                 // 最大枪魂点数
            .unbreakable(false)              // 必须 false！否则枪魂系统不生效
            // ---- 蓄力系统 ----
            .chargeStage(1)
            .chargeTime(4)
            .chargeIcons(Arrays.asList("my_gun"))
            // ---- 伤害 ----
            .basicDamage(10.0)
            .iceDamage(5.0)
            .knockback(0.3)
            // ---- 射击参数 ----
            .shootMode(ShootMode.SEMI)
            .shootNumber(1)
            .shootDelay(10)                  // 半自动冷却 10 tick
            .accuracy(0.85)
            .shootSound("bow")
            .maxRange(50.0)
            .maxSpeed(4.0)
            // ---- 弹道 ----
            .gravity(false)
            .penetrate(false)
            // ---- 弹药 ----
            .allowedAmmo(Arrays.asList(MyAmmo.AMMO_NAME))
            // ---- 构建 NBT ----
            .build());
    }

    /** 每 tick 挂载枪魂标签（仅首次写入） */
    @Override
    public void onUpdate(ItemStack _stack, World _world, Entity _entity, int _slot, boolean _isCurrent)
    {
        super.onUpdate(_stack, _world, _entity, _slot, _isCurrent);
        if (_isCurrent && _entity instanceof EntityPlayer)
        {
            // 使用预置配置或自定义 Preset
            NbtEventTags.write(_stack, NbtEventTags.PRESET_PISTOL);
        }
    }
}
```

#### Step 2 — 注册到 `ATcraftAddon.java`

```java
// ATcraftAddon.java — preInit() 中添加:
public static MyGun myGun;

// 注册（ID 范围 25000+，避开引擎 12001~12142）
myGun = (MyGun) new MyGun(25007)
    .setUnlocalizedName("myGun")
    .setCreativeTab(tabAddon);
GameRegistry.registerItem(myGun, "myGun");
```

#### Step 3 — 纹理文件

```
forge/src/main/resources/assets/adventurer_engine/textures/items/my_gun.png
```

> 🚨 引擎硬编码纹理论域为 `adventurer_engine:`，不要放到 `assets/atcraftaddon/` 下！

#### Step 4 — 编译部署

```powershell
# 完整构建部署链（3 步缺一不可）
$env:JAVA_HOME='F:\jdk8\jdk8u492-b09'
cd F:\ATcraft-Addon-164\forge

# Step 1: 编译
.\gradlew.bat build

# Step 2: MCP → SRG 重映射
& 'F:\jdk8\jdk8u492-b09\bin\java.exe' `
  -jar 'F:\ATcraft-Addon-164\forge\forge-src\forge\mcp\runtime\bin\specialsource.jar' `
  -i 'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0.jar' `
  -o 'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar' `
  -m 'F:\ATcraft-Addon-164\forge\forge-src\forge\mcp\conf\mcp2srg.srg' -r

# Step 3: 部署
Copy-Item `
  'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar' `
  'C:\Users\Yuanqiubai\Desktop\☆冒险者传说V2.0【制作中】\☆冒险者传说V1.91_测试调试\.minecraft\mods\ATcraft-Addon-164-1.0.jar' `
  -Force
```

---

## 十、引擎层耐久系统 (adventurer_engine 反编译结果)

> 🚨 **重要发现**: 引擎层存在两套**独立**的耐久系统！
> 1. **物品耐久系统** — `BasicRangedWeapon.shootOnce()` / `BasicItemSword.hitEntity()` 直接扣物品 `damage`
> 2. **玩家 `dur_delay` 系统** — 控制**饥饿值恢复**的计时器，间接影响蓄力伤害（`use_duration` 同步为饥饿值）

### 10.1 物品耐久消耗

#### `BasicRangedWeapon.shootOnce()` — 开枪扣耐久

```java
// 引擎源码（反编译）— BasicRangedWeapon.java
private ItemStack shootOnce(EntityPlayer player, ItemStack item, String ammoName) {
    if (!BasicRangedWeapon.hasAmmo(player, ammoName)) return item;
    if (!this.unbreakable) {
        item.damageItem(1, (EntityLivingBase) player);  // ⬅ 每发子弹扣 1 耐久
    }
    // 计算枪魂等级系数...
    int chargeStage = Math.min((int)Math.floor(item.use_duration / charge_time * charge_stage), charge_stage);
    float chargeRatio = chargeStage / charge_stage;
    float basicDamage = this.basic_damage * chargeRatio;
    // ...生成弹射物、播放音效...
    PlayerTools.consumeAmmo(player, ammoName);
    return item;
}
```

`handleShoot` 循环 `shoot_number` 次调用 `shootOnce` — **多弹丸武器每发都独立扣耐久！**

#### `BasicItemSword.hitEntity()` — 近战扣耐久

```java
// 引擎源码（反编译）— BasicItemSword.java
public boolean hitEntity(ItemStack item, EntityLivingBase wounded, EntityLivingBase attacker) {
    if (!this.unbreakable) {
        item.damageItem(1, attacker);  // ⬅ 每次近战扣 1 耐久
    }
    if (this.onAttack != null && attacker instanceof EntityPlayer) {
        this.onAttack.causeDamage((EntityPlayer) attacker, wounded, 0.0f, this.ice_damage, null);
    }
    return true;
}
```

`getMaxItemUseDuration` 返回 **72000**（半自动模式），允许玩家长时间按住蓄力。

### 10.2 玩家 `dur_delay` 系统（饥饿值恢复，与物品耐久无关！）

```java
// CommonConfig.java
public static int resumeRate;   // 默认 10 — 每 resumeRate tick 恢复 1 点饥饿值
public static int resumeStop;   // 默认 40 — 攻击/使用时暂停恢复 resumeStop tick
```

#### `EventLoader.onUpdate_UpdatePlayerStatus` — 每 tick 递减

```java
private static void onUpdate_UpdatePlayerStatus(LivingEvent.LivingUpdateEvent event) {
    if (event.entity.worldObj.isRemote || !(event.entity instanceof EntityPlayer)) return;
    EntityPlayer player = (EntityPlayer) event.entity;
    player.shoot_delay = player.shoot_delay > 0 ? --player.shoot_delay : 0;
    player.dur_delay   = player.dur_delay   > 0 ? --player.dur_delay   : 0;  // ⬅ 每 tick -1
}
```

#### `EventLoader.onUpdate_ApplyGreenRingAndDuring` — dur_delay=0 时触发恢复

```java
private static void onUpdate_ApplyGreenRingAndDuring(LivingEvent.LivingUpdateEvent event) {
    // ...
    if (player.dur_delay == 0) {
        player.dur_delay = CommonConfig.resumeRate;  // 重置为 resumeRate (默认 10)
        if (flag && !player.isPotionActive(Potion.hunger)) {  // flag = 持有 ItemDuring
            player.getFoodStats().addStats(Math.min(player.getFoodStats().getFoodLevel() + 1, 20), 0.0F);
            // 恢复 1 点饥饿值（上限 20）
        }
        // GreenRing 最大生命值处理...
    }
}
```

#### `RenewDurationDelayPacket` — 攻击/使用时暂停恢复

```java
// 由 ClientEventLoader 在玩家按下攻击键/使用键时发送
public class RenewDurationDelayPacket extends RCsPacket {
    @Override
    public void execute(EntityPlayer player, Side side) {
        player.dur_delay = CommonConfig.resumeStop;  // ⬅ 重置为 resumeStop (默认 40)
    }
}
```

#### 🧩 `use_duration` ↔ 饥饿值联动（蓄力伤害系数！）

```java
// EventLoader.onUpdate_RefreshUseDuration
private static void onUpdate_RefreshUseDuration(LivingEvent.LivingUpdateEvent event) {
    if (!(event.entity instanceof EntityPlayer)) return;
    EntityPlayer player = (EntityPlayer) event.entity;
    if (player.getItemInUse() != null) {
        player.getItemInUse().use_duration = player.getFoodLevel();  // ⬅ use_duration = 饥饿值！
    }
}
```

**这意味着**: 饥饿值越高 → `use_duration` 越大 → 蓄力等级越高 → 射击伤害倍数越高！

```java
// shootOnce() 中的枪魂伤害计算
int chargeStage = Math.min((int)Math.floor(use_duration / charge_time * charge_stage), charge_stage);
float chargeRatio = chargeStage / charge_stage;  // 0.0 ~ 1.0 的伤害系数
float basicDamage = this.basic_damage * chargeRatio;
```

### 10.3 ⚠️ 空桩方法 — `onLivingHurt_PlayerDurability`

```java
private static void onLivingHurt_PlayerDurability(LivingHurtEvent event, EntityPlayer player) {
    // 方法体完全为空！玩家受伤时不消耗物品耐久（未实现的功能）
}
```

### 10.4 `ItemDuring` — 耐久戒指（dur_ring, 物品 ID 7761）

持有 `ItemDuring` 时，`dur_delay=0` 触发饥饿值恢复 + 绿戒指生命上限更新。这是连接 `dur_delay` 系统和实际恢复效果的关键物品。

### 10.5 两套系统的关系

```
┌──────────────────────────────────────────────────────────────┐
│  系统 A: 物品耐久 (NBTs 枪魂标签)                               │
│  ───────────────────────────────────                         │
│  控制: 枪魂武器的 damage 值                                    │
│  恢复: onUpdate_HealDamageTimer (自动) / hitEntity_HealDamage │
│  消耗: shootOnce() damageItem(1) / hitEntity() damageItem(1)  │
│  限制: onItemRightClick_Disable (枪魂耗尽锁枪)                  │
│  适用: 所有标记了 NBT 枪魂标签的物品                             │
├──────────────────────────────────────────────────────────────┤
│  系统 B: 玩家饥饿值 (dur_delay 计时器)                          │
│  ───────────────────────────────────                         │
│  控制: 玩家饥饿值 (foodLevel)                                  │
│  恢复: dur_delay=0 + 持有 ItemDuring → +1 饥饿值               │
│  消耗: 攻击/使用 → RenewDurationDelayPacket → dur_delay=40    │
│  联动: use_duration = foodLevel → 影响蓄力伤害倍数             │
│  适用: 持有 ItemDuring 或绿戒指的玩家                           │
└──────────────────────────────────────────────────────────────┘
```

**两者独立运作，互不干扰。** 枪魂武器同时受益于两套系统：系统 A 控制开枪权限，系统 B 通过饥饿值影响蓄力伤害。

---

## 十一、常见问题

### Q: 为什么枪魂不恢复？
A: 检查三点：
1. `onUpdate()` 是否调用了 `NbtEventTags.write()`？
2. `unbreakable` 是否设为 `false`？
3. NBTs mod 是否已安装？

### Q: 近战攻击枪魂没变化？
A: 引擎 `BasicItemSword.hitEntity()` 同时扣 1 耐久。设 `meleeHeal=-1` 时净效果为 0（互相抵消）。如需净回枪魂，设 `meleeHeal=-2` 或更大负值。

### Q: 如何让武器只能近战恢复、不能自动恢复？
A: 设 `healTimer=0`：
```java
NbtEventTags.Preset meleeOnly = new NbtEventTags.Preset(0, 5, 1, -2);
```

### Q: 如何让武器只能自动恢复、不能近战恢复？
A: 设 `meleeHeal=0`：
```java
NbtEventTags.Preset autoOnly = new NbtEventTags.Preset(100, 5, 1, 0);
```

### Q: dur_delay 系统和 NBTs 枪魂系统是什么关系？
A: **完全独立，互不干扰。** `dur_delay` 控制玩家饥饿值恢复（需要持有 `ItemDuring` 耐久戒指），间接影响蓄力伤害倍数（`use_duration = foodLevel`）。NBTs 枪魂标签直接控制物品 `damage` 值（开枪权限）。两者可以同时生效。

### Q: 多弹丸武器（shoot_number > 1）如何扣耐久？
A: 引擎 `handleShoot()` 循环调用 `shootOnce()`，**每发弹丸独立扣 1 耐久**。例如 `shoot_number=10` → 一次开枪扣 **10 耐久**！设计多弹丸武器时务必增大 `durability` 或 `disableThreshold`。

### Q: `onLivingHurt_PlayerDurability` 为什么是空的？
A: 这是引擎中一个**未实现的功能**。原本设计是玩家受伤时扣物品耐久，但方法体被留空。可能是开发中途砍掉的功能。

---

## 十二、文件索引

### Addon 层

| 文件 | 路径 | 作用 |
|------|------|------|
| `NbtEventTags.java` | `forge/src/main/java/atwandaddon/weapon/nbt/` | NBT 标签常量 + write() + Preset |
| `GunNbtBuilder.java` | 同上 | 枪械 NBT Fluent Builder |
| `AmmoNbtBuilder.java` | 同上 | 弹药 NBT Fluent Builder |
| `SnowfieldExplorer.java` | `forge/src/main/java/atwandaddon/weapon/gun/` | 枪魂武器完整示例 |
| `SnowEagleAP.java` | `forge/src/main/java/atwandaddon/weapon/ammo/` | 弹药完整示例 |
| `ATcraftAddon.java` | `forge/src/main/java/atwandaddon/` | 注册入口 |

### 引擎层（反编译参考）

| 类 | 包 | 关键内容 |
|----|-----|---------|
| `CommonConfig` | `com.adventurer_engine.common` | `resumeRate=10`, `resumeStop=40` |
| `EventLoader` | `com.adventurer_engine.common.event` | `dur_delay` 计时器、`use_duration`↔饥饿值联动 |
| `BasicRangedWeapon` | `...items.blank.ranged_weapon` | `shootOnce()` 扣耐久、蓄力伤害系数 |
| `BasicItemSword` | `...items.blank` | `hitEntity()` 扣耐久 |
| `BasicAmmo` | `...items.blank.ranged_weapon` | `unbreakable`、`maxAmmoAmount` |
| `PlayerTools` | `com.adventurer_engine.common` | `consumeAmmo()` 弹药消耗逻辑 |
| `RenewDurationDelayPacket` | `...network.client2server` | 攻击/使用时重置 `dur_delay` |
| `ItemDuring` | `com.adventurer_engine.common.items` | 耐久戒指（ID 7761），触发饥饿值恢复 |
| `ItemGreenRing` | `com.adventurer_engine.common.items` | 绿戒指，通过碎片增加最大生命值 |
