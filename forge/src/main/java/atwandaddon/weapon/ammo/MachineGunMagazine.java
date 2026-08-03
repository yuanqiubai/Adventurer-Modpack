package atwandaddon.weapon.ammo;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import atwandaddon.weapon.nbt.AmmoNbtBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MachineGunMagazine extends BasicAmmo {

    /** 弹药注册名，供枪械 {@code allowed_ammo} 引用 */
    public static final String AMMO_NAME = "machineGunMagazine";

    /**
     * @param _id             物品数字 ID（逻辑 ID）
     * @param _renderItemId   LeadBulletMod 的 {@code itemID}（shiftedIndex = 逻辑 ID + 256）。
     *                        用于弹幕实体渲染，必须匹配 {@code Item.itemsList} 的索引。
     */
    public MachineGunMagazine(int _id, int _renderItemId) {

        super(AmmoNbtBuilder.create(_id, AMMO_NAME)
            .icon("machine_gun_magazine")
            .description("蓝火转轮机枪专用弹夹\\n能够发射出密集弹珠\\n并激发蓝火转轮机枪的过载模式")
            .maxStack(1)
            .basicDamage(5.0)   // 基础伤害（护甲计算）
            .iceDamage(0.1)     // 冰穿甲伤害
            .entityModel("item_" + _renderItemId)
            .bulletNum(3)       // 弹幕实体数量（3 发 = 3 个弹幕实体）
            .ammoCapacity(128)  // 弹药容量（128 发）
            .gravity(false)     // 无重力直线弹道
            .shootMode(ShootMode.AUTO)  // 自动射击模式
            .shootDelay(103)     // 射击间隔（tick），预热加速初始值：(103-3)/1=100tick=5秒满速
            .chargeTime(72000)   // 蓄力时间（tick），设大值配合机枪预热；弹药装填会覆盖枪的此值
            .accuracy(0.75)     // 精度
            .knockback(0.1)     // 击退
            .shootSound("3gt")
            .maxRange(32.0)     // 最大射程（方块数）
            .maxSpeed(6.0)      // 最大弹丸飞行速度（方块/秒）
            .penetrate(false)   // 子弹穿透（命中后销毁，不可贯穿多个目标）
            .noeff(true)        // 禁止击退（true=命中不施加击退，即使 knockback>0）
            .build());
    }

    public void OnUseing(ItemStack _item, EntityPlayer _player)
    {
        _item.damageItem(1, _player);
    }
}

