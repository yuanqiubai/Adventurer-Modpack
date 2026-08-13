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
import noppes.common.constants.EnumParticleType;
import noppes.common.constants.EnumPotionType;
import noppes.common.entity.data.DataStats;

public class SubGuiNpcProjectiles
extends SubGuiInterface
implements ITextfieldListener {
    private final DataStats stats;
    private final String[] potionNames = new String[]{"gui.none", "tile.fire.name", "potion.poison", "potion.hunger", "potion.weakness", "potion.moveSlowdown", "potion.confusion", "potion.blindness", "potion.wither"};
    private final String[] trailNames = new String[]{"gui.none", "Smoke", "Portal", "Redstone", "Lightning", "LargeSmoke", "Magic", "Enchant"};

    public SubGuiNpcProjectiles(DataStats stats) {
        this.stats = stats;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(1, "enchantment.arrowDamage", this.guiLeft + 5, this.guiTop + 15, 0x404040));
        this.addTextField(new GuiNpcTextField(1, this, this.o, this.guiLeft + 45, this.guiTop + 10, 50, 18, String.valueOf(this.stats.pDamage)));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(0, 9999, 5);
        this.addLabel(new GuiNpcLabel(2, "enchantment.arrowKnockback", this.guiLeft + 110, this.guiTop + 15, 0x404040));
        this.addTextField(new GuiNpcTextField(2, this, this.o, this.guiLeft + 150, this.guiTop + 10, 50, 18, String.valueOf(this.stats.pImpact)));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(0, 3, 0);
        this.addLabel(new GuiNpcLabel(3, "stats.size", this.guiLeft + 5, this.guiTop + 45, 0x404040));
        this.addTextField(new GuiNpcTextField(3, this, this.o, this.guiLeft + 45, this.guiTop + 40, 50, 18, String.valueOf(this.stats.pSize)));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(1, 10, 10);
        this.addLabel(new GuiNpcLabel(4, "stats.speed", this.guiLeft + 5, this.guiTop + 75, 0x404040));
        this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 45, this.guiTop + 70, 50, 18, String.valueOf(this.stats.pSpeed)));
        this.getTextField((int)4).numbersOnly = true;
        this.getTextField(4).setMinMaxDefault(1, 50, 10);
        this.addLabel(new GuiNpcLabel(5, "stats.hasgravity", this.guiLeft + 5, this.guiTop + 105, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 60, this.guiTop + 100, 60, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pPhysics ? 1 : 0));
        if (!this.stats.pPhysics) {
            this.addButton(new GuiNpcButton(1, this.guiLeft + 140, this.guiTop + 100, 60, 20, new String[]{"gui.constant", "gui.accelerate"}, this.stats.pXlr8 ? 1 : 0));
        }
        this.addLabel(new GuiNpcLabel(6, "stats.explosive", this.guiLeft + 5, this.guiTop + 135, 0x404040));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 60, this.guiTop + 130, 60, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pExplode ? 1 : 0));
        if (this.stats.pExplode) {
            this.addButton(new GuiNpcButton(3, this.guiLeft + 140, this.guiTop + 130, 60, 20, new String[]{"gui.none", "gui.small", "gui.medium", "gui.large"}, this.stats.pArea));
        }
        this.addLabel(new GuiNpcLabel(7, "stats.rangedeffect", this.guiLeft + 5, this.guiTop + 165, 0x404040));
        this.addButton(new GuiNpcButton(4, this.guiLeft + 60, this.guiTop + 160, 60, 20, this.potionNames, this.stats.pEffect.ordinal()));
        if (this.stats.pEffect != EnumPotionType.None) {
            this.addTextField(new GuiNpcTextField(5, this, this.o, this.guiLeft + 140, this.guiTop + 160, 60, 18, String.valueOf(this.stats.pDur)));
            this.getTextField((int)5).numbersOnly = true;
            this.getTextField(5).setMinMaxDefault(1, 99999, 5);
            if (this.stats.pEffect != EnumPotionType.Fire) {
                this.addButton(new GuiNpcButton(10, this.guiLeft + 210, this.guiTop + 160, 40, 20, new String[]{"stats.regular", "stats.amplified"}, this.stats.pEffAmp));
            }
        }
        this.addLabel(new GuiNpcLabel(8, "stats.trail", this.guiLeft + 5, this.guiTop + 195, 0x404040));
        this.addButton(new GuiNpcButton(5, this.guiLeft + 60, this.guiTop + 190, 60, 20, this.trailNames, this.stats.pTrail.ordinal()));
        this.addButton(new GuiNpcButton(7, this.guiLeft + 220, this.guiTop + 10, 30, 20, new String[]{"2D", "3D"}, this.stats.pRender3D ? 1 : 0));
        if (this.stats.pRender3D) {
            this.addLabel(new GuiNpcLabel(10, "stats.spin", this.guiLeft + 160, this.guiTop + 45, 0x404040));
            this.addButton(new GuiNpcButton(8, this.guiLeft + 220, this.guiTop + 40, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pSpin ? 1 : 0));
            this.addLabel(new GuiNpcLabel(11, "stats.stick", this.guiLeft + 160, this.guiTop + 75, 0x404040));
            this.addButton(new GuiNpcButton(9, this.guiLeft + 220, this.guiTop + 70, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pStick ? 1 : 0));
        }
        this.addButton(new GuiNpcButton(6, this.guiLeft + 140, this.guiTop + 190, 60, 20, new String[]{"Glow", "No glow"}, this.stats.pGlows ? 1 : 0));
        this.addButton(new GuiNpcButton(66, this.guiLeft + 210, this.guiTop + 190, 40, 20, "gui.done"));
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 1) {
            this.stats.pDamage = textfield.getInteger();
        } else if (textfield.id == 2) {
            this.stats.pImpact = textfield.getInteger();
        } else if (textfield.id == 3) {
            this.stats.pSize = textfield.getInteger();
        } else if (textfield.id == 4) {
            this.stats.pSpeed = textfield.getInteger();
        } else if (textfield.id == 5) {
            this.stats.pDur = textfield.getInteger();
        }
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g == 0) {
            this.stats.pPhysics = button.getValue() == 1;
            this.A_();
        }
        if (button.g == 1) {
            boolean bl = this.stats.pXlr8 = button.getValue() == 1;
        }
        if (button.g == 2) {
            this.stats.pExplode = button.getValue() == 1;
            this.A_();
        }
        if (button.g == 3) {
            this.stats.pArea = button.getValue();
        }
        if (button.g == 4) {
            this.stats.pEffect = EnumPotionType.values()[button.getValue()];
            this.A_();
        }
        if (button.g == 5) {
            this.stats.pTrail = EnumParticleType.values()[button.getValue()];
        }
        if (button.g == 6) {
            boolean bl = this.stats.pGlows = button.getValue() == 1;
        }
        if (button.g == 7) {
            this.stats.pRender3D = button.getValue() == 1;
            this.A_();
        }
        if (button.g == 8) {
            boolean bl = this.stats.pSpin = button.getValue() == 1;
        }
        if (button.g == 9) {
            boolean bl = this.stats.pStick = button.getValue() == 1;
        }
        if (button.g == 10) {
            this.stats.pEffAmp = button.getValue();
        }
        if (guibutton.g == 66) {
            this.close();
        }
    }
}

