/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  mt
 *  uf
 *  yc
 *  ye
 */
package noppes.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.CustomNpcs;
import noppes.common.config.CustomNPCConfig;
import noppes.common.constants.EnumGuiType;
import noppes.common.objcenter.CustomItems;

public class ItemNpcSpawner
extends yc {
    public ItemNpcSpawner(int i) {
        super(i - 26700 + CustomNPCConfig.ItemStartId);
        this.cw = 1;
        this.a(CustomItems.tab);
    }

    public boolean a(ye par1ItemStack, uf par2EntityPlayer, abw par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par3World.I) {
            CustomNpcs.proxy.openGui(par4, par5, par6, EnumGuiType.MobSpawner, par2EntityPlayer);
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
        this.cz = yc.h.b_(0);
    }

    public yc b(String name) {
        GameRegistry.registerItem((yc)this, (String)name);
        return super.b(name);
    }
}

