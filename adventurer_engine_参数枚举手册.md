# Adventurer Engine 枪械/弹药 — 全参数枚举手册

> 从 `adventurer_engine.jar` 反编译源码中提取的全部 JSON / NBT 参数键及可选值。
> 分为 **JSON 路径**（addon 开发者使用）和 **NBT 路径**（引擎内部加载 test.dat）。

---

## 目录

1. [JSON 参数 — 枪械 (BasicRangedWeapon)](#1-json-参数--枪械-basicrangedweapon)
2. [JSON 参数 — 弹药 (BasicAmmo)](#2-json-参数--弹药-basicammo)
3. [JSON 参数 — 击中包 (DamagePacket / onAttack)](#3-json-参数--击中包-damagepacket--onattack)
4. [JSON 参数 — 基类 (BasicItem)](#4-json-参数--基类-basicitem)
5. [NBT 参数 — 顶层结构 (ItemLoader)](#5-nbt-参数--顶层结构-itemloader)
6. [NBT 参数 — 枪械 (BasicRangedWeapon)](#6-nbt-参数--枪械-basicrangedweapon)
7. [NBT 参数 — 弹药 (BasicAmmo)](#7-nbt-参数--弹药-basicammo)
8. [NBT 参数 — 射击属性 (RangeAttackPacket / FireArmAttribute)](#8-nbt-参数--射击属性-rangeattackpacket--firearmattribute)
9. [NBT 参数 — 击中包 (DamagePacket / AttackPacket)](#9-nbt-参数--击中包-damagepacket--attackpacket)
10. [NBT 参数 — 冷兵器 (BasicItemSword)](#10-nbt-参数--冷兵器-basicitemsword)
11. [字符串枚举值一览](#11-字符串枚举值一览)
12. [快速参考卡片](#12-快速参考卡片)

---

## 1. JSON 参数 — 枪械 (BasicRangedWeapon)

**源码位置**: `BasicRangedWeapon.java` 第 80–103 行（`(int, JsonHelper, String)` 构造函数）

| 参数键 | 取值方法 | 类型 | 默认值 | 说明 |
|--------|---------|------|--------|------|
| `name` | `getString` | String | **必填** | 内部注册名（继承自 BasicItem） |
| `icon` | `getString` | String | **必填** | 图标纹理名（继承自 BasicItem） |
| `model` | `getString` | String | `null` | 自定义模型名（继承自 BasicItem） |
| `description` | `getString` | String | `""` | 物品描述（继承自 BasicItem） |
| `default_nbt` | `getSubJson` | JsonHelper | `null` | 默认 NBT 标签（继承自 BasicItem） |
| `duration` | `getInt` | int | **必填** | 耐久度 |
| `charge_stage` | `getInt` | int | `0` | 蓄力阶段数 |
| `charge_time` | `getInt` | int | `40` | 蓄力时间（tick，20 tick = 1 秒） |
| `basic_damage` | `getFloat` | float | `4.0` | 基础伤害 |
| `knockback` | `getFloat` | float | `0.0` | 击退强度 |
| `icons` | `getList` | List\<String\> | `null` | 各蓄力阶段的图标名列表 |
| `shoot_mode` | `getString` | String | `"semi"` | 射击模式 → [详见 §11](#111-shoot_mode) |
| `allowed_ammo` | `getList` | List\<String\> | `null` | 允许使用的弹药内部名列表 |
| `shoot_number` | `getInt` | int | `1` | 每次射击发射弹丸数（霰弹） |
| `unbreakable` | `getBoolean` | boolean | `false` | 无限耐久 |
| `shoot_delay` | `getInt` | int | `0` | 射击间隔（tick） |
| `accuracy` | `getFloat` | float | `1.0` | 精度（0.0 = 最差，1.0 = 完美） |
| `shoot_sound` | `getString` | String | `"bow"` | 射击音效 → [详见 §11](#114-shoot_sound) |
| `onAttack` | `getSubJson` | DamagePacket | `null` | 击中效果包 → [详见 §3](#3-json-参数--击中包-damagepacket--onattack) |

> ⚠️ **Double 陷阱**: 所有数字必须用 Double 类型（`100.0D`），否则 `JsonHelper.getInt()` 会 `ClassCastException`。

---

## 2. JSON 参数 — 弹药 (BasicAmmo)

**源码位置**: `BasicAmmo.java` 第 51–75 行（`(int, JsonHelper, String)` 构造函数）

| 参数键 | 取值方法 | 类型 | 默认值 | 说明 |
|--------|---------|------|--------|------|
| `name` | `getString` | String | **必填** | 内部注册名（也是 `ItemLoader.ammoItem` 的 key） |
| `duration` | `getInt` | int | `1` | 单格弹药容量（不是耐久度！弹匣容量） |
| `basic_damage` | `getFloat` | float | `0.0` | 附加伤害（叠加到武器基础伤害上） |
| `entity_num` | `getInt` | int | `1` | 弹丸数（会**覆盖**武器的 `shoot_number`） |
| `entity_speed` | `getFloat` | float | `2.0` | 弹丸速度 |
| `gravity` | `getBoolean` | boolean | `true` | 弹丸受重力影响 |
| `fly_fx` | `getString` | String | `null` | 飞行粒子特效 → [详见 §11](#112-fly_fx--hit_fx) |
| `hit_fx` | `getString` | String | `null` | 击中粒子特效 → [详见 §11](#112-fly_fx--hit_fx) |
| `entity_model` | `getString` | String | `"arrow"` | 弹丸渲染模型 → [详见 §11](#113-entity_model) |
| `shoot_mode` | `getString` | String | `null` | 覆盖武器射击模式（`null` = 使用武器的值） |
| `accuracy` | `getFloat` | float | `1.0` | 覆盖武器精度 |
| `shoot_delay` | `getInt` | int | `-1` | 覆盖武器射击间隔（`-1` = 使用武器的值） |
| `knockback` | `getFloat` | float | `0.0` | 覆盖武器击退 |
| `charge_time` | `getInt` | int | `-1` | 覆盖武器蓄力时间（`-1` = 使用武器的值） |
| `onAttack` | `getSubJson` | DamagePacket | `null` | 覆盖武器的击中效果包 |
| `shoot_sound` | `getString` | String | `null` | 覆盖武器射击音效（`null` = 使用武器的值） |

> 💡 **弹药覆盖机制**: 弹药参数中的非 null / 非 -1 值会覆盖武器同名参数。这使得同一把枪装不同弹药时可以有不同的射速、精度、伤害等。

---

## 3. JSON 参数 — 击中包 (DamagePacket / onAttack)

**源码位置**: `DamagePacket.java` 第 38–87 行（`(JsonHelper)` 构造函数）

| 参数键 | 取值方法 | 类型 | 默认值 | 说明 |
|--------|---------|------|--------|------|
| `fluctuating` | `getFloat` | float | `0.0` | 伤害浮动（正态分布的**方差** σ²。`4.0` ≈ σ=2.0） |
| `bomb_amount` | `getInt` | int | `0` | 延时炸弹数量 |
| ┣ `bomb` | `getSubJson` | — | — | **炸弹子对象**（每个炸弹）: |
| ┃ `bomb.damage` | `getFloat` | float | — | 炸弹伤害 |
| ┃ `bomb.delay` | `getInt` | int | — | 炸弹延时（tick） |
| ┃ `bomb.sound` | `getString` | String | `null` | 炸弹爆炸音效 |
| `effect_wounded` | `getList` | List | `null` | 施加给**受害者**的药水效果（每 3 个 int 一组: `[id, duration, amplifier]`） |
| `p_effect_wounded` | `getList` | List | `null` | 对应上述效果对受害者生效的概率 |
| `effect_attacker` | `getList` | List | `null` | 施加给**攻击者**的药水效果（格式同上） |
| `p_effect_attacker` | `getList` | List | `null` | 对应上述效果对攻击者生效的概率 |

### effect 列表格式示例

```json
"effect_wounded": [2, 100, 1,  9, 60, 0],
"p_effect_wounded": [1.0, 0.3]
```

> 第 1 组: 药水 ID=2（缓慢）持续 100 tick，等级 II → 100% 概率触发
> 第 2 组: 药水 ID=9（反胃）持续 60 tick，等级 I → 30% 概率触发

---

## 4. JSON 参数 — 基类 (BasicItem)

**源码位置**: `BasicItem.java` 第 36–44 行（`(int, JsonHelper, String)` 构造函数）

| 参数键 | 取值方法 | 类型 | 默认值 | 说明 |
|--------|---------|------|--------|------|
| `name` | `getString` | String | **必填** | 内部注册名 |
| `icon` | `getString` | String | **必填** | 纹理图标名（路径: `assets/adventurer_engine/textures/items/<icon>.png`） |
| `model` | `getString` | String | `null` | 自定义模型名 |
| `description` | `getString` | String | `""` | 物品描述文本 |
| `default_nbt` | `getSubJson` | JsonHelper | `null` | 物品创建时写入的默认 NBT 数据 |

---

## 5. NBT 参数 — 顶层结构 (ItemLoader)

**源码位置**: `ItemLoader.java` 第 147–208 行（`loadNBT()` 方法）

### test.dat 顶层 NBT 结构

```
root (NBTTagCompound)
├── "AttackPacket"    → NBTTagList<NBTTagCompound>  → DamagePacket 列表
├── "FireArmAttribute" → NBTTagList<NBTTagCompound>  → RangeAttackPacket 列表
├── "Item"            → NBTTagList<NBTTagCompound>  → 基础物品列表
├── "AMMO"            → NBTTagList<NBTTagCompound>  → 弹药物品列表
├── "ColdWeapon"      → NBTTagList<NBTTagCompound>  → 冷兵器列表
└── "Gun"             → NBTTagList<NBTTagCompound>  → 枪械列表
```

### JSON 加载器顶层键（`RCS/items/*.json`）

| 键 | 说明 |
|----|------|
| `items` | 物品数据对象列表 |
| `module` | 所属模块/标签页名称 |
| `type` | 物品类型: `"normal"` / `"sword"` / `"ranged_weapon"` / `"ammo"` |

---

## 6. NBT 参数 — 枪械 (BasicRangedWeapon)

**源码位置**: `BasicRangedWeapon.java` 第 106–151 行（`(NBTTagCompound)` 构造函数）

| NBT 键 | NBT 方法 | 类型 | 说明 |
|--------|---------|------|------|
| `theItem` | `nbt.l()` | NBTTagCompound | 父类 BasicItem 数据（嵌套复合标签） |
| `duration` | `nbt.e()` | int | 耐久度 |
| `unbreakable` | `nbt.d()` | byte | 不可破坏（0/1） |
| `charge_stage` | `nbt.d()` | int | 蓄力阶段数 |
| `shoot_number` | `nbt.e()` | int | 每次射击弹丸数 |
| `allowed_ammo` | `nbt.m()` | NBTTagList | 允许的弹药名列表（String tag） |
| `charge_icons` | `nbt.m()` | NBTTagList | 各蓄力阶段的图标名列表 |
| `fireArmAttr` | `nbt.e()` | int | 引用 `FireArmAttribute.attr_id`，用于查找 RangeAttackPacket |

> `fireArmAttr` 是 NBT 路径的核心机制：枪械本身不存储射击参数（精度、伤害、速度等），只存一个 ID 引用，所有射击参数都在 `FireArmAttribute` 列表中。

### theItem 子标签

| NBT 键 | NBT 方法 | 说明 |
|--------|---------|------|
| `item_ID` | `nbt.e()` | 物品 ID |
| `item_group` | `nbt.e()` | 标签页组索引 |
| `item_internalName` | `nbt.i()` | 内部名称 |
| `item_icon` | `nbt.i()` | 图标纹理 |
| `item_maxstack` | `nbt.e()` | 最大堆叠数 |
| `item_description` | `nbt.i()` | 描述文本 |
| `item_model` | `nbt.i()` | 模型名 |
| `item_defaultNBT` | `nbt.l()` | 默认 NBT（复合标签） |

---

## 7. NBT 参数 — 弹药 (BasicAmmo)

**源码位置**: `BasicAmmo.java` 第 78–104 行（`(NBTTagCompound)` 构造函数）

| NBT 键 | NBT 方法 | 类型 | 说明 |
|--------|---------|------|------|
| `theItem` | `nbt.l()` | NBTTagCompound | 父类 BasicItem 数据（同上表） |
| `unbreakable` | `nbt.d()` | byte | 不可破坏（0/1） |
| `bullet_num` | `nbt.e()` | int | 弹丸数 |
| `duration` | `nbt.e()` | int | 弹匣容量（对应 JSON 的 `duration`） |
| `entity_model` | `nbt.i()` | String | 弹丸模型: `"arrow"` / `"bullet"` / `"item_XXXXX"` |
| `fly_fx` | `nbt.i()` | String | 飞行粒子特效名 |
| `hit_fx` | `nbt.i()` | String | 击中粒子特效名 |
| `fireArmAttr` | `nbt.e()` | int | 引用 RangeAttackPacket ID |

---

## 8. NBT 参数 — 射击属性 (RangeAttackPacket / FireArmAttribute)

**源码位置**: `RangeAttackPacket.java` 第 31–46 行（`(NBTTagCompound)` 构造函数）

| NBT 键 | NBT 方法 | 类型 | 说明 |
|--------|---------|------|------|
| `attr_id` | `nbt.e()` | int | **包索引键**，枪械/弹药通过 `fireArmAttr` 引用此 ID |
| `attack_packet` | `nbt.e()` | int | 引用 DamagePacket 的 `packet_id` |
| `shoot_mode` | `nbt.d()` | int | 射击模式: `0` = SEMI（半自动），`1` = AUTO（全自动） |
| `shoot_delay` | `nbt.e()` | int | 射击间隔（tick） |
| `charge_time` | `nbt.e()` | int | 蓄力时间（tick） |
| `accuracy` | `nbt.h()` | double | 精度（0.0–1.0） |
| `basic_damage` | `nbt.h()` | double | 基础伤害 |
| `ice_damage` | `nbt.h()` | double | 冰霜伤害（附加） |
| `knockback` | `nbt.h()` | double | 击退强度 |
| `max_range` | `nbt.h()` | double | 最大射程（方块） |
| `max_speed` | `nbt.h()` | double | 最大弹丸速度 |
| `gravity` | `nbt.d()` | byte | 弹丸重力（0/1） |
| `penetrate` | `nbt.d()` | byte | 穿透（0/1，穿怪不消失） |
| `noeff` | `nbt.d()` | byte | 无击退（0/1） |
| `shoot_sound` | `nbt.i()` | String | 射击音效名 |

> 📌 **NBT 路径参数比 JSON 路径多**: `ice_damage`、`max_range`、`max_speed`、`penetrate`、`noeff` 在 JSON 路径中不可用（JSON 路径的 `BasicRangedWeapon` 构造函数未读取这些字段）。

---

## 9. NBT 参数 — 击中包 (DamagePacket / AttackPacket)

**源码位置**: `DamagePacket.java` 第 90–129 行（`(NBTTagCompound)` 构造函数）

| NBT 键 | NBT 方法 | 类型 | 说明 |
|--------|---------|------|------|
| `packet_id` | `nbt.e()` | int | **包索引键**，被 `attack_packet` 引用 |
| `fluctuating` | `nbt.h()` | double | 伤害浮动（方差 σ²） |
| `bomb_damage` | `nbt.h()` | double | 炸弹伤害 |
| `bomb_delay` | `nbt.e()` | int | 炸弹延时（tick） |
| `bomb_sound` | `nbt.i()` | String | 炸弹音效 |
| `bomb_amount` | `nbt.e()` | int | 炸弹数量 |
| `effect_wounded` | `nbt.k()` | int[] | 受害者药水效果（3 个 int 一组） |
| `p_effect_wounded` | `nbt.m()` | NBTTagList | 受害者效果概率 |
| `effect_attacker` | `nbt.k()` | int[] | 攻击者药水效果 |
| `p_effect_attacker` | `nbt.m()` | NBTTagList | 攻击者效果概率 |

> ⚠️ NBT 与 JSON 的 bomb 结构不同：NBT 用扁平键 `bomb_damage` / `bomb_delay` / `bomb_sound`，而 JSON 用嵌套子对象 `bomb: { damage, delay, sound }`。

---

## 10. NBT 参数 — 冷兵器 (BasicItemSword)

**源码位置**: `BasicItemSword.java` 第 47–53 行

| NBT 键 | NBT 方法 | 类型 | 说明 |
|--------|---------|------|------|
| `theItem` | `nbt.l()` | NBTTagCompound | 父类数据 |
| `duration` | `nbt.e()` | int | 耐久度 |
| `unbreakable` | `nbt.d()` | byte | 不可破坏（0/1） |
| `basic_damage` | `nbt.h()` | double | 基础伤害 |
| `ice_damage` | `nbt.h()` | double | 冰霜伤害 |
| `attack_packet` | `nbt.e()` | int | 引用 DamagePacket ID |

---

## 11. 字符串枚举值一览

### 11.1 `shoot_mode`

| 值 | 枚举 | 行为 |
|----|------|------|
| `"semi"` | `ShootMode.SEMI` | 半自动：每次右键发射一发，松开右键才能再发 |
| `"auto"` | `ShootMode.AUTO` | 全自动：按住右键持续射击（受 `shoot_delay` 限制） |

NBT 中为数字: `0` = SEMI, `1` = AUTO

---

### 11.2 `fly_fx` / `hit_fx`

粒子特效名称字符串。直接传给 Minecraft 的粒子系统，支持原版粒子名。

| 常见值（推测） | 说明 |
|--------------|------|
| `"crit"` | 暴击粒子 |
| `"flame"` | 火焰粒子 |
| `"smoke"` | 烟雾粒子 |
| `"portal"` | 传送门粒子 |
| `"magicCrit"` | 魔法暴击 |
| `null` / `""` | 无特效 |

> 🔍 作者在 test.dat NBT 中的具体取值未在源码中硬编码，由 NBT 数据决定。这些是 Minecraft 1.6.4 原版粒子名。

---

### 11.3 `entity_model`

决定弹丸使用哪种渲染器。

| 值 | 渲染器 | 说明 |
|----|--------|------|
| `"arrow"` | `ArrowLikeRender` | **默认值**。Tessellator 手绘的 6 面体箭矢，不需要模型文件 |
| `"bullet"` | `BulletRender` | 3D Techne 模型渲染，模型内置于 `adventurer_engine` 中 |
| `"item_XXXXX"` | `FlyItemRender` | **物品图标平替**。`XXXXX` 为物品 ID 数字。将物品的 2D 图标作为 billboard 渲染 |
| 其他任意字符串 | `ArrowLikeRender`（回退） | 默认回退到手绘箭头 |

**`"item_XXXXX"` 解析逻辑**（`BasicRangedWeapon.java` 第 314 行）:

```java
if ("bullet".equals(entityModel)) {
    // 使用 BulletRender（3D 模型）
    arr = new EntityBulletModelBullet(...);
} else if (entityModel.startsWith("item_")) {
    // 解析 item_ 前缀后的数字作为物品 ID
    int id = Integer.parseInt(entityModel.substring(5));
    arr = new EntityBulletModelItemDrop(..., id);
} else {
    // 默认：ArrowLikeRender（手绘 6 面体）
    arr = new EntityBulletGeneral(...);
}
```

---

### 11.4 `shoot_sound`

射击音效名称。引擎会调用 `AdventurerEngine.proxy.addSound(name)` 预注册音效。

| 常见值 | 说明 |
|--------|------|
| `"bow"` | **默认值**，映射到原版 `random.bow` 音效 |
| `"random.bow"` | 原版弓音效 |
| 其他字符串 | 自定义音效名（取决于资源包中注册的音效） |

> 音效播放逻辑在 `PlaySoundPacket.java` 第 71 行：若 `sound.equals("bow")`，则播放 `"random.bow"`。

---

### 11.5 `type`（JSON 加载器）

| 值 | 对应类 | 说明 |
|----|--------|------|
| `"normal"` | `BasicItem` | 普通物品 |
| `"sword"` | `BasicItemSword` | 冷兵器 |
| `"ranged_weapon"` | `BasicRangedWeapon` | 枪械 |
| `"ammo"` | `BasicAmmo` | 弹药 |

---

### 11.6 NBT 内部常量

| 常量 | 值 | 位置 | 说明 |
|------|-----|------|------|
| `CURRENT_AMMO` | `"current_ammo"` | `BasicRangedWeapon.java:56` | 物品 NBT 中存储当前选中弹药名的 key |

---

## 12. 快速参考卡片

### 枪械 JSON 最小示例

```json
{
  "name": "my_gun",
  "icon": "my_gun_icon",
  "duration": 100,
  "basic_damage": 8.0,
  "shoot_mode": "semi",
  "shoot_delay": 12,
  "accuracy": 0.9,
  "allowed_ammo": ["my_ammo", "arrow"],
  "shoot_sound": "bow"
}
```

### 弹药 JSON 最小示例

```json
{
  "name": "my_ammo",
  "icon": "my_ammo_icon",
  "duration": 30,
  "entity_model": "bullet",
  "entity_speed": 3.0,
  "gravity": true
}
```

### DamagePacket JSON 最小示例

```json
{
  "fluctuating": 2.0,
  "bomb_amount": 1,
  "bomb": {
    "damage": 5.0,
    "delay": 20,
    "sound": "random.explode"
  },
  "effect_wounded": [2, 100, 1],
  "p_effect_wounded": [1.0]
}
```

### 参数覆盖优先级

```
弹药 JSON 参数  >  枪械 JSON 参数  >  引擎默认值
   (非null/-1)      (构造函数)        (硬编码)
```

### 渲染器选择逻辑

```
entity_model = "arrow"   → ArrowLikeRender（手绘 6 面体）
entity_model = "bullet"  → BulletRender（3D Techne 模型）
entity_model = "item_N"  → FlyItemRender（物品图标 billboard，N = 物品 ID）
其他 / 未指定             → ArrowLikeRender（回退默认）
```

### NBT 引用链

```
Gun.fireArmAttr ──→ FireArmAttribute.attr_id
                        │
                        ├── shoot_mode, accuracy, basic_damage, knockback,
                        │   max_range, max_speed, gravity, penetrate, noeff,
                        │   shoot_delay, charge_time, ice_damage, shoot_sound
                        │
                        └── attack_packet ──→ AttackPacket.packet_id
                                                  │
                                                  ├── fluctuating
                                                  ├── bomb_damage, bomb_delay, bomb_sound, bomb_amount
                                                  └── effect_wounded, p_effect_wounded,
                                                      effect_attacker, p_effect_attacker
```

---

> 📄 本文档所有参数均提取自 `adventurer_engine.jar` 反编译源码，覆盖 JSON（addon 开发）和 NBT（引擎内部）两条加载路径。
