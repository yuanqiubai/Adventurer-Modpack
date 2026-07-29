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

@Mod(modid = ATcraftAddon.MODID, name = ATcraftAddon.NAME, version = ATcraftAddon.VERSION)
public class ATcraftAddon {

    public static final String MODID = "ATcraftAddon";
    public static final String NAME = "ATcraft Addon";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static ATcraftAddon instance;

    @SidedProxy(clientSide = "atwandaddon.client.ClientProxy", serverSide = "atwandaddon.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabAddon;
    public static Item flameWand;
    public static Item ammoOrb;

    /** 包装方法：阻止 javac 将 creativeTabArray.length 内联为常量 12 */
    private static int getNextCreativeTabId() {
        return CreativeTabs.creativeTabArray.length;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent _event) {
        tabAddon = new CreativeTabs(getNextCreativeTabId(), MODID) {
            @Override
            public Item getTabIconItem() {
                return flameWand;
            }
        };

        ammoOrb = new Item(25000);
        ammoOrb.setUnlocalizedName("ammoOrb");
        ammoOrb.setTextureName("atcraftaddon:ammo_orb");
        ammoOrb.setCreativeTab(tabAddon);
        GameRegistry.registerItem(ammoOrb, "ammoOrb");

        flameWand = new ItemFlameWand(25001);
        flameWand.setUnlocalizedName("flameWand");
        flameWand.setTextureName("atcraftaddon:flame_wand");
        flameWand.setCreativeTab(tabAddon);
        GameRegistry.registerItem(flameWand, "flameWand");

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent _event) {
        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityFlameBall.class, "FlameBall", entityId);
        EntityRegistry.registerModEntity(EntityFlameBall.class, "FlameBall", entityId, this, 64, 3, true);
    }
}
