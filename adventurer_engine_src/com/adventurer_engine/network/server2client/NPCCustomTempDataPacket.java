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

import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class NPCCustomTempDataPacket
extends RCsPacket {
    private String varName;
    private int value;
    private int entityId;

    public NPCCustomTempDataPacket(String varName, int value, int entityId) {
        this.varName = varName;
        this.value = value;
        this.entityId = entityId;
    }

    public NPCCustomTempDataPacket() {
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeUTF(this.varName);
        out.writeInt(this.value);
        out.writeInt(this.entityId);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        this.varName = in.readUTF();
        this.value = in.readInt();
        this.entityId = in.readInt();
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (side.isServer()) {
            return;
        }
        nn target = atv.w().f.a(this.entityId);
        if (!(target instanceof EntityRCsNPCBase)) {
            return;
        }
        ((EntityRCsNPCBase)target).tempData.setCustomVar(this.varName, this.value);
    }
}

