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
import noppes.common.roles.RoleTrader;

public class ContainerNPCTraderSetup
extends uy {
    private final RoleTrader role;

    public ContainerNPCTraderSetup(EntityNPCInterface npc, uf player) {
        int var6;
        int j1;
        this.role = (RoleTrader)npc.roleInterface;
        for (j1 = 0; j1 < 18; ++j1) {
            int l1 = 194;
            var6 = l1 + j1 % 3 * 59;
            int y = 14;
            int var7 = y + j1 / 3 * 22;
            this.a(new we((mo)this.role.inventoryCurrency, j1, var6, var7));
            this.a(new we((mo)this.role.inventorySold, j1, var6 + 26, var7));
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (var6 = 0; var6 < 9; ++var6) {
                this.a(new we((mo)player.bn, var6 + j1 * 9 + 9, 8 + var6 * 18, 103 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 8 + j1 * 18, 161));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }
}

