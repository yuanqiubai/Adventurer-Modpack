/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  ni
 *  of
 */
package com.adventurer_engine.common.potion;

import com.adventurer_engine.common.CommonConfig;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PotionResentment
extends ni {
    public PotionResentment() {
        super(CommonConfig.potionStartID + 4, false, 0xFF55FF);
        this.b("potion.resentment");
        this.b(5, 2);
    }

    public void onHurt(LivingHurtEvent event, int level) {
        of attacker;
        of of2 = attacker = event.source.i() instanceof of ? (of)event.source.i() : null;
        if (attacker == null) {
            return;
        }
        event.ammount *= 0.1f * (float)Math.abs(level) + 1.0f;
    }
}

