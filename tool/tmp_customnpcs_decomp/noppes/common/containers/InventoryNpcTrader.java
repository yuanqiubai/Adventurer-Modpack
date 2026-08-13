/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  uf
 *  ye
 */
package noppes.common.containers;

import noppes.common.containers.ContainerNPCTrader;

public class InventoryNpcTrader
implements mo {
    private final String inventoryTitle;
    private final int slotsCount;
    private final ye[] inventoryContents;
    private final ContainerNPCTrader con;

    public InventoryNpcTrader(String s, int i, ContainerNPCTrader con) {
        this.con = con;
        this.inventoryTitle = s;
        this.slotsCount = i;
        this.inventoryContents = new ye[i];
    }

    public ye a(int i) {
        ye toBuy = this.inventoryContents[i];
        return toBuy == null ? null : ye.b((ye)toBuy);
    }

    public ye a(int i, int j) {
        if (this.inventoryContents[i] != null) {
            ye itemstack = this.inventoryContents[i];
            return ye.b((ye)itemstack);
        }
        return null;
    }

    public void a(int i, ye itemstack) {
        if (itemstack != null) {
            this.inventoryContents[i] = itemstack.m();
        }
        this.e();
    }

    public int j_() {
        return this.slotsCount;
    }

    public String b() {
        return this.inventoryTitle;
    }

    public int d() {
        return 64;
    }

    public void e() {
        this.con.a(this);
    }

    public boolean a(uf entityplayer) {
        return true;
    }

    public void k_() {
    }

    public void g() {
    }

    public ye a_(int i) {
        return null;
    }

    public boolean c() {
        return true;
    }

    public boolean b(int i, ye itemstack) {
        return true;
    }
}

