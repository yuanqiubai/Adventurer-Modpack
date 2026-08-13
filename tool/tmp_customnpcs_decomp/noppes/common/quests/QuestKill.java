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
import java.util.Iterator;
import java.util.Vector;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.QuestData;
import noppes.common.quests.QuestInterface;
import noppes.util.NBTTags;

public class QuestKill
extends QuestInterface {
    public HashMap<String, Integer> targets = new HashMap();

    @Override
    public void readEntityFromNBT(by compound) {
        this.targets = NBTTags.getStringIntegerMap(compound.m("QuestDialogs"));
    }

    @Override
    public void writeEntityToNBT(by compound) {
        compound.a("QuestDialogs", (cl)NBTTags.nbtStringIntegerMap(this.targets));
    }

    @Override
    public boolean isCompleted(uf player) {
        String entity;
        PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((uf)player).questData;
        QuestData data = playerdata.activeQuests.get(this.questId);
        if (data == null) {
            return false;
        }
        HashMap<String, Integer> killed = this.getKilled(data);
        if (killed.size() != this.targets.size()) {
            return false;
        }
        Iterator<String> i$ = killed.keySet().iterator();
        do {
            if (i$.hasNext()) continue;
            return true;
        } while (this.targets.containsKey(entity = i$.next()) && this.targets.get(entity) <= killed.get(entity));
        return false;
    }

    @Override
    public void handleComplete(uf player) {
    }

    @Override
    public Vector<String> getQuestLogStatus(uf player) {
        Vector<String> vec = new Vector<String>();
        PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((uf)player).questData;
        QuestData data = playerdata.activeQuests.get(this.questId);
        if (data == null) {
            return vec;
        }
        HashMap<String, Integer> killed = this.getKilled(data);
        for (String entityName : this.targets.keySet()) {
            int amount = 0;
            if (killed.containsKey(entityName)) {
                amount = killed.get(entityName);
            }
            String state = amount + "/" + this.targets.get(entityName);
            vec.add(entityName + " " + state);
        }
        return vec;
    }

    public HashMap<String, Integer> getKilled(QuestData data) {
        return NBTTags.getStringIntegerMap(data.extraData.m("Killed"));
    }

    public void setKilled(QuestData data, HashMap<String, Integer> killed) {
        data.extraData.a("Killed", (cl)NBTTags.nbtStringIntegerMap(killed));
    }
}

