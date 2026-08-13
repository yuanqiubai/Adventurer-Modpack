/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  awe
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.IScrollData;

public class ScrollPackets {

    public static class ScrollSelectedPacket
    extends RCsPacket {
        private String info;

        public ScrollSelectedPacket(String info) {
            this.info = info;
        }

        public ScrollSelectedPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.info);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.info = in.readUTF();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            awe gui = atv.w().n;
            if (!(gui instanceof IScrollData)) {
                return;
            }
            ((IScrollData)gui).setSelected(this.info);
        }
    }

    public static class ScrollDataPacket
    extends RCsPacket {
        private HashMap<String, Integer> map;

        public ScrollDataPacket(HashMap<String, Integer> map) {
            this.map = map;
        }

        public ScrollDataPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.map.size());
            for (Map.Entry<String, Integer> entry : this.map.entrySet()) {
                out.writeUTF(entry.getKey());
                out.writeInt(entry.getValue().intValue());
            }
        }

        public void read(ByteArrayDataInput in) throws IOException {
            int size = in.readInt();
            this.map = new HashMap(size);
            for (int i = 0; i < size; ++i) {
                this.map.put(in.readUTF(), in.readInt());
            }
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            awe gui = atv.w().n;
            if (gui != null) {
                Vector<String> list = new Vector<String>(this.map.keySet());
                if (gui instanceof GuiNPCInterface2 && ((GuiNPCInterface2)gui).hasSubGui()) {
                    gui = ((GuiNPCInterface2)gui).getSubGui();
                }
                if (gui instanceof GuiContainerNPCInterface2 && ((GuiContainerNPCInterface2)gui).hasSubGui()) {
                    gui = ((GuiContainerNPCInterface2)gui).getSubGui();
                }
                if (gui instanceof IScrollData) {
                    ((IScrollData)gui).setData(list, this.map);
                }
            }
        }
    }

    public static class ScrollListPacket
    extends RCsPacket {
        private Vector<String> vec;

        public ScrollListPacket() {
        }

        public ScrollListPacket(List<String> ls2) {
            this.vec = new Vector<String>(ls2);
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.vec.size());
            for (String s : this.vec) {
                out.writeUTF(s);
            }
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.vec = new Vector();
            int size = in.readInt();
            for (int i = 0; i < size; ++i) {
                this.vec.add(in.readUTF());
            }
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            awe gui = atv.w().n;
            if (gui instanceof IScrollData) {
                ((IScrollData)gui).setData(this.vec, null);
            }
        }
    }
}

