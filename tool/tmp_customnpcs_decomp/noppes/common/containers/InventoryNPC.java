/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  uf
 *  uy
 *  ye
 */
package noppes.common.containers;

public class InventoryNPC
implements mo {
    private final String inventoryTitle;
    private final int slotsCount;
    private final ye[] inventoryContents;
    private final uy con;

    public InventoryNPC(String s, int i, uy con) {
        this.con = con;
        this.inventoryTitle = s;
        this.slotsCount = i;
        this.inventoryContents = new ye[i];
    }

    public ye a(int i) {
        return this.inventoryContents[i];
    }

    public ye a(int i, int j) {
        if (this.inventoryContents[i] != null) {
            if (this.inventoryContents[i].b <= j) {
                ye itemstack1 = this.inventoryContents[i];
                this.inventoryContents[i] = null;
                return itemstack1;
            }
            ye itemstack1 = this.inventoryContents[i].a(j);
            if (this.inventoryContents[i].b == 0) {
                this.inventoryContents[i] = null;
            }
            return itemstack1;
        }
        return null;
    }

    public void a(int i, ye itemstack) {
        this.inventoryContents[i] = itemstack;
        if (itemstack != null && itemstack.b > this.d()) {
            itemstack.b = this.d();
        }
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
        this.con.a((mo)this);
    }

    public boolean a(uf entityplayer) {
        return false;
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

