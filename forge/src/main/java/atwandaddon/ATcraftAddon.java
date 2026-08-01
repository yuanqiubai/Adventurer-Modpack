package atwandaddon;

import atwandaddon.client.ClientProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import atwandaddon.weapon.melee.HeavySword;
import atwandaddon.weapon.gun.SnowfieldExplorer;
import atwandaddon.weapon.ammo.LeadBulletMod;
import atwandaddon.weapon.ammo.SnowEagleAP;

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

    /** 武器分类栏 */
    public static CreativeTabs tabAddon;
    /** 弹药分类栏 */
    public static CreativeTabs tabAmmo;

    /** 占位物品(保留 ID) */
    public static Item placeholder1;
    public static Item placeholder2;
    public static Item placeholder3;
    public static Item placeholder4;
    public static Item placeholder5;
    /** 重剑 */
    public static HeavySword heavySword;
    /** 雪原探索者狙击步枪 */
    public static SnowfieldExplorer snowfieldExplorer;
    /** 雪鹰穿甲弹 */
    public static SnowEagleAP snowEagleAP;
    /** 铅弹改 */
    public static LeadBulletMod leadBulletMod;

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

        // 注册顺序决定内部 ID，必须与旧版一致以兼容 RPG 地图存档
        // ID 25256 — 占位
        placeholder1 = new Item(25000).setUnlocalizedName("placeholder1");
        GameRegistry.registerItem(placeholder1, "placeholder1");

        // ID 25257 — 占位
        placeholder2 = new Item(25001).setUnlocalizedName("placeholder2");
        GameRegistry.registerItem(placeholder2, "placeholder2");

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

        // ID 26256 — 铅弹改
        leadBulletMod = (LeadBulletMod) new LeadBulletMod(26000)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(leadBulletMod, "leadBulletMod");
        leadBulletMod.registerAsAmmo();

        // ID 26267 — 雪鹰穿甲弹（NBT 路径，自动注册 ammoItem）
        // 传入 leadBulletMod.itemID（shiftedIndex = 26256），
        // 确保 entity_model 中的 renderItemID 与 Item.itemsList 索引一致
        snowEagleAP = (SnowEagleAP) new SnowEagleAP(26001, leadBulletMod.itemID)
            .setCreativeTab(tabAmmo);
        GameRegistry.registerItem(snowEagleAP, "snowEagleAP");
        // snowEagleAP.registerAsAmmo(); — NBT 构造器自动完成注册

        proxy.registerRenderers();
    }
}
