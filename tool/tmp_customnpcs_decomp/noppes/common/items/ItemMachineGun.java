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
 *  ye
 *  zj
 */
package noppes.common.items;

import noppes.common.entity.EntityProjectile;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemMachineGun
extends ItemNpcInterface {
    public ItemMachineGun(int par1) {
        super(par1);
        this.e(80);
        this.a(CustomItems.tabWeapon);
    }

    public void a(ye stack, abw par2World, uf player, int count) {
        if (!player.bG.d) {
            int ticks = this.d_(stack) - count;
            int shotsleft = stack.e.e("ShotsLeft") - ticks / 6;
            if (stack.e.e("Reloading") == 1) {
                shotsleft = ticks / 3;
                if (ticks > 24) {
                    shotsleft = 8;
                }
                if (shotsleft > 1) {
                    stack.e.a("ShotsLeft", shotsleft);
                    stack.e.a("Reloading", 0);
                }
            } else if (shotsleft <= 0) {
                stack.e.a("Reloading", 1);
                stack.a(1, (of)player);
            } else {
                stack.e.a("ShotsLeft", shotsleft);
            }
        }
    }

    public void onUsingItemTick(ye stack, uf player, int count) {
        int ticks;
        if (!player.q.I && (ticks = this.d_(stack) - count) % 6 == 0) {
            int shotsleft = stack.e.e("ShotsLeft") - ticks / 6;
            if (!player.bG.d) {
                if (stack.e.e("Reloading") == 1 && player.bn.e(CustomItems.bulletBlack.cv)) {
                    if (ticks > 0 && ticks <= 24) {
                        player.q.a((nn)player, "customnpcs:gun.ak47chamberround", 1.0f, 1.0f);
                    }
                    return;
                }
                if (shotsleft <= 0 || !player.bn.e(CustomItems.bulletBlack.cv)) {
                    player.q.a((nn)player, "customnpcs:gun.empty", 1.0f, 1.0f);
                    return;
                }
            }
            EntityProjectile projectile = new EntityProjectile(player.q, (of)player, new ye(CustomItems.bulletBlack, 1, 0), false);
            projectile.damage = 4.0f;
            projectile.setSpeed(40);
            projectile.shoot(2.0f);
            if (!player.bG.d) {
                player.bn.d(CustomItems.bulletBlack.cv);
            }
            player.q.a((nn)player, "customnpcs:gun.pistolshot", 0.9f, f.nextFloat() * 0.3f + 0.8f);
            player.q.d((nn)projectile);
        }
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.7f, (float)0.7f, (float)0.7f);
        GL11.glTranslatef((float)0.0f, (float)0.2f, (float)0.0f);
    }

    public ye a(ye stack, abw world, uf player) {
        if (stack.e == null) {
            stack.e = new by();
        }
        if (!player.bG.d && !player.bn.e(CustomItems.bulletBlack.cv)) {
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

