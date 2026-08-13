/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  uf
 *  ye
 */
package noppes.common.items;

import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;

public class ItemMusic
extends ItemNpcInterface {
    public ItemMusic(int par1) {
        super(par1);
        this.a(CustomItems.tabMisc);
    }

    public ye a(ye par1ItemStack, abw par2World, uf player) {
        if (par2World.I) {
            return par1ItemStack;
        }
        int note = par2World.s.nextInt(24);
        float var7 = (float)Math.pow(2.0, (double)(note - 12) / 12.0);
        String var8 = "harp";
        par2World.a(player.u, player.v, player.w, "note." + var8, 3.0f, var7);
        par2World.a("note", player.v, player.v + 1.2, player.v, (double)note / 24.0, 0.0, 0.0);
        return par1ItemStack;
    }
}

