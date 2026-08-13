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
import noppes.common.controllers.Faction;
import noppes.common.controllers.FactionController;
import noppes.common.controllers.IPlayerData;

public class PlayerFactionData
implements IPlayerData {
    public HashMap<Integer, Integer> factionData = new HashMap();

    public void readNBT(by compound) {
        cg list;
        HashMap<Integer, Integer> factionData = new HashMap<Integer, Integer>();
        if (compound != null && (list = compound.m("FactionData")) != null && list.c() != 0) {
            for (int i = 0; i < list.c(); ++i) {
                by nbttagcompound = (by)list.b(i);
                factionData.put(nbttagcompound.e("Faction"), nbttagcompound.e("Points"));
            }
            this.factionData = factionData;
        }
    }

    @Override
    public by writeNBT(by compound) {
        cg list = new cg();
        for (int faction : this.factionData.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Faction", faction);
            nbttagcompound.a("Points", this.factionData.get(faction).intValue());
            list.a((cl)nbttagcompound);
        }
        compound.a("FactionData", (cl)list);
        return compound;
    }

    public int getFactionPoints(int id) {
        if (!this.factionData.containsKey(id)) {
            Faction faction = FactionController.getInstance().getFaction(id);
            this.factionData.put(id, faction == null ? -1 : faction.defaultPoints);
        }
        return this.factionData.get(id);
    }

    public void increasePoints(int factionId, int points) {
        if (!this.factionData.containsKey(factionId)) {
            Faction faction = FactionController.getInstance().getFaction(factionId);
            this.factionData.put(factionId, faction == null ? -1 : faction.defaultPoints);
        }
        this.factionData.put(factionId, this.factionData.get(factionId) + points);
    }

    public by getPlayerGuiData() {
        by compound = new by();
        this.writeNBT(compound);
        cg list = new cg();
        for (int id : this.factionData.keySet()) {
            Faction faction = FactionController.getInstance().getFaction(id);
            if (faction == null || faction.hideFaction) continue;
            by com = new by();
            faction.writeNBT(com);
            list.a((cl)com);
        }
        compound.a("FactionList", (cl)list);
        return compound;
    }
}

