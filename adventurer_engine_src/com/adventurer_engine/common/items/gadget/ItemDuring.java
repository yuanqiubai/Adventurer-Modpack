/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bkb
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ms
 *  mt
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.common.items.gadget;

import com.adventurer_engine.common.items.TabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.List;

public class ItemDuring
extends yc {
    public ItemDuring() {
        super(7761);
        this.cw = 1;
        this.a(TabsLoader.GADGETS);
        this.b("during");
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt register) {
        this.cz = register.a("adventurer_engine:dur_ring");
    }

    @SideOnly(value=Side.CLIENT)
    public ms h(ye item) {
        return this.cz;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(ye itemStack, uf player, List list, boolean par4) {
        String[] desc = bkb.a((String)"item.dur_ring.info").split("\\\\n");
        Collections.addAll(list, desc);
    }
}

