/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  nn
 *  uf
 */
package noppes.common.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.List;
import noppes.common.constants.EnumGuiType;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerTransportData;
import noppes.common.controllers.TransportController;
import noppes.common.controllers.TransportLocation;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleInterface;
import noppes.network.client.ChatPacket;
import noppes.util.NoppesUtilServer;

public class RoleTransporter
extends RoleInterface {
    public int transportId = -1;
    public String name;
    private int ticks = 10;

    public RoleTransporter(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("TransporterId", this.transportId);
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.transportId = nbttagcompound.e("TransporterId");
        TransportLocation loc = this.getLocation();
        if (loc != null) {
            this.name = loc.name;
        }
        if (loc == null) {
            this.transportId = -1;
            this.name = "";
        }
    }

    @Override
    public boolean aiShouldExecute() {
        --this.ticks;
        if (this.ticks > 0) {
            return false;
        }
        this.ticks = 10;
        if (!this.hasTransport()) {
            return false;
        }
        TransportLocation loc = this.getLocation();
        if (loc.type != 0) {
            return false;
        }
        List inRange = this.npc.q.a(uf.class, this.npc.E.b(6.0, 6.0, 6.0));
        for (uf player : inRange) {
            if (!this.npc.o((nn)player)) continue;
            this.unlock(player, loc);
        }
        return false;
    }

    private void unlock(uf player, TransportLocation loc) {
        PlayerTransportData data = PlayerDataController.instance.getPlayerData((uf)player).transportData;
        if (!data.transports.contains(this.transportId)) {
            data.transports.add(this.transportId);
            String[] chatInfo = new String[]{"transporter.unlock1", " ", loc.name, " ", "transporter.unlock2"};
            PacketDispatcher.sendPacketToPlayer((ey)new ChatPacket(chatInfo).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    @Override
    public void aiStartExecuting() {
    }

    @Override
    public boolean interact(uf player) {
        if (this.hasTransport()) {
            TransportLocation loc = this.getLocation();
            if (loc.type == 2) {
                this.unlock(player, loc);
            }
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTransporter, this.npc);
        }
        return false;
    }

    public TransportLocation getLocation() {
        return this.npc.q.I ? null : TransportController.getInstance().getTransport(this.transportId);
    }

    public boolean hasTransport() {
        TransportLocation loc = this.getLocation();
        return loc != null && loc.isNpc(this.npc);
    }

    public void setTransport(TransportLocation location) {
        this.transportId = location.id;
        this.name = location.name;
    }
}

