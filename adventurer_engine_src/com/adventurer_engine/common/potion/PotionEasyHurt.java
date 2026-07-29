/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  ni
 */
package com.adventurer_engine.common.potion;

import com.adventurer_engine.common.CommonConfig;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PotionEasyHurt
extends ni {
    public PotionEasyHurt() {
        super(CommonConfig.potionStartID, true, 16766720);
        this.b("potion.easyHurt");
        this.b(5, 1);
    }

    public void onHurt(LivingHurtEvent event, int level) {
        String type = event.source.o;
        if ("inFire".equals(type) || "onFire".equals(type) || "wither".equals(type) || "magic".equals(type)) {
            event.ammount += 1.0f * (float)level;
        }
    }
}

