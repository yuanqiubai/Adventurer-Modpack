/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.config;

import noppes.common.config.ConfigProp;

public class CustomNPCConfig {
    @ConfigProp
    public static boolean DisableExtraNpcItems = false;
    @ConfigProp(info="Default Item ID range is from 26700")
    public static int ItemStartId = 26700;
    @ConfigProp(info="Default Block ID range is from 1525")
    public static int BlockStartId = 1525;
    @ConfigProp(info="Uses unique entities ids")
    public static boolean UseUniqueEntities = true;
    @ConfigProp(info="To use this UseUniqueEntities has to be false")
    public static int EntityStartId = 120;
    @ConfigProp(info="Navigation search range for NPCs. Not recommended to increase if you have a slow pc or on a server")
    public static int NpcNavRange = 32;
    @ConfigProp(info="Set to true if you want the dialog command option to be able to use op commands like tp etc")
    public static boolean NpcUseOpCommands = false;
    @ConfigProp
    public static boolean InventoryGuiEnabled = true;
    @ConfigProp(info="Enables CustomNpcs startup update message")
    public static boolean EnableUpdateChecker = true;
    @ConfigProp(info="Only ops can create and edit npcs")
    public static boolean OpsOnly = false;
}

