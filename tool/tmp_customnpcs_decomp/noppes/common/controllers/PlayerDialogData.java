/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 */
package noppes.common.controllers;

import java.util.HashSet;
import noppes.common.controllers.IPlayerData;

public class PlayerDialogData
implements IPlayerData {
    public HashSet<Integer> dialogsRead = new HashSet();

    public void readNBT(by compound) {
        HashSet<Integer> dialogsRead = new HashSet<Integer>();
        if (compound != null) {
            cg list = compound.m("DialogData");
            if (list != null && list.c() != 0) {
                for (int i = 0; i < list.c(); ++i) {
                    by nbttagcompound = (by)list.b(i);
                    dialogsRead.add(nbttagcompound.e("Dialog"));
                }
            }
            this.dialogsRead = dialogsRead;
        }
    }

    @Override
    public by writeNBT(by compound) {
        cg list = new cg();
        for (int dia : this.dialogsRead) {
            by nbttagcompound = new by();
            nbttagcompound.a("Dialog", dia);
            list.a((cl)nbttagcompound);
        }
        compound.a("DialogData", (cl)list);
        return compound;
    }
}

