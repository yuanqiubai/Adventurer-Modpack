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

import com.adventurer_engine.client.model.rc.RCsAnimationManager;
import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class NPCAnimationPacket
extends RCsPacket {
    private String animationName;
    private int entityId;

    public NPCAnimationPacket(String animationName, int entityId) {
        this.animationName = animationName;
        this.entityId = entityId;
    }

    public NPCAnimationPacket() {
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeUTF(this.animationName);
        out.writeInt(this.entityId);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        this.animationName = in.readUTF();
        this.entityId = in.readInt();
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (side.isServer()) {
            return;
        }
        nn entity = atv.w().f.a(this.entityId);
        if (!(entity instanceof EntityRCsNPCBase)) {
            return;
        }
        EntityRCsNPCBase npc = (EntityRCsNPCBase)entity;
        if (npc.animation == null) {
            npc.animation = new RCsAnimationManager();
        }
        npc.setAnimation(this.animationName);
    }
}

