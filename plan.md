# 雪原探索者狙击枪 — 开发日志

> **最后更新**: 2025-08-01 15:45 — 充能系统改用 ITickHandler

---

## 开发者指南：如何编写新枪械 / 弹药 / 弹幕

> 如果你要新加一把枪或一种弹药，按下面模板复制改参数即可。已踩过的坑都内化到模板中了。

### 1. 弹药类（必须用 NBT 路径，不要用 JSON）

JSON 构造器有 `ice_damage` 硬编码为 0 的引擎 bug，所以弹药一律走 NBT。

```java
// 模板：放在 atwandaddon.weapon.ammo 包下
public class YourAmmo extends BasicAmmo {
    public static final String AMMO_NAME = "your_ammo_name";

    public YourAmmo(int _id, int _renderItemId) {
        super(AmmoNbtBuilder.create(_id, AMMO_NAME)
            .icon("your_ammo_icon")          // 背包图标 png 名
            .maxStack(64)
            .basicDamage(8.0)                // 普通伤害
            .iceDamage(8.0)                  // 冰穿甲伤害（仅 NBT 有效）
            .entityModel("item_" + _renderItemId)  // 弹幕图标 = Item.itemsList 索引
            .bulletNum(1)
            .ammoCapacity(1)
            .gravity(false)                  // 狙击无重力
            .shootMode(ShootMode.SEMI)
            .shootDelay(1)
            .chargeTime(2)
            .accuracy(0.95)
            .knockback(0.5)
            .shootSound("bow_zx")            // 音效名（不加域名前缀）
            .maxRange(80.0)
            .maxSpeed(6.0)
            .penetrate(true)
            .noeff(false)
            .build());
    }
}
```

注册：在 `ATcraftAddon.java` 的 `preInit()` 中：
```java
yourAmmo = new YourAmmo(2500x, leadBulletMod.itemID);
GameRegistry.registerItem(yourAmmo, "yourAmmo");
// 不需要手动 registerAsAmmo() — NBT 构造器自动注册
```

### 2. 枪械类

枪械只需覆写 `onPlayerStoppedUsing` 来创建自定义弹丸。`this.accuracy` 从枪械 NBT/JSON 配置自动读取，传给弹丸构造器控制散布。

```java
// 模板：放在 atwandaddon.weapon.gun 包下
public class YourGun extends BasicRangedWeapon {
    // buildJson() 或 GunNbtBuilder → 参考 SnowfieldExplorer

    @Override
    public void onPlayerStoppedUsing(ItemStack _item, World _world, EntityPlayer _player, int _count) {
        // ... 弹药检查、蓄力检查、耐久消耗 ...
        // 创建弹丸，this.accuracy 从枪械配置自动读取
        YourBullet bullet = new YourBullet(_world, _player, ammo.entity_speed, this.accuracy);
        bullet.setDamage(basicDamage);
        // ... 生成弹丸、消耗弹药、播放音效 ...
    }
}
```

### 3. 自定义弹幕实体（仅需要零散布时）

如果需要零散布弹道（狙击枪），按三个步骤：

**Step 1** — 创建实体类继承 `EntityBulletModelItem`：
```java
public class YourBullet extends EntityBulletModelItem {
    // 必须有无参 World 构造器（Forge 反射创建客户端实体用）
    public YourBullet(World _world) { super(_world); }

    // 射击构造器 — _accuracy 控制散布
    //   accuracy = 1.0 → 零散布（狙击枪）
    //   accuracy = 0.9 → 小散布（步枪）
    //   accuracy = 0.5 → 大散布（霰弹枪）
    public YourBullet(World _world, EntityLivingBase _shooter, float _speed, float _accuracy) {
        super(_world, _shooter, _speed, 1.0F);

        // 方向计算（含散布偏移）
        float yawRad = ((Entity)_shooter).rotationYaw / 180.0F * (float)Math.PI;
        float pitchRad = ((Entity)_shooter).rotationPitch / 180.0F * (float)Math.PI;

        // 散布：MAX_SPREAD = 15°，accuracy 越低散布越大
        float MAX_SPREAD = 15.0F;
        float maxOffset = (1.0F - _accuracy) * MAX_SPREAD;
        float maxOffsetRad = maxOffset / 180.0F * (float)Math.PI;
        float yawOffset = (((Entity)_shooter).rand.nextFloat() * 2 - 1) * maxOffsetRad;
        float pitchOffset = (((Entity)_shooter).rand.nextFloat() * 2 - 1) * maxOffsetRad;

        float dirX = -MathHelper.sin(yawRad + yawOffset) * MathHelper.cos(pitchRad + pitchOffset);
        float dirY = -MathHelper.sin(pitchRad + pitchOffset);
        float dirZ = MathHelper.cos(yawRad + yawOffset) * MathHelper.cos(pitchRad + pitchOffset);

        float len = MathHelper.sqrt_float(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float speed = 3.0F;
        // ⚠️ motion 赋值必须通过 Entity 类型中转
        Entity e = this;
        e.motionX = dirX / len * speed;
        e.motionY = dirY / len * speed;
        e.motionZ = dirZ / len * speed;
    }
}

    // ⚠️ 必须覆写 setRenderItemID 立即同步 DataWatcher
    @Override
    public void setRenderItemID(int _id) {
        this.renderItemID = _id;
        this.dataWatcher.updateObject(16, Integer.valueOf(_id));
    }
}
```

**Step 2** — 在 `ClientProxy` 注册渲染器：
```java
RenderingRegistry.registerEntityRenderingHandler(
    YourBullet.class, new FlyItemRender());
```

**Step 3** — 在 `ATcraftAddon.preInit()` 注册实体：
```java
EntityRegistry.registerModEntity(YourBullet.class, "YourBullet",
    250, instance, 64, 1, true);
```

### 4. 必须检查清单

| # | 检查项 | 忘了会怎样 |
|---|--------|-----------|
| 1 | `@NetworkMod` 注解在 `ATcraftAddon` 上 | 弹幕客户端不创建（NPE） |
| 2 | `ATcraftPacketHandler` 存根类存在 | 同上 |
| 3 | 弹幕实体有无参 `(World)` 构造器 | 客户端反射失败 |
| 4 | `setRenderItemID` 覆写调用 `dataWatcher.updateObject` | 弹幕图标不显示 |
| 5 | motion 赋值用 `Entity e = this;` 中转 | SRG 映射错误 |
| 6 | 纹理放 `assets/adventurer_engine/textures/items/` | 紫黑块 |
| 7 | NBT 构造器的数值用 Double（`8.0` 不是 `8`） | ClassCastException |
| 8 | 编译后运行 SpecialSource 重映射 | NoSuchFieldError |
| 9 | 部署时文件名匹配 mods 目录已有文件 | 加载旧 MCP jar |
| 10 | 音效用 `"adventurer_engine:" + sound` | 无声音 |

---

## 踩坑记录

> 按发现时间排序，编号代表踩坑顺序。

### 坑 #1: 弹幕图标不显示 — DataWatcher 同步时序

**日期**: 2025-08-01  
**现象**: 弹道精准但弹幕完全不可见（没有粒子、没有图标）。

**诊断过程**:
1. 确认 `renderItemID = 26256` 在服务端正确设置（`[SniperDebug] Server bullet created ... setRenderItemID done: field=26256`）
2. 发现客户端日志中 **没有** `"Client entity created"`（弹幕实体未在客户端创建）— 见坑 #2
3. 修复坑 #2 后客户端实体创建成功，但 `renderItemID=0`
4. 查 `FlyItemRender.java` 第 26-27 行：`int i = bullet.renderItemID; if (i == 0) return;`
5. 查 `EntityBulletModelItem.onUpdate()`：服务端每 tick 写 DW[16]=renderItemID，客户端从 DW[16] 读到字段。但 **spawn 包在第一个 tick 前就发送了**！

**根因**: `setRenderItemID()`（父类实现）只改 `this.renderItemID` 字段，不更新 DataWatcher。spawn 包中的 DW[16] 是构造时的默认值 0。客户端收到 spawn 包后立即尝试渲染，此时 `renderItemID=0` → `FlyItemRender` 直接 return。

**关键代码链**:
```
SnowfieldExplorer.onPlayerStoppedUsing
  → bullet.setRenderItemID(26256)     // 只改字段，DW 还是 0
  → world.spawnEntityInWorld(bullet)  // spawn 包: DW[16]=0
  → 客户端收到包: DW[16]=0
  → EntityBulletModelItem.onUpdate: if (renderItemID==0) renderItemID=DW[16]  // 还是 0
  → FlyItemRender: if (i==0) return;  // 不渲染！
```

**修复**: 在 `EntitySniperBullet` 中覆写 `setRenderItemID()`，额外调用 `dataWatcher.updateObject(16, id)` 在 spawn 前立即同步 DW：

```java
@Override
public void setRenderItemID(int _id) {
    this.renderItemID = _id;
    this.dataWatcher.updateObject(16, Integer.valueOf(_id));  // 立即同步 DW
}
```

**SRG 映射**: `FD: nn/ah atwandaddon/entity/EntitySniperBullet/dataWatcher`

---

### 坑 #2: 客户端实体未创建 — @NetworkMod 缺失

**日期**: 2025-08-01  
**现象**: `EntitySpawnPacket.generatePacket` 第 75 行 NPE，日志中无 `"Client entity created"`，弹幕图标不显示。

**日志特征**:
```
[STDERR] "Silently" catching entity tracking error.
Caused by: java.lang.NullPointerException
    at cpw.mods.fml.common.network.EntitySpawnPacket.generatePacket(EntitySpawnPacket.java:75)
```

**根因**: `@Mod` 缺少 `@NetworkMod` 注解。Forge 1.6.4 中，没有 `@NetworkMod` 的 mod 无 `NetworkModHandler` → `FMLNetworkHandler.findNetworkModHandler(er.getContainer())` 返回 null → `generatePacket` 第 75 行 `handler.getNetworkId()` NPE。

**对比引擎**: `adventurer_engine` 有完整的 `@NetworkMod(clientSideRequired=true, serverSideRequired=true, channels={"adv_channel"}, packetHandler=ADEPacketHandler.class)`

**修复**: 添加 `@NetworkMod` + 存根 `IPacketHandler`：

```java
// ATcraftAddon.java
@Mod(modid = ATcraftAddon.MODID, name = ATcraftAddon.NAME, version = ATcraftAddon.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
    channels = {"ATcraftAddon"}, packetHandler = ATcraftPacketHandler.class)
public class ATcraftAddon { ... }

// ATcraftPacketHandler.java — 存根，不处理任何实际包
public class ATcraftPacketHandler implements IPacketHandler {
    public void onPacketData(INetworkManager _m, Packet250CustomPayload _p, Player _pl) {}
}
```

---

### 坑 #3: SRG 字段映射 — NoSuchFieldError: Q

**日期**: 2025-08-01  
**现象**: 在 `EntitySniperBullet` 中添加 `onUpdate()` 和 `setDead()` 覆写后，射击瞬间闪退，报 `NoSuchFieldError: Q`

**根因**: `Q` = `ticksExisted` 的 SRG 名。`this.ticksExisted` 在子类中直接访问时，字节码 fieldref owner = `EntitySniperBullet`。`mcp2srg.srg` 中 `ticksExisted → Q` 的 FD 只映射了 `nn/Q → Entity/ticksExisted`，SpecialSource 不会跨类继承查找。

**修复**: 
- 避免在自定义 Entity 子类中直接访问 Minecraft Entity 的继承字段
- 如需访问，通过 `Entity e = this;` 强转后使用 `e.ticksExisted`，使 fieldref owner 变为 `nn`
- 本次直接移除了有问题的覆写（仅用于调试，非必需）

---

### 坑 #4: 自定义弹幕实体中 motion 赋值

**日期**: 2025-08-01  
**现象**: SRG 重映射后 `motionX/Y/Z` 字段引用可能不正确

**根因**: `this.motionX = value` 的 fieldref owner 是当前子类。mcp2srg.srg 中 `motionX → x` 的 FD 只映射了 `nn.x → Entity/motionX`。

**修复**: 通过 `Entity thisEntity = this;` 强转后赋值：

```java
net.minecraft.entity.Entity thisEntity = this;
thisEntity.motionX = dirX / len * effectiveSpeed;
thisEntity.motionY = dirY / len * effectiveSpeed;
thisEntity.motionZ = dirZ / len * effectiveSpeed;
```

同样，`rotationYaw` / `rotationPitch` 也需要通过 `(Entity)_shooter` 强转访问。

---

### 坑 #5: 音效域名不匹配

**日期**: 2025-08-01  
**现象**: 射击无音效

**根因**: 引擎 `SoundManager.addSound("bow_zx")` 会加前缀 → 注册为 `"adventurer_engine:bow_zx"`。`PlaySoundPacket.execute()` 客户端侧也会加 `"adventurer_engine:"` 前缀。但我们用 `world.playSoundAtEntity(_player, "bow_zx", ...)` 直接传了裸名 → 查找失败。

**修复**: 传入完整音效名：
```java
_world.playSoundAtEntity(_player, "adventurer_engine:" + sound, 1.0F, 1.0F);
```

---

### 坑 #6: JSON 构造函数 ice_damage 硬编码为 0

**日期**: 2025-08-01 (早期发现)  
**现象**: JSON 路径的 `ice_damage`、`penetrate`、`gravity` 等参数实际不生效

**根因**: 引擎 `BasicAmmo(int, JsonHelper, String)` 构造器第 69-73 行硬编码：
```java
this.ice_damage = 0.0f;
this.unbreakable = false;
this.penetrate = false;
this.noeff = false;
this.maxRange = 30.0f;
```

**修复**: 使用 NBT 构造函数（`AmmoNbtBuilder`），经 `RangeAttackPacket` 从 NBT 读取全部参数，完全绕过 JSON 构造器的硬编码。

---

### 坑 #7: Entity.rand 的 SRG 映射 — NoSuchFieldError: R

**日期**: 2025-08-01  
**现象**: 在 `EntitySniperBullet` 中使用 `this.rand.nextFloat()` 后射击闪退，报 `NoSuchFieldError: R`

**根因**: `this.rand` 在子类字节码中 fieldref owner = `EntitySniperBullet`。加入 `FD: nn/R atwandaddon/entity/EntitySniperBullet/rand` 后，SpecialSource 正确将 `rand` 重映射为 `R`。但运行时 Forge 的 `FMLDeobfuscatingRemapper` 在 SRG→Obf 阶段无法跨类继承解析 `EntitySniperBullet/R` → `Entity/R` → obfuscated 名。

**对比 `dataWatcher`（`ah`）为什么能工作**: 待查证。可能是 Forge 运行时对某些字段有特殊处理，或 `packaged.srg` 中某些字段的继承解析路径不同。

**修复**: 避免使用任何 Minecraft Entity 的实例字段。用 `Math.random()` 替代 `this.rand.nextFloat()`——无字段访问，零 SRG 风险：

```java
// ❌ 崩溃 — Entity.rand 的 fieldref owner = EntitySniperBullet
float offset = (this.rand.nextFloat() * 2.0F - 1.0F) * maxOffsetRad;

// ✅ 安全 — Math.random() 是静态方法，不涉及 Minecraft 字段
float offset = ((float)Math.random() * 2.0F - 1.0F) * maxOffsetRad;
```

**经验法则**: 在自定义 Entity 子类中，能用 JDK 标准库解决的问题不要碰 Minecraft Entity 的实例字段。

---

### 坑 #8: Item.onUpdate 在 MC 1.6.4 服务端不被调用

**日期**: 2025-08-01  
**现象**: 充能系统的 `ChargeTimer` 在 NBTExplorer 中不存在，`[ChargeDebug]` 日志一条都没有。充能效果完全失效。

**根因**: MC 1.6.4 中 `Item.onUpdate()` 仅客户端触发：
- `ItemStack.updateAnimation()` 调用 `item.onUpdate()`
- 集成服务器 (Integrated Server) 使用 `EntityPlayerMP.onUpdate()` → `inventory.decrementAnimations()` — **不触发** `updateAnimation()`
- 只有客户端 `EntityClientPlayerMP.onUpdate()` → `inventory.decrementAnimations()` → `updateAnimation()`

**修复**: 改用 Forge 1.6.4 的 `ITickHandler` + `TickRegistry` 在服务端每 tick 手动驱动充能逻辑：

```java
// ATcraftAddon.preInit() 中注册
TickRegistry.registerTickHandler(new ITickHandler() {
    @Override
    public void tickEnd(EnumSet<TickType> _type, Object... _tickData) {
        EntityPlayer player = (EntityPlayer) _tickData[1];
        if (player.worldObj.isRemote) return;

        // 遍历玩家背包，查找需要充能的枪械
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack != null && stack.getItem() instanceof SnowfieldExplorer) {
                SnowfieldExplorer.tickRecharge(stack);
            }
        }
    }

    @Override public EnumSet<TickType> ticks() { return EnumSet.of(TickType.PLAYER); }
    @Override public String getLabel() { return "ATcraftAddon:GunRecharge"; }
    @Override public void tickStart(EnumSet<TickType> _type, Object... _tickData) {}
}, Side.SERVER);
```

**充能逻辑** (`SnowfieldExplorer.tickRecharge`, `public static`):
- 每 tick 累加 NBT 的 `ChargeTimer`
- 达到 130 tick (6.5 秒) 恢复 1 耐久
- 耐久满时重置计时器

**设计要点**:
- 充能逻辑从 `Item.onUpdate` 抽出为 `public static void tickRecharge(ItemStack)` — 独立于实例生命周期，由 `ITickHandler` 驱动
- 遍历背包处理所有 `SnowfieldExplorer` 实例 — 玩家可能持有多把枪
- 仅注册 `Side.SERVER` — 客户端 tick 由引擎原逻辑处理

**经验法则**: MC 1.6.4 中不要依赖 `Item.onUpdate` 做服务端逻辑。需要服务端 tick 驱动时用 `ITickHandler` + `TickRegistry`。

---

## 功能记录

### 可配置散布系统 (2025-08-01)

**设计**: `EntitySniperBullet` 构造器新增 `_accuracy` 参数（0.0~1.0），控制弹道散布。

**散布公式**:
```
maxOffset(度) = (1.0 - accuracy) × 15.0°
yawOffset   = rand(-1, 1) × maxOffset(弧度)
pitchOffset = rand(-1, 1) × maxOffset(弧度)
最终方向 = vanilla_dir(yaw + yawOffset, pitch + pitchOffset)
```

**精度对照表**:

| accuracy | 最大散布 | 适用场景 |
|----------|---------|---------|
| 1.0 | 0° | 狙击枪（指哪打哪） |
| 0.9 | 1.5° | 精确步枪 |
| 0.8 | 3.0° | 突击步枪 |
| 0.5 | 7.5° | 霰弹枪 |
| 0.0 | 15.0° | 最大散布 |

**调用方式**: 枪械侧 `new EntitySniperBullet(_world, _player, ammo.entity_speed, this.accuracy)`，`this.accuracy` 自动从枪械 NBT/JSON 配置读取。

---

### NBT 充能核心系统 (2025-08-01)

**设计**: 枪械耐久 = 充能槽，开枪消耗耐久，自动恢复。

**架构**:
```
ATcraftAddon.preInit()
  → TickRegistry.registerTickHandler(ITickHandler, Side.SERVER)
    → 每 tick 遍历玩家背包
      → SnowfieldExplorer.tickRecharge(ItemStack)
        → NBT ChargeTimer 累加
        → 达 130 tick → 恢复 1 点耐久
```

**关键常量** (`SnowfieldExplorer`):
| 常量 | 值 | 说明 |
|------|-----|------|
| `CHARGE_TIMER` | `"ChargeTimer"` | NBT 键名 |
| `CHARGE_INTERVAL` | `130` | 充能间隔（tick），6.5 秒 |
| `MAX_CHARGE` | `9` | 最大耐久（充能槽数） |

**耐久保护**: `_item.getItemDamage() + duraCost >= MAX_CHARGE` 时禁止开枪，防止枪械销毁。

**耐久消耗**: 由弹药类 `SnowEagleAP.DURABILITY_COST` 定义（当前为 1），方便不同弹药差异化消耗。

**NBT 持久化**: `ChargeTimer` 存储在 ItemStack NBT → 跟随玩家存档自动持久化。NBTExplorer 可查看/编辑。

---

## 关键文件索引

| 文件 | 作用 |
|------|------|
| `EntitySniperBullet.java` | 零散布弹丸实体，继承 EntityBulletModelItem，覆写 setRenderItemID |
| `SnowfieldExplorer.java` | 狙击枪物品，覆写 onPlayerStoppedUsing + 充能逻辑 |
| `SnowEagleAP.java` | 穿甲弹，NBT 路径构建 + DURABILITY_COST |
| `AmmoNbtBuilder.java` | NBT 构造器，绕过 JSON 构造器 ice_damage 硬编码 bug |
| `ATcraftPacketHandler.java` | 存根 IPacketHandler，满足 @NetworkMod 要求 |
| `mcp2srg.srg` | 自定义 SRG 映射条目（dataWatcher、worldObj 等） |
| `ClientProxy.java` | 注册 FlyItemRender 渲染器 |
| `LeadBulletMod.java` | 弹幕图标物品（itemID 供 renderItemID 引用） |
| `ATcraftAddon.java` | @Mod 主类 + @NetworkMod + ITickHandler 充能驱动 + 实体/物品注册 |
