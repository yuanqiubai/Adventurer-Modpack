/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPostInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.event.FMLServerStartingEvent
 *  cpw.mods.fml.common.registry.TickRegistry
 *  cpw.mods.fml.relauncher.Side
 *  ni
 *  yc
 */
package com.adventurer_engine.common;

import com.adventurer_engine.common.CommonConfig;
import com.adventurer_engine.common.command.CommandLoader;
import com.adventurer_engine.common.entity.EntityLoader;
import com.adventurer_engine.common.event.EventLoader;
import com.adventurer_engine.common.event.RCsTickHandler;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.common.items.TabsLoader;
import com.adventurer_engine.common.potion.PotionLoader;
import com.adventurer_engine.network.RCsGuiHandler;
import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.DataCenter;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ni[] potions = new ni[1024];
        System.arraycopy(ni.a, 0, potions, 0, ni.a.length);
        ni.a = potions;
        new DataCenter(event);
        new CommonConfig(event);
        TabsLoader.load(event);
        ItemLoader.load(event);
        PotionLoader.load();
        EntityLoader.registerEntity();
    }

    public void init(FMLInitializationEvent event) {
        new EventLoader();
        TickRegistry.registerTickHandler((ITickHandler)new RCsTickHandler(), (Side)Side.SERVER);
        new RCsGuiHandler();
        yc.bu.d(CommonConfig.maxstack);
    }

    public void postInit(FMLPostInitializationEvent event) {
        RCsPacket.addPackagePath("com.adventurer_engine.network.client2server");
        RCsPacket.addPackagePath("com.adventurer_engine.network.server2client");
    }

    public void serverStart(FMLServerStartingEvent event) {
        CommandLoader.load(event);
    }

    public void addSound(String soundName) {
    }
}

