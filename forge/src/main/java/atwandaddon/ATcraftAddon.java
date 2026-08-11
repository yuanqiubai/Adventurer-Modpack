package atwandaddon;

import atwandaddon.client.ClientProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
// ============================= 导入自定义武器类 ============================================
import atwandaddon.weapon.melee.HeavySword;                 // 重剑
import atwandaddon.weapon.melee.Bayonet;                    // 刺刀:寒锋
import atwandaddon.weapon.melee.Bayonet_2;                  // 刺刀:猎魂
import atwandaddon.weapon.gun.BoltActionRifle;              // 栓动式步枪-猎魂
import atwandaddon.weapon.gun.BoltActionRifle_2;            // 栓动式步枪-猎魂-A
import atwandaddon.weapon.gun.RotaryMachineGun;             // 蓝火转轮机枪
import atwandaddon.weapon.gun.SnowfieldExplorer;            // 雪原探索者
import atwandaddon.weapon.gun.SnowfieldExplorerAddBayonet;  // 雪原探索者-寒锋
import atwandaddon.weapon.gun.Ranger_K;                     // 游骑兵
import atwandaddon.weapon.gun.Maverick;                     // 孤骑
// ============================= 导入自定义弹药类 ============================================
import atwandaddon.weapon.ammo.LeadBulletMod;               // 铅弹改
import atwandaddon.weapon.ammo.SnowEagleAP;                 // 雪鹰穿甲弹
import atwandaddon.weapon.ammo.MachineGunMagazine;          // 蓝火弹夹
import atwandaddon.weapon.ammo.BrandRoundBullet;            // 刻印弹
// ============================= 导入自定义纯物品（弹幕图标用）===================================
import atwandaddon.weapon.BulletEntity.LeadBulletModEntity; // 铅弹改-实体
import atwandaddon.weapon.BulletEntity.ProjectileEntity;    // 弹丸-实体
import atwandaddon.weapon.BulletEntity.BrandRoundEntity;    // 刻印弹-实体
import atwandaddon.weapon.BulletEntity.SnowEagleAPEntity;   // 雪鹰穿甲弹-实体

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.potion.Potion;

import atwandaddon.buff.BuffDef;
import atwandaddon.buff.BuffMonitor;
import atwandaddon.buff.BuffRegistry;
import atwandaddon.buff.EntityBuffListener;
import atwandaddon.buff.NbtDamageListener;
import atwandaddon.buff.potion.PotionBleed;
import atwandaddon.buff.potion.PotionDeadline;
import atwandaddon.buff.potion.PotionGunHunger;
import atwandaddon.buff.potion.PotionVulnerable;

import atwandaddon.entity.EntitySniperBullet;

/**
 * ATcraft Addon Mod 主入口
 * 负责物品注册、实体注册、创造标签页创建和代理初始化。
 * 目标整合包：冒险者传说V1.91(MC 1.6.4 + Forge 9.11.1.916)。
 *
 * 职责边界：
 * - 仅负责注册和初始化，不包含业务逻辑
 * - 武器行为由各自 Item 子类实现
 * - 渲染由 ClientProxy 代理处理
 */
@Mod(modid = ATcraftAddon.MODID, name = ATcraftAddon.NAME, version = ATcraftAddon.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
    channels = {"ATcraftAddon"}, packetHandler = ATcraftPacketHandler.class)
public class ATcraftAddon {

    /** Mod 标识符，Forge 用此 ID 区分不同 Mod */
    public static final String MODID = "ATcraftAddon";
    /** Mod 显示名称 */
    public static final String NAME = "ATcraft Addon";
    /** Mod 版本号 */
    public static final String VERSION = "1.0";

    /** Mod 单例实例，由 Forge 自动注入 */
    @Mod.Instance(MODID)
    public static ATcraftAddon instance;

    /**
     * 客户端/服务端代理
     * 客户端自动注入 ClientProxy(处理渲染注册)，服务端注入 CommonProxy。
     */
    @SidedProxy(clientSide = "atwandaddon.client.ClientProxy", serverSide = "atwandaddon.CommonProxy")
    public static CommonProxy proxy;

    /** 武器分类栏 */
    public static CreativeTabs tabAddon;
    /** 弹药分类栏 */
    public static CreativeTabs tabAmmo;
    /** 实体分类栏 */
    public static CreativeTabs tabEntity;
    /** 其他武器分类栏 */
    public static CreativeTabs tabOtherWeapons;

    // ============================ 自定义武器实例 ============================================
    public static Ranger_K ranger_k;                            // 游骑兵
    public static BoltActionRifle boltActionRifle;              // 栓动式步枪-猎魂
    public static BoltActionRifle_2 boltActionRifle_2;          // 栓动式步枪-猎魂-A
    public static RotaryMachineGun rotaryMachineGun;            // 转轮机枪
    public static Item placeholder3;
    public static Item placeholder4;
    public static Item placeholder5;
    public static HeavySword heavySword;                        // 重剑
    public static SnowfieldExplorer snowfieldExplorer;          // 雪原探索者
    public static SnowfieldExplorerAddBayonet snowfieldExplorerAddBayonet;  // 雪原探索者-寒锋
    public static Bayonet bayonet;                              // 刺刀
    public static Bayonet_2 bayonet_2;                          // 刺刀2
    public static Maverick maverick;                            // 孤骑

    // ============================ 其它武器实例 (sword_1 ~ sword_12) ====================================
    public static HeavySword sword_1;   // 其它武器 sword_1
    public static HeavySword sword_2;   // 其它武器 sword_2
    public static HeavySword sword_3;   // 其它武器 sword_3
    public static HeavySword sword_4;   // 其它武器 sword_4
    public static HeavySword sword_5;   // 其它武器 sword_5
    public static HeavySword sword_6;   // 其它武器 sword_6
    public static HeavySword sword_7;   // 其它武器 sword_7
    public static HeavySword sword_8;   // 其它武器 sword_8
    public static HeavySword sword_9;   // 其它武器 sword_9
    public static HeavySword sword_10;  // 其它武器 sword_10
    public static HeavySword sword_11;  // 其它武器 sword_11
    public static HeavySword sword_12;  // 其它武器 sword_12
    // ============================ 自定义弹药实例 ============================================
    public static SnowEagleAP snowEagleAP;                  // 雪鹰穿甲弹
    public static LeadBulletMod leadBulletMod;              // 铅弹改
    public static MachineGunMagazine machineGunMagazine;    // 蓝火弹夹
    public static BrandRoundBullet brandRoundBullet;        // 刻印弹
    // ============================ 自定义弹药实体实例 ============================================
    public static LeadBulletModEntity leadBulletModEntity;  // 铅弹改实体
    public static ProjectileEntity projectileEntity;        // 弹丸实体
    public static BrandRoundEntity brandRoundEntity;        // 刻印弹实体
    public static SnowEagleAPEntity snowEagleAPEntity;      // 雪鹰穿甲弹实体

    /**
     * 预初始化阶段
     * 创建创造标签页、注册所有物品、初始化代理渲染。
     * 此阶段在所有 Mod 的 init() 之前执行，仅用于注册。
     *
     * @param _event Forge 预初始化事件，携带配置目录等信息
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent _event) {
        // ============================================= 扩展 Potion 数组 ============================================
        // MC 1.6.4 的 Potion.potionTypes 数组只有 32 个槽位 (new Potion[32])。
        // 通过反射移除 final 修饰符后替换为 256 槽位的大数组，以支持自定义 Potion ID 30+。
        expandPotionArray();

        tabAddon = new CreativeTabs(CreativeTabs.creativeTabArray.length, MODID) {
            @Override
            public Item getTabIconItem() {
                return heavySword;
            }
        };

        tabAmmo = new CreativeTabs(CreativeTabs.creativeTabArray.length, "ATcraftAmmo") {
            @Override
            public Item getTabIconItem() {
                return snowEagleAP;
            }
        };

        tabEntity = new CreativeTabs(CreativeTabs.creativeTabArray.length, "ATcraftEntity") {
            @Override
            public Item getTabIconItem() {
                return projectileEntity;
            }
        };

        tabOtherWeapons = new CreativeTabs(CreativeTabs.creativeTabArray.length, "ATcraftEntity") {
            @Override
            public Item getTabIconItem() {
                return sword_2;
            }
        };
        // ============================================= 注册自定义武器 ============================================
        // ID 25256 — 栓动式步枪-猎魂
        boltActionRifle = (BoltActionRifle) new BoltActionRifle(25000)
            .setUnlocalizedName("boltActionRifle")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(boltActionRifle, "boltActionRifle");

        // ID 25257 — 栓动式步枪-猎魂-A
        boltActionRifle_2 = (BoltActionRifle_2) new BoltActionRifle_2(25001)
            .setUnlocalizedName("boltActionRifle_2")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(boltActionRifle_2, "boltActionRifle_2");

        // ID 25258 — 游骑兵
        ranger_k = (Ranger_K) new Ranger_K(25002)
            .setUnlocalizedName("ranger_k")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(ranger_k, "ranger_k");

        // ID 25259 — 孤骑
        maverick = (Maverick) new Maverick(25003)
            .setUnlocalizedName("maverick")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(maverick, "maverick");

        // ID 25260 — 占位
        placeholder5 = new Item(25004).setUnlocalizedName("placeholder5");
        GameRegistry.registerItem(placeholder5, "placeholder5");

        // ID 25261 — 重剑保持旧 ID
        heavySword = (HeavySword) new HeavySword(25005, "heavy_sword", "heavy_sword")
            .setUnlocalizedName("heavySword")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(heavySword, "heavySword");
        
        // ID 25262 — 雪原探索者狙击步枪
        snowfieldExplorer = (SnowfieldExplorer) new SnowfieldExplorer(25006)
            .setUnlocalizedName("snowfieldExplorer")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(snowfieldExplorer, "snowfieldExplorer");

        // ID 25263 — 雪原探索者狙击步枪附加刺刀
        snowfieldExplorerAddBayonet = (SnowfieldExplorerAddBayonet) new SnowfieldExplorerAddBayonet(25007)
            .setUnlocalizedName("snowfieldExplorerAddBayonet")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(snowfieldExplorerAddBayonet, "snowfieldExplorerAddBayonet");

        // ID 25264 — 蓝火转轮机枪
        rotaryMachineGun = (RotaryMachineGun) new RotaryMachineGun(25008)
            .setUnlocalizedName("rotaryMachineGun")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(rotaryMachineGun, "rotaryMachineGun");

        // ID 25265 — 刺刀
        bayonet = (Bayonet) new Bayonet(25009)
            .setUnlocalizedName("bayonet")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(bayonet, "bayonet");

        bayonet_2 = (Bayonet_2) new Bayonet_2(25010)
            .setUnlocalizedName("bayonet_2")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(bayonet_2, "bayonet_2");

        // ============================================= 注册自定义物品（弹幕图标）============================================
        // CRITICAL: 纯物品必须在弹药之前注册！弹药构造器通过 itemID 引用纯物品，
        // 用于 entity_model 弹幕渲染图标。若纯物品为 null → NullPointerException。

        leadBulletModEntity = (LeadBulletModEntity) new LeadBulletModEntity(26500)
            .setUnlocalizedName("leadBulletModEntity")
            .setTextureName("adventurer_engine:lead_bullet_mod")
            .setCreativeTab(tabEntity);
        GameRegistry.registerItem(leadBulletModEntity, "leadBulletModEntity");

        projectileEntity = (ProjectileEntity) new ProjectileEntity(26501)
            .setUnlocalizedName("projectileEntity")
            .setTextureName("adventurer_engine:projectile")
            .setCreativeTab(tabEntity);
        GameRegistry.registerItem(projectileEntity, "projectileEntity");

        // 刻印弹物品-实体
        brandRoundEntity = (BrandRoundEntity) new BrandRoundEntity(26502)
            .setUnlocalizedName("brandRoundEntity")
            .setTextureName("adventurer_engine:brand_round_bullet_entity")
            .setCreativeTab(tabEntity);
        GameRegistry.registerItem(brandRoundEntity, "brandRoundEntity");

                // 雪鹰穿甲弹实体
        snowEagleAPEntity = (SnowEagleAPEntity) new SnowEagleAPEntity(26503)
            .setUnlocalizedName("snowEagleAPEntity")
            .setTextureName("adventurer_engine:snow_eagle_ap_entity")
            .setCreativeTab(tabEntity);
        GameRegistry.registerItem(snowEagleAPEntity, "snowEagleAPEntity");

        // ============================================= 注册自定义弹药 ============================================
        leadBulletMod = (LeadBulletMod) new LeadBulletMod(26000, leadBulletModEntity.itemID)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(leadBulletMod, "leadBulletMod");

        // ID 26267 — 雪鹰穿甲弹（NBT 路径，自动注册 ammoItem）
        snowEagleAP = (SnowEagleAP) new SnowEagleAP(26001, snowEagleAPEntity.itemID)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(snowEagleAP, "snowEagleAP");

        // 蓝火弹夹
        machineGunMagazine = (MachineGunMagazine) new MachineGunMagazine(26002, projectileEntity.itemID)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(machineGunMagazine, "machineGunMagazine");

        // 刻印弹
        brandRoundBullet = (BrandRoundBullet) new BrandRoundBullet(26003, brandRoundEntity.itemID)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(brandRoundBullet, "brandRoundBullet");



        // 弹丸实体
        // trackingRange=64, updateFrequency=1, sendsVelocityUpdates=true
        EntityRegistry.registerModEntity(EntitySniperBullet.class, "SniperBullet",
            250, instance, 64, 1, true);
        
        // ============================================= 注册其它武器 ==============================================
        // sword_1 ~ sword_12，ID 27000 ~ 27011，name/icon 与纹理文件名一一对应
        sword_1 = (HeavySword) new HeavySword(27000, "sword_1", "sword_1")
            .setUnlocalizedName("sword_1")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_1, "sword_1");

        sword_2 = (HeavySword) new HeavySword(27001, "sword_2", "sword_2")
            .setUnlocalizedName("sword_2")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_2, "sword_2");

        sword_3 = (HeavySword) new HeavySword(27002, "sword_3", "sword_3")
            .setUnlocalizedName("sword_3")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_3, "sword_3");

        sword_4 = (HeavySword) new HeavySword(27003, "sword_4", "sword_4")
            .setUnlocalizedName("sword_4")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_4, "sword_4");

        sword_5 = (HeavySword) new HeavySword(27004, "sword_5", "sword_5")
            .setUnlocalizedName("sword_5")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_5, "sword_5");

        sword_6 = (HeavySword) new HeavySword(27005, "sword_6", "sword_6")
            .setUnlocalizedName("sword_6")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_6, "sword_6");

        sword_7 = (HeavySword) new HeavySword(27006, "sword_7", "sword_7")
            .setUnlocalizedName("sword_7")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_7, "sword_7");

        sword_8 = (HeavySword) new HeavySword(27007, "sword_8", "sword_8")
            .setUnlocalizedName("sword_8")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_8, "sword_8");

        sword_9 = (HeavySword) new HeavySword(27008, "sword_9", "sword_9")
            .setUnlocalizedName("sword_9")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_9, "sword_9");

        sword_10 = (HeavySword) new HeavySword(27009, "sword_10", "sword_10")
            .setUnlocalizedName("sword_10")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_10, "sword_10");

        sword_11 = (HeavySword) new HeavySword(27010, "sword_11", "sword_11")
            .setUnlocalizedName("sword_11")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_11, "sword_11");

        sword_12 = (HeavySword) new HeavySword(27011, "sword_12", "sword_12")
            .setUnlocalizedName("sword_12")
            .setCreativeTab(tabOtherWeapons);
        GameRegistry.registerItem(sword_12, "sword_12");
        // ============================================= 注册自定义buff ============================================
        // 注册自定义 Potion（流血 DOT）并注册 Forge 事件监听
        // Potion 构造器自动注册到 potionTypes[ID]（MC 原生机制）
        PotionBleed bleed = new PotionBleed();

        // BuffDef 注册到 BuffRegistry — 后续武器可通过 NbtEventTags 施加
        // 参数 (ID, 时长, 等级) 与 NbtEventTags.writePotion() 保持一致
        BuffRegistry.register(new BuffDef(PotionBleed.ID, 200, 0)
            .damageRatio(0.1F)
            .tickInterval(2L));

        // 注册自定义 Potion（悬赏·死线 — 累积伤害到期爆发）
        // 死线的计时和结算由 EntityBuffListener 独立管理，不依赖 MC Potion 系统的持续时间
        PotionDeadline deadline = new PotionDeadline();

        // BuffDef 注册到 BuffRegistry — tickInterval=0 表示死线无间隔触发（由监听器独立计时）
        BuffRegistry.register(new BuffDef(PotionDeadline.ID, 200, 0)
            .damageRatio(PotionDeadline.DAMAGE_RATIO)
            .tickInterval(0L));

        // 注册自定义 Potion（DOT 易伤 — 每级使实体承受的 DOT 伤害 +25%）
        // 纯被动 Buff，无主动效果；DOT 来源（如 PotionBleed）主动查询此 Buff 并应用倍率
        PotionVulnerable vulnerable = new PotionVulnerable();

        BuffRegistry.register(new BuffDef(PotionVulnerable.ID, 200, 0)
            .tickInterval(0L));

        // 注册自定义 Potion（枪之饥 — 吸血增益，每颗子弹命中回复 HP）
        // 纯被动 Buff，回血逻辑在 EntityBuffListener.onLivingHurt 中
        PotionGunHunger gunHunger = new PotionGunHunger();

        BuffRegistry.register(new BuffDef(PotionGunHunger.ID, 200, 0)
            .tickInterval(0L));

        // BuffMonitor 接管 LivingHurtEvent 监听（解耦武器与 buff）
        // 旧设计：PotionBleed 自身监听 EVENT_BUS
        // 新设计：BuffMonitor 是唯一监听者，Potion 子类通过 consumeDamage() 读取
        // ===== 旧代码 (保留以作参考) =====
        // BuffMonitor monitor = new BuffMonitor();
        // MinecraftForge.EVENT_BUS.register(monitor);
        //
        // // NbtDamageListener 追踪玩家武器 + 装备 NBT 属性加成
        // // 与 BuffMonitor 互补：BuffMonitor 捕获实际伤害，NbtDamageListener 捕获 NBT 加成
        // // PotionBleed 组合两者还原完整面板伤害 → DOT
        // NbtDamageListener nbtListener = new NbtDamageListener();
        // MinecraftForge.EVENT_BUS.register(nbtListener);
        // ===== 新代码 =====

        // EntityBuffListener 统一实体伤害数据持久化 + Buff 状态扫描
        // 取代 BuffMonitor + NbtDamageListener，解决旧设计"消费后删除"导致
        // 流血 DOT 仅触发一次的 bug。伤害数据持久存储，允许多次 DOT tick 重复读取。
        EntityBuffListener entityListener = new EntityBuffListener();
        MinecraftForge.EVENT_BUS.register(entityListener);

        proxy.registerRenderers();
    }

    /**
     * 确保 {@link Potion#potionTypes} 数组足够大以容纳自定义 Potion ID。
     *
     * <p>MC 1.6.4 原版 Potion.potionTypes 仅 32 槽位。其他 coremod（如 adventurer_core）
     * 可能已提前扩展。本方法检查当前数组大小，仅在不足时扩展到 {@code max(当前大小, 256)}。</p>
     */
    private static void expandPotionArray()
    {
        try
        {
            // 按类型查找 Potion.potionTypes 字段（运行时字段名为 SRG，不用硬编码字符串）
            Field potionTypesField = null;
            for (Field _f : Potion.class.getDeclaredFields())
            {
                if (_f.getType() == Potion[].class
                    && java.lang.reflect.Modifier.isStatic(_f.getModifiers()))
                {
                    potionTypesField = _f;
                    break;
                }
            }

            if (potionTypesField == null)
            {
                throw new NoSuchFieldException(
                    "Cannot find static Potion[] field in Potion class");
            }

            potionTypesField.setAccessible(true);
            Potion[] oldArray = (Potion[]) potionTypesField.get(null);

            // 数组已足够大（> 50），无需扩展。adventurer_core 等 coremod 可能已提前扩展。
            if (oldArray.length > 50)
            {
                FMLLog.info("[ATcraftAddon] Potion.potionTypes already %d slots, skip expand",
                    Integer.valueOf(oldArray.length));
                return;
            }

            // 移除 final 修饰符
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(potionTypesField,
                potionTypesField.getModifiers() & ~Modifier.FINAL);

            // 扩展到 256 槽位
            Potion[] newArray = new Potion[256];
            System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
            potionTypesField.set(null, newArray);

            FMLLog.info("[ATcraftAddon] Potion.potionTypes expanded: %d → %d slots",
                Integer.valueOf(oldArray.length), Integer.valueOf(newArray.length));
        }
        catch (Exception _e)
        {
            FMLLog.severe("[ATcraftAddon] Failed to expand Potion.potionTypes!", _e);
            throw new RuntimeException(
                "Cannot register custom potions — potionTypes expansion failed", _e);
        }
    }
}