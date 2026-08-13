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

public class PlayerTransportData
implements IPlayerData {
    public HashSet<Integer> transports = new HashSet();

    public void readNBT(by compound) {
        cg list;
        HashSet<Integer> dialogsRead = new HashSet<Integer>();
        if (compound != null && (list = compound.m("TransportData")) != null && list.c() != 0) {
            for (int i = 0; i < list.c(); ++i) {
                by nbttagcompound = (by)list.b(i);
                dialogsRead.add(nbttagcompound.e("Transport"));
            }
            this.transports = dialogsRead;
        }
    }

    @Override
    public by writeNBT(by compound) {
        cg list = new cg();
        for (int dia : this.transports) {
            by nbttagcompound = new by();
            nbttagcompound.a("Transport", dia);
            list.a((cl)nbttagcompound);
        }
        compound.a("TransportData", (cl)list);
        return compound;
    }
}

