# ATcraft-Addon-164

冒险者传说整合包（MC 1.6.4）的武器扩展 Mod，基于 Forge 9.11.1.916 + adventurer_engine。

---

## 环境要求

| 依赖 | 路径 / 版本 |
|------|-------------|
| JDK（编译用） | `F:\jdk8\jdk8u492-b09` — JDK 1.8.0_492 |
| JDK 21（CFR 反编译） | `F:\java 11` — OpenJDK 21.0.9 |
| JRE（运行用） | `F:\java` — JRE 1.8.0_411 |
| Forge MDK | 已包含在仓库 `forge\` 目录中 |
| MCP | Forge MDK 内置 — MCP 8.11 |
| adventurer_engine | `libs/adventurer_engine.jar` |
| NBTs-1.0.2 | 第三方 mod（枪魂 ASM 注入层） |

> ⚠️ `F:\java` 是 JRE 不能编译。编译用 `F:\jdk8`，CFR 反编译用 `F:\java 11`。

---

## 快速开始

### 1. 克隆仓库

```powershell
git clone https://github.com/yuanqiubai/-Legend-of-the-Adventurer-Modpack.git
cd -Legend-of-the-Adventurer-Modpack
```

### 2. 编译 & 部署（完整 3 步）

```powershell
$env:JAVA_HOME = 'F:\jdk8\jdk8u492-b09'
cd F:\ATcraft-Addon-164\forge

# Step 1: 编译
.\gradlew.bat build

# Step 2: MCP → SRG 重映射（必须！否则 NoSuchFieldError）
& 'F:\jdk8\jdk8u492-b09\bin\java.exe' `
  -jar 'F:\ATcraft-Addon-164\forge\forge-src\forge\mcp\runtime\bin\specialsource.jar' `
  -i 'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0.jar' `
  -o 'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar' `
  -m 'F:\ATcraft-Addon-164\forge\forge-src\forge\mcp\conf\mcp2srg.srg' -r

# Step 3: 部署到整合包
Copy-Item `
  'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar' `
  'C:\Users\Yuanqiubai\Desktop\☆冒险者传说V2.0【制作中】\☆冒险者传说V1.91_测试调试\.minecraft\mods\ATcraft-Addon-164-1.0.jar' `
  -Force
```

> 🚨 **SRG 重映射不可跳过**：自定义 Entity 子类中的字段访问（motionX、dataWatcher 等）在 MCP 编译产物中是 MCP 名，服务器端需要 SRG 名才能正确 lookup。

---

## 项目结构

```
atwandaddon/
├── ATcraftAddon.java          # @Mod 主入口 + @NetworkMod + 物品/实体/ITickHandler 注册
├── ATcraftPacketHandler.java  # IPacketHandler 存根（@NetworkMod 必需）
├── CommonProxy.java           # 服务端代理
├── client/
│   ├── ClientProxy.java       # 客户端渲染注册
│   └── render/
│       └── LargeBulletRender.java  # 大型弹射物渲染
├── entity/
│   └── EntitySniperBullet.java     # 零散布狙击弹丸
├── buff/                           # ★ 通用 Buff 系统 (v2)
│   ├── BuffDef.java               # Buff 属性定义
│   ├── BuffFactory.java           # Buff 工厂
│   ├── BuffMonitor.java           # Buff 生命周期监控
│   ├── BuffRegistry.java          # Buff 注册中心
│   ├── EntityBuffListener.java    # 实体 Buff 事件监听
│   ├── NbtDamageListener.java     # NBT 伤害 Buff 处理
│   └── potion/
│       ├── PotionBleed.java       # 流血 Potion
│       └── PotionDeadline.java    # 死线 Potion
├── BuffIconShow.java          # Buff 图标显示
└── weapon/
    ├── ItemExampleGun.java        # ★ 通用枪械基类（多弹丸/精度/NBT 属性）
    ├── ItemExampleAmmo.java       # ★ 通用弹药基类
    ├── ItemExampleSword.java      # 通用近战武器基类
    ├── ammo/
    │   ├── SnowEagleAP.java       # 雪鹰穿甲弹（狙击用）
    │   ├── LeadBulletMod.java     # 铅弹改（弹幕图标载体）
    │   ├── MachineGunMagazine.java # 机枪弹匣
    │   └── Projectile.java       # 通用弹丸类型
    ├── BulletEntity/
    │   ├── LeadBulletModEntity.java
    │   └── ProjectileEntity.java
    ├── gun/
    │   ├── SnowfieldExplorer.java       # 雪原探索者（栓动狙击）
    │   ├── SnowfieldExplorerAddBayonet.java  # 带刺刀变体
    │   ├── BoltActionRifle.java        # 栓动步枪 1 号
    │   ├── BoltActionRifle_2.java      # 栓动步枪 2 号
    │   └── RotaryMachineGun.java       # 转轮机枪
    ├── melee/
    │   ├── HeavySword.java        # 重剑
    │   ├── Bayonet.java           # 刺刀
    │   └── Bayonet_2.java         # 刺刀 2 号
    └── nbt/
        ├── AmmoNbtBuilder.java    # 弹药 NBT Fluent Builder
        ├── GunNbtBuilder.java     # 枪械 NBT Fluent Builder
        ├── SwordNbtBuilder.java   # 近战武器 NBT Fluent Builder
        ├── NbtEventTags.java      # 枪魂标签常量 + Preset + write()
        └── NbtAttributeHelper.java # NBT AttributeModifiers 属性加成解析

adventurer_engine_src/             # adventurer_engine 反编译参考源码
libs/                              # 编译依赖 JAR
tool/                              # 开发工具脚本
```

---

## 三大核心系统

### 1. 枪魂系统 (NBTs-1.0.2 + NbtEventTags)

枪械耐久 = 枪魂精力槽。开枪消耗耐久 → 精力耗尽锁枪 → 自动恢复 / 近战恢复。

```
开枪 → shootOnce() damageItem(1) → damage += 1
锁枪 → onItemRightClick_Disable 阻止右键（damage >= 阈值）
恢复 → onUpdate_HealDamageTimer 每 N tick 自动 damage -= 1
近战 → hitEntity_HealDamage 枪托攻击 damage -= N
```

三种预置配置：`PRESET_SNIPER` (6.5s) / `PRESET_PISTOL` (3s) / `PRESET_SHOTGUN` (5s)

> 详细文档：**[NBTEvent.md](NBTEvent.md)** — 10 个 ASM 钩入点 / 药水标签 / 引擎耐久系统 / 完整数据流

### 2. 通用 Buff 系统

替代旧的 Bleed 特化实现。支持自定义 Buff 属性、生命周期监控、实体事件监听、NBT 伤害处理。

> 详细文档：**[BUFF_DEV_GUIDE.md](BUFF_DEV_GUIDE.md)**

### 3. NBT 属性加成 (NbtAttributeHelper)

解析 CustomNPCs 写入的 `AttributeModifiers` NBT，支持 `generic.attackDamage` / `ice_power` / `bow_power` 三类伤害，按 Operation 0/1/2 分组计算。

---

## 物品 ID 分配

> adventurer_engine 占用 ID 区间 12001–12142，分配新 ID 时避免冲突。

| ID | 物品 | 类型 |
|----|------|------|
| 25000 | ammoOrb（弹药宝珠） | 消耗品 |
| 25001 | flameWand（火焰法杖） | 蓄力武器（已弃用） |
| 25002+ | 新武器/弹药 | 按需分配 |

---

## 参考文档

| 文档 | 内容 |
|------|------|
| **[NBTEvent.md](NBTEvent.md)** | 枪魂事件系统完整参考 — NBTs 钩入点 / 预置配置 / 引擎耐久 / 武器模板 |
| **[plan.md](plan.md)** | 雪原探索者开发日志 — 全部踩坑记录 / SRG 映射 / 可配置散布 / 构建部署链 |
| **[BUFF_DEV_GUIDE.md](BUFF_DEV_GUIDE.md)** | Buff 系统 API 文档和使用示例 |
| **[NBTExplorer-雪原探索者速查表.md](NBTExplorer-雪原探索者速查表.md)** | NBTExplorer 调试速查 |
| **adventurer_engine_src/** | adventurer_engine 反编译源码（武器加载机制、NBT 结构参考） |
| `C:\Users\Yuanqiubai\.copilot\skills\atcraft-modding\SKILL.md` | AI 辅助开发技能文档 |

---

## 常用工具

| 工具 | 命令 |
|------|------|
| 编译 Mod | `cd forge && .\gradlew.bat build` |
| MCP→SRG 重映射 | SpecialSource: `java -jar specialsource.jar -i in.jar -o out.jar -m mcp2srg.srg -r` |
| 部署到整合包 | `Copy-Item build/libs/*-srg.jar → .minecraft/mods/` |
| CFR 反编译 JAR | `& "F:\java 11\bin\java.exe" -jar tool\cfr-0.152.jar <in.jar> --outputdir <out/>` |
| NBT 调试 | NBTExplorer 打开存档 level.dat / 物品 NBT |
