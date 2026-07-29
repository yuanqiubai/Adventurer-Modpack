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

import com.adventurer_engine.client.hud.HUDEngine;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class PlayTitlePacket
extends RCsPacket {
    private String title;

    public PlayTitlePacket() {
    }

    public PlayTitlePacket(String title) {
        this.title = title;
    }

    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeUTF(this.title);
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
        this.title = in.readUTF();
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (side.isServer()) {
            return;
        }
        HUDEngine.HUD_playTitle.displayTitle(this.title);
    }
}

