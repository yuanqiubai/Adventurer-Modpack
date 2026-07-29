/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aau
 *  aaw
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  ni
 *  nn
 *  uf
 *  ux
 *  ye
 */
package com.adventurer_engine.network.client2server;

import com.adventurer_engine.common.CommonConfig;
import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class RequestSlideStep
extends RCsPacket {
    @Override
    public void write(ByteArrayDataOutput out) throws IOException {
    }

    @Override
    public void read(ByteArrayDataInput in) throws IOException {
    }

    @Override
    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        if (!player.bG.d) {
            boolean isSpeed = player.a(ni.c);
            boolean isSlow = player.a(ni.d);
            int consume = 4;
            if (!(isSpeed && isSlow || !isSpeed)) {
                consume = 3;
            } else if (!isSpeed && isSlow) {
                consume = 8;
            }
            ux fs = player.bI();
            fs.a(Math.max(0, fs.a() - consume));
        }
        player.af = CommonConfig.slideStepTick + aaw.a((int)aau.f.z, (ye)player.n(4));
        float volume = player.q.s.nextFloat() * 0.4f + 0.5f;
        float frequency = 1.0f / (player.q.s.nextFloat() * 0.4f + 0.5f);
        player.q.a((nn)player, "adventurer_engine:slide_step", volume, frequency);
        player.dur_delay = CommonConfig.resumeStop;
    }
}

