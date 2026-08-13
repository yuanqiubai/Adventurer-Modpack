/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  uf
 */
package noppes.common.controllers;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.Vector;
import noppes.common.constants.EnumQuestRepeat;
import noppes.common.constants.EnumQuestType;
import noppes.common.controllers.PlayerData;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestData;
import noppes.common.quests.QuestDialog;
import noppes.network.client.ChatPacket;
import noppes.network.client.MessagePacket;

public class PlayerQuestController {
    public static boolean hasActiveQuests(uf player) {
        PlayerQuestData data = PlayerDataController.instance.getPlayerData((uf)player).questData;
        return !data.activeQuests.isEmpty();
    }

    public static boolean isQuestActive(uf player, int quest) {
        PlayerQuestData data = PlayerDataController.instance.getPlayerData((uf)player).questData;
        return data.activeQuests.containsKey(quest);
    }

    public static boolean isQuestFinished(uf player, int questid) {
        PlayerQuestData data = PlayerDataController.instance.getPlayerData((uf)player).questData;
        return data.finishedQuests.containsKey(questid);
    }

    public static void addActiveQuest(Quest quest, uf player) {
        PlayerQuestData data = PlayerDataController.instance.getPlayerData((uf)player).questData;
        if (PlayerQuestController.canQuestBeAccepted(quest, player)) {
            data.activeQuests.put(quest.id, new QuestData(quest));
            PacketDispatcher.sendPacketToPlayer((ey)new MessagePacket(1, quest.title).makePacket("CNPCs Client"), (Player)((Player)player));
            String[] chatInfo = new String[]{"quest.newquest", ": ", quest.title};
            PacketDispatcher.sendPacketToPlayer((ey)new ChatPacket(chatInfo).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static void setQuestFinished(Quest quest, uf player) {
        PlayerData playerdata = PlayerDataController.instance.getPlayerData(player);
        PlayerQuestData data = playerdata.questData;
        data.activeQuests.remove(quest.id);
        data.finishedQuests.put(quest.id, player.q.J());
        if (quest.repeat != EnumQuestRepeat.None && quest.type == EnumQuestType.Dialog) {
            QuestDialog questdialog = (QuestDialog)quest.questInterface;
            for (Integer integer : questdialog.dialogs.values()) {
                int dialog = integer;
                playerdata.dialogData.dialogsRead.remove(dialog);
            }
        }
    }

    public static boolean canQuestBeAccepted(Quest quest, uf player) {
        if (quest == null) {
            return false;
        }
        PlayerQuestData data = PlayerDataController.instance.getPlayerData((uf)player).questData;
        if (data.activeQuests.containsKey(quest.id)) {
            return false;
        }
        if (data.finishedQuests.containsKey(quest.id) && quest.repeat != EnumQuestRepeat.Repeatable) {
            if (quest.repeat == EnumQuestRepeat.None) {
                return false;
            }
            long questTime = data.finishedQuests.get(quest.id);
            long worldTime = player.q.J();
            if (worldTime < questTime) {
                return true;
            }
            long timePassed = worldTime - questTime;
            return quest.repeat == EnumQuestRepeat.Daily ? timePassed >= 24000L : quest.repeat == EnumQuestRepeat.Weekly && timePassed >= 168000L;
        }
        return true;
    }

    public static Vector<Quest> getActiveQuests(uf player) {
        Vector<Quest> quests = new Vector<Quest>();
        PlayerQuestData data = PlayerDataController.instance.getPlayerData((uf)player).questData;
        for (QuestData questdata : data.activeQuests.values()) {
            if (questdata.quest == null) continue;
            quests.add(questdata.quest);
        }
        return quests;
    }
}

