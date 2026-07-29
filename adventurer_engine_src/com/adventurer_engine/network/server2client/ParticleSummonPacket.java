/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  nn
 *  uf
 */
package com.adventurer_engine.network.server2client;

import com.adventurer_engine.client.vfx.ParticleVFXFactory;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import java.util.Objects;

public class ParticleSummonPacket
extends RCsPacket {
    private int entityId;
    private short type;

    public ParticleSummonPacket(int entityId, short type) {
        this.entityId = entityId;
        this.type = type;
    }

    public ParticleSummonPacket(nn entity, short type) {
        this(entity.k, type);
    }

    public ParticleSummonPacket() {
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.entityId);
        out.writeShort((int)this.type);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        this.entityId = in.readInt();
        this.type = in.readShort();
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        nn entity = player.q.a(this.entityId);
        atv.w().k.a(Objects.requireNonNull(ParticleVFXFactory.generateParticleFX(player.q, entity, this.type)));
    }
}

