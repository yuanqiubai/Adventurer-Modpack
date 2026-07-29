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
 *  nn
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

public class ItemGreenRing
extends yc {
    public ItemGreenRing() {
        super(7763);
        this.cw = 1;
        this.a(TabsLoader.GADGETS);
        this.b("green_ring");
    }

    public ye a(ye item, abw world, uf player) {
        by nbt;
        if (world.I) {
            return item;
        }
        if (player.ah() && item.p() && (nbt = item.q()).b("amount") && nbt.e("amount") > 0) {
            nbt.a("amount", nbt.e("amount") - 1);
            ye lifeGem = new ye((yc)ItemLoader.itemLifeGem);
            lifeGem.b = 1;
            player.q.d((nn)player.b(lifeGem));
        }
        return item;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt register) {
        this.cz = register.a("adventurer_engine:green_ring");
    }

    @SideOnly(value=Side.CLIENT)
    public ms h(ye item) {
        return this.cz;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(ye itemStack, uf player, List list, boolean par4) {
        int amount = 0;
        if (itemStack.p()) {
            amount = itemStack.q().e("amount");
        }
        if (amount == 0) {
            list.add(bkb.a((String)"item.green_ring.empty"));
        } else {
            list.add(String.format(bkb.a((String)"item.green_ring.amount"), amount));
        }
        String[] desc = bkb.a((String)"item.green_ring.info").split("\\\\n");
        Collections.addAll(list, desc);
    }
}

