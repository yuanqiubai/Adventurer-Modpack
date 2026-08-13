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
import noppes.common.roles.RoleFollower;

public class ContainerNPCFollowerSetup
extends uy {
    private final RoleFollower role;

    public ContainerNPCFollowerSetup(EntityNPCInterface npc, uf player) {
        int j1;
        this.role = (RoleFollower)npc.roleInterface;
        for (j1 = 0; j1 < 3; ++j1) {
            this.a(new we((mo)this.role.inventory, j1, 44, 29 + j1 * 25));
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.a(new we((mo)player.bn, l1 + j1 * 9 + 9, 8 + l1 * 18, 103 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 8 + j1 * 18, 161));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        ye itemstack = null;
        we slot = (we)this.c.get(i);
        if (slot != null && slot.e()) {
            ye itemstack1 = slot.d();
            itemstack = itemstack1.m();
            if (i >= 0 && i < 3 ? !this.a(itemstack1, 3, 38, true) : (i >= 3 && i < 30 ? !this.a(itemstack1, 30, 38, false) : (i >= 30 && i < 38 ? !this.a(itemstack1, 3, 29, false) : !this.a(itemstack1, 3, 38, false)))) {
                return null;
            }
            if (itemstack1.b == 0) {
                slot.c(null);
            } else {
                slot.f();
            }
            if (itemstack1.b == itemstack.b) {
                return null;
            }
            slot.a(par1EntityPlayer, itemstack1);
        }
        return itemstack;
    }

    public boolean a(uf entityplayer) {
        return true;
    }
}

