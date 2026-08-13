/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  aux
 *  awe
 *  bu
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import noppes.client.gui.player.GuiMailmanWrite;
import noppes.client.gui.util.GuiCustomScroll;
import noppes.client.gui.util.GuiCustomScrollActionListener;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.IGuiData;
import noppes.common.controllers.PlayerMail;
import noppes.common.controllers.PlayerMailData;
import noppes.network.player.MailPackets;
import noppes.util.NoppesUtil;

public class GuiMailbox
extends GuiNPCInterface
implements IGuiData,
GuiCustomScrollActionListener {
    private GuiCustomScroll scroll;
    private PlayerMailData data;
    private PlayerMail selected;

    public GuiMailbox() {
        this.xSize = 256;
        this.setBackground("menubg.png");
        PacketDispatcher.sendPacketToServer((ey)new MailPackets.MailGetPacket().makePacket("CNPCs Player"));
    }

    @Override
    public void A_() {
        super.A_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScroll(this, 0);
            this.scroll.setSize(165, 186);
            this.scroll.guiLeft = this.guiLeft + 4;
            this.scroll.guiTop = this.guiTop + 4;
        }
        this.addScroll(this.scroll, this.f);
        String title = bu.a((String)"mailbox.name");
        int x = (this.xSize - this.o.a(title)) / 2;
        this.addLabel(new GuiNpcLabel(0, title, this.guiLeft + x, this.guiTop - 8, 0xFFFFFF));
        if (this.selected != null) {
            this.addLabel(new GuiNpcLabel(3, bu.a((String)"mailbox.sender") + ":", this.guiLeft + 170, this.guiTop + 6, 0x404040));
            this.addLabel(new GuiNpcLabel(1, this.selected.sender, this.guiLeft + 174, this.guiTop + 18, 0x404040));
            this.addLabel(new GuiNpcLabel(2, bu.a((String)"mailbox.timesend", (Object[])new Object[]{this.getTimePast()}), this.guiLeft + 174, this.guiTop + 30, 0x404040));
        }
        this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 192, 82, 20, "mailbox.read"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 88, this.guiTop + 192, 82, 20, "selectWorld.deleteButton"));
        this.getButton((int)1).h = this.selected != null;
    }

    private String getTimePast() {
        if (this.selected.timePast > 86400000L) {
            int minutes = (int)(this.selected.timePast / 86400000L);
            return minutes == 1 ? minutes + " " + bu.a((String)"mailbox.day") : minutes + " " + bu.a((String)"mailbox.days");
        }
        if (this.selected.timePast > 3600000L) {
            int minutes = (int)(this.selected.timePast / 3600000L);
            return minutes == 1 ? minutes + " " + bu.a((String)"mailbox.hour") : minutes + " " + bu.a((String)"mailbox.hours");
        }
        int minutes = (int)(this.selected.timePast / 60000L);
        return minutes + " " + bu.a((String)"mailbox.minutes");
    }

    public void a(boolean flag, int i) {
        if (flag && this.selected != null) {
            PacketDispatcher.sendPacketToServer((ey)new MailPackets.MailDeletePacket(this.selected.time, this.selected.sender).makePacket("CNPCs Player"));
            this.selected = null;
        }
        NoppesUtil.openGUI(this.player, this);
    }

    @Override
    protected void a(aut guibutton) {
        if (this.scroll.selected >= 0) {
            if (guibutton.g == 0) {
                this.f.a((awe)new GuiMailmanWrite(this, this.selected.message, false));
            }
            if (guibutton.g == 1) {
                aux guiyesno = new aux((awe)this, "Confirm", bu.a((String)"gui.delete"), 0);
                this.f.a((awe)guiyesno);
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
    public void setGuiData(by compound) {
        PlayerMailData data = new PlayerMailData();
        data.readNBT(compound);
        ArrayList<String> list = new ArrayList<String>();
        for (PlayerMail mail : data.playermail) {
            list.add(mail.subject);
        }
        this.data = data;
        this.scroll.clear();
        this.selected = null;
        this.scroll.setUnsortedList(list);
    }

    @Override
    public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
        this.selected = this.data.playermail.get(guiCustomScroll.selected);
        this.A_();
        if (this.selected != null && !this.selected.beenRead) {
            this.selected.beenRead = true;
            PacketDispatcher.sendPacketToServer((ey)new MailPackets.MailReadPacket(this.selected.time, this.selected.sender).makePacket("CNPCs Player"));
        }
    }
}

