/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aut
 */
package noppes.client.gui;

import noppes.client.gui.util.GuiNpcButton;
import noppes.client.gui.util.GuiNpcLabel;
import noppes.client.gui.util.GuiNpcSlider;
import noppes.client.gui.util.ISliderListener;
import noppes.client.gui.util.SubGuiInterface;
import noppes.common.entity.data.Resistances;

public class SubGuiNpcResistanceProperties
extends SubGuiInterface
implements ISliderListener {
    private final Resistances resistances;

    public SubGuiNpcResistanceProperties(Resistances resistances) {
        this.resistances = resistances;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "enchantment.knockback", this.guiLeft + 4, this.guiTop + 15, 0x404040));
        this.addSlider(new GuiNpcSlider(this, 0, this.guiLeft + 94, this.guiTop + 10, null, (int)(this.resistances.knockback * 100.0f - 100.0f) + "%", this.resistances.knockback / 2.0f));
        this.addLabel(new GuiNpcLabel(1, "item.arrow.name", this.guiLeft + 4, this.guiTop + 37, 0x404040));
        this.addSlider(new GuiNpcSlider(this, 1, this.guiLeft + 94, this.guiTop + 32, null, (int)(this.resistances.arrow * 100.0f - 100.0f) + "%", this.resistances.arrow / 2.0f));
        this.addLabel(new GuiNpcLabel(2, "stats.melee", this.guiLeft + 4, this.guiTop + 59, 0x404040));
        this.addSlider(new GuiNpcSlider(this, 2, this.guiLeft + 94, this.guiTop + 54, null, (int)(this.resistances.playermelee * 100.0f - 100.0f) + "%", this.resistances.playermelee / 2.0f));
        this.addButton(new GuiNpcButton(66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
    }

    @Override
    protected void a(aut guibutton) {
        if (guibutton.g == 66) {
            this.close();
        }
    }

    @Override
    public void mouseDragged(GuiNpcSlider slider) {
        slider.f = (int)(slider.o * 200.0f - 100.0f) + "%";
    }

    @Override
    public void mousePressed(GuiNpcSlider slider) {
    }

    @Override
    public void mouseReleased(GuiNpcSlider slider) {
        if (slider.g == 0) {
            this.resistances.knockback = slider.o * 2.0f;
        }
        if (slider.g == 1) {
            this.resistances.arrow = slider.o * 2.0f;
        }
        if (slider.g == 2) {
            this.resistances.playermelee = slider.o * 2.0f;
        }
    }
}

