/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  ni
 *  org.apache.commons.lang3.tuple.Pair
 *  tp
 *  uf
 */
package com.adventurer_engine.util;

import com.adventurer_engine.util.math.Formula;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.lang3.tuple.Pair;

public class DataCenter {
    public static Formula leapingBlow_damage;
    public static Formula armorBreak_damage;
    public static Formula armorBreak_exhaustion;
    public static Formula armorBreak_time;
    public static Formula armorBreak_bomb;
    public static Formula armorBreak_bomb_damage;
    public static Formula armorBreak_bomb_potion;
    public static Formula sword_basic_distance;
    public static Formula sword_basic_break;
    public static Formula endingBlow_damage;
    public static Formula endingBlow_percent;
    public static Formula endingBlow_potion_percent;
    public static Formula risingCut_exhaustion;
    public static Formula risingCut_height;
    public static Formula risingCut_damage;
    public static Formula risingCut_addition;
    public static Formula risingCut_addition_damage;
    public static Formula dash_damage;
    public static Formula dash_exhaustion;
    public static Formula spin_damage;

    public DataCenter(FMLPreInitializationEvent event) {
        File data = new File(event.getModConfigurationDirectory().getParentFile().getPath() + File.separator + "data.txt");
        try {
            Scanner scan = new Scanner(data, "utf-8");
            while (scan.hasNext()) {
                String[] sap;
                String line = scan.nextLine();
                if (line.startsWith("//") || (sap = line.split("=")).length != 2) continue;
                if ("leapingBlow_damage".equals(sap[0])) {
                    leapingBlow_damage = new Formula(sap[1]);
                    continue;
                }
                if ("armorBreak_damage".equals(sap[0])) {
                    armorBreak_damage = new Formula(sap[1]);
                    continue;
                }
                if ("armorBreak_exhaustion".equals(sap[0])) {
                    armorBreak_exhaustion = new Formula(sap[1]);
                    continue;
                }
                if ("armorBreak_time".equals(sap[0])) {
                    armorBreak_time = new Formula(sap[1]);
                    continue;
                }
                if ("armorBreak_bomb".equals(sap[0])) {
                    armorBreak_bomb = new Formula(sap[1]);
                    continue;
                }
                if ("armorBreak_bomb_damage".equals(sap[0])) {
                    armorBreak_bomb_damage = new Formula(sap[1]);
                    continue;
                }
                if ("armorBreak_bomb_potion".equals(sap[0])) {
                    armorBreak_bomb_potion = new Formula(sap[1]);
                    continue;
                }
                if ("sword_basic_distance".equals(sap[0])) {
                    sword_basic_distance = new Formula(sap[1]);
                    continue;
                }
                if ("sword_basic_break".equals(sap[0])) {
                    sword_basic_break = new Formula(sap[1]);
                    continue;
                }
                if ("endingBlow_damage".equals(sap[0])) {
                    endingBlow_damage = new Formula(sap[1]);
                    continue;
                }
                if ("endingBlow_percent".equals(sap[0])) {
                    endingBlow_percent = new Formula(sap[1]);
                    continue;
                }
                if ("endingBlow_potion_percent".equals(sap[0])) {
                    endingBlow_potion_percent = new Formula(sap[1]);
                    continue;
                }
                if ("risingCut_exhaustion".equals(sap[0])) {
                    risingCut_exhaustion = new Formula(sap[1]);
                    continue;
                }
                if ("risingCut_height".equals(sap[0])) {
                    risingCut_height = new Formula(sap[1]);
                    continue;
                }
                if ("risingCut_damage".equals(sap[0])) {
                    risingCut_damage = new Formula(sap[1]);
                    continue;
                }
                if ("risingCut_addition".equals(sap[0])) {
                    risingCut_addition = new Formula(sap[1]);
                    continue;
                }
                if ("risingCut_addition_damage".equals(sap[0])) {
                    risingCut_addition_damage = new Formula(sap[1]);
                    continue;
                }
                if ("dash_damage".equals(sap[0])) {
                    dash_damage = new Formula(sap[1]);
                    continue;
                }
                if ("dash_exhaustion".equals(sap[0])) {
                    dash_exhaustion = new Formula(sap[1]);
                    continue;
                }
                if (!"spin_damage".equals(sap[0])) continue;
                spin_damage = new Formula(sap[1]);
            }
            scan.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static HashMap<String, Double> getPlayerInfo(uf player, Pair<String, Double> ... add) {
        HashMap<String, Double> nums = new HashMap<String, Double>();
        double ad2 = player.a(tp.e).e();
        double exp = player.bH;
        double sp = player.bI().a();
        nums.put("ad", ad2);
        nums.put("exp", exp);
        nums.put("sp", sp);
        for (Pair<String, Double> p : add) {
            nums.put((String)p.getLeft(), (Double)p.getRight());
        }
        for (int i = 1; i <= 23; ++i) {
            int lv2 = player.i(i) ? player.b(ni.a[i]).c() + 1 : 0;
            nums.put("p" + i, Double.valueOf(lv2));
        }
        return nums;
    }
}

