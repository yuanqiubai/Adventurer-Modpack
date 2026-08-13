/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  ci
 *  cl
 *  jv
 */
package noppes.common.controllers;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import noppes.common.constants.EnumRoleType;
import noppes.common.controllers.TransportCategory;
import noppes.common.controllers.TransportLocation;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleTransporter;
import noppes.util.Tools;

public class TransportController {
    private static TransportController instance;
    public HashMap<Integer, TransportCategory> categories = new HashMap();
    private HashMap<Integer, TransportLocation> locations = new HashMap();
    private int lastUsedID = 0;

    public TransportController() {
        instance = this;
        this.loadCategories();
        if (this.categories.isEmpty()) {
            TransportCategory cat = new TransportCategory();
            cat.id = 1;
            cat.title = "Default";
            this.categories.put(cat.id, cat);
        }
    }

    public static TransportController getInstance() {
        return instance;
    }

    private void loadCategories() {
        File saveDir = Tools.getWorldSaveDirectory();
        if (saveDir != null) {
            try {
                File e = new File(saveDir, "transport.dat");
                if (!e.exists()) {
                    return;
                }
                this.loadCategoriesFile(e);
            }
            catch (IOException var5) {
                try {
                    File ee = new File(saveDir, "transport.dat_old");
                    if (!ee.exists()) {
                        return;
                    }
                    this.loadCategoriesFile(ee);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    private void loadCategoriesFile(File file) throws IOException {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
        this.loadCategoriesStream(var1);
        var1.close();
    }

    public void loadCategoriesStream(DataInputStream stream) throws IOException {
        HashMap<Integer, TransportLocation> locations = new HashMap<Integer, TransportLocation>();
        HashMap<Integer, TransportCategory> categories = new HashMap<Integer, TransportCategory>();
        by nbttagcompound1 = ci.a((DataInput)stream);
        this.lastUsedID = nbttagcompound1.e("lastID");
        cg list = nbttagcompound1.m("NPCTransportCategories");
        if (list != null) {
            for (int i = 0; i < list.c(); ++i) {
                TransportCategory category = new TransportCategory();
                by compound = (by)list.b(i);
                category.readNBT(compound);
                for (TransportLocation location : category.locations.values()) {
                    locations.put(location.id, location);
                }
                categories.put(category.id, category);
            }
            this.locations = locations;
            this.categories = categories;
        }
    }

    public by getNBT() {
        cg list = new cg();
        for (TransportCategory category : this.categories.values()) {
            by compound = new by();
            category.writeNBT(compound);
            list.a((cl)compound);
        }
        by nbttagcompound1 = new by();
        nbttagcompound1.a("lastID", this.lastUsedID);
        nbttagcompound1.a("NPCTransportCategories", (cl)list);
        return nbttagcompound1;
    }

    public void saveCategories() {
        try {
            File e = Tools.getWorldSaveDirectory();
            File file = new File(e, "transport.dat_new");
            File file1 = new File(e, "transport.dat_old");
            File file2 = new File(e, "transport.dat");
            ci.a((by)this.getNBT(), (OutputStream)new FileOutputStream(file));
            if (file1.exists()) {
                file1.delete();
            }
            file2.renameTo(file1);
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception var5) {
            System.err.println(var5.getMessage());
        }
    }

    public TransportLocation getTransport(int transportId) {
        return this.locations.get(transportId);
    }

    public TransportLocation getTransport(String name) {
        TransportLocation loc;
        Iterator<TransportLocation> i$ = this.locations.values().iterator();
        do {
            if (!i$.hasNext()) {
                return null;
            }
            loc = i$.next();
        } while (!loc.name.equals(name));
        return loc;
    }

    private int getUniqueIdLocation() {
        if (this.lastUsedID == 0) {
            for (int catid : this.locations.keySet()) {
                if (catid <= this.lastUsedID) continue;
                this.lastUsedID = catid;
            }
        }
        ++this.lastUsedID;
        return this.lastUsedID;
    }

    private int getUniqueIdCategory() {
        int id = 0;
        for (int catid : this.categories.keySet()) {
            if (catid <= id) continue;
            id = catid;
        }
        return ++id;
    }

    public void setLocation(TransportLocation location) {
        if (this.locations.containsKey(location.id)) {
            for (TransportCategory cat : this.categories.values()) {
                cat.locations.remove(location.id);
            }
        }
        this.locations.put(location.id, location);
        location.category.locations.put(location.id, location);
    }

    public void removeLocation(int location) {
        TransportLocation loc = this.locations.get(location);
        if (loc != null) {
            loc.category.locations.remove(location);
            this.locations.remove(location);
            this.saveCategories();
        }
    }

    private boolean containsCategoryName(String name) {
        TransportCategory cat;
        name = name.toLowerCase();
        Iterator<TransportCategory> i$ = this.categories.values().iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            cat = i$.next();
        } while (!cat.title.toLowerCase().equals(name));
        return true;
    }

    public void saveCategory(String name, int id) {
        if (id < 0) {
            id = this.getUniqueIdCategory();
        }
        if (this.categories.containsKey(id)) {
            TransportCategory category = this.categories.get(id);
            if (!category.title.equals(name)) {
                while (this.containsCategoryName(name)) {
                    name = name + "_";
                }
                this.categories.get((Object)Integer.valueOf((int)id)).title = name;
            }
        } else {
            while (this.containsCategoryName(name)) {
                name = name + "_";
            }
            TransportCategory category = new TransportCategory();
            category.id = id;
            category.title = name;
            this.categories.put(id, category);
        }
        this.saveCategories();
    }

    public void removeCategory(int id) {
        TransportCategory cat;
        if (this.categories.size() != 1 && (cat = this.categories.get(id)) != null) {
            for (int i : cat.locations.keySet()) {
                this.locations.remove(i);
            }
            this.categories.remove(id);
            this.saveCategories();
        }
    }

    public boolean containsLocationName(String name) {
        TransportLocation loc;
        name = name.toLowerCase();
        Iterator<TransportLocation> i$ = this.locations.values().iterator();
        do {
            if (!i$.hasNext()) {
                return false;
            }
            loc = i$.next();
        } while (!loc.name.toLowerCase().equals(name));
        return true;
    }

    public TransportLocation saveLocation(int categoryId, by nbt, jv player, EntityNPCInterface npc) {
        TransportCategory category = this.categories.get(categoryId);
        if (category != null && npc.advanced.role == EnumRoleType.Transporter) {
            RoleTransporter role = (RoleTransporter)npc.roleInterface;
            TransportLocation location = new TransportLocation();
            location.readNBT(nbt);
            location.category = category;
            if (role.hasTransport()) {
                location.id = role.transportId;
            }
            if (location.id < 0 || !this.locations.get((Object)Integer.valueOf((int)location.id)).name.equals(location.name)) {
                while (this.containsLocationName(location.name)) {
                    location.name = location.name + "_";
                }
            }
            if (location.id < 0) {
                location.id = this.getUniqueIdLocation();
            }
            category.locations.put(location.id, location);
            this.locations.put(location.id, location);
            this.saveCategories();
            return location;
        }
        return null;
    }
}

