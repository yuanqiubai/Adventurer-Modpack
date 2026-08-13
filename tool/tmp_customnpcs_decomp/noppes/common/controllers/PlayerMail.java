/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package noppes.common.controllers;

import noppes.common.containers.NpcMiscInventory;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestController;

public class PlayerMail {
    public NpcMiscInventory items = new NpcMiscInventory(9);
    public String subject = "";
    public String sender = "";
    public by message = new by();
    public long time = 0L;
    public boolean beenRead = false;
    public int questId = -1;
    public String questTitle = "";
    public long timePast;

    public void readNBT(by compound) {
        this.items.setFromNBT(compound.l("Items"));
        this.subject = compound.i("Subject");
        this.sender = compound.i("Sender");
        this.time = compound.f("Time");
        this.beenRead = compound.n("BeenRead");
        this.message = compound.l("Message");
        this.timePast = compound.f("TimePast");
        if (compound.b("MailQuest")) {
            this.questId = compound.e("MailQuest");
        }
        this.questTitle = compound.i("MailQuestTitle");
    }

    public by writeNBT() {
        by compound = new by();
        compound.a("Items", this.items.getToNBT());
        compound.a("Subject", this.subject);
        compound.a("Sender", this.sender);
        compound.a("Time", this.time);
        compound.a("BeenRead", this.beenRead);
        compound.a("Message", this.message);
        compound.a("TimePast", System.currentTimeMillis() - this.time);
        compound.a("MailQuest", this.questId);
        if (this.hasQuest()) {
            compound.a("MailQuestTitle", this.getQuest().title);
        }
        return compound;
    }

    public boolean isValid() {
        return !this.subject.isEmpty() && !this.message.d() && !this.sender.isEmpty();
    }

    public boolean hasQuest() {
        return this.getQuest() != null;
    }

    public Quest getQuest() {
        return QuestController.instance != null ? QuestController.instance.quests.get(this.questId) : null;
    }
}

