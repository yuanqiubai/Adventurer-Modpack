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
    public static final String AMMO_NAME = "snowEagleAP";

    /** 每发射击消耗的枪魂点数 */
    public static final int COST = 4;

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
            .description("能够冻结敌人同时有概率造成高毁灭性伤害")
            .maxStack(64)
            // ---- 伤害 ----
            .basicDamage(8.0)   // 基础伤害（护甲计算）
            .iceDamage(16.0)     // 冰穿甲伤害
            // ---- 弹道 ----
            // 弹幕实体图标与弹药背包图标分离：
            //   背包图标: snow_eagle_ap.png  (由 .icon("snow_eagle_ap") 控制)
            //   弹幕图标: lead_bullet_mod.png (由 LeadBulletMod.itemID 动态传入)
            .entityModel("item_" + _renderItemId)
            .bulletNum(1)       // 弹幕实体数量（1 发 = 1 个弹幕实体）
            .ammoCapacity(1)    // 弹药容量
            .gravity(false)     // 无重力狙击弹道
            // ---- 射击参数 ----
            .shootMode(ShootMode.SEMI)
            .shootDelay(1)      // 射击冷却（tick）
            .chargeTime(40)     // 蓄力时间（tick）
            .accuracy(0.95)     // 精度
            .knockback(0.5)     // 击退
            .shootSound("4ld")
            .maxRange(48.0)     // 最大射程（方块数）
            .maxSpeed(6.0)      // 最大弹丸飞行速度（方块/秒）
            // ---- 特殊效果 ----
            .penetrate(true)    // 穿透目标
            .noeff(false)       // 是否无击中效果（true=无击中效果，false=有击中效果）
            // ---- 击中异常 ----
            // 格式: {效果ID, 持续tick, 等级, 效果ID, 持续tick, 等级, ...}
            //   2=缓慢, 18=虚弱(抗性降低)
            //   effect_wounded 的效果等级: 0=I, 1=II, ...
            .effectWounded(new int[]{2, 40, 3, 11, 60, -1})
            // 格式: {效果0概率, 效果1概率, ...}  1.0=100%触发
            .probWounded(new float[]{1.0f, 0.5f})
            // ---- 构建 NBT ----
            .build());
    }


    /**
     * NBT 构造器已在 {@link BasicAmmo#BasicAmmo(net.minecraft.nbt.NBTTagCompound)}
     * 中自动调用 {@code ItemLoader.ammoItem.put(...)}，无需重复注册。
     */
    // registerAsAmmo() — 由 NBT 构造器自动完成，不 override
}
