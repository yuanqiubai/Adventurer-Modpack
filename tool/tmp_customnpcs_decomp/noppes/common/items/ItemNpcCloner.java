/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  mt
 *  uf
 *  yc
 *  ye
 */
package noppes.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.CustomNpcs;
import noppes.common.config.CustomNPCConfig;
import noppes.common.constants.EnumGuiType;
import noppes.common.objcenter.CustomItems;

public class ItemNpcCloner
extends yc {
    public ItemNpcCloner(int i) {
        super(i - 26700 + CustomNPCConfig.ItemStartId);
        this.cw = 1;
        this.a(CustomItems.tab);
    }

    public boolean a(ye item, uf player, abw world, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (world.I) {
            CustomNpcs.proxy.openGui(par4, par5, par6, EnumGuiType.MobSpawner, player);
        }
        return true;
    }

    public int a(ye par1ItemStack, int par2) {
        return 9127187;
    }

    public boolean b() {
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt par1IconRegister) {
        this.cz = yc.j.b_(0);
    }
}

