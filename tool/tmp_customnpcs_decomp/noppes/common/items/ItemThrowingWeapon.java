/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  nn
 *  of
 *  uf
 *  ye
 */
package noppes.common.items;

import noppes.common.entity.EntityProjectile;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;

public class ItemThrowingWeapon
extends ItemNpcInterface {
    private boolean rotating = false;
    private int damage = 2;
    private boolean dropItem = false;

    public ItemThrowingWeapon(int par1) {
        super(par1);
        this.a(CustomItems.tabWeapon);
    }

    public void a(ye par1ItemStack, abw worldObj, uf player, int par4) {
        if (worldObj.I) {
            player.aV();
        } else {
            EntityProjectile projectile = new EntityProjectile(worldObj, (of)player, new ye(par1ItemStack.b(), 1, par1ItemStack.k()), false);
            projectile.damage = this.damage;
            projectile.canBePickedUp = !player.bG.d && this.dropItem;
            projectile.setRotating(this.rotating);
            projectile.setIs3D(true);
            projectile.setStickInWall(true);
            projectile.setHasGravity(true);
            projectile.setSpeed(12);
            if (!player.bG.d) {
                player.bn.d(this.cv);
            }
            projectile.shoot(1.0f);
            worldObj.a((nn)player, "customnpcs:misc.swosh", 1.0f, 1.0f);
            worldObj.d((nn)projectile);
        }
    }

    public ItemThrowingWeapon setRotating() {
        this.rotating = true;
        return this;
    }

    public ItemThrowingWeapon setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public ItemThrowingWeapon setDropItem() {
        this.dropItem = true;
        return this;
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        par3EntityPlayer.a(par1ItemStack, this.d_(par1ItemStack));
        return par1ItemStack;
    }

    public int d_(ye par1ItemStack) {
        return 72000;
    }
}

