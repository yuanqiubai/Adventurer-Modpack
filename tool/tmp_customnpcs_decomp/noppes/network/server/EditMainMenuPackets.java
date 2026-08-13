/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.DebugGadgets
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.client.GuiPackets;
import noppes.network.client.UpdateNpcPacket;
import noppes.util.NoppesUtilServer;

public class EditMainMenuPackets {

    public static class MainmenuAdvancedSave
    extends RCsPacket {
        private by nbt;

        public MainmenuAdvancedSave(by nbt) {
            this.nbt = nbt;
        }

        public MainmenuAdvancedSave() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            npc.advanced.readToNBT(this.nbt);
            PacketDispatcher.sendPacketToAllPlayers((ey)new UpdateNpcPacket(npc.copy()).makePacket("CNPCs Client"));
        }
    }

    public static class MainmenuAdvancedGet
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            by nbt = npc.advanced.writeToNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class MainmenuAISave
    extends RCsPacket {
        private by nbt;

        public MainmenuAISave(by nbt) {
            this.nbt = nbt;
        }

        public MainmenuAISave() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            npc.ai.readToNBT(this.nbt);
            PacketDispatcher.sendPacketToAllPlayers((ey)new UpdateNpcPacket(npc.copy()).makePacket("CNPCs Client"));
        }
    }

    public static class MainmenuAIGet
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            by nbt = npc.ai.writeToNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class MainmenuInvSave
    extends RCsPacket {
        private by nbt;

        public MainmenuInvSave(by nbt) {
            this.nbt = nbt;
        }

        public MainmenuInvSave() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            npc.inventory.readEntityFromNBT(this.nbt);
            PacketDispatcher.sendPacketToAllPlayers((ey)new UpdateNpcPacket(npc.copy()).makePacket("CNPCs Client"));
        }
    }

    public static class MainmenuInvGet
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            by nbt = npc.inventory.writeEntityToNBT(new by());
            DebugGadgets.debugInfo((String)"send it");
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class MainmenuStatsSave
    extends RCsPacket {
        private by nbt;

        public MainmenuStatsSave(by nbt) {
            this.nbt = nbt;
        }

        public MainmenuStatsSave() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            npc.stats.readToNBT(this.nbt);
            PacketDispatcher.sendPacketToAllPlayers((ey)new UpdateNpcPacket(npc.copy()).makePacket("CNPCs Client"));
        }
    }

    public static class MainmenuStatsGet
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            by nbt = npc.stats.writeToNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class MainmenuDisplaySave
    extends RCsPacket {
        private by nbt;

        public MainmenuDisplaySave(by nbt) {
            this.nbt = nbt;
        }

        public MainmenuDisplaySave() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            npc.display.readToNBT(this.nbt);
            PacketDispatcher.sendPacketToAllPlayers((ey)new UpdateNpcPacket(npc.copy()).makePacket("CNPCs Client"));
        }
    }

    public static class MainmenuDisplayGet
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            by nbt = npc.display.writeToNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }
}

