/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  bkb
 *  by
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ms
 *  mt
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.common.items.gadget;

import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.common.items.TabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.List;

public class ItemLifeGem
extends yc {
    public ItemLifeGem() {
        super(7762);
        this.cw = 64;
        this.a(TabsLoader.GADGETS);
        this.b("life_gem");
    }

    public ye a(ye item, abw world, uf player) {
        if (world.I) {
            return item;
        }
        ye greenRing = null;
        for (ye i : player.bn.a) {
            if (i == null || i.d != ItemLoader.itemGreenRing.cv) continue;
            greenRing = i;
            break;
        }
        if (greenRing != null) {
            if (greenRing.p()) {
                greenRing.q().a("amount", greenRing.q().e("amount") + 1);
            } else {
                greenRing.d(new by());
                greenRing.q().a("amount", 1);
            }
            --item.b;
        }
        return item;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt register) {
        this.cz = register.a("adventurer_engine:life_gem");
    }

    @SideOnly(value=Side.CLIENT)
    public ms h(ye item) {
        return this.cz;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(ye itemStack, uf player, List list, boolean par4) {
        String[] desc = bkb.a((String)"item.life_gem.info").split("\\\\n");
        Collections.addAll(list, desc);
    }
}

