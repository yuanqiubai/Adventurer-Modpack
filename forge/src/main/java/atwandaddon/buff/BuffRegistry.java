package atwandaddon.buff;

import java.util.HashMap;
import java.util.Map;

/**
 * Buff 类型注册表 — 全局静态映射 Potion ID → {@link BuffDef}。
 *
 * <p>武器通过 NbtEventTags 施加 Potion 效果后，BuffMonitor 查询此表
 * 来判断该 potion 是否为一个可追踪的 buff，并获取 DOT 参数。</p>
 *
 * <h2>注册时机</h2>
 * 在 {@code ATcraftAddon.preInit()} 中完成注册，确保在游戏加载物品前
 * 所有 buff 定义已就位。
 */
public final class BuffRegistry
{
    /** Potion ID → BuffDef 映射 */
    private static final Map<Integer, BuffDef> BUFFS = new HashMap<Integer, BuffDef>();

    /** 禁止实例化 */
    private BuffRegistry() {}

    /**
     * 注册一个 buff 类型定义。
     *
     * @param _def Buff 定义（potionId 不可与已有注册冲突）
     */
    public static void register(BuffDef _def)
    {
        if (_def == null)
        {
            return;
        }
        BUFFS.put(Integer.valueOf(_def.potionId), _def);
    }

    /**
     * 查询 potion ID 对应的 buff 定义。
     *
     * @param _potionId Potion 效果 ID
     * @return BuffDef，未注册时返回 null
     */
    public static BuffDef get(int _potionId)
    {
        return BUFFS.get(Integer.valueOf(_potionId));
    }

    /** @return 当前已注册的 buff 数量 */
    public static int size()
    {
        return BUFFS.size();
    }

    /**
     * 获取所有已注册的 Potion ID 集合。
     * 供 {@code EntityBuffListener} 遍历检查实体当前持有的活跃 Buff。
     *
     * @return 所有已注册 Potion ID 的不可变视图
     */
    public static java.util.Set<Integer> getAllIds()
    {
        return java.util.Collections.unmodifiableSet(BUFFS.keySet());
    }
}
