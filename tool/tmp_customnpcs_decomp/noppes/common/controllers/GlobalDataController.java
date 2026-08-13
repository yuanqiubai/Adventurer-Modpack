/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ci
 */
package noppes.common.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import noppes.util.Tools;

public class GlobalDataController {
    public static GlobalDataController instance;
    private int itemGiverId = 0;

    public GlobalDataController() {
        instance = this;
        this.load();
    }

    private void load() {
        File saveDir = Tools.getWorldSaveDirectory();
        try {
            File e = new File(saveDir, "global.dat");
            if (e.exists()) {
                this.loadData(e);
            }
        }
        catch (Exception var5) {
            try {
                File ee = new File(saveDir, "global.dat_old");
                if (ee.exists()) {
                    this.loadData(ee);
                }
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }

    private void loadData(File file) throws Exception {
        by nbttagcompound1 = ci.a((InputStream)new FileInputStream(file));
        this.itemGiverId = nbttagcompound1.e("itemGiverId");
    }

    public void saveData() {
        try {
            File e = Tools.getWorldSaveDirectory();
            by nbttagcompound = new by();
            nbttagcompound.a("itemGiverId", this.itemGiverId);
            File file = new File(e, "global.dat_new");
            File file1 = new File(e, "global.dat_old");
            File file2 = new File(e, "global.dat");
            ci.a((by)nbttagcompound, (OutputStream)new FileOutputStream(file));
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
        catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    public int incrementItemGiverId() {
        ++this.itemGiverId;
        this.saveData();
        return this.itemGiverId;
    }
}

