package atwandaddon.buff.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * 易伤 Buff — 每级使实体承受的 DOT 伤害增加 25%。
 *
 * <h2>机制</h2>
 * <ul>
 *   <li>纯被动 Buff，不触发 performEffect（{@link #isReady} 始终返回 false）</li>
 *   <li>DOT 来源（如 {@link PotionBleed}）在计算伤害时调用 {@link #getDOTMultiplier(EntityLivingBase)}
 *       查询实体是否持有此 Buff，并应用倍率</li>
 *   <li>倍率公式：{@code 1.0 + 0.25 × (amplifier + 1)}</li>
 *   <li>amplifier 0 = I 级（×1.25），amplifier 1 = II 级（×1.5），以此类推</li>
 * </ul>
 *
 * <h2>施加方式</h2>
 * 武器 Builder 中通过 {@code .effectWounded(new int[]{PotionVulnerable.ID, duration, amplifier})}
 * 配置。
 */
public class PotionVulnerable extends Potion
{
    /** Potion ID，注册在 {@code ATcraftAddon.preInit()} */
    public static final int ID = 32;

    /** 每级 DOT 伤害增幅比例，0.25 = +25% */
    public static final float DOT_BONUS_PER_LEVEL = 0.25F;

    /** 静态实例，供外部类通过 {@link EntityLivingBase#getActivePotionEffect(Potion)} 查询 */
    public static PotionVulnerable instance;

    public PotionVulnerable()
    {
        super(ID, true, 0x800080);  // 负面效果，紫色
        this.setPotionName("potion.vulnerable");
        this.setIconIndex(3, 1);    // UV 坐标 (1, 3) = 像素(90, 36)
        instance = this;
    }

    // ================================================================
    //  Potion 生命周期 — 空操作，纯被动标记
    // ================================================================

    /** 始终返回 false — 本 Buff 无主动效果，仅作为 DOT 来源读取的标记。 */
    @Override
    public boolean isReady(int _duration, int _amplifier)
    {
        return false;
    }

    /** 空操作 — 所有逻辑由 DOT 来源（如 PotionBleed）主动查询。 */
    @Override
    public void performEffect(EntityLivingBase _entity, int _amplifier)
    {
        // 空操作：纯被动 Buff，由 DOT 来源类在计算伤害时查询
    }

    // ================================================================
    //  静态工具方法
    // ================================================================

    /**
     * 查询实体是否持有易伤 Buff，并返回 DOT 伤害倍率。
     *
     * <p>倍率公式：{@code 1.0 + 0.25 × (amplifier + 1)}</p>
     *
     * @param _entity 目标实体
     * @return DOT 伤害倍率（≥ 1.0），无此 Buff 时返回 1.0
     */
    public static float getDOTMultiplier(EntityLivingBase _entity)
    {
        if (_entity == null || instance == null)
        {
            return 1.0F;
        }
        PotionEffect effect = _entity.getActivePotionEffect(instance);
        if (effect == null)
        {
            return 1.0F;
        }
        // 每级 +25%：amplifier 0 = ×1.25, amplifier 1 = ×1.5, ...
        return 1.0F + DOT_BONUS_PER_LEVEL * (float)(effect.getAmplifier() + 1);
    }
}
