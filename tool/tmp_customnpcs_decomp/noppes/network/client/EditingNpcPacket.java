/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  nn
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;

public class EditingNpcPacket
extends RCsPacket {
    private int entityId;

    public EditingNpcPacket(int entityId) {
        this.entityId = entityId;
    }

    public EditingNpcPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.entityId);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.entityId = in.readInt();
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        nn entity = atv.w().f.a(this.entityId);
        if (!(entity instanceof EntityNPCInterface)) {
            return;
        }
        NoppesUtil.setLastNpc((EntityNPCInterface)entity);
    }
}

