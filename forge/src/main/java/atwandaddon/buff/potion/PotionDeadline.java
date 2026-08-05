package atwandaddon.buff.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

/**
 * 悬赏·死线 Buff — 标记目标，累积玩家伤害，结束时一次性结算。
 *
 * <h2>机制</h2>
 * <ul>
 *   <li>命中时通过引擎 {@code DamagePacket.effect_wounded} 施加</li>
 *   <li>Buff 持续期间，EntityBuffListener 累积玩家对目标造成的全部伤害</li>
 *   <li>Buff 到期时（由 EntityBuffListener 独立计时），一次性爆发：
 *       {@code 爆发伤害 = 1 + 累积伤害 × 0.1 × (amplifier + 1)}</li>
 *   <li>Buff 未到期前不可重新施加（通过 EntityBuffListener 锁定）</li>
 * </ul>
 *
 * <h2>数据流</h2>
 * <pre>
 * 武器命中 → LivingHurtEvent → EntityBuffListener
 *     ├─ 实体已有死线 → 累加伤害到 deadlineAccumulatedDamage
 *     └─ 实体无死线 → 记录理论面板伤害（首次命中）
 *     ↓
 * LivingUpdateEvent → EntityBuffListener
 *     ├─ 死线首次激活 → 记录 expireTick = worldTime + duration
 *     └─ 死线到期 → 结算爆发伤害 → 清除记录 + 移除 Potion 效果
 * </pre>
 *
 * <p>本 Potion 的 {@link #performEffect} 和 {@link #isReady} 均为空操作，
 * 所有计时和结算逻辑由 {@link atwandaddon.buff.EntityBuffListener} 统一管理。</p>
 */
public class PotionDeadline extends Potion
{
    /** Potion ID，注册在 {@code ATcraftAddon.preInit()} */
    public static final int ID = 31;

    /** 默认持续时长（tick），20 tick = 1 秒，默认 10 秒 */
    public static final int DEFAULT_DURATION = 200;

    /** 爆发伤害系数：爆发 = 1 + 累积伤害 × RATIO × (amplifier + 1) */
    public static final float DAMAGE_RATIO = 0.1F;

    /** 静态实例，供 {@code EntityBuffListener} 通过 {@link net.minecraft.entity.EntityLivingBase#getActivePotionEffect(Potion)} 查询 */
    public static PotionDeadline instance;

    public PotionDeadline()
    {
        super(ID, true, 0x8B0000);  // 负面效果，暗红色
        this.setPotionName("potion.deadline");
        instance = this;
    }

    // ================================================================
    //  Potion 生命周期 — 空操作，结算由 EntityBuffListener 管理
    // ================================================================

    /**
     * 始终返回 false — 死线不需要每 tick 触发效果。
     * 所有计时和爆发结算在 {@code EntityBuffListener.onLivingUpdate()} 中完成。
     */
    @Override
    public boolean isReady(int _duration, int _amplifier)
    {
        return false;
    }

    /**
     * 空操作 — 死线的爆发结算由 EntityBuffListener 统一管理，
     * 不在此处执行任何逻辑。
     */
    @Override
    public void performEffect(EntityLivingBase _entity, int _amplifier)
    {
        // 空操作：所有结算逻辑在 EntityBuffListener.onLivingUpdate() 中
    }
}
