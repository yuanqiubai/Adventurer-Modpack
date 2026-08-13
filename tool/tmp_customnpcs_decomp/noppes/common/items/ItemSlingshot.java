/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  aqz
 *  nn
 *  of
 *  org.lwjgl.opengl.GL11
 *  uf
 *  ye
 *  zj
 */
package noppes.common.items;

import noppes.common.constants.EnumParticleType;
import noppes.common.entity.EntityProjectile;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemSlingshot
extends ItemNpcInterface {
    public ItemSlingshot(int par1) {
        super(par1);
        this.cw = 1;
        this.e(384);
        this.a(CustomItems.tabWeapon);
    }

    public void a(ye par1ItemStack, abw worldObj, uf player, int par4) {
        int ticks;
        if (!worldObj.I && (ticks = this.d_(par1ItemStack) - par4) >= 6 && (player.bG.d || player.bn.d(aqz.B.cF))) {
            par1ItemStack.a(1, (of)player);
            EntityProjectile projectile = new EntityProjectile(worldObj, (of)player, new ye(aqz.B), false);
            projectile.damage = 3.0f;
            projectile.punch = 1;
            projectile.setRotating(true);
            if (ticks > 24) {
                projectile.setParticleEffect(EnumParticleType.Crit);
                projectile.punch = 2;
            }
            projectile.setHasGravity(true);
            projectile.setSpeed(14);
            projectile.shoot(1.0f);
            worldObj.a((nn)player, "random.bow", 1.0f, f.nextFloat() * 0.3f + 0.8f);
            worldObj.d((nn)projectile);
        }
    }

    @Override
    public void renderSpecial() {
        GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glTranslatef((float)0.0f, (float)0.3f, (float)0.0f);
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        par3EntityPlayer.a(par1ItemStack, this.d_(par1ItemStack));
        return par1ItemStack;
    }

    public int d_(ye par1ItemStack) {
        return 72000;
    }

    public zj c_(ye par1ItemStack) {
        return zj.e;
    }
}

