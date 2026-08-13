/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 */
package noppes.common.controllers;

import java.util.HashMap;
import noppes.common.containers.NpcMiscInventory;
import noppes.util.NBTTags;

public class Bank {
    public int id = -1;
    public String name = "";
    public HashMap<Integer, Integer> slotTypes = new HashMap();
    public int startSlots = 1;
    public int maxSlots = 6;
    public NpcMiscInventory currencyInventory = new NpcMiscInventory(6);
    public NpcMiscInventory upgradeInventory = new NpcMiscInventory(6);

    public Bank() {
        for (int i = 0; i < 6; ++i) {
            this.slotTypes.put(i, 0);
        }
    }

    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("BankID", this.id);
        nbttagcompound.a("BankCurrency", this.currencyInventory.getToNBT());
        nbttagcompound.a("BankUpgrade", this.upgradeInventory.getToNBT());
        nbttagcompound.a("Username", this.name);
        nbttagcompound.a("MaxSlots", this.maxSlots);
        nbttagcompound.a("StartSlots", this.startSlots);
        nbttagcompound.a("BankTypes", (cl)NBTTags.nbtIntegerIntegerMap(this.slotTypes));
    }

    public void readEntityFromNBT(by nbttagcompound) {
        this.id = nbttagcompound.e("BankID");
        this.name = nbttagcompound.i("Username");
        this.startSlots = nbttagcompound.e("StartSlots");
        this.maxSlots = nbttagcompound.e("MaxSlots");
        this.slotTypes = NBTTags.getIntegerIntegerMap(nbttagcompound.m("BankTypes"));
        this.currencyInventory.setFromNBT(nbttagcompound.l("BankCurrency"));
        this.upgradeInventory.setFromNBT(nbttagcompound.l("BankUpgrade"));
    }

    public boolean isUpgraded(int slot) {
        return this.slotTypes.get(slot) != null && this.slotTypes.get(slot) == 2;
    }

    public boolean canBeUpgraded(int slot) {
        return this.upgradeInventory.a(slot) != null && (this.slotTypes.get(slot) == null || this.slotTypes.get(slot) == 0);
    }

    public int getMaxSlots() {
        for (int i = 0; i < this.maxSlots; ++i) {
            if (this.currencyInventory.a(i) != null || i <= this.startSlots - 1) continue;
            return i;
        }
        return this.maxSlots;
    }
}

