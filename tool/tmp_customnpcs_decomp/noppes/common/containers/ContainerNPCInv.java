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

import noppes.common.containers.SlotNPCArmor;
import noppes.common.entity.EntityNPCInterface;

public class ContainerNPCInv
extends uy {
    public ContainerNPCInv(EntityNPCInterface npc, uf player) {
        int j1;
        for (j1 = 0; j1 < 4; ++j1) {
            this.a(new SlotNPCArmor(this, npc.inventory, j1, 9, 22 + j1 * 18, j1));
        }
        this.a(new we((mo)npc.inventory, 4, 81, 22));
        this.a(new we((mo)npc.inventory, 5, 81, 40));
        this.a(new we((mo)npc.inventory, 6, 81, 58));
        for (j1 = 0; j1 < 8; ++j1) {
            this.a(new we((mo)npc.inventory, j1 + 7, 191, 17 + j1 * 22));
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.a(new we((mo)player.bn, l1 + j1 * 9 + 9, l1 * 18 + 8, 113 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, j1 * 18 + 8, 171));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }
}

