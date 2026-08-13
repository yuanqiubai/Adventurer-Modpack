/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  rz
 *  ww
 *  ye
 */
package noppes.common.items;

import java.awt.Color;
import java.util.List;
import noppes.common.items.ItemNpcInterface;

public class ItemOrb
extends ItemNpcInterface {
    public ItemOrb(int par1) {
        super(par1);
        this.a(true);
    }

    public int a(ye par1ItemStack, int par2) {
        float[] color = rz.bp[par1ItemStack.k()];
        return new Color(color[0], color[1], color[2]).getRGB();
    }

    public boolean b() {
        return true;
    }

    public void a(int par1, ww par2CreativeTabs, List par3List) {
        for (int var4 = 0; var4 < 16; ++var4) {
            par3List.add(new ye(par1, 1, var4));
        }
    }
}

