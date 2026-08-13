/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  ci
 *  cl
 *  com.adventurer_engine.util.DebugGadgets
 *  nn
 */
package noppes.client.controllers;

import com.adventurer_engine.util.DebugGadgets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import noppes.CustomNpcs;

public class CloneController {
    public static nn toClone = null;

    private static ArrayList<by> loadClones() {
        try {
            File e = new File(CustomNpcs.Dir, "clonednpcs.dat");
            return !e.exists() ? new ArrayList<by>() : CloneController.loadClones(e);
        }
        catch (Exception var2) {
            System.err.println(var2.getMessage());
            try {
                File e = new File(CustomNpcs.Dir, "clonednpcs.dat_old");
                return CloneController.loadClones(e);
            }
            catch (Exception var1) {
                System.err.println(var1.getMessage());
                return new ArrayList<by>();
            }
        }
    }

    private static ArrayList<by> loadClones(File file) throws Exception {
        ArrayList<by> clones = new ArrayList<by>();
        by nbttagcompound1 = ci.a((InputStream)new FileInputStream(file));
        cg list = nbttagcompound1.m("Data");
        if (list == null) {
            return clones;
        }
        for (int i = 0; i < list.c(); ++i) {
            by nbttagcompound = (by)list.b(i);
            if (!nbttagcompound.b("ClonedDate")) {
                nbttagcompound.a("ClonedDate", System.currentTimeMillis());
                nbttagcompound.a("ClonedName", nbttagcompound.i("Name"));
            }
            clones.add(nbttagcompound);
        }
        return clones;
    }

    public static void saveClones(Collection<by> clones) {
        Object file2;
        cg e = new cg();
        for (Object file2 : clones) {
            e.a((cl)file2);
        }
        DebugGadgets.debugInfo((String)("clones size = " + clones.size() + " e.length = " + e.c()));
        by nbttagcompound1 = new by();
        nbttagcompound1.a("Data", (cl)e);
        file2 = new File(CustomNpcs.Dir, "clonednpcs.dat_new");
        File file1 = new File(CustomNpcs.Dir, "clonednpcs.dat_old");
        File file2 = new File(CustomNpcs.Dir, "clonednpcs.dat");
        CloneController.saveInFile(nbttagcompound1, (File)file2, file1, file2);
    }

    public static void saveInFile(by nbttagcompound1, File file, File file1, File file2) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            ci.a((by)nbttagcompound1, (OutputStream)out);
            out.close();
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
        catch (IOException e) {
            DebugGadgets.debugInfo((String)e.getMessage());
        }
    }

    public static ArrayList<by> getClones() {
        return CloneController.loadClones();
    }

    public static void addClone(nn entity, String name, int tab) {
        ArrayList<by> clones = CloneController.getClones();
        by nbttagcompound = new by();
        entity.d(nbttagcompound);
        nbttagcompound.a("ClonedDate", System.currentTimeMillis());
        nbttagcompound.a("ClonedName", name);
        nbttagcompound.a("ClonedTab", tab);
        clones.add(nbttagcompound);
        CloneController.saveClones(clones);
    }
}

