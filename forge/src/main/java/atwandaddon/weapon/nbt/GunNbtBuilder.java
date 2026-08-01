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
 * <h2>NBT 结构</h2>
 * <pre>
 * Gun NBT
 * ┌──────────────────────┐
 * │ theItem (子标签)      │  ← BasicItem: item_ID, item_internalName, item_icon, ...
 * │ duration             │  ← 耐久度
 * │ unbreakable          │  ← 不可破坏 (short)
 * │ charge_stage         │  ← 蓄力阶段数 (short)
 * │ shoot_number         │  ← 弹丸数 (int, 可选)
 * │ allowed_ammo         │  ← NBTTagList&lt;NBTTagString&gt; 弹药名列表
 * │ charge_icons         │  ← NBTTagList&lt;NBTTagString&gt; 蓄力图标列表 (可选)
 * │ fireArmAttr ───────────→ RangeAttackPacket.attr_id (全局注册)
 * └──────────────────────┘
 * </pre>
 *
 * @see AmmoNbtBuilder
 * @see RangeAttackPacket
 */
public class GunNbtBuilder {

    // ===== 全局 ID 计数器（与 AmmoNbtBuilder 共享范围 1000+） =====
    private static int nextAttrId   = 2000;
    private static int nextPacketId = 2000;

    // ===== 必填参数 =====
    private final int    itemId;
    private final String internalName;

    // ===== theItem 基础属性 =====
    private String icon        = "";
    private int    maxStack    = 1;     // 枪械默认 1
    private int    itemGroup   = 0;
    private String model       = null;
    private String description = null;

    // ===== 枪械属性 =====
    private int          durability    = 100;    // 耐久度（NBT: duration）
    private boolean      unbreakable   = false;
    private int          chargeStage   = 0;      // 蓄力阶段数
    private int          shootNumber   = 1;      // 弹丸数
    private List<String> allowedAmmo   = null;   // 可用弹药名列表
    private List<String> chargeIcons   = null;   // 蓄力图标列表

    // ===== RangeAttackPacket 参数 =====
    private boolean    gravity     = true;
    private boolean    penetrate   = false;
    private boolean    noeff       = false;
    private ShootMode  shootMode   = ShootMode.SEMI;
    private int        shootDelay  = 0;
    private int        chargeTime  = 40;
    private double     accuracy    = 1.0;
    private double     basicDamage = 4.0;
    private double     iceDamage   = 0.0;
    private double     knockback   = 0.0;
    private double     maxRange    = 30.0;
    private double     maxSpeed    = 20.0;
    private String     shootSound  = "bow";

    // ===== DamagePacket 参数（可选） =====
    private double   fluctuating   = 0.0;
    private double   bombDamage    = 0.0;
    private int      bombDelay     = 0;
    private String   bombSound     = null;
    private int      bombAmount    = 0;
    private int[]    effectWounded = null;
    private float[]  probWounded   = null;
    private int[]    effectAttacker = null;
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

    public GunNbtBuilder icon(String _icon)                         { this.icon = _icon; return this; }
    public GunNbtBuilder maxStack(int _maxStack)                    { this.maxStack = _maxStack; return this; }
    public GunNbtBuilder itemGroup(int _itemGroup)                  { this.itemGroup = _itemGroup; return this; }
    public GunNbtBuilder model(String _model)                       { this.model = _model; return this; }
    public GunNbtBuilder description(String _description)           { this.description = _description; return this; }

    // ==================== 枪械属性 ====================

    public GunNbtBuilder durability(int _durability)                { this.durability = _durability; return this; }
    public GunNbtBuilder unbreakable(boolean _unbreakable)          { this.unbreakable = _unbreakable; return this; }
    public GunNbtBuilder chargeStage(int _chargeStage)              { this.chargeStage = _chargeStage; return this; }
    public GunNbtBuilder shootNumber(int _shootNumber)              { this.shootNumber = _shootNumber; return this; }
    public GunNbtBuilder allowedAmmo(List<String> _allowedAmmo)     { this.allowedAmmo = _allowedAmmo; return this; }
    public GunNbtBuilder chargeIcons(List<String> _chargeIcons)     { this.chargeIcons = _chargeIcons; return this; }

    // ==================== RangeAttackPacket 参数 ====================

    public GunNbtBuilder gravity(boolean _gravity)                  { this.gravity = _gravity; return this; }
    public GunNbtBuilder penetrate(boolean _penetrate)              { this.penetrate = _penetrate; return this; }
    public GunNbtBuilder noeff(boolean _noeff)                      { this.noeff = _noeff; return this; }
    public GunNbtBuilder shootMode(ShootMode _shootMode)            { this.shootMode = _shootMode; return this; }
    public GunNbtBuilder shootDelay(int _shootDelay)                { this.shootDelay = _shootDelay; return this; }
    public GunNbtBuilder chargeTime(int _chargeTime)                { this.chargeTime = _chargeTime; return this; }
    public GunNbtBuilder accuracy(double _accuracy)                 { this.accuracy = _accuracy; return this; }
    public GunNbtBuilder basicDamage(double _basicDamage)           { this.basicDamage = _basicDamage; return this; }
    /** 🔥 冰穿甲伤害 — NBT 路径专属，JSON 路径硬编码为 0 */
    public GunNbtBuilder iceDamage(double _iceDamage)               { this.iceDamage = _iceDamage; return this; }
    public GunNbtBuilder knockback(double _knockback)               { this.knockback = _knockback; return this; }
    public GunNbtBuilder maxRange(double _maxRange)                 { this.maxRange = _maxRange; return this; }
    public GunNbtBuilder maxSpeed(double _maxSpeed)                 { this.maxSpeed = _maxSpeed; return this; }
    public GunNbtBuilder shootSound(String _shootSound)             { this.shootSound = _shootSound; return this; }

    // ==================== DamagePacket 参数 ====================

    public GunNbtBuilder fluctuating(double _fluctuating)           { this.fluctuating = _fluctuating; return this; }
    public GunNbtBuilder bombDamage(double _bombDamage)             { this.bombDamage = _bombDamage; return this; }
    public GunNbtBuilder bombDelay(int _bombDelay)                  { this.bombDelay = _bombDelay; return this; }
    public GunNbtBuilder bombSound(String _bombSound)               { this.bombSound = _bombSound; return this; }
    public GunNbtBuilder bombAmount(int _bombAmount)                { this.bombAmount = _bombAmount; return this; }
    public GunNbtBuilder effectWounded(int[] _effectWounded)        { this.effectWounded = _effectWounded; return this; }
    public GunNbtBuilder probWounded(float[] _probWounded)          { this.probWounded = _probWounded; return this; }
    public GunNbtBuilder effectAttacker(int[] _effectAttacker)      { this.effectAttacker = _effectAttacker; return this; }
    public GunNbtBuilder probAttacker(float[] _probAttacker)        { this.probAttacker = _probAttacker; return this; }

    // ==================== 构建 ====================

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

    private static NBTTagList floatArrayToList(float[] _arr) {
        NBTTagList list = new NBTTagList();
        for (float f : _arr) {
            list.appendTag(new net.minecraft.nbt.NBTTagFloat("", f));
        }
        return list;
    }
}
