package atwandaddon.weapon;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;
import java.util.Arrays;

/**
 * Example ranged weapon. It extends the adventurer_engine BasicRangedWeapon class.
 * This version is a semi-auto gun using example_ammo.
 */
public class ItemExampleGun extends BasicRangedWeapon {

    private static JsonHelper buildJson() {
        StringMap map = new StringMap();
        map.put("name", "example_gun");
        map.put("icon", "example_gun");
        map.put("duration", 100.0D);            // 最大耐久度
        map.put("basic_damage", 8.0D);          // 基础伤害
        map.put("charge_time", 0.0D);           // 蓄力时间（单位：tick），0 = 无需蓄力
        // 🚨 蓄力阶段数，0 = 无蓄力阶段。
        // 🚨 若 charge_stage >= 1，必须同时提供 icons 列表！
        // 🚨 例如：map.put("icons", Arrays.asList("icon_name"));
        // 🚨 否则 registerIcons() 中 this.icons.size() 会空指针 → 启动闪退！
        // 🚨 参见 BasicRangedWeapon.a(mt) 第 222-227 行。
        map.put("charge_stage", 0.0D);
        map.put("shoot_delay", 12.0D);          // 射击间隔（单位：tick）
        map.put("shoot_mode", "semi");          // 半自动模式
        map.put("shoot_number", 1.0D);          // 每次射击的子弹数
        map.put("accuracy", 0.9D);              // 精度，1.0 = 完美精度
        map.put("knockback", 0.5D);             // 击退强度
        map.put("unbreakable", false);          // 是否不可破坏（false = 会消耗耐久度）
        map.put("shoot_sound", "random.bow");   // 射击音效
        map.put("gravity", true);               // 弹射物受重力影响
        map.put("penetrate", false);            // 弹射物不具备穿透效果
        map.put("maxRange", 64.0D);             // 最大射程（单位：方块）
        map.put("maxSpeed", 3.0D);              // 弹射物速度
        map.put("stack", 1.0D);                 // 堆叠数量（1.0 = 不可堆叠）
        map.put("allowed_ammo", Arrays.asList(ItemExampleAmmo.AMMO_NAME));
        return new JsonHelper(map);
    }

    public ItemExampleGun(int _id) {
        super(_id, buildJson(), "ade_gadgets");
    }
}
