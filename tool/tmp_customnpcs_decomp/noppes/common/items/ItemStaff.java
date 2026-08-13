/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  ls
 *  nn
 *  of
 *  uf
 *  ye
 *  zj
 */
package noppes.common.items;

import noppes.CustomNpcs;
import noppes.common.entity.EntityProjectile;
import noppes.common.entity.model_entity.EntityMagicProjectile;
import noppes.common.items.EnumNpcToolMaterial;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;

public class ItemStaff
extends ItemNpcInterface {
    private final EnumNpcToolMaterial material;

    public ItemStaff(int par1, EnumNpcToolMaterial material) {
        super(par1);
        this.material = material;
        this.a(CustomItems.tabWeapon);
    }

    @Override
    public void renderSpecial() {
    }

    public void a(ye stack, abw worldObj, uf player, int par4) {
        nn entity;
        if (!worldObj.I && stack.e != null && (entity = player.q.a(stack.e.e("MagicProjectile"))) != null && entity instanceof EntityProjectile) {
            EntityProjectile item = (EntityProjectile)entity;
            item.C = item.A = player.A;
            item.D = item.B = player.B;
            item.shoot(2.0f);
            player.q.a((nn)player, "customnpcs:magic.shot", 1.0f, 1.0f);
        }
    }

    public void onUsingItemTick(ye stack, uf player, int count) {
        int tick = this.d_(stack) - count;
        if (player.q.I) {
            this.spawnParticle(stack, player);
        } else {
            double dz2;
            double dx;
            int chargeTime = 20 + this.material.getHarvestLevel() * 8;
            if (tick == chargeTime) {
                if (!player.bG.d) {
                    if (!player.bn.e(CustomItems.mana.cv)) {
                        return;
                    }
                    player.bn.d(CustomItems.mana.cv);
                }
                player.q.a((nn)player, "customnpcs:magic.charge", 1.0f, 1.0f);
                if (stack.e == null) {
                    stack.e = new by();
                }
                int entity = 6 + this.material.getDamageVsEntity() + player.q.s.nextInt(4);
                EntityMagicProjectile item = new EntityMagicProjectile(player.q, player, this.getProjectile(stack), false);
                item.damage = entity;
                item.setSpeed(25);
                dx = -ls.a((float)((float)((double)(player.A / 180.0f) * Math.PI))) * ls.b((float)((float)((double)(player.B / 180.0f) * Math.PI)));
                dz2 = ls.b((float)((float)((double)(player.A / 180.0f) * Math.PI))) * ls.b((float)((float)((double)(player.B / 180.0f) * Math.PI)));
                item.b(player.u + dx * 0.8, player.v + 1.5 - (double)(player.B / 40.0f), player.w + dz2 * 0.8);
                player.q.d((nn)item);
                stack.e.a("MagicProjectile", item.k);
            }
            if (tick > chargeTime && stack.e != null) {
                nn entity1 = player.q.a(stack.e.e("MagicProjectile"));
                if (entity1 == null || !(entity1 instanceof EntityProjectile)) {
                    return;
                }
                EntityProjectile item1 = (EntityProjectile)entity1;
                item1.ticksInAir = 0;
                dx = -ls.a((float)((float)((double)(player.A / 180.0f) * Math.PI))) * ls.b((float)((float)((double)(player.B / 180.0f) * Math.PI)));
                dz2 = ls.b((float)((float)((double)(player.A / 180.0f) * Math.PI))) * ls.b((float)((float)((double)(player.B / 180.0f) * Math.PI)));
                item1.b(player.u + dx * 0.8, player.v + 1.5 - (double)(player.B / 40.0f), player.w + dz2 * 0.8);
            }
        }
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

    public ye getProjectile(ye stack) {
        return stack.b() == CustomItems.staffWood ? new ye(CustomItems.spellNature) : (stack.b() == CustomItems.staffStone ? new ye(CustomItems.spellDark) : (stack.b() == CustomItems.staffIron ? new ye(CustomItems.spellHoly) : (stack.b() == CustomItems.staffBronze ? new ye(CustomItems.spellLightning) : (stack.b() == CustomItems.staffGold ? new ye(CustomItems.spellFire) : (stack.b() == CustomItems.staffDiamond ? new ye(CustomItems.spellIce) : (stack.b() == CustomItems.staffEmerald ? new ye(CustomItems.spellArcane) : new ye(CustomItems.orb, 1, stack.k())))))));
    }

    public void spawnParticle(ye stack, uf player) {
        if (stack.b() == CustomItems.staffWood) {
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 5, 2);
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 12, 2);
        }
        if (stack.b() == CustomItems.staffStone) {
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 5649239, 2);
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 4400964, 2);
        }
        if (stack.b() == CustomItems.staffBronze) {
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 8648694, 2);
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 6091007, 2);
        }
        if (stack.b() == CustomItems.staffIron) {
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 0xFCFFC9, 2);
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 15728535, 2);
        }
        if (stack.b() == CustomItems.staffGold) {
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 1, 2);
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 14, 2);
        }
        if (stack.b() == CustomItems.staffDiamond) {
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 9756653, 2);
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 4503295, 2);
        }
        if (stack.b() == CustomItems.staffEmerald) {
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 16761831, 2);
            CustomNpcs.proxy.spawnParticle((of)player, "Spell", 16487167, 2);
        }
    }
}

