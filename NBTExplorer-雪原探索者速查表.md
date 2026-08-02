# NBTExplorer 雪原探索者速查表

> 适用对象：`snowfieldExplorer` / 雪原探索者  
> 目的：直接用 `NBTExplorer` 调整“枪魂”玩法参数，不必每次重新编译

---

## 1. 物品定位

在玩家存档、背包、箱子或地图掉落物里，找到对应 `ItemStack` 的 `tag` 节点。  
雪原探索者的核心事件标签都写在这个物品自己的 `tag` 下面。

常见结构大致如下：

```text
ItemStack
├─ id
├─ Damage
├─ Count
└─ tag
   ├─ current_ammo
   ├─ onUpdate_HealDamageTimer
   ├─ onItemRightClick_Disable
   ├─ onItemRightClick_dur
   └─ hitEntity_HealDamage
```

---

## 2. 核心键速查

| NBT Key | 类型 | 当前狙击预设 | 作用 |
|------|------|------:|------|
| `onUpdate_HealDamageTimer` | `int` | `130` | 每 `130 tick` 自动恢复 `1` 点耐久 |
| `onItemRightClick_Disable` | `int` | `5` | 当 `Damage >= 5` 时禁止右键开枪 |
| `onItemRightClick_dur` | `int` | `0` | 右键时额外耐久变化。`0` = 不额外处理 |
| `hitEntity_HealDamage` | `int` | `-1` | 近战命中时恢复 `1` 点耐久 |

---

## 3. Damage 含义

雪原探索者的“枪魂充能”本质上还是走原版 `Damage`：

| `Damage` 值 | 含义 |
|------:|------|
| `0` | 满充能 |
| `1` | 已消耗 1 点 |
| `5` | 到达锁枪阈值，不能右键开枪 |
| `9` | 接近耐久上限 |

换算关系：

```text
当前充能 = 最大耐久 9 - Damage
```

例如：

| Damage | 当前充能 |
|------:|------:|
| `0` | `9` |
| `1` | `8` |
| `5` | `4` |
| `9` | `0` |

---

## 4. 推荐改法

### 4.1 只保留自动恢复

```text
onUpdate_HealDamageTimer = 130
onItemRightClick_Disable = 5
onItemRightClick_dur = 0
hitEntity_HealDamage = 0
```

效果：
- 开枪靠引擎自己扣耐久
- 每 6.5 秒回 1 点
- 近战不回充

### 4.2 只允许近战恢复

```text
onUpdate_HealDamageTimer = 0
onItemRightClick_Disable = 5
onItemRightClick_dur = 0
hitEntity_HealDamage = -2
```

效果：
- 不自动恢复
- 枪托近战净回 1 点左右
- `-1` 往往会被引擎近战本身的耐久消耗抵消，所以通常建议 `-2`

### 4.3 快速自动恢复

```text
onUpdate_HealDamageTimer = 60
onItemRightClick_Disable = 5
onItemRightClick_dur = 0
hitEntity_HealDamage = -1
```

效果：
- 每 3 秒回 1 点
- 更接近手枪式枪魂循环

### 4.4 完全关闭枪魂恢复

```text
onUpdate_HealDamageTimer = 0
onItemRightClick_Disable = 5
onItemRightClick_dur = 0
hitEntity_HealDamage = 0
```

效果：
- 只扣不回
- 最适合排查动画问题

---

## 5. 不建议乱改的项

### `onItemRightClick_dur`

这个值会在右键入口额外改耐久。

建议：
- 狙击枪保持 `0`
- 不要随便设成 `1`、`-1` 去和引擎自身扣耐久叠加

原因：
- 引擎 `BasicRangedWeapon` 本来就会在开枪时扣耐久
- 这里再改一次，很容易出现双扣、双回，或者和动画状态互相抢

### `onItemRightClick_Disable`

这个值不是“冷却时间”，而是“锁枪阈值”。

逻辑是：

```text
Damage >= onItemRightClick_Disable
=> 禁止右键开枪
```

所以：
- 值越小，越容易锁枪
- 值越大，可连续射击次数越多

---

## 6. 修改后的注意事项

`SnowfieldExplorer` 当前会在手持时调用一次 `NbtEventTags.write(_stack, PRESET_SNIPER)`，  
但这个写入逻辑是“只在缺少这些 key 时才补写”，不会覆盖你已经手改好的值。

这意味着：

- 你可以直接用 `NBTExplorer` 改已有 key 的数值
- 游戏不会立刻把它改回默认值
- 但如果你把这些 key 整个删掉，下一次手持更新时会按预设重新补回来

---

## 7. 最稳的排错顺序

如果以后又怀疑闪烁或充能逻辑有问题，建议按这个顺序测：

1. `onUpdate_HealDamageTimer = 0`
2. `hitEntity_HealDamage = 0`
3. 保留 `onItemRightClick_Disable = 5`
4. 保留 `onItemRightClick_dur = 0`
5. 观察是否仍然闪烁

如果这样不闪，再逐步恢复自动回充或近战回充。
