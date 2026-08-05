package atwandaddon.weapon.gun;

import java.util.Arrays;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.items.blank.BasicItem;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;

import atwandaddon.entity.EntitySniperBullet;
import atwandaddon.weapon.nbt.GunNbtBuilder;
import atwandaddon.weapon.nbt.NbtEventTags;

/**
 * 栓动式步枪 — 猎魂·A（开启模式变体）。
 *
 * <h2>核心定位</h2>
 * <p>{@link BoltActionRifle} 的强化版本。在开启模式下作战能力大幅提升，
 * 但会增加枪魂消耗。其余弹道特性与基础版一致。</p>
 *
 * <h2>与基础版差异</h2>
 * <ul>
 *   <li>开启模式：伤害 ↑ / 枪魂消耗 ↑</li>
 *   <li>弹种/弹道/蓄力参数完全相同</li>
 * </ul>
 *
 * <h2>职责边界</h2>
 * <ul>
 *   <li>本类仅负责开火逻辑和弹幕生成</li>
 *   <li>枪魂 NBT 标签读写由 NBTs mod 的 ASM 钩子处理</li>
 *   <li>开启模式的额外逻辑由上层触发，不在本类中实现</li>
 * </ul>
 *
 * @see BoltActionRifle 基础版（无枪魂消耗）
 * @see EntitySniperBullet 零散布弹丸
 */
public class BoltActionRifle_2 extends BasicRangedWeapon
{
    // ================================================================
    //  构造器
    // ================================================================

    /**
     * 通过 {@link GunNbtBuilder} 构建枪械 NBT 并传入引擎构造器。
     *
     * @param _id 物品数字 ID，通过 {@code GameRegistry.registerItem} 注册
     */
    public BoltActionRifle_2(int _id)
    {
        super(GunNbtBuilder.create(_id, "boltActionRifle_2")
            // ---- 基础属性 ----
            .description("步枪\\n支持的弹种较少,但单发威力较高\\n同时具有极其优秀射程的步枪\\n装配了枪魂配件:刺刀\\n提升了作战能力且能够近战恢复枪魂\\n在开启模式下,作战能力大幅提升,但会增加枪魂消耗")
            .icon("bolt_action_rifle_2")
            .maxStack(1)
            .durability(13)
            .unbreakable(false)
            // ---- 蓄力系统 ----
            .chargeStage(5)
            .chargeTime(0)       // 0 = 瞬发无蓄力
            .chargeIcons(Arrays.asList("bolt_action_rifle_2"))
            // ---- 伤害 ----
            .basicDamage(2.0)
            .iceDamage(18.0)
            .knockback(0.0)
            // ---- 射击参数 ----
            .shootMode(ShootMode.SEMI)
            .shootNumber(1)
            .shootDelay(10)
            .accuracy(0.95)
            .shootSound("bow_zx")
            .maxRange(48.0)
            .maxSpeed(6.0)
            // ---- 弹道 ----
            .gravity(false)
            .penetrate(false)
            // ---- 弹药 ----
            .allowedAmmo(Arrays.asList(
                ((BasicItem) Item.itemsList[12273]).internal_name,  // 铅弹
                ((BasicItem) Item.itemsList[12277]).internal_name,  // 穿甲弹
                ((BasicItem) Item.itemsList[12293]).internal_name)) // 狙击弹
            // ---- 命中效果：流血 DOT (PotionBleed, ID=30, 100% 触发, 10秒) ----
            .effectWounded(new int[]{30, 200, 5})
            .probWounded(new float[]{1.0F})
            .build());
    }

    // ================================================================
    //  数据容器 — 蓄力计算结果传递
    // ================================================================

    /**
     * 蓄力计算结果容器。
     *
     * <p>用于在 {@link #accumulatingPower} 与 {@link #projectileGeneration}
     * 之间传递所有计算值，避免使用类级可变字段或返回多值数组。</p>
     */
    private static class PowerResult
    {
        /** 合并后的基础伤害（枪 + 弹药 × 蓄力倍率） */
        float basicDamage;
        /** 合并后的冰穿甲伤害 */
        float iceDamage;
        /** 弹丸飞行速度（弹药基础速度 × 蓄力倍率） */
        float bulletSpeed;
        /** 弹丸总数 = shoot_number × entity_num */
        int   bulletCount;
        /** 弹幕渲染图标 ID，-1 表示无自定义图标 */
        int   renderItemId;
    }


    // ================================================================
    //  生命周期 — 覆写引擎方法
    // ================================================================

    /**
     * 松手开火回调。
     *
     * <p>引擎在玩家松开右键时调用此方法。本覆写完全绕过引擎的 {@code shootOnce()}
     * 精度系统，改用 {@link EntitySniperBullet} 实现零散布狙击弹道。</p>
     *
     * <p>流程：校验弹药 → 仅服务端 → 蓄力计算 → 弹幕生成。</p>
     *
     * @param _item           枪械 ItemStack
     * @param _world          当前世界
     * @param _player         射击者
     * @param _itemInUseCount 蓄力倒计时，由引擎透传
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack _item, World _world,
                                     EntityPlayer _player, int _itemInUseCount)
    {
        // ---- 校验弹药状态 ----
        if (!_item.hasTagCompound() || !_item.getTagCompound().hasKey(CURRENT_AMMO))
        {
            return; // 无 NBT 或未装填弹药
        }

        String ammoName = _item.getTagCompound().getString(CURRENT_AMMO);
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        if (ammo == null)
        {
            return; // 弹药名无效（未注册或已移除）
        }

        if (PlayerTools.ammoAmount(_player, ammoName) <= 0)
        {
            return; // 背包中弹药耗尽
        }

        // ---- 仅服务端执行 ----
        if (_world.isRemote)
        {
            return;
        }

        // ---- 蓄力 → 伤害计算 → 耐久消耗 ----
        PowerResult power = accumulatingPower(_item, _itemInUseCount, _player, ammo);
        if (power == null)
        {
            return; // 蓄力不足，不发射
        }

        // ---- 弹幕生成 → 音效 → 弹药消耗 ----
        projectileGeneration(_world, _player, ammo, ammoName, power, _item);
    }

    // ================================================================
    //  开火流程 — 步骤 1：蓄力计算
    // ================================================================

    /**
     * 蓄力处理。
     *
     * <p>计算蓄力倍率 → 合并枪械与弹药伤害 → 应用倍率 → 消耗枪械耐久。
     * 蓄力倍率公式复现引擎 {@code BasicRangedWeapon.shootOnce()} 中的计算逻辑。</p>
     *
     * <p>职责边界：仅负责数值计算和耐久消耗，不涉及弹幕生成或音效播放。</p>
     *
     * @param _item           枪械 ItemStack，用于读取最大使用时长和消耗耐久
     * @param _itemInUseCount 蓄力倒计时，引擎透传
     * @param _player         射击者，用于耐久消耗
     * @param _ammo           当前装填的弹药，用于伤害合并和弹速计算
     * @return 蓄力计算结果；蓄力不足时返回 {@code null}，调用方应中止发射
     */
    private PowerResult accumulatingPower(ItemStack _item, int _itemInUseCount, EntityPlayer _player, BasicAmmo _ammo)
    {
        // ---- 蓄力倍率 ----
        int maxUseDuration = _item.getMaxItemUseDuration();
        int elapsedTicks = maxUseDuration - _itemInUseCount;
        float chargeRatio = 1.0F;
        if (this.charge_time > 1 && this.charge_stage > 0)
        {
            int chargeStage = Math.min(
                (int)Math.floor(
                    (double)elapsedTicks / (double)this.charge_time * (double)this.charge_stage),
                this.charge_stage);
            chargeRatio = (float)chargeStage / (float)this.charge_stage;
        }

        if (chargeRatio <= 0.0F)
        {
            return null; // 蓄力未完成，不发射
        }

        // ---- 伤害合并 + 蓄力倍率应用 ----
        PowerResult result = new PowerResult();
        result.basicDamage = this.basic_damage + _ammo.basic_damage;
        result.iceDamage   = this.ice_damage   + _ammo.ice_damage;

        result.basicDamage *= chargeRatio;
        result.iceDamage   *= chargeRatio;
        result.bulletSpeed  = _ammo.entity_speed * chargeRatio;

        // ---- 耐久消耗 ----
        _item.damageItem(1, _player);

        // ---- 弹丸数量 = shoot_number × entity_num ----
        result.bulletCount = this.shoot_number * _ammo.entity_num;

        // ---- 解析弹幕渲染图标 ----
        String entityModel = _ammo.entity_model;
        result.renderItemId = -1;
        if (entityModel != null && entityModel.startsWith("item_"))
        {
            result.renderItemId = Integer.parseInt(entityModel.substring(5));
        }

        return result;
    }

    // ================================================================
    //  开火流程 — 步骤 2：弹幕生成
    // ================================================================

    /**
     * 弹幕实体生成。
     *
     * <p>循环创建 {@link EntitySniperBullet} 实体 → 注入伤害和
     * {@link DamagePacket} → 设置渲染图标 → 生成到世界。</p>
     *
     * <p>播放射击音效和消耗弹药在循环外执行（各仅一次）。</p>
     *
     * <p>职责边界：仅负责实体创建和世界生成，不涉及伤害计算或蓄力逻辑。</p>
     *
     * @param _world    当前世界
     * @param _player   射击者
     * @param _ammo     当前弹药实例，用于读取击退/射程/穿透等弹道属性
     * @param _ammoName 弹药内部名，用于 {@link PlayerTools#consumeAmmo} 消耗
     * @param _power    蓄力计算结果，包含已合并的伤害/弹速/弹数等
     */
    private void projectileGeneration(World _world, EntityPlayer _player,
                                      BasicAmmo _ammo, String _ammoName,
                                      PowerResult _power, ItemStack _weaponItem)
    {
        int totalBullets = _power.bulletCount;

        // ---- 循环生成弹丸 ----
        for (int i = 0; i < totalBullets; i++)
        {
            EntitySniperBullet bullet = new EntitySniperBullet(
                _world, _player, _power.bulletSpeed, this.accuracy);

            // 注入伤害值
            bullet.setDamage(_power.basicDamage);
            bullet.setIceDamage(_power.iceDamage);

            // 注入弹药弹道属性
            bullet.setKnockbackStrength(_ammo.knockback);
            bullet.setMaxRange(_ammo.maxRange);
            bullet.setGravity(_ammo.gravity);
            bullet.setPenetrate(_ammo.penetrate);
            bullet.setNoeff(_ammo.noeff);

            // 注入弹药命中效果
            DamagePacket dmgPacket = _ammo.onAttack;
            if (dmgPacket != null)
            {
                bullet.setDamagePacket(dmgPacket);
            }

            // 注入枪械命中效果（effect_wounded / 流血 DOT 等）
            // 引擎 shootOnce() 被绕过，需手动传递枪械的 DamagePacket
            if (this.onAttack != null)
            {
                bullet.setDamagePacket(this.onAttack);
            }

            // 设置弹幕渲染图标
            if (_power.renderItemId >= 0)
            {
                bullet.setRenderItemID(_power.renderItemId);
            }

            _world.spawnEntityInWorld(bullet);

            // ===== 旧代码 (保留以作参考) =====
            // 注入武器面板伤害到 PotionBleed（含 NBT 属性 + 护甲加成，按 Operation 应用）
            // NbtAttributeHelper.Result nbtBonus = NbtAttributeHelper.readAll(_weaponItem, _player);
            // float finalBasic = nbtBonus.bowPower.applyTo(_power.basicDamage);
            // float finalIce   = nbtBonus.icePower.applyTo(_power.iceDamage);
            // float finalWand  = nbtBonus.wandDamage.applyTo(0F);
            // float totalPanel = finalBasic + finalIce + finalWand;
            // PotionBleed.pendingWeaponDamage = totalPanel;
            // FMLLog.info(...);
            // ===== 新代码 =====
            // BuffMonitor 通过 LivingHurtEvent 自动捕获实际承受伤害，
            // 无需武器端手动传递面板伤害给 PotionBleed。
            // PotionBleed.performEffect() 通过 BuffMonitor.consumeDamage() 读取。
        }

        // ---- 音效 + 弹药消耗（循环外，仅一次）----
        String sound = _ammo.shoot_sound != null ? _ammo.shoot_sound : "bow_zx";
        _world.playSoundAtEntity(_player, "adventurer_engine:" + sound, 1.0F, 1.0F);

        PlayerTools.consumeAmmo(_player, _ammoName);
    }

    @Override
    public void onUpdate(ItemStack _stack, World _world, Entity _entity,
                         int _slot, boolean _isCurrent)
    {
        super.onUpdate(_stack, _world, _entity, _slot, _isCurrent);
        if (_entity instanceof EntityPlayer)
        {
            NbtEventTags.write(_stack, NbtEventTags.PRESET_SNIPER_ADD_BAYONET);
        }
    }
}
