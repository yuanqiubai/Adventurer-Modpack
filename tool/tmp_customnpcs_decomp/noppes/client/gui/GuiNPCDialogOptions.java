/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 *  awe
 */
package noppes.client.gui;

import java.util.HashMap;
import noppes.client.gui.GuiNPCDialogSelection;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.ITextfieldListener;
import noppes.common.constants.EnumOptionType;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogOption;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;

public class GuiNPCDialogOptions
extends GuiNPCInterface
implements ITextfieldListener,
GuiSelectionListener {
    public HashMap<Integer, DialogOption> options = new HashMap();
    private final awe parent;
    private final Dialog dialog;
    private int selectedSlot;

    public GuiNPCDialogOptions(EntityNPCInterface npc, awe parent, Dialog dialog) {
        super(npc);
        this.parent = parent;
        this.dialog = dialog;
        this.options = dialog.options;
        this.title = "";
        this.drawDefaultBackground = false;
    }

    @Override
    public void A_() {
        int i;
        super.A_();
        for (i = 0; i < 6; ++i) {
            if (this.options.containsKey(i)) continue;
            this.options.put(i, new DialogOption());
        }
        for (i = 0; i < 6; ++i) {
            DialogOption option = this.options.get(i);
            int y = 6 + i % 3 * 70;
            int x = (this.g - 420) / 4 + this.g / 2 + 1;
            if (i > 2) {
                x -= this.g / 2;
            }
            this.addButton(new GuiNpcButton(i, x, y, 92, 20, new String[]{"dialog.quitoption", "dialog.option", " gui.disabled", "dialog.roleoption", "dialogcommandoption"}, option.optionType.ordinal()));
            this.addTextField(new GuiNpcTextField(i, this, this.o, x + 2, y + 22, 196, 20, option.title));
            this.addButton(new GuiNpcButton(i + 6, x, y + 44, 196, 20, "dialog.selectoption"));
            String color = Integer.toHexString(option.optionColor);
            while (color.length() < 6) {
                color = 0 + color;
            }
            this.addLabel(new GuiNpcLabel(i, "gui.color", x + 98, y + 4, 0xFFFFFF));
            this.addTextField(new GuiNpcTextField(i + 6, this, this.o, x + 130, y, 45, 20, color));
            this.addTextField(new GuiNpcTextField(i + 12, this, this.o, x + 2, y + 44, 196, 20, option.command));
            this.getTextField((int)(i + 12)).enabled = option.optionType == EnumOptionType.CommandBlock;
            this.getButton((int)(i + 6)).i = option.optionType == EnumOptionType.DialogOption;
            this.getButton((int)(i + 6)).h = this.getButton((int)(i + 6)).i;
            this.getTextField((int)i).enabled = option.optionType != EnumOptionType.Disabled;
            this.getTextField((int)(i + 6)).enabled = option.optionType != EnumOptionType.Disabled;
            this.getLabel((int)i).enabled = option.optionType != EnumOptionType.Disabled;
        }
        this.addButton(new GuiNpcButton(66, this.g / 2 - 49, 215, 98, 20, "gui.back"));
    }

    @Override
    public void a(int i, int j, float f) {
        this.e();
        this.b(this.g / 2, 0, this.h, -1);
        this.a(0, this.g, 74, -1);
        this.a(0, this.g, 144, -1);
        super.a(i, j, f);
    }

    @Override
    protected void a(aut guibutton) {
        DialogOption option;
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (guibutton.g >= 0 && guibutton.g < 6) {
            option = this.options.get(guibutton.g);
            option.optionType = EnumOptionType.values()[button.getValue()];
            this.A_();
        }
        if (guibutton.g >= 6 && guibutton.g < 12) {
            this.save();
            this.selectedSlot = guibutton.g - 6;
            option = this.options.get(this.selectedSlot);
            int id = -1;
            if (option != null) {
                id = option.dialogId;
            }
            NoppesUtil.openGUI(this.player, new GuiNPCDialogSelection(this.npc, this, id));
        }
        if (guibutton.g == 66) {
            this.save();
            NoppesUtil.openGUI(this.player, this.parent);
        }
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        DialogOption option;
        if (textfield.id >= 0 && textfield.id < 6) {
            option = this.options.get(textfield.id);
            if (textfield.isEmpty()) {
                option.optionType = EnumOptionType.Disabled;
                this.A_();
            } else {
                option.title = textfield.b();
            }
        }
        if (textfield.id >= 6 && textfield.id < 12) {
            option = this.options.get(textfield.id - 6);
            int color = 0xE0E0E0;
            try {
                color = Integer.parseInt(textfield.b(), 16);
            }
            catch (NumberFormatException var5) {
                color = 0xE0E0E0;
            }
            option.optionColor = color;
            String colors = Integer.toHexString(option.optionColor);
            while (colors.length() < 6) {
                colors = 0 + colors;
            }
            textfield.a(colors);
        }
        if (textfield.id >= 12 && textfield.id < 18) {
            option = this.options.get(textfield.id - 12);
            option.command = textfield.b();
        }
    }

    @Override
    public void save() {
        this.dialog.options = this.options;
    }

    @Override
    public void selected(int id) {
        if (!this.options.containsKey(this.selectedSlot)) {
            this.options.put(this.selectedSlot, new DialogOption());
        }
        DialogOption option = this.options.get(this.selectedSlot);
        option.dialogId = id;
    }
}

