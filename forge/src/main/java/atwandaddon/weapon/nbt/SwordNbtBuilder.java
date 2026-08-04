package atwandaddon.weapon.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.adventurer_engine.common.gadgets.DamagePacket;

/**
 * 近战武器 NBT 构造器（Fluent Builder）。
 *
 * <p>与 {@link GunNbtBuilder} 同理，绕过引擎 JSON 路径的硬编码 bug
 * （{@code ice_damage=0.0f} 等），使用 {@code BasicItemSword(NBTTagCompound)} 构造器正确加载全部参数。</p>
 *
 * <h2>NBT 结构 &amp; 引擎加载链</h2>
 * <pre>
 * Sword NBTTagCompound
 * ┌──────────────────────────────────────────────────────┐
 * │ theItem (NBTTagCompound 子标签)                       │
 * │   ├─ item_ID            → Item.itemID                │
 * │   ├─ item_group         → TabsLoader.getTab(int)     │ 创造标签页索引
 * │   ├─ item_internalName  → Item.setUnlocalizedName()  │ 内部名称
 * │   ├─ item_icon          → IconRegister("adventurer_engine:" + icon) │
 * │   ├─ item_maxstack      → Item.maxStackSize          │
 * │   ├─ item_description   → addInformation() lore文本  │ 支持 \n 换行
 * │   └─ item_model         → hasModel() / getModelName()│ 3D 模型名
 * ├──────────────────────────────────────────────────────┤
 * │ duration               → setMaxDamage()              │ 耐久度 (int)
 * │ unbreakable            → 是否不可损坏 (short 0/1)      │
 * │ basic_damage           → 基础物理伤害 (double)         │ 近战平A伤害
 * │ ice_damage             → 冰/穿甲伤害 (double, 可选)    │ 穿透护甲的额外伤害线
 * │ attack_packet          → DamagePacket ID (int, 可选)  │ 命中附加效果（药水/炸弹等）
 * └──────────────────────────────────────────────────────┘
 *                              │
 *     BasicItemSword(NBTTagCompound) 构造器：
 *       super(nbt.getCompound("theItem"))  → 基础属性
 *       nbt.getInteger("duration")         → 耐久
 *       nbt.getShort("unbreakable")        → 不可损坏
 *       nbt.getDouble("basic_damage")      → 基础伤害
 *       nbt.getDouble("ice_damage")        → 冰穿甲伤害（可选，默认 0）
 *       nbt.getInteger("attack_packet")    → DamagePacket ID（可选，null=无附加效果）
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * public class MySword extends BasicItemSword {
 *     public MySword(int _id) {
 *         super(SwordNbtBuilder.create(_id, "my_sword")
 *             .icon("my_sword")
 *             .durability(40)
 *             .basicDamage(6.0)
 *             .iceDamage(12.0)
 *             .build());
 *     }
 * }
 * }</pre>
 *
 * @see GunNbtBuilder
 * @see AmmoNbtBuilder
 * @see com.adventurer_engine.common.items.blank.BasicItemSword
 */
public class SwordNbtBuilder {

    // ===== 全局 ID 计数器 =====
    /** DamagePacket 自增 packet_id，每次创建含附加效果的 DamagePacket 自增 */
    private static int nextPacketId = 3000;

    // ===== 必填参数 =====
    /** 游戏内物品 ID（int），通过 GameRegistry.registerItem 注册 */
    private final int    itemId;
    /** 物品内部名称，用于 setUnlocalizedName + ItemLoader 注册表索引 */
    private final String internalName;

    // ===== theItem 基础属性（对应 BasicItem NBT 构造器字段） =====
    /** 纹理图标文件名（不含扩展名），引擎自动拼接 "adventurer_engine:" 前缀 */
    private String icon        = "";
    /** 最大堆叠数，近战武器默认 1（不可堆叠） */
    private int    maxStack    = 1;
    /** 创造模式标签页索引，0=默认。通过 TabsLoader.getTab(int) 解析 */
    private int    itemGroup   = 0;
    /** 3D 模型名称，null=无模型。由 hasModel() / getModelName() 读取 */
    private String model       = null;
    /** 物品描述（Lore），支持 \n 换行。通过 addInformation() 注入 tooltip */
    private String description = null;

    // ===== 近战武器 NBT 字段（由 BasicItemSword NBT 构造器直接读取） =====
    /** 耐久度（NBT key: duration），引擎通过 setMaxDamage(int) 设置 */
    private int     durability   = 100;
    /** 是否不可损坏（NBT key: unbreakable），short 0/1 */
    private boolean unbreakable  = false;
    /** 基础物理伤害（NBT key: basic_damage），近战平A伤害值 */
    private double  basicDamage  = 4.0;
    /** 冰/穿甲伤害（NBT key: ice_damage），独立于基础伤害的另一条伤害线（DamageSource="ice_damage"） */
    private double  iceDamage    = 0.0;

    // ===== DamagePacket 参数（命中时对受伤者/攻击者施加的效果，可选，所有字段均可为 0/null） =====
    /**
     * 伤害浮动值（高斯分布标准差）。
     * 引擎 {@code PlayerTools.randomGaussian(basicDamage, flu)} 在伤害计算前引入随机波动。
     * 0=无浮动，值越大伤害波动越大。
     */
    private double   fluctuating     = 0.0;
    /** 炸弹伤害值，>0 时引擎命中后附加多段爆炸 */
    private double   bombDamage      = 0.0;
    /** 炸弹延迟（tick），每段爆炸间隔 */
    private int      bombDelay       = 0;
    /** 炸弹音效名，null=无特殊音效 */
    private String   bombSound       = null;
    /** 炸弹爆炸段数，引擎命中后生成 bombAmount 次延时爆炸 */
    private int      bombAmount      = 0;
    /**
     * 命中受伤者的药水效果，格式 {id, duration, amplifier, ...} 每 3 个 int 一组。
     * duration=持续时间(tick)，amplifier=等级（0=I级，-N=负等级/降抗）。
     * 引擎 parseEffects() 拆分为 PotionEffect[]，然后 buffGiver() 按概率施加。
     */
    private int[]    effectWounded   = null;
    /** 命中受伤者效果的触发概率（0.0~1.0），与 effectWounded 一一对应。null=100% 全部施加 */
    private float[]  probWounded     = null;
    /** 施加于攻击者的药水效果，格式同 effectWounded */
    private int[]    effectAttacker  = null;
    /** 攻击者效果的触发概率，格式同 probWounded */
    private float[]  probAttacker    = null;

    /**
     * 私有构造器，通过 {@link #create(int, String)} 创建实例。
     *
     * @param _itemId       物品数字 ID
     * @param _internalName 内部注册名
     */
    private SwordNbtBuilder(int _itemId, String _internalName) {
        this.itemId = _itemId;
        this.internalName = _internalName;
    }

    /** 创建近战武器 NBT 构造器 */
    public static SwordNbtBuilder create(int _itemId, String _internalName) {
        return new SwordNbtBuilder(_itemId, _internalName);
    }

    // ==================== theItem 基础属性 ====================

    /** 设置纹理图标文件名（不含扩展名），引擎自动补全为 "adventurer_engine:&lt;icon&gt;" */
    public SwordNbtBuilder icon(String _icon)                        { this.icon = _icon; return this; }
    /** 设置最大堆叠数，近战武器默认 1 */
    public SwordNbtBuilder maxStack(int _maxStack)                   { this.maxStack = _maxStack; return this; }
    /** 设置创造标签页索引，通过 TabsLoader.getTab(int) 解析 */
    public SwordNbtBuilder itemGroup(int _itemGroup)                 { this.itemGroup = _itemGroup; return this; }
    /** 设置 3D 模型名，null=无模型 */
    public SwordNbtBuilder model(String _model)                      { this.model = _model; return this; }
    /** 设置物品描述（Lore），支持 \n 换行 */
    public SwordNbtBuilder description(String _description)          { this.description = _description; return this; }

    // ==================== 近战武器属性 ====================

    /** 设置耐久度（引擎内部 key: duration） */
    public SwordNbtBuilder durability(int _durability)               { this.durability = _durability; return this; }
    /** 设置是否不可损坏 */
    public SwordNbtBuilder unbreakable(boolean _unbreakable)         { this.unbreakable = _unbreakable; return this; }
    /** 设置基础物理伤害（近战平A伤害值） */
    public SwordNbtBuilder basicDamage(double _basicDamage)          { this.basicDamage = _basicDamage; return this; }
    /** 设置冰/穿甲伤害（独立于基础伤害的另一条伤害线，DamageSource="ice_damage"） */
    public SwordNbtBuilder iceDamage(double _iceDamage)              { this.iceDamage = _iceDamage; return this; }

    // ==================== DamagePacket 参数 ====================

    /**
     * 设置伤害浮动值（高斯分布标准差）。
     * 引擎 causeDamage() 对 basicDamage 做 randomGaussian 波动，0=无波动。
     */
    public SwordNbtBuilder fluctuating(double _fluctuating)          { this.fluctuating = _fluctuating; return this; }
    /** 设置炸弹伤害值，>0 时命中后触发延时爆炸 */
    public SwordNbtBuilder bombDamage(double _bombDamage)            { this.bombDamage = _bombDamage; return this; }
    /** 设置炸弹间隔延迟（tick） */
    public SwordNbtBuilder bombDelay(int _bombDelay)                 { this.bombDelay = _bombDelay; return this; }
    /** 设置炸弹音效名 */
    public SwordNbtBuilder bombSound(String _bombSound)              { this.bombSound = _bombSound; return this; }
    /** 设置炸弹爆炸段数 */
    public SwordNbtBuilder bombAmount(int _bombAmount)               { this.bombAmount = _bombAmount; return this; }
    /**
     * 设置命中受伤者的药水效果，格式 {id, duration, amplifier, ...} 每 3 个一组。
     * duration=tick，amplifier=等级（0=I级，负数=降抗/减益）。
     * 必须同时设置 probWounded() 对应概率。
     */
    public SwordNbtBuilder effectWounded(int[] _effectWounded)       { this.effectWounded = _effectWounded; return this; }
    /** 设置命中受伤者效果的触发概率（0.0~1.0），数组长度与 effectWounded 一致 */
    public SwordNbtBuilder probWounded(float[] _probWounded)         { this.probWounded = _probWounded; return this; }
    /** 设置命中时施加于攻击者自身的药水效果，格式同 effectWounded */
    public SwordNbtBuilder effectAttacker(int[] _effectAttacker)     { this.effectAttacker = _effectAttacker; return this; }
    /** 设置攻击者效果的触发概率 */
    public SwordNbtBuilder probAttacker(float[] _probAttacker)       { this.probAttacker = _probAttacker; return this; }

    // ==================== 构建 ====================

    /**
     * 构建完整近战武器 NBT。
     *
     * <p>构建顺序：</p>
     * <ol>
     *   <li>若有 DamagePacket 参数 → new DamagePacket(nbt) 自动注册到全局 PACKETS</li>
     *   <li>构建近战武器根 NBT（含 theItem 子标签 + 伤害/耐久字段）</li>
     * </ol>
     *
     * <p>引擎加载时 {@code BasicItemSword(NBTTagCompound)} 直接读取根 NBT 字段：</p>
     * <ul>
     *   <li>{@code super(nbt.getCompoundTag("theItem"))} → BasicItem 基础属性</li>
     *   <li>{@code nbt.getInteger("duration")} → 耐久</li>
     *   <li>{@code nbt.getDouble("basic_damage")} → 基础伤害</li>
     *   <li>{@code nbt.getDouble("ice_damage")} → 冰穿甲伤害（可选）</li>
     *   <li>{@code nbt.getInteger("attack_packet")} → DamagePacket ID（可选）</li>
     * </ul>
     *
     * @return 可直接传入 {@code new BasicItemSword(nbt)} 的完整近战武器 NBT
     */
    public NBTTagCompound build() {
        int packetId = 0;

        // Step 1: DamagePacket（可选，仅在设置了附加效果时创建）
        if (fluctuating > 0.0 || bombDamage > 0.0
                || effectWounded != null || effectAttacker != null) {
            packetId = nextPacketId++;
            buildDamagePacket(packetId);
        }

        // Step 2: 近战武器根 NBT
        return buildSwordNbt(packetId);
    }

    /**
     * 创建 DamagePacket 并注册到全局 PACKETS。
     *
     * <p>DamagePacket(NBTTagCompound) 构造器末尾自动执行
     * {@code PACKETS.put(packet_id, this)}。</p>
     *
     * <p>引擎近战命中流程：BasicItemSword.a() 拦截攻击事件 →
     * 若 onAttack != null → onAttack.causeDamage(attacker, wounded, 0.0, ice_damage, null)
     * → 内部先 randomGaussian 浮动伤害 → 再 buffGiver 施加药水效果 → 最后 causeDamage。</p>
     *
     * @param _packetId packet_id，全局唯一
     */
    private void buildDamagePacket(int _packetId) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("packet_id", _packetId);
        if (fluctuating > 0.0)  nbt.setDouble("fluctuating", fluctuating);
        if (bombDamage > 0.0) {
            nbt.setDouble("bomb_damage", bombDamage);
            nbt.setInteger("bomb_delay",  bombDelay);
            nbt.setInteger("bomb_amount", bombAmount);
            if (bombSound != null) nbt.setString("bomb_sound", bombSound);
        }
        if (effectWounded != null)  nbt.setIntArray("effect_wounded", effectWounded);
        if (probWounded != null)    nbt.setTag("p_effect_wounded", floatArrayToList(probWounded));
        if (effectAttacker != null) nbt.setIntArray("effect_attacker", effectAttacker);
        if (probAttacker != null)   nbt.setTag("p_effect_attacker", floatArrayToList(probAttacker));
        new DamagePacket(nbt);
    }

    /**
     * 构建近战武器根 NBT（包含 theItem 子标签 + 伤害/耐久字段）。
     *
     * <p>引擎 BasicItemSword(NBTTagCompound) 加载链：</p>
     * <ol>
     *   <li>{@code super(nbt.getCompoundTag("theItem"))} → BasicItem 基础属性</li>
     *   <li>{@code nbt.getInteger("duration")} → 耐久度</li>
     *   <li>{@code nbt.getDouble("basic_damage")} → 基础伤害</li>
     *   <li>{@code nbt.getDouble("ice_damage")} → 冰穿甲伤害（可选）</li>
     *   <li>{@code nbt.getInteger("attack_packet")} → DamagePacket ID（可选）</li>
     * </ol>
     *
     * @param _packetId 关联的 DamagePacket ID，0=无关联
     * @return 近战武器根 NBT
     */
    private NBTTagCompound buildSwordNbt(int _packetId) {
        NBTTagCompound nbt = new NBTTagCompound();

        // ---- theItem（BasicItem 基础属性子标签）----
        NBTTagCompound theItem = new NBTTagCompound();
        theItem.setInteger("item_ID",           itemId);
        theItem.setInteger("item_group",        itemGroup);
        theItem.setString ("item_internalName", internalName);
        theItem.setString ("item_icon",         icon);
        theItem.setInteger("item_maxstack",     maxStack);
        if (description != null)  theItem.setString("item_description", description);
        if (model != null)        theItem.setString("item_model", model);
        nbt.setCompoundTag("theItem", theItem);

        // ---- 近战武器字段 ----
        nbt.setInteger("duration",     durability);
        nbt.setShort  ("unbreakable",  unbreakable ? (short) 1 : (short) 0);
        nbt.setDouble ("basic_damage", basicDamage);
        // ice_damage 仅在 > 0 时写入（与引擎读逻辑一致：nbt.hasKey("ice_damage") ? getDouble : 0.0f）
        if (iceDamage > 0.0) {
            nbt.setDouble("ice_damage", iceDamage);
        }
        // attack_packet 仅在有关联 DamagePacket 时写入
        if (_packetId > 0) {
            nbt.setInteger("attack_packet", _packetId);
        }

        return nbt;
    }

    /**
     * 将 float[] 转换为 NBTTagList&lt;NBTTagFloat&gt;。
     * 引擎 DamagePacket NBT 构造器中 p_effect_wounded / p_effect_attacker 的存储格式。
     */
    private static NBTTagList floatArrayToList(float[] _arr) {
        NBTTagList list = new NBTTagList();
        for (float f : _arr) {
            list.appendTag(new net.minecraft.nbt.NBTTagFloat("", f));
        }
        return list;
    }
}
