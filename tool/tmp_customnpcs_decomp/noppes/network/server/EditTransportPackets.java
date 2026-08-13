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
import noppes.common.constants.EnumRoleType;
import noppes.common.controllers.TransportController;
import noppes.common.controllers.TransportLocation;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleTransporter;
import noppes.network.client.GuiPackets;
import noppes.network.client.ScrollPackets;
import noppes.util.NoppesUtilServer;

public class EditTransportPackets {

    public static class TransportGetLocationPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (npc.advanced.role != EnumRoleType.Transporter) {
                return;
            }
            RoleTransporter role = (RoleTransporter)npc.roleInterface;
            if (role.hasTransport()) {
                by nbt = role.getLocation().writeNBT();
                PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
                PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollSelectedPacket(role.getLocation().category.title).makePacket("CNPCs Client"), (Player)((Player)player));
            }
        }
    }

    public static class TransportSavePacket
    extends RCsPacket {
        private int id;
        private by nbt;

        public TransportSavePacket(int id, by nbt) {
            this.id = id;
            this.nbt = nbt;
        }

        public TransportSavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            TransportLocation transList = TransportController.getInstance().saveLocation(this.id, this.nbt, (jv)player, NoppesUtilServer.getEditingNpc(player));
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (transList == null) {
                return;
            }
            if (npc.advanced.role != EnumRoleType.Transporter) {
                return;
            }
            ((RoleTransporter)npc.roleInterface).setTransport(transList);
            NoppesUtilServer.sendTransportData((jv)player, this.id);
            by nbt = transList.writeNBT();
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class TransportsGetPacket
    extends RCsPacket {
        private int id;

        public TransportsGetPacket(int id) {
            this.id = id;
        }

        public TransportsGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        }
    }

    public static class TransportRemovePacket
    extends RCsPacket {
        private int id;

        public TransportRemovePacket(int id) {
            this.id = id;
        }

        public TransportRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            TransportController.getInstance().removeLocation(this.id);
            NoppesUtilServer.sendTransportData((jv)player, this.id);
        }
    }

    public static class TransportCategoryRemovePacket
    extends RCsPacket {
        private int id;

        public TransportCategoryRemovePacket(int id) {
            this.id = id;
        }

        public TransportCategoryRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            TransportController.getInstance().removeCategory(this.id);
            NoppesUtilServer.sendTransportCategoryData((jv)player);
        }
    }

    public static class TransportCategorySavePacket
    extends RCsPacket {
        private String name;
        private int id;

        public TransportCategorySavePacket(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public TransportCategorySavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.name);
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.name = in.readUTF();
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            TransportController.getInstance().saveCategory(this.name, this.id);
        }
    }

    public static class TransportCategoriesGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendTransportCategoryData((jv)player);
        }
    }
}

