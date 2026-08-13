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
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  jv
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.controllers.Faction;
import noppes.common.controllers.FactionController;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.client.GuiPackets;
import noppes.util.NoppesUtilServer;

public class EditFactionPackets {

    public static class FactionGetPacket
    extends RCsPacket {
        private int id;

        public FactionGetPacket(int id) {
            this.id = id;
        }

        public FactionGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            by nbt = new by();
            Faction faction = FactionController.getInstance().getFaction(this.id);
            faction.writeNBT(nbt);
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class FactionRemovePacket
    extends RCsPacket {
        private int id;

        public FactionRemovePacket(int id) {
            this.id = id;
        }

        public FactionRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            FactionController.getInstance().removeFaction(this.id);
            NoppesUtilServer.sendFactionDataAll((jv)player);
            by nbt = new by();
            new Faction().writeNBT(nbt);
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class FactionSavePacket
    extends RCsPacket {
        private by nbt;

        public FactionSavePacket(by nbt) {
            this.nbt = nbt;
        }

        public FactionSavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Faction faction = new Faction();
            faction.readNBT(this.nbt);
            FactionController.getInstance().saveFaction(faction);
            NoppesUtilServer.sendFactionDataAll((jv)player);
            by nbtTagCompound = new by();
            faction.writeNBT(nbtTagCompound);
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbtTagCompound).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class FactionSetPacket
    extends RCsPacket {
        private int id;

        public FactionSetPacket(int id) {
            this.id = id;
        }

        public FactionSetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            npc.setFaction(this.id);
        }
    }

    public static class FactionsGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendFactionDataAll((jv)player);
        }
    }
}

