/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.Mod
 *  cpw.mods.fml.common.Mod$EventHandler
 *  cpw.mods.fml.common.Mod$Instance
 *  cpw.mods.fml.common.SidedProxy
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPostInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.event.FMLServerStartingEvent
 *  cpw.mods.fml.common.network.NetworkMod
 */
package com.adventurer_engine;

import com.adventurer_engine.common.CommonProxy;
import com.adventurer_engine.network.ADEPacketHandler;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.Tools;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import java.io.File;

@Mod(modid="adventurer_engine", name="Adventurer Engine", version="1.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=true, channels={"adv_channel"}, packetHandler=ADEPacketHandler.class)
public class AdventurerEngine {
    public static final String MODID = "adventurer_engine";
    public static final String NAME = "Adventurer Engine";
    public static final String VERSION = "1.0";
    public static final String CHANNEL = "adv_channel";
    @SidedProxy(clientSide="com.adventurer_engine.client.ClientProxy", serverSide="com.adventurer_engine.common.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance(value="adventurer_engine")
    public static AdventurerEngine instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        boolean lockNPC = true;
        String sha256 = "a96b0ec6eba8ad715b832381086f6523f6d00ac68e948376003800e2998737cd";
        if (lockNPC) {
            String trueSha256;
            String npcFilePath = event.getModConfigurationDirectory().getParent() + "\\mods\\customnpcs_1.6.4.jar";
            File npc = new File(npcFilePath);
            if (!npc.exists() || npc.isDirectory()) {
                System.exit(-1);
            }
            if (!sha256.equals(trueSha256 = Tools.calculateSHA256(npc))) {
                System.exit(-1);
            }
        }
        DebugGadgets.initDebugFile(event);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        proxy.serverStart(event);
    }
}

