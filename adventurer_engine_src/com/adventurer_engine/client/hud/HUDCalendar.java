/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  awf
 *  bex
 *  bjo
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.hud;

import com.adventurer_engine.client.ClientProxy;
import com.adventurer_engine.client.hud.IGuiHUDBase;
import com.adventurer_engine.util.Tools;
import org.lwjgl.opengl.GL11;

public class HUDCalendar
implements IGuiHUDBase {
    private static final float SCALE = 1.0f;
    private static final float RE_SCALE = 1.0f;

    @Override
    public boolean canDisplay(bex player) {
        return true;
    }

    @Override
    public void display(bex player, awf scaledResolution) {
        GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
        HUDCalendar.mc.l.a(Tools.formatCalendar(ClientProxy.worldCalendar), 5, 5, 0xFFFFFF, true);
        GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Override
    public bjo getTexture(bex player) {
        return null;
    }

    @Override
    public String getHUDName() {
        return "calendar";
    }
}

