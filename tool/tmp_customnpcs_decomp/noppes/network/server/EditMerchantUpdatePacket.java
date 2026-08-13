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
 *  nn
 *  ub
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EditMerchantUpdatePacket
extends RCsPacket {
    private int entityId;
    private abm merchantRecipes;

    public EditMerchantUpdatePacket(int entityId, abm merchantRecipes) {
        this.entityId = entityId;
        this.merchantRecipes = merchantRecipes;
    }

    public EditMerchantUpdatePacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.entityId);
        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        os2.write(out.toByteArray());
        this.merchantRecipes.a(new DataOutputStream(os2));
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.entityId = in.readInt();
        this.merchantRecipes = abm.a((DataInputStream)((DataInputStream)in));
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        nn entity = player.q.a(this.entityId);
        if (!(entity instanceof ub)) {
            return;
        }
        ((ub)entity).a(this.merchantRecipes);
    }
}

