/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPostInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.registry.TickRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package com.adventurer_engine.client;

import com.adventurer_engine.client.RenderLoader;
import com.adventurer_engine.client.SoundManager;
import com.adventurer_engine.client.entity.model.RCsModelManager;
import com.adventurer_engine.client.event.ClientEventLoader;
import com.adventurer_engine.client.event.RCsTickHandlerClient;
import com.adventurer_engine.client.hud.HUDEngine;
import com.adventurer_engine.client.key.KeyTickHandler;
import com.adventurer_engine.client.model.ItemModelLoader;
import com.adventurer_engine.common.CommonProxy;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class ClientProxy
extends CommonProxy {
    public static long worldCalendar;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RCsModelManager.addModel();
        ItemModelLoader.loadModel();
        HUDEngine.registerHUD();
        RenderLoader.registerEntityRender();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        new SoundManager();
        RenderLoader.registerItemRender();
        new KeyTickHandler();
        new ClientEventLoader();
        TickRegistry.registerTickHandler((ITickHandler)new RCsTickHandlerClient(), (Side)Side.CLIENT);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void addSound(String soundName) {
        SoundManager.addSound(soundName);
    }
}

