/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  jv
 *  uf
 */
package noppes.network.player;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtilPlayer;
import noppes.util.NoppesUtilServer;

public class TransportPacket
extends RCsPacket {
    private String select;

    public TransportPacket(String select) {
        this.select = select;
    }

    public TransportPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeUTF(this.select);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.select = in.readUTF();
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
        if (npc == null) {
            return;
        }
        NoppesUtilPlayer.transport((jv)player, npc, this.select);
    }
}

