/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  net.minecraftforge.common.Configuration
 */
package com.adventurer_engine.common;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.util.logging.Logger;
import net.minecraftforge.common.Configuration;

public class CommonConfig {
    private static Logger logger;
    private static Configuration config;
    public static int potionStartID;
    public static int itemStartID;
    public static boolean enableSlideStep;
    public static int slideStepTick;
    public static int maxstack;
    public static int resumeRate;
    public static int resumeStop;
    public static int npc_hpm_maxplayer;
    public static double npc_hpm_ratio;
    public static boolean enable_npc_hpm;

    public CommonConfig(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        CommonConfig.load();
    }

    public static void load() {
        logger.info("RCsUtil: Start load the config.");
        String commit = "Start ID of the potions.";
        potionStartID = config.get("general", "potionStartID", 24, commit).getInt();
        commit = "Enable slide step";
        enableSlideStep = config.get("general", "enableSlideStep", true, commit).getBoolean(true);
        commit = "Slide step ticks";
        slideStepTick = config.get("general", "slideStepTick", 30, commit).getInt();
        commit = "Start ID of the items.";
        itemStartID = config.get("general", "itemStartID", 10000, commit).getInt();
        itemStartID -= 256;
        commit = "Potion bottle max stack amount.";
        maxstack = config.get("general", "potion_maxstack", 10, commit).getInt();
        commit = "Resume rate. Resume 1 hunger value / resumeRate(tick)";
        resumeRate = config.get("general", "resumeRate", 10, commit).getInt();
        commit = "When slide step or right/left click, stop resume hunger value for resumeStop(tick)";
        resumeStop = config.get("general", "resumeStop", 40, commit).getInt();
        commit = "The more players there are, the more health the NPC has. This variable limits the maximum number of players that can change the health of the NPC. If the number of players exceeds this value, the health increase of the NPC will be determined by this value";
        npc_hpm_maxplayer = config.get("general", "npc_hpm_maxplayer", 4, commit).getInt();
        commit = "The increase in NPC health for each additional player";
        npc_hpm_ratio = (float)config.get("general", "npc_hpm_ratio", 0.5, commit).getDouble(0.5);
        commit = "Enable npc health modify by player amount";
        enable_npc_hpm = config.get("general", "enable_npc_hpm", true, commit).getBoolean(true);
        config.save();
        logger.info("Adventurer Engine: Finished to load the config.");
    }
}

