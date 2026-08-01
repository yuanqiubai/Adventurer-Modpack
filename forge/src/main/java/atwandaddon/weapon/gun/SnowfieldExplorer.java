package atwandaddon.weapon.gun;

import java.util.Arrays;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;
import com.adventurer_engine.util.JsonHelper;
import com.google.gson.internal.StringMap;
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
            .shootNumber(1)
            .shootDelay(1)
            .accuracy(0.9)
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

    /**
     * [测试] JSON 构造参数 — 用于排除 NBT 映射问题。
     * 引擎 JSON 路径的 BasicRangedWeapon 构造器硬编码 ice_damage=0, gravity=true, penetrate=false，
     * 但 accuracy 等核心参数能正确读取。先用 JSON 路径测试精度是否正常。
     */
    // private static JsonHelper buildJson() {
    //     StringMap map = new StringMap();
    //     // ---- 基础属性 ----
    //     map.put("name", "snowfield_explorer");
    //     map.put("icon", "snowfield_explorer");
    //     map.put("duration", 9.0D);            // MUST be Double
    //     map.put("unbreakable", false);
    //     map.put("stack", 1.0D);
    //     // ---- 蓄力系统 ----
    //     map.put("charge_stage", 1.0D);
    //     map.put("charge_time", 4.0D);
    //     // 蓄力图标列表 — charge_stage >= 1 时必须提供，否则 registerIcons 空指针
    //     map.put("icons", Arrays.asList("snowfield_explorer"));
    //     // ---- 伤害 ----
    //     map.put("basic_damage", 16.0D);
    //     map.put("knockback", 0.1D);
    //     // ---- 射击参数 ----
    //     map.put("shoot_mode", "semi");
    //     map.put("shoot_number", 1.0D);
    //     map.put("shoot_delay", 1.0D);
    //     map.put("accuracy", 0.9D);            // 狙击精度
    //     map.put("shoot_sound", "bow_zx");
    //     map.put("maxRange", 64.0D);
    //     map.put("maxSpeed", 6.0D);
    //     // ---- 弹道 ----
    //     map.put("gravity", false);
    //     map.put("penetrate", false);
    //     // ---- 弹药 ----
    //     map.put("allowed_ammo", Arrays.asList(SnowEagleAP.AMMO_NAME));
    //     return new JsonHelper(map);
    // }

    // ==================== NBTs 充能系统 ====================

    @Override
    public void onUpdate(ItemStack _stack, World _world, Entity _entity, int _slot, boolean _isCurrent)
    {
        super.onUpdate(_stack, _world, _entity, _slot, _isCurrent);
        if (_isCurrent && _entity instanceof EntityPlayer)
        {
            NbtEventTags.write(_stack, NbtEventTags.PRESET_SNIPER);
        }
    }

}
