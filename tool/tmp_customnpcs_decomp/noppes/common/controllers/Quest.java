/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  uf
 */
package noppes.common.controllers;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import noppes.common.constants.EnumQuestCompletion;
import noppes.common.constants.EnumQuestRepeat;
import noppes.common.constants.EnumQuestType;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.controllers.FactionOptions;
import noppes.common.controllers.PlayerMail;
import noppes.common.controllers.QuestCategory;
import noppes.common.controllers.QuestController;
import noppes.common.controllers.QuestData;
import noppes.common.quests.ICompatibilty;
import noppes.common.quests.QuestDialog;
import noppes.common.quests.QuestInterface;
import noppes.common.quests.QuestItem;
import noppes.common.quests.QuestKill;
import noppes.common.quests.QuestLocation;
import noppes.common.quests.VersionCompatibility;
import noppes.network.client.QuestCompletionPacket;

public class Quest
implements ICompatibilty {
    public int version = VersionCompatibility.ModRev;
    public int id = -1;
    public EnumQuestType type = EnumQuestType.Item;
    public EnumQuestRepeat repeat = EnumQuestRepeat.None;
    public EnumQuestCompletion completion = EnumQuestCompletion.Npc;
    public String title = "default";
    public QuestCategory category;
    public String logText = "";
    public String completeText = "";
    public String completerNpc = "";
    public int nextQuestid = -1;
    public String nextQuestTitle = "";
    public PlayerMail mail = new PlayerMail();
    public QuestInterface questInterface = new QuestItem();
    public int rewardExp = 0;
    public NpcMiscInventory rewardItems = new NpcMiscInventory(9);
    public FactionOptions factionOptions = new FactionOptions();

    public void readNBT(by compound) {
        this.version = compound.e("ModRev");
        VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
        this.id = compound.e("Id");
        this.setType(EnumQuestType.values()[compound.e("Type")]);
        this.title = compound.i("Title");
        this.logText = compound.i("Text");
        this.completeText = compound.i("CompleteText");
        this.completerNpc = compound.i("CompleterNpc");
        this.nextQuestid = compound.e("NextQuestId");
        this.nextQuestTitle = compound.i("NextQuestTitle");
        this.nextQuestTitle = this.hasNewQuest() ? this.getNextQuest().title : "";
        this.rewardExp = compound.e("RewardExp");
        this.rewardItems.setFromNBT(compound.l("Rewards"));
        this.completion = EnumQuestCompletion.values()[compound.e("QuestCompletion")];
        this.repeat = EnumQuestRepeat.values()[compound.e("QuestRepeat")];
        this.questInterface.readEntityFromNBT(compound);
        this.factionOptions.readFromNBT(compound.l("QuestFactionPoints"));
        this.mail.readNBT(compound.l("QuestMail"));
    }

    public void setType(EnumQuestType questType) {
        this.type = questType;
        if (this.type == EnumQuestType.Item) {
            this.questInterface = new QuestItem();
        } else if (this.type == EnumQuestType.Dialog) {
            this.questInterface = new QuestDialog();
        } else if (this.type == EnumQuestType.Kill) {
            this.questInterface = new QuestKill();
        } else if (this.type == EnumQuestType.Location) {
            this.questInterface = new QuestLocation();
        }
        if (this.questInterface != null) {
            this.questInterface.questId = this.id;
        }
    }

    @Override
    public by writeToNBT(by compound) {
        compound.a("ModRev", this.version);
        compound.a("Id", this.id);
        compound.a("Type", this.type.ordinal());
        compound.a("Title", this.title);
        compound.a("Text", this.logText);
        compound.a("CompleteText", this.completeText);
        compound.a("CompleterNpc", this.completerNpc);
        compound.a("NextQuestId", this.nextQuestid);
        compound.a("NextQuestTitle", this.nextQuestTitle);
        compound.a("RewardExp", this.rewardExp);
        compound.a("Rewards", this.rewardItems.getToNBT());
        compound.a("QuestCompletion", this.completion.ordinal());
        compound.a("QuestRepeat", this.repeat.ordinal());
        this.questInterface.writeEntityToNBT(compound);
        compound.a("QuestFactionPoints", this.factionOptions.writeToNBT(new by()));
        compound.a("QuestMail", this.mail.writeNBT());
        return compound;
    }

    public boolean hasNewQuest() {
        return this.getNextQuest() != null;
    }

    public Quest getNextQuest() {
        return QuestController.instance == null ? null : QuestController.instance.quests.get(this.nextQuestid);
    }

    public boolean complete(uf player, QuestData data) {
        if (this.completion == EnumQuestCompletion.Instant) {
            by nbt = data.quest.writeToNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new QuestCompletionPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
            return true;
        }
        return false;
    }

    public Quest copy() {
        Quest quest = new Quest();
        quest.readNBT(this.writeToNBT(new by()));
        return quest;
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

