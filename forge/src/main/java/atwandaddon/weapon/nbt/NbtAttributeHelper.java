package atwandaddon.weapon.nbt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;

/**
 * NBT {@code AttributeModifiers} 属性加成解析工具。
 *
 * <p>CustomNPCs 等第三方 mod 通过 ItemStack NBT 上的 {@code AttributeModifiers}
 * 标签为武器附加伤害值。每个 entry 包含 {@code AttributeName}、{@code Amount}
 * 和 {@code Operation} 三个关键字段。</p>
 *
 * <h2>NBT 结构</h2>
 * <pre>
 * AttributeModifiers (NBTTagCompound)
 *   └── entries (NBTTagList)
 *        └── [N] (NBTTagCompound)
 *             ├── AttributeName: "generic.attackDamage" | "ice_power" | "generic.WandDamage"
 *             ├── Amount: 7.0         (float)
 *             ├── Operation: 0 | 1 | 2
 *             ├── Name: "..."         (显示名)
 *             ├── UUIDMost: ...
 *             └── UUIDLeast: ...
 * </pre>
 *
 * <h2>Operation 计算方案</h2>
 * <table>
 * <tr><th>Operation</th><th>含义</th><th>公式</th></tr>
 * <tr><td>0</td><td>基础面板 += 常数</td><td>{@code base += amount}</td></tr>
 * <tr><td>1</td><td>当前面板 *= (1 + Σ常数)</td><td>{@code current *= (1 + Σamount)}</td></tr>
 * <tr><td>2</td><td>最终面板 *= 常数</td><td>{@code final *= Πamount}（在 op0/op1 之后应用）</td></tr>
 * </table>
 *
 * <p>完整计算顺序：{@code ((base + Σop0) × (1 + Σop1)) × Πop2}</p>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * NbtAttributeHelper.Result bonus = NbtAttributeHelper.readAll(weaponStack, player);
 * // 按 Operation 分别应用到各伤害类型
 * float finalBasic = bonus.attackDamage.applyTo(weapon.basic_damage);
 * float finalIce   = bonus.icePower.applyTo(weapon.ice_damage);
 * float finalWand  = bonus.wandDamage.applyTo(0F);
 * float totalPanel = finalBasic + finalIce + finalWand;
 * }</pre>
 *
 * @see ModifierGroup
 * @see Result
 */
public final class NbtAttributeHelper
{
    // ============================
    //  NBT Key 常量
    // ============================

    /** NBT 根标签下存储属性修饰符列表的复合标签名 */
    private static final String TAG_MODIFIERS  = "AttributeModifiers";

    /** 属性修饰符列表的列表标签名 */
    private static final String TAG_ENTRIES    = "entries";

    /** 单个修饰符 entry 中，属性名的 key */
    private static final String KEY_ATTR_NAME  = "AttributeName";

    /** 单个修饰符 entry 中，加成量的 key */
    private static final String KEY_AMOUNT     = "Amount";

    /** 单个修饰符 entry 中，计算方案的 key（0=加, 1=乘当前, 2=乘最终） */
    private static final String KEY_OPERATION  = "Operation";

    // ============================
    //  已知属性名常量
    // ============================

    /** MC 原版攻击力属性名，对应 {@code generic.attackDamage} */
    private static final String ATTR_ATTACK_DAMAGE = "generic.attackDamage";

    /** 冒险者传说引擎自定义冰/穿甲伤害属性名 */
    private static final String ATTR_ICE_POWER     = "ice_power";

    /** 法杖/魔法伤害属性名 */
    private static final String ATTR_WAND_DAMAGE   = "generic.WandDamage";

    /** 弓/枪械物理伤害属性名 */
    private static final String ATTR_BOW_POWER     = "bow_power";

    // ============================
    //  护甲常量
    // ============================

    /** MC 1.6.4 护甲槽位数（头盔/胸甲/护腿/靴子） */
    private static final int ARMOR_SLOTS = 4;

    // ============================
    //  禁止实例化
    // ============================

    private NbtAttributeHelper() {}

    // ============================
    //  ModifierGroup — 按 Operation 分组的三值容器
    // ============================

    /**
     * 按 {@code Operation} 分组的属性加成值集合。
     *
     * <p>存储同一属性（如 {@code generic.attackDamage}）在不同 Operation 下的
     * 累加值。三个字段分别对应：</p>
     * <ul>
     *   <li>{@code op0} — 加法常数求和（初始 0，各 entry 的 Amount 累加）</li>
     *   <li>{@code op1} — 当前面板乘数求和（初始 0，各 entry 的 Amount 累加，应用时 +1）</li>
     *   <li>{@code op2} — 最终面板乘法因子求积（初始 1，各 entry 的 Amount 累乘）</li>
     * </ul>
     *
     * <p>完整公式：{@code ((base + op0) × (1 + op1)) × op2}</p>
     */
    public static class ModifierGroup
    {
        /** Operation 0 累加和（各 entry Amount 求和，初始 0） */
        public final float op0;

        /** Operation 1 累加和（各 entry Amount 求和，初始 0，应用时 ×(1+op1)） */
        public final float op1;

        /** Operation 2 累乘积（各 entry Amount 相乘，初始 1 = 无效果） */
        public final float op2;

        /** 零值/恒等实例（op0=0, op1=0, op2=1），避免重复创建 */
        private static final ModifierGroup ZERO = new ModifierGroup(0F, 0F, 1F);

        /**
         * 构造一组按 Operation 分组的值。
         *
         * @param _op0 加法常数和
         * @param _op1 当前面板乘数累加和（应用时 +1）
         * @param _op2 最终面板乘数和
         */
        public ModifierGroup(float _op0, float _op1, float _op2)
        {
            this.op0 = _op0;
            this.op1 = _op1;
            this.op2 = _op2;
        }

        /**
         * 按 Operation 顺序将 modifiers 应用到基础值。
         *
         * <p>公式：{@code ((base + op0) × (1 + op1)) × op2}</p>
         *
         * @param _base 基础面板值（如武器的 basic_damage）
         * @return 应用全部 modifier 后的最终值
         */
        public float applyTo(float _base)
        {
            float result = _base + op0;          // Step 1: base += Σop0
            result *= (1F + op1);                 // Step 2: base *= (1 + Σop1)
            result *= op2;                        // Step 3: base *= Πop2
            return result;
        }

        /**
         * 累加另一个 ModifierGroup，返回新实例。
         *
         * <p>op0/op1 求和，op2 求积。</p>
         *
         * @param _other 另一个分组值，可为 null（返回 this）
         * @return 合并后的新 ModifierGroup
         */
        public ModifierGroup add(ModifierGroup _other)
        {
            if (_other == null)
            {
                return this;
            }
            return new ModifierGroup(op0 + _other.op0, op1 + _other.op1, op2 * _other.op2);
        }

        /** @return 是否有任何非恒等加成（op0≠0 或 op1≠0 或 op2≠1） */
        public boolean hasAny()
        {
            return op0 != 0F || op1 != 0F || op2 != 1F;
        }
    }

    // ============================
    //  Result — 解析结果容器
    // ============================

    /**
     * {@link #read(ItemStack)} 的解析结果。
     *
     * <p>包含按属性名和 Operation 分类的全部加成。三个属性组分别对应：</p>
     * <ul>
     *   <li>{@code attackDamage} — {@code generic.attackDamage}</li>
     *   <li>{@code icePower} — {@code ice_power}</li>
     *   <li>{@code wandDamage} — {@code generic.WandDamage}</li>
     *   <li>{@code bowPower} — {@code bow_power}（弓/枪械物理伤害）</li>
     * </ul>
     */
    public static class Result
    {
        /** {@code generic.attackDamage} 的各 Operation 加成 */
        public final ModifierGroup attackDamage;

        /** {@code ice_power} 的各 Operation 加成 */
        public final ModifierGroup icePower;

        /** {@code generic.WandDamage} 的各 Operation 加成 */
        public final ModifierGroup wandDamage;

        /** {@code bow_power} 的各 Operation 加成（弓/枪械物理伤害） */
        public final ModifierGroup bowPower;

        /** 零值/恒等实例 */
        private static final Result ZERO = new Result(
            ModifierGroup.ZERO, ModifierGroup.ZERO, ModifierGroup.ZERO, ModifierGroup.ZERO);

        /**
         * 构造解析结果。
         *
         * @param _attackDamage 攻击力 modifier 组
         * @param _icePower     冰伤 modifier 组
         * @param _wandDamage   法杖伤 modifier 组
         * @param _bowPower     弓/枪械物理伤 modifier 组
         */
        public Result(ModifierGroup _attackDamage, ModifierGroup _icePower, ModifierGroup _wandDamage, ModifierGroup _bowPower)
        {
            this.attackDamage = _attackDamage;
            this.icePower     = _icePower;
            this.wandDamage   = _wandDamage;
            this.bowPower     = _bowPower;
        }

        /**
         * 累加另一个 Result，返回新 Result。
         *
         * <p>用于多次调用 {@link #read(ItemStack)} 后合并结果（如遍历护甲槽位）。</p>
         *
         * @param _other 另一个解析结果，可为 null
         * @return 累加后的新 Result
         */
        public Result add(Result _other)
        {
            if (_other == null)
            {
                return this;
            }
            return new Result(
                attackDamage.add(_other.attackDamage),
                icePower.add(_other.icePower),
                wandDamage.add(_other.wandDamage),
                bowPower.add(_other.bowPower));
        }

        /**
         * 返回三种属性的加法（op0 部分）总和，用于向后兼容。
         *
         * <p><b>注意</b>：此方法仅累加 op0，不含 op1/op2 乘法。
         * 需要完整计算请对各属性分别调用 {@link ModifierGroup#applyTo(float)}。</p>
         *
         * @return attackDamage.op0 + icePower.op0 + wandDamage.op0 + bowPower.op0
         */
        public float totalOp0()
        {
            return attackDamage.op0 + icePower.op0 + wandDamage.op0 + bowPower.op0;
        }

        /** @return 是否有任何加成（任一属性的任一 Operation 非零） */
        public boolean hasAny()
        {
            return attackDamage.hasAny() || icePower.hasAny() || wandDamage.hasAny() || bowPower.hasAny();
        }
    }

    // ============================
    //  解析方法
    // ============================

    /**
     * 从 ItemStack NBT 中读取 {@code AttributeModifiers} 伤害加成（含 Operation 分组）。
     *
     * <p>遍历 {@code AttributeModifiers/entries/} 下的所有 entry，
     * 读取 {@code AttributeName}、{@code Amount}、{@code Operation}，
     * 按 (属性名, Operation) 分类累加。</p>
     *
     * <p><b>安全调用</b>：传入 {@code null}、无 NBT、无对应标签均返回零值 Result。</p>
     *
     * @param _stack 武器 ItemStack，可为 null
     * @return 解析结果，attackDamage / icePower / wandDamage 各含 op0/op1/op2
     */
    public static Result read(ItemStack _stack)
    {
        if (_stack == null || !_stack.hasTagCompound())
        {
            return Result.ZERO;
        }

        NBTTagCompound root = _stack.getTagCompound();
        if (!root.hasKey(TAG_MODIFIERS))
        {
            return Result.ZERO;
        }

        // AttributeModifiers 可能是 NBTTagList（MC 原生）或 NBTTagCompound{entries}（CustomNPCs）
        NBTTagList entries;
        net.minecraft.nbt.NBTBase modifierTag = root.getTag(TAG_MODIFIERS);
        if (modifierTag instanceof NBTTagList)
        {
            // MC 原生格式：AttributeModifiers 直接是 NBTTagList
            entries = (NBTTagList) modifierTag;
        }
        else if (modifierTag instanceof NBTTagCompound)
        {
            // CustomNPCs 格式：AttributeModifiers 是 NBTTagCompound，内含 entries
            NBTTagCompound attr = (NBTTagCompound) modifierTag;
            if (!attr.hasKey(TAG_ENTRIES))
            {
                return Result.ZERO;
            }
            entries = attr.getTagList(TAG_ENTRIES);
        }
        else
        {
            return Result.ZERO;
        }

        // 四个属性各自按 Operation 累加（op0/op1 求和，op2 求积）
        float atk0 = 0F, atk1 = 0F, atk2 = 1F;
        float ice0 = 0F, ice1 = 0F, ice2 = 1F;
        float wnd0 = 0F, wnd1 = 0F, wnd2 = 1F;
        float bow0 = 0F, bow1 = 0F, bow2 = 1F;

        for (int i = 0; i < entries.tagCount(); i++)
        {
            NBTTagCompound entry = (NBTTagCompound) entries.tagAt(i);

            // 读取属性名
            String attrName = entry.getString(KEY_ATTR_NAME);
            if (attrName == null || attrName.isEmpty())
            {
                continue;
            }

            // 读取加成量（兼容 Double/Float/Int — CustomNPCs 可能写入不同数字类型）
            NBTBase amountTag = entry.getTag(KEY_AMOUNT);
            if (amountTag == null) { continue; }

            float amount;
            if (amountTag instanceof NBTTagDouble)
            {
                amount = (float) entry.getDouble(KEY_AMOUNT);
            }
            else if (amountTag instanceof NBTTagFloat)
            {
                amount = entry.getFloat(KEY_AMOUNT);
            }
            else if (amountTag instanceof NBTTagInt)
            {
                amount = (float) entry.getInteger(KEY_AMOUNT);
            }
            else
            {
                continue;  // 无法识别的数字类型，跳过此 entry
            }

            // 读取 Operation（不存在时默认为 0 = 加法）
            int operation = entry.hasKey(KEY_OPERATION) ? entry.getInteger(KEY_OPERATION) : 0;

            // 按属性名 + Operation 分类累加（0/1=求和, 2=求积）
            if (ATTR_ATTACK_DAMAGE.equals(attrName))
            {
                switch (operation)
                {
                    case 0: atk0 += amount; break;
                    case 1: atk1 += amount; break;  // op1 求和，应用时 ×(1+Σop1)
                    case 2: atk2 *= amount; break;
                }
            }
            else if (ATTR_ICE_POWER.equals(attrName))
            {
                switch (operation)
                {
                    case 0: ice0 += amount; break;
                    case 1: ice1 += amount; break;  // op1 求和，应用时 ×(1+Σop1)
                    case 2: ice2 *= amount; break;
                }
            }
            else if (ATTR_WAND_DAMAGE.equals(attrName))
            {
                switch (operation)
                {
                    case 0: wnd0 += amount; break;
                    case 1: wnd1 += amount; break;  // op1 求和，应用时 ×(1+Σop1)
                    case 2: wnd2 *= amount; break;
                }
            }
            else if (ATTR_BOW_POWER.equals(attrName))
            {
                switch (operation)
                {
                    case 0: bow0 += amount; break;
                    case 1: bow1 += amount; break;  // op1 求和，应用时 ×(1+Σop1)
                    case 2: bow2 *= amount; break;
                }
            }
            // 忽略其他未知属性
        }

        return new Result(
            new ModifierGroup(atk0, atk1, atk2),
            new ModifierGroup(ice0, ice1, ice2),
            new ModifierGroup(wnd0, wnd1, wnd2),
            new ModifierGroup(bow0, bow1, bow2));
    }

    /**
     * 便捷方法：只返回三种属性 op0 的总和。
     *
     * <p>等价于 {@code read(_stack).totalOp0()}。</p>
     */
    public static float readTotal(ItemStack _stack)
    {
        return read(_stack).totalOp0();
    }

    // ============================
    //  护甲遍历
    // ============================

    /**
     * 遍历玩家全身护甲槽位，累加所有护甲的 {@code AttributeModifiers} 伤害加成。
     *
     * <p>逐个读取 4 个护甲槽，对每个 ItemStack 调用 {@link #read(ItemStack)}，
     * 然后通过 {@link Result#add(Result)} 累加。</p>
     *
     * @param _player 玩家实体，可为 null（返回零值 Result）
     * @return 全身护甲属性加成的累计结果
     */
    public static Result readFromArmor(EntityPlayer _player)
    {
        if (_player == null)
        {
            return Result.ZERO;
        }

        // MC 1.6.4: InventoryPlayer.armorInventory (ItemStack[4])
        ItemStack[] armorSlots = _player.inventory.armorInventory;
        if (armorSlots == null || armorSlots.length == 0)
        {
            return Result.ZERO;
        }

        // 逐个槽位读取并累加
        Result total = Result.ZERO;
        int count = Math.min(armorSlots.length, ARMOR_SLOTS);
        for (int i = 0; i < count; i++)
        {
            total = total.add(read(armorSlots[i]));
        }

        return total;
    }

    /**
     * 一次性读取武器 + 护甲的全部属性加成。
     *
     * <p>等价于 {@code read(_weapon).add(readFromArmor(_player))}。</p>
     *
     * @param _weapon 武器 ItemStack，可为 null
     * @param _player 玩家实体，可为 null
     * @return 武器 + 全身护甲属性加成的合并结果
     */
    public static Result readAll(ItemStack _weapon, EntityPlayer _player)
    {
        return read(_weapon).add(readFromArmor(_player));
    }
}
