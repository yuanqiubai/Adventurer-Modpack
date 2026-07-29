/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ats
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraftforge.event.Event
 */
package com.adventurer_engine.client.key;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.event.Event;

@SideOnly(value=Side.CLIENT)
public class KeyInputEvent
extends Event {
    private int type;
    public ats key;

    public KeyInputEvent(ats key, int type) {
        this.key = key;
        this.type = type;
    }

    public boolean keyDown() {
        return this.type == 1;
    }

    public boolean keyUp() {
        return this.type == 2;
    }
}

