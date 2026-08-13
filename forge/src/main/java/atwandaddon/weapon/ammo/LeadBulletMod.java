package atwandaddon.weapon.ammo;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import atwandaddon.weapon.nbt.AmmoNbtBuilder;

public class LeadBulletMod extends BasicAmmo {

    public static final String AMMO_NAME = "leadBulletMod";

    /**
     * @param _id             物品数字 ID（逻辑 ID）
     * @param _renderItemId   LeadBulletMod 的 {@code itemID}（shiftedIndex = 逻辑 ID + 256）。
     *                        用于弹幕实体渲染，必须匹配 {@code Item.itemsList} 的索引。
     */
    public LeadBulletMod(int _id, int _renderItemId)
    {
        super(AmmoNbtBuilder.create(_id, AMMO_NAME)
            // ---- 基础属性 ----
            .icon("lead_bullet_mod")
            .description("特质改造的铅弹\\n20%概率附加1s特殊流血效果\\n流血伤害基于武器面板")
            .maxStack(16)
            .unbreakable(true)
            // ---- 伤害 ----
            .basicDamage(1.0)   // 基础伤害（护甲计算）
            .iceDamage(1.0)     // 冰穿甲伤害
            // ---- 弹道 ----
            .entityModel("item_" + _renderItemId)
            .bulletNum(1)       // 弹幕实体数量（1 发 = 1 个弹幕实体）
            .ammoCapacity(1)    // 弹药容量
            .gravity(false)     // 无重力狙击弹道
            // ---- 射击参数 ----
            .shootMode(ShootMode.SEMI)
            .shootDelay(1)      // 射击冷却（tick）
            .chargeTime(10)     // 蓄力时间（tick）
            .accuracy(0.90)     // 精度
            .knockback(0.1)     // 击退
            .shootSound("4ld")
            .maxRange(40.0)     // 最大射程（方块数）
            .maxSpeed(5.0)      // 最大弹丸飞行速度（方块/秒）
            // ---- 特殊效果 ----
            .penetrate(false)   // 穿透目标
            // ---- 击中异常 ----
            .effectWounded(new int[]{30, 40, 0})
            .probWounded(new float[]{0.2f})
            // ---- 构建 NBT ----
            .build());
    }

    public void OnUseing(ItemStack _item, EntityPlayer _player)
    {
        _item.damageItem(1, _player);
    }
}
