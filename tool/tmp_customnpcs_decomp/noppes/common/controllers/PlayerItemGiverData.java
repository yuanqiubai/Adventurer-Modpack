/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 */
package noppes.common.controllers;

import java.util.HashMap;
import noppes.common.controllers.IPlayerData;
import noppes.common.roles.JobItemGiver;
import noppes.util.NBTTags;

public class PlayerItemGiverData
implements IPlayerData {
    private HashMap<Integer, Long> itemgivers = new HashMap();
    private HashMap<Integer, Integer> chained = new HashMap();

    public void readNBT(by compound) {
        this.chained = NBTTags.getIntegerIntegerMap(compound.m("ItemGiverChained"));
        this.itemgivers = NBTTags.getIntegerLongMap(compound.m("ItemGiversList"));
    }

    @Override
    public by writeNBT(by compound) {
        compound.a("ItemGiverChained", (cl)NBTTags.nbtIntegerIntegerMap(this.chained));
        compound.a("ItemGiversList", (cl)NBTTags.nbtIntegerLongMap(this.itemgivers));
        return compound;
    }

    public boolean hasInteractedBefore(JobItemGiver jobItemGiver) {
        return this.itemgivers.containsKey(jobItemGiver.itemGiverId);
    }

    public long getTime(JobItemGiver jobItemGiver) {
        return this.itemgivers.get(jobItemGiver.itemGiverId);
    }

    public void setTime(JobItemGiver jobItemGiver, long day) {
        this.itemgivers.put(jobItemGiver.itemGiverId, day);
    }

    public int getItemIndex(JobItemGiver jobItemGiver) {
        return this.chained.containsKey(jobItemGiver.itemGiverId) ? this.chained.get(jobItemGiver.itemGiverId) : 0;
    }

    public void setItemIndex(JobItemGiver jobItemGiver, int i) {
        this.chained.put(jobItemGiver.itemGiverId, i);
    }
}

