/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui;

import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;

public class SubGuiNpcCommand
extends SubGuiInterface
implements ITextfieldListener {
    public String command;

    public SubGuiNpcCommand(String command) {
        this.command = command;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 4, this.guiTop + 84, 248, 20, this.command));
        this.getTextField(4).f(Short.MAX_VALUE);
        this.addLabel(new GuiNpcLabel(4, "advMode.command", this.guiLeft + 4, this.guiTop + 110, 0x404040));
        this.addLabel(new GuiNpcLabel(5, "advMode.nearestPlayer", this.guiLeft + 4, this.guiTop + 125, 0x404040));
        this.addLabel(new GuiNpcLabel(6, "advMode.randomPlayer", this.guiLeft + 4, this.guiTop + 140, 0x404040));
        this.addLabel(new GuiNpcLabel(7, "advMode.allPlayers", this.guiLeft + 4, this.guiTop + 155, 0x404040));
        this.addLabel(new GuiNpcLabel(8, "dialogcommandoptionplayer", this.guiLeft + 4, this.guiTop + 170, 0x404040));
        this.addButton(new GuiNpcButton(66, this.guiLeft + 82, this.guiTop + 190, 98, 20, "gui.done"));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 66) {
            this.close();
        }
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 4) {
            this.command = textfield.b();
        }
    }
}

