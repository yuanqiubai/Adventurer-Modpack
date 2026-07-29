/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ci
 *  cl
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  jv
 *  uf
 */
package com.adventurer_engine.network.client2server;

import com.adventurer_engine.common.PlayerStatusData;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PlayerDataSynchronousC2S {

    public static class SendIntArray
    extends RCsPacket {
        private String key;
        private int[] toSend;

        public SendIntArray(String key, int[] toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendIntArray() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeInt(this.toSend.length);
            for (int i : this.toSend) {
                out.writeInt(i);
            }
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = new int[in.readInt()];
            for (int i = 0; i < this.toSend.length; ++i) {
                this.toSend[i] = in.readInt();
            }
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendByteArray
    extends RCsPacket {
        private String key;
        private byte[] toSend;

        public SendByteArray(String key, byte[] toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendByteArray() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeInt(this.toSend.length);
            out.write(this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = new byte[in.readInt()];
            in.readFully(this.toSend);
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendNBT
    extends RCsPacket {
        private String key;
        private by toSend;

        public SendNBT(String key, by toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendNBT() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            ci.a((by)this.toSend, (DataOutput)out);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = ci.a((DataInput)in);
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, (cl)this.toSend);
        }
    }

    public static class SendBoolean
    extends RCsPacket {
        private String key;
        private boolean toSend;

        public SendBoolean(String key, boolean toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendBoolean() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeBoolean(this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readBoolean();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
        }
    }

    public static class SendString
    extends RCsPacket {
        private String key;
        private String toSend;

        public SendString(String key, String toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendString() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeUTF(this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readUTF();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendDouble
    extends RCsPacket {
        private String key;
        private double toSend;

        public SendDouble(String key, double toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendDouble() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeDouble(this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readDouble();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendFloat
    extends RCsPacket {
        private String key;
        private float toSend;

        public SendFloat(String key, float toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendFloat() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeFloat(this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readFloat();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendLong
    extends RCsPacket {
        private String key;
        private long toSend;

        public SendLong(String key, long toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendLong() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeLong(this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readLong();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendInt
    extends RCsPacket {
        private String key;
        private int toSend;

        public SendInt(String key, int toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendInt() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeInt(this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readInt();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendShort
    extends RCsPacket {
        private String key;
        private short toSend;

        public SendShort(String key, short toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendShort() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeShort((int)this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readShort();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }

    public static class SendByte
    extends RCsPacket {
        private String key;
        private byte toSend;

        public SendByte(String key, byte toSend) {
            this.key = key;
            this.toSend = toSend;
        }

        public SendByte() {
        }

        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.key);
            out.writeByte((int)this.toSend);
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
            this.key = in.readUTF();
            this.toSend = in.readByte();
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            assert (side == Side.SERVER);
            jv mp = (jv)player;
            PlayerStatusData.getPlayerData(mp).getData().a(this.key, this.toSend);
        }
    }
}

