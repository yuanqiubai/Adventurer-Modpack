/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 *  ye
 */
package noppes.common.roles;

import java.util.Iterator;
import noppes.common.constants.EnumGuiType;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleInterface;
import noppes.util.NoppesUtilPlayer;
import noppes.util.NoppesUtilServer;

public class RoleTrader
extends RoleInterface {
    public NpcMiscInventory inventoryCurrency = new NpcMiscInventory(18);
    public NpcMiscInventory inventorySold = new NpcMiscInventory(18);

    public RoleTrader(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("TraderCurrency", this.inventoryCurrency.getToNBT());
        nbttagcompound.a("TraderSold", this.inventorySold.getToNBT());
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.inventoryCurrency.setFromNBT(nbttagcompound.l("TraderCurrency"));
        this.inventorySold.setFromNBT(nbttagcompound.l("TraderSold"));
    }

    @Override
    public boolean interact(uf player) {
        this.npc.say(player, this.npc.advanced.getInteractLine());
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTrader, this.npc);
        return false;
    }

    public boolean hasCurrency(ye itemstack) {
        ye item;
        if (itemstack == null) {
            return false;
        }
        Iterator<ye> i$ = this.inventoryCurrency.items.values().iterator();
        do {
            if (i$.hasNext()) continue;
            return false;
        } while ((item = i$.next()) == null || !NoppesUtilPlayer.compareItems(item, itemstack, false));
        return true;
    }
}

