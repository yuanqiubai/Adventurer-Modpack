/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.IGuiData;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.data.DataAI;
import noppes.network.server.EditMainMenuPackets;

public class GuiNpcPather
extends GuiNPCInterface
implements IGuiData {
    private GuiCustomScroll scroll;
    private final HashMap<String, Integer> data = new HashMap();
    private final DataAI ai;
    private static final ey AI_GET = new EditMainMenuPackets.MainmenuAIGet().makePacket("CNPCs Server");

    public GuiNpcPather(EntityNPCInterface npc) {
        this.drawDefaultBackground = false;
        this.xSize = 176;
        this.title = "Npc Pather";
        this.setBackground("smallbg.png");
        this.ai = npc.ai;
        PacketDispatcher.sendPacketToServer((ey)AI_GET);
    }

    @Override
    public void A_() {
        super.A_();
        this.scroll = new GuiCustomScroll(this, 0);
        this.scroll.setSize(160, 164);
        ArrayList<String> list = new ArrayList<String>();
        for (int[] arr : this.ai.getMovingPath()) {
            list.add("x:" + arr[0] + " y:" + arr[1] + " z:" + arr[2]);
        }
        this.scroll.setUnsortedList(list);
        this.scroll.guiLeft = this.guiLeft + 7;
        this.scroll.guiTop = this.guiTop + 12;
        this.addScroll(this.scroll, this.f);
        this.addButton(new GuiNpcButton(0, this.guiLeft + 6, this.guiTop + 178, 52, 20, "gui.down"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 62, this.guiTop + 178, 52, 20, "gui.up"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 118, this.guiTop + 178, 52, 20, "selectWorld.deleteButton"));
    }

    @Override
    protected void a(aut guibutton) {
        if (this.scroll.selected >= 0) {
            int[] b;
            int[] a;
            int selected;
            List<int[]> list;
            if (guibutton.g == 0) {
                list = this.ai.getMovingPath();
                selected = this.scroll.selected;
                if (list.size() <= selected + 1) {
                    return;
                }
                a = list.get(selected);
                b = list.get(selected + 1);
                list.set(selected, b);
                list.set(selected + 1, a);
                this.ai.setMovingPath(list);
                this.A_();
                this.scroll.selected = selected + 1;
            }
            if (guibutton.g == 1) {
                if (this.scroll.selected - 1 < 0) {
                    return;
                }
                list = this.ai.getMovingPath();
                selected = this.scroll.selected;
                a = list.get(selected);
                b = list.get(selected - 1);
                list.set(selected, b);
                list.set(selected - 1, a);
                this.ai.setMovingPath(list);
                this.A_();
                this.scroll.selected = selected - 1;
            }
            if (guibutton.g == 2) {
                list = this.ai.getMovingPath();
                if (list.size() <= 1) {
                    return;
                }
                list.remove(this.scroll.selected);
                this.ai.setMovingPath(list);
                this.A_();
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    }

    @Override
    public void a(int i, int j, int k) {
        super.a(i, j, k);
        this.scroll.a(i, j, k);
    }

    @Override
    public void a(char c, int i) {
        if (i == 1 || i == this.f.u.N.d) {
            this.close();
        }
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAISave(this.ai.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void setGuiData(by compound) {
        this.ai.readToNBT(compound);
        this.A_();
    }
}

