/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
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

public class PlayerDialogPacket
extends RCsPacket {
    private int dialogId;
    private int select;

    public PlayerDialogPacket(int dialogId, int select) {
        this.dialogId = dialogId;
        this.select = select;
    }

    public PlayerDialogPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.dialogId);
        out.writeInt(this.select);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.dialogId = in.readInt();
        this.select = in.readInt();
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
        if (npc == null) {
            return;
        }
        NoppesUtilPlayer.dialogSelected(this.dialogId, this.select, player, npc);
    }
}

