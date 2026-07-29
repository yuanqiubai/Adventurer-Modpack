/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ci
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  uf
 */
package com.adventurer_engine.network.server2client;

import com.adventurer_engine.common.gui_handler.BackpackInventory;
import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.network.client2server.ClientBackpackHandlerPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class SendBackpackData
extends RCsPacket {
    public static BackpackInventory clientInv;
    by invNBT;

    public SendBackpackData() {
    }

    public SendBackpackData(by invNBT) {
        this.invNBT = invNBT;
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        byte[] nbtData = ci.a((by)this.invNBT);
        out.writeInt(nbtData.length);
        out.write(nbtData);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        int len = in.readInt();
        byte[] nbtData = new byte[len];
        in.readFully(nbtData);
        this.invNBT = ci.a((byte[])nbtData);
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (side.isServer()) {
            return;
        }
        clientInv = new BackpackInventory(this.invNBT);
        PacketDispatcher.sendPacketToServer((ey)new ClientBackpackHandlerPacket.AcknowledgeReceiveBackpackData().makePacket());
    }
}

