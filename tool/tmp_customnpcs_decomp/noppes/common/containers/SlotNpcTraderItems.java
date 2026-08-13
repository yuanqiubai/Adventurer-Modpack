/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  we
 *  ye
 */
package noppes.common.containers;

class SlotNpcTraderItems
extends we {
    public SlotNpcTraderItems(mo iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
    }

    public void onPickupFromSlot(ye itemstack) {
        if (itemstack != null && this.d() != null && itemstack.d == this.d().d) {
            --itemstack.b;
        }
    }

    public int a() {
        return 64;
    }

    public boolean a(ye itemstack) {
        return false;
    }
}

