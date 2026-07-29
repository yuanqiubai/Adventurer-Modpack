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
        map.put("duration", 100.0D);             // Max durability
        map.put("basic_damage", 8.0D);          // Base damage
        map.put("charge_time", 0.0D);           // Charge time in ticks, 0 = no charge
        map.put("charge_stage", 0.0D);          // Charge stages, 0 = none
        map.put("shoot_delay", 12.0D);          // Delay between shots in ticks
        map.put("shoot_mode", "semi");         // Semi-auto
        map.put("shoot_number", 1.0D);          // Bullets per shot
        map.put("accuracy", 0.9D);              // Accuracy, 1.0 = perfect
        map.put("knockback", 0.5D);             // Knockback strength
        map.put("unbreakable", false);          // Can take durability damage
        map.put("shoot_sound", "random.bow"); // Shoot sound
        map.put("gravity", true);               // Projectile is affected by gravity
        map.put("penetrate", false);            // Projectile does not penetrate
        map.put("maxRange", 64.0D);             // Max range in blocks
        map.put("maxSpeed", 3.0D);              // Projectile speed
        map.put("stack", 1.0D);                 // Not stackable
        map.put("allowed_ammo", Arrays.asList(ItemExampleAmmo.AMMO_NAME));
        return new JsonHelper(map);
    }

    public ItemExampleGun(int _id) {
        super(_id, buildJson(), "ade_gadgets");
    }
}
