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
import noppes.common.controllers.Faction;

public class SubGuiNpcFactionPoints
extends SubGuiInterface
implements ITextfieldListener {
    private final Faction faction;

    public SubGuiNpcFactionPoints(Faction faction) {
        this.faction = faction;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 48, this.guiTop + 48, 70, 20, String.valueOf(this.faction.defaultPoints)));
        this.addLabel(new GuiNpcLabel(2, "faction.default", this.guiLeft + 8, this.guiTop + 48 + 5, 0x404040));
        this.getTextField(2).f(6);
        this.getTextField((int)2).numbersOnly = true;
        this.addLabel(new GuiNpcLabel(3, "faction.unfriendly", this.guiLeft + 48, this.guiTop + 72, 0xFF0000));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 48, this.guiTop + 82, 70, 20, String.valueOf(this.faction.neutralPoints)));
        this.addLabel(new GuiNpcLabel(4, "faction.neutral", this.guiLeft + 48, this.guiTop + 104, 0xF2FF00));
        this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 48, this.guiTop + 114, 70, 20, String.valueOf(this.faction.friendlyPoints)));
        this.addLabel(new GuiNpcLabel(5, "faction.friendly", this.guiLeft + 48, this.guiTop + 136, 65280));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField((int)4).numbersOnly = true;
        this.getLabel(3).center(70);
        this.getLabel(4).center(70);
        this.getLabel(5).center(70);
        this.addButton(new GuiNpcButton(66, this.guiLeft + 20, this.guiTop + 192, 90, 20, "gui.done"));
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 2) {
            this.faction.defaultPoints = textfield.getInteger();
        } else if (textfield.id == 3) {
            this.faction.neutralPoints = textfield.getInteger();
        } else if (textfield.id == 4) {
            this.faction.friendlyPoints = textfield.getInteger();
        }
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 66) {
            this.close();
        }
    }
}

