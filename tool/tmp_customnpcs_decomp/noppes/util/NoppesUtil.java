/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abm
 *  asp
 *  atv
 *  awe
 *  bdd
 *  beg
 *  by
 *  ci
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ea
 *  ey
 *  uf
 */
package noppes.util;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;
import noppes.CustomNpcs;
import noppes.client.fx.EntityEnderFX;
import noppes.client.gui.player.GuiDialogInteract;
import noppes.client.gui.player.GuiQuestCompletion;
import noppes.client.gui.util.GuiContainerNPCInterface2;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.IScrollData;
import noppes.common.constants.EnumGuiType;
import noppes.common.constants.EnumPacketType;
import noppes.common.controllers.BankController;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogController;
import noppes.common.controllers.Quest;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.model_entity.EntityNpcEnderchibi;
import noppes.network.player.QuestPackets;
import noppes.network.server.EditGuiPacket;

public class NoppesUtil {
    private static EntityNPCInterface lastNpc;

    public static void requestOpenGUI(EnumGuiType gui) {
        NoppesUtil.requestOpenGUI(gui, 0, 0, 0);
    }

    public static void requestOpenGUI(EnumGuiType gui, int i, int j, int k) {
        ey pkt = new EditGuiPacket(gui.ordinal(), i, j, k).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    public static DataOutputStream getDataOutputStream(ByteArrayOutputStream stream) throws IOException {
        return new DataOutputStream(new GZIPOutputStream(stream));
    }

    public static void spawnParticle(DataInputStream dis) {
        try {
            double posX = dis.readDouble();
            double posY = dis.readDouble();
            double posZ = dis.readDouble();
            float height = dis.readFloat();
            float width = dis.readFloat();
            float yOffset = dis.readFloat();
            String particle = dis.readUTF();
            bdd worldObj = atv.w().f;
            Random rand = worldObj.s;
            if (particle.equals("heal")) {
                for (int k = 0; k < 6; ++k) {
                    worldObj.a("instantSpell", posX + (rand.nextDouble() - 0.5) * (double)width, posY + rand.nextDouble() * (double)height - (double)yOffset, posZ + (rand.nextDouble() - 0.5) * (double)width, 0.0, 0.0, 0.0);
                    worldObj.a("spell", posX + (rand.nextDouble() - 0.5) * (double)width, posY + rand.nextDouble() * (double)height - (double)yOffset, posZ + (rand.nextDouble() - 0.5) * (double)width, 0.0, 0.0, 0.0);
                }
            }
        }
        catch (IOException var14) {
            var14.printStackTrace();
        }
    }

    public static EntityNPCInterface getLastNpc() {
        return lastNpc;
    }

    public static void setLastNpc(EntityNPCInterface npc) {
        lastNpc = npc;
    }

    public static void openGUI(uf player, Object guiscreen) {
        CustomNpcs.proxy.openGui(player, guiscreen);
    }

    public static void setScrollList(DataInputStream dis) {
        awe gui = atv.w().n;
        if (gui instanceof IScrollData) {
            Vector<String> data = new Vector<String>();
            try {
                String line;
                while ((line = dis.readUTF()) != null) {
                    data.add(line);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ((IScrollData)gui).setData(data, null);
        }
    }

    public static void setScrollData(DataInputStream dis) {
        block6: {
            awe gui = atv.w().n;
            if (gui != null) {
                Vector<String> list = new Vector<String>();
                HashMap<String, Integer> data = new HashMap<String, Integer>();
                try {
                    while (true) {
                        int e = dis.readInt();
                        String name = dis.readUTF();
                        data.put(name, e);
                        list.add(name);
                    }
                }
                catch (Exception var6) {
                    if (gui instanceof GuiNPCInterface2 && ((GuiNPCInterface2)gui).hasSubGui()) {
                        gui = ((GuiNPCInterface2)gui).getSubGui();
                    }
                    if (gui instanceof GuiContainerNPCInterface2 && ((GuiContainerNPCInterface2)gui).hasSubGui()) {
                        gui = ((GuiContainerNPCInterface2)gui).getSubGui();
                    }
                    if (!(gui instanceof IScrollData)) break block6;
                    ((IScrollData)gui).setData(list, data);
                }
            }
        }
    }

    public static void guiQuestCompletion(uf player, by read) {
        Quest quest = new Quest();
        quest.readNBT(read);
        if (!quest.completeText.equals("")) {
            NoppesUtil.openGUI(player, new GuiQuestCompletion(quest));
        } else {
            PacketDispatcher.sendPacketToServer((ey)new QuestPackets.QuestCompletionPacket(quest.id).makePacket("CNPCs Player"));
        }
    }

    public static void openDialog(DataInputStream dis, EntityNPCInterface npc, uf player) throws IOException {
        if (DialogController.instance == null) {
            DialogController.instance = new DialogController();
        }
        by compound = ci.a((DataInput)dis);
        Dialog dialog = new Dialog();
        dialog.readNBT(compound);
        CustomNpcs.proxy.openGui(player, (Object)new GuiDialogInteract(npc, dialog));
    }

    public static void openDialog(by compound, EntityNPCInterface npc, uf player) throws IOException {
        if (DialogController.instance == null) {
            DialogController.instance = new DialogController();
        }
        Dialog dialog = new Dialog();
        dialog.readNBT(compound);
        CustomNpcs.proxy.openGui(player, (Object)new GuiDialogInteract(npc, dialog));
    }

    public static void sendData(EnumPacketType enu, Object ... obs) {
        try {
            ByteArrayOutputStream e = new ByteArrayOutputStream();
            DataOutputStream out = NoppesUtil.getDataOutputStream(e);
            out.writeInt(enu.ordinal());
            for (Object ob : obs) {
                if (ob == null) continue;
                if (ob instanceof Map) {
                    Map map = (Map)ob;
                    for (Object o : map.keySet()) {
                        String key = (String)o;
                        int value = (Integer)map.get(key);
                        out.writeInt(value);
                        out.writeUTF(key);
                    }
                    continue;
                }
                if (ob instanceof Enum) {
                    out.writeInt(((Enum)ob).ordinal());
                    continue;
                }
                if (ob instanceof Double) {
                    out.writeDouble((Double)ob);
                    continue;
                }
                if (ob instanceof Float) {
                    out.writeFloat(((Float)ob).floatValue());
                    continue;
                }
                if (ob instanceof Integer) {
                    out.writeInt((Integer)ob);
                    continue;
                }
                if (ob instanceof String) {
                    out.writeUTF((String)ob);
                    continue;
                }
                if (ob instanceof by) {
                    ci.a((by)((by)ob), (DataOutput)out);
                    continue;
                }
                if (!(ob instanceof abm)) continue;
                ((abm)ob).a(out);
            }
            out.close();
            PacketDispatcher.sendPacketToServer((ey)new ea("CNPCs Server", e.toByteArray()));
        }
        catch (IOException var12) {
            var12.printStackTrace();
        }
    }

    public static void bankData(DataInputStream dis) throws IOException {
        BankController controller = BankController.getInstance();
        controller.loadBanks(dis);
        awe gui = atv.w().n;
        if (gui != null) {
            gui.A_();
        }
    }

    public static void spawnEnderchibi(EntityNpcEnderchibi chibi) {
        Random rand = chibi.q.s;
        EntityEnderFX fx = new EntityEnderFX(chibi, chibi.u + (rand.nextDouble() - 0.5) * (double)chibi.O, chibi.v + rand.nextDouble() * (double)chibi.P, chibi.w + (rand.nextDouble() - 0.5) * (double)chibi.O, (rand.nextDouble() - 0.5) * 2.0, -rand.nextDouble(), (rand.nextDouble() - 0.5) * 2.0);
        atv.w().k.a((beg)fx);
    }

    public static void saveRedstoneBlock(uf player, DataInputStream dis) throws IOException {
        by compound = ci.a((DataInput)dis);
        int x = compound.e("x");
        int y = compound.e("y");
        int z2 = compound.e("z");
        asp tile = player.q.r(x, y, z2);
        tile.a(compound);
        CustomNpcs.proxy.openGui(x, y, z2, EnumGuiType.RedstoneBlock, player);
    }

    public static void saveWayPointBlock(uf player, DataInputStream dis) throws IOException {
        by nbt = ci.a((DataInput)dis);
        int x = nbt.e("x");
        int y = nbt.e("y");
        int z2 = nbt.e("z");
        asp tile = player.q.r(x, y, z2);
        tile.a(nbt);
        CustomNpcs.proxy.openGui(x, y, z2, EnumGuiType.Waypoint, player);
    }
}

