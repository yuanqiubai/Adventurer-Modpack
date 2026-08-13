/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 */
package noppes.common.controllers;

import java.util.HashMap;
import noppes.common.controllers.Quest;

public class QuestCategory {
    public HashMap<Integer, Quest> quests = new HashMap();
    public int id = -1;
    public String title = "";

    public void readNBT(by nbttagcompound) {
        this.id = nbttagcompound.e("Slot");
        this.title = nbttagcompound.i("Title");
        cg dialogsList = nbttagcompound.m("Dialogs");
        if (dialogsList != null) {
            for (int ii = 0; ii < dialogsList.c(); ++ii) {
                by nbttagcompound2 = (by)dialogsList.b(ii);
                Quest quest = new Quest();
                quest.readNBT(nbttagcompound2);
                quest.category = this;
                this.quests.put(quest.id, quest);
            }
        }
    }

    public by writeNBT(by nbttagcompound) {
        nbttagcompound.a("Slot", this.id);
        nbttagcompound.a("Title", this.title);
        cg dialogs = new cg();
        for (int dialogId : this.quests.keySet()) {
            Quest quest = this.quests.get(dialogId);
            dialogs.a((cl)quest.writeToNBT(new by()));
        }
        nbttagcompound.a("Dialogs", (cl)dialogs);
        return nbttagcompound;
    }
}

