/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 *  mo
 *  uf
 *  ye
 */
package noppes.common.containers;

import java.util.HashMap;
import noppes.util.NBTTags;

public class NpcMiscInventory
implements mo {
    public HashMap<Integer, ye> items = new HashMap();
    public int stackLimit = 64;
    private final int size;

    public NpcMiscInventory(int size) {
        this.size = size;
    }

    public by getToNBT() {
        by nbttagcompound = new by();
        nbttagcompound.a("NpcMiscInv", (cl)NBTTags.nbtItemStackList(this.items));
        return nbttagcompound;
    }

    public void setFromNBT(by nbttagcompound) {
        this.items = NBTTags.getItemStackList(nbttagcompound.m("NpcMiscInv"));
    }

    public int j_() {
        return this.size;
    }

    public ye a(int var1) {
        return this.items.get(var1);
    }

    public ye a(int idx, int amount) {
        ye item = null;
        if (this.items.get(idx) != null) {
            if (this.items.get((Object)Integer.valueOf((int)idx)).b <= amount) {
                item = this.items.get(idx);
                this.items.put(idx, null);
            } else {
                item = this.items.get(idx).a(amount);
                if (this.items.get((Object)Integer.valueOf((int)idx)).b == 0) {
                    this.items.put(idx, null);
                }
            }
        }
        return item;
    }

    public ye a_(int idx) {
        if (this.items.get(idx) != null) {
            ye var3 = this.items.get(idx);
            this.items.put(idx, null);
            return var3;
        }
        return null;
    }

    public void a(int var1, ye var2) {
        this.items.put(var1, var2);
    }

    public String b() {
        return "Npc Misc Inventory";
    }

    public int d() {
        return this.stackLimit;
    }

    public void e() {
    }

    public boolean a(uf var1) {
        return true;
    }

    public void k_() {
    }

    public void g() {
    }

    public boolean c() {
        return true;
    }

    public boolean b(int i, ye itemstack) {
        return true;
    }
}

