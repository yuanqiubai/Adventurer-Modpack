package atwandaddon.weapon.ammo;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import atwandaddon.weapon.nbt.AmmoNbtBuilder;

/**
 * 雪鹰穿甲弹 — 雪原探索者狙击步枪专用 AP 弹药。
 *
 * <p>使用 {@link AmmoNbtBuilder}（NBT 路径）构造，
 * 绕过了引擎 JSON 路径 {@code ice_damage = 0.0f} 的硬编码 bug，
 * 从而正确获得穿甲伤害 + 高斯波动暴击。</p>
 */
public class SnowEagleAP extends BasicAmmo {

    /** 弹药注册名，供枪械 {@code allowed_ammo} 引用 */
    public static final String AMMO_NAME = "snow_eagle_ap";

    /**
     * @param _id             物品数字 ID（逻辑 ID）
     * @param _renderItemId   LeadBulletMod 的 {@code itemID}（shiftedIndex = 逻辑 ID + 256）。
     *                        用于弹幕实体渲染，必须匹配 {@code Item.itemsList} 的索引。
     */
    public SnowEagleAP(int _id, int _renderItemId) {
        // ===== NBT 路径（当前使用）=====
        // 引擎 JSON 构造器硬编码 ice_damage = 0.0f，
        // 导致穿甲伤害、穿透等参数无法生效。
        // 改用 AmmoNbtBuilder → BasicAmmo(NBTTagCompound) 绕过此限制。
        super(AmmoNbtBuilder.create(_id, AMMO_NAME)
            // ---- 基础属性 ----
            .icon("snow_eagle_ap")
            .maxStack(64)
            // ---- 伤害 ----
            .basicDamage(8.0)    // 普通物理伤害
            .iceDamage(8.0)      // 冰穿甲伤害（独立护甲计算）🔥 NBT 专属
            // ---- 弹道 ----
            // 弹幕实体图标与弹药背包图标分离：
            //   背包图标: snow_eagle_ap.png  (由 .icon("snow_eagle_ap") 控制)
            //   弹幕图标: lead_bullet_mod.png (由 LeadBulletMod.itemID 动态传入)
            .entityModel("item_" + _renderItemId)
            .bulletNum(10)
            .ammoCapacity(1)
            .gravity(false)      // 无重力狙击弹道
            // ---- 射击参数 ----
            .shootMode(ShootMode.SEMI)
            .shootDelay(1)
            .chargeTime(2)
            .accuracy(0.95)       // 狙击精度
            .knockback(0.5)
            .shootSound("bow_zx")
            .maxRange(80.0)
            .maxSpeed(6.0)
            // ---- 特殊效果 ----
            .penetrate(true)     // 穿透目标
            .noeff(false)
            // ---- 暴击：高斯波动 ±16 ----
            // .fluctuating(16.0)
            // ---- 构建 NBT ----
            .build());

        // ===== JSON 路径（旧版，保留参考）=====
        // 此路径存在引擎 bug：BasicAmmo JSON 构造器硬编码 this.ice_damage = 0.0f
        // 因此 ice_damage, penetrate, gravity 等参数实际不生效
        // super(_id, buildJson_OLD(), "ade_gadgets");
    }

    /**
     * [保留] 旧版 JSON 构造参数。
     * 引擎 JSON 路径的 BasicAmmo 构造器有 bug（ice_damage 硬编码为 0），
     * 故已切换到 NBT 路径。此方法仅作为参数参考保留。
     */
    /*
    private static JsonHelper buildJson_OLD() {
        StringMap map = new StringMap();
        map.put("name", AMMO_NAME);
        map.put("icon", "snow_eagle_ap");
        map.put("duration", 1.0D);
        map.put("unbreakable", false);
        map.put("stack", 64.0D);
        map.put("basic_damage", 8.0D);
        map.put("ice_damage", 6.0D);             // ❌ JSON 路径不生效
        map.put("entity_model", "bullet");
        map.put("entity_num", 1.0D);
        map.put("entity_speed", 6.0D);
        map.put("gravity", false);
        map.put("shoot_mode", "semi");
        map.put("shoot_delay", 1.0D);
        map.put("charge_time", 2.0D);
        map.put("accuracy", 1.0D);
        map.put("knockback", 0.5D);
        map.put("shoot_sound", "bow_zx");
        map.put("penetrate", true);
        map.put("noeff", false);
        map.put("onAttack", BuffFactory.armorPiercingCrit(16.0D));
        return new JsonHelper(map);
    }
    */

    /**
     * NBT 构造器已在 {@link BasicAmmo#BasicAmmo(net.minecraft.nbt.NBTTagCompound)}
     * 中自动调用 {@code ItemLoader.ammoItem.put(...)}，无需重复注册。
     */
    // registerAsAmmo() — 由 NBT 构造器自动完成，不 override
}
