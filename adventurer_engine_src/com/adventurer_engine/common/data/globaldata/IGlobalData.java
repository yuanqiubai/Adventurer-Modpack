/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ci
 */
package com.adventurer_engine.common.data.globaldata;

import com.adventurer_engine.common.data.globaldata.BackpackInventoryData;
import com.adventurer_engine.common.data.globaldata.CalendarData;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.Tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public abstract class IGlobalData {
    public static CalendarData CALENDAR_DATA;
    public static BackpackInventoryData BACKPACK_INVENTORY_DATA;
    private static boolean initialed;
    private static final List<IGlobalData> globalDataList;
    protected boolean modified = false;

    public IGlobalData() {
        globalDataList.add(this);
    }

    public static void initAll() {
        CALENDAR_DATA = new CalendarData();
        BACKPACK_INVENTORY_DATA = new BackpackInventoryData();
        for (IGlobalData data : globalDataList) {
            while (!data.initialed()) {
                try {
                    by nbt = IGlobalData.readNBTFromFile(data.saveFile());
                    if (nbt == null) {
                        data.setDefaultValue();
                        continue;
                    }
                    data.readNBTTagCompound(nbt);
                }
                catch (IOException e) {
                    DebugGadgets.debugInfo("Exception on init global data: " + data.getClass().getName() + ": ");
                    DebugGadgets.debugInfo(e.getMessage());
                    data.setDefaultValue();
                }
            }
        }
        initialed = true;
    }

    public static void saveAll() {
        for (IGlobalData data : globalDataList) {
            if (!data.modified) continue;
            try {
                by nbt = new by();
                data.writeNBTTagCompound(nbt);
                IGlobalData.saveNBT2File(nbt, data.saveFile());
                data.modified = false;
            }
            catch (IOException e) {
                Tools.getWorldSaveDirectory();
                DebugGadgets.debugInfo("Exception on save global data: " + data.getClass().getName() + ": ");
                DebugGadgets.debugInfo(e.getMessage());
            }
        }
    }

    abstract String saveFile();

    abstract boolean initialed();

    abstract void setDefaultValue();

    abstract void readNBTTagCompound(by var1);

    abstract void writeNBTTagCompound(by var1);

    public static boolean allInitialed() {
        return initialed;
    }

    public static void saveNBT2File(by nbt, String fileName) throws IOException {
        File e = Tools.getWorldSaveDirectory();
        File file = new File(e, fileName + "_new");
        File file1 = new File(e, fileName + "_old");
        File file2 = new File(e, fileName);
        ci.a((by)nbt, (OutputStream)new FileOutputStream(file));
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

    public static by readNBTFromFile(String fileName) throws IOException {
        File saveDir = Tools.getWorldSaveDirectory();
        File e = new File(saveDir, fileName);
        if (e.exists()) {
            return ci.a((InputStream)new FileInputStream(e));
        }
        e = new File(saveDir, fileName + "_old");
        if (e.exists()) {
            return ci.a((InputStream)new FileInputStream(e));
        }
        return null;
    }

    static {
        initialed = false;
        globalDataList = new LinkedList<IGlobalData>();
    }
}

