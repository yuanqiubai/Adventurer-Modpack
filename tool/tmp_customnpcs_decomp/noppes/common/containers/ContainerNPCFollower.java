/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  uf
 *  uy
 *  we
 *  ye
 */
package noppes.common.containers;

import noppes.common.containers.InventoryNPC;
import noppes.common.containers.SlotNpcMercenaryCurrency;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleFollower;

public class ContainerNPCFollower
extends uy {
    public InventoryNPC currencyMatrix;
    public RoleFollower role;

    public ContainerNPCFollower(EntityNPCInterface npc, uf player) {
        this.role = (RoleFollower)npc.roleInterface;
        this.currencyMatrix = new InventoryNPC("currency", 1, this);
        this.a(new SlotNpcMercenaryCurrency(this.role, this.currencyMatrix, 0, 26, 9));
        for (int j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 8 + j1 * 18, 142));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }

    public void b(uf entityplayer) {
        ye itemstack;
        super.b(entityplayer);
        if (!entityplayer.q.I && (itemstack = this.currencyMatrix.a_(0)) != null && !entityplayer.q.I) {
            entityplayer.a(itemstack, 0.0f);
        }
    }
}

