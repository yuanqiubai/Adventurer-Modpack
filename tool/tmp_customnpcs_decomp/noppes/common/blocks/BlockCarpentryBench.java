/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  akc
 *  amw
 *  asp
 *  ls
 *  of
 *  uf
 *  ye
 */
package noppes.common.blocks;

import noppes.CustomNpcs;
import noppes.common.blocks.TileBlockAnvil;
import noppes.common.constants.EnumGuiType;
import noppes.common.objcenter.CustomItems;

public class BlockCarpentryBench
extends amw {
    public BlockCarpentryBench(int par1) {
        super(par1, akc.f);
        this.a(CustomItems.tab);
    }

    public boolean a(abw par1World, int i, int j, int k, uf player, int par6, float par7, float par8, float par9) {
        if (!par1World.I) {
            player.openGui((Object)CustomNpcs.instance, EnumGuiType.PlayerAnvil.ordinal(), par1World, i, j, k);
        }
        return true;
    }

    public asp b(abw var1) {
        return new TileBlockAnvil();
    }

    public boolean c() {
        return false;
    }

    public int d() {
        return -1;
    }

    public boolean b() {
        return false;
    }

    public void a(abw par1World, int par2, int par3, int par4, of par5EntityLiving, ye item) {
        int var6 = ls.c((double)((double)(par5EntityLiving.A / 90.0f) + 0.5)) & 3;
        par1World.b(par2, par3, par4, var6, 2);
    }
}

