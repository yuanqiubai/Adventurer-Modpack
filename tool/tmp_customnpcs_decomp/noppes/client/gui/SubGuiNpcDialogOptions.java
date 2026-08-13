/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui;

import noppes.client.gui.SubGuiNpcDialogOption;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.constants.EnumOptionType;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogOption;

public class SubGuiNpcDialogOptions
extends SubGuiInterface {
    private final Dialog dialog;

    public SubGuiNpcDialogOptions(Dialog dialog) {
        this.dialog = dialog;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(66, "dialog.options", this.guiLeft, this.guiTop + 4, 0x404040));
        this.getLabel(66).center(this.xSize);
        for (int i = 0; i < 6; ++i) {
            String optionString = "";
            DialogOption option = this.dialog.options.get(i);
            if (option != null && option.optionType != EnumOptionType.Disabled) {
                optionString = optionString + option.title;
            }
            this.addLabel(new GuiNpcLabel(i + 10, i + 1 + ": ", this.guiLeft + 4, this.guiTop + 16 + i * 32, 0x404040));
            this.addLabel(new GuiNpcLabel(i, optionString, this.guiLeft + 14, this.guiTop + 12 + i * 32, 0x404040));
            this.addButton(new GuiNpcButton(i, this.guiLeft + 13, this.guiTop + 21 + i * 32, 60, 20, "selectServer.edit"));
        }
        this.addButton(new GuiNpcButton(66, this.guiLeft + 82, this.guiTop + 194, 98, 20, "gui.done"));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g < 6) {
            if (!this.dialog.options.containsKey(guibutton.g)) {
                this.dialog.options.put(guibutton.g, new DialogOption());
            }
            this.changeSubGui(new SubGuiNpcDialogOption(this.dialog.options.get(guibutton.g)));
        }
        if (guibutton.g == 66) {
            this.close();
        }
    }
}

