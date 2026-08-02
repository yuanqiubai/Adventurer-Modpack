package atwandaddon.weapon.gun;

import java.util.Arrays;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

import atwandaddon.entity.EntitySniperBullet;
import atwandaddon.weapon.ammo.SnowEagleAP;
import atwandaddon.weapon.nbt.GunNbtBuilder;
import atwandaddon.weapon.nbt.NbtEventTags;

/**
 * 雪原探索者 — 栓动狙击步枪（充能系统试验品）。
 *
 * <p>充能机制委托给 NBTs-1.0.2 mod 的 ASM 钩子处理：</p>
 * <ul>
 * <li>每枪消耗 1 引擎耐久 → 累计 5 发后 {@code onItemRightClick_Disable} 阻止开枪</li>
 * <li>{@code onUpdate_HealDamageTimer: 130} → 每 6.5 秒自动回 1 充能</li>
 * <li>{@code hitEntity_HealDamage: -1} → 近战枪托攻击回 1 充能</li>
 * </ul>
 */
public class SnowfieldExplorer extends BasicRangedWeapon
{
    public SnowfieldExplorer(int _id)
    {
        // ===== NBT 路径（测试中注释掉）=====
        super(GunNbtBuilder.create(_id, "snowfield_explorer")
            // ---- 基础属性 ----
            .icon("snowfield_explorer")
            .maxStack(1)
            .durability(9)
            .unbreakable(false)
            // ---- 蓄力系统 ----
            .chargeStage(1)
            .chargeTime(4)
            .chargeIcons(Arrays.asList("snowfield_explorer"))
            // ---- 伤害 ----
            .basicDamage(16.0)
            .iceDamage(16.0)
            .knockback(0.1)
            // ---- 射击参数 ----
            .shootMode(ShootMode.SEMI)
            .shootNumber(10)
            .shootDelay(1)
            .accuracy(0.75)
            .shootSound("bow_zx")
            .maxRange(64.0)
            .maxSpeed(6.0)
            // ---- 弹道 ----
            .gravity(false)
            .penetrate(false)
            // ---- 弹药 ----
            .allowedAmmo(Arrays.asList(SnowEagleAP.AMMO_NAME))
            // ---- 构建 NBT ----
            .build());
    }


    // ==================== NBTs 充能系统 ====================

    // ==================== 自定义零散布狙击弹道 ====================

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


        // 计算合并伤害（枪 + 弹药，从 NBT 字段读取而非硬编码）
        float basicDamage = this.basic_damage + ammo.basic_damage;
        float iceDamage   = this.ice_damage   + ammo.ice_damage;

        // 伤害枪械耐久（每发 1 点）
        _item.damageItem(SnowEagleAP.DURABILITY_COST, _player);


        // ===== 新代码 — 多弹丸散弹系统 =====
        // 弹丸数量上限由枪械控制，实际发射数取枪上限与弹药数量的较小值
        // 枪: BasicRangedWeapon.shoot_number (public final int, 来自 NBT shoot_number)
        // 弹药: BasicAmmo.entity_num (public final int, 来自 NBT bullet_num)
        int bulletCount = Math.min(this.shoot_number, ammo.entity_num);

        // 解析弹幕渲染图标（从弹药 entity_model 解析 "item_<id>" 格式）
        // 提取到循环外避免重复解析
        String entityModel = ammo.entity_model;
        int renderItemId = -1; // 弹幕渲染图标 ID，-1 = 无效/不设置
        // [DEBUG] 记录弹药 entity_model 解析
        // System.out.println("[SniperDebug] Ammo entity_model='" + entityModel + "' ammoName=" + ammoName);
        if (entityModel != null && entityModel.startsWith("item_"))
        {
            renderItemId = Integer.parseInt(entityModel.substring(5));
            System.out.println("[SniperDebug] Parsed renderItemID=" + renderItemId
                + " itemExists=" + (net.minecraft.item.Item.itemsList[renderItemId] != null));
        }
        else
        {
            System.out.println("[SniperDebug] WARNING: entity_model is null or doesn't start with 'item_'!");
        }

        // 循环生成弹丸：每颗弹丸独立调用 EntitySniperBullet 构造器，
        // 构造器内的 Math.random() 保证每颗弹丸获得不同的随机散布偏转
        for (int i = 0; i < bulletCount; i++)
        {
            // 创建可配置散布的自定义弹丸（accuracy 从枪械 NBT 配置读取）
            // accuracy = 1.0 → 零散布狙击；accuracy = 0.9 → 小散布（当前配置）
            EntitySniperBullet bullet = new EntitySniperBullet(_world, _player, ammo.entity_speed, this.accuracy);
            bullet.setDamage(basicDamage);
            bullet.setIceDamage(iceDamage);
            bullet.setKnockbackStrength(ammo.knockback);
            bullet.setMaxRange(ammo.maxRange);
            bullet.setGravity(ammo.gravity);
            bullet.setPenetrate(ammo.penetrate);
            bullet.setNoeff(ammo.noeff);

            // 设置弹幕渲染图标
            if (renderItemId >= 0)
            {
                bullet.setRenderItemID(renderItemId);
            }

            // 生成弹丸实体到世界
            _world.spawnEntityInWorld(bullet);
        }

        // 播放射击音效（只播放一次，不在循环内）
        // ⚠️ 引擎 SoundManager.addSound() 会加 "adventurer_engine:" 前缀注册音效，
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
            NbtEventTags.write(_stack, NbtEventTags.PRESET_SNIPER);
        }
    }

}
