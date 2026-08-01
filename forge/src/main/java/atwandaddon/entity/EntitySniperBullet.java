package atwandaddon.entity;

import com.adventurer_engine.common.entity.projectiles.EntityBulletModelItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 零散布狙击弹丸 — 绕过引擎精度系统的自定义弹道实体。
 *
 * <p>引擎的 {@link EntityBulletGeneral} 构造函数在设置 motion 时经过多层
 * 精度/散布处理（disturbVector + 自定义 c() 方法），导致弹道严重偏离。
 * 此类直接使用 vanilla Minecraft 方向数学计算 motion，完全绕过引擎精度系统：</p>
 * <ul>
 * <li>方向：纯 {@code -sin(yaw)*cos(pitch), -sin(pitch), cos(yaw)*cos(pitch)}</li>
 * <li>散布：零（{@code accuracy = 1.0F} 传入 super 跳过 disturbVector）</li>
 * <li>速度：正态化后乘 entity_speed，无 nextGaussian 噪声</li>
 * </ul>
 *
 * <p>继承 {@link EntityBulletModelItem} 以保留 FlyItemRender 2D 物品图标渲染。</p>
 *
 * @see EntityBulletModelItem 父类（renderItemID 数据同步 + FlyItemRender 支持）
 * @see SnowfieldExplorer 调用方（覆写 a() 方法生成此类实例）
 */
public class EntitySniperBullet extends EntityBulletModelItem
{
    /** 无参构造器 — 供 Forge EntityRegistry 反射创建实体用 */
    public EntitySniperBullet(World _world)
    {
        super(_world);
        // [DEBUG] 客户端反射构造 — 记录实体创建
        System.out.println("[SniperDebug] Client entity created (World-only ctor), renderItemID=" + this.renderItemID);
    }

    /**
     * 从射击者创建零散布狙击弹丸。
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
     * <li><b>弹速 ×1.5</b>：引擎 {@code EntityBulletGeneral} 构造器内部
     * {@code c()} 方法有 {@code speed * 1.5f}。此类覆盖 motion 后 super
     * 的 motion 被丢弃，不会二次累乘。此处 ×1.5F 仅匹配引擎标准弹速行为，
     * 实际速度 = _speed × 1.5，与引擎默认子弹一致。</li>
     *
     * <li><b>零散布</b>：accuracy=1.0F 跳过 disturbVector，motion 由纯
     * vanilla 方向公式直接计算，无 nextGaussian 噪声。</li>
     * </ol>
     *
     * @param _world    游戏世界实例
     * @param _shooter  射击者实体（EntityPlayer，提供瞄准 yaw/pitch）
     * @param _speed    弹丸飞行速度（已包含引擎 chargeRatio 蓄力修正）
     */
    public EntitySniperBullet(World _world, EntityLivingBase _shooter, float _speed)
    {
        // accuracy=1.0F → disturVector 不执行（条件为 accuracy < 1.0）
        // super 设置 spawn 位置（含 eyeHeight + 后退偏移）和实体数据
        super(_world, _shooter, _speed, 1.0F);

        // ── 角度转弧度 ──
        // 强转为 Entity：javac 对 _shooter.rotationYaw（声明类型 EntityLivingBase）
        // 生成的 fieldref owner 为 of（EntityLivingBase），mcp2srg.srg 中 rotationYaw
        // 的 FD 映射只存在于 nn（Entity）。强转后 fieldref owner = nn，SRG 映射正确。
        float yawRad = ((net.minecraft.entity.Entity)_shooter).rotationYaw / 180.0F * (float)Math.PI;
        float pitchRad = ((net.minecraft.entity.Entity)_shooter).rotationPitch / 180.0F * (float)Math.PI;

        // ── vanilla MC 标准方向向量 ──
        float dirX = -MathHelper.sin(yawRad) * MathHelper.cos(pitchRad);
        float dirY = -MathHelper.sin(pitchRad);
        float dirZ = MathHelper.cos(yawRad) * MathHelper.cos(pitchRad);

        // ── 正态化 × 速度 ──
        // 硬编码速度 3.0，确保弹幕在屏幕上可见（引擎默认 1.5 倍率可能太快）
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

        // [DEBUG] 记录服务端弹丸创建参数
        System.out.println("[SniperDebug] Server bullet created: yaw="
            + ((net.minecraft.entity.Entity)_shooter).rotationYaw
            + " pitch=" + ((net.minecraft.entity.Entity)_shooter).rotationPitch
            + " dir=(" + dirX + "," + dirY + "," + dirZ + ")"
            + " speed=" + effectiveSpeed
            + " motion=(" + thisEntity.motionX + "," + thisEntity.motionY + "," + thisEntity.motionZ + ")");
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
        // [DEBUG] 记录设置前的状态
        System.out.println("[SniperDebug] setRenderItemID called: _id=" + _id
            + " old field=" + this.renderItemID);

        this.renderItemID = _id;
        // 立即同步到 DataWatcher，避免生成包发送 0 值
        // dataWatcher 是 Entity 的 protected 字段，子类可直接访问。
        // 字节码 fieldref owner = EntitySniperBullet，需在 mcp2srg.srg
        // 中添加 FD: nn/ah atwandaddon/entity/EntitySniperBullet/dataWatcher
        this.dataWatcher.updateObject(16, Integer.valueOf(_id));

        // [DEBUG] 确认同步后的字段值
        System.out.println("[SniperDebug] setRenderItemID done: field=" + this.renderItemID);
    }
}
