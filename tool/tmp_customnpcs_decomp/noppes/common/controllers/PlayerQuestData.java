/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  uf
 */
package noppes.common.controllers;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import java.util.Iterator;
import noppes.common.constants.EnumQuestCompletion;
import noppes.common.constants.EnumQuestType;
import noppes.common.controllers.IPlayerData;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestController;
import noppes.common.controllers.QuestData;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.quests.QuestInterface;
import noppes.network.client.ChatPacket;
import noppes.network.client.MessagePacket;

public class PlayerQuestData
implements IPlayerData {
    public HashMap<Integer, QuestData> activeQuests = new HashMap();
    public HashMap<Integer, Long> finishedQuests = new HashMap();

    public void readNBT(by mainCompound) {
        if (mainCompound != null) {
            cg var11;
            by compound = mainCompound.l("QuestData");
            cg list = compound.m("CompletedQuests");
            if (list != null) {
                HashMap<Integer, Long> list2 = new HashMap<Integer, Long>();
                for (int activeQuests = 0; activeQuests < list.c(); ++activeQuests) {
                    by i = (by)list.b(activeQuests);
                    list2.put(i.e("Quest"), i.f("Date"));
                }
                this.finishedQuests = list2;
            }
            if ((var11 = compound.m("ActiveQuests")) != null) {
                HashMap<Integer, QuestData> var12 = new HashMap<Integer, QuestData>();
                for (int var13 = 0; var13 < var11.c(); ++var13) {
                    by nbttagcompound = (by)var11.b(var13);
                    int id = nbttagcompound.e("Quest");
                    Quest quest = QuestController.instance.quests.get(id);
                    if (quest == null) continue;
                    QuestData data = new QuestData(quest);
                    data.readEntityFromNBT(nbttagcompound);
                    var12.put(id, data);
                }
                this.activeQuests = var12;
            }
        }
    }

    @Override
    public by writeNBT(by maincompound) {
        by compound = new by();
        cg list = new cg();
        for (int i$ : this.finishedQuests.keySet()) {
            by quest = new by();
            quest.a("Quest", i$);
            quest.a("Date", this.finishedQuests.get(i$).longValue());
            list.a((cl)quest);
        }
        compound.a("CompletedQuests", (cl)list);
        cg list21 = new cg();
        for (Integer integer : this.activeQuests.keySet()) {
            int quest1 = integer;
            by nbttagcompound = new by();
            nbttagcompound.a("Quest", quest1);
            this.activeQuests.get(quest1).writeEntityToNBT(nbttagcompound);
            list21.a((cl)nbttagcompound);
        }
        compound.a("ActiveQuests", (cl)list21);
        maincompound.a("QuestData", compound);
        return maincompound;
    }

    public QuestData getQuestCompletion(uf player, EntityNPCInterface npc) {
        QuestData data;
        Quest quest;
        Iterator<QuestData> i$ = this.activeQuests.values().iterator();
        do {
            if (!i$.hasNext()) {
                return null;
            }
            data = i$.next();
        } while ((quest = data.quest) == null || quest.completion != EnumQuestCompletion.Npc || !quest.completerNpc.equals(npc.an()) || !quest.questInterface.isCompleted(player));
        return data;
    }

    public boolean checkQuestCompletion(uf player, EnumQuestType type) {
        boolean bo = false;
        for (QuestData data : this.activeQuests.values()) {
            if (data.quest.type != type && type != null) continue;
            QuestInterface inter = data.quest.questInterface;
            if (inter.isCompleted(player)) {
                if (data.isCompleted) continue;
                if (!data.quest.complete(player, data)) {
                    PacketDispatcher.sendPacketToPlayer((ey)new MessagePacket(0, data.quest.title).makePacket("CNPCs Client"), (Player)((Player)player));
                    String[] chatInfo = new String[]{"quest.completed", ": ", data.quest.title};
                    PacketDispatcher.sendPacketToPlayer((ey)new ChatPacket(chatInfo).makePacket("CNPCs Client"), (Player)((Player)player));
                }
                data.isCompleted = true;
                bo = true;
                continue;
            }
            data.isCompleted = false;
        }
        return bo;
    }
}

