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

import com.adventurer_engine.client.hud.IGuiHUDBase;
import org.lwjgl.opengl.GL11;

public class HUDPlayTitle
implements IGuiHUDBase {
    private boolean isDisplaying = false;
    private String title = null;
    private static final int PLAY_DELAY = 2000;
    private long startTime = 0L;
    private int offsetX_Left = 0;
    private static final double SCALE = 3.0;
    private static final double RE_SCALE = 0.3333333333333333;
    private static final int OFFSET_Y_UP = 40;

    @Override
    public boolean canDisplay(bex player) {
        if (!this.isDisplaying) {
            return false;
        }
        if (System.currentTimeMillis() - this.startTime > 2000L) {
            this.isDisplaying = false;
            this.startTime = 0L;
            return false;
        }
        return true;
    }

    @Override
    public void display(bex player, awf scaledResolution) {
        int offsetX = scaledResolution.a() / 2 - this.offsetX_Left;
        int offsetY = scaledResolution.b() / 2 - 40;
        offsetX = (int)((double)offsetX / 3.0);
        offsetY = (int)((double)offsetY / 3.0);
        GL11.glScaled((double)3.0, (double)3.0, (double)3.0);
        HUDPlayTitle.mc.l.a(this.title, offsetX, offsetY, -1610612737, true);
        GL11.glScaled((double)0.3333333333333333, (double)0.3333333333333333, (double)0.3333333333333333);
    }

    @Override
    public bjo getTexture(bex player) {
        return null;
    }

    @Override
    public String getHUDName() {
        return "title";
    }

    public void displayTitle(String title) {
        this.isDisplaying = true;
        this.title = title;
        this.startTime = System.currentTimeMillis();
        this.offsetX_Left = (int)((double)(HUDPlayTitle.mc.l.a(title) / 2) * 3.0);
    }
}

