/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  mu
 *  uf
 *  uy
 *  we
 *  ye
 */
package noppes.common.containers;

import noppes.common.containers.SlotNpcMercenaryCurrency;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleFollower;

public class ContainerNPCFollowerHire
extends uy {
    public mu currencyMatrix;
    public RoleFollower role;

    public ContainerNPCFollowerHire(EntityNPCInterface npc, uf player) {
        int j1;
        this.role = (RoleFollower)npc.roleInterface;
        this.currencyMatrix = new mu("currency", false, 1);
        this.a(new SlotNpcMercenaryCurrency(this.role, (mo)this.currencyMatrix, 0, 44, 35));
        for (j1 = 0; j1 < 3; ++j1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.a(new we((mo)player.bn, l1 + j1 * 9 + 9, 8 + l1 * 18, 84 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
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

