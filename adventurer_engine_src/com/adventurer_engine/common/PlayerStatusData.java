/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  jv
 */
package com.adventurer_engine.common;

import com.adventurer_engine.network.server2client.PlayerDataSynchronousS2C;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStatusData {
    private static Map<UUID, PlayerStatusData> playerEntry = new HashMap<UUID, PlayerStatusData>();
    private final by data = new by();
    private Player playerObj;

    private PlayerStatusData() {
    }

    public static synchronized PlayerStatusData getPlayerData(jv player) {
        if (playerEntry.containsKey(player.aw())) {
            PlayerStatusData pd = playerEntry.get(player.aw());
            pd.playerObj = (Player)player;
            return pd;
        }
        PlayerStatusData playerData = new PlayerStatusData();
        playerEntry.put(player.aw(), playerData);
        playerData.playerObj = (Player)player;
        return playerData;
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
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendByte(key, b).makePacket(), (Player)this.playerObj);
    }

    public void setShort(String key, short s) {
        this.data.a(key, s);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendShort(key, s).makePacket(), (Player)this.playerObj);
    }

    public void setInt(String key, int i) {
        this.data.a(key, i);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendInt(key, i).makePacket(), (Player)this.playerObj);
    }

    public void setLong(String key, long l) {
        this.data.a(key, l);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendLong(key, l).makePacket(), (Player)this.playerObj);
    }

    public void setFloat(String key, float f) {
        this.data.a(key, f);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendFloat(key, f).makePacket(), (Player)this.playerObj);
    }

    public void setDouble(String key, double d) {
        this.data.a(key, d);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendDouble(key, d).makePacket(), (Player)this.playerObj);
    }

    public void setString(String key, String s) {
        this.data.a(key, s);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendString(key, s).makePacket(), (Player)this.playerObj);
    }

    public void setBoolean(String key, boolean b) {
        this.data.a(key, b);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendBoolean(key, b).makePacket(), (Player)this.playerObj);
    }

    public void setByteArray(String key, byte[] b) {
        this.data.a(key, b);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendByteArray(key, b).makePacket(), (Player)this.playerObj);
    }

    public void setIntArray(String key, int[] i) {
        this.data.a(key, i);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendIntArray(key, i).makePacket(), (Player)this.playerObj);
    }

    public void setCompound(String key, by nbt) {
        this.data.a(key, (cl)nbt);
        PacketDispatcher.sendPacketToPlayer((ey)new PlayerDataSynchronousS2C.SendNBT(key, nbt).makePacket(), (Player)this.playerObj);
    }
}

