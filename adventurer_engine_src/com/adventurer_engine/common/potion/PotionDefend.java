/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  ni
 *  nj
 *  nn
 *  of
 */
package com.adventurer_engine.common.potion;

import com.adventurer_engine.common.CommonConfig;
import com.adventurer_engine.common.potion.PotionLoader;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PotionDefend
extends ni {
    public PotionDefend() {
        super(CommonConfig.potionStartID + 2, false, 16766720);
        this.b("potion.defend");
        this.b(5, 1);
    }

    public void onHurt(LivingHurtEvent event) {
        String type = event.source.o;
        if ("wither".equals(type) || "magic".equals(type) || "inFire".equals(type) || "onFire".equals(type)) {
            return;
        }
        of entity = event.entityLiving;
        if (entity.a((ni)PotionLoader.defend)) {
            event.ammount = 0.0f;
            int level = entity.b((ni)PotionLoader.defend).c() + 1;
            float par3 = entity.q.s.nextFloat() * 0.4f + 0.5f;
            float par4 = 1.0f / (entity.q.s.nextFloat() * 0.4f + 0.5f);
            if (level == 1) {
                entity.q.a((nn)entity, "adventurer_engine:defend", par3, par4);
            } else if (level > 1 && level <= 7) {
                entity.q.a((nn)entity, "adventurer_engine:defend_strong", par3, par4);
            } else if (level > 7 && level <= 15) {
                entity.q.a((nn)entity, "adventurer_engine:defend_just", par3, par4);
            }
            entity.k(PotionLoader.defend.H);
            if (level != 1) {
                entity.c(new nj(PotionLoader.foresight.H, 60, level - 2));
            }
        }
    }
}

