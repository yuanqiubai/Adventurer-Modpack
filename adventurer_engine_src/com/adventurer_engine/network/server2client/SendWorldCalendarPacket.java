/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package com.adventurer_engine.network.server2client;

import com.adventurer_engine.client.ClientProxy;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class SendWorldCalendarPacket
extends RCsPacket {
    private long calendar;

    public SendWorldCalendarPacket() {
    }

    public SendWorldCalendarPacket(long calendar) {
        this.calendar = calendar;
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeLong(this.calendar);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        this.calendar = in.readLong();
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (side.isServer()) {
            return;
        }
        ClientProxy.worldCalendar = this.calendar;
    }
}

