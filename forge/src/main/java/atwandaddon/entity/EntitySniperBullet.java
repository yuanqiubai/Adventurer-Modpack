package atwandaddon.entity;

import com.adventurer_engine.common.entity.projectiles.EntityBulletModelItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 可配置散布的自定义弹丸 — 绕过引擎精度系统的独立弹道实体。
 *
 * <p>引擎的 {@link EntityBulletGeneral} 构造函数在设置 motion 时经过多层
 * 精度/散布处理（disturbVector + 自定义 c() 方法），导致弹道严重偏离。
 * 此类直接使用 vanilla Minecraft 方向数学计算 motion，完全绕过引擎精度系统：</p>
 * <ul>
 * <li>方向：纯 {@code -sin(yaw)*cos(pitch), -sin(pitch), cos(yaw)*cos(pitch)}</li>
 * <li>散布：由 {@code accuracy} 参数控制（1.0=零散布狙击，0.5=大散布霰弹）</li>
 * <li>速度：正态化后乘 effectiveSpeed，无 nextGaussian 噪声</li>
 * </ul>
 *
 * <p>继承 {@link EntityBulletModelItem} 以保留 FlyItemRender 2D 物品图标渲染。</p>
 *
 * @see EntityBulletModelItem 父类（renderItemID 数据同步 + FlyItemRender 支持）
 * @see SnowfieldExplorer 调用方（覆写 onPlayerStoppedUsing 方法生成此类实例）
 */
public class EntitySniperBullet extends EntityBulletModelItem
{
    // ================================================================
    //  距离缩放系统 — 命中伤害随飞行距离动态增长
    // ================================================================

    /** 是否启用距离缩放（true = 每 tick 根据飞行距离重新计算伤害） */
    private boolean useDistanceScaling = false;
    /** 未缩放的基础伤害值，在 {@link #enableDistanceScaling} 时保存 */
    private double baseDamage = 0.0D;
    /** 未缩放的基础冰穿甲伤害值 */
    private double baseIceDamage = 0.0D;
    /** 距离除数（maxRange），距离/除数 = 伤害倍率增量 */
    private double distanceDivisor = 28.0D;
    /** 射击者引用，用于计算弹丸飞行距离 */
    private EntityLivingBase savedShooter;

    /** 无参构造器 — 供 Forge EntityRegistry 反射创建实体用 */
    public EntitySniperBullet(World _world)
    {
        super(_world);
    }

    /**
     * 从射击者创建可配置散布的自定义弹丸。
     *
     * <h3>设计说明</h3>
     * <ol>
     * <li><b>射击位置</b>：由 super() 构造器自动处理（shooter.x,
     * shooter.y + eyeHeight, shooter.z，再向面朝方向后退 0.16 格）。无需额外处理。</li>
     *
     * <li><b>瞄准方向</b>：使用 {@code Entity.rotationYaw / rotationPitch}。
     * 对 EntityPlayer（本武器唯一使用者），rotationYaw 就是鼠标控制的瞄准方向。
     * 若将来需要 NPC 兼容，可改为 {@code EntityLivingBase.rotationYawHead}。</li>
     *
     * <li><b>散布计算</b>：在基础方向上叠加随机偏转。
     * {@code accuracy = 1.0} → 零散布（完全精准）；
     * {@code accuracy = 0.5} → 最大 ±7.5° 偏转。
     * 公式：{@code maxOffset = (1.0 - accuracy) × MAX_SPREAD_DEGREES}，
     * 在 yaw 和 pitch 上各加随机偏移（均匀分布）。</li>
     *
     * <li><b>弹速</b>：正态化方向向量后乘 {@code effectiveSpeed}。
     * 默认值 3.0，确保弹幕在屏幕上可见。</li>
     * </ol>
     *
     * @param _world     游戏世界实例
     * @param _shooter   射击者实体（EntityPlayer，提供瞄准 yaw/pitch）
     * @param _speed     弹丸飞行速度（已包含引擎 chargeRatio 蓄力修正，此处未使用）
     * @param _accuracy  精度（0.0~1.0，1.0=零散布，值越小散布越大）
     */
    public EntitySniperBullet(World _world, EntityLivingBase _shooter, float _speed, float _accuracy)
    {
        // accuracy=1.0F → disturVector 不执行（条件为 accuracy < 1.0）
        // super 设置 spawn 位置（含 eyeHeight + 后退偏移）和实体数据
        super(_world, _shooter, _speed, 1.0F);

        // 保存射击者引用，供 onUpdate() 距离缩放计算使用
        this.savedShooter = _shooter;

        // ── 角度转弧度 ──
        // 强转为 Entity：javac 对 _shooter.rotationYaw（声明类型 EntityLivingBase）
        // 生成的 fieldref owner 为 of（EntityLivingBase），mcp2srg.srg 中 rotationYaw
        // 的 FD 映射只存在于 nn（Entity）。强转后 fieldref owner = nn，SRG 映射正确。
        float yawRad = ((net.minecraft.entity.Entity)_shooter).rotationYaw / 180.0F * (float)Math.PI;
        float pitchRad = ((net.minecraft.entity.Entity)_shooter).rotationPitch / 180.0F * (float)Math.PI;

        // ── 散布计算 ──
        // accuracy = 1.0 → maxOffset = 0°（零散布狙击）
        // accuracy = 0.5 → maxOffset = 7.5°
        // accuracy = 0.0 → maxOffset = 15°（最大散布）
        float MAX_SPREAD_DEGREES = 15.0F;
        float maxOffset = (1.0F - _accuracy) * MAX_SPREAD_DEGREES;
        // 偏转角度转弧度
        float maxOffsetRad = maxOffset / 180.0F * (float)Math.PI;
        // 使用 Math.random() 避免 Entity.rand 的 SRG 映射问题
        // （Entity.rand → SRG "R"，Forge 运行时跨类继承解析不稳定）
        float yawOffset = ((float)Math.random() * 2.0F - 1.0F) * maxOffsetRad;
        float pitchOffset = ((float)Math.random() * 2.0F - 1.0F) * maxOffsetRad;

        // ── vanilla MC 标准方向向量（含散布偏移）──
        float dirX = -MathHelper.sin(yawRad + yawOffset) * MathHelper.cos(pitchRad + pitchOffset);
        float dirY = -MathHelper.sin(pitchRad + pitchOffset);
        float dirZ = MathHelper.cos(yawRad + yawOffset) * MathHelper.cos(pitchRad + pitchOffset);

        // ── 正态化 × 速度 ──
        // 有效速度硬编码 3.0，确保弹幕在屏幕上可见
        float len = MathHelper.sqrt_float(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float effectiveSpeed = 3.0F;

        // ── 写入 motion（零散布，纯方向）──
        // this.motionX = ... 的字节码 fieldref owner 是 EntitySniperBullet，
        // mcp2srg.srg 只有 nn（Entity）的 FD 映射。通过 Entity 类型本地变量
        // 中转，强制 fieldref owner = nn → SpecialSource 正确重映射为 nn.x/y/z。
        net.minecraft.entity.Entity thisEntity = this;
        thisEntity.motionX = dirX / len * effectiveSpeed;
        thisEntity.motionY = dirY / len * effectiveSpeed;
        thisEntity.motionZ = dirZ / len * effectiveSpeed;
    }

    /**
     * 设置弹幕渲染图标 ID，并立即同步到 DataWatcher。
     *
     * <p>父类 {@link EntityBulletModelItem#setRenderItemID} 只设置
     * {@code this.renderItemID} 字段，不更新 DataWatcher。DataWatcher
     * 同步发生在 {@code onUpdate()} 的下一个 tick，导致弹幕生成包中的
     * DataWatcher 值为 0 → FlyItemRender 直接 return 不渲染。</p>
     *
     * <p>此覆写在 spawn 前调用 {@code dataWatcher.updateObject(16, id)}，
     * 确保数据在实体生成时就已同步到客户端。</p>
     *
     * <p>SRG 重映射注意事项：{@code ((Entity)this).dataWatcher} 安全 —
     * 字段引用 owner 是 Entity（nn），mcp2srg.srg 中有 FD 映射。</p>
     *
     * @param _id 物品 shiftedIndex（即 {@code Item.itemsList} 索引）
     */
    @Override
    public void setRenderItemID(int _id)
    {
        this.renderItemID = _id;
        // 立即同步到 DataWatcher，避免生成包发送 0 值
        // dataWatcher 是 Entity 的 protected 字段，子类可直接访问。
        // 字节码 fieldref owner = EntitySniperBullet，需在 mcp2srg.srg
        // 中添加 FD: nn/ah atwandaddon/entity/EntitySniperBullet/dataWatcher
        this.dataWatcher.updateObject(16, Integer.valueOf(_id));
    }

    // ================================================================
    //  距离缩放 API
    // ================================================================

    /**
     * 启用距离缩放伤害机制。
     *
     * <p>启用后，每 tick 在 {@link #onUpdate()} 中根据弹丸与射击者之间的
     * 欧几里得距离动态调整伤害：</p>
     * <pre>damage = baseDamage × (1 + distance / divisor)</pre>
     *
     * <p>调用时机：在 {@code setDamage()} 和 {@code setIceDamage()} 之后，
     * {@code spawnEntityInWorld()} 之前调用。</p>
     *
     * @param _baseDamage     基础伤害（与 setDamage 传入的值相同）
     * @param _baseIceDamage  基础冰穿甲伤害
     * @param _divisor        距离除数（通常为枪械的 maxRange 值）
     */
    public void enableDistanceScaling(double _baseDamage, double _baseIceDamage, double _divisor)
    {
        this.useDistanceScaling = true;
        this.baseDamage = _baseDamage;
        this.baseIceDamage = _baseIceDamage;
        this.distanceDivisor = _divisor;
    }

    // ================================================================
    //  每 tick 更新 — 注入距离缩放
    // ================================================================

    /**
     * 覆写实体更新方法，在引擎碰撞检测前注入距离缩放伤害。
     *
     * <p>原理：引擎父类 {@code EntityBulletGeneral.onUpdate()} 在同一 tick 内
     * 完成移动 + 碰撞检测 + 伤害应用。本覆写在调用 {@code super.onUpdate()}
     * 之前，根据当前弹丸位置与射击者位置计算飞行距离，并用距离缩放公式
     * 更新 {@code damage} 和 {@code iceDamage} 字段。</p>
     *
     * <p>伤害公式：{@code effectiveDamage = baseDamage × (1 + distance / divisor)}</p>
     *
     * <p>未命中时：下一 tick 重新计算，值随飞行距离增长。</p>
     */
    @Override
    public void onUpdate()
    {
        // 仅在启用缩放且有有效射击者时执行
        if (this.useDistanceScaling && this.savedShooter != null)
        {
            // 计算弹丸到射击者的欧几里得距离
            double dx = this.posX - this.savedShooter.posX;
            double dy = this.posY - this.savedShooter.posY;
            double dz = this.posZ - this.savedShooter.posZ;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

            // 伤害倍率 = 1 + 距离/除数（maxRange=5 → 最远距离时伤害×2）
            double multiplier = 1.0D + dist / this.distanceDivisor;

            // 写入缩放后的伤害值（父类 setDamage/setIceDamage 是 public 方法）
            setDamage(this.baseDamage * multiplier);
            setIceDamage(this.baseIceDamage * multiplier);
        }

        // 委托父类完成移动、碰撞检测和伤害应用
        super.onUpdate();
    }
}
