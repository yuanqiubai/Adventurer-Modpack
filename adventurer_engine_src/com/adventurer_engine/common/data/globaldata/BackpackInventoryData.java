/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bkb
 *  by
 *  cg
 *  cl
 *  uf
 */
package com.adventurer_engine.common.data.globaldata;

import com.adventurer_engine.common.data.globaldata.IGlobalData;
import com.adventurer_engine.common.gui_handler.BackpackInventory;
import java.util.HashMap;
import java.util.Map;

public class BackpackInventoryData
extends IGlobalData {
    public Map<String, BackpackInventoryEntry> backpackInventoryMap;
    private static final String BACKPACK_INVENTORY_LIST_NAME = "backpackInventory";

    @Override
    public String saveFile() {
        return "inventory_value.dat";
    }

    @Override
    boolean initialed() {
        return this.backpackInventoryMap != null;
    }

    @Override
    void setDefaultValue() {
        this.backpackInventoryMap = new HashMap<String, BackpackInventoryEntry>();
    }

    @Override
    void readNBTTagCompound(by nbt) {
        cg ls2 = nbt.m(BACKPACK_INVENTORY_LIST_NAME);
        this.backpackInventoryMap = new HashMap<String, BackpackInventoryEntry>();
        for (int i = 0; i < ls2.c(); ++i) {
            by backpackData = (by)ls2.b(i);
            String name = backpackData.i("name");
            by tmp = backpackData.l("data");
            String ownerName = backpackData.i("owner_name");
            this.backpackInventoryMap.put(name, new BackpackInventoryEntry(ownerName, new BackpackInventory(tmp)));
        }
    }

    @Override
    void writeNBTTagCompound(by nbt) {
        cg ls2 = new cg();
        for (String keys : this.backpackInventoryMap.keySet()) {
            by backpackData = new by();
            by tmp = new by();
            BackpackInventoryEntry entry = this.backpackInventoryMap.get(keys);
            entry.backpackData.writeToNBT(tmp);
            backpackData.a("name", keys);
            backpackData.a("owner_name", entry.ownerName);
            backpackData.a("data", tmp);
            ls2.a((cl)backpackData);
        }
        nbt.a(BACKPACK_INVENTORY_LIST_NAME, (cl)ls2);
    }

    public void allocateBackpackInventory(String name, uf player) {
        if (!this.backpackInventoryMap.containsKey(name)) {
            this.backpackInventoryMap.put(name, new BackpackInventoryEntry(player.bu, new BackpackInventory()));
            this.modified = true;
        }
    }

    public BackpackInventory getBackpackInventory(String name, uf player) {
        String username = player.bu;
        BackpackInventoryEntry entry = this.backpackInventoryMap.get(name);
        if (username.equals(entry.ownerName)) {
            this.modified = true;
            return entry.backpackData;
        }
        player.a(String.format(bkb.a((String)"adv.warn.backpack"), entry.ownerName));
        return null;
    }

    public String getBackpackOwnerName(String name) {
        return this.backpackInventoryMap.get((Object)name).ownerName;
    }

    public static class BackpackInventoryEntry {
        public String ownerName;
        public BackpackInventory backpackData;

        public BackpackInventoryEntry(String ownerName, BackpackInventory backpackData) {
            this.ownerName = ownerName;
            this.backpackData = backpackData;
        }
    }
}

