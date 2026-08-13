/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bu
 *  by
 *  uf
 */
package noppes.common.quests;

import java.util.Vector;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.QuestData;
import noppes.common.quests.QuestInterface;

public class QuestLocation
extends QuestInterface {
    public String location = "";
    public String location2 = "";
    public String location3 = "";

    @Override
    public void readEntityFromNBT(by compound) {
        this.location = compound.i("QuestLocation");
        this.location2 = compound.i("QuestLocation2");
        this.location3 = compound.i("QuestLocation3");
    }

    @Override
    public void writeEntityToNBT(by compound) {
        compound.a("QuestLocation", this.location);
        compound.a("QuestLocation2", this.location2);
        compound.a("QuestLocation3", this.location3);
    }

    @Override
    public boolean isCompleted(uf player) {
        PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((uf)player).questData;
        QuestData data = playerdata.activeQuests.get(this.questId);
        return data != null && this.getFound(data, 0);
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
        String found = bu.a((String)"quest.found");
        String notfound = bu.a((String)"quest.notfound");
        if (!this.location.isEmpty()) {
            vec.add(this.location + ": " + (this.getFound(data, 1) ? found : notfound));
        }
        if (!this.location2.isEmpty()) {
            vec.add(this.location2 + ": " + (this.getFound(data, 2) ? found : notfound));
        }
        if (!this.location3.isEmpty()) {
            vec.add(this.location3 + ": " + (this.getFound(data, 3) ? found : notfound));
        }
        return vec;
    }

    public boolean getFound(QuestData data, int i) {
        return i == 1 ? data.extraData.n("LocationFound") : (i == 2 ? data.extraData.n("Location2Found") : (i == 3 ? data.extraData.n("Location3Found") : !(!this.location.isEmpty() && !data.extraData.n("LocationFound") || !this.location2.isEmpty() && !data.extraData.n("Location2Found") || !this.location3.isEmpty() && !data.extraData.n("Location3Found"))));
    }

    public void setFound(QuestData data, String location) {
        if (location.equalsIgnoreCase(this.location)) {
            data.extraData.a("LocationFound", true);
        }
        if (location.equalsIgnoreCase(this.location2)) {
            data.extraData.a("Location2Found", true);
        }
        if (location.equalsIgnoreCase(this.location3)) {
            data.extraData.a("Location3Found", true);
        }
    }
}

