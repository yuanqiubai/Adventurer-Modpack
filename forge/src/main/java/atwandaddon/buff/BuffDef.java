package atwandaddon.buff;

/**
 * Buff 类型定义 — 描述一个可被 BuffMonitor 追踪的 DOT 型 buff。
 *
 * <p>构造参数顺序 {@code (ID, 时长, 等级)} 与 {@code NbtEventTags.writePotion()}
 * 保持一致，方便武器端直接传递。</p>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * // 注册到 BuffRegistry
 * BuffRegistry.register(new BuffDef(PotionBleed.ID, 200, 0)
 *     .damageRatio(0.1f)
 *     .tickInterval(2));
 *
 * // 与 NbtEventTags 配合 — 武器端无需知道 BuffDef
 * NbtEventTags.writePotion(stack, NbtEventTags.TAG_HIT_TARGET,
 *     PotionBleed.ID, 200, 0);
 * }</pre>
 *
 * @see BuffRegistry
 * @see BuffMonitor
 */
public class BuffDef
{
    /** Potion 效果 ID，对应 {@code Potion.potionTypes[id]} */
    public final int potionId;

    /** 默认持续时长（tick），20 tick = 1 秒 */
    public final int duration;

    /** 默认效果等级（0 = I 级, 1 = II 级, ...） */
    public final int amplifier;

    /** DOT 伤害倍率：每次触发 = 实际承受伤害 × damageRatio × (amplifier + 1) */
    public float damageRatio = 0.1F;

    /** DOT 触发间隔（tick），2 = 每 0.1 秒触发一次 */
    public long tickInterval = 2L;

    /**
     * 构造一个 buff 类型定义。
     *
     * <p>参数顺序 {@code (ID, 时长, 等级)} 与 {@link NbtEventTags#writePotion}
     * 保持一致。</p>
     *
     * @param _potionId  Potion 效果 ID
     * @param _duration  默认持续时长（tick）
     * @param _amplifier 默认效果等级（0 = I 级）
     */
    public BuffDef(int _potionId, int _duration, int _amplifier)
    {
        this.potionId = _potionId;
        this.duration = _duration;
        this.amplifier = _amplifier;
    }

    /** 设置 DOT 伤害倍率，默认 0.1 */
    public BuffDef damageRatio(float _ratio)
    {
        this.damageRatio = _ratio;
        return this;
    }

    /** 设置 DOT 触发间隔（tick），默认 2 */
    public BuffDef tickInterval(long _interval)
    {
        this.tickInterval = _interval;
        return this;
    }
}
