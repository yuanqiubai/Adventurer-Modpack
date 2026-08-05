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
// ============================= 导入自定义弹药类 ============================================
import atwandaddon.weapon.ammo.LeadBulletMod;               // 铅弹改
import atwandaddon.weapon.ammo.SnowEagleAP;                 // 雪鹰穿甲弹
import atwandaddon.weapon.ammo.MachineGunMagazine;          // 蓝火弹夹
// ============================= 导入自定义纯物品（弹幕图标用）===================================
import atwandaddon.weapon.BulletEntity.LeadBulletModEntity; // 铅弹改-实体
import atwandaddon.weapon.BulletEntity.ProjectileEntity;    // 弹丸实体

import atwandaddon.buff.BuffDef;
import atwandaddon.buff.BuffMonitor;
import atwandaddon.buff.BuffRegistry;
import atwandaddon.buff.EntityBuffListener;
import atwandaddon.buff.NbtDamageListener;
import atwandaddon.buff.potion.PotionBleed;
import atwandaddon.buff.potion.PotionDeadline;

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
    public static CreativeTabs otherWeapons;

    // ============================ 自定义武器实例 ============================================
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
    // ============================ 自定义弹药实例 ============================================
    public static SnowEagleAP snowEagleAP;                  // 雪鹰穿甲弹
    public static LeadBulletMod leadBulletMod;              // 铅弹改
    public static MachineGunMagazine machineGunMagazine;    // 风暴弹夹
    // ============================ 自定义弹药实体实例 ============================================
    public static LeadBulletModEntity leadBulletModEntity;  // 铅弹改实体
    public static ProjectileEntity projectileEntity;        // 弹丸实体

    /**
     * 预初始化阶段
     * 创建创造标签页、注册所有物品、初始化代理渲染。
     * 此阶段在所有 Mod 的 init() 之前执行，仅用于注册。
     *
     * @param _event Forge 预初始化事件，携带配置目录等信息
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent _event) {
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

        // ID 25258 — 占位
        placeholder3 = new Item(25002).setUnlocalizedName("placeholder3");
        GameRegistry.registerItem(placeholder3, "placeholder3");

        // ID 25259 — 占位
        placeholder4 = new Item(25003).setUnlocalizedName("placeholder4");
        GameRegistry.registerItem(placeholder4, "placeholder4");

        // ID 25260 — 占位
        placeholder5 = new Item(25004).setUnlocalizedName("placeholder5");
        GameRegistry.registerItem(placeholder5, "placeholder5");

        // ID 25261 — 重剑保持旧 ID
        heavySword = (HeavySword) new HeavySword(25005)
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

        // ============================================= 注册自定义弹药 ============================================
        leadBulletMod = (LeadBulletMod) new LeadBulletMod(26000)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(leadBulletMod, "leadBulletMod");

        // ID 26267 — 雪鹰穿甲弹（NBT 路径，自动注册 ammoItem）
        // 传入 leadBulletModEntity.itemID（shiftedIndex = 26256），
        // 确保 entity_model 中的 renderItemID 与 Item.itemsList 索引一致
        snowEagleAP = (SnowEagleAP) new SnowEagleAP(26001, leadBulletModEntity.itemID)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(snowEagleAP, "snowEagleAP");

        // 蓝火弹夹
        machineGunMagazine = (MachineGunMagazine) new MachineGunMagazine(26002, projectileEntity.itemID)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(machineGunMagazine, "machineGunMagazine");

        // 注册自定义弹丸实体
        // trackingRange=64, updateFrequency=1, sendsVelocityUpdates=true
        EntityRegistry.registerModEntity(EntitySniperBullet.class, "SniperBullet",
            250, instance, 64, 1, true);

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
}