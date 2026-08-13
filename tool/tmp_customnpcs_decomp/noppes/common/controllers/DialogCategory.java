/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 */
package noppes.common.controllers;

import java.util.HashMap;
import noppes.common.controllers.Dialog;

public class DialogCategory {
    public int id = -1;
    public String title = "";
    public HashMap<Integer, Dialog> dialogs = new HashMap();

    public void readNBT(by compound) {
        this.id = compound.e("Slot");
        this.title = compound.i("Title");
        cg dialogsList = compound.m("Dialogs");
        if (dialogsList != null) {
            for (int ii = 0; ii < dialogsList.c(); ++ii) {
                Dialog dialog = new Dialog();
                dialog.category = this;
                dialog.readNBT((by)dialogsList.b(ii));
                this.dialogs.put(dialog.id, dialog);
            }
        }
    }

    public by writeNBT(by nbtfactions) {
        nbtfactions.a("Slot", this.id);
        nbtfactions.a("Title", this.title);
        cg dialogs = new cg();
        for (Dialog dialog : this.dialogs.values()) {
            dialogs.a((cl)dialog.writeToNBT(new by()));
        }
        nbtfactions.a("Dialogs", (cl)dialogs);
        return nbtfactions;
    }
}

