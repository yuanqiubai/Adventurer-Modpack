package atwandaddon;

import atwandaddon.client.ClientProxy;
import atwandaddon.entity.EntityFlameBall;
import atwandaddon.weapon.ItemExampleAmmo;
import atwandaddon.weapon.ItemExampleGun;
import atwandaddon.weapon.ItemExampleSword;
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
    public static ItemExampleAmmo exampleAmmo;
    public static ItemExampleGun exampleGun;
    public static ItemExampleSword exampleSword;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent _event) {
        tabAddon = new CreativeTabs(CreativeTabs.creativeTabArray.length, MODID) {
            @Override
            public Item getTabIconItem() {
                return flameWand;
            }
        };

        ammoOrb = new Item(25000)
            .setUnlocalizedName("ammoOrb")
            .setTextureName("atcraftaddon:ammo_orb")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(ammoOrb, "ammoOrb");

        flameWand = new ItemFlameWand(25001)
            .setUnlocalizedName("flameWand")
            .setTextureName("atcraftaddon:flame_wand")
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(flameWand, "flameWand");

        /* ---- adventurer_engine 联动武器 ---- */
        exampleAmmo = (ItemExampleAmmo) new ItemExampleAmmo(25002)
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(exampleAmmo, "exampleAmmo");
        exampleAmmo.registerAsAmmo();

        exampleGun = (ItemExampleGun) new ItemExampleGun(25003)
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(exampleGun, "exampleGun");

        // 近战武器(物品 ID 25004)
        exampleSword = (ItemExampleSword) new ItemExampleSword(25004)
            .setCreativeTab(tabAddon);
        GameRegistry.registerItem(exampleSword, "exampleSword");

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent _event) {
        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityFlameBall.class, "FlameBall", entityId);
        EntityRegistry.registerModEntity(EntityFlameBall.class, "FlameBall", entityId, this, 64, 3, true);
    }
}
