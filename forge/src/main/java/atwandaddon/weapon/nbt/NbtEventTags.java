package atwandaddon.weapon.nbt;

import atwandaddon.weapon.nbt.NbtEventTags.Preset;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * NBTs-1.0.2 事件标签注册表与运行时写入工具。
 *
 * <p>NBTs mod 通过 ASM 字节码注入到 Minecraft 原版类中，在特定钩子点
 * 读取 ItemStack 上的 NBT 标签并执行对应逻辑。本类提供：</p>
 * <ul>
 * <li>所有已知 NBT 事件标签的常量定义</li>
 * <li>向 ItemStack 写入标签的工具方法</li>
 * <li>预置配置（后续可扩展自定义事件处理）</li>
 * </ul>
 *
 * <h2>NBTs 钩入点一览</h2>
 * <table>
 * <tr><th>钩入方法</th><th>消费的标签</th></tr>
 * <tr><td>ItemStack.useItemRightClick()</td><td>TAG_DISABLE_THRESHOLD, TAG_RIGHT_CLICK_DUR, TAG_RIGHT_CLICK_POTION, TAG_RIGHT_CLICK_SOUND</td></tr>
 * <tr><td>ItemStack.hitEntity()</td><td>TAG_HIT_HEAL, TAG_HIT_SOUND, TAG_HIT_TARGET, TAG_HIT_ATTACKER</td></tr>
 * <tr><td>ItemStack.updateAnimation()</td><td>TAG_HEAL_TIMER, TAG_ON_UPDATE_POTION</td></tr>
 * <tr><td>ItemStack.onPlayerStoppedUsing()</td><td>TAG_STOP_USING_DUR, TAG_STOP_USING_POTION</td></tr>
 * <tr><td>Item.onEntitySwing()</td><td>TAG_SWING_SOUND</td></tr>
 * <tr><td>ItemStack.isItemStackDamageable()</td><td>TAG_DISABLE_BREAK</td></tr>
 * <tr><td>ItemStack.getMaxItemUseDuration()</td><td>TAG_MAX_USE_DURATION</td></tr>
 * <tr><td>ItemStack.onFoodEaten()</td><td>TAG_ON_EATEN</td></tr>
 * <tr><td>ItemStack.tryPlaceItemIntoWorld()</td><td>TAG_ON_ITEM_USE</td></tr>
 * <tr><td>InventoryPlayer.decrementAnimations()</td><td>TAG_ARMOR_TICK, TAG_ARMOR_UNION</td></tr>
 * </table>
 *
 * <h2>音效约定</h2>
 * 所有 PlaySoundEffect 标签自动加 {@code "tpcore."} 前缀。
 * 例如值 {@code "blade"} → 播放音效 {@code "tpcore.blade"}。
 *
 * @see GunNbtBuilder
 * @see AmmoNbtBuilder
 */
public final class NbtEventTags
{
    // ============================
    // 枪魂系统标签
    // ============================

    /** 每 N tick 自动修复 1 点耐久。0 = 不自动修复 */
    public static final String TAG_HEAL_TIMER        = "onUpdate_HealDamageTimer";

    /** damage >= 此值时阻止右键（枪魂耗尽阈值）。不设置 = 永远不禁用 */
    public static final String TAG_DISABLE_THRESHOLD = "onItemRightClick_Disable";

    /** 右键时耐久变化量。正值 = 消耗枪魂，负值 = 恢复枪魂，0 = 不额外消耗 */
    public static final String TAG_RIGHT_CLICK_DUR   = "onItemRightClick_dur";

    /** 近战命中时耐久修复量。通常设负值（-1 = 回 1 耐久） */
    public static final String TAG_HIT_HEAL          = "hitEntity_HealDamage";

    // ============================
    // 音效标签
    // ============================

    /** 右键时音效（自动加 "tpcore." 前缀） */
    public static final String TAG_RIGHT_CLICK_SOUND = "onItemRightClick_PlaySoundEffect";

    /** 近战命中时音效（自动加 "tpcore." 前缀） */
    public static final String TAG_HIT_SOUND         = "hitEntity_PlaySoundEffect";

    /** 挥动武器时音效（自动加 "tpcore." 前缀） */
    public static final String TAG_SWING_SOUND       = "onEntitySwing_PlaySoundEffect";

    // ============================
    // 药水效果标签
    // ============================

    /** 右键时给药水效果。格式：int[] = [id, dur, amp, id, dur, amp, ...] */
    public static final String TAG_RIGHT_CLICK_POTION = "onItemRightClick";

    /** 松右键时给药水效果。格式同上 */
    public static final String TAG_STOP_USING_POTION  = "onPlayerStoppedUsing";

    /** 每 tick 手持时给药水效果。格式同上 */
    public static final String TAG_ON_UPDATE_POTION   = "onUpdate";

    /** 命中目标时给药水效果。格式：int[] = [id, dur, amp, ...] */
    public static final String TAG_HIT_TARGET         = "hitEntity_Target";

    /** 命中时给攻击者药水效果。格式同上 */
    public static final String TAG_HIT_ATTACKER       = "hitEntity_Attacker";

    /** 吃掉物品时给药水效果。格式同上 */
    public static final String TAG_ON_EATEN           = "onEaten";

    /** 对方块使用时给药水效果。格式同上 */
    public static final String TAG_ON_ITEM_USE        = "onItemUse";

    // ============================
    // 其他标签
    // ============================

    /** 松右键时耐久变化量 */
    public static final String TAG_STOP_USING_DUR    = "onPlayerStoppedUsing_dur";

    /** 覆盖最大使用时长（tick） */
    public static final String TAG_MAX_USE_DURATION  = "MaxItemUseDuration";

    /** 覆盖 isItemStackDamageable() 返回值 */
    public static final String TAG_DISABLE_BREAK     = "DisableBreak";

    /** 挥动时阻止攻击（true = 饥饿 ≤ 0 且非手持时 attackTime = 1） */
    public static final String TAG_SWING_WEAKNESS    = "onEntitySwing_Weakness";

    /** 护甲每 tick 药水效果。格式同上 */
    public static final String TAG_ARMOR_TICK        = "onArmorTickUpdate";

    /** 全套护甲共鸣效果。NBTTagList<String> = ["key value", ...] */
    public static final String TAG_ARMOR_UNION       = "onArmorTickUpdateUnion";

    // ============================
    // 预置配置
    // ============================

    /** 速射类：快速恢复（3 秒 / 点），满耐久扣减6点耐久后锁住，额外消耗 1 */
    public static final Preset PRESET_PISTOL  = new Preset(60, 6, 1, -1);

    /** 狙击类：慢速恢复（6.5 秒 / 点），满耐久扣减5点耐久后锁住，无额外消耗， 近战不恢复耐久 */
    public static final Preset PRESET_SNIPER  = new Preset(130, 5, 0, 0);

    /** 狙击 + 刺刀类：慢速恢复（6.5 秒 / 点），满耐久扣减5点耐久后锁住，无额外消耗， 近战回 1 耐久 */
    public static final Preset PRESET_SNIPER_ADD_BAYONET = new Preset(130, 10, 1, -1);

    /** 轨道炮类：慢速恢复(10 秒 / 点)，满耐久扣减10点耐久后锁住，无额外消耗 */
    public static final Preset PRESET_RAILGUN = new Preset(200, 10, 0, 0);

    /** 散弹类：中速恢复（5 秒 / 点），满耐久扣减3点耐久后锁住，额外消耗 1，近战回 2 */
    public static final Preset PRESET_SHOTGUN = new Preset(100, 3, 1, -2);

    /**蓝火转轮 */
    public static final Preset PRESET_MACHINEGUN = new Preset(10, 140, 0, 0);

    /** 孤骑 */
    public static final Preset PRESET_MAVERICK = new Preset(140, 10, 0, -1);

    /** 游骑兵 */
    public static final Preset PRESET_RANGER_K = new Preset(140, 7, 0, -2);

    // ============================
    // 写入方法
    // ============================

    private NbtEventTags() {}

    /**
     * 确保 ItemStack 持有指定的 NBT 事件标签。
     *
     * <p>仅首次写入 —— 如果标签已存在则跳过，保留 NBTexplorer 等外部工具的修改。</p>
     * <p>建议在 {@code Item.onUpdate()} 中调用，保证标签在任何操作前已就位。</p>
     *
     * @param _stack            物品栈
     * @param _healTimer        自动修复间隔（tick），0 = 不自动修复
     * @param _disableThreshold 禁用阈值（damage >= 此值 → 阻止右键），0 = 不禁用
     * @param _shootCost        右键耐久消耗（引擎已扣 1，这里可追加），0 = 不额外消耗
     * @param _meleeHeal        近战修复量（-1 = 回 1），0 = 不修复
     */
    public static void write(ItemStack _stack, int _healTimer, int _disableThreshold,
                              int _shootCost, int _meleeHeal)
    {
        if (_stack == null)
        {
            return;
        }
        if (!_stack.hasTagCompound())
        {
            _stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = _stack.getTagCompound();
        if (!nbt.hasKey(TAG_HEAL_TIMER))
        {
            nbt.setInteger(TAG_HEAL_TIMER,        _healTimer);
            nbt.setInteger(TAG_DISABLE_THRESHOLD, _disableThreshold);
            nbt.setInteger(TAG_RIGHT_CLICK_DUR,   _shootCost);
            nbt.setInteger(TAG_HIT_HEAL,          _meleeHeal);
        }
    }

    /**
     * 使用预置配置写入枪魂标签。
     * @see #write(ItemStack, int, int, int, int)
     */
    public static void write(ItemStack _stack, Preset _preset)
    {
        write(_stack, _preset.healTimer, _preset.disableThreshold, _preset.shootCost, _preset.meleeHeal);
        // 写入可选音效
        if (_preset.rightClickSound != null)
        {
            writeSound(_stack, TAG_RIGHT_CLICK_SOUND, _preset.rightClickSound);
        }
        if (_preset.hitSound != null)
        {
            writeSound(_stack, TAG_HIT_SOUND, _preset.hitSound);
        }
        if (_preset.swingSound != null)
        {
            writeSound(_stack, TAG_SWING_SOUND, _preset.swingSound);
        }
    }

    // ============================
    // 音效写入
    // ============================

    /**
     * 向 ItemStack 写入音效标签。
     *
     * <p>音效名自动加 {@code "tpcore."} 前缀，与 NBTs mod 约定一致。</p>
     * <p>例如 {@code writeSound(stack, TAG_HIT_SOUND, "blade")} → 播放 {@code tpcore.blade}。</p>
     * <p>与枪魂标签不同，音效标签每次调用都会覆写（允许多次调用切换音效）。</p>
     *
     * @param _stack 物品栈
     * @param _tag   音效标签常量（TAG_RIGHT_CLICK_SOUND / TAG_HIT_SOUND / TAG_SWING_SOUND）
     * @param _sound 音效名（不含 "tpcore." 前缀），如 "blade", "bow"
     */
    public static void writeSound(ItemStack _stack, String _tag, String _sound)
    {
        if (_stack == null || _sound == null)
        {
            return;
        }
        if (!_stack.hasTagCompound())
        {
            _stack.setTagCompound(new NBTTagCompound());
        }
        _stack.getTagCompound().setString(_tag, _sound);
    }

    // ============================
    // 药水写入
    // ============================

    /**
     * 向 ItemStack 追加一组药水效果（id, dur, amp）。
     *
     * <p>支持多次调用以叠加多个药水效果。内部将新的三元组追加到已有 {@code int[]} 末尾。</p>
     * <p>格式：{@code int[] = [potionId, duration, amplifier, ...]}，每 3 个 int 为一组。</p>
     * <p>例如两次调用：
     * <pre>{@code
     * writePotion(stack, TAG_HIT_TARGET, 2, 100, 1);  // 缓慢 II 100tick
     * writePotion(stack, TAG_HIT_TARGET, 9, 60, 0);    // 反胃 I 60tick
     * // → int[] = [2, 100, 1, 9, 60, 0]
     * }</pre></p>
     *
     * @param _stack      物品栈
     * @param _tag        药水标签常量（TAG_HIT_TARGET / TAG_RIGHT_CLICK_POTION 等）
     * @param _potionId   MC 药水效果 ID（如 1=速度, 2=缓慢, 5=力量）
     * @param _duration   效果持续时长（tick），20 tick = 1 秒
     * @param _amplifier  效果等级（0 = I 级, 1 = II 级）
     */
    public static void writePotion(ItemStack _stack, String _tag, int _potionId, int _duration, int _amplifier)
    {
        if (_stack == null)
        {
            return;
        }
        if (!_stack.hasTagCompound())
        {
            _stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = _stack.getTagCompound();
        // 读取已有药水数组，无则为空数组
        int[] existing = nbt.hasKey(_tag) ? nbt.getIntArray(_tag) : new int[0];
        // 新数组 = 已有 + 3 个新元素
        int[] combined = new int[existing.length + 3];
        System.arraycopy(existing, 0, combined, 0, existing.length);
        combined[existing.length]     = _potionId;
        combined[existing.length + 1] = _duration;
        combined[existing.length + 2] = _amplifier;
        nbt.setIntArray(_tag, combined);
    }

    // ============================
    // 预置配置类
    // ============================

    public static class Preset
    {
        /** 自动修复间隔（tick），0 = 不自动修复 */
        public final int healTimer;
        /** 枪魂耗尽阈值（damage >= 此值 → 阻止右键），0 = 不禁用 */
        public final int disableThreshold;
        /** 右键耐久消耗（引擎已扣 1，可追加），0 = 不额外消耗 */
        public final int shootCost;
        /** 近战修复量（负值 = 回耐久），0 = 不修复 */
        public final int meleeHeal;
        /** [可选] 右键音效名（不含 "tpcore." 前缀），null = 不设置 */
        public final String rightClickSound;
        /** [可选] 近战命中音效名，null = 不设置 */
        public final String hitSound;
        /** [可选] 挥动武器音效名，null = 不设置 */
        public final String swingSound;

        /**
         * 构造枪魂预置（不含音效）。
         *
         * @param _healTimer        自动修复间隔（tick），0 = 不自动修复
         * @param _disableThreshold 枪魂耗尽阈值，0 = 不禁用
         * @param _shootCost        右键耐久消耗，0 = 不额外消耗
         * @param _meleeHeal        近战修复量，0 = 不修复, -1 = 回 1 点耐久
         */
        public Preset(int _healTimer, int _disableThreshold, int _shootCost, int _meleeHeal)
        {
            this(_healTimer, _disableThreshold, _shootCost, _meleeHeal, null, null, null);
        }

        /**
         * 构造枪魂预置（含可选音效）。
         *
         * @param _healTimer        自动修复间隔（tick），0 = 不自动修复
         * @param _disableThreshold 枪魂耗尽阈值，0 = 不禁用
         * @param _shootCost        右键耐久消耗，0 = 不额外消耗
         * @param _meleeHeal        近战修复量，0 = 不修复, -1 = 回 1 点耐久
         * @param _rightClickSound  右键音效名（null = 不设置），如 "blade"
         * @param _hitSound         近战命中音效名（null = 不设置），如 "blade_hit"
         * @param _swingSound       挥动武器音效名（null = 不设置），如 "whoosh"
         */
        public Preset(int _healTimer, int _disableThreshold, int _shootCost, int _meleeHeal,
                      String _rightClickSound, String _hitSound, String _swingSound)
        {
            this.healTimer = _healTimer;
            this.disableThreshold = _disableThreshold;
            this.shootCost = _shootCost;
            this.meleeHeal = _meleeHeal;
            this.rightClickSound = _rightClickSound;
            this.hitSound = _hitSound;
            this.swingSound = _swingSound;
        }
    }
}
