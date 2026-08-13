/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  bu
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.player;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.player.GuiMailmanWrite;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.IGuiClose;
import noppes.client.gui.util.IGuiError;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.controllers.PlayerMail;
import noppes.network.player.MailPackets;

public class GuiMailmanSend
extends GuiNPCInterface
implements ITextfieldListener,
IGuiError,
IGuiClose {
    private GuiNpcLabel error;
    private final PlayerMail mail;
    private String username = "";
    private final by compound = new by();

    public GuiMailmanSend() {
        this.xSize = 256;
        this.setBackground("menubg.png");
        this.mail = new PlayerMail();
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "mailbox.username", this.guiLeft + 4, this.guiTop + 19, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 60, this.guiTop + 14, 180, 20, this.username));
        this.addLabel(new GuiNpcLabel(1, "mailbox.subject", this.guiLeft + 4, this.guiTop + 49, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 60, this.guiTop + 44, 180, 20, this.mail.subject));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 29, this.guiTop + 100, "mailbox.write"));
        this.error = new GuiNpcLabel(2, "", this.guiLeft + 4, this.guiTop + 160, 0xFF0000);
        this.addLabel(this.error);
        this.addButton(new GuiNpcButton(0, this.guiLeft + 26, this.guiTop + 190, 100, 20, "mailbox.send"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 130, this.guiTop + 190, 100, 20, "gui.cancel"));
    }

    @Override
    public void buttonEvent(aut guibutton) {
        if (guibutton.g == 0) {
            this.mail.sender = this.player.bu;
            this.mail.message = this.compound;
            PacketDispatcher.sendPacketToServer((ey)new MailPackets.MailSendPacket(this.username, this.mail.writeNBT()).makePacket("CNPCs Player"));
        }
        if (guibutton.g == 1) {
            this.f.a(null);
        }
        if (guibutton.g == 2) {
            this.f.a((awe)new GuiMailmanWrite(this, this.compound, true));
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void unFocused(GuiNpcTextField textField) {
        if (textField.id == 0) {
            this.username = textField.b();
        }
        if (textField.id == 1) {
            this.mail.subject = textField.b();
        }
    }

    @Override
    public void setError(int i, by data) {
        if (i == 0) {
            this.error.label = bu.a((String)"mailbox.errorUsername");
        }
        if (i == 1) {
            this.error.label = bu.a((String)"mailbox.errorSubject");
        }
    }

    @Override
    public void setClose(int i, by data) {
        this.player.a(bu.a((String)"mailbox.succes", (Object[])new Object[]{data.i("username")}));
    }
}

