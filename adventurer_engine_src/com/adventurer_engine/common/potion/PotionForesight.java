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
import com.adventurer_engine.common.potion.PotionLoader;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PotionForesight
extends ni {
    public PotionForesight() {
        super(CommonConfig.potionStartID + 1, false, 16766720);
        this.b("potion.foresight");
        this.b(5, 1);
    }

    public void onHurt(LivingHurtEvent event, int level) {
        of attacker;
        of of2 = attacker = event.source.i() instanceof of ? (of)event.source.i() : null;
        if (attacker == null) {
            return;
        }
        event.ammount *= 0.2f * (float)Math.abs(level) + 1.0f;
        if (attacker.a((ni)this)) {
            attacker.k(PotionLoader.foresight.H);
        }
    }
}

