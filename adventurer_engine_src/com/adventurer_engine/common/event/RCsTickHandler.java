/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.TickType
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  js
 *  net.minecraft.server.MinecraftServer
 */
package com.adventurer_engine.common.event;

import com.adventurer_engine.common.data.globaldata.IGlobalData;
import com.adventurer_engine.network.server2client.SendWorldCalendarPacket;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.EnumSet;
import net.minecraft.server.MinecraftServer;

public class RCsTickHandler
implements ITickHandler {
    private static final long DAY_TICKS = 24000L;
    public static int healNPCbuf = 60;
    public static long lastWorldTime = -1L;

    public void tickStart(EnumSet<TickType> type, Object ... tickData) {
        this.updateCalendar();
        MinecraftServer mc = MinecraftServer.F();
        if (mc.aj() % 900 == 0) {
            IGlobalData.saveAll();
        }
        if (--healNPCbuf < 0) {
            healNPCbuf = 60;
        }
    }

    private void updateCalendar() {
        if (!IGlobalData.allInitialed()) {
            return;
        }
        MinecraftServer mc = MinecraftServer.F();
        js mainWorld = mc.b[0];
        long currentWorldTime = mainWorld.J();
        long delta = (currentWorldTime - lastWorldTime + 24000L) % 24000L;
        if (lastWorldTime != -1L && delta > 1L) {
            IGlobalData.CALENDAR_DATA.addDataCalendar(delta);
        } else {
            IGlobalData.CALENDAR_DATA.upDataCalendar();
        }
        if ((currentWorldTime %= 24000L) != IGlobalData.CALENDAR_DATA.getCalendarValue() % 24000L) {
            IGlobalData.CALENDAR_DATA.setDayTime((int)currentWorldTime);
        }
        lastWorldTime = currentWorldTime;
        ey pkt = new SendWorldCalendarPacket(IGlobalData.CALENDAR_DATA.getCalendarValue()).makePacket();
        PacketDispatcher.sendPacketToAllPlayers((ey)pkt);
    }

    public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    public String getLabel() {
        return "RCsTickHandler";
    }
}

