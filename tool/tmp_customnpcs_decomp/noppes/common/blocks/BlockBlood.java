/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  acf
 *  akc
 *  aqz
 *  asx
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ls
 *  ms
 *  mt
 *  of
 *  ye
 */
package noppes.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.common.objcenter.CustomItems;

public class BlockBlood
extends aqz {
    @SideOnly(value=Side.CLIENT)
    private ms furnaceIconTop;
    @SideOnly(value=Side.CLIENT)
    private ms furnaceIconFront;

    public BlockBlood(int par1) {
        super(par1, akc.e);
        this.r();
        this.a(CustomItems.tabMisc);
        this.a(0.01f, 0.01f, 0.01f, 0.99f, 0.99f, 0.99f);
        this.a(0.08f);
    }

    @SideOnly(value=Side.CLIENT)
    public ms a(int par1, int par2) {
        return par2 % 3 == 1 ? this.furnaceIconFront : (par2 % 3 == 2 ? this.furnaceIconTop : this.cW);
    }

    public asx b(abw world, int i, int j, int k) {
        return null;
    }

    public asx c_(abw par1World, int par2, int par3, int par4) {
        return asx.a().a((double)par2, (double)par3, (double)par4, (double)par2, (double)par3, (double)par4);
    }

    public boolean b() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt par1IconRegister) {
        this.cW = par1IconRegister.a(this.E());
        this.furnaceIconFront = par1IconRegister.a(this.E() + "2");
        this.furnaceIconTop = par1IconRegister.a(this.E() + "3");
    }

    public boolean a(acf world, int par2, int par3, int par4, int par5) {
        aqz block = aqz.s[world.a(par2, par3, par4)];
        return block != null && block.b();
    }

    public boolean c() {
        return false;
    }

    public int n() {
        return 1;
    }

    public void a(abw par1World, int par2, int par3, int par4, of par5EntityLiving, ye item) {
        int var6 = ls.c((double)((double)(par5EntityLiving.A / 90.0f) + 0.5)) & 3;
        par1World.b(par2, par3, par4, var6, 2);
    }
}

