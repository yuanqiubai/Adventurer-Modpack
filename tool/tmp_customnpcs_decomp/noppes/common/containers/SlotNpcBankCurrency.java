/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  we
 *  ye
 */
package noppes.common.containers;

import noppes.common.containers.ContainerNPCBankInterface;

public class SlotNpcBankCurrency
extends we {
    public ye item;

    public SlotNpcBankCurrency(ContainerNPCBankInterface containerplayer, mo iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
    }

    public int a() {
        return 64;
    }

    public boolean a(ye itemstack) {
        return this.item != null && this.item.d == itemstack.d && (!this.item.h() || this.item.k() == itemstack.k());
    }
}

