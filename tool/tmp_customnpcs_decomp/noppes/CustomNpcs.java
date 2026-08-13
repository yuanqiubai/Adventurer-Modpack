/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ab
 *  cpw.mods.fml.common.Mod
 *  cpw.mods.fml.common.Mod$EventHandler
 *  cpw.mods.fml.common.SidedProxy
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLServerStartingEvent
 *  cpw.mods.fml.common.network.NetworkMod
 *  cpw.mods.fml.common.network.NetworkMod$SidedPacketHandler
 *  ye
 */
package noppes;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import java.io.File;
import noppes.common.CommonProxy;
import noppes.common.commands.CommandSlay;
import noppes.common.items.ItemNpcCloner;
import noppes.common.items.ItemNpcMovingPath;
import noppes.common.items.ItemNpcWand;
import noppes.network.NPCPacketHandlerClient;
import noppes.network.NPCPacketHandlerPlayer;
import noppes.network.NPCPacketHandlerServer;

@NetworkMod(clientSideRequired=true, serverSideRequired=true, channels={"CNPCs Player"}, packetHandler=NPCPacketHandlerPlayer.class, clientPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"CNPCs Client", "CNPCs Player"}, packetHandler=NPCPacketHandlerClient.class), serverPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"CNPCs Server", "CNPCs Player"}, packetHandler=NPCPacketHandlerServer.class), versionBounds="[1.6.4]")
@Mod(modid="customnpcs", name="CustomNpcs", version="1.6.4")
public class CustomNpcs {
    public static boolean TextureSelection = false;
    public static final boolean ENABLE_WAND = true;
    public static long ticks;
    @SidedProxy(clientSide="noppes.client.ClientProxy", serverSide="noppes.common.CommonProxy")
    public static CommonProxy proxy;
    public static CustomNpcs instance;
    public static boolean FreezeNPCs;
    public static File Dir;

    public CustomNpcs() {
        instance = this;
    }

    public static boolean activeWand(ye ye2) {
        return ye2 != null && ye2.b() instanceof ItemNpcWand;
    }

    public static boolean activeCloner(ye ye2) {
        return ye2 != null && ye2.b() instanceof ItemNpcCloner;
    }

    public static boolean activeMoving(ye ye2) {
        return ye2 != null && ye2.b() instanceof ItemNpcMovingPath;
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onInit(event);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        event.registerServerCommand((ab)new CommandSlay());
    }

    static {
        FreezeNPCs = false;
    }
}

