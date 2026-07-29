/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  avk
 *  bdi
 *  bex
 *  bjo
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.ForgeSubscribe
 */
package com.adventurer_engine.client.hud;

import com.adventurer_engine.client.hud.HUDAmmoSelect;
import com.adventurer_engine.client.hud.HUDCalendar;
import com.adventurer_engine.client.hud.HUDPlayTitle;
import com.adventurer_engine.client.hud.IGuiHUDBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(value=Side.CLIENT)
public class HUDEngine {
    private static ArrayList<IGuiHUDBase> allHUD = new ArrayList();
    public static HUDAmmoSelect HUD_ammoSelect = new HUDAmmoSelect();
    public static HUDPlayTitle HUD_playTitle = new HUDPlayTitle();
    public static HUDCalendar HUD_calendar = new HUDCalendar();

    public HUDEngine() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @ForgeSubscribe
    public void renderHUD(RenderGameOverlayEvent event) {
        atv mc = atv.w();
        bdi player = mc.h;
        bjo hudTexture = null;
        for (IGuiHUDBase hud : allHUD) {
            if (!hud.canDisplay((bex)player)) continue;
            hudTexture = hud.getTexture((bex)player);
            mc.C.a(hud.getHUDName());
            if (hudTexture != null) {
                mc.J().a(hudTexture);
            }
            hud.display((bex)player, event.resolution);
            mc.C.b();
            mc.J().a(avk.m);
        }
    }

    public static void registerHUD() {
        allHUD.add(HUD_ammoSelect);
        allHUD.add(HUD_playTitle);
        allHUD.add(HUD_calendar);
        new HUDEngine();
    }
}

