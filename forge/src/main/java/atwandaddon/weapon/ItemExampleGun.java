package atwandaddon.weapon;

import java.util.Arrays;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;

import atwandaddon.weapon.nbt.GunNbtBuilder;
import atwandaddon.weapon.nbt.NbtEventTags;

/**
 * 示例枪械 — 使用 {@link GunNbtBuilder}（NBT 构造器）模式。
 *
 * <p>与 {@code SnowfieldExplorer} 同理，绕过引擎 JSON 路径的硬编码 bug
 * （{@code ice_damage=0.0f} 等），直接构造 NBT 传入
 * {@code BasicRangedWeapon(int, NBTTagCompound)}。</p>
 *
 * <p>枪魂机制委托给 NBTs-1.0.2 mod 的 ASM 钩子处理：</p>
 * <ul>
 * <li>每枪消耗 1 引擎耐久 → 累计 6 发后 {@code onItemRightClick_Disable} 阻止开枪</li>
 * <li>{@code onUpdate_HealDamageTimer: 60} → 每 3 秒自动回 1 枪魂</li>
 * <li>{@code hitEntity_HealDamage: -1} → 近战攻击回 1 枪魂</li>
 * </ul>
 */
public class ItemExampleGun extends BasicRangedWeapon
{
    public ItemExampleGun(int _id)
    {
        super(GunNbtBuilder.create(_id, "example_gun")
            // ---- 基础属性 ----
            .icon("example_gun")
            .maxStack(1)
            .durability(100)
            .unbreakable(false)
            // ---- 蓄力系统（无需蓄力）----
            .chargeStage(0)
            .chargeTime(0)
            // ---- 射击参数 ----
            .shootMode(ShootMode.SEMI)
            .shootNumber(1)
            .shootDelay(12)
            .accuracy(0.9)
            .shootSound("random.bow")
            .maxRange(64.0)
            .maxSpeed(3.0)
            // ---- 伤害 ----
            .basicDamage(8.0)
            .iceDamage(0.0)
            .knockback(0.5)
            // ---- 弹道 ----
            .gravity(true)
            .penetrate(false)
            // ---- 弹药 ----
            .allowedAmmo(Arrays.asList(ItemExampleAmmo.AMMO_NAME))
            // ---- 构建 NBT ----
            .build());
    }

    // ==================== NBTs 枪魂系统 ====================

    /**
     * 每 tick 挂载枪魂标签（仅首次写入）。
     *
     * <p>{@code PRESET_PISTOL}: 3 秒回 1 枪魂，6 发耗尽锁枪，近战回 1。</p>
     */
    @Override
    public void onUpdate(ItemStack _stack, World _world, Entity _entity, int _slot, boolean _isCurrent)
    {
        super.onUpdate(_stack, _world, _entity, _slot, _isCurrent);
        if (_entity instanceof EntityPlayer)
        {
            NbtEventTags.write(_stack, NbtEventTags.PRESET_PISTOL);
        }
    }

    // ==================== 散弹 / 自定义弹丸模板 ====================
    //
    // 当需要自定义弹丸实体（散弹、特殊弹道等）时，覆写 onPlayerStoppedUsing。
    // 以下模板来自 SnowfieldExplorer，需按实际需求修改：
    //
    // 【必须自定义的部分】
    //   1. 弹丸实体类 → 替换 EntitySniperBullet 为你的弹丸类
    //   2. 伤害计算   → basicDamage / iceDamage 用 this.basic_damage + ammo.xxx
    //   3. 弹药消耗量 → SnowEagleAP.DURABILITY_COST 改你的 ammo 常量
    //
    // @Override
    // public void onPlayerStoppedUsing(ItemStack _item, World _world, EntityPlayer _player, int _itemInUseCount)
    // {
    //     // 检查是否有已装填的弹药
    //     if (!_item.hasTagCompound() || !_item.getTagCompound().hasKey(CURRENT_AMMO))
    //     {
    //         return;
    //     }
    //
    //     // 从 NBT 读取当前装填的弹药名
    //     String ammoName = _item.getTagCompound().getString(CURRENT_AMMO);
    //     BasicAmmo ammo = (BasicAmmo) BasicAmmo.getAmmoByString(ammoName);
    //     if (ammo == null)
    //     {
    //         return;
    //     }
    //
    //     // 检查玩家背包中是否还有此弹药
    //     if (PlayerTools.ammoAmount(_player, ammoName) <= 0)
    //     {
    //         return;
    //     }
    //
    //     // 仅在服务端生成实体
    //     if (_world.isRemote)
    //     {
    //         return;
    //     }
    //
    //     // 【需自定义】合并伤害：this.basic_damage + ammo.basic_damage
    //     float basicDamage = this.basic_damage + ammo.basic_damage;
    //     float iceDamage   = this.ice_damage   + ammo.ice_damage;
    //
    //     // 【需自定义】伤害枪械耐久（替换为你的弹药耐久消耗常量）
    //     _item.damageItem(1, _player);
    //
    //     // 弹丸数量：取枪上限与弹药数量的较小值
    //     int bulletCount = Math.min(this.shoot_number, ammo.entity_num);
    //
    //     // 解析弹幕渲染图标（从弹药 entity_model 解析 "item_<id>" 格式）
    //     String entityModel = ammo.entity_model;
    //     int renderItemId = -1;
    //     if (entityModel != null && entityModel.startsWith("item_"))
    //     {
    //         renderItemId = Integer.parseInt(entityModel.substring(5));
    //     }
    //
    //     // 循环生成弹丸：每颗独立构造，Math.random() 保证散布偏转不同
    //     for (int i = 0; i < bulletCount; i++)
    //     {
    //         // 【需自定义】替换为你的弹丸实体类
    //         EntitySniperBullet bullet = new EntitySniperBullet(_world, _player,
    //             ammo.entity_speed, this.accuracy);
    //         bullet.setDamage(basicDamage);
    //         bullet.setIceDamage(iceDamage);
    //         bullet.setKnockbackStrength(ammo.knockback);
    //         bullet.setMaxRange(ammo.maxRange);
    //         bullet.setGravity(ammo.gravity);
    //         bullet.setPenetrate(ammo.penetrate);
    //         bullet.setNoeff(ammo.noeff);
    //         if (renderItemId >= 0)
    //         {
    //             bullet.setRenderItemID(renderItemId);
    //         }
    //         _world.spawnEntityInWorld(bullet);
    //     }
    //
    //     // 播放射击音效（只播放一次，不在循环内）
    //     String sound = ammo.shoot_sound != null ? ammo.shoot_sound : this.shoot_sound;
    //     _world.playSoundAtEntity(_player, "adventurer_engine:" + sound, 1.0F, 1.0F);
    //
    //     // 消耗弹药（只消耗一次）
    //     PlayerTools.consumeAmmo(_player, ammoName);
    // }

    // ===== 旧代码 — JSON 版本（保留以作参考）=====
    // private static JsonHelper buildJson() {
    //     StringMap map = new StringMap();
    //     map.put("name", "example_gun");
    //     map.put("icon", "example_gun");
    //     map.put("duration", 100.0D);
    //     map.put("basic_damage", 8.0D);
    //     map.put("charge_time", 0.0D);
    //     map.put("charge_stage", 0.0D);
    //     map.put("shoot_delay", 12.0D);
    //     map.put("shoot_mode", "semi");
    //     map.put("shoot_number", 1.0D);
    //     map.put("accuracy", 0.9D);
    //     map.put("knockback", 0.5D);
    //     map.put("unbreakable", false);
    //     map.put("shoot_sound", "random.bow");
    //     map.put("gravity", true);
    //     map.put("penetrate", false);
    //     map.put("maxRange", 64.0D);
    //     map.put("maxSpeed", 3.0D);
    //     map.put("stack", 1.0D);
    //     map.put("allowed_ammo", Arrays.asList(ItemExampleAmmo.AMMO_NAME));
    //     return new JsonHelper(map);
    // }
    //
    // public ItemExampleGun(int _id) {
    //     super(_id, buildJson(), "ade_gadgets");
    // }
}
