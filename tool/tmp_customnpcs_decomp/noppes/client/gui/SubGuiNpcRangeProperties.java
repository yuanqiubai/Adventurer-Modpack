/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui;

import noppes.client.gui.GuiNpcSoundSelection;
import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcTextField;
import noppes.client.gui.util.ITextfieldListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.entity.data.DataStats;
import noppes.util.NoppesUtil;

public class SubGuiNpcRangeProperties
extends SubGuiInterface
implements ITextfieldListener {
    private final DataStats stats;
    private GuiNpcSoundSelection gui;

    public SubGuiNpcRangeProperties(DataStats stats) {
        this.stats = stats;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(1, "stats.accuracy", this.guiLeft + 5, this.guiTop + 11, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 85, this.guiTop + 6, 50, 18, String.valueOf(this.stats.accuracy)));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(0, 100, 90);
        this.addLabel(new GuiNpcLabel(2, "stats.rangedrange", this.guiLeft + 5, this.guiTop + 35, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 85, this.guiTop + 30, 50, 18, String.valueOf(this.stats.rangedRange)));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(1, 64, 2);
        this.addLabel(new GuiNpcLabel(3, "stats.firedelay", this.guiLeft + 5, this.guiTop + 59, 0x404040));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 85, this.guiTop + 54, 50, 18, String.valueOf(this.stats.fireDelay)));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(1, 1000, 20);
        this.addLabel(new GuiNpcLabel(4, "stats.delayvariance", this.guiLeft + 5, this.guiTop + 83, 0x404040));
        this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 85, this.guiTop + 78, 50, 18, String.valueOf(this.stats.delayVariance)));
        this.getTextField((int)4).numbersOnly = true;
        this.getTextField(4).setMinMaxDefault(0, 100, 20);
        this.addLabel(new GuiNpcLabel(5, "stats.burstspeed", this.guiLeft + 5, this.guiTop + 107, 0x404040));
        this.addTextField(new GuiNpcTextField(5, this, this.o, this.guiLeft + 85, this.guiTop + 102, 50, 18, String.valueOf(this.stats.fireRate)));
        this.getTextField((int)5).numbersOnly = true;
        this.getTextField(5).setMinMaxDefault(0, 30, 0);
        this.addLabel(new GuiNpcLabel(6, "stats.burstcount", this.guiLeft + 5, this.guiTop + 131, 0x404040));
        this.addTextField(new GuiNpcTextField(6, this, this.o, this.guiLeft + 85, this.guiTop + 126, 50, 18, String.valueOf(this.stats.burstCount)));
        this.getTextField((int)6).numbersOnly = true;
        this.getTextField(6).setMinMaxDefault(1, 100, 20);
        this.addLabel(new GuiNpcLabel(7, "stats.firesound:", this.guiLeft + 5, this.guiTop + 155, 0x404040));
        this.addTextField(new GuiNpcTextField(7, this, this.o, this.guiLeft + 85, this.guiTop + 150, 100, 20, this.stats.fireSound));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 187, this.guiTop + 150, 60, 20, "mco.template.button.select"));
        this.addButton(new GuiNpcButton(66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 1) {
            this.stats.accuracy = textfield.getInteger();
        } else if (textfield.id == 2) {
            this.stats.rangedRange = textfield.getInteger();
        } else if (textfield.id == 3) {
            this.stats.fireDelay = textfield.getInteger();
        } else if (textfield.id == 4) {
            this.stats.delayVariance = textfield.getInteger();
        } else if (textfield.id == 5) {
            this.stats.fireRate = textfield.getInteger();
        } else if (textfield.id == 6) {
            this.stats.burstCount = textfield.getInteger();
        } else if (textfield.id == 7) {
            this.stats.fireSound = textfield.b();
        }
    }

    @Override
    public void elementClicked() {
        this.getTextField(7).a(this.gui.getSelected());
        this.unFocused(this.getTextField(7));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 7) {
            this.gui = new GuiNpcSoundSelection(this.npc, this.parent, this.getTextField(7).b());
            NoppesUtil.openGUI(this.player, (Object)this.gui);
        }
        if (guibutton.g == 66) {
            this.close();
        }
    }
}

