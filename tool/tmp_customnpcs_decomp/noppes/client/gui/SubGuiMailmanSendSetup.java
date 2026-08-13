/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 *  by
 */
package noppes.client.gui;

import noppes.client.gui.global.GuiNPCQuestSelection;
import noppes.client.gui.player.GuiMailmanWrite;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.controllers.PlayerMail;
import noppes.util.NoppesUtil;

public class SubGuiMailmanSendSetup
extends SubGuiInterface
implements ITextfieldListener,
GuiSelectionListener {
    private final PlayerMail mail;
    private GuiNPCQuestSelection questSelection;

    public SubGuiMailmanSendSetup(PlayerMail mail, awe parent) {
        this.parent = parent;
        this.xSize = 256;
        this.setBackground("menubg.png");
        this.mail = mail;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(1, "mailbox.subject", this.guiLeft + 4, this.guiTop + 19, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 60, this.guiTop + 14, 180, 20, this.mail.subject));
        this.addLabel(new GuiNpcLabel(0, "mailbox.sender", this.guiLeft + 4, this.guiTop + 41, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 60, this.guiTop + 36, 180, 20, this.mail.sender));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 29, this.guiTop + 100, "mailbox.write"));
        this.addLabel(new GuiNpcLabel(3, "quest.quest", this.guiLeft + 13, this.guiTop + 135, 0x404040));
        String title = this.mail.questTitle;
        if (title.isEmpty()) {
            title = "gui.select";
        }
        this.addButton(new GuiNpcButton(3, this.guiLeft + 70, this.guiTop + 130, 100, 20, title));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 171, this.guiTop + 130, 20, 20, "X"));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 26, this.guiTop + 190, 100, 20, "gui.done"));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 130, this.guiTop + 190, 100, 20, "gui.cancel"));
    }

    @Override
    public void buttonEvent(aut guibutton) {
        if (guibutton.g == 0) {
            this.close();
        }
        if (guibutton.g == 1) {
            this.mail.questId = -1;
            this.mail.questTitle = "";
            this.mail.message = new by();
            this.close();
        }
        if (guibutton.g == 2) {
            this.f.a((awe)new GuiMailmanWrite(this.parent, this.mail.message, true));
        }
        if (guibutton.g == 3) {
            this.questSelection = new GuiNPCQuestSelection(this.npc, this.parent, this.mail.questId);
            NoppesUtil.openGUI(this.player, this.questSelection);
            this.questSelection.listener = this;
        }
        if (guibutton.g == 4) {
            this.mail.questId = -1;
            this.mail.questTitle = "";
            this.A_();
        }
    }

    @Override
    public void selected(int ob) {
        this.mail.questId = ob;
        this.mail.questTitle = this.questSelection.getSelected();
        this.A_();
    }

    @Override
    public void save() {
    }

    @Override
    public void unFocused(GuiNpcTextField textField) {
        if (textField.id == 0) {
            this.mail.sender = textField.b();
        }
        if (textField.id == 1) {
            this.mail.subject = textField.b();
        }
    }
}

