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
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogController;
import noppes.common.controllers.PlayerDataController;
import noppes.common.quests.QuestInterface;
import noppes.util.NBTTags;

public class QuestDialog
extends QuestInterface {
    public HashMap<Integer, Integer> dialogs = new HashMap();

    @Override
    public void readEntityFromNBT(by compound) {
        this.dialogs = NBTTags.getIntegerIntegerMap(compound.m("QuestDialogs"));
    }

    @Override
    public void writeEntityToNBT(by compound) {
        compound.a("QuestDialogs", (cl)NBTTags.nbtIntegerIntegerMap(this.dialogs));
    }

    @Override
    public boolean isCompleted(uf player) {
        int dialogId;
        Iterator<Integer> i$ = this.dialogs.values().iterator();
        do {
            if (i$.hasNext()) continue;
            return true;
        } while (PlayerDataController.instance.getPlayerData((uf)player).dialogData.dialogsRead.contains(dialogId = i$.next().intValue()));
        return false;
    }

    @Override
    public void handleComplete(uf player) {
    }

    @Override
    public Vector<String> getQuestLogStatus(uf player) {
        Vector<String> vec = new Vector<String>();
        for (int dialogId : this.dialogs.values()) {
            Dialog dialog = DialogController.instance.dialogs.get(dialogId);
            if (dialog == null) continue;
            String title = dialog.title;
            title = PlayerDataController.instance.getPlayerData((uf)player).dialogData.dialogsRead.contains(dialogId) ? title + " (read)" : title + " (unread)";
            vec.add(title);
        }
        return vec;
    }
}

