/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  ms
 *  we
 *  wh
 *  ye
 *  zh
 */
package noppes.common.containers;

import noppes.common.containers.ContainerNPCInv;

class SlotNPCArmor
extends we {
    final int armorType;
    final ContainerNPCInv f;

    SlotNPCArmor(ContainerNPCInv containerplayer, mo iinventory, int i, int j, int k, int l) {
        super(iinventory, i, j, k);
        this.f = containerplayer;
        this.armorType = l;
    }

    public int a() {
        return 1;
    }

    public ms c() {
        return wh.b((int)this.armorType);
    }

    public boolean a(ye itemstack) {
        return itemstack.b() instanceof wh ? ((wh)itemstack.b()).b == this.armorType : itemstack.b() instanceof zh && this.armorType == 0;
    }
}

