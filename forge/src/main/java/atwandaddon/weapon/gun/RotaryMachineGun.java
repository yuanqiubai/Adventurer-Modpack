package atwandaddon.weapon.gun;

import java.util.Arrays;

import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;

import atwandaddon.entity.EntitySniperBullet;
import atwandaddon.weapon.ammo.MachineGunMagazine;
import atwandaddon.weapon.ammo.Projectile;
import atwandaddon.weapon.ammo.SnowEagleAP;
import atwandaddon.weapon.nbt.GunNbtBuilder;
import atwandaddon.weapon.nbt.NbtEventTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RotaryMachineGun extends BasicRangedWeapon
{
    /** NBT key：上次开火时的世界时间（tick），用于自管冷却 */
    private static final String TAG_LAST_FIRE = "rmg_lastFire";
    /** NBT key：当前有效射击间隔（tick），预热加速机制的核心变量 */
    private static final String TAG_CURRENT_DELAY = "rmg_curDelay";
    /** 预热加速：初始射击间隔（tick），首发约 1.0 秒 */
    private static final int INITIAL_DELAY = 20;
    /** 预热加速：每次开火后减少的射击间隔（仅在开火时递减，实现线性加速） */
    private static final int DELAY_DECREASE = 2;
    /** 预热加速：最小射击间隔（射速上限），2 tick = 0.1 秒/发 */
    private static final int DELAY_MIN = 2;

    /** Debug：计数器，每 20 tick 打印一次日志避免刷屏 */
    private int debugTick = 0;

    public RotaryMachineGun(int _id)
    {
        super(GunNbtBuilder.create(_id, "rotaryMachineGun")
            .description("重火器\\n可进行超高速射击的机枪,但不具备枪魂\\n持续射击会降低耐久,耐久过低时无法使用\\n随时间流逝枪管会降温")
            .icon("rotary_machine_gun")
            .maxStack(1)                        // 最大堆叠数
            .durability(160)                    // 耐久
            .unbreakable(false)                 // 是否无耐久
            .chargeStage(1)                     // 1=单阶段蓄力（≥1 时渲染才不会 NPE；icons 必填见 pitfall#9）
            .chargeTime(72000)                  // 蓄力时间（tick），设大值让玩家能无限按住右键
            .chargeIcons(Arrays.asList("rotary_machine_gun"))  // 蓄力图标（chargeStage≥1 时必填，见 pitfall#9）

            .basicDamage(5.0)                   // 基础伤害（护甲计算）
            .iceDamage(1)                       // 冰穿甲伤害

            .knockback(0.1)                     // 击退
            .shootMode(ShootMode.AUTO)          // 自动射击模式
            .shootNumber(1)                     // 最大发射的弹丸数
            .shootDelay(103)                    // 射击间隔（tick），预热加速初始值：(103-3)/1=100tick=5秒满速
            .accuracy(0.75)                     // 精度（0.0~1.0，越高越准）
            .shootSound("bow_zx")               // 射击音效
            .maxRange(28.0)                     // 最大射程（方块数）
            .maxSpeed(6.0)                      // 最大弹丸飞行速度（方块/秒）
            .gravity(false)                     // 弹道重力（true=受重力影响，false=无重力直线弹道）
            .penetrate(false)                   // 子弹穿透（命中后销毁，不可贯穿多个目标）
            .allowedAmmo(Arrays.asList(
                MachineGunMagazine.AMMO_NAME))
            .build());
    }

    // ==================== onUpdate — 根据弹药类型分发开火模式 ====================

    /**
     * 每 tick 调度：共通设置 → 按弹药类型路由到蓝火模式或引擎标准 AUTO。
     *
     * <p>蓝火模式（{@link MachineGunMagazine}）：自管冷却 + 线性预热加速，见 {@link #handleBlueFire}</p>
     * <p>其他弹药（如 {@link Projectile}）：委托 {@code super.onUpdate()} 走引擎标准全自动</p>
     */
    @Override
    public void onUpdate(ItemStack _stack, World _world, Entity _entity, int _slot, boolean _isCurrent)
    {
        if (!(_entity instanceof EntityPlayer))
        {
            return;
        }
        EntityPlayer player = (EntityPlayer)_entity;

        // 确保 NBT 存在
        if (!_stack.hasTagCompound())
        {
            _stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = _stack.getTagCompound();

        // 首次写入 NBTs 枪魂标签，然后强制覆盖禁用阈值为极大值
        // ——防止 NBTs mod 在 damage≥140 时拦截右键，导致 player.getItemInUse() 永远为 null
        NbtEventTags.write(_stack, NbtEventTags.PRESET_MACHINEGUN);
        nbt.setInteger(NbtEventTags.TAG_DISABLE_THRESHOLD, Integer.MAX_VALUE);
        nbt.setInteger(NbtEventTags.TAG_RIGHT_CLICK_DUR, 0);

        // ===== Debug：每 20 tick 打印一次状态 =====
        debugTick++;
        if (debugTick % 20 == 0)
        {
            boolean isUsing = player.getItemInUse() == _stack;
            String ammoTag = nbt.hasKey(CURRENT_AMMO) ? nbt.getString(CURRENT_AMMO) : "none";
            int curDelay = nbt.hasKey(TAG_CURRENT_DELAY) ? nbt.getInteger(TAG_CURRENT_DELAY) : -1;
            System.out.println("[RMG] tick=" + debugTick
                + " isUsing=" + isUsing
                + " remote=" + _world.isRemote
                + " ammo=" + ammoTag
                + " delay=" + curDelay);
        }

        // 仅在服务端处理（客户端提前返回不修改 NBT）
        if (_world.isRemote)
        {
            return;
        }

        // ===== 未在使用时清理蓝火预热标签，防止切物品导致 NBT 残留 =====
        // onPlayerStoppedUsing 只在松手时调用；切物品/死亡时不会触发，
        // 导致下次使用时 now - oldLastFire 巨大 → 每 tick 开火 → "秒满速" bug
        if (player.getItemInUse() != _stack)
        {
            if (nbt.hasKey(TAG_LAST_FIRE))
            {
                nbt.removeTag(TAG_LAST_FIRE);
                nbt.removeTag(TAG_CURRENT_DELAY);
            }
            return;
        }

        // 弹药检查
        if (!nbt.hasKey(CURRENT_AMMO))
        {
            return;
        }
        String ammoName = nbt.getString(CURRENT_AMMO);
        BasicAmmo ammo = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
        if (ammo == null)
        {
            return;
        }
        if (PlayerTools.ammoAmount(player, ammoName) <= 0)
        {
            return;
        }

        // ===== 按弹药类型分发开火模式 =====
        if (MachineGunMagazine.AMMO_NAME.equals(ammoName))
        {
            // 蓝火模式：自管冷却 + 线性预热加速
            handleBlueFire(_stack, _world, player, nbt, ammo);
        }
        else
        {
            // 其他弹药（如弹丸）：走引擎标准 AUTO 模式
            super.onUpdate(_stack, _world, _entity, _slot, _isCurrent);
        }
    }

    // ==================== 蓝火模式：线性预热加速全自动 ====================

    /**
     * 蓝火模式核心逻辑：自管冷却（不依赖 {@code player.shoot_delay}）+ 线性预热加速。
     *
     * <p>预热机制：</p>
     * <ol>
     * <li>首发由引擎 {@code handleShoot()} 处理（本 tick 初始化后 return）</li>
     * <li>之后间隔 = {@link #TAG_CURRENT_DELAY}，每次开火后减去 {@link #DELAY_DECREASE}</li>
     * <li>间隔从 {@link #INITIAL_DELAY}(20t=1.0s) 线性递减至 {@link #DELAY_MIN}(2t=0.1s)</li>
     * <li>加速阶段约 9 发，总耗时约 5.4 秒 → 满速 10 发/秒</li>
     * </ol>
     *
     * @param _stack  武器物品堆
     * @param _world  世界
     * @param _player 持有者
     * @param _nbt    物品 NBT（已确保存在）
     * @param _ammo   当前弹药实例
     */
    private void handleBlueFire(ItemStack _stack, World _world, EntityPlayer _player,
                                 NBTTagCompound _nbt, BasicAmmo _ammo)
    {
        String ammoName = _ammo.internal_name;
        long now = _world.getTotalWorldTime();

        // 首次进入"使用"状态时初始化时间戳，防止 lastFire=0 导致瞬间开火
        // 同时也处理 NBT 残留问题：若 lastFire 过于陈旧（> INITIAL_DELAY*2），
        // 说明上一次使用的标签未被正常清理（切物品/死亡等场景），强制重新初始化
        if (!_nbt.hasKey(TAG_LAST_FIRE) || (now - _nbt.getLong(TAG_LAST_FIRE)) > (long)(INITIAL_DELAY * 2))
        {
            _nbt.setLong(TAG_LAST_FIRE, now);
            _nbt.setInteger(TAG_CURRENT_DELAY, INITIAL_DELAY);
            System.out.println("[RMG BlueFire] Init fresh cycle — lastFire=" + now + " curDelay=" + INITIAL_DELAY);
            return; // 本 tick 交给引擎 handleShoot() 处理第一发
        }

        // 自管冷却：通过世界时间戳判断是否到达下一发间隔
        int curDelay = _nbt.hasKey(TAG_CURRENT_DELAY)
            ? _nbt.getInteger(TAG_CURRENT_DELAY)
            : INITIAL_DELAY;
        long lastFire = _nbt.getLong(TAG_LAST_FIRE);
        if (now - lastFire < (long)curDelay)
        {
            return; // 冷却中，跳过本 tick
        }

        // 耐久保护：损坏 ≥ 140 禁止开火（兜底守卫，防 TAG_DISABLE_THRESHOLD 漏检）
        if (_stack.getItemDamage() >= 140)
        {
            return;
        }

        // 伤害计算
        float basicDamage = this.basic_damage + _ammo.basic_damage;
        float iceDamage   = this.ice_damage   + _ammo.ice_damage;

        // 扣减枪魂值
        _stack.damageItem(1, _player);

        // ===== 散弹系统 =====
        int bulletCount = this.shoot_number * _ammo.entity_num;

        String entityModel = _ammo.entity_model;
        int renderItemId = -1;
        if (entityModel != null && entityModel.startsWith("item_"))
        {
            renderItemId = Integer.parseInt(entityModel.substring(5));
        }

        for (int i = 0; i < bulletCount; i++)
        {
            EntitySniperBullet bullet = new EntitySniperBullet(_world, _player, _ammo.entity_speed, this.accuracy);
            bullet.setDamage(basicDamage);
            bullet.setIceDamage(iceDamage);
            bullet.setKnockbackStrength(_ammo.knockback);
            bullet.setMaxRange(_ammo.maxRange);
            bullet.setGravity(_ammo.gravity);
            bullet.setPenetrate(_ammo.penetrate);
            bullet.setNoeff(_ammo.noeff);

            if (_ammo.onAttack != null)
            {
                bullet.setDamagePacket(_ammo.onAttack);
            }

            if (renderItemId >= 0)
            {
                bullet.setRenderItemID(renderItemId);
            }

            _world.spawnEntityInWorld(bullet);
        }

        // 播放射击音效
        String sound = _ammo.shoot_sound != null ? _ammo.shoot_sound : "bow_zx";
        _world.playSoundAtEntity(_player, "adventurer_engine:" + sound, 1.0F, 1.0F);

        // 消耗弹药 + 更新冷却时间戳 + 递减预热间隔
        PlayerTools.consumeAmmo(_player, ammoName);
        _nbt.setLong(TAG_LAST_FIRE, now);
        int newDelay = Math.max(DELAY_MIN, curDelay - DELAY_DECREASE);
        _nbt.setInteger(TAG_CURRENT_DELAY, newDelay);

        System.out.println("[RMG BlueFire] FIRE! delay=" + curDelay + "->" + newDelay
            + " bullets=" + bulletCount + " ammo=" + ammoName);
    }

    // ==================== 松手重置预热加速 ====================

    /**
     * 玩家松开右键时，清除射击间隔和冷却时间戳，实现"松手后重新预热"的玩法。
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack _stack, World _world, EntityPlayer _player, int _itemInUseCount)
    {
        super.onPlayerStoppedUsing(_stack, _world, _player, _itemInUseCount);
        if (_stack.hasTagCompound())
        {
            NBTTagCompound nbt = _stack.getTagCompound();
            nbt.removeTag(TAG_CURRENT_DELAY); // 清除预热进度，下次按住从 INITIAL_DELAY 开始
            nbt.removeTag(TAG_LAST_FIRE);     // 清除冷却时间戳，下次按住由首次初始化重新设置
            System.out.println("[RMG BlueFire] Stopped using — delay + lastFire cleared for fresh cycle");
        }
    }
}
