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

import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobItemGiver;

public class ContainerNpcItemGiver
extends uy {
    private final JobItemGiver role;

    public ContainerNpcItemGiver(EntityNPCInterface npc, uf player) {
        int j1;
        this.role = (JobItemGiver)npc.jobInterface;
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)this.role.inventory, j1, 6 + j1 * 18, 81));
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.a(new we((mo)player.bn, l1 + j1 * 9 + 9, 6 + l1 * 18, 107 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 6 + j1 * 18, 165));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }
}

