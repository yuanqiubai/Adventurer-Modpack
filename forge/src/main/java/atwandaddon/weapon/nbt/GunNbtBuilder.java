package atwandaddon.weapon.nbt;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.gadgets.RangeAttackPacket;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;

/**
 * 枪械 NBT 构造器（Fluent Builder）。
 *
 * <p>与 {@link AmmoNbtBuilder} 同理，绕过引擎 JSON 路径的硬编码 bug
 * （{@code ice_damage=0.0f, gravity=true, penetrate=false} 等），
 * 使用 {@code BasicRangedWeapon(NBTTagCompound)} 构造器正确加载全部参数。</p>
 *
 * <h2>NBT 结构 &amp; 引擎加载链</h2>
 * <pre>
 * Gun NBTTagCompound
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
 * │ charge_stage           → 蓄力阶段数 (short)           │ ≥1 时须提供 charge_icons
 * │ shoot_number           → 每次射击弹丸数 (int)          │ 0 = 默认 1 发
 * │ allowed_ammo           → NBTTagList&lt;NBTTagString&gt;    │ 弹药内部名列表
 * │ charge_icons           → NBTTagList&lt;NBTTagString&gt;    │ 蓄力图标名列表 (可选)
 * │ fireArmAttr ───────────→ RangeAttackPacket.PACKETS   │ attr_id 全局注册引用
 * └──────────────────────────────────────────────────────┘
 *                              │
 *        RangeAttackPacket(NBTTagCompound) ← 引擎 NBT 构造时自动 getAttackPacket(attr_id)
 *                              │
 *              ┌───────────────┼───────────────┐
 *              │               │               │
 *         gravity         penetrate         noeff
 *         shoot_mode      shoot_delay       charge_time
 *         accuracy        basic_damage      ice_damage
 *         knockback       max_range          max_speed
 *         shoot_sound     onAttack ──→ DamagePacket.getPacket(id)
 * </pre>
 *
 * @see AmmoNbtBuilder
 * @see RangeAttackPacket
 */
public class GunNbtBuilder {

    // ===== 全局 ID 计数器（与 AmmoNbtBuilder 共享范围，避免碰撞） =====
    /** RangeAttackPacket 自增 attr_id，每把枪唯一 */
    private static int nextAttrId   = 2000;
    /** DamagePacket 自增 packet_id，每次创建新 DamagePacket 自增 */
    private static int nextPacketId = 2000;

    // ===== 必填参数 =====
    /** 游戏内物品 ID（int），通过 GameRegistry.registerItem 注册 */
    private final int    itemId;
    /** 物品内部名称，用于 setUnlocalizedName + ItemLoader 注册表索引 */
    private final String internalName;

    // ===== theItem 基础属性（对应 BasicItem NBT 构造器字段） =====
    /** 纹理图标文件名（不含扩展名），引擎自动拼接 "adventurer_engine:" 前缀 */
    private String icon        = "";
    /** 最大堆叠数，枪械默认 1（不可堆叠） */
    private int    maxStack    = 1;
    /** 创造模式标签页索引，0=默认。通过 TabsLoader.getTab(int) 解析 */
    private int    itemGroup   = 0;
    /** 3D 模型名称，null=无模型。由 hasModel() / getModelName() 读取 */
    private String model       = null;
    /** 物品描述（Lore），支持 \n 换行。通过 addInformation() 注入 tooltip */
    private String description = null;

    // ===== 枪械 NBT 字段（由 BasicRangedWeapon NBT 构造器直接读取） =====
    /** 耐久度（NBT key: duration），引擎通过 setMaxDamage(int) 设置 */
    private int          durability    = 100;
    /** 是否不可损坏（NBT key: unbreakable），short 0/1 */
    private boolean      unbreakable   = false;
    /** 蓄力阶段数（NBT key: charge_stage），≥1 时必须提供 charge_icons，否则 registerIcons NPE */
    private int          chargeStage   = 0;
    /** 每次射击弹丸数（NBT key: shoot_number），0→引擎 fallback 为 1 */
    private int          shootNumber   = 1;
    /** 可用弹药内部名列表（NBT key: allowed_ammo），引擎在 ItemLoader.ammoItem 全局注册表中查找 */
    private List<String> allowedAmmo   = null;
    /** 蓄力阶段图标列表（NBT key: charge_icons），与 charge_stage 数量对应 */
    private List<String> chargeIcons   = null;

    // ===== RangeAttackPacket 参数（引擎 shootOnce() → EntityBulletGeneral 行为控制） =====
    /** 子弹是否受重力影响（true=抛物线，false=直线飞行） */
    private boolean    gravity     = true;
    /** 子弹是否穿透（true=命中后不销毁，可穿透多个目标） */
    private boolean    penetrate   = false;
    /**
     * 禁止击退（no effect knockback）。true=子弹命中时不施加击退力。
     * 引擎逻辑：{@code if (knockbackStrength > 0 && !noeff) { 施加击退 }}
     * 弹药也可设置 noeff，最终取 gun.noeff || ammo.noeff
     */
    private boolean    noeff       = false;
    /** 射击模式：SEMI=半自动（每次右键一发），AUTO=全自动（按住连射） */
    private ShootMode  shootMode   = ShootMode.SEMI;
    /** 射击间隔（tick），SEMI 模式下两次射击间的最小间隔 */
    private int        shootDelay  = 0;
    /** 蓄力时间（tick），0=无蓄力瞬发。蓄力完成后可提升弹速比例 */
    private int        chargeTime  = 40;
    /** 精度 0.0~1.0，1.0=完美精准（无散布），越小散布越大 */
    private double     accuracy    = 1.0;
    /** 基础物理伤害（double），引擎 shootOnce() 注入 EntityBulletGeneral.setDamage() */
    private double     basicDamage = 4.0;
    /** 冰/穿甲伤害（double），独立于基础伤害的另一条伤害线（DamageSource="ice_damage"） */
    private double     iceDamage   = 0.0;
    /** 击退强度，0=无击退。引擎命中时取子弹速度向量的比例施加击退力 */
    private double     knockback   = 0.0;
    /** 最大射程（格），子弹飞行距离超过此值后自动销毁 */
    private double     maxRange    = 30.0;
    /** 最大弹速（格/秒），影响子弹飞行速度 */
    private double     maxSpeed    = 20.0;
    /** 射击音效名称，引擎通过 AdventurerEngine.proxy.addSound() 预注册 */
    private String     shootSound  = "bow";

    // ===== DamagePacket 参数（命中时对受伤者/攻击者施加的效果，可选，所有字段均可为 0/null） =====
    /**
     * 伤害浮动值（高斯分布标准差）。
     * 引擎 {@code PlayerTools.randomGaussian(basicDamage, flu)} 在伤害计算前引入随机波动。
     * 0=无浮动，值越大伤害波动越大。
     */
    private double   fluctuating   = 0.0;
    /** 炸弹伤害值，>0 时引擎命中后附加多段爆炸 */
    private double   bombDamage    = 0.0;
    /** 炸弹延迟（tick），每段爆炸间隔 */
    private int      bombDelay     = 0;
    /** 炸弹音效名，null=无特殊音效 */
    private String   bombSound     = null;
    /** 炸弹爆炸段数，引擎命中后生成 bombAmount 次延时爆炸 */
    private int      bombAmount    = 0;
    /**
     * 命中受伤者的药水效果，格式 {id, duration, amplifier, ...} 每 3 个 int 一组。
     * duration=持续时间(tick)，amplifier=等级（0=I级，-N=负等级/降抗）。
     * 引擎 parseEffects() 拆分为 PotionEffect[]，然后 buffGiver() 按概率施加。
     */
    private int[]    effectWounded = null;
    /** 命中受伤者效果的触发概率（0.0~1.0），与 effectWounded 一一对应。null=100% 全部施加 */
    private float[]  probWounded   = null;
    /** 施加于攻击者的药水效果，格式同 effectWounded */
    private int[]    effectAttacker = null;
    /** 攻击者效果的触发概率，格式同 probWounded */
    private float[]  probAttacker   = null;

    private GunNbtBuilder(int _itemId, String _internalName) {
        this.itemId = _itemId;
        this.internalName = _internalName;
    }

    /** 创建枪械 NBT 构造器 */
    public static GunNbtBuilder create(int _itemId, String _internalName) {
        return new GunNbtBuilder(_itemId, _internalName);
    }

    // ==================== theItem 基础属性 ====================

    /** 设置纹理图标文件名（不含扩展名），引擎自动补全为 "adventurer_engine:&lt;icon&gt;" */
    public GunNbtBuilder icon(String _icon)                         { this.icon = _icon; return this; }
    /** 设置最大堆叠数，枪械默认 1 */
    public GunNbtBuilder maxStack(int _maxStack)                    { this.maxStack = _maxStack; return this; }
    /** 设置创造标签页索引，通过 TabsLoader.getTab(int) 解析 */
    public GunNbtBuilder itemGroup(int _itemGroup)                  { this.itemGroup = _itemGroup; return this; }
    /** 设置 3D 模型名，null=无模型 */
    public GunNbtBuilder model(String _model)                       { this.model = _model; return this; }
    /** 设置物品描述（Lore），支持 \n 换行 */
    public GunNbtBuilder description(String _description)           { this.description = _description; return this; }

    // ==================== 枪械 NBT 字段 ====================

    /** 设置耐久度（引擎内部 key: duration） */
    public GunNbtBuilder durability(int _durability)                { this.durability = _durability; return this; }
    /** 设置是否不可损坏 */
    public GunNbtBuilder unbreakable(boolean _unbreakable)          { this.unbreakable = _unbreakable; return this; }
    /** 设置蓄力阶段数，≥1 时必须同时设置 chargeIcons() */
    public GunNbtBuilder chargeStage(int _chargeStage)              { this.chargeStage = _chargeStage; return this; }
    /** 设置每次射击弹丸数（0=引擎 fallback 为 1） */
    public GunNbtBuilder shootNumber(int _shootNumber)              { this.shootNumber = _shootNumber; return this; }
    /** 设置可用弹药列表，引擎在 ItemLoader.ammoItem 全局注册表中按名查找 */
    public GunNbtBuilder allowedAmmo(List<String> _allowedAmmo)     { this.allowedAmmo = _allowedAmmo; return this; }
    /** 设置蓄力阶段图标列表，与 charge_stage 数量一一对应 */
    public GunNbtBuilder chargeIcons(List<String> _chargeIcons)     { this.chargeIcons = _chargeIcons; return this; }

    // ==================== RangeAttackPacket 参数 ====================

    /** 设置子弹是否受重力（true=抛物线，false=直线） */
    public GunNbtBuilder gravity(boolean _gravity)                  { this.gravity = _gravity; return this; }
    /** 设置子弹是否穿透（可命中多个目标而不销毁） */
    public GunNbtBuilder penetrate(boolean _penetrate)              { this.penetrate = _penetrate; return this; }
    /**
     * 设置是否禁止击退。
     * true=命中时不施加击退力，即使 knockback&gt;0。
     * 最终值 = gun.noeff || ammo.noeff。
     */
    public GunNbtBuilder noeff(boolean _noeff)                      { this.noeff = _noeff; return this; }
    /** 设置射击模式（SEMI=半自动 / AUTO=全自动） */
    public GunNbtBuilder shootMode(ShootMode _shootMode)            { this.shootMode = _shootMode; return this; }
    /** 设置射击间隔（tick），SEMI 下两次射击的最小间隔 */
    public GunNbtBuilder shootDelay(int _shootDelay)                { this.shootDelay = _shootDelay; return this; }
    /** 设置蓄力时间（tick），0=瞬发无蓄力 */
    public GunNbtBuilder chargeTime(int _chargeTime)                { this.chargeTime = _chargeTime; return this; }
    /** 设置精度 0.0~1.0，1.0=完美精准无散布 */
    public GunNbtBuilder accuracy(double _accuracy)                 { this.accuracy = _accuracy; return this; }
    /** 设置基础物理伤害 */
    public GunNbtBuilder basicDamage(double _basicDamage)           { this.basicDamage = _basicDamage; return this; }
    /** 设置冰/穿甲伤害（独立于基础伤害的另一条伤害线，DamageSource="ice_damage"） */
    public GunNbtBuilder iceDamage(double _iceDamage)               { this.iceDamage = _iceDamage; return this; }
    /** 设置击退强度，命中时按子弹速度向量比例施加 */
    public GunNbtBuilder knockback(double _knockback)               { this.knockback = _knockback; return this; }
    /** 设置最大射程（格），超过此距离子弹自动销毁 */
    public GunNbtBuilder maxRange(double _maxRange)                 { this.maxRange = _maxRange; return this; }
    /** 设置最大弹速（格/秒） */
    public GunNbtBuilder maxSpeed(double _maxSpeed)                 { this.maxSpeed = _maxSpeed; return this; }
    /** 设置射击音效名，引擎自动预注册 */
    public GunNbtBuilder shootSound(String _shootSound)             { this.shootSound = _shootSound; return this; }

    // ==================== DamagePacket 参数 ====================

    /**
     * 设置伤害浮动值（高斯分布标准差）。
     * 引擎 causeDamage() 对 basicDamage 做 randomGaussian 波动，0=无波动。
     */
    public GunNbtBuilder fluctuating(double _fluctuating)           { this.fluctuating = _fluctuating; return this; }
    /** 设置炸弹伤害值，>0 时命中后触发延时爆炸 */
    public GunNbtBuilder bombDamage(double _bombDamage)             { this.bombDamage = _bombDamage; return this; }
    /** 设置炸弹间隔延迟（tick） */
    public GunNbtBuilder bombDelay(int _bombDelay)                  { this.bombDelay = _bombDelay; return this; }
    /** 设置炸弹音效名 */
    public GunNbtBuilder bombSound(String _bombSound)               { this.bombSound = _bombSound; return this; }
    /** 设置炸弹爆炸段数 */
    public GunNbtBuilder bombAmount(int _bombAmount)                { this.bombAmount = _bombAmount; return this; }
    /**
     * 设置命中受伤者的药水效果，格式 {id, duration, amplifier, ...} 每 3 个一组。
     * duration=tick，amplifier=等级（0=I级，负数=降抗/减益）。
     * 必须同时设置 probWounded() 对应概率。
     */
    public GunNbtBuilder effectWounded(int[] _effectWounded)        { this.effectWounded = _effectWounded; return this; }
    /** 设置命中受伤者效果的触发概率（0.0~1.0），数组长度与 effectWounded 一致 */
    public GunNbtBuilder probWounded(float[] _probWounded)          { this.probWounded = _probWounded; return this; }
    /** 设置命中时施加于攻击者自身的药水效果，格式同 effectWounded */
    public GunNbtBuilder effectAttacker(int[] _effectAttacker)      { this.effectAttacker = _effectAttacker; return this; }
    /** 设置攻击者效果的触发概率 */
    public GunNbtBuilder probAttacker(float[] _probAttacker)        { this.probAttacker = _probAttacker; return this; }

    // ==================== 构建 ====================

    /**
     * 构建完整枪械 NBT。
     *
     * <p>构建顺序：</p>
     * <ol>
     *   <li>若有 DamagePacket 参数 → new DamagePacket(nbt) 自动注册到全局 PACKETS</li>
     *   <li>new RangeAttackPacket(nbt) 自动注册到全局 PACKETS（引用上一步的 packet_id）</li>
     *   <li>构建枪械根 NBT，fireArmAttr 指向 RangeAttackPacket.attr_id</li>
     * </ol>
     *
     * <p>引擎加载时 {@code BasicRangedWeapon(NBTTagCompound)} 通过 fireArmAttr
     * → RangeAttackPacket.getAttackPacket(id) → 获取 gravity/penetrate/.../onAttack 全部属性。</p>
     *
     * @return 可直接传入 {@code new BasicRangedWeapon(nbt)} 的完整枪械 NBT
     */
    public NBTTagCompound build() {
        int attrId   = nextAttrId++;
        int packetId = 0;

        // Step 1: DamagePacket（可选）
        if (fluctuating > 0.0 || bombDamage > 0.0
                || effectWounded != null || effectAttacker != null) {
            packetId = nextPacketId++;
            buildDamagePacket(packetId);
        }

        // Step 2: RangeAttackPacket（全局注册）
        buildRangeAttackPacket(attrId, packetId);

        // Step 3: 枪械 NBT
        return buildGunNbt(attrId);
    }

    /**
     * 创建 RangeAttackPacket 并注册到全局 PACKETS。
     *
     * <p>RangeAttackPacket(NBTTagCompound) 构造器在末尾自动执行
     * {@code PACKETS.put(attr_id, this)}，后续引擎通过 fireArmAttr 查找。</p>
     *
     * @param _attrId   attr_id，全局唯一
     * @param _packetId 关联的 DamagePacket.packet_id，0=无关联
     */
    private void buildRangeAttackPacket(int _attrId, int _packetId) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("attr_id", _attrId);
        nbt.setShort  ("gravity",      gravity    ? (short) 1 : (short) 0);
        nbt.setShort  ("penetrate",    penetrate  ? (short) 1 : (short) 0);
        nbt.setShort  ("noeff",        noeff      ? (short) 1 : (short) 0);
        nbt.setShort  ("shoot_mode",   (short) (shootMode == ShootMode.AUTO ? 1 : 0));
        nbt.setInteger("shoot_delay",  shootDelay);
        nbt.setInteger("charge_time",  chargeTime);
        nbt.setDouble ("accuracy",     accuracy);
        nbt.setDouble ("basic_damage", basicDamage);
        nbt.setDouble ("ice_damage",   iceDamage);
        nbt.setDouble ("knockback",    knockback);
        nbt.setDouble ("max_range",    maxRange);
        nbt.setDouble ("max_speed",    maxSpeed);
        nbt.setString ("shoot_sound",  shootSound);
        if (_packetId > 0) {
            nbt.setInteger("attack_packet", _packetId);
        }
        new RangeAttackPacket(nbt);
    }

    /**
     * 创建 DamagePacket 并注册到全局 PACKETS。
     *
     * <p>DamagePacket(NBTTagCompound) 构造器末尾自动执行
     * {@code PACKETS.put(packet_id, this)}。</p>
     *
     * <p>引擎命中流程：EntityBulletGeneral.onUpdate() 检测 dmgPack != null →
     * dmgPack.causeDamage(shooter, target, basicDamage, iceDamage, source)
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
     * 构建枪械根 NBT（包含 theItem 子标签）。
     *
     * <p>引擎 BasicRangedWeapon(NBTTagCompound) 加载链：</p>
     * <ol>
     *   <li>{@code super(nbt.getCompoundTag("theItem"))} → BasicItem 基础属性</li>
     *   <li>{@code nbt.getInteger("fireArmAttr")} → RangeAttackPacket.getAttackPacket(id) → 全部弹道属性</li>
     * </ol>
     *
     * @param _attrId RangeAttackPacket 的 attr_id
     * @return 枪械根 NBT
     */
    private NBTTagCompound buildGunNbt(int _attrId) {
        NBTTagCompound nbt = new NBTTagCompound();

        // ---- theItem ----
        NBTTagCompound theItem = new NBTTagCompound();
        theItem.setInteger("item_ID",           itemId);
        theItem.setInteger("item_group",        itemGroup);
        theItem.setString ("item_internalName", internalName);
        theItem.setString ("item_icon",         icon);
        theItem.setInteger("item_maxstack",     maxStack);
        if (description != null)  theItem.setString("item_description", description);
        if (model != null)        theItem.setString("item_model", model);
        nbt.setCompoundTag("theItem", theItem);

        // ---- 枪械字段 ----
        nbt.setInteger("duration",     durability);
        nbt.setShort  ("unbreakable",  unbreakable ? (short) 1 : (short) 0);
        nbt.setShort  ("charge_stage", (short) chargeStage);
        nbt.setInteger("shoot_number", shootNumber);

        // allowed_ammo → NBTTagList<NBTTagString>
        if (allowedAmmo != null && !allowedAmmo.isEmpty()) {
            NBTTagList ammoList = new NBTTagList();
            for (String ammo : allowedAmmo) {
                ammoList.appendTag(new NBTTagString("", ammo));
            }
            nbt.setTag("allowed_ammo", ammoList);
        }

        // charge_icons → NBTTagList<NBTTagString>（可选）
        if (chargeIcons != null && !chargeIcons.isEmpty()) {
            NBTTagList iconList = new NBTTagList();
            for (String ico : chargeIcons) {
                iconList.appendTag(new NBTTagString("", ico));
            }
            nbt.setTag("charge_icons", iconList);
        }

        nbt.setInteger("fireArmAttr", _attrId);
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
