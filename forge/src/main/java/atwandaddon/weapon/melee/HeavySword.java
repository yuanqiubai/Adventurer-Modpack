package atwandaddon.weapon.melee;

import com.adventurer_engine.common.items.blank.BasicItemSword;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

/**
 * 重剑武器 编写与测试
 */
public class HeavySword extends BasicItemSword
{
    /**
     * 构造函数
     *
     * @param _id 物品数字 ID，由 ATcraftAddon.preInit() 分配
     */
    public HeavySword(int _id)
    {
        super(_id, buildJson(), "ade_gadgets");
    }

    /**
     * 构建重剑属性
     * 数值必须为 Double 类型，以匹配 adventurer_engine JsonHelper 格式。
     */
    private static JsonHelper buildJson()
    {
        StringMap map = new StringMap();
        map.put("name", "heavy_sword");
        map.put("icon", "heavy_sword");
        map.put("duration", 200.0D);       // 武器耐久
        map.put("basic_damage", 12.0D);    // 基础伤害
        map.put("unbreakable", false);     // 可损坏
        map.put("stack", 1.0D);            // 不可堆叠
        return new JsonHelper(map);
    }
}
