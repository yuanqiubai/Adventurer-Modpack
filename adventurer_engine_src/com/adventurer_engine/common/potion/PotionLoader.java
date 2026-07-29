/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.potion;

import com.adventurer_engine.common.potion.PotionDefend;
import com.adventurer_engine.common.potion.PotionEasyHurt;
import com.adventurer_engine.common.potion.PotionForesight;
import com.adventurer_engine.common.potion.PotionResentment;
import com.adventurer_engine.common.potion.PotionSprinter;

public class PotionLoader {
    public static PotionEasyHurt easyHurt;
    public static PotionForesight foresight;
    public static PotionDefend defend;
    public static PotionSprinter sprinter;
    public static PotionResentment resentment;

    public static void load() {
        easyHurt = new PotionEasyHurt();
        foresight = new PotionForesight();
        defend = new PotionDefend();
        sprinter = new PotionSprinter();
        resentment = new PotionResentment();
    }
}

