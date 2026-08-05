package atwandaddon.weapon.melee;

import com.adventurer_engine.common.items.blank.BasicItemSword;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

/**
 * 重剑武器 — 支持自定义 name / icon，用于批量创建不同纹理的同类武器。
 */
public class HeavySword extends BasicItemSword
{
    /** 物品内部名称，用于 ItemLoader 全局注册表 */
    private final String internalName;
    /** 纹理图标文件名（不含扩展名），对应 adventurer_engine 域下的 png */
    private final String iconName;

    /**
     * 通用构造函数 — 传入自定义 name 和 icon。
     *
     * @param _id        物品数字 ID，由 ATcraftAddon.preInit() 分配
     * @param _name      物品内部名称（如 "sword_1"）
     * @param _icon      纹理图标名（如 "sword_1"），对应 assets/adventurer_engine/textures/items/sword_1.png
     */
    public HeavySword(int _id, String _name, String _icon)
    {
        super(_id, buildJson(_name, _icon), "ade_gadgets");
        this.internalName = _name;
        this.iconName = _icon;
    }

    // ===== 旧代码 (保留以作参考) =====
    // public HeavySword(int _id)
    // {
    //     super(_id, buildJson(), "ade_gadgets");
    // }
    // ===== 新代码 =====

    /**
     * 构建物品属性 JSON。
     * 数值必须为 Double 类型，以匹配 adventurer_engine JsonHelper 格式。
     *
     * @param _name  物品内部名称
     * @param _icon  纹理图标名
     * @return 包含物品属性的 JsonHelper 实例
     */
    private static JsonHelper buildJson(String _name, String _icon)
    {
        StringMap map = new StringMap();
        map.put("name", _name);            // 内部名称（用于注册表识别）
        map.put("icon", _icon);            // 纹理图标文件名
        map.put("duration", 200.0D);       // 武器耐久
        map.put("basic_damage", 12.0D);    // 基础伤害
        map.put("unbreakable", false);     // 可损坏
        map.put("stack", 1.0D);            // 不可堆叠
        return new JsonHelper(map);
    }

    // ===== 旧代码 (保留以作参考) =====
    // private static JsonHelper buildJson()
    // {
    //     StringMap map = new StringMap();
    //     map.put("name", "heavy_sword");
    //     map.put("icon", "heavy_sword");
    //     map.put("duration", 200.0D);
    //     map.put("basic_damage", 12.0D);
    //     map.put("unbreakable", false);
    //     map.put("stack", 1.0D);
    //     return new JsonHelper(map);
    // }
    // ===== 新代码 =====
}
