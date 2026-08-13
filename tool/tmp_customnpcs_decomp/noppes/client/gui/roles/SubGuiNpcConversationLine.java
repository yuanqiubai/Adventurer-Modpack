/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui.roles;

import noppes.client.gui.GuiNpcMusicSelection;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.util.NoppesUtil;

public class SubGuiNpcConversationLine
extends SubGuiInterface
implements ITextfieldListener {
    public String line;
    public String sound;
    private GuiNpcMusicSelection gui;

    public SubGuiNpcConversationLine(String line, String sound) {
        this.line = line;
        this.sound = sound;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "Line", this.guiLeft + 4, this.guiTop + 10, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 4, this.guiTop + 22, 200, 20, this.line));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 4, this.guiTop + 55, 90, 20, "Select Sound"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 96, this.guiTop + 55, 20, 20, "X"));
        this.addLabel(new GuiNpcLabel(1, this.sound, this.guiLeft + 4, this.guiTop + 81, 0x404040));
        this.addButton(new GuiNpcButton(66, this.guiLeft + 162, this.guiTop + 192, 90, 20, "gui.done"));
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        this.line = textfield.b();
    }

    @Override
    public void elementClicked() {
        this.sound = this.gui.getSelected();
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 1) {
            this.gui = new GuiNpcMusicSelection(this.npc, this.parent, this.sound);
            NoppesUtil.openGUI(this.player, (Object)this.gui);
        }
        if (guibutton.g == 2) {
            this.sound = "";
            this.A_();
        }
        if (guibutton.g == 66) {
            this.close();
        }
    }
}

