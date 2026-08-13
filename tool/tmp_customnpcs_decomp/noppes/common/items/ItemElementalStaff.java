/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  of
 *  rz
 *  uf
 *  ww
 *  ye
 */
package noppes.common.items;

import java.awt.Color;
import java.util.List;
import noppes.CustomNpcs;
import noppes.common.items.EnumNpcToolMaterial;
import noppes.common.items.ItemStaff;
import noppes.common.objcenter.CustomItems;

public class ItemElementalStaff
extends ItemStaff {
    public ItemElementalStaff(int par1, EnumNpcToolMaterial material) {
        super(par1, material);
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

    @Override
    public ye getProjectile(ye stack) {
        return new ye(CustomItems.orb, 1, stack.k());
    }

    @Override
    public void spawnParticle(ye stack, uf player) {
        CustomNpcs.proxy.spawnParticle((of)player, "Spell", stack.k(), 4);
    }
}

