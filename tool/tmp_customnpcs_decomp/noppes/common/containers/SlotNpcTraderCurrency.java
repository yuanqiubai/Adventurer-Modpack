/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  we
 *  ye
 */
package noppes.common.containers;

import noppes.common.containers.ContainerNPCTrader;

class SlotNpcTraderCurrency
extends we {
    final ContainerNPCTrader f;

    public SlotNpcTraderCurrency(ContainerNPCTrader containerplayer, mo iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.f = containerplayer;
    }

    public int a() {
        return 64;
    }

    public boolean a(ye itemstack) {
        return this.f.role.hasCurrency(itemstack);
    }
}

