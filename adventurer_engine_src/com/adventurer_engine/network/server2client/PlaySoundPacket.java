/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  nn
 *  uf
 */
package com.adventurer_engine.network.server2client;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class PlaySoundPacket
extends RCsPacket {
    private String sound;
    private float volume;
    private float pitch;
    private double x;
    private double y;
    private double z;

    public PlaySoundPacket() {
    }

    public PlaySoundPacket(String sound, float volume, float pitch, double x, double y, double z2) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z2;
    }

    public PlaySoundPacket(String sound, float volume, float pitch, nn entity) {
        this(sound, volume, pitch, entity != null ? entity.u : 0.0, entity != null ? entity.v : 0.0, entity != null ? entity.w : 0.0);
    }

    public PlaySoundPacket(String sound, float volume, float pitch) {
        this(sound, volume, pitch, null);
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeUTF(this.sound);
        out.writeFloat(this.volume);
        out.writeFloat(this.pitch);
        out.writeDouble(this.x);
        out.writeDouble(this.y);
        out.writeDouble(this.z);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        this.sound = in.readUTF();
        this.volume = in.readFloat();
        this.pitch = in.readFloat();
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.z = in.readDouble();
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (side.isClient()) {
            if ("bow".equals(this.sound)) {
                player.a("random.bow", this.volume, this.pitch);
            } else {
                player.a("adventurer_engine:" + this.sound, this.volume, this.pitch);
            }
        } else {
            player.q.a(this.x, this.y, this.z, this.sound, this.volume, this.pitch);
        }
    }
}

