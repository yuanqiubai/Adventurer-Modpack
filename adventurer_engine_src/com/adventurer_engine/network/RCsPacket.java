/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  adventurer_core.reflactions.PackageClassGetter
 *  by
 *  ci
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.ImmutableBiMap
 *  com.google.common.collect.ImmutableBiMap$Builder
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.io.ByteStreams
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  uf
 */
package com.adventurer_engine.network;

import adventurer_core.reflactions.PackageClassGetter;
import com.adventurer_engine.util.DebugGadgets;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import java.util.List;

public abstract class RCsPacket {
    private static BiMap<Integer, Class<? extends RCsPacket>> idMap;
    private static ImmutableBiMap.Builder<Integer, Class<? extends RCsPacket>> builder;
    private static int idx;

    public static void addPackagePath(String path) {
        try {
            List classes = PackageClassGetter.getClasses((String)path);
            for (Class clazz : classes) {
                builder.put((Object)idx++, (Object)clazz);
            }
            idMap = builder.build();
        }
        catch (Exception e) {
            DebugGadgets.debugInfo(e.getMessage());
        }
    }

    public static RCsPacket constructPacket(int packetId) throws ProtocolException, InstantiationException, IllegalAccessException {
        Class clazz = (Class)idMap.get((Object)packetId);
        if (clazz == null) {
            throw new ProtocolException("Unknown Packet Id!");
        }
        return (RCsPacket)clazz.newInstance();
    }

    public final int getPacketId() {
        if (idMap.inverse().containsKey(this.getClass())) {
            return (Integer)idMap.inverse().get(this.getClass());
        }
        throw new RuntimeException("Packet " + this.getClass().getSimpleName() + " is missing a mapping!");
    }

    public final ey makePacket() {
        return this.makePacket("adv_channel");
    }

    public final ey makePacket(String channel) {
        ByteArrayDataOutput dataOut = ByteStreams.newDataOutput();
        try {
            this.write(dataOut);
        }
        catch (IOException e) {
            System.err.println("Error writing packet: " + e);
        }
        byte[] data = dataOut.toByteArray();
        ByteArrayDataOutput packetOut = ByteStreams.newDataOutput();
        packetOut.writeInt(this.getPacketId());
        packetOut.write(data);
        return PacketDispatcher.getPacket((String)channel, (byte[])packetOut.toByteArray());
    }

    public abstract void write(ByteArrayDataOutput var1) throws IOException;

    public abstract void read(ByteArrayDataInput var1) throws IOException;

    public abstract void execute(uf var1, Side var2) throws ProtocolException;

    public void process(ByteArrayDataInput in, uf player, Side side) throws IOException, ProtocolException {
        this.read(in);
        this.execute(player, side);
    }

    public static void writeNBTTagCompound(by compound, ByteArrayDataOutput out) throws IOException {
        if (compound == null) {
            out.writeShort(-1);
        } else {
            byte[] abyte = ci.a((by)compound);
            out.writeShort((int)((short)abyte.length));
            out.write(abyte);
        }
    }

    public static by readNBTTagCompound(ByteArrayDataInput in) throws IOException {
        short short1 = in.readShort();
        if (short1 < 0) {
            return null;
        }
        byte[] abyte = new byte[short1];
        in.readFully(abyte);
        return ci.a((byte[])abyte);
    }

    public static RCsPacket packetFactory(int id) throws InstantiationException, IllegalAccessException {
        Class c = (Class)idMap.get((Object)id);
        if (c == null) {
            throw new RuntimeException(String.format("Invalid packet id: %d", id));
        }
        return (RCsPacket)c.newInstance();
    }

    static {
        builder = ImmutableBiMap.builder();
        idx = 0;
    }

    public static class ProtocolException
    extends Exception {
        private static final long serialVersionUID = -7850212538983273200L;

        public ProtocolException() {
        }

        public ProtocolException(String message, Throwable cause) {
            super(message, cause);
        }

        public ProtocolException(String message) {
            super(message);
        }

        public ProtocolException(Throwable cause) {
            super(cause);
        }
    }
}

