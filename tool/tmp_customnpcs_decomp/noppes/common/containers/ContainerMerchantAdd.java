/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abk
 *  abw
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  mo
 *  mu
 *  ud
 *  uf
 *  uy
 *  vi
 *  we
 *  ye
 */
package noppes.common.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMerchantAdd
extends uy {
    private final abw theWorld;
    private final abk theMerchant;
    private final mu merchantInventory;

    public ContainerMerchantAdd(ud par1InventoryPlayer, abk par2IMerchant, abw par3World) {
        int i;
        this.theMerchant = par2IMerchant;
        this.theWorld = par3World;
        this.merchantInventory = new mu("", false, 3);
        this.a(new we((mo)this.merchantInventory, 0, 36, 53));
        this.a(new we((mo)this.merchantInventory, 1, 62, 53));
        this.a(new we((mo)this.merchantInventory, 2, 120, 53));
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new we((mo)par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.a(new we((mo)par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    public void a(vi par1ICrafting) {
        super.a(par1ICrafting);
    }

    public void b() {
        super.b();
    }

    public void a(mo par1IInventory) {
        super.a(par1IInventory);
    }

    public void setCurrentRecipeIndex(int par1) {
    }

    @SideOnly(value=Side.CLIENT)
    public void b(int par1, int par2) {
    }

    public boolean a(uf par1EntityPlayer) {
        return true;
    }

    public ye b(uf par1EntityPlayer, int par2) {
        ye itemstack = null;
        we slot = (we)this.c.get(par2);
        if (slot != null && slot.e()) {
            ye itemstack1 = slot.d();
            itemstack = itemstack1.m();
            if (par2 != 0 && par2 != 1 && par2 != 2 ? (par2 >= 3 && par2 < 30 ? !this.a(itemstack1, 30, 39, false) : par2 >= 30 && par2 < 39 && !this.a(itemstack1, 3, 30, false)) : !this.a(itemstack1, 3, 39, false)) {
                return null;
            }
            if (itemstack1.b == 0) {
                slot.c(null);
            } else {
                slot.f();
            }
            if (itemstack1.b == itemstack.b) {
                return null;
            }
            slot.a(par1EntityPlayer, itemstack1);
        }
        return itemstack;
    }

    public void b(uf par1EntityPlayer) {
        super.b(par1EntityPlayer);
        this.theMerchant.a_(null);
        super.b(par1EntityPlayer);
        if (!this.theWorld.I) {
            ye itemstack = this.merchantInventory.a_(0);
            if (itemstack != null) {
                par1EntityPlayer.b(itemstack);
            }
            if ((itemstack = this.merchantInventory.a_(1)) != null) {
                par1EntityPlayer.b(itemstack);
            }
        }
    }
}

