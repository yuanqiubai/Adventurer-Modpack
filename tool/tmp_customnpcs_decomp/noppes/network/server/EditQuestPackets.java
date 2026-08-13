/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  jv
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestCategory;
import noppes.common.controllers.QuestController;
import noppes.network.client.GuiPackets;
import noppes.util.NoppesUtilServer;

public class EditQuestPackets {

    public static class QuestRemovePacket
    extends RCsPacket {
        private int questId;

        public QuestRemovePacket(int questId) {
            this.questId = questId;
        }

        public QuestRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.questId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.questId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Quest quest = QuestController.instance.quests.get(this.questId);
            if (quest == null) {
                return;
            }
            QuestController.instance.removeQuest(quest);
            NoppesUtilServer.sendQuestData((jv)player, quest.category);
        }
    }

    public static class QuestSavePacket
    extends RCsPacket {
        private int categoryId;
        private by nbt;

        public QuestSavePacket(int categoryId, by nbt) {
            this.categoryId = categoryId;
            this.nbt = nbt;
        }

        public QuestSavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.categoryId);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.categoryId = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Quest quest = QuestController.instance.saveQuest(this.categoryId, this.nbt);
            if (quest.category == null) {
                return;
            }
        }
    }

    public static class QuestEditPacket
    extends RCsPacket {
        private int questId;

        public QuestEditPacket(int questId) {
            this.questId = questId;
        }

        public QuestEditPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.questId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.questId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Quest quest = QuestController.instance.quests.get(this.questId);
            if (quest == null) {
                return;
            }
            by nbt = quest.writeToNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class QuestsGetFromQuestPacket
    extends RCsPacket {
        private int questId;

        public QuestsGetFromQuestPacket(int questId) {
            this.questId = questId;
        }

        public QuestsGetFromQuestPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.questId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.questId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Quest quest = QuestController.instance.quests.get(this.questId);
            if (quest == null) {
                return;
            }
            NoppesUtilServer.sendQuestData((jv)player, quest.category);
        }
    }

    public static class QuestsGetPacket
    extends RCsPacket {
        private int categoryId;

        public QuestsGetPacket(int categoryId) {
            this.categoryId = categoryId;
        }

        public QuestsGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.categoryId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.categoryId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            QuestCategory questCategory = QuestController.instance.categories.get(this.categoryId);
            NoppesUtilServer.sendQuestData((jv)player, questCategory);
        }
    }

    public static class QuestGetPacket
    extends RCsPacket {
        private int questId;

        public QuestGetPacket(int questId) {
            this.questId = questId;
        }

        public QuestGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.questId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.questId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Quest quest = QuestController.instance.quests.get(this.questId);
            if (quest == null) {
                return;
            }
            by nbt = new by();
            if (quest.hasNewQuest()) {
                nbt.a("NextQuestTitle", quest.getNextQuest().title);
            }
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(quest.writeToNBT(nbt)).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }
}

