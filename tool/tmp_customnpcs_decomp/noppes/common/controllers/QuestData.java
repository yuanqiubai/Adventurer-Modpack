/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package noppes.common.controllers;

import noppes.common.controllers.Quest;

public class QuestData {
    public Quest quest;
    public boolean isCompleted;
    public by extraData = new by();

    public QuestData(Quest quest) {
        this.quest = quest;
    }

    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("QuestCompleted", this.isCompleted);
        nbttagcompound.a("ExtraData", this.extraData);
    }

    public void readEntityFromNBT(by nbttagcompound) {
        this.isCompleted = nbttagcompound.n("QuestCompleted");
        this.extraData = nbttagcompound.l("ExtraData");
    }
}

