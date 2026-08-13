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
import noppes.client.gui.GuiNPCDialogSelection;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.GuiSelectionListener;
import noppes.client.gui.util.IGuiData;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.constants.EnumOptionType;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogOption;
import noppes.network.server.EditDialogPackets;
import noppes.util.NoppesUtil;

public class SubGuiNpcDialogOption
extends SubGuiInterface
implements IGuiData,
ITextfieldListener,
GuiSelectionListener {
    private final DialogOption option;

    public SubGuiNpcDialogOption(DialogOption option) {
        this.option = option;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(66, "dialog.editoption", this.guiLeft, this.guiTop + 4, 0x404040));
        this.getLabel(66).center(this.xSize);
        this.addLabel(new GuiNpcLabel(0, "gui.title", this.guiLeft + 4, this.guiTop + 20, 0x404040));
        this.addTextField(new GuiNpcTextField(0, this, this.o, this.guiLeft + 40, this.guiTop + 15, 196, 20, this.option.title));
        String color = Integer.toHexString(this.option.optionColor);
        while (color.length() < 6) {
            color = 0 + color;
        }
        this.addLabel(new GuiNpcLabel(2, "gui.color", this.guiLeft + 4, this.guiTop + 45, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 62, this.guiTop + 40, 92, 20, color));
        this.getTextField(2).g(this.option.optionColor);
        this.addLabel(new GuiNpcLabel(1, "dialog.optiontype", this.guiLeft + 4, this.guiTop + 67, 0x404040));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 62, this.guiTop + 62, 92, 20, new String[]{"gui.close", "dialog.dialog", "gui.disabled", "menu.role", "tile.commandBlock.name"}, this.option.optionType.ordinal()));
        if (this.option.optionType == EnumOptionType.DialogOption) {
            this.addButton(new GuiNpcButton(3, this.guiLeft + 4, this.guiTop + 84, "availability.selectdialog"));
            if (this.option.dialogId >= 0) {
                PacketDispatcher.sendPacketToServer((ey)new EditDialogPackets.DialogGetPacket(this.option.dialogId).makePacket("CNPCs Server"));
            }
        }
        if (this.option.optionType == EnumOptionType.CommandBlock) {
            this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 4, this.guiTop + 84, 248, 20, this.option.command));
            this.getTextField(4).f(Short.MAX_VALUE);
            this.addLabel(new GuiNpcLabel(4, "advMode.command", this.guiLeft + 4, this.guiTop + 110, 0x404040));
            this.addLabel(new GuiNpcLabel(5, "advMode.nearestPlayer", this.guiLeft + 4, this.guiTop + 125, 0x404040));
            this.addLabel(new GuiNpcLabel(6, "advMode.randomPlayer", this.guiLeft + 4, this.guiTop + 140, 0x404040));
            this.addLabel(new GuiNpcLabel(7, "advMode.allPlayers", this.guiLeft + 4, this.guiTop + 155, 0x404040));
            this.addLabel(new GuiNpcLabel(8, "dialogcommandoptionplayer", this.guiLeft + 4, this.guiTop + 170, 0x404040));
        }
        this.addButton(new GuiNpcButton(66, this.guiLeft + 82, this.guiTop + 190, 98, 20, "gui.done"));
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g == 1) {
            this.option.optionType = EnumOptionType.values()[button.getValue()];
            this.A_();
        }
        if (button.g == 3) {
            GuiNPCDialogSelection gui = new GuiNPCDialogSelection(this.npc, this.parent, this.option.dialogId);
            gui.listener = this;
            NoppesUtil.openGUI(this.player, gui);
        }
        if (guibutton.g == 66) {
            this.close();
        }
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 0) {
            if (textfield.isEmpty()) {
                textfield.a(this.option.title);
            } else {
                this.option.title = textfield.b();
            }
        }
        if (textfield.id == 2) {
            int color = 0xE0E0E0;
            try {
                color = Integer.parseInt(textfield.b(), 16);
            }
            catch (NumberFormatException var4) {
                color = 0xE0E0E0;
            }
            this.option.optionColor = color;
            String colors = Integer.toHexString(this.option.optionColor);
            while (colors.length() < 6) {
                colors = 0 + colors;
            }
            textfield.a(colors);
            textfield.g(color);
        }
        if (textfield.id == 4) {
            this.option.command = textfield.b();
        }
    }

    @Override
    public void setGuiData(by compound) {
        if (compound.b("DialogId")) {
            Dialog dialog = new Dialog();
            dialog.readNBT(compound);
            this.option.dialogId = dialog.id;
            if (this.getButton(3) != null) {
                this.getButton((int)3).f = dialog.title;
            }
        }
    }

    @Override
    public void selected(int ob) {
        this.option.dialogId = ob;
    }
}

