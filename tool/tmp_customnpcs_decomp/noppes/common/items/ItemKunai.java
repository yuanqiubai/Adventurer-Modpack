/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  nn
 *  of
 *  org.lwjgl.opengl.GL11
 *  uf
 *  yd
 *  ye
 */
package noppes.common.items;

import noppes.common.entity.EntityProjectile;
import noppes.common.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemKunai
extends ItemNpcWeaponInterface {
    public ItemKunai(int par1, yd tool) {
        super(par1, tool);
    }

    public void a(ye par1ItemStack, abw worldObj, uf player, int par4) {
        if (worldObj.I) {
            player.aV();
        } else {
            EntityProjectile projectile = new EntityProjectile(worldObj, (of)player, par1ItemStack, false);
            projectile.damage = this.g();
            projectile.destroyedOnEntityHit = false;
            projectile.canBePickedUp = !player.bG.d;
            projectile.setIs3D(true);
            projectile.setStickInWall(true);
            projectile.setHasGravity(true);
            projectile.setSpeed(12);
            projectile.shoot(1.0f);
            if (!player.bG.d) {
                par1ItemStack.a(1, (of)player);
                if (par1ItemStack.b == 0) {
                    return;
                }
                player.bn.a[player.bn.c] = null;
            }
            worldObj.a((nn)player, "customnpcs:misc.swosh", 1.0f, 1.0f);
            worldObj.d((nn)projectile);
        }
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        par3EntityPlayer.a(par1ItemStack, this.d_(par1ItemStack));
        return par1ItemStack;
    }

    public int d_(ye par1ItemStack) {
        return 72000;
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.4f, (float)0.4f, (float)0.4f);
        GL11.glTranslatef((float)-0.2f, (float)0.3f, (float)0.2f);
    }

    public boolean o_() {
        return true;
    }
}

