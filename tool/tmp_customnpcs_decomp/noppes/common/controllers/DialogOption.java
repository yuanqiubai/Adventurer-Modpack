/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 */
package noppes.common.controllers;

import noppes.common.constants.EnumOptionType;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogController;

public class DialogOption {
    public int dialogId = -1;
    public String title = "Talk";
    public EnumOptionType optionType = EnumOptionType.Disabled;
    public int optionColor = 0xE0E0E0;
    public String command = "";

    public void readNBT(by compound) {
        if (compound != null) {
            this.title = compound.i("Title");
            this.dialogId = compound.e("Dialog");
            this.optionColor = compound.e("DialogColor");
            this.optionType = EnumOptionType.values()[compound.e("OptionType")];
            this.command = compound.i("DialogCommand");
            if (this.optionColor == 0) {
                this.optionColor = 0xE0E0E0;
            }
        }
    }

    public by writeNBT() {
        by compound = new by();
        compound.a("Title", this.title);
        compound.a("OptionType", this.optionType.ordinal());
        compound.a("Dialog", this.dialogId);
        compound.a("DialogColor", this.optionColor);
        compound.a("DialogCommand", this.command);
        return compound;
    }

    public boolean hasDialog() {
        if (this.dialogId <= 0) {
            return false;
        }
        if (!DialogController.instance.hasDialog(this.dialogId)) {
            this.dialogId = -1;
            return false;
        }
        return true;
    }

    public Dialog getDialog() {
        return !this.hasDialog() ? null : DialogController.instance.dialogs.get(this.dialogId);
    }

    public boolean isAvailable(uf player) {
        Dialog dialog = this.getDialog();
        return dialog != null && dialog.availability.isAvailable(player);
    }
}

