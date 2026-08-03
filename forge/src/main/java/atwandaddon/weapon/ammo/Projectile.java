package atwandaddon.weapon.ammo;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import atwandaddon.weapon.nbt.AmmoNbtBuilder;

public class Projectile extends BasicAmmo {

    /** 弹药注册名，供枪械 {@code allowed_ammo} 引用 */
    public static final String AMMO_NAME = "projectile";

    /**
     * @param _id             物品数字 ID（逻辑 ID）
     * @param _renderItemId   LeadBulletMod 的 {@code itemID}（shiftedIndex = 逻辑 ID + 256）。
     *                        用于弹幕实体渲染，必须匹配 {@code Item.itemsList} 的索引。
     */
    public Projectile(int _id, int _renderItemId) {

        super(AmmoNbtBuilder.create(_id, AMMO_NAME)
            .icon("machine_gun_magazine")
            .description("能够冻结敌人同时有概率造成高毁灭性伤害")
            .maxStack(64)
            .basicDamage(8.0)   // 基础伤害（护甲计算）
            .iceDamage(16.0)    // 冰穿甲伤害
            .entityModel("item_" + _renderItemId)
            .bulletNum(1)       // 弹幕实体数量（1 发 = 1 个弹幕实体）
            .ammoCapacity(1)    // 弹药容量
            .gravity(false)     // 无重力弹道
            .shootMode(ShootMode.SEMI)
            .shootDelay(1)      // 射击冷却（tick）
            .chargeTime(40)     // 蓄力时间（tick）
            .accuracy(0.95)     // 精度
            .knockback(0.5)     // 击退
            .shootSound("bow_zx")
            .maxRange(48.0)     // 最大射程（方块数）
            .maxSpeed(6.0)      // 最大弹丸飞行速度（方块/秒）
            .penetrate(true)    // 穿透目标
            .noeff(false)       // 是否无击中效果（true=无击中效果，false=有击中效果）
            .effectWounded(new int[]{2, 40, 3, 11, 40, -2})
            .probWounded(new float[]{1.0f, 0.5f})
            .build());
    }
}