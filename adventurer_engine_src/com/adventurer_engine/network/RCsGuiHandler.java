/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  cpw.mods.fml.common.network.IGuiHandler
 *  cpw.mods.fml.common.network.NetworkRegistry
 *  uf
 */
package com.adventurer_engine.network;

import com.adventurer_engine.AdventurerEngine;
import com.adventurer_engine.client.gui.GuiBackpackContainer;
import com.adventurer_engine.common.gui_handler.BackpackContainer;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class RCsGuiHandler
implements IGuiHandler {
    public static final int GUI_BACKPACK_ID = 1;

    public RCsGuiHandler() {
        NetworkRegistry.instance().registerGuiHandler((Object)AdventurerEngine.instance, (IGuiHandler)this);
    }

    public Object getServerGuiElement(int ID, uf player, abw world, int x, int y, int z2) {
        switch (ID) {
            case 1: {
                return new BackpackContainer(player, player.aZ());
            }
        }
        return null;
    }

    public Object getClientGuiElement(int ID, uf player, abw world, int x, int y, int z2) {
        switch (ID) {
            case 1: {
                return new GuiBackpackContainer(new BackpackContainer(player, player.aZ()));
            }
        }
        return null;
    }
}

