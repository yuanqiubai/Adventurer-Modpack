/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.client.UpdateNpcPacket;
import noppes.util.NoppesUtilServer;

public class EditSaveNpcPacket
extends RCsPacket {
    private by nbt;

    public EditSaveNpcPacket(by nbt) {
        this.nbt = nbt;
    }

    public EditSaveNpcPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.nbt = Tools.readNBT((ByteArrayDataInput)in);
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
        npc.f(this.nbt);
        npc.reset();
        PacketDispatcher.sendPacketToAllPlayers((ey)new UpdateNpcPacket(npc.copy()).makePacket("CNPCs Client"));
    }
}

