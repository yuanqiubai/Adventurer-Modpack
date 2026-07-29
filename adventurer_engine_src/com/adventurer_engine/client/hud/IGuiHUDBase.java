/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  awf
 *  bex
 *  bjo
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package com.adventurer_engine.client.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public interface IGuiHUDBase {
    public static final atv mc = atv.w();

    public boolean canDisplay(bex var1);

    public void display(bex var1, awf var2);

    public bjo getTexture(bex var1);

    public String getHUDName();
}

