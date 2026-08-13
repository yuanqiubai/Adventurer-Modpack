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
import noppes.common.controllers.DialogCategory;
import noppes.common.controllers.DialogController;
import noppes.network.client.GuiPackets;
import noppes.util.NoppesUtilServer;

public class EditDialogCategoriesPackets {

    public static class DialogCategoryGetPacket
    extends RCsPacket {
        private int id;

        public DialogCategoryGetPacket(int id) {
            this.id = id;
        }

        public DialogCategoryGetPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            DialogCategory dialogCategory = DialogController.instance.categories.get(this.id);
            if (dialogCategory != null) {
                by nbt = dialogCategory.writeNBT(new by());
                PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
            }
        }
    }

    public static class DialogCategoryRemovePacket
    extends RCsPacket {
        private int id;

        public DialogCategoryRemovePacket(int id) {
            this.id = id;
        }

        public DialogCategoryRemovePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.id);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.id = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            DialogController.instance.removeCategory(this.id);
            NoppesUtilServer.sendDialogCategoryData((jv)player);
        }
    }

    public static class DialogCategorySavePacket
    extends RCsPacket {
        private by nbt;

        public DialogCategorySavePacket(by nbt) {
            this.nbt = nbt;
        }

        public DialogCategorySavePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            DialogCategory dialog = new DialogCategory();
            dialog.readNBT(this.nbt);
            try {
                DialogController.instance.saveCategory(dialog);
            }
            catch (IOException e) {
                DebugGadgets.debugInfo((String)e.getMessage());
            }
            NoppesUtilServer.sendDialogCategoryData((jv)player);
        }
    }

    public static class DialogCategoriesGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.sendDialogCategoryData((jv)player);
        }
    }
}

