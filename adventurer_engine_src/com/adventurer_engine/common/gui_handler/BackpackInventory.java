/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  mo
 *  uf
 *  ye
 */
package com.adventurer_engine.common.gui_handler;

import com.adventurer_engine.common.items.ItemLoader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BackpackInventory
implements mo {
    private static final int ITEM_AMOUNT = 104;
    public ye[] items = new ye[104];

    public int j_() {
        return 104;
    }

    public BackpackInventory(by nbt) {
        this.readFromNBT(nbt);
    }

    public BackpackInventory() {
    }

    public ye a(int i) {
        return this.items[i];
    }

    public ye a(int i, int j) {
        if (this.items[i] == null) {
            return null;
        }
        ye item = this.items[i];
        if (item.b <= j) {
            this.items[i] = null;
            this.e();
            return item;
        }
        item = item.a(j);
        if (this.items[i].b == 0) {
            this.items[i] = null;
        }
        this.e();
        return item;
    }

    public ye a_(int i) {
        if (this.items[i] == null) {
            return null;
        }
        ye item = this.items[i];
        this.items[i] = null;
        return item;
    }

    public void a(int i, ye itemstack) {
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.b > this.d()) {
            itemstack.b = this.d();
        }
        this.e();
    }

    public String b() {
        return "backpack";
    }

    public boolean c() {
        return false;
    }

    public int d() {
        return 64;
    }

    public void e() {
    }

    public boolean a(uf player) {
        return player.aZ() != null && player.aZ().d == ItemLoader.itemBackpack.cv;
    }

    public void k_() {
    }

    public void g() {
    }

    public void modifyItem(ye item) {
    }

    public boolean b(int i, ye itemstack) {
        return true;
    }

    public void readFromNBT(by nbt) {
        cg tagList = nbt.m("Items");
        for (int i = 0; i < tagList.c(); ++i) {
            by tag = (by)tagList.b(i);
            int slot = tag.e("Slot");
            if (slot < 0 || slot >= this.j_()) continue;
            this.items[slot] = ye.a((by)tag);
        }
    }

    public void writeToNBT(by nbt) {
        cg tagList = new cg();
        for (int i = 0; i < this.j_(); ++i) {
            if (this.items[i] == null) continue;
            by tag = new by();
            tag.a("Slot", i);
            this.items[i].b(tag);
            tagList.a((cl)tag);
        }
        nbt.a("Items", (cl)tagList);
    }

    public void collate() {
        HashMap collatedItems = new HashMap();
        for (ye item : this.items) {
            if (item == null) continue;
            String key = item.d + "" + item.k() + "" + (item.p() ? item.q().hashCode() : 0);
            if (!collatedItems.containsKey(key)) {
                collatedItems.put(key, new LinkedList());
            }
            ((List)collatedItems.get(key)).add(item);
        }
        int idx = 0;
        for (List itemList : collatedItems.values()) {
            int totalAmount = 0;
            ye first = (ye)itemList.get(0);
            for (ye i : itemList) {
                totalAmount += i.b;
            }
            while (totalAmount > 0) {
                int amountToAdd = Math.min(totalAmount, 64);
                ye newItem = first.m();
                newItem.b = amountToAdd;
                totalAmount -= amountToAdd;
                this.items[idx] = newItem;
                ++idx;
            }
        }
        while (idx < this.items.length) {
            this.items[idx] = null;
            ++idx;
        }
        this.heap_sort(this.items);
    }

    private void heap_sort(ye[] arr) {
        int i;
        int n = arr.length;
        for (i = n / 2 - 1; i >= 0; --i) {
            this.heapify(arr, n, i);
        }
        for (i = n - 1; i >= 0; --i) {
            ye temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            this.heapify(arr, i, 0);
        }
    }

    private void heapify(ye[] arr, int n, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && this.compare(arr[left], arr[smallest]) < 0) {
            smallest = left;
        }
        if (right < n && this.compare(arr[right], arr[smallest]) < 0) {
            smallest = right;
        }
        if (smallest != i) {
            ye temp = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = temp;
            this.heapify(arr, n, smallest);
        }
    }

    private short compare(ye a, ye b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        return (short)Integer.compare(b.d, a.d);
    }
}

