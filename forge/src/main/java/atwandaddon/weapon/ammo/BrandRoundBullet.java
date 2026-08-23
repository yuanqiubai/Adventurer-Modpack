package atwandaddon.weapon.ammo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;

import atwandaddon.weapon.nbt.AmmoNbtBuilder;

public class BrandRoundBullet extends BasicAmmo
{
    /** 弹药注册名，供枪械 {@code allowed_ammo} 引用 */
    public static final String AMMO_NAME = "brandRoundBullet";

    /**
     * @param _id             物品数字 ID（逻辑 ID）
     * @param _renderItemId   LeadBulletMod 的 {@code itemID}（shiftedIndex = 逻辑 ID + 256）。
     *                        用于弹幕实体渲染，必须匹配 {@code Item.itemsList} 的索引。
     */
    public BrandRoundBullet(int _id, int _renderItemId) {

        super(AmmoNbtBuilder.create(_id, AMMO_NAME)
            .icon("brand_round_bullet")
            .description("游骑兵专用弹\\n目标被命中后,施加:12s悬赏·死线-标记\\n标记期间积累伤害池\\n再次开枪引爆悬赏·死线\\n仅累计追猎者专属伤害")
            .maxStack(8)
            .iceDamage(2)       // 冰穿甲伤害
            .entityModel("item_" + _renderItemId)
            .bulletNum(4)       // 弹幕实体数量（4 发 = 4 个弹幕实体）
            .gravity(false)     // 无重力直线弹道
            .shootMode(ShootMode.SEMI)  // 半自动射击模式
            .shootDelay(4)      // 射击间隔（tick）
            .chargeTime(12)     // 蓄力时间（tick）
            .accuracy(0.50)     // 精度
            .shootSound("BOMB")
            .maxRange(12.0)     // 最大射程（方块数）
            .maxSpeed(5.0)      // 最大弹丸飞行速度（方块/秒）
            .penetrate(false)   // 子弹穿透（命中后销毁，不可贯穿多个目标）
            .noeff(true)        // 禁止击退（true=命中不施加击退，即使 knockback>0）
            .effectWounded(new int[]{31, 140, 3})   // buff:悬赏死线
            .probWounded(new float[]{1.0f})         // 概率
            .build());
    }

    public void OnUseing(ItemStack _item, EntityPlayer _player)
    {
        _item.damageItem(1, _player);
    }
}
