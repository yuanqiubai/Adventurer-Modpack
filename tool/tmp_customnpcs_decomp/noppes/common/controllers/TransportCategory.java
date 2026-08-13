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
import java.util.Vector;
import noppes.common.controllers.TransportLocation;

public class TransportCategory {
    public int id = -1;
    public String title = "";
    public HashMap<Integer, TransportLocation> locations = new HashMap();

    public Vector<TransportLocation> getDefaultLocations() {
        Vector<TransportLocation> list = new Vector<TransportLocation>();
        for (TransportLocation loc : this.locations.values()) {
            if (!loc.isDefault()) continue;
            list.add(loc);
        }
        return list;
    }

    public void readNBT(by compound) {
        this.id = compound.e("CategoryId");
        this.title = compound.i("CategoryTitle");
        cg locs = compound.m("CategoryLocations");
        if (locs != null && locs.c() != 0) {
            for (int ii = 0; ii < locs.c(); ++ii) {
                TransportLocation location = new TransportLocation();
                location.readNBT((by)locs.b(ii));
                location.category = this;
                this.locations.put(location.id, location);
            }
        }
    }

    public void writeNBT(by compound) {
        compound.a("CategoryId", this.id);
        compound.a("CategoryTitle", this.title);
        cg locs = new cg();
        for (TransportLocation location : this.locations.values()) {
            locs.a((cl)location.writeNBT());
        }
        compound.a("CategoryLocations", (cl)locs);
    }
}

