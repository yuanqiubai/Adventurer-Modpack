package atwandaddon.buff.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * 枪之饥 Buff — 吸血型增益，每颗子弹命中回复 1 × 等级 HP。
 *
 * <h2>机制</h2>
 * <ul>
 *   <li>纯被动 Buff，不触发 performEffect（{@link #isReady} 始终返回 false）</li>
 *   <li>{@link atwandaddon.buff.EntityBuffListener#onLivingHurt} 在枪械命中时检测攻击者是否持有此 Buff，
 *       若持有则恢复攻击者 HP</li>
 *   <li>回血公式：{@code heal = 1.0 × (amplifier + 1)}</li>
 *   <li>amplifier 0 = I 级（回 1 HP/弹），amplifier 1 = II 级（回 2 HP/弹），以此类推</li>
 * </ul>
 *
 * <h2>施加方式</h2>
 * 玩家通过饮用自定义药水获得此 Buff，或通过其他 Mod 的 Buff 施加机制。
 *
 * <h2>命名来源</h2>
 * 联动魂枪设定，让吸血行为变成"枪在进食"。
 */
public class PotionGunHunger extends Potion
{
    /** Potion ID，注册在 {@code ATcraftAddon.preInit()} */
    public static final int ID = 37;

    /** 静态实例，供外部类通过 {@link EntityLivingBase#getActivePotionEffect(Potion)} 查询 */
    public static PotionGunHunger instance;

    public PotionGunHunger()
    {
        super(ID, false, 0xCC3333);  // 增益效果（isBadEffect=false），暗红色
        this.setPotionName("potion.gunHunger");
        this.setIconIndex(6, 2);     // UV 坐标 (5, 2)
        instance = this;
    }

    // ================================================================
    //  Potion 生命周期 — 空操作，纯被动标记
    // ================================================================

    /**
     * 始终就绪。间隔判断交由 performEffect 使用世界时间自行处理，
     * 避免 duration 被 Potion 重施加打乱节奏。
     *
     * TODO: 当前 isReady 返回 _duration > 0 只是临时方案。实测 buff 触发一次回血后
     * 仍会消失，需排查是 MC 药水系统在 performEffect 空跑时自动清理，还是其他 mod
     * 干扰。备选方案：在 performEffect 中做空操作的 worldTime 检查来维持 Potion 活跃状态。
     */
    @Override
    public boolean isReady(int _duration, int _amplifier)
    {
        return _duration > 0;
    }

    /** 空操作 — 所有逻辑由 EntityBuffListener.onLivingHurt 处理。 */
    @Override
    public void performEffect(EntityLivingBase _entity, int _amplifier)
    {
        // 空操作：纯被动 Buff，回血逻辑在 EntityBuffListener.onLivingHurt 中
    }
}
