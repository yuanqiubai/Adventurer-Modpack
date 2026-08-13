/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  awe
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.CustomNpcs;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.IGuiClose;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.IGuiError;
import noppes.common.constants.EnumGuiType;
import noppes.util.NoppesUtil;

public class GuiPackets {

    public static class GuiClosePacket
    extends RCsPacket {
        private int i;
        private by nbt;

        public GuiClosePacket(int i, by nbt) {
            this.i = i;
            this.nbt = nbt;
        }

        public GuiClosePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.i);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.i = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            awe screen = atv.w().n;
            if (screen == null) {
                return;
            }
            if (screen instanceof IGuiClose) {
                ((IGuiClose)screen).setClose(this.i, this.nbt);
            }
            atv mc = atv.w();
            mc.a(null);
            mc.g();
        }
    }

    public static class GuiErrorPacket
    extends RCsPacket {
        private int i;
        private by nbt;

        public GuiErrorPacket(int i, by nbt) {
            this.i = i;
            this.nbt = nbt;
        }

        public GuiErrorPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.i);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.i = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            awe screen = atv.w().n;
            if (!(screen instanceof IGuiError)) {
                return;
            }
            ((IGuiError)screen).setError(this.i, this.nbt);
        }
    }

    public static class GuiDataPacket
    extends RCsPacket {
        private by nbt;

        public GuiDataPacket(by nbt) {
            this.nbt = nbt;
        }

        public GuiDataPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            awe guiObj = atv.w().n;
            if (guiObj == null) {
                return;
            }
            if (guiObj instanceof GuiNPCInterface2 && ((GuiNPCInterface2)guiObj).hasSubGui()) {
                guiObj = ((GuiNPCInterface2)guiObj).getSubGui();
            }
            if (guiObj instanceof IGuiData) {
                ((IGuiData)guiObj).setGuiData(this.nbt);
            }
        }
    }

    public static class GuiPacket
    extends RCsPacket {
        private int guiId;

        public GuiPacket(int guiId) {
            this.guiId = guiId;
        }

        public GuiPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.guiId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.guiId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EnumGuiType enumGui = EnumGuiType.values()[this.guiId];
            CustomNpcs.proxy.openGui(NoppesUtil.getLastNpc(), enumGui);
        }
    }
}

