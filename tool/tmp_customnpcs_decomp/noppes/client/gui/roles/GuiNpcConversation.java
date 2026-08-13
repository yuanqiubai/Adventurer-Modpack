/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  ey
 */
package noppes.client.gui.roles;

import cpw.mods.fml.common.network.PacketDispatcher;
import noppes.client.gui.SubGuiNpcAvailability;
import noppes.client.gui.global.GuiNPCQuestSelection;
import noppes.client.gui.roles.SubGuiNpcConversationLine;
import noppes.client.gui.util.GuiNPCInterface2;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobConversation;
import noppes.network.server.EditMainMenuPackets;
import noppes.util.NoppesUtil;

public class GuiNpcConversation
extends GuiNPCInterface2
implements ITextfieldListener,
GuiSelectionListener {
    private final JobConversation job;
    private int slot = -1;
    private GuiNPCQuestSelection questSelection;

    public GuiNpcConversation(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobConversation)npc.jobInterface;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(40, "gui.name", this.guiLeft + 40, this.guiTop + 4, 0x404040));
        this.addLabel(new GuiNpcLabel(41, "gui.name", this.guiLeft + 240, this.guiTop + 4, 0x404040));
        this.addLabel(new GuiNpcLabel(42, "conversation.delay", this.guiLeft + 164, this.guiTop + 4, 0x404040));
        this.addLabel(new GuiNpcLabel(43, "conversation.delay", this.guiLeft + 364, this.guiTop + 4, 0x404040));
        for (int title = 0; title < 14; ++title) {
            JobConversation.ConversationLine line = this.job.getLine(title);
            int offset = title >= 7 ? 200 : 0;
            this.addLabel(new GuiNpcLabel(title, String.valueOf(title), this.guiLeft + 5 + offset - (title > 9 ? 6 : 0), this.guiTop + 18 + title % 7 * 22, 0));
            this.addTextField(new GuiNpcTextField(title, this, this.o, this.guiLeft + 13 + offset, this.guiTop + 13 + title % 7 * 22, 100, 20, line.npc));
            this.addButton(new GuiNpcButton(title, this.guiLeft + 115 + offset, this.guiTop + 13 + title % 7 * 22, 46, 20, "conversation.line"));
            if (title == 13) break;
            this.addTextField(new GuiNpcTextField(title + 14, this, this.o, this.guiLeft + 164 + offset, this.guiTop + 13 + title % 7 * 22, 30, 20, String.valueOf(line.delay)));
            this.getTextField((int)(title + 14)).numbersOnly = true;
            this.getTextField(title + 14).setMinMaxDefault(5, 1000, 40);
        }
        this.addLabel(new GuiNpcLabel(50, "conversation.delay", this.guiLeft + 205, this.guiTop + 175, 0x404040));
        this.addTextField(new GuiNpcTextField(50, this, this.o, this.guiLeft + 270, this.guiTop + 170, 50, 20, String.valueOf(this.job.generalDelay)));
        this.getTextField((int)50).numbersOnly = true;
        this.getTextField(50).setMinMaxDefault(10, 1000000, 12000);
        this.addLabel(new GuiNpcLabel(54, "gui.range", this.guiLeft + 205, this.guiTop + 196, 0x404040));
        this.addTextField(new GuiNpcTextField(54, this, this.o, this.guiLeft + 270, this.guiTop + 191, 50, 20, String.valueOf(this.job.range)));
        this.getTextField((int)54).numbersOnly = true;
        this.getTextField(54).setMinMaxDefault(4, 60, 20);
        this.addLabel(new GuiNpcLabel(51, "quest.quest", this.guiLeft + 13, this.guiTop + 175, 0x404040));
        String var4 = this.job.questTitle;
        if (var4.isEmpty()) {
            var4 = "gui.select";
        }
        this.addButton(new GuiNpcButton(51, this.guiLeft + 70, this.guiTop + 170, 100, 20, var4));
        this.addButton(new GuiNpcButton(52, this.guiLeft + 171, this.guiTop + 170, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(53, "availability.name", this.guiLeft + 13, this.guiTop + 196, 0x404040));
        this.addButton(new GuiNpcButton(53, this.guiLeft + 110, this.guiTop + 191, 60, 20, "selectServer.edit"));
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g >= 0 && button.g < 14) {
            this.slot = button.g;
            JobConversation.ConversationLine line = this.job.getLine(this.slot);
            this.setSubGui(new SubGuiNpcConversationLine(line.text, line.sound));
        }
        if (button.g == 51) {
            this.questSelection = new GuiNPCQuestSelection(this.npc, this, this.job.quest);
            NoppesUtil.openGUI(this.player, this.questSelection);
        }
        if (button.g == 52) {
            this.job.quest = -1;
            this.job.questTitle = "";
            this.A_();
        }
        if (button.g == 53) {
            this.setSubGui(new SubGuiNpcAvailability(this.job.availability));
        }
    }

    @Override
    public void selected(int ob) {
        this.job.quest = ob;
        this.job.questTitle = this.questSelection.getSelected();
        this.A_();
    }

    @Override
    public void closeSubGui(SubGuiInterface gui) {
        super.closeSubGui(gui);
        if (gui instanceof SubGuiNpcConversationLine) {
            SubGuiNpcConversationLine sub = (SubGuiNpcConversationLine)gui;
            JobConversation.ConversationLine line = this.job.getLine(this.slot);
            line.text = sub.line;
            line.sound = sub.sound;
        }
    }

    @Override
    public void save() {
        ey pkt = new EditMainMenuPackets.MainmenuAdvancedSave(this.npc.advanced.writeToNBT(new by())).makePacket("CNPCs Server");
        PacketDispatcher.sendPacketToServer((ey)pkt);
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        JobConversation.ConversationLine line;
        if (textfield.id >= 0 && textfield.id < 14) {
            line = this.job.getLine(textfield.id);
            line.npc = textfield.b();
        }
        if (textfield.id >= 14 && textfield.id < 28) {
            line = this.job.getLine(textfield.id - 14);
            line.delay = textfield.getInteger();
        }
        if (textfield.id == 50) {
            this.job.generalDelay = textfield.getInteger();
        }
        if (textfield.id == 54) {
            this.job.range = textfield.getInteger();
        }
    }
}

