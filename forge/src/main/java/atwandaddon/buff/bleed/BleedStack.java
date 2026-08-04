package atwandaddon.buff.bleed;

/**
 * 单个流血 DOT 实例。
 *
 * <p>每 5 tick 触发一次伤害：{@code weaponDamage × 0.05 × level}，
 * 触发后 level--；level 降至 -1 时标记为死亡，由 {@link BleedManager} 清理。</p>
 *
 * <p>职责边界：纯数据容器，不包含任何 tick 逻辑或世界交互。</p>
 */
class BleedStack
{
    /** 剩余流血层数，每触发一次减 1，-1 = 已结束 */
    int level;
    /** 距下次触发的 tick 倒计时 */
    int tickTimer;
    /** 施加流血时的武器最终伤害（用于计算每跳伤害） */
    float baseDamage;

    BleedStack(int _level, float _baseDamage)
    {
        this.level = _level;
        this.tickTimer = 5;        // 首次触发需要等 5 tick
        this.baseDamage = _baseDamage;
    }

    /** 每跳伤害 = 武器伤害 × 0.05 × 当前层数 */
    float damagePerTick()
    {
        return this.baseDamage * 0.05F * (float)this.level;
    }
}
