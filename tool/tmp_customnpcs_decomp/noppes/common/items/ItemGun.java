/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
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
import noppes.common.items.EnumNpcToolMaterial;
import noppes.common.items.ItemBullet;
import noppes.common.items.ItemGun$1;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemGun
extends ItemNpcInterface {
    private final EnumNpcToolMaterial material;

    public ItemGun(int par1, EnumNpcToolMaterial material) {
        super(par1);
        this.cw = 1;
        this.material = material;
        this.e(material.getMaxUses());
        this.a(CustomItems.tabWeapon);
    }

    public void a(ye par1ItemStack, abw worldObj, uf player, int par4) {
        if (!worldObj.I) {
            if (!this.hasBullet(player)) {
                worldObj.a((nn)player, "customnpcs:gun.empty", 1.0f, 1.0f);
            } else {
                int ticks = this.d_(par1ItemStack) - par4;
                if (ticks >= 10) {
                    par1ItemStack.a(1, (of)player);
                    ItemBullet bullet = (ItemBullet)this.getBullet(player);
                    int damage = (bullet.getBulletDamage() + this.material.getDamageVsEntity() + 1) / 2 + 5;
                    EntityProjectile projectile = new EntityProjectile(worldObj, (of)player, new ye(this.getBullet(player)), false);
                    projectile.damage = damage;
                    projectile.setSpeed(40);
                    projectile.shoot(this.material.getDamageVsEntity() + 1);
                    if (!player.bG.d) {
                        player.bn.d(this.getBullet((uf)player).cv);
                    }
                    worldObj.a((nn)player, "customnpcs:gun.pistolshot", 1.0f, f.nextFloat() * 0.3f + 0.8f);
                    worldObj.d((nn)projectile);
                }
            }
        }
    }

    public void onUsingItemTick(ye stack, uf player, int count) {
        int ticks = this.d_(stack) - count;
        if (ticks == 8 && !player.q.I) {
            player.q.a((nn)player, "customnpcs:gun.pistoltrigger", 1.0f, 1.0f / (player.q.s.nextFloat() * 0.4f + 0.8f));
        }
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.7f, (float)0.7f, (float)0.7f);
        GL11.glTranslatef((float)0.0f, (float)0.3f, (float)0.0f);
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        par3EntityPlayer.a(par1ItemStack, this.d_(par1ItemStack));
        return par1ItemStack;
    }

    public int d_(ye par1ItemStack) {
        return 72000;
    }

    private boolean hasBullet(uf par3EntityPlayer) {
        yc bullet = this.getBullet(par3EntityPlayer);
        return bullet != null && bullet.cv >= 0;
    }

    private yc getBullet(uf par3EntityPlayer) {
        switch (ItemGun$1.$SwitchMap$noppes$npcs$items$EnumNpcToolMaterial[this.material.ordinal()]) {
            case 1: {
                if (par3EntityPlayer.bn.e(CustomItems.bulletEmerald.cv)) {
                    return CustomItems.bulletEmerald;
                }
            }
            case 2: {
                if (par3EntityPlayer.bn.e(CustomItems.bulletDiamond.cv)) {
                    return CustomItems.bulletDiamond;
                }
            }
            case 3: {
                if (par3EntityPlayer.bn.e(CustomItems.bulletIron.cv)) {
                    return CustomItems.bulletIron;
                }
            }
            case 4: {
                if (par3EntityPlayer.bn.e(CustomItems.bulletBronze.cv)) {
                    return CustomItems.bulletBronze;
                }
            }
            case 5: {
                if (par3EntityPlayer.bn.e(CustomItems.bulletGold.cv)) {
                    return CustomItems.bulletGold;
                }
            }
            case 6: {
                if (par3EntityPlayer.bn.e(CustomItems.bulletStone.cv)) {
                    return CustomItems.bulletStone;
                }
            }
            case 7: {
                if (!par3EntityPlayer.bn.e(CustomItems.bulletWood.cv)) break;
                return CustomItems.bulletWood;
            }
        }
        return !par3EntityPlayer.bn.e(CustomItems.bulletBlack.cv) && !par3EntityPlayer.bG.d ? null : CustomItems.bulletBlack;
    }

    public zj c_(ye par1ItemStack) {
        return zj.e;
    }
}

