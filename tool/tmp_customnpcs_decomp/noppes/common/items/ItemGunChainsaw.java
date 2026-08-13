/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  of
 *  yd
 *  ye
 */
package noppes.common.items;

import noppes.common.items.ItemNpcWeaponInterface;

public class ItemGunChainsaw
extends ItemNpcWeaponInterface {
    public ItemGunChainsaw(int par1, yd tool) {
        super(par1, tool);
    }

    public boolean a(ye par1ItemStack, of par2EntityLiving, of par3EntityLiving) {
        if (par2EntityLiving.aN() <= 0.0f) {
            return false;
        }
        double x = par2EntityLiving.u;
        double y = par2EntityLiving.v + (double)(par2EntityLiving.P / 2.0f);
        double z2 = par2EntityLiving.w;
        par3EntityLiving.q.a(x, y, z2, "random.explode", 0.8f, (1.0f + (par3EntityLiving.q.s.nextFloat() - par3EntityLiving.q.s.nextFloat()) * 0.2f) * 0.7f);
        par3EntityLiving.q.a("largeexplode", x, y, z2, 0.0, 0.0, 0.0);
        return super.a(par1ItemStack, par2EntityLiving, par3EntityLiving);
    }
}

