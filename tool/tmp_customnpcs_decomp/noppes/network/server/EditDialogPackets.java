/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.DebugGadgets
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
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.CustomNpcs;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogController;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestController;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.client.GuiPackets;
import noppes.util.NoppesUtilServer;

public class EditDialogPackets {

    public static class DialogNpcRemovePacket
    extends RCsPacket {
        private int id;

        public DialogNpcRemovePacket(int id) {
            this.id = id;
        }

        public DialogNpcRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            npc.dialogs.remove(this.id);
        }
    }

    public static class DialogNpcSetPacket
    extends RCsPacket {
        private int slot;
        private int id;

        public DialogNpcSetPacket(int slot, int id) {
            this.slot = slot;
            this.id = id;
        }

        public DialogNpcSetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.slot);
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.slot = in.readInt();
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            try {
                NoppesUtilServer.setNpcDialog(this.slot, this.id, player);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class DialogNpcGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendNpcDialogs(player);
        }
    }

    public static class DialogRemovePacket
    extends RCsPacket {
        private int dialogId;

        public DialogRemovePacket(int dialogId) {
            this.dialogId = dialogId;
        }

        public DialogRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.dialogId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.dialogId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Dialog dialog = DialogController.instance.dialogs.get(this.dialogId);
            if (dialog == null || dialog.category == null) {
                return;
            }
            DialogController.instance.removeDialog(dialog);
            NoppesUtilServer.sendDialogData((jv)player, dialog.category);
        }
    }

    public static class DialogEditPacket
    extends RCsPacket {
        private int dialogId;

        public DialogEditPacket(int dialogId) {
            this.dialogId = dialogId;
        }

        public DialogEditPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.dialogId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.dialogId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Dialog dialog = DialogController.instance.dialogs.get(this.dialogId);
            if (dialog == null) {
                return;
            }
            by nbt = dialog.writeToNBT(new by());
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class QuestOpenGuiPacket
    extends RCsPacket {
        private int guiId;
        private by nbt;

        public QuestOpenGuiPacket(int guiId, by nbt) {
            this.guiId = guiId;
            this.nbt = nbt;
        }

        public QuestOpenGuiPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.guiId);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.guiId = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Quest quest = new Quest();
            quest.readNBT(this.nbt);
            NoppesUtilServer.setEditingQuest(player, quest);
            player.openGui((Object)CustomNpcs.instance, this.guiId, player.q, 0, 0, 0);
        }
    }

    public static class DialogSavePacket
    extends RCsPacket {
        private int dialogId;
        private by nbt;

        public DialogSavePacket(int dialogId, by nbt) {
            this.dialogId = dialogId;
            this.nbt = nbt;
        }

        public DialogSavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.dialogId);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.dialogId = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Dialog dialog = new Dialog();
            dialog.readNBT(this.nbt);
            try {
                DialogController.instance.saveDialog(this.dialogId, dialog);
            }
            catch (IOException e) {
                DebugGadgets.debugInfo((String)e.getMessage());
            }
            if (dialog.category != null) {
                NoppesUtilServer.sendDialogData((jv)player, dialog.category);
            }
        }
    }

    public static class DialogsGetFromDialogPacket
    extends RCsPacket {
        private int dialogId;

        public DialogsGetFromDialogPacket(int dialogId) {
            this.dialogId = dialogId;
        }

        public DialogsGetFromDialogPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.dialogId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.dialogId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Dialog dialog = DialogController.instance.dialogs.get(this.dialogId);
            if (dialog == null) {
                return;
            }
            NoppesUtilServer.sendDialogData((jv)player, dialog.category);
        }
    }

    public static class DialogGetPacket
    extends RCsPacket {
        private int dialogId;

        public DialogGetPacket(int dialogId) {
            this.dialogId = dialogId;
        }

        public DialogGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.dialogId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.dialogId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            Dialog dialog = DialogController.instance.dialogs.get(this.dialogId);
            if (dialog != null) {
                by nbt = dialog.writeToNBT(new by());
                Quest quest = QuestController.instance.quests.get(dialog.quest);
                if (quest != null) {
                    nbt.a("DialogQuestName", quest.title);
                }
                PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
            }
        }
    }

    public static class DialogsGetPacket
    extends RCsPacket {
        private int categoryId;

        public DialogsGetPacket(int categoryId) {
            this.categoryId = categoryId;
        }

        public DialogsGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.categoryId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.categoryId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendDialogData((jv)player, DialogController.instance.categories.get(this.categoryId));
        }
    }
}

