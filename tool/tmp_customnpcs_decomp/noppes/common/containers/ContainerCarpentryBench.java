/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  dz
 *  ey
 *  jv
 *  mo
 *  ud
 *  uf
 *  uy
 *  vk
 *  wc
 *  wd
 *  we
 *  ye
 */
package noppes.common.containers;

import noppes.common.controllers.RecipeCarpentry;
import noppes.common.controllers.RecipeController;
import noppes.common.objcenter.CustomItems;

public class ContainerCarpentryBench
extends uy {
    public vk craftMatrix = new vk((uy)this, 4, 4);
    public mo craftResult = new wc();
    private final uf player;
    private final abw worldObj;
    private final int posX;
    private final int posY;
    private final int posZ;

    public ContainerCarpentryBench(ud par1InventoryPlayer, abw par2World, int par3, int par4, int par5) {
        int var7;
        int var6;
        this.worldObj = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.player = par1InventoryPlayer.d;
        this.a((we)new wd(par1InventoryPlayer.d, (mo)this.craftMatrix, this.craftResult, 0, 132, 35));
        for (var6 = 0; var6 < 4; ++var6) {
            for (var7 = 0; var7 < 4; ++var7) {
                this.a(new we((mo)this.craftMatrix, var7 + var6 * 4, 17 + var7 * 18, 8 + var6 * 18));
            }
        }
        for (var6 = 0; var6 < 3; ++var6) {
            for (var7 = 0; var7 < 9; ++var7) {
                this.a(new we((mo)par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }
        for (var6 = 0; var6 < 9; ++var6) {
            this.a(new we((mo)par1InventoryPlayer, var6, 8 + var6 * 18, 142));
        }
        this.a((mo)this.craftMatrix);
    }

    public void a(mo par1IInventory) {
        if (!this.worldObj.I) {
            RecipeCarpentry recipe = RecipeController.instance.findMatchingRecipe(this.craftMatrix);
            ye item = null;
            if (recipe != null && recipe.availability.isAvailable(this.player)) {
                item = recipe.a(this.craftMatrix);
            }
            this.craftResult.a(0, item);
            jv plmp = (jv)this.player;
            plmp.a.b((ey)new dz(this.d, 0, item));
        }
    }

    public void b(uf par1EntityPlayer) {
        super.b(par1EntityPlayer);
        if (!this.worldObj.I) {
            for (int var2 = 0; var2 < 16; ++var2) {
                ye var3 = this.craftMatrix.a_(var2);
                if (var3 == null) continue;
                par1EntityPlayer.b(var3);
            }
        }
    }

    public boolean a(uf par1EntityPlayer) {
        return this.worldObj.a(this.posX, this.posY, this.posZ) == CustomItems.carpentyBench.cF && par1EntityPlayer.e((double)this.posX + 0.5, (double)this.posY + 0.5, (double)this.posZ + 0.5) <= 64.0;
    }

    public ye b(uf par1EntityPlayer, int par1) {
        ye var2 = null;
        we var3 = (we)this.c.get(par1);
        if (var3 != null && var3.e()) {
            ye var4 = var3.d();
            var2 = var4.m();
            if (par1 == 0) {
                if (!this.a(var4, 17, 53, true)) {
                    return null;
                }
                var3.a(var4, var2);
            } else if (par1 >= 17 && par1 < 44 ? !this.a(var4, 44, 53, false) : (par1 >= 44 && par1 < 53 ? !this.a(var4, 17, 44, false) : !this.a(var4, 17, 53, false))) {
                return null;
            }
            if (var4.b == 0) {
                var3.c(null);
            } else {
                var3.f();
            }
            if (var4.b == var2.b) {
                return null;
            }
            var3.a(par1EntityPlayer, var4);
        }
        return var2;
    }
}

