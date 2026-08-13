/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.DebugGadgets
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  nn
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;

public class DialogPacket
extends RCsPacket {
    private int entityId;
    private by nbt;

    public DialogPacket(int entityId, by nbt) {
        this.entityId = entityId;
        this.nbt = nbt;
    }

    public DialogPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.entityId);
        Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.entityId = in.readInt();
        this.nbt = Tools.readNBT((ByteArrayDataInput)in);
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        nn entity = atv.w().f.a(this.entityId);
        if (!(entity instanceof EntityNPCInterface)) {
            return;
        }
        try {
            NoppesUtil.openDialog(this.nbt, (EntityNPCInterface)entity, player);
        }
        catch (IOException e) {
            DebugGadgets.debugInfo((String)e.getMessage());
        }
    }
}

