package atwandaddon.weapon.gun;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;
import java.util.Arrays;
import atwandaddon.weapon.ammo.SnowEagleAP;

public class SnowfieldExplorer extends BasicRangedWeapon
{
    public static JsonHelper buildJson() 
    {
        StringMap map = new StringMap();
        map.put("name", "snowfield_explorer");
        map.put("icon", "snowfield_explorer");
        map.put("duration", 8.0D);
        map.put("ice_damage", false);
        map.put("basic_damage", 16.0D);
        map.put("charge_time", 4.0D);           // 蓄力 0.2 秒满伤害
        map.put("charge_stage", 1.0D);           // 1 段蓄力动画
        map.put("icons", Arrays.asList("snowfield_explorer"));  // 暂时共用图标，后续可换不同帧
        map.put("shoot_delay", 1.0D);
        map.put("shoot_mode", "semi");
        map.put("shoot_number", 1.0D);
        map.put("accuracy", 0.9D);
        map.put("knockback", 0.5D);
        map.put("unbreakable", true);
        map.put("shoot_sound", "bow_zx");
        map.put("gravity", false);
        map.put("penetrate", false);
        map.put("maxRange", 64.0D);
        map.put("maxSpeed", 6.0D);
        map.put("stack", 1.0D);
        map.put("allowed_ammo", Arrays.asList(SnowEagleAP.AMMO_NAME));
        return new JsonHelper(map);
    }

    public SnowfieldExplorer(int _id) 
    {
        super(_id, buildJson(), "ade_gadgets");
    }
}
