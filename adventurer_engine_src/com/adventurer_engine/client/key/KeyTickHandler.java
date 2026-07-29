/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ats
 *  atv
 *  bu
 *  cpw.mods.fml.client.registry.KeyBindingRegistry
 *  cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler
 *  cpw.mods.fml.common.TickType
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.Event
 */
package com.adventurer_engine.client.key;

import com.adventurer_engine.client.key.KeyInputEvent;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;

public class KeyTickHandler
extends KeyBindingRegistry.KeyHandler {
    public static final String[] DESC = new String[]{"slide_step", "ammo_up", "ammo_down"};
    private static final int[] KEY_VALUES = new int[]{46, 200, 208};
    public static final ats[] keys = new ats[DESC.length + 2];
    public static final int SLIDE_STEP = 0;
    public static final int AMMO_UP = 1;
    public static final int AMMO_DOWN = 2;

    public KeyTickHandler() {
        super(keys, new boolean[DESC.length + 2]);
        for (int i = 0; i < DESC.length; ++i) {
            KeyTickHandler.keys[i] = new ats("key.adventurer_engine." + DESC[i] + ".desc", KEY_VALUES[i]);
            this.repeatings[i] = false;
        }
        KeyTickHandler.keys[KeyTickHandler.keys.length - 2] = atv.w().u.R;
        KeyTickHandler.keys[KeyTickHandler.keys.length - 1] = atv.w().u.S;
        KeyBindingRegistry.registerKeyBinding((KeyBindingRegistry.KeyHandler)this);
    }

    public void keyDown(EnumSet<TickType> types, ats kb, boolean tickEnd, boolean isRepeat) {
        atv mc = atv.w();
        if (!tickEnd || mc.h == null) {
            return;
        }
        KeyInputEvent event = new KeyInputEvent(kb, 1);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }

    public void keyUp(EnumSet<TickType> types, ats kb, boolean tickEnd) {
        atv mc = atv.w();
        if (!tickEnd || mc.h == null) {
            return;
        }
        KeyInputEvent event = new KeyInputEvent(kb, 2);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    public String getLabel() {
        return bu.a((String)"key.adventurer_engine.label");
    }
}

