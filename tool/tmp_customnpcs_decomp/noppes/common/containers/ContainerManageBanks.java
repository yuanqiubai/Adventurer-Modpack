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

import noppes.common.controllers.Bank;

public class ContainerManageBanks
extends uy {
    public Bank bank = new Bank();

    public ContainerManageBanks(uf player) {
        int var6;
        int y;
        int j1;
        for (j1 = 0; j1 < 6; ++j1) {
            int x = 36;
            y = 21;
            var6 = y + j1 * 22;
            this.a(new we((mo)this.bank.currencyInventory, j1, x, var6));
        }
        for (j1 = 0; j1 < 6; ++j1) {
            int var5 = 142;
            y = 21;
            var6 = y + j1 * 22;
            this.a(new we((mo)this.bank.upgradeInventory, j1, var5, var6));
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 8 + j1 * 18, 154));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        ye itemstack = null;
        we slot = (we)this.c.get(i);
        if (slot != null && slot.e()) {
            ye itemstack1 = slot.d();
            itemstack = itemstack1.m();
            if (i >= 0 && i < 7 ? !this.a(itemstack1, 7, 43, true) : (i >= 20 && i < 47 ? !this.a(itemstack1, 36, 43, false) : (i >= 47 && i < 56 ? !this.a(itemstack1, 7, 34, false) : !this.a(itemstack1, 7, 43, false)))) {
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

    public void setBank(Bank bank2) {
        for (int i = 0; i < 6; ++i) {
            this.bank.currencyInventory.a(i, bank2.currencyInventory.a(i));
            this.bank.upgradeInventory.a(i, bank2.upgradeInventory.a(i));
        }
    }
}

