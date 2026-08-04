package atwandaddon.buff.bleed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

/**
 * 流血 DOT 全局管理器。
 *
 * <h2>核心职责</h2>
 * <ul>
 *   <li>维护每实体的流血栈列表（{@link WeakHashMap} 防内存泄漏）</li>
 *   <li>每 tick 推进所有流血栈的倒计时，到期则造成伤害并降级</li>
 *   <li>自动清理 level = -1 的已结束流血栈</li>
 * </ul>
 *
 * <h2>使用方式</h2>
 * <pre>{@code
 * // 施加流血（在 hitEntity / onPlayerStoppedUsing 中调用）
 * BleedManager.addBleed(target, 3, weaponFinalDamage);
 *
 * // 每 tick 更新（由 BleedTickHandler.LivingUpdateEvent 驱动）
 * BleedManager.tickBleeds(entity);
 * }</pre>
 *
 * <p>职责边界：仅管理数据结构和伤害施加，不负责 tick 事件的监听注册。</p>
 */
public class BleedManager
{
    /** 每实体的流血栈列表，WeakHashMap 确保实体死亡后自动释放 */
    private static final Map<EntityLivingBase, List<BleedStack>> BLEED_MAP =
        new WeakHashMap<EntityLivingBase, List<BleedStack>>();

    /**
     * 向目标实体施加一层流血。
     *
     * @param _target      目标实体
     * @param _level       流血层数（决定持续时间和伤害段数）
     * @param _weaponDmg   施加流血时的武器最终伤害
     */
    public static void addBleed(EntityLivingBase _target, int _level, float _weaponDmg)
    {
        if (_target == null || _level <= 0)
        {
            return;
        }

        List<BleedStack> stacks = BLEED_MAP.get(_target);
        if (stacks == null)
        {
            stacks = new ArrayList<BleedStack>();
            BLEED_MAP.put(_target, stacks);
        }

        stacks.add(new BleedStack(_level, _weaponDmg));
    }

    /**
     * 推进指定实体上的所有流血栈一 tick。
     *
     * <p>由 {@code BleedTickHandler} 在 {@code LivingUpdateEvent} 中调用。
     * 仅在服务端执行伤害计算。</p>
     *
     * @param _entity 要处理的实体
     */
    public static void tickBleeds(EntityLivingBase _entity)
    {
        if (_entity.worldObj.isRemote)
        {
            return; // 仅服务端
        }

        List<BleedStack> stacks = BLEED_MAP.get(_entity);
        if (stacks == null || stacks.isEmpty())
        {
            return;
        }

        Iterator<BleedStack> it = stacks.iterator();
        while (it.hasNext())
        {
            BleedStack stack = it.next();
            stack.tickTimer--;

            if (stack.tickTimer <= 0)
            {
                // 触发一次流血伤害
                float dmg = stack.damagePerTick();
                if (dmg > 0.0F)
                {
                    _entity.attackEntityFrom(DamageSource.generic, dmg);
                }

                stack.level--;
                stack.tickTimer = 5; // 重置倒计时
            }

            // 流血结束：level 降至 -1 时直接结束
            if (stack.level < 0)
            {
                it.remove();
            }
        }

        // 清理空列表
        if (stacks.isEmpty())
        {
            BLEED_MAP.remove(_entity);
        }
    }

    /**
     * 查询实体当前是否有活跃的流血效果。
     *
     * @param _entity 目标实体
     * @return {@code true} 如果至少有一个流血栈活跃
     */
    public static boolean hasBleed(EntityLivingBase _entity)
    {
        List<BleedStack> stacks = BLEED_MAP.get(_entity);
        return stacks != null && !stacks.isEmpty();
    }
}
