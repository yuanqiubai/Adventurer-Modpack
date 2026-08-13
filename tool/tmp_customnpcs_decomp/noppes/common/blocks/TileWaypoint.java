/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  asp
 *  asx
 *  bu
 *  by
 *  uf
 */
package noppes.common.blocks;

import java.util.ArrayList;
import java.util.List;
import noppes.common.constants.EnumQuestType;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.QuestData;
import noppes.common.quests.QuestLocation;

public class TileWaypoint
extends asp {
    public String name = "";
    public int range = 10;
    private int ticks = 10;
    private final List<uf> recentlyChecked = new ArrayList<uf>();
    private List<uf> toCheck;

    public void h() {
        if (!this.k.I) {
            --this.ticks;
            if (this.ticks <= 0) {
                this.ticks = 10;
                this.toCheck = this.getPlayerList(this.range, this.range, this.range);
                this.toCheck.removeAll(this.recentlyChecked);
                List<uf> listMax = this.getPlayerList(this.range + 10, this.range + 10, this.range + 10);
                this.recentlyChecked.retainAll(listMax);
                this.recentlyChecked.addAll(this.toCheck);
                if (!this.toCheck.isEmpty()) {
                    for (uf player : this.toCheck) {
                        PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((uf)player).questData;
                        for (QuestData data : playerdata.activeQuests.values()) {
                            if (data.quest.type != EnumQuestType.Location) continue;
                            QuestLocation quest = (QuestLocation)data.quest.questInterface;
                            quest.setFound(data, this.name);
                            if (playerdata.checkQuestCompletion(player, EnumQuestType.Location)) continue;
                            player.a(this.name + " " + bu.a((String)"quest.found"));
                        }
                    }
                }
            }
        }
    }

    private List<uf> getPlayerList(int x, int y, int z) {
        return this.k.a(uf.class, asx.a((double)this.l, (double)this.m, (double)this.n, (double)(this.l + 1), (double)(this.m + 1), (double)(this.n + 1)).b((double)x, (double)y, (double)z));
    }

    public void a(by compound) {
        super.a(compound);
        this.name = compound.i("LocationName");
        this.range = compound.e("LocationRange");
        if (this.range < 2) {
            this.range = 2;
        }
    }

    public void b(by compound) {
        super.b(compound);
        if (!this.name.isEmpty()) {
            compound.a("LocationName", this.name);
        }
        compound.a("LocationRange", this.range);
    }
}

