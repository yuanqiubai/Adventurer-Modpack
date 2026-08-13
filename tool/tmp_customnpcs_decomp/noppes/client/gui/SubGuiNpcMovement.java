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
import noppes.common.constants.EnumAnimation;
import noppes.common.constants.EnumMovingType;
import noppes.common.constants.EnumStandingType;
import noppes.common.entity.data.DataAI;

public class SubGuiNpcMovement
extends SubGuiInterface
implements ITextfieldListener {
    private final DataAI ai;

    public SubGuiNpcMovement(DataAI ai) {
        this.ai = ai;
        this.setBackground("menubg.png");
        this.xSize = 256;
        this.ySize = 216;
        this.closeOnEsc = true;
    }

    @Override
    public void A_() {
        super.A_();
        this.addLabel(new GuiNpcLabel(0, "Moving type", this.guiLeft + 4, this.guiTop + 9, 0x404040));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 80, this.guiTop + 4, 100, 20, EnumMovingType.names(), this.ai.movingType.ordinal()));
        if (this.ai.movingType == EnumMovingType.Wandering) {
            this.addLabel(new GuiNpcLabel(4, "Walking range", this.guiLeft + 4, this.guiTop + 31, 0x404040));
            this.addTextField(new GuiNpcTextField(4, this, this.o, this.guiLeft + 100, this.guiTop + 26, 40, 20, String.valueOf(this.ai.walkingRange)));
            this.getTextField((int)4).numbersOnly = true;
            this.getTextField(4).setMinMaxDefault(0, 1000, 5);
        } else if (this.ai.movingType == EnumMovingType.Standing) {
            this.addLabel(new GuiNpcLabel(7, "Position Offset X:", this.guiLeft + 4, this.guiTop + 31, 0x404040));
            this.addTextField(new GuiNpcTextField(7, this, this.o, this.guiLeft + 99, this.guiTop + 26, 24, 20, String.valueOf((int)this.ai.bodyOffsetX)));
            this.getTextField((int)7).numbersOnly = true;
            this.getTextField(7).setMinMaxDefault(0, 10, 5);
            this.addLabel(new GuiNpcLabel(8, "Y:", this.guiLeft + 125, this.guiTop + 31, 0x404040));
            this.addTextField(new GuiNpcTextField(8, this, this.o, this.guiLeft + 135, this.guiTop + 26, 24, 20, String.valueOf((int)this.ai.bodyOffsetY)));
            this.getTextField((int)8).numbersOnly = true;
            this.getTextField(8).setMinMaxDefault(0, 10, 5);
            this.addLabel(new GuiNpcLabel(9, "Z:", this.guiLeft + 161, this.guiTop + 31, 0x404040));
            this.addTextField(new GuiNpcTextField(9, this, this.o, this.guiLeft + 171, this.guiTop + 26, 24, 20, String.valueOf((int)this.ai.bodyOffsetZ)));
            this.getTextField((int)9).numbersOnly = true;
            this.getTextField(9).setMinMaxDefault(0, 10, 5);
            this.addLabel(new GuiNpcLabel(3, "Animation", this.guiLeft + 4, this.guiTop + 53, 0x404040));
            this.addButton(new GuiNpcButton(3, this.guiLeft + 80, this.guiTop + 48, 100, 20, new String[]{"Normal", "Sitting", "Lying", "Sneaking", "Dancing", "Aiming"}, this.ai.animationType.ordinal()));
            if (this.ai.standingType == EnumStandingType.NoRotation || this.ai.standingType == EnumStandingType.HeadRotation) {
                this.addTextField(new GuiNpcTextField(5, this, this.o, this.guiLeft + 165, this.guiTop + 70, 40, 20, String.valueOf(this.ai.orientation)));
                this.getTextField((int)5).numbersOnly = true;
                this.getTextField(5).setMinMaxDefault(0, 359, 0);
                this.addLabel(new GuiNpcLabel(5, "(0-359)", this.guiLeft + 207, this.guiTop + 75, 0x404040));
            }
            if (this.ai.animationType != EnumAnimation.LYING) {
                this.addLabel(new GuiNpcLabel(1, "Rotation", this.guiLeft + 4, this.guiTop + 75, 0x404040));
                this.addButton(new GuiNpcButton(4, this.guiLeft + 80, this.guiTop + 70, 80, 20, new String[]{"Body", "Manual", "Stalking", "Head"}, this.ai.standingType.ordinal()));
            } else {
                this.addLabel(new GuiNpcLabel(6, "Lying Rotation", this.guiLeft + 4, this.guiTop + 75, 0x404040));
                this.addTextField(new GuiNpcTextField(5, this, this.o, this.guiLeft + 99, this.guiTop + 70, 40, 20, String.valueOf(this.ai.orientation)));
                this.getTextField((int)5).numbersOnly = true;
                this.getTextField(5).setMinMaxDefault(0, 359, 0);
                this.addLabel(new GuiNpcLabel(5, "(0-359)", this.guiLeft + 142, this.guiTop + 75, 0x404040));
            }
        }
        if (this.ai.movingType != EnumMovingType.Standing) {
            this.addLabel(new GuiNpcLabel(12, "Animation", this.guiLeft + 4, this.guiTop + 53, 0x404040));
            this.addButton(new GuiNpcButton(12, this.guiLeft + 80, this.guiTop + 48, 100, 20, new String[]{"Normal", "Sneaking", "Aiming", "Dancing"}, this.ai.animationType.getWalkingAnimation()));
        }
        if (this.ai.movingType == EnumMovingType.MovingPath) {
            this.addLabel(new GuiNpcLabel(8, "Movement", this.guiLeft + 4, this.guiTop + 31, 0x404040));
            this.addButton(new GuiNpcButton(8, this.guiLeft + 80, this.guiTop + 26, 80, 20, new String[]{"ai.looping", "ai.backtracking"}, this.ai.movingPattern));
            this.addLabel(new GuiNpcLabel(9, "Pauses", this.guiLeft + 4, this.guiTop + 75, 0x404040));
            this.addButton(new GuiNpcButton(9, this.guiLeft + 80, this.guiTop + 70, 80, 20, new String[]{"gui.no", "gui.yes"}, this.ai.movingPause ? 1 : 0));
        }
        this.addButton(new GuiNpcButton(66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
    }

    @Override
    protected void a(aut guibutton) {
        GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.g == 0) {
            this.ai.movingType = EnumMovingType.values()[button.getValue()];
            if (this.ai.movingType != EnumMovingType.Standing) {
                this.ai.animationType = EnumAnimation.NONE;
                this.ai.standingType = EnumStandingType.RotateBody;
                this.ai.bodyOffsetZ = 5.0f;
                this.ai.bodyOffsetY = 5.0f;
                this.ai.bodyOffsetX = 5.0f;
            }
            this.A_();
        } else if (button.g == 3) {
            this.ai.animationType = EnumAnimation.values()[button.getValue()];
            this.A_();
        } else if (button.g == 4) {
            this.ai.standingType = EnumStandingType.values()[button.getValue()];
            this.A_();
        } else if (button.g == 8) {
            this.ai.movingPattern = button.getValue();
        } else if (button.g == 9) {
            this.ai.movingPause = button.getValue() == 1;
        } else if (button.g == 12) {
            if (button.getValue() == 0) {
                this.ai.animationType = EnumAnimation.NONE;
            }
            if (button.getValue() == 1) {
                this.ai.animationType = EnumAnimation.SNEAKING;
            }
            if (button.getValue() == 2) {
                this.ai.animationType = EnumAnimation.Aiming;
            }
            if (button.getValue() == 3) {
                this.ai.animationType = EnumAnimation.DANCING;
            }
        } else if (guibutton.g == 66) {
            this.close();
        }
    }

    @Override
    public void unFocused(GuiNpcTextField textfield) {
        if (textfield.id == 7) {
            this.ai.bodyOffsetX = textfield.getInteger();
        } else if (textfield.id == 8) {
            this.ai.bodyOffsetY = textfield.getInteger();
        } else if (textfield.id == 9) {
            this.ai.bodyOffsetZ = textfield.getInteger();
        } else if (textfield.id == 5) {
            this.ai.orientation = textfield.getInteger();
        } else if (textfield.id == 4) {
            this.ai.walkingRange = textfield.getInteger();
        } else if (textfield.id == 6) {
            this.ai.distanceToMelee = textfield.getInteger();
        }
    }
}

