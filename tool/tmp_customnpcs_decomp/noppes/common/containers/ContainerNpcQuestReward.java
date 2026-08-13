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
import noppes.util.NoppesUtilServer;

public class ContainerNpcQuestReward
extends uy {
    public ContainerNpcQuestReward(uf player) {
        int l1;
        int j1;
        Quest quest = NoppesUtilServer.getEditingQuest(player);
        if (player.q.I) {
            quest = GuiNPCManageQuest.quest;
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (l1 = 0; l1 < 3; ++l1) {
                this.a(new we((mo)quest.rewardItems, l1 + j1 * 3, 105 + l1 * 18, 17 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 3; ++j1) {
            for (l1 = 0; l1 < 9; ++l1) {
                this.a(new we((mo)player.bn, l1 + j1 * 9 + 9, 8 + l1 * 18, 84 + j1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.a(new we((mo)player.bn, j1, 8 + j1 * 18, 142));
        }
    }

    public ye b(uf par1EntityPlayer, int i) {
        return null;
    }

    public boolean a(uf entityplayer) {
        return true;
    }
}

