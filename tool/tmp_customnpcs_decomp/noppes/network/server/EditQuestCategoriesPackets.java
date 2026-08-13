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
import noppes.common.controllers.QuestCategory;
import noppes.common.controllers.QuestController;
import noppes.network.client.GuiPackets;
import noppes.util.NoppesUtilServer;

public class EditQuestCategoriesPackets {

    public static class QuestCategoryRemovePacket
    extends RCsPacket {
        private int questId;

        public QuestCategoryRemovePacket(int questId) {
            this.questId = questId;
        }

        public QuestCategoryRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.questId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.questId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            QuestController.instance.removeCategory(this.questId);
            NoppesUtilServer.sendQuestCategoryData((jv)player);
        }
    }

    public static class QuestCategoriesGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendQuestCategoryData((jv)player);
        }
    }

    public static class QuestCategorySavePacket
    extends RCsPacket {
        private by nbt;

        public QuestCategorySavePacket(by nbt) {
            this.nbt = nbt;
        }

        public QuestCategorySavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            QuestCategory questCategory = new QuestCategory();
            questCategory.readNBT(this.nbt);
            QuestController.instance.saveCategory(questCategory);
            NoppesUtilServer.sendQuestCategoryData((jv)player);
        }
    }

    public static class QuestCategoryGetPacket
    extends RCsPacket {
        private int questId;

        public QuestCategoryGetPacket(int questId) {
            this.questId = questId;
        }

        public QuestCategoryGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.questId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.questId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            QuestCategory questCategory = QuestController.instance.categories.get(this.questId);
            if (questCategory == null) {
                return;
            }
            by nbt = questCategory.writeNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }
}

