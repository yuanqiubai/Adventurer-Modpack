package atwandaddon.weapon.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.gadgets.RangeAttackPacket;
import com.adventurer_engine.common.items.blank.ranged_weapon.ShootMode;

/**
 * 弹药 NBT 构造器（Fluent Builder）。
 *
 * <h2>为什么需要 NBT 路径？</h2>
 * adventurer_engine 的 JSON 构造器 {@code BasicAmmo(int, JsonHelper, String)}
 * 硬编码 {@code this.ice_damage = 0.0f}，导致穿甲伤害、穿透等参数无法生效。
 * NBT 构造器 {@code BasicAmmo(NBTTagCompound)} 则能正确读取全部参数。
 *
 * <h2>NBT 结构（三部分组成）</h2>
 * <pre>
 * RangeAttackPacket (全局注册, 先创建)       DamagePacket (全局注册, 可选)
 * ┌──────────────────────────┐              ┌──────────────────────┐
 * │ attr_id  ←── fireArmAttr  │              │ packet_id            │
 * │ gravity, penetrate, noeff │              │ fluctuating          │
 * │ shoot_mode, shoot_delay   │              │ bomb_*               │
 * │ accuracy, basic_damage    │──attack_packet──→                    │
 * │ ice_damage, knockback     │              │ effect_wounded       │
 * │ max_range, max_speed      │              │ effect_attacker      │
 * │ charge_time, shoot_sound  │              └──────────────────────┘
 * └──────────────────────────┘
 *
 *                              Ammo NBT
 *                       ┌──────────────────┐
 *                       │ theItem (子标签)  │
 *                       │ unbreakable      │
 *                       │ bullet_num       │
 *                       │ duration         │
 *                       │ entity_model     │
 *                       │ fly_fx, hit_fx   │
 *                       │ fireArmAttr ────────→ RangeAttackPacket.attr_id
 *                       └──────────────────┘
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * public SnowEagleAP(int _id) {
 *     super(AmmoNbtBuilder.create(_id, "snow_eagle_ap")
 *         .icon("snow_eagle_ap")
 *         .maxStack(64)
 *         .bulletNum(1).ammoCapacity(1)
 *         .entityModel("bullet")
 *         .gravity(false).penetrate(true)
 *         .shootMode(ShootMode.SEMI)
 *         .shootDelay(1).chargeTime(2)
 *         .accuracy(1.0).basicDamage(8.0).iceDamage(6.0)
 *         .knockback(0.5).maxRange(80.0).maxSpeed(6.0)
 *         .shootSound("bow_zx")
 *         .fluctuating(16.0)
 *         .build());
 * }
 * }</pre>
 *
 * @see RangeAttackPacket
 * @see DamagePacket
 * @see com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo#BasicAmmo(NBTTagCompound)
 */
public class AmmoNbtBuilder {

    // ===== 全局 ID 计数器（避免与引擎 test.dat 中的 1~50 冲突） =====
    private static int nextAttrId   = 1000; // RangeAttackPacket.attr_id
    private static int nextPacketId = 1000; // DamagePacket.packet_id

    // ===== 必填参数 =====
    private final int    itemId;
    private final String internalName;

    // ===== theItem 基础属性（带默认值） =====
    private String icon        = "";
    private int    maxStack    = 64;
    private int    itemGroup   = 0;    // 创造标签页组 ID，0 = 稍后手动 setCreativeTab()
    private String model       = null; // 手持模型（null = 不写入）
    private String description = null; // 物品描述（null = 不写入）

    // ===== 弹药属性 =====
    private boolean   unbreakable  = false;
    private int       bulletNum    = 1;       // 弹丸数
    private int       ammoCapacity = 1;       // 单格弹药容量（NBT: duration）
    private String    entityModel  = "arrow"; // 弹丸模型: "arrow" / "bullet" / "item_XXXXX"
    private String    flyFx        = "";      // 飞行粒子（空串 = 无）
    private String    hitFx        = "";      // 命中粒子（空串 = 无）

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
    private double  fluctuating  = 0.0;  // 高斯波动方差（0 = 不创建 DamagePacket）
    private double  bombDamage   = 0.0;
    private int     bombDelay    = 0;
    private String  bombSound    = null;
    private int     bombAmount   = 0;
    private int[]   effectWounded = null;   // [id, dur, amp, ...] xN 组
    private float[] probWounded   = null;   // 对应概率数组
    private int[]   effectAttacker = null;
    private float[] probAttacker   = null;

    // ===== 私有构造器（通过 create() 工厂方法调用） =====
    private AmmoNbtBuilder(int _itemId, String _internalName) {
        this.itemId = _itemId;
        this.internalName = _internalName;
    }

    /**
     * 创建一个新的弹药 NBT 构造器。
     *
     * @param _itemId       物品数字 ID（如 26001）
     * @param _internalName 内部注册名，同时也是 ammoItem 注册表的 key
     */
    public static AmmoNbtBuilder create(int _itemId, String _internalName) {
        return new AmmoNbtBuilder(_itemId, _internalName);
    }

    // ==================== theItem 基础属性 ====================

    /** 纹理图标名（不含域前缀，引擎自动加 adventurer_engine:） */
    public AmmoNbtBuilder icon(String _icon) {
        this.icon = _icon;
        return this;
    }

    /** 最大堆叠数，默认 64 */
    public AmmoNbtBuilder maxStack(int _maxStack) {
        this.maxStack = _maxStack;
        return this;
    }

    /**
     * 创造标签页组 ID。
     * 默认 0（表示不归属引擎内置标签），后续可在注册时调用 {@code .setCreativeTab()} 覆盖。
     */
    public AmmoNbtBuilder itemGroup(int _itemGroup) {
        this.itemGroup = _itemGroup;
        return this;
    }

    /** 手持模型名，如 {@code "techne:my_gun"}。默认 null = 不写入 */
    public AmmoNbtBuilder model(String _model) {
        this.model = _model;
        return this;
    }

    /** 物品描述文本（支持 § 颜色码）。默认 null = 不写入 */
    public AmmoNbtBuilder description(String _description) {
        this.description = _description;
        return this;
    }

    // ==================== 弹药属性 ====================

    /** 不可破坏（无限耐久），默认 false */
    public AmmoNbtBuilder unbreakable(boolean _unbreakable) {
        this.unbreakable = _unbreakable;
        return this;
    }

    /** 每次射击弹丸数（霰弹），默认 1 */
    public AmmoNbtBuilder bulletNum(int _bulletNum) {
        this.bulletNum = _bulletNum;
        return this;
    }

    /** 单格弹药容量（NBT key: duration），默认 1 */
    public AmmoNbtBuilder ammoCapacity(int _ammoCapacity) {
        this.ammoCapacity = _ammoCapacity;
        return this;
    }

    /**
     * 弹丸渲染模型。
     * <ul>
     * <li>{@code "arrow"} — 手绘 6 面体（默认）</li>
     * <li>{@code "bullet"} — 3D Techne 模型</li>
     * <li>{@code "item_XXXXX"} — 物品图标 billboard（XXXXX = 物品 ID）</li>
     * </ul>
     */
    public AmmoNbtBuilder entityModel(String _entityModel) {
        this.entityModel = _entityModel;
        return this;
    }

    /** 飞行粒子特效名。空串 = 无效果 */
    public AmmoNbtBuilder flyFx(String _flyFx) {
        this.flyFx = _flyFx;
        return this;
    }

    /** 命中粒子特效名。空串 = 无效果 */
    public AmmoNbtBuilder hitFx(String _hitFx) {
        this.hitFx = _hitFx;
        return this;
    }

    // ==================== RangeAttackPacket 参数 ====================

    /** 弹丸受重力，默认 true */
    public AmmoNbtBuilder gravity(boolean _gravity) {
        this.gravity = _gravity;
        return this;
    }

    /** 弹丸穿透目标，默认 false */
    public AmmoNbtBuilder penetrate(boolean _penetrate) {
        this.penetrate = _penetrate;
        return this;
    }

    /** 无击退效果，默认 false */
    public AmmoNbtBuilder noeff(boolean _noeff) {
        this.noeff = _noeff;
        return this;
    }

    /** 射击模式：{@link ShootMode#SEMI} 半自动 / {@link ShootMode#AUTO} 全自动 */
    public AmmoNbtBuilder shootMode(ShootMode _shootMode) {
        this.shootMode = _shootMode;
        return this;
    }

    /** 射击间隔（tick），默认 0 */
    public AmmoNbtBuilder shootDelay(int _shootDelay) {
        this.shootDelay = _shootDelay;
        return this;
    }

    /** 蓄力时间（tick），默认 40 */
    public AmmoNbtBuilder chargeTime(int _chargeTime) {
        this.chargeTime = _chargeTime;
        return this;
    }

    /** 精度（0.0 = 最差，1.0 = 完美），默认 1.0 */
    public AmmoNbtBuilder accuracy(double _accuracy) {
        this.accuracy = _accuracy;
        return this;
    }

    /** 基础物理伤害，默认 4.0 */
    public AmmoNbtBuilder basicDamage(double _basicDamage) {
        this.basicDamage = _basicDamage;
        return this;
    }

    /**
     * 冰属性穿甲伤害（独立计算护甲）。
     * <p>🔥 这是 NBT 路径的核心优势——JSON 路径硬编码为 0！
     */
    public AmmoNbtBuilder iceDamage(double _iceDamage) {
        this.iceDamage = _iceDamage;
        return this;
    }

    /** 击退强度，默认 0.0 */
    public AmmoNbtBuilder knockback(double _knockback) {
        this.knockback = _knockback;
        return this;
    }

    /** 最大射程（方块），默认 30.0 */
    public AmmoNbtBuilder maxRange(double _maxRange) {
        this.maxRange = _maxRange;
        return this;
    }

    /** 弹丸速度，默认 20.0 */
    public AmmoNbtBuilder maxSpeed(double _maxSpeed) {
        this.maxSpeed = _maxSpeed;
        return this;
    }

    /** 射击音效名（不含 adventurer_engine: 前缀），默认 "bow" */
    public AmmoNbtBuilder shootSound(String _shootSound) {
        this.shootSound = _shootSound;
        return this;
    }

    // ==================== DamagePacket 参数（可选） ====================

    /**
     * 伤害高斯波动方差（σ²）。
     * <p>设为 {@code basicDamage} 时约 16% 概率打出 2x 伤害。
     * 设为 0（默认）则不创建 DamagePacket。
     */
    public AmmoNbtBuilder fluctuating(double _fluctuating) {
        this.fluctuating = _fluctuating;
        return this;
    }

    /** 炸弹伤害。与 {@link #bombDelay(int)}、{@link #bombAmount(int)} 配合使用 */
    public AmmoNbtBuilder bombDamage(double _bombDamage) {
        this.bombDamage = _bombDamage;
        return this;
    }

    /** 炸弹延时（tick） */
    public AmmoNbtBuilder bombDelay(int _bombDelay) {
        this.bombDelay = _bombDelay;
        return this;
    }

    /** 炸弹爆炸音效 */
    public AmmoNbtBuilder bombSound(String _bombSound) {
        this.bombSound = _bombSound;
        return this;
    }

    /** 炸弹数量 */
    public AmmoNbtBuilder bombAmount(int _bombAmount) {
        this.bombAmount = _bombAmount;
        return this;
    }

    /**
     * 命中药水效果。
     * 每 3 个 int 一组：{@code [potionId, duration, amplifier]}，
     * 多组依次拼接。
     * <p>示例：{@code new int[]{2, 100, 1, 9, 60, 0}}
     * → 缓慢 II 100tick + 反胃 I 60tick
     */
    public AmmoNbtBuilder effectWounded(int[] _effectWounded) {
        this.effectWounded = _effectWounded;
        return this;
    }

    /** 对应 {@link #effectWounded} 各组的触发概率，长度必须匹配组数 */
    public AmmoNbtBuilder probWounded(float[] _probWounded) {
        this.probWounded = _probWounded;
        return this;
    }

    /** 攻击者药水效果，格式同 {@link #effectWounded} */
    public AmmoNbtBuilder effectAttacker(int[] _effectAttacker) {
        this.effectAttacker = _effectAttacker;
        return this;
    }

    /** 对应 {@link #effectAttacker} 各组的触发概率 */
    public AmmoNbtBuilder probAttacker(float[] _probAttacker) {
        this.probAttacker = _probAttacker;
        return this;
    }

    // ==================== 构建 ====================

    /**
     * 构建完整的 NBT 结构，可直接传入 {@code BasicAmmo(NBTTagCompound)} 构造器。
     *
     * <h3>构建顺序（必须严格遵守）</h3>
     * <ol>
     * <li>如果有 DamagePacket 数据 → 创建 DamagePacket NBT → 全局注册</li>
     * <li>创建 RangeAttackPacket NBT → 全局注册</li>
     * <li>创建弹药 NBT（含 theItem 子标签 + fireArmAttr 引用）</li>
     * </ol>
     */
    public NBTTagCompound build() {
        int attrId   = nextAttrId++;
        int packetId = 0;

        // ---- Step 1: DamagePacket（可选） ----
        if (fluctuating > 0.0 || bombDamage > 0.0
                || effectWounded != null || effectAttacker != null) {
            packetId = nextPacketId++;
            buildDamagePacket(packetId);
        }

        // ---- Step 2: RangeAttackPacket（必建，全局注册） ----
        buildRangeAttackPacket(attrId, packetId);

        // ---- Step 3: 弹药 NBT ----
        return buildAmmoNbt(attrId);
    }

    /**
     * 创建 RangeAttackPacket 并注册到全局表。
     * 参考 {@code RangeAttackPacket.RangeAttackPacket(NBTTagCompound)} 的参数读取顺序。
     */
    private void buildRangeAttackPacket(int _attrId, int _packetId) {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("attr_id", _attrId);
        // ⚠️ 引擎用 nbt.d() = getShort() 读取，必须 setShort，不能 setByte！
        nbt.setShort  ("gravity",      gravity    ? (short) 1 : (short) 0);
        nbt.setShort  ("penetrate",    penetrate  ? (short) 1 : (short) 0);
        nbt.setShort  ("noeff",        noeff      ? (short) 1 : (short) 0);
        nbt.setShort  ("shoot_mode",   (short) shootModeToInt(shootMode));
        nbt.setInteger("shoot_delay",  shootDelay);
        nbt.setInteger("charge_time",  chargeTime);
        nbt.setDouble ("accuracy",     accuracy);
        nbt.setDouble ("basic_damage", basicDamage);
        nbt.setDouble ("ice_damage",   iceDamage);
        nbt.setDouble ("knockback",    knockback);
        nbt.setDouble ("max_range",    maxRange);
        nbt.setDouble ("max_speed",    maxSpeed);
        nbt.setString ("shoot_sound",  shootSound);

        // 如果有 DamagePacket，通过 attack_packet 引用
        if (_packetId > 0) {
            nbt.setInteger("attack_packet", _packetId);
        }

        // 构造即注册：new RangeAttackPacket(nbt) 会把 this 放入全局 PACKETS 表
        new RangeAttackPacket(nbt);
    }

    /**
     * 创建 DamagePacket 并注册到全局表。
     */
    private void buildDamagePacket(int _packetId) {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("packet_id", _packetId);

        if (fluctuating > 0.0) {
            nbt.setDouble("fluctuating", fluctuating);
        }
        if (bombDamage > 0.0) {
            nbt.setDouble ("bomb_damage", bombDamage);
            nbt.setInteger("bomb_delay",  bombDelay);
            nbt.setInteger("bomb_amount", bombAmount);
            if (bombSound != null) {
                nbt.setString("bomb_sound", bombSound);
            }
        }
        if (effectWounded != null) {
            nbt.setIntArray("effect_wounded", effectWounded);
        }
        if (probWounded != null) {
            nbt.setTag("p_effect_wounded", floatArrayToList(probWounded));
        }
        if (effectAttacker != null) {
            nbt.setIntArray("effect_attacker", effectAttacker);
        }
        if (probAttacker != null) {
            nbt.setTag("p_effect_attacker", floatArrayToList(probAttacker));
        }

        // 构造即注册：new DamagePacket(nbt) 会把 this 放入全局 PACKETS 表
        new DamagePacket(nbt);
    }

    /**
     * 构建弹药主体 NBT，包含 theItem 子标签和 fireArmAttr 引用。
     */
    private NBTTagCompound buildAmmoNbt(int _attrId) {
        NBTTagCompound nbt = new NBTTagCompound();

        // ---- theItem 子标签（BasicItem 构造器读取） ----
        NBTTagCompound theItem = new NBTTagCompound();
        theItem.setInteger("item_ID",           itemId);
        theItem.setInteger("item_group",        itemGroup);
        theItem.setString ("item_internalName", internalName);
        theItem.setString ("item_icon",         icon);
        theItem.setInteger("item_maxstack",     maxStack);
        if (description != null) {
            theItem.setString("item_description", description);
        }
        if (model != null) {
            theItem.setString("item_model", model);
        }
        nbt.setCompoundTag("theItem", theItem);

        // ---- 弹药字段 ----
        nbt.setShort  ("unbreakable",  unbreakable ? (short) 1 : (short) 0);
        nbt.setInteger("bullet_num",   bulletNum);
        nbt.setInteger("duration",     ammoCapacity);
        nbt.setString ("entity_model", entityModel);
        nbt.setString ("fly_fx",       flyFx);
        nbt.setString ("hit_fx",       hitFx);
        nbt.setInteger("fireArmAttr",  _attrId);

        return nbt;
    }

    // ==================== 工具方法 ====================

    /** ShootMode 枚举 → NBT 数字：SEMI=0, AUTO=1 */
    private static int shootModeToInt(ShootMode _mode) {
        if (_mode == ShootMode.AUTO) {
            return 1;
        }
        return 0; // SEMI 或 null 默认
    }

    /** float[] → NBTTagList（引擎用 NBTTagFloat 存储概率） */
    private static NBTTagList floatArrayToList(float[] _arr) {
        NBTTagList list = new NBTTagList();
        for (float f : _arr) {
            list.appendTag(new net.minecraft.nbt.NBTTagFloat("", f));
        }
        return list;
    }
}
