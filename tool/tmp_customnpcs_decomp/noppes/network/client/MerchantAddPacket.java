/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abm
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import noppes.common.events.EventLoader;

public class MerchantAddPacket
extends RCsPacket {
    private abm ls;

    public MerchantAddPacket(abm ls2) {
        this.ls = ls2;
    }

    public MerchantAddPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        byte[] data = out.toByteArray();
        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        os2.write(data);
        this.ls.a(new DataOutputStream(os2));
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.ls = abm.a((DataInputStream)((DataInputStream)in));
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        EventLoader.Merchant.a(this.ls);
    }
}

