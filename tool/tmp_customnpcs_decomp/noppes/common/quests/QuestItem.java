/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 *  ye
 */
package noppes.common.quests;

import java.util.HashMap;
import java.util.Vector;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.quests.QuestInterface;
import noppes.util.NoppesUtilPlayer;

public class QuestItem
extends QuestInterface {
    public NpcMiscInventory items = new NpcMiscInventory(3);

    @Override
    public void readEntityFromNBT(by compound) {
        this.items.setFromNBT(compound.l("Items"));
    }

    @Override
    public void writeEntityToNBT(by compound) {
        compound.a("Items", this.items.getToNBT());
    }

    @Override
    public boolean isCompleted(uf player) {
        HashMap<Integer, ye> map = this.getProcessSet(player);
        for (ye reqItem : this.items.items.values()) {
            boolean done = false;
            for (ye item : map.values()) {
                if (!NoppesUtilPlayer.compareItems(reqItem, item, false) || item.b < reqItem.b) continue;
                done = true;
                break;
            }
            if (done) continue;
            return false;
        }
        return true;
    }

    public HashMap<Integer, ye> getProcessSet(uf player) {
        HashMap<Integer, ye> map = new HashMap<Integer, ye>();
        for (int len$ : this.items.items.keySet()) {
            ye i$ = this.items.items.get(len$);
            if (i$ == null) continue;
            ye item = i$.m();
            item.b = 0;
            map.put(len$, item);
        }
        for (ye item : player.bn.a) {
            if (item == null) continue;
            for (ye questItem : map.values()) {
                if (!NoppesUtilPlayer.compareItems(questItem, item, false)) continue;
                questItem.b += item.b;
            }
        }
        return map;
    }

    @Override
    public void handleComplete(uf player) {
        block0: for (ye questitem : this.items.items.values()) {
            int stacksize = questitem.b;
            for (int i = 0; i < player.bn.a.length; ++i) {
                ye item = player.bn.a[i];
                if (item == null || !NoppesUtilPlayer.compareItems(item, questitem, false)) continue;
                int size = item.b;
                if (stacksize - size >= 0) {
                    player.bn.a(i, null);
                    item.a(size);
                } else {
                    item.a(stacksize);
                }
                if ((stacksize -= size) <= 0) continue block0;
            }
        }
    }

    @Override
    public Vector<String> getQuestLogStatus(uf player) {
        Vector<String> vec = new Vector<String>();
        HashMap<Integer, ye> map = this.getProcessSet(player);
        for (int slot : map.keySet()) {
            ye item = map.get(slot);
            ye quest = this.items.items.get(slot);
            if (item == null) continue;
            String process = String.valueOf(item.b);
            if (item.b > quest.b) {
                process = String.valueOf(quest.b);
            }
            process = process + "/" + quest.b;
            vec.add(item.s() + ": " + process);
        }
        return vec;
    }
}

