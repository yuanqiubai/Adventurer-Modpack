package atwandaddon.buff;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.adventurer_engine.common.items.blank.BasicItem;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;

import atwandaddon.buff.potion.PotionBleed;
import atwandaddon.buff.potion.PotionDeadline;
import atwandaddon.weapon.nbt.NbtAttributeHelper;

/**
 * 实体 Buff 监听器 — 统一管理实体伤害数据持久化与 Buff DOT 处理。
 *
 * <h2>解决的问题</h2>
 * 旧设计中 {@link BuffMonitor#consumeDamage(int)} 读取后立即删除数据，
 * 导致流血 DOT 仅在第一次 tick 触发，后续 tick 无伤害数据可用。
 * 本监听器将伤害数据持久化存储，允许 DOT 在整个 Buff 持续期间重复读取。
 *
 * <h2>职责</h2>
 * <ul>
 *   <li>{@link LivingHurtEvent} — 捕获实体实际承受伤害 + 攻击者 NBT 属性加成（持久存储，不消费）</li>
 *   <li>{@link LivingUpdateEvent} — 扫描实体身上的 Buff（流血等），按间隔触发 DOT 伤害</li>
 *   <li>{@link LivingDeathEvent} — 清理已死亡实体的记录</li>
 * </ul>
 *
 * <h2>排除规则</h2>
 * 弹幕实体（{@link EntityThrowable} 子类）和掉落物（{@link EntityItem}）
 * 不参与监听。实际因 {@link LivingUpdateEvent} 仅对 {@link EntityLivingBase} 触发，
 * 弹幕/掉落物已被自然排除，此排除作为双重保险。
 *
 * <h2>注册方式</h2>
 * <pre>{@code
 * EntityBuffListener listener = new EntityBuffListener();
 * MinecraftForge.EVENT_BUS.register(listener);
 * }</pre>
 *
 * <h2>与旧监听器的关系</h2>
 * 本类取代 {@link BuffMonitor} 和 {@link NbtDamageListener} 的功能，
 * 但后两者保留代码（注释）以便参考回溯。
 *
 * @see BuffMonitor      旧版伤害累加器（已弃用，保留代码）
 * @see NbtDamageListener 旧版 NBT 属性监听器（已弃用，保留代码）
 * @see PotionBleed       流血 Potion 效果（DOT 消费方）
 */
public class EntityBuffListener
{
    /** 单例实例，供 {@link PotionBleed} 等外部类静态访问 */
    private static EntityBuffListener instance;

    /**
     * 实体 ID → 持久伤害记录。
     * 与 {@link BuffMonitor} 不同，伤害数据不会被"消费"删除，
     * 而是持续保留直到实体死亡或 Buff 过期。
     */
    private final Map<Integer, DamageRecord> records = new HashMap<Integer, DamageRecord>();

    /** 构造即设为单例 */
    public EntityBuffListener()
    {
        instance = this;
    }

    // ================================================================
    //  事件监听 — LivingHurtEvent：捕获伤害数据
    // ================================================================

    /**
     * 监听所有实体受伤事件，计算并持久存储武器的<strong>理论面板伤害</strong>。
     *
     * <p>与旧版不同，此方法不再依赖 {@code _event.ammount}（已扣护甲的减免后伤害），
     * 而是从攻击来源玩家的手持武器和全身 NBT 属性中计算出完整理论伤害。
     * 这解决了两个核心问题：</p>
     * <ol>
     *   <li>乘区丢失：{@code applyTo(0F)} 丢失了 op1/op2 对基础面板的乘法效应</li>
     *   <li>护甲干扰：流血 DOT 伤害不应受目标护甲影响</li>
     * </ol>
     *
     * <p>攻击者识别逻辑：</p>
     * <ul>
     *   <li>先用 {@link DamageSource#getEntity()} 检查直接攻击者</li>
     *   <li>再用 {@link DamageSource#getSourceOfDamage()} 检查间接攻击者
     *       （弹丸命中时，{@code getEntity()} 返回弹丸实体，{@code getSourceOfDamage()} 返回射击玩家）</li>
     * </ul>
     *
     * <p>伤害公式：
     * <pre>
     * totalPanelDamage = bowPower.applyTo(basic_damage)   // 物理伤害 + NBT
     *                  + icePower.applyTo(ice_damage)     // 穿甲伤害 + NBT
     *                  + attackDamage.applyTo(0F)         // 额外 attackDamage 加成
     *                  + wandDamage.applyTo(0F)           // 法杖属性加成
     * </pre>
     * 其中 {@code applyTo(base) = ((base + op0) * (1 + op1)) * op2}</p>
     *
     * @param _event LivingHurtEvent
     */
    @ForgeSubscribe
    public void onLivingHurt(LivingHurtEvent _event)
    {
        // 跳过 DOT 自身造成的伤害，防止反馈循环
        if (_event.source == DamageSource.generic)
        {
            return;
        }

        EntityLivingBase entity = _event.entityLiving;
        if (entity == null || isExcluded(entity))
        {
            return;
        }

        int entityId = entity.entityId;

        // ---- 识别攻击来源玩家 ----
        // 优先 getSourceOfDamage()（间接伤害源，弹丸→射击玩家）
        // 然后 getEntity()（直接伤害源，近战→玩家自身）
        EntityPlayer player = null;
        Entity srcOfDmg = _event.source.getSourceOfDamage();
        Entity srcEntity = _event.source.getEntity();

        if (srcOfDmg instanceof EntityPlayer)
        {
            player = (EntityPlayer) srcOfDmg;
        }
        else if (srcEntity instanceof EntityPlayer)
        {
            player = (EntityPlayer) srcEntity;
        }

        if (player == null)
        {
            // 非玩家攻击 → 用 _event.ammount 作为兜底（NPC / 跌落 / 环境伤害等）
            DamageRecord rec = getOrCreate(entityId);
            rec.totalPanelDamage += _event.ammount;
            return;
        }

        // ---- 玩家攻击 → 计算理论面板伤害 ----
        ItemStack heldItem = player.getHeldItem();
        if (heldItem == null)
        {
            return;
        }

        Item item = heldItem.getItem();

        // 读取武器面板基础伤害
        float weaponBasicDmg = 0F;
        float weaponIceDmg   = 0F;

        if (item instanceof BasicRangedWeapon)
        {
            BasicRangedWeapon rw = (BasicRangedWeapon) item;
            weaponBasicDmg = rw.basic_damage;
            weaponIceDmg   = rw.ice_damage;
        }
        else if (item instanceof BasicItem)
        {
            // BasicItemSword 等近战武器也有此字段
            // 尝试从 NBT default_nbt 中读取（BasicItem.default_nbt 可能含 basic_damage）
            // 若读不到则 fallback 到 _event.ammount
            BasicItem bi = (BasicItem) item;
            if (bi.default_nbt != null && bi.default_nbt.hasKey("basic_damage"))
            {
                weaponBasicDmg = bi.default_nbt.getFloat("basic_damage");
            }
            if (bi.default_nbt != null && bi.default_nbt.hasKey("ice_damage"))
            {
                weaponIceDmg = bi.default_nbt.getFloat("ice_damage");
            }
        }
        else
        {
            // 非引擎武器（原版剑/弓等）→ 用 _event.ammount 兜底
            DamageRecord rec = getOrCreate(entityId);
            rec.totalPanelDamage += _event.ammount;
            return;
        }

        // 读取玩家武器 + 全身护甲的 NBT 属性加成（bow_power / ice_power / generic.attackDamage / generic.WandDamage）
        NbtAttributeHelper.Result nbtBonus = NbtAttributeHelper.readAll(heldItem, player);

        // 计算各伤害分量的最终值（基础面板 + NBT 加成，按 Operation 应用）
        float finalBasic = nbtBonus.bowPower.applyTo(weaponBasicDmg)
                         + nbtBonus.attackDamage.applyTo(0F);
        float finalIce   = nbtBonus.icePower.applyTo(weaponIceDmg);
        float finalWand  = nbtBonus.wandDamage.applyTo(0F);

        // 理论完整面板伤害 = 物理 + 穿甲 + 法杖
        float totalPanelDamage = finalBasic + finalIce + finalWand;

        // 存储（覆盖而非累加：同一次攻击可能触发 basic+ice 两次 LivingHurtEvent）
        DamageRecord rec = getOrCreate(entityId);
        rec.totalPanelDamage = totalPanelDamage;
        rec.attackerEntityId = player.entityId;

        // ===== DEBUG: 面板伤害存储日志 =====
        FMLLog.info("[BleedDebug] onLivingHurt | entityId=%d | weapon=%s | totalPanelDamage=%.2f",
            Integer.valueOf(entityId), item.getClass().getSimpleName(),
            Float.valueOf(totalPanelDamage));
        // ===== END DEBUG =====

        // ---- 死线伤害累积 ----
        // 若实体已持有死线 potion，本次攻击的伤害累加入死线伤害池
        // 注意：此时 Engine 尚未施加本次攻击的 PotionEffect（DamagePacket.causeDamage 在 attackEntityFrom 返回后执行），
        // 但上一个周期的死线 potion 可能仍然活跃
        if (entity.isPotionActive(PotionDeadline.ID))
        {
            // 死线已激活 → 累加伤害，但不重新施加 buff
            // EntityBuffListener 使用独立计时器（deadlineExpireTick），不受 MC potion 持续时间延长的影响
            rec.deadlineAccumulatedDamage += totalPanelDamage;
        }
    }

    // ================================================================
    //  事件监听 — LivingUpdateEvent：扫描实体 Buff 状态并清理过期记录
    // ================================================================

    /**
     * 每 tick 处理死线计时与爆发结算，不再清理伤害记录。
     *
     * <p>DOT 伤害的实际触发由 {@link PotionBleed#performEffect} 通过
     * MC 原版 Potion 系统处理。本方法仅负责死线的独立计时管理。</p>
     *
     * <h2>为什么不在本方法清理记录？</h2>
     * <p>MC 的 {@code EntityLivingBase.onEntityUpdate()} 先触发 {@code LivingUpdateEvent}，
     * 再调用 {@code updatePotionEffects()}。引擎的 {@code DamagePacket.causeDamage}
     * 流程是先 {@code attackEntityFrom}（触发 {@code LivingHurtEvent} → 存储记录），
     * 再 {@code buffGiver}（施加 PotionEffect）。如果在 {@code LivingUpdateEvent}
     * 中清理记录，Potion 可能尚未被施加，导致 {@code hasActiveBuff=false}，
     * 记录被误删 → DOT 失效。记录改为由 {@link #onWorldUnload(WorldEvent.Unload)}
     * 和 {@link #onLivingDeath(LivingDeathEvent)} 清理。</p>
     *
     * @param _event LivingUpdateEvent，每个 EntityLivingBase 每 tick 触发一次
     */
    @ForgeSubscribe
    public void onLivingUpdate(LivingUpdateEvent _event)
    {
        EntityLivingBase entity = _event.entityLiving;
        if (entity == null || isExcluded(entity))
        {
            return;
        }

        int entityId = entity.entityId;
        DamageRecord rec = records.get(Integer.valueOf(entityId));
        if (rec == null)
        {
            return;  // 无记录，无需处理
        }

        // 获取世界时间（用于死线独立计时）
        long worldTime = entity.worldObj.getTotalWorldTime();

        // ---- 死线 buff 检查 — 独立计时 + 到期爆发结算 ----
        // 死线使用 EntityBuffListener 自己的计时器（不依赖 MC Potion 系统的持续时间），
        // 以避免武器多次命中 → Engine 延长 Potion 持续时间 → 死线永不到期的问题
        if (entity.isPotionActive(PotionDeadline.ID))
        {
            if (!rec.deadlineActive)
            {
                // 死线刚刚被施加（首次检测到 Potion 效果） → 启动独立计时器
                rec.deadlineActive = true;

                // 从实体的活跃 PotionEffect 读取武器配置的实际时长和等级
                PotionEffect pe = entity.getActivePotionEffect(PotionDeadline.instance);
                if (pe != null)
                {
                    rec.deadlineExpireTick = worldTime + (long)pe.getDuration();
                    rec.deadlineAmplifier = pe.getAmplifier();
                }
                else
                {
                    // 兜底：使用默认值（10 秒，I 级）
                    rec.deadlineExpireTick = worldTime + (long)PotionDeadline.DEFAULT_DURATION;
                    rec.deadlineAmplifier = 0;
                }

                // 首次命中的伤害（onLivingHurt 中存储的 totalPanelDamage）作为初始累积值
                rec.deadlineAccumulatedDamage = rec.totalPanelDamage;
            }
            else if (worldTime >= rec.deadlineExpireTick)
            {
                // 死线到期 → 计算并施加爆发伤害
                // 公式: 爆发 = 1 + 累积伤害 × DAMAGE_RATIO × (amplifier + 1)
                float burstDamage = 1F + rec.deadlineAccumulatedDamage
                                  * PotionDeadline.DAMAGE_RATIO
                                  * (float)(rec.deadlineAmplifier + 1);

                entity.attackEntityFrom(DamageSource.generic, burstDamage);

                // 重置受伤无敌帧，确保后续伤害不被 MC 的 hurtResistantTime 阻挡
                ((Entity)entity).hurtResistantTime = 0;

                // 移除 MC Potion 效果（视觉标记消失，允许下次重新施加死线）
                entity.removePotionEffect(PotionDeadline.ID);

                // 重置死线状态，伤害池清零，开放重新施加
                rec.deadlineActive = false;
                rec.deadlineAccumulatedDamage = 0F;
            }
        }
    }

    // ================================================================
    //  事件监听 — LivingDeathEvent：清理记录
    // ================================================================

    /**
     * 实体死亡时清理其伤害记录，释放内存。
     *
     * @param _event LivingDeathEvent
     */
    @ForgeSubscribe
    public void onLivingDeath(LivingDeathEvent _event)
    {
        if (_event.entityLiving != null)
        {
            records.remove(Integer.valueOf(_event.entityLiving.entityId));
        }
    }

    // ================================================================
    //  事件监听 — WorldEvent.Unload：全清记录
    // ================================================================

    /**
     * 世界卸载时清空所有伤害记录。
     *
     * <p>由于 {@link #records} 是静态 HashMap，跨地图加载时可能残留旧记录。
     * 切换地图后旧记录中的 entityId 不会与新地图的实体重叠（entityId 由 MC
     * 自动递增分配，不同地图互不冲突），但残留记录仍占用内存。
     * 在世界卸载时全量清除，确保每次进入地图都是干净状态。</p>
     *
     * @param _event WorldEvent.Unload
     */
    @ForgeSubscribe
    public void onWorldUnload(WorldEvent.Unload _event)
    {
        int size = records.size();
        records.clear();
        if (size > 0)
        {
            FMLLog.info("[EntityBuffListener] World unloaded — cleared %d damage records", Integer.valueOf(size));
        }
    }

    // ================================================================
    //  静态访问接口（供 PotionBleed 等外部类调用）
    // ================================================================

    /**
     * 获取指定实体的理论面板伤害（持久存储，不消费）。
     *
     * <p>此值为攻击时从玩家武器面板 + NBT 属性计算出的完整理论伤害，
     * 不受目标护甲/抗性影响。与 {@link BuffMonitor#consumeDamage(int)} 不同，
     * 此方法不删除记录，允许多次读取。</p>
     *
     * @param _entityId 实体数字 ID
     * @return 理论面板伤害，无记录时返回 0
     */
    public static float getDamage(int _entityId)
    {
        if (instance == null)
        {
            FMLLog.info("[BleedDebug] getDamage | entityId=%d | instance=NULL", Integer.valueOf(_entityId));
            return 0F;
        }
        DamageRecord rec = instance.records.get(Integer.valueOf(_entityId));
        float result = rec != null ? rec.totalPanelDamage : 0F;
        // ===== DEBUG 日志 =====
        FMLLog.info("[BleedDebug] getDamage | entityId=%d | recordExists=%b | totalPanelDamage=%.2f",
            Integer.valueOf(_entityId),
            Boolean.valueOf(rec != null),
            Float.valueOf(result));
        // ===== END DEBUG =====
        return result;
    }

    /**
     * 已弃用 — NBT 加成已合并到 {@link #getDamage(int)} 返回的总面板伤害中。
     *
     * <p>保留此方法仅为向后兼容，始终返回 0。</p>
     *
     * @param _entityId 实体数字 ID
     * @return 始终返回 0
     * @deprecated 使用 {@link #getDamage(int)} 替代
     */
    @Deprecated
    public static float getNbtBonus(int _entityId)
    {
        return 0F;
    }

    /**
     * 强制清除指定实体的所有伤害记录。
     * 用于 Buff 效果被手动移除时（如 /effect clear）。
     *
     * @param _entityId 实体数字 ID
     */
    public static void clearRecord(int _entityId)
    {
        if (instance != null)
        {
            instance.records.remove(Integer.valueOf(_entityId));
        }
    }

    // ================================================================
    //  内部工具方法
    // ================================================================

    /**
     * 获取或创建指定实体 ID 的伤害记录。
     *
     * @param _entityId 实体数字 ID
     * @return 已存在或新创建的 DamageRecord（绝不会返回 null）
     */
    private DamageRecord getOrCreate(int _entityId)
    {
        Integer key = Integer.valueOf(_entityId);
        DamageRecord rec = records.get(key);
        if (rec == null)
        {
            rec = new DamageRecord();
            records.put(key, rec);
        }
        return rec;
    }

    /**
     * 判断实体是否应被排除监听。
     *
     * <p>排除条件：</p>
     * <ul>
     *   <li>弹幕实体（{@link EntityThrowable} 子类）</li>
     *   <li>掉落物（{@link EntityItem}）</li>
     *   <li>非 {@link EntityLivingBase} 类型（双重保险）</li>
     * </ul>
     *
     * @param _entity 待检查实体
     * @return true = 应排除，不参与监听
     */
    private static boolean isExcluded(Entity _entity)
    {
        if (_entity == null)
        {
            return true;
        }
        // 排除弹幕实体
        if (_entity instanceof EntityThrowable)
        {
            return true;
        }
        // 排除掉落物
        if (_entity instanceof EntityItem)
        {
            return true;
        }
        // 非活体实体不监听（双重保险，LivingUpdateEvent 已保证此条件）
        if (!(_entity instanceof EntityLivingBase))
        {
            return true;
        }
        return false;
    }

    // ================================================================
    //  内部数据类
    // ================================================================

    /**
     * 单个实体的持久伤害记录。
     *
     * <p>与 {@link BuffMonitor} 的"消费后删除"策略不同，
     * 此记录的字段在整个 Buff 持续期间保留。</p>
     *
     * <h3>v2 变更</h3>
     * <ul>
     *   <li>{@code totalPanelDamage} 替代旧的 {@code accumulatedDamage}：
     *       存储攻击时从玩家武器面板 + NBT 属性计算出的完整理论伤害，
     *       不再依赖 {@code LivingHurtEvent.ammount}（已扣护甲）</li>
     *   <li>{@code attackerEntityId} 记录攻击来源玩家，供后续扩展使用</li>
     * </ul>
     */
    private static class DamageRecord
    {
        /** 理论完整面板伤害 = bowPower.applyTo(basic_dmg) + icePower.applyTo(ice_dmg) + attackDamage.applyTo(0) + wandDamage.applyTo(0) */
        float totalPanelDamage;

        /** 攻击来源玩家的 entityId（保留供后续扩展，如需要根据攻击者动态更新伤害） */
        int attackerEntityId;

        /** 上次 DOT 触发时的世界时间（tick），用于间隔控制 */
        long lastDOTTick;

        // ============================================================
        //  死线 (PotionDeadline) 追踪字段
        // ============================================================

        /** 死线 buff 是否处于激活状态（由 EntityBuffListener 独立计时，不依赖 MC Potion 系统） */
        boolean deadlineActive;

        /** 死线 buff 的预计到期世界时间（tick），到达此时间时触发爆发结算 */
        long deadlineExpireTick;

        /** 死线 buff 的 Potion 等级（0 = I 级, 1 = II 级, ...），从主动 PotionEffect 读取 */
        int deadlineAmplifier;

        /** 死线 buff 持续期间累积的伤害总量（每次命中累加，到期后清零） */
        float deadlineAccumulatedDamage;
    }
}
