package atwandaddon.weapon;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

/**
 * Example ammo item. It extends the adventurer_engine BasicAmmo class.
 * Registering it in ItemLoader.ammoItem allows ranged weapons to find it.
 */
public class ItemExampleAmmo extends BasicAmmo {

    public static final String AMMO_NAME = "example_ammo";

    private static JsonHelper buildJson() {
        StringMap map = new StringMap();
        map.put("name", AMMO_NAME);
        map.put("icon", "example_ammo");
        map.put("duration", 1.0D);            // One-shot consumable style
        map.put("unbreakable", false);
        map.put("stack", 64.0D);
        return new JsonHelper(map);
    }

    public ItemExampleAmmo(int _id) {
        super(_id, buildJson(), "ade_gadgets");
    }

    /**
     * Register this ammo in the adventurer_engine ammo registry so guns can resolve
     * it through the allowed_ammo list.
     */
    public void registerAsAmmo() {
        ItemLoader.ammoItem.put(AMMO_NAME, this);
    }
}
