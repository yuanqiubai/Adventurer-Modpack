/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bex
 *  by
 *  cl
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ey
 */
package com.adventurer_engine.client;

import com.adventurer_engine.network.client2server.PlayerDataSynchronousC2S;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class PlayerStatusDataClient {
    private by data = new by();
    private bex player;
    private static final PlayerStatusDataClient INSTANCE = new PlayerStatusDataClient();

    private PlayerStatusDataClient() {
        this.player = atv.w().h;
    }

    public static PlayerStatusDataClient getInstance() {
        return INSTANCE;
    }

    public byte getByte(String key) {
        return this.data.c(key);
    }

    public short getShort(String key) {
        return this.data.d(key);
    }

    public int getInt(String key) {
        return this.data.e(key);
    }

    public long getLong(String key) {
        return this.data.f(key);
    }

    public float getFloat(String key) {
        return this.data.g(key);
    }

    public double getDouble(String key) {
        return this.data.h(key);
    }

    public String getString(String key) {
        return this.data.b(key) ? this.data.i(key) : null;
    }

    public boolean getBoolean(String key) {
        return this.data.n(key);
    }

    public byte[] getByteArray(String key) {
        return this.data.j(key);
    }

    public int[] getIntArray(String key) {
        return this.data.k(key);
    }

    public by getCompound(String key) {
        return this.data.l(key);
    }

    public by getData() {
        return this.data;
    }

    public boolean hasKey(String key) {
        return this.data.b(key);
    }

    public void setByte(String key, byte b) {
        this.data.a(key, b);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendByte(key, b).makePacket());
    }

    public void setShort(String key, short s) {
        this.data.a(key, s);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendShort(key, s).makePacket());
    }

    public void setInt(String key, int i) {
        this.data.a(key, i);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendInt(key, i).makePacket());
    }

    public void setLong(String key, long l) {
        this.data.a(key, l);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendLong(key, l).makePacket());
    }

    public void setFloat(String key, float f) {
        this.data.a(key, f);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendFloat(key, f).makePacket());
    }

    public void setDouble(String key, double d) {
        this.data.a(key, d);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendDouble(key, d).makePacket());
    }

    public void setString(String key, String s) {
        this.data.a(key, s);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendString(key, s).makePacket());
    }

    public void setBoolean(String key, boolean b) {
        this.data.a(key, b);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendBoolean(key, b).makePacket());
    }

    public void setByteArray(String key, byte[] b) {
        this.data.a(key, b);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendByteArray(key, b).makePacket());
    }

    public void setIntArray(String key, int[] i) {
        this.data.a(key, i);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendIntArray(key, i).makePacket());
    }

    public void setCompound(String key, by nbt) {
        this.data.a(key, (cl)nbt);
        PacketDispatcher.sendPacketToServer((ey)new PlayerDataSynchronousC2S.SendNBT(key, nbt).makePacket());
    }
}

