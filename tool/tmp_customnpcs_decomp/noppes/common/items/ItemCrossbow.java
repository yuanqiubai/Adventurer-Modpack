/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  nn
 *  of
 *  org.lwjgl.opengl.GL11
 *  uf
 *  yc
 *  ye
 *  zj
 */
package noppes.common.items;

import noppes.common.entity.EntityProjectile;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemCrossbow
extends ItemNpcInterface {
    public ItemCrossbow(int par1) {
        super(par1);
        this.e(129);
        this.a(CustomItems.tabWeapon);
    }

    public void a(ye stack, abw par2World, uf player, int count) {
        if (!player.q.I && (stack.e.e("IsLoaded") == 1 || player.bG.d)) {
            if (stack.e.e("Reloading") == 1 && !player.bG.d) {
                stack.e.a("Reloading", 0);
                return;
            }
            stack.a(1, (of)player);
            EntityProjectile projectile = new EntityProjectile(player.q, (of)player, new ye(yc.n, 1, 0), false);
            projectile.damage = 10.0f;
            projectile.setSpeed(20);
            projectile.setHasGravity(true);
            projectile.shoot(2.0f);
            if (!player.bG.d) {
                player.bn.d(CustomItems.crossbowBolt.cv);
            }
            player.q.a((nn)player, "random.bow", 0.9f, f.nextFloat() * 0.3f + 0.8f);
            player.q.d((nn)projectile);
            stack.e.a("IsLoaded", 0);
        }
    }

    public void onUsingItemTick(ye stack, uf player, int count) {
        if (!player.q.I) {
            int ticks = this.d_(stack) - count;
            if (!player.bG.d && stack.e.e("Reloading") == 1 && player.bn.e(CustomItems.crossbowBolt.cv) && ticks == 20) {
                player.q.a((nn)player, "random.click", 1.0f, 1.0f);
                stack.e.a("IsLoaded", 1);
            }
        }
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.7f, (float)0.7f, (float)0.7f);
        GL11.glTranslatef((float)0.2f, (float)0.2f, (float)-0.2f);
    }

    public ye a(ye stack, abw world, uf player) {
        if (stack.e == null) {
            stack.e = new by();
        }
        if (!player.bG.d && player.bn.e(CustomItems.crossbowBolt.cv) && stack.e.e("IsLoaded") == 0) {
            stack.e.a("Reloading", 1);
        }
        player.a(stack, this.d_(stack));
        return stack;
    }

    public int d_(ye par1ItemStack) {
        return 72000;
    }

    public zj c_(ye stack) {
        return stack.e != null && stack.e.e("Reloading") != 0 ? zj.d : zj.e;
    }
}

