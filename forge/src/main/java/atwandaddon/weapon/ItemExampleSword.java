package atwandaddon.weapon;

import com.adventurer_engine.common.items.blank.BasicItemSword;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

/**
 * Example melee weapon. It extends the adventurer_engine BasicItemSword class.
 * The weapon properties are configured through JsonHelper.
 */
public class ItemExampleSword extends BasicItemSword {

    /**
     * Constructor.
     *
     * @param _id Item numeric ID assigned in ATcraftAddon.preInit()
     */
    public ItemExampleSword(int _id) {
        super(_id, buildJson(), "ade_gadgets");
    }

    /**
     * Build the sword attributes.
     * The numeric values must be Double to match the adventurer_engine JsonHelper format.
     */
    private static JsonHelper buildJson() {
        StringMap map = new StringMap();
        map.put("name", "example_sword");
        map.put("icon", "example_sword");
        map.put("duration", 100.0D);       // Weapon durability
        map.put("basic_damage", 8.0D);    // Base damage
        map.put("unbreakable", false);    // Can be damaged
        map.put("stack", 1.0D);           // Not stackable
        return new JsonHelper(map);
    }
}
