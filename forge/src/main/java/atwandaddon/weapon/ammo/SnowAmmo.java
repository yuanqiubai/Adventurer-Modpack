package atwandaddon.weapon.ammo;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

public class SnowAmmo extends BasicAmmo {

    public static final String AMMO_NAME = "snow_ammo";

    private static JsonHelper buildJson() {
        StringMap map = new StringMap();
        map.put("name", AMMO_NAME);
        map.put("icon", "snow_ammo");
        map.put("duration", 1.0D);
        map.put("unbreakable", false);
        map.put("stack", 64.0D);
        return new JsonHelper(map);
    }

    public SnowAmmo(int _id) {
        super(_id, buildJson(), "ade_gadgets");
    }

    public void registerAsAmmo() {
        ItemLoader.ammoItem.put(AMMO_NAME, this);
    }
}
