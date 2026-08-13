/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 */
package noppes.common.controllers;

import noppes.common.constants.EnumAvailabilityDialog;
import noppes.common.constants.EnumAvailabilityFaction;
import noppes.common.constants.EnumAvailabilityFactionType;
import noppes.common.constants.EnumAvailabilityQuest;
import noppes.common.constants.EnumDayTime;
import noppes.common.controllers.Faction;
import noppes.common.controllers.FactionController;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerFactionData;
import noppes.common.controllers.PlayerQuestController;
import noppes.common.quests.ICompatibilty;
import noppes.common.quests.VersionCompatibility;

public class Availability
implements ICompatibilty {
    public int version = VersionCompatibility.ModRev;
    public EnumAvailabilityDialog dialogAvailable = EnumAvailabilityDialog.Always;
    public EnumAvailabilityQuest questAvailable = EnumAvailabilityQuest.Always;
    public int questId = -1;
    public int dialogId = -1;
    public EnumAvailabilityDialog dialog2Available = EnumAvailabilityDialog.Always;
    public EnumAvailabilityQuest quest2Available = EnumAvailabilityQuest.Always;
    public int quest2Id = -1;
    public int dialog2Id = -1;
    public EnumDayTime daytime = EnumDayTime.Always;
    public int factionId = -1;
    public int faction2Id = -1;
    public EnumAvailabilityFactionType factionAvailable = EnumAvailabilityFactionType.Always;
    public EnumAvailabilityFactionType faction2Available = EnumAvailabilityFactionType.Always;
    public EnumAvailabilityFaction factionStance = EnumAvailabilityFaction.Friendly;
    public EnumAvailabilityFaction faction2Stance = EnumAvailabilityFaction.Friendly;
    public int minPlayerLevel = 0;

    public void readFromNBT(by compound) {
        this.version = compound.e("ModRev");
        VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
        this.setDialogAvailability(compound.e("AvailabilityDialog"));
        this.setQuestAvailability(compound.e("AvailabilityQuest"));
        this.setFactionAvailability(compound.e("AvailabilityFaction"));
        this.setFactionAvailabilityStance(compound.e("AvailabilityFactionStance"));
        this.questId = compound.e("AvailabilityQuestId");
        this.dialogId = compound.e("AvailabilityDialogId");
        this.factionId = compound.e("AvailabilityFactionId");
        this.setDialog2Availability(compound.e("AvailabilityDialog2"));
        this.setQuest2Availability(compound.e("AvailabilityQuest2"));
        this.setFaction2Availability(compound.e("AvailabilityFaction2"));
        this.setFaction2AvailabilityStance(compound.e("AvailabilityFaction2Stance"));
        this.quest2Id = compound.e("AvailabilityQuest2Id");
        this.dialog2Id = compound.e("AvailabilityDialog2Id");
        this.faction2Id = compound.e("AvailabilityFaction2Id");
        this.daytime = EnumDayTime.values()[compound.e("AvailabilityDayTime")];
        this.minPlayerLevel = compound.e("AvailabilityMinPlayerLevel");
    }

    @Override
    public by writeToNBT(by compound) {
        compound.a("ModRev", this.version);
        compound.a("AvailabilityDialog", this.dialogAvailable.ordinal());
        compound.a("AvailabilityQuest", this.questAvailable.ordinal());
        compound.a("AvailabilityFaction", this.factionAvailable.ordinal());
        compound.a("AvailabilityFactionStance", this.factionStance.ordinal());
        compound.a("AvailabilityQuestId", this.questId);
        compound.a("AvailabilityDialogId", this.dialogId);
        compound.a("AvailabilityFactionId", this.factionId);
        compound.a("AvailabilityDialog2", this.dialog2Available.ordinal());
        compound.a("AvailabilityQuest2", this.quest2Available.ordinal());
        compound.a("AvailabilityFaction2", this.faction2Available.ordinal());
        compound.a("AvailabilityFaction2Stance", this.faction2Stance.ordinal());
        compound.a("AvailabilityQuest2Id", this.quest2Id);
        compound.a("AvailabilityDialog2Id", this.dialog2Id);
        compound.a("AvailabilityFaction2Id", this.faction2Id);
        compound.a("AvailabilityDayTime", this.daytime.ordinal());
        compound.a("AvailabilityMinPlayerLevel", this.minPlayerLevel);
        return compound;
    }

    public void setDialogAvailability(int integer) {
        this.dialogAvailable = EnumAvailabilityDialog.values()[integer];
    }

    public void setQuestAvailability(int integer) {
        this.questAvailable = EnumAvailabilityQuest.values()[integer];
    }

    public void setDialog2Availability(int integer) {
        this.dialog2Available = EnumAvailabilityDialog.values()[integer];
    }

    public void setQuest2Availability(int integer) {
        this.quest2Available = EnumAvailabilityQuest.values()[integer];
    }

    public void setFactionAvailability(int value) {
        this.factionAvailable = EnumAvailabilityFactionType.values()[value];
    }

    public void setFaction2Availability(int value) {
        this.faction2Available = EnumAvailabilityFactionType.values()[value];
    }

    public void setFactionAvailabilityStance(int integer) {
        this.factionStance = EnumAvailabilityFaction.values()[integer];
    }

    public void setFaction2AvailabilityStance(int integer) {
        this.faction2Stance = EnumAvailabilityFaction.values()[integer];
    }

    public boolean isAvailable(uf player) {
        long time;
        if (this.daytime == EnumDayTime.Day && (time = player.q.J() % 24000L) > 12000L) {
            return false;
        }
        if (this.daytime == EnumDayTime.Night && (time = player.q.J() % 24000L) < 12000L) {
            return false;
        }
        return this.dialogAvailable(this.dialogId, this.dialogAvailable, player) && this.dialogAvailable(this.dialog2Id, this.dialog2Available, player) && this.questAvailable(this.questId, this.questAvailable, player) && this.questAvailable(this.quest2Id, this.quest2Available, player) && this.factionAvailable(this.factionId, this.factionStance, this.factionAvailable, player) && this.factionAvailable(this.faction2Id, this.faction2Stance, this.faction2Available, player) && player.bH >= this.minPlayerLevel;
    }

    private boolean factionAvailable(int id, EnumAvailabilityFaction stance, EnumAvailabilityFactionType available, uf player) {
        if (available == EnumAvailabilityFactionType.Always) {
            return true;
        }
        Faction faction = FactionController.getInstance().getFaction(id);
        if (faction == null) {
            return true;
        }
        PlayerFactionData data = PlayerDataController.instance.getPlayerData((uf)player).factionData;
        int points = data.getFactionPoints(id);
        EnumAvailabilityFaction current = EnumAvailabilityFaction.Neutral;
        if (faction.neutralPoints >= points) {
            current = EnumAvailabilityFaction.Hostile;
        }
        if (faction.friendlyPoints < points) {
            current = EnumAvailabilityFaction.Friendly;
        }
        return available == EnumAvailabilityFactionType.Is && stance == current || available == EnumAvailabilityFactionType.IsNot && stance != current;
    }

    public boolean dialogAvailable(int id, EnumAvailabilityDialog en, uf player) {
        if (en == EnumAvailabilityDialog.Always) {
            return true;
        }
        boolean hasRead = PlayerDataController.instance.getPlayerData((uf)player).dialogData.dialogsRead.contains(id);
        return hasRead && en == EnumAvailabilityDialog.After || !hasRead && en == EnumAvailabilityDialog.Before;
    }

    public boolean questAvailable(int id, EnumAvailabilityQuest en, uf player) {
        return en == EnumAvailabilityQuest.Always || en == EnumAvailabilityQuest.After && PlayerQuestController.isQuestFinished(player, id) || en == EnumAvailabilityQuest.Before && !PlayerQuestController.isQuestFinished(player, id) || en == EnumAvailabilityQuest.Active && PlayerQuestController.isQuestActive(player, id) || en == EnumAvailabilityQuest.NotActive && !PlayerQuestController.isQuestActive(player, id);
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }
}

