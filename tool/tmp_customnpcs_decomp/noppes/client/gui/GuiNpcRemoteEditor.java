/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  aux
 *  awe
 *  bu
 *  com.adventurer_engine.network.RCsPacket
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 *  nn
 */
package noppes.client.gui;

import com.adventurer_engine.network.RCsPacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Vector;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.IScrollData;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.server.EditRemoteControl;
import noppes.util.NoppesUtil;

public class GuiNpcRemoteEditor
extends GuiNPCInterface
implements IScrollData {
    private GuiCustomScroll scroll;
    private HashMap<String, Integer> data = new HashMap();
    private static final ey REMOTE_NPC_GET = new EditRemoteControl.RemoteNpcsGetPacket().makePacket("CNPCs Server");
    private static final ey REMOTE_FREEZE = new EditRemoteControl.RemoteFreezePacket().makePacket("CNPCs Server");

    public GuiNpcRemoteEditor() {
        this.xSize = 256;
        this.setBackground("menubg.png");
        PacketDispatcher.sendPacketToServer((ey)REMOTE_NPC_GET);
    }

    @Override
    public void A_() {
        super.A_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScroll(this, 0);
            this.scroll.setSize(165, 208);
            this.scroll.guiLeft = this.guiLeft + 4;
            this.scroll.guiTop = this.guiTop + 4;
        }
        this.addScroll(this.scroll, this.f);
        String title = bu.a((String)"remote.title");
        int x = (this.xSize - this.o.a(title)) / 2;
        this.addLabel(new GuiNpcLabel(0, title, this.guiLeft + x, this.guiTop - 8, 0xFFFFFF));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 170, this.guiTop + 6, 82, 20, "selectServer.edit"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 170, this.guiTop + 28, 82, 20, "selectWorld.deleteButton"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 170, this.guiTop + 50, 82, 20, "remote.reset"));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 170, this.guiTop + 72, 82, 20, "remote.tp"));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 170, this.guiTop + 110, 82, 20, "remote.resetall"));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 170, this.guiTop + 132, 82, 20, "remote.freeze"));
    }

    public void a(boolean flag, int i) {
        if (flag) {
            EditRemoteControl.RemoteDeletePacket pkt = new EditRemoteControl.RemoteDeletePacket(this.data.get(this.scroll.getSelected()));
            PacketDispatcher.sendPacketToServer((ey)pkt.makePacket("CNPCs Server"));
        }
        NoppesUtil.openGUI(this.player, this);
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 3) {
            PacketDispatcher.sendPacketToServer((ey)REMOTE_FREEZE);
        }
        if (guibutton.g == 5) {
            for (Integer integer : this.data.values()) {
                int id = integer;
                PacketDispatcher.sendPacketToServer((ey)new EditRemoteControl.RemoteResetPacket(id).makePacket("CNPCs Server"));
                nn entity1 = this.player.q.a(id);
                if (!(entity1 instanceof EntityNPCInterface)) continue;
                ((EntityNPCInterface)entity1).reset();
            }
        }
        if (this.data.containsKey(this.scroll.getSelected())) {
            RCsPacket pkt;
            if (guibutton.g == 0) {
                pkt = new EditRemoteControl.RemoteMainMenuPacket(this.data.get(this.scroll.getSelected()));
                PacketDispatcher.sendPacketToServer((ey)pkt.makePacket("CNPCs Server"));
            }
            if (guibutton.g == 1) {
                aux entity2 = new aux((awe)this, "Confirm", bu.a((String)"gui.delete"), 0);
                this.f.a((awe)entity2);
            }
            if (guibutton.g == 2) {
                pkt = new EditRemoteControl.RemoteResetPacket(this.data.get(this.scroll.getSelected()));
                PacketDispatcher.sendPacketToServer((ey)pkt.makePacket("CNPCs Server"));
                nn entity3 = this.player.q.a(this.data.get(this.scroll.getSelected()).intValue());
                if (entity3 instanceof EntityNPCInterface) {
                    ((EntityNPCInterface)entity3).reset();
                }
            }
            if (guibutton.g == 4) {
                PacketDispatcher.sendPacketToServer((ey)new EditRemoteControl.RemoteTpToNpcPacket(this.data.get(this.scroll.getSelected())).makePacket("CNPCs Server"));
                this.close();
            }
        }
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
    }

    @Override
    public void setData(Vector<String> list, HashMap<String, Integer> data) {
        this.scroll.setList(list);
        this.data = data;
    }

    @Override
    public void setSelected(String selected) {
        this.getButton((int)3).f = selected;
    }
}

