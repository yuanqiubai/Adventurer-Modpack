/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  nn
 *  nt
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtilServer;

public class EditChangeModelPacket
extends RCsPacket {
    private int entity;
    private int list;
    private int datatype;
    private by nbt;

    public EditChangeModelPacket(int entity, int list, int datatype, by nbt) {
        this.entity = entity;
        this.list = list;
        this.datatype = datatype;
        this.nbt = nbt;
    }

    public EditChangeModelPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.entity);
        out.writeInt(this.list);
        out.writeInt(this.datatype);
        Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.entity = in.readInt();
        this.list = in.readInt();
        this.datatype = in.readInt();
        this.nbt = Tools.readNBT((ByteArrayDataInput)in);
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
        npc.delete();
        NoppesUtilServer.deleteNpc(npc, player);
        npc = (EntityNPCInterface)nt.a((by)this.nbt, (abw)player.q);
        npc.startPos = new int[]{this.entity, this.list, this.datatype};
        npc.b((float)this.entity + 0.5f, npc.getStartYPos(), (float)this.datatype + 0.5f);
        npc.g(npc.aT());
        player.q.d((nn)npc);
        NoppesUtilServer.setEditingNpc(player, npc);
    }
}

