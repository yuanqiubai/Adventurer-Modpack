package atwandaddon.buff;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Buff 监控中心 — 解耦武器伤害值与 Buff DOT 计算。
 *
 * <h2>解决的问题</h2>
 * 旧设计中，武器通过静态字段 {@code PotionBleed.pendingWeaponDamage = xxx}
 * 向 buff 传递面板伤害，导致 buff 逻辑与武器类强耦合。
 * BuffMonitor 作为中间层，通过 {@link LivingHurtEvent} 自动捕获实体实际
 * 承受伤害，buff 类通过 {@link #consumeDamage(int)} 读取，双方互不感知。
 *
 * <h2>数据流</h2>
 * <pre>
 * 武器命中 → attackEntityFrom() → LivingHurtEvent
 *     ↓                                    ↓
 *  NbtEventTags 施加 PotionEffect    BuffMonitor 累积 actualDamage[entityId]
 *     ↓                                    ↓
 * MC updatePotionEffects() → Potion.performEffect()
 *     ↓
 * consumeDamage(entityId) → 读取累计伤害 → 计算 DOT
 * </pre>
 *
 * <h2>注册方式</h2>
 * <pre>{@code
 * BuffMonitor monitor = new BuffMonitor();
 * MinecraftForge.EVENT_BUS.register(monitor);
 * }</pre>
 *
 * <p><b>注意</b>：本类跳过 {@link DamageSource#generic} 造成的伤害，
 * 避免 DOT 自身触发新的 {@code LivingHurtEvent} 形成反馈循环。</p>
 *
 * @see BuffDef
 * @see BuffRegistry
 */
public class BuffMonitor
{
    /** 单例实例，供 Potion 子类静态访问 */
    private static BuffMonitor instance;

    /**
     * 实体 ID → 累计实际承受伤害。
     * 每次 {@link LivingHurtEvent} 触发时累加（同一次攻击可能分 basic + ice 两次调用），
     * 被 {@link #consumeDamage(int)} 读取后清除。
     */
    private final Map<Integer, Float> pendingDamage = new HashMap<Integer, Float>();

    /** 构造即设为单例 */
    public BuffMonitor()
    {
        instance = this;
    }

    // ================================================================
    //  事件监听
    // ================================================================

    /**
     * 监听所有实体受伤事件，累加实际伤害值。
     *
     * <p>引擎的 {@code DamagePacket.causeDamage()} 可能分 basic + ice
     * 两次 {@code attackEntityFrom} 调用，因此使用累加而非覆盖。</p>
     *
     * <p>跳过 {@link DamageSource#generic}（DOT 自身伤害），防止反馈循环。</p>
     *
     * @param _event LivingHurtEvent，{@code ammount} 为经过护甲/抗性后的最终伤害
     */
    @ForgeSubscribe
    public void onLivingHurt(LivingHurtEvent _event)
    {
        // 跳过 DOT 自身造成的伤害，防止反馈循环
        if (_event.source == DamageSource.generic)
        {
            return;
        }

        EntityLivingBase entity = _event.entityLiving;
        if (entity == null)
        {
            return;
        }

        int entityId = entity.entityId;
        Float old = pendingDamage.get(Integer.valueOf(entityId));
        float accumulated = (old != null ? old.floatValue() : 0F) + _event.ammount;
        pendingDamage.put(Integer.valueOf(entityId), Float.valueOf(accumulated));
    }

    // ================================================================
    //  静态访问接口（供 Potion 子类调用）
    // ================================================================

    /**
     * 获取并清除指定实体的累计实际伤害。
     *
     * <p>调用后该实体的累计值归零，避免同一次伤害被多次 DOT 使用。</p>
     *
     * @param _entityId 实体数字 ID
     * @return 累计实际承受伤害，无记录时返回 0
     */
    public static float consumeDamage(int _entityId)
    {
        if (instance == null)
        {
            return 0F;
        }
        Float val = instance.pendingDamage.remove(Integer.valueOf(_entityId));
        return val != null ? val.floatValue() : 0F;
    }
}
