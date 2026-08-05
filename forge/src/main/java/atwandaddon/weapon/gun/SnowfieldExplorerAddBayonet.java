package atwandaddon.weapon.gun;

import java.util.Arrays;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.items.blank.BasicItem;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

import atwandaddon.ATcraftAddon;
import atwandaddon.entity.EntitySniperBullet;
import atwandaddon.weapon.ammo.MachineGunMagazine;
import atwandaddon.weapon.ammo.SnowEagleAP;
import atwandaddon.weapon.melee.Bayonet;
import atwandaddon.weapon.nbt.GunNbtBuilder;
import atwandaddon.weapon.nbt.NbtEventTags;

public class SnowfieldExplorerAddBayonet extends BasicRangedWeapon
{
    public SnowfieldExplorerAddBayonet(int _id)
    {
        super(GunNbtBuilder.create(_id, "snowfieldExplorerAddBayonet")
            // ---- 基础属性 ----
            .description("轨道炮\\n可造成毁灭性伤害,但不具备枪魂且枪身沉重\\n消耗特殊子弹时,会大幅消耗能量\\n每次用尽能量都需要长时间等待恢复\\n装备枪魂配件:刺刀后,获得一定的近战能力")
            .icon("snowfield_explorer_1")
            .maxStack(1)        // 最大堆叠数
            .durability(16)     // 耐久
            .unbreakable(false) // 是否无耐久
            .chargeStage(3)     // 1=单阶段蓄力，2=双阶段蓄力
            .chargeTime(20)     // 蓄力时间（tick）
            .chargeIcons(Arrays.asList("snowfield_explorer_1"))
            .basicDamage(5.0)   // 基础伤害（护甲计算）
            .iceDamage(40.0)    // 冰穿甲伤害
            .knockback(0.1)     // 击退
            .shootMode(ShootMode.SEMI)
            .shootNumber(1)     // 最大发射的弹丸数（由弹药 entity_num 决定实际数量）
            .shootDelay(10)     // 射击间隔（tick）
            .accuracy(0.95)     // 精度（0.0~1.0，越高越准）
            .shootSound("bow_zx")
            .maxRange(48.0)     // 最大射程（方块数）
            .maxSpeed(6.0)      // 最大弹丸飞行速度（方块/秒）
            .gravity(false)
            .penetrate(false)
            .allowedAmmo(Arrays.asList(
                SnowEagleAP.AMMO_NAME,
                 "weapon_12021",
                ((BasicItem) Item.itemsList[12294]).internal_name))
            // ---- 构建 NBT ----
            .build());
    }

     /**
     * 覆写引擎的松手开火方法，用零散布 {@link EntitySniperBullet} 替换默认弹丸。
     *
     * <p>引擎 {@code shootOnce()} 经过 {@code disturbVector} + 自定义 {@code c()} 方法
     * 多层精度处理，实测导致弹道严重偏离目标。此覆写完全绕过引擎精度系统：</p>
     * <ol>
     * <li>检查弹药是否加载、玩家是否持有足够弹药</li>
     * <li>检查蓄力是否完成（use_duration >= charge_time）</li>
     * <li>伤害枪械耐久</li>
     * <li>创建 {@link EntitySniperBullet} — 纯 vanilla 方向数学，零散布</li>
     * <li>消耗弹药、播放音效、设置冷却</li>
     * </ol>
     *
     * @param _item   枪械 ItemStack
     * @param _world  当前世界
     * @param _player 射击者
     * @param _uc2    蓄力倒计时（引擎透传参数）
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack _item, World _world, EntityPlayer _player, int _itemInUseCount)
    {
        // 检查是否有已装填的弹药
        if (!_item.hasTagCompound() || !_item.getTagCompound().hasKey(CURRENT_AMMO))
        {
            return;
        }

        // 从 NBT 读取当前装填的弹药名
        String ammoName = _item.getTagCompound().getString(CURRENT_AMMO);
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        if (ammo == null)
        {
            return;
        }

        // 检查玩家背包中是否还有此弹药
        if (PlayerTools.ammoAmount(_player, ammoName) <= 0)
        {
            return;
        }

        // 仅在服务端生成实体
        if (_world.isRemote)
        {
            return;
        }

        // ===== 蓄力倍率计算（复现引擎 shootOnce 中的公式）=====
        int maxUseDuration = _item.getMaxItemUseDuration();
        int elapsedTicks = maxUseDuration - _itemInUseCount;
        float chargeRatio = 1.0F;
        if (this.charge_time > 1 && this.charge_stage > 0)
        {
            int chargeStage = Math.min(
                (int)Math.floor((double)elapsedTicks / (double)this.charge_time * (double)this.charge_stage),
                this.charge_stage
            );
            chargeRatio = (float)chargeStage / (float)this.charge_stage;
        }
        // 蓄力不足时不发射
        if (chargeRatio <= 0.0F)
        {
            return;
        }

        // 计算合并伤害（枪 + 弹药，从 NBT 字段读取而非硬编码）
        float basicDamage = this.basic_damage + ammo.basic_damage;
        float iceDamage   = this.ice_damage   + ammo.ice_damage;

        // 应用蓄力倍率到伤害和弹速
        basicDamage *= chargeRatio;
        iceDamage   *= chargeRatio;
        float bulletSpeed = ammo.entity_speed * chargeRatio;


        if(ammo.internal_name.equals(SnowEagleAP.AMMO_NAME))
        {   
            int remain = _item.getMaxDamage() - _item.getItemDamage();

            float multiplier = CriticalDamageMultiplier();
            basicDamage *= multiplier;
            iceDamage   *= multiplier;

            // 伤害枪械耐久（每发 1 点）
            _item.damageItem(SnowEagleAP.COST, _player);
        }
        else
        {
            // 伤害枪械耐久（每发 1 点）
            _item.damageItem(1, _player);
        }


        // 弹丸数量 = shoot_number × entity_num（复现引擎双循环：外层 shoot_number，内层 entity_num）
        int bulletCount = this.shoot_number * ammo.entity_num;

        // 解析弹幕渲染图标（从弹药 entity_model 解析 "item_<id>" 格式）
        // 提取到循环外避免重复解析
        String entityModel = ammo.entity_model;
        int renderItemId = -1; // 弹幕渲染图标 ID，-1 = 无效/不设置
       
        if (entityModel != null && entityModel.startsWith("item_"))
        {
            renderItemId = Integer.parseInt(entityModel.substring(5));
        }

        // 循环生成弹丸：每颗弹丸独立调用 EntitySniperBullet 构造器，
        // 构造器内的 Math.random() 保证每颗弹丸获得不同的随机散布偏转
        for (int i = 0; i < bulletCount; i++)
        {
            // 创建可配置散布的自定义弹丸（accuracy 从枪械 NBT 配置读取）
            // accuracy = 1.0 → 零散布狙击；accuracy = 0.9 → 小散布（当前配置）
            EntitySniperBullet bullet = new EntitySniperBullet(_world, _player, bulletSpeed, this.accuracy);
            bullet.setDamage(basicDamage);
            bullet.setIceDamage(iceDamage);
            bullet.setKnockbackStrength(ammo.knockback);
            bullet.setMaxRange(ammo.maxRange);
            bullet.setGravity(ammo.gravity);
            bullet.setPenetrate(ammo.penetrate);
            bullet.setNoeff(ammo.noeff);

            // 注入弹药 DamagePacket，使击中异常效果（effect_wounded 等）生效
            // 引擎 EntityBulletGeneral.onUpdate() 在命中时调用 dmgPack.causeDamage()
            DamagePacket dmgPacket = ammo.onAttack;
                      
            if (dmgPacket != null)
            {
                bullet.setDamagePacket(dmgPacket);
            }

            // 设置弹幕渲染图标
            if (renderItemId >= 0)
            {
                bullet.setRenderItemID(renderItemId);
            }

            // 生成弹丸实体到世界
            _world.spawnEntityInWorld(bullet);
        }

        // 播放射击音效（只播放一次，不在循环内）
        // 引擎 SoundManager.addSound() 会加 "adventurer_engine:" 前缀注册音效，
        // 因此 playSoundAtEntity 也需要传入带域名的完整音效名。
        String sound = ammo.shoot_sound != null ? ammo.shoot_sound : "bow_zx";
        _world.playSoundAtEntity(_player, "adventurer_engine:" + sound, 1.0F, 1.0F);

        // 消耗弹药（只消耗一次，不在循环内）
        // 注：shoot_delay 冷却由引擎 EventLoader 每 tick 管理，此处无需手动设置
        PlayerTools.consumeAmmo(_player, ammoName);
    }

    @Override
    public void onUpdate(ItemStack _stack, World _world, Entity _entity, int _slot, boolean _isCurrent)
    {
        super.onUpdate(_stack, _world, _entity, _slot, _isCurrent);
        if (_entity instanceof EntityPlayer)
        {
            NbtEventTags.write(_stack, NbtEventTags.PRESET_RAILGUN);
        }
    }

    /**
     * 刺刀近战攻击。
     *
     * <p>伤害数值从 {@link atwandaddon.weapon.melee.Bayonet} 实例动态读取。
     * 修改 {@code Bayonet.java} 中的 {@code basicDamage/iceDamage} 即可调整刺刀伤害，
     * 枪械无需改动。</p>
     *
     * @param _item     枪械 ItemStack
     * @param _target   被攻击实体
     * @param _attacker 攻击者
     * @return {@code true} 表示攻击已处理
     */
    // @Override
    // public boolean hitEntity(ItemStack _item, EntityLivingBase _target, EntityLivingBase _attacker)
    // {
    //     // 从刺刀配件实例动态读取伤害值
    //     Bayonet bayonetItem = ATcraftAddon.bayonet;
    //     float physDmg = bayonetItem.basic_damage;  // 物理伤害（受护甲减免）
    //     float iceDmg  = bayonetItem.ice_damage;    // 冰穿甲伤害（无视护甲）

    //     // 物理伤害 - 走原版全套减伤链
    //     if (physDmg > 0.0F && _attacker instanceof EntityPlayer)
    //     {
    //         _target.attackEntityFrom(
    //             DamageSource.causePlayerDamage((EntityPlayer)_attacker), physDmg);
    //     }

    //     // 冰穿甲伤害 - 自定义 DamageSource，引擎可能跳过护甲
    //     if (iceDmg > 0.0F)
    //     {
    //         _target.attackEntityFrom(
    //             new EntityDamageSource("ice_damage", _attacker), iceDmg);
    //     }

    //     return true;
    // }


    public float CriticalDamageMultiplier()
    {
        if(Math.random() < 0.5) // 50% 概率触发暴击
        {
            if(Math.random() < 0.5)
            {
                return 2.0f;     // 暴击伤害倍率
            }
            return 1.75f;        // 暴击伤害倍率
        }
        return 1.0f;             // 非暴击：正常伤害
    }

}
