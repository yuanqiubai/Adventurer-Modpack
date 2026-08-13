/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mo
 *  uf
 *  uy
 *  we
 *  ye
 */
package noppes.common.containers;

import noppes.client.gui.global.GuiNPCManageQuest;
import noppes.common.controllers.Quest;
import noppes.common.quests.QuestItem;
import noppes.util.NoppesUtilServer;

public class ContainerNpcQuestTypeItem
extends uy {
    public ContainerNpcQuestTypeItem(uf player) {
        int j1;
        Quest quest = NoppesUtilServer.getEditingQuest(player);
        if (player.q.I) {
            quest = GuiNPCManageQuest.quest;
        }
        for (j1 = 0; j1 < 3; ++j1) {
            this.a(new we((mo)((QuestItem)quest.questInterface).items, j1, 44, 39 + j1 * 25));
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.a(new we((mo)player.bn, l1 + j1 * 9 + 9, 8 + l1 * 18, 113 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 8 + j1 * 18, 171));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }
}

