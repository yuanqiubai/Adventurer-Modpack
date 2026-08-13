/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cl
 *  uf
 */
package noppes.common.quests;

import java.util.HashMap;
import java.util.Vector;
import noppes.common.controllers.PlayerQuestController;
import noppes.common.controllers.Quest;
import noppes.util.NBTTags;

public class QuestLogData {
    public HashMap<String, Vector<String>> categories = new HashMap();
    public String selectedQuest = "";
    public String selectedCategory = "";
    public HashMap<String, String> questText = new HashMap();
    public HashMap<String, Vector<String>> questStatus = new HashMap();

    public by writeNBT() {
        by compound = new by();
        compound.a("Categories", (cl)NBTTags.nbtVectorMap(this.categories));
        compound.a("Logs", (cl)NBTTags.nbtStringStringMap(this.questText));
        compound.a("Status", (cl)NBTTags.nbtVectorMap(this.questStatus));
        return compound;
    }

    public void readNBT(by compound) {
        this.categories = NBTTags.getVectorMap(compound.m("Categories"));
        this.questText = NBTTags.getStringStringMap(compound.m("Logs"));
        this.questStatus = NBTTags.getVectorMap(compound.m("Status"));
    }

    public void setData(uf player) {
        for (Quest quest : PlayerQuestController.getActiveQuests(player)) {
            String category = quest.category.title;
            if (!this.categories.containsKey(category)) {
                this.categories.put(category, new Vector());
            }
            Vector<String> list = this.categories.get(category);
            list.add(quest.title);
            this.questText.put(category + ":" + quest.title, quest.logText);
            this.questStatus.put(category + ":" + quest.title, quest.questInterface.getQuestLogStatus(player));
        }
    }

    public boolean hasSelectedQuest() {
        return !this.selectedQuest.isEmpty();
    }

    public String getQuestText() {
        return this.questText.get(this.selectedCategory + ":" + this.selectedQuest);
    }

    public Vector<String> getQuestStatus() {
        return this.questStatus.get(this.selectedCategory + ":" + this.selectedQuest);
    }
}

