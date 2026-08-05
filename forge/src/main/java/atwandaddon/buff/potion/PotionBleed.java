package atwandaddon.buff.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

import cpw.mods.fml.common.FMLLog;

import atwandaddon.buff.EntityBuffListener;

/**
 * 流血 DOT Potion — 基于实体实际承受伤害的百分比流血。
 *
 * <h2>机制</h2>
 * <ul>
 *   <li>命中时通过引擎 {@code DamagePacket.effect_wounded} 或 NbtEventTags {@code hitEntity_Target} 施加</li>
 *   <li>{@link BuffMonitor} 通过 {@code LivingHurtEvent} 自动捕获实体实际承受的最终伤害（含护甲/抗性/乘区），
 *       无需武器端手动传值</li>
 *   <li>DOT 伤害 = 1 + 实际承受伤害 × DAMAGE_RATIO × (amplifier + 1)</li>
 *   <li>使用世界时间做全局时钟，精确控制触发间隔</li>
 *   <li>再次命中同一目标 → amplifier 叠加（MC 原生机制）</li>
 * </ul>
 *
 * <h2>数据流（解耦后）</h2>
 * <pre>
 * 武器命中 → LivingHurtEvent → EntityBuffListener 持久存储 actualDamage[entityId] + nbtBonus
 *     ↓
 * NbtEventTags / DamagePacket 施加 PotionEffect
 *     ↓
 * MC updatePotionEffects() → performEffect() → EntityBuffListener.getDamage() → 计算 DOT
 *     ↓
 * DOT 伤害不消费数据 → 后续 tick 可重复读取 → 持续流血直到 Buff 过期
 * </pre>
 *
 * <p>EntityBuffListener.onLivingUpdate() 负责在 Buff 过期后自动清理记录。</p>
 */
public class PotionBleed extends Potion
{
    /** Potion ID，注册在 {@code ATcraftAddon.preInit()} */
    public static final int ID = 30;

    /** DOT 伤害系数 — 每次触发伤害 = 1 + 实际承受伤害 × (amplifier + 1) × DAMAGE_RATIO */
    private static final float DAMAGE_RATIO = 0.1F;

    /** 伤害触发间隔（tick），2 tick = 0.1 秒 = 10 次/秒 */
    private static final long TICK_INTERVAL = 2L;

    public PotionBleed()
    {
        super(ID, true, 0xCC0000);  // 负面效果，深红色
        this.setPotionName("potion.bleed");
        this.setIconIndex(3, 2);    // vanilla 未使用: (3,2) = 像素(54,36)
    }

    // ================================================================
    //  Potion 生命周期
    // ================================================================

    /**
     * 始终就绪。间隔判断交由 performEffect 使用世界时间自行处理，
     * 避免 duration 被 Potion 重施加打乱节奏。
     */
    @Override
    public boolean isReady(int _duration, int _amplifier)
    {
        return _duration > 0;
    }

    /**
     * 执行流血伤害。使用世界时间作为全局时钟，每 {@value #TICK_INTERVAL} tick 触发一次。
     *
     * <p>伤害数据来源：{@link EntityBuffListener#getDamage(int)} 返回的理论面板伤害，
     * 已包含武器基础面板 + NBT 属性加成的全部乘区计算，不再需要单独读取 NBT 加成。</p>
     *
     * <p>DOT 公式：{@code DOT = 1 + totalPanelDamage × DAMAGE_RATIO × (amplifier + 1)}</p>
     *
     * @param _entity     目标实体
     * @param _amplifier  Potion 等级（0 = I 级, 1 = II 级, ...）
     */
    @Override
    public void performEffect(EntityLivingBase _entity, int _amplifier)
    {
        long worldTime = _entity.worldObj.getTotalWorldTime();
        if (worldTime % TICK_INTERVAL != 0)
        {
            // 不在触发间隔内，静默跳过（避免刷屏）
            return;
        }

        int entityId = _entity.entityId;

        // ===== 旧代码 (保留以作参考) =====
        // // 从 BuffMonitor 读取该实体实际承受的累计伤害（含护甲/抗性/乘区）
        // float actualDamage = BuffMonitor.consumeDamage(entityId);
        // ...
        // // v1: 从 EntityBuffListener 分别读取 actualDamage 和 nbtFlatBonus
        // float actualDamage = EntityBuffListener.getDamage(entityId);
        // float nbtFlatBonus = EntityBuffListener.getNbtBonus(entityId);
        // float totalDamage = actualDamage + nbtFlatBonus;
        // ===== 新代码 v2 =====

        // 从 EntityBuffListener 读取理论面板伤害（已含武器面板 + NBT 全部乘区）
        float totalPanelDamage = EntityBuffListener.getDamage(entityId);

        // ===== DEBUG: performEffect 入口日志 =====
        FMLLog.info("[BleedDebug] performEffect ENTER | entityId=%d | worldTime=%d | amplifier=%d | totalPanelDamage=%.2f",
            Integer.valueOf(entityId), Long.valueOf(worldTime), Integer.valueOf(_amplifier),
            Float.valueOf(totalPanelDamage));
        // ===== END DEBUG =====

        if (totalPanelDamage <= 0F)
        {
            return;  // 无有效伤害记录，跳过此 tick
        }

        // DOT = 1 + 理论面板伤害 × 倍率 × 等级
        // amplifier 0 = I 级（×1），amplifier 1 = II 级（×2）
        float damage = 1F + (totalPanelDamage * DAMAGE_RATIO * (float)(_amplifier + 1));

        _entity.attackEntityFrom(DamageSource.generic, damage);

        // ===== DEBUG: DOT 伤害确认 =====
        FMLLog.info("[BleedDebug] performEffect DOT | entityId=%d | dotDamage=%.2f | hurtResistantTime_before=%d",
            Integer.valueOf(entityId), Float.valueOf(damage),
            Integer.valueOf(((net.minecraft.entity.Entity)_entity).hurtResistantTime));
        // ===== END DEBUG =====

        // 重置受伤无敌帧，确保下一跳 DOT 不被阻挡
        // MC attackEntityFrom 成功后 hurtResistantTime=10，但我们每 2 tick 触发一次，必须重置为 0
        // 强转 Entity 确保字节码 owner=nn（Entity），否则 EntityLivingBase 类型的 field 需要额外 FD 映射
        ((net.minecraft.entity.Entity)_entity).hurtResistantTime = 0;
    }
}
