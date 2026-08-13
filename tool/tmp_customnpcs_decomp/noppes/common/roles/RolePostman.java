/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 */
package noppes.common.roles;

import java.util.ArrayList;
import java.util.List;
import noppes.common.constants.EnumGuiType;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.controllers.PlayerDataController;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleInterface;
import noppes.util.NoppesUtilServer;

public class RolePostman
extends RoleInterface {
    public NpcMiscInventory inventory = new NpcMiscInventory(1);
    private final List<uf> recentlyChecked = new ArrayList<uf>();
    private List<uf> toCheck;

    public RolePostman(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public boolean aiShouldExecute() {
        if (this.npc.ac % 20 != 0) {
            return false;
        }
        this.toCheck = this.npc.q.a(uf.class, this.npc.E.b(10.0, 10.0, 10.0));
        this.toCheck.removeAll(this.recentlyChecked);
        List listMax = this.npc.q.a(uf.class, this.npc.E.b(20.0, 20.0, 20.0));
        this.recentlyChecked.retainAll(listMax);
        this.recentlyChecked.addAll(this.toCheck);
        for (uf player : this.toCheck) {
            if (!PlayerDataController.instance.hasMail(player)) continue;
            player.a("You've got mail");
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("PostInv", this.inventory.getToNBT());
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.inventory.setFromNBT(nbttagcompound.l("PostInv"));
    }

    @Override
    public boolean interact(uf player) {
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerMailman, this.npc);
        return true;
    }
}

