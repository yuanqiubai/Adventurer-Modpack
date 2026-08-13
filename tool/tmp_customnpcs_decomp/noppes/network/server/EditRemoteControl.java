/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  jv
 *  nn
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.CustomNpcs;
import noppes.common.constants.EnumGuiType;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.client.ScrollPackets;
import noppes.util.NoppesUtilServer;

public class EditRemoteControl {

    public static class RemoteTpToNpcPacket
    extends RCsPacket {
        private int entityId;

        public RemoteTpToNpcPacket(int entityId) {
            this.entityId = entityId;
        }

        public RemoteTpToNpcPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.entityId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.entityId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            nn entity = player.q.a(this.entityId);
            if (!(entity instanceof EntityNPCInterface)) {
                return;
            }
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            ((jv)player).a.a(npc.u, npc.v, npc.w, 0.0f, 0.0f);
        }
    }

    public static class RemoteResetPacket
    extends RCsPacket {
        private int entityId;

        public RemoteResetPacket(int entityId) {
            this.entityId = entityId;
        }

        public RemoteResetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.entityId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.entityId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            nn entity = player.q.a(this.entityId);
            if (!(entity instanceof EntityNPCInterface)) {
                return;
            }
            ((EntityNPCInterface)entity).reset();
        }
    }

    public static class RemoteFreezePacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            CustomNpcs.FreezeNPCs = !CustomNpcs.FreezeNPCs;
            String option = CustomNpcs.FreezeNPCs ? "Unfreeze Npcs" : "Freeze Npcs";
            PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollSelectedPacket(option).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class RemoteNpcsGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendNearbyNpcs((jv)player);
            String option = CustomNpcs.FreezeNPCs ? "Unfreeze Npcs" : "Freeze Npcs";
            PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollSelectedPacket(option).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class RemoteDeletePacket
    extends RCsPacket {
        private int entityId;

        public RemoteDeletePacket(int entityId) {
            this.entityId = entityId;
        }

        public RemoteDeletePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.entityId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.entityId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            nn entity = player.q.a(this.entityId);
            if (!(entity instanceof EntityNPCInterface)) {
                return;
            }
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            npc.delete();
            NoppesUtilServer.deleteNpc(npc, player);
            NoppesUtilServer.sendNearbyNpcs((jv)player);
        }
    }

    public static class RemoteMainMenuPacket
    extends RCsPacket {
        private int entityId;

        public RemoteMainMenuPacket(int entityId) {
            this.entityId = entityId;
        }

        public RemoteMainMenuPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.entityId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.entityId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            nn entity3 = player.q.a(this.entityId);
            if (!(entity3 instanceof EntityNPCInterface)) {
                return;
            }
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.MainMenuDisplay, (EntityNPCInterface)entity3);
        }
    }
}

