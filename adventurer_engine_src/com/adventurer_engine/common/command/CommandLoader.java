/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ab
 *  cpw.mods.fml.common.event.FMLServerStartingEvent
 */
package com.adventurer_engine.common.command;

import com.adventurer_engine.common.command.CommandModifyDate;
import com.adventurer_engine.common.command.CommandTitle;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommandLoader {
    public static void load(FMLServerStartingEvent event) {
        event.registerServerCommand((ab)new CommandTitle());
        event.registerServerCommand((ab)new CommandModifyDate());
    }
}

