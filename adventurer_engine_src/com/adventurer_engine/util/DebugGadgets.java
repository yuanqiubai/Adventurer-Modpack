/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 */
package com.adventurer_engine.util;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.PrintStream;
import java.util.Calendar;

public class DebugGadgets {
    public static PrintStream debugFile;
    private static final boolean enable = true;

    public static void debugInfo(String info) {
        debugFile.printf("[%s]", Calendar.getInstance().getTime());
        debugFile.println(info);
    }

    public static void hexDump(byte[] data) {
        int i = 0;
        debugFile.printf("[%s]", Calendar.getInstance().getTime());
        for (byte b : data) {
            debugFile.printf("%02x ", b);
            if (++i % 32 != 0 || i == 0) continue;
            debugFile.println();
            debugFile.printf("[%s]", Calendar.getInstance().getTime());
        }
    }

    public static void initDebugFile(FMLPreInitializationEvent event) {
        try {
            debugFile = new PrintStream(event.getModConfigurationDirectory().getParentFile().getParentFile().getPath() + "\\rce_debug.log");
            DebugGadgets.debugInfo("First Message");
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

