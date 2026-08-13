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
import java.util.Random;
import noppes.common.controllers.Line;

public class Lines {
    private static final Random random = new Random();
    public HashMap<Integer, Line> lines = new HashMap();

    public by writeToNBT() {
        by compound = new by();
        cg nbttaglist = new cg();
        for (int slot : this.lines.keySet()) {
            Line line = this.lines.get(slot);
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot);
            nbttagcompound.a("Line", line.text);
            nbttagcompound.a("Song", line.sound);
            nbttaglist.a((cl)nbttagcompound);
        }
        compound.a("Lines", (cl)nbttaglist);
        return compound;
    }

    public void readNBT(by compound) {
        cg nbttaglist = compound.m("Lines");
        HashMap<Integer, Line> map = new HashMap<Integer, Line>();
        for (int i = 0; i < nbttaglist.c(); ++i) {
            by nbttagcompound = (by)nbttaglist.b(i);
            Line line = new Line();
            line.text = nbttagcompound.i("Line");
            line.sound = nbttagcompound.i("Song");
            map.put(nbttagcompound.e("Slot"), line);
        }
        this.lines = map;
    }

    public Line getLine() {
        return this.lines.isEmpty() ? null : this.lines.get(random.nextInt(this.lines.size()));
    }

    public boolean isEmpty() {
        return this.lines.isEmpty();
    }
}

