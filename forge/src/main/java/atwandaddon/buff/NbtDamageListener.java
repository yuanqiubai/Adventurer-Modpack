package atwandaddon.buff;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import atwandaddon.weapon.nbt.NbtAttributeHelper;

/**
 * NBT 属性加成监听器 — 从玩家武器 + 装备 NBT 中提取伤害加成，供 Buff DOT 计算使用。
 *
 * <h2>解决的问题</h2>
 * 武器面板伤害（如 basic_damage）通过 {@link net.minecraft.entity.projectile.EntityThrowable}
 * 施加到目标时，CustomNPCs 的 NBT {@code AttributeModifiers} 加成（bow_power / ice_power /
 * wandDamage）并不会自动应用到弹丸的 {@code setDamage()} 上。这意味着 {@link LivingHurtEvent}
 * 捕获到的实际伤害不包含 NBT 加成。
 *
 * <p>本监听器在每次实体受伤时追踪攻击来源玩家，读取其手持武器和装备护甲的 NBT 属性，
 * 通过 {@link NbtAttributeHelper#readAll(ItemStack, EntityPlayer)} 计算完整的
 * bowPower / icePower / wandDamage 加成，并与 {@link BuffMonitor} 捕获的实际伤害组合，
 * 还原出武器"理论完整面板伤害"。</p>
 *
 * <h2>数据流</h2>
 * <pre>
 * 弹丸命中 → LivingHurtEvent
 *     ├── BuffMonitor:         累积 actualDamage（无 NBT 加成）
 *     └── NbtDamageListener:   读取玩家武器+护甲 NBT → 累积 nbtBonus
 *                ↓                           ↓
 *         PotionBleed.performEffect()
 *                ↓
 *         totalDamage = actualDamage + nbtFlatBonus
 *         DOT = 1 + totalDamage * ratio * (amplifier + 1)
 * </pre>
 *
 * <h2>NBT 加成提取公式</h2>
 * 由于本监听器无法获取武器的基础面板值（basic_damage / ice_damage），
 * NBT 加成通过 {@code applyTo(0F)} 提取纯加成部分：
 * <pre>
 * nbtFlatBonus = bowPower.applyTo(0) + icePower.applyTo(0) + wandDamage.applyTo(0)
 * </pre>
 * 其中 {@code applyTo(0) = (0 + op0) * (1 + op1) * op2 = op0 * (1 + op1) * op2}。
 *
 * <p><b>注意</b>：此近似会丢失 op1/op2 对武器基础面板的乘法效应。
 * 例如 bowPower.op2=2.0 时，10 伤害武器实际加成为 10（10*2 - 10），
 * 但 {@code applyTo(0)} 仅计算了 {@code op0*op2} 部分。</p>
 *
 * @see BuffMonitor    实际伤害累加器
 * @see NbtAttributeHelper  NBT 属性解析工具
 * @see PotionBleed     DOT 效果实现（消费两个监听器的数据）
 */
public class NbtDamageListener
{
    /** 单例实例，供 Potion 子类静态访问 */
    private static NbtDamageListener instance;

    /**
     * 目标实体 ID → 攻击来源玩家的 NBT 属性加成。
     * 键为受伤实体 ID，值为攻击者（玩家）武器 + 护甲的 NBT 加成总和。
     * 被 {@link #consumeNbtBonus(int)} 读取后清除。
     */
    private final Map<Integer, NbtAttributeHelper.Result> pendingNbtBonus =
        new HashMap<Integer, NbtAttributeHelper.Result>();

    /** 构造即设为单例 */
    public NbtDamageListener()
    {
        instance = this;
    }

    // ================================================================
    //  事件监听
    // ================================================================

    /**
     * 监听实体受伤事件，提取攻击来源玩家的 NBT 属性加成。
     *
     * <p>仅处理攻击来源为 {@link EntityPlayer} 的伤害事件。
     * 从玩家手持武器和装备护甲的 NBT 中读取 bowPower / icePower / wandDamage
     * 属性加成，按目标实体 ID 存储以配合 {@link BuffMonitor} 的累积机制。</p>
     *
     * <p>若同一目标在同一 tick 内被多次命中，后命中的 NBT 加成会覆盖前者。
     * 这与 {@link BuffMonitor} 的累加策略不同——NBT 加成取决于攻击瞬间的
     * 武器/装备状态，多次命中之间加成值通常相同或相近。</p>
     *
     * @param _event LivingHurtEvent，{@code source.getEntity()} 为攻击来源
     */
    @ForgeSubscribe
    public void onLivingHurt(LivingHurtEvent _event)
    {
        // 获取攻击来源实体
        Entity sourceEntity = _event.source.getEntity();
        if (!(sourceEntity instanceof EntityPlayer))
        {
            return;  // 非玩家攻击，忽略
        }

        EntityPlayer player = (EntityPlayer) sourceEntity;

        // 读取玩家手持武器（当前持有物品）
        ItemStack heldItem = player.getHeldItem();
        if (heldItem == null)
        {
            return;  // 空手攻击，无 NBT 加成可读
        }

        // 通过 NbtAttributeHelper 读取武器 + 全身护甲的属性加成
        // readAll() 内部调用 read(heldItem).add(readFromArmor(player))
        NbtAttributeHelper.Result nbtBonus = NbtAttributeHelper.readAll(heldItem, player);

        // 若没有任何加成，跳过存储以节省内存
        if (!nbtBonus.hasAny())
        {
            return;
        }

        // 按目标实体 ID 存储 NBT 加成（不累加，使用覆盖策略）
        int targetId = _event.entityLiving.entityId;
        pendingNbtBonus.put(Integer.valueOf(targetId), nbtBonus);
    }

    // ================================================================
    //  静态访问接口（供 Potion 子类调用）
    // ================================================================

    /**
     * 获取并清除指定实体的 NBT 属性加成。
     *
     * <p>调用后该实体的记录被移除，避免同一次 buff 效果重复使用。</p>
     *
     * @param _entityId 受伤实体的数字 ID
     * @return NBT 属性加成结果，无记录时返回 {@code null}
     */
    public static NbtAttributeHelper.Result consumeNbtBonus(int _entityId)
    {
        if (instance == null)
        {
            return null;
        }
        return instance.pendingNbtBonus.remove(Integer.valueOf(_entityId));
    }
}
