package atwandaddon;

import atwandaddon.client.ClientProxy;
import atwandaddon.entity.EntityFlameBall;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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

    /** 自定义创造模式标签页，收纳本 Mod 所有物品 */
    public static CreativeTabs tabAddon;
    /** 火焰法杖武器物品 */
    public static Item flameWand;
    /** 弹药宝珠，火焰法杖消耗品 */
    public static Item ammoOrb;

    /** Melee: 重剑 */
    public static Item heavySword;


    /**
     * 预初始化阶段
     * 创建创造标签页、注册所有物品、初始化代理渲染。
     * 此阶段在所有 Mod 的 init() 之前执行，仅用于注册。
     *
     * 注意：setUnlocalizedName / setTextureName / setCreativeTab 分开调用，
     * 不要链式调用 —— MC 1.6.4 的这些方法返回类型不稳定。
     *
     * @param _event Forge 预初始化事件，携带配置目录等信息
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent _event) {
        // 创造标签页(ID=12，自定义标签页需要显式指定 ID)
        tabAddon = new CreativeTabs(12, "ATcraftAddon") {
            @Override
            public Item getTabIconItem() {
                return flameWand; // 用火焰法杖作为标签页图标
            }
        };

        // 弹药宝珠(ID=25000)，火焰法杖发射消耗的弹药
        ammoOrb = new Item(25000)
            .setUnlocalizedName("ammoOrb")
            .setTextureName("atcraftaddon:ammo_orb")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(ammoOrb, "ammoOrb");

        // 火焰法杖(ID=25001)，蓄力发射火焰弹
        flameWand = new ItemFlameWand(25001)
            .setUnlocalizedName("flameWand")
            .setTextureName("atcraftaddon:flame_wand")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(flameWand, "flameWand");

        heavySword = new ItemHeavySword(25002)
            .setUnlocalizedName("heavySword")
            .setTextureName("atcraftaddon:heavy_sword")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(heavySword, "heavy_Sword");

        // 委托代理注册渲染器(客户端注册实体渲染，服务端空实现)
        proxy.registerRenderers();
    }

    /**
     * 初始化阶段
     * 注册火焰弹实体到全局实体列表和 Forge Mod 实体注册表。
     * 此阶段在所有 Mod 的 preInit() 之后执行，可以安全引用其他 Mod 注册的内容。
     *
     * registerModEntity 参数说明：
     * - trackingRange=64：实体在 64 格内同步给客户端
     * - updateFrequency=3：每 3 tick 发送一次位置更新
     * - sendVelocity=true：同步速度数据(弹射物需要)
     *
     * @param _event Forge 初始化事件
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent _event) {
        // 获取全局唯一实体 ID 并注册火焰弹
        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityFlameBall.class, "FlameBall", entityId);
        EntityRegistry.registerModEntity(EntityFlameBall.class, "FlameBall", entityId, this, 64, 3, true);
    }
}
