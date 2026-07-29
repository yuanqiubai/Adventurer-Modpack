/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package com.adventurer_engine.network.client2server;

import com.adventurer_engine.common.gui_handler.BackpackContainer;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class RequestCollateBackpack
extends RCsPacket {
    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (side.isClient()) {
            return;
        }
        if (player.bp instanceof BackpackContainer) {
            ((BackpackContainer)player.bp).collate();
        }
    }
}

