/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  awe
 *  by
 *  uf
 *  yc
 *  ye
 */
package noppes.common.items;

import noppes.common.config.CustomNPCConfig;
import noppes.common.items.MoneyBagContents;
import noppes.common.objcenter.CustomItems;
import noppes.util.NoppesUtil;

public class ItemMoneyBag
extends yc {
    public ItemMoneyBag(int i) {
        super(i - 26700 + CustomNPCConfig.ItemStartId);
        this.cw = 1;
        this.a(CustomItems.tab);
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        if (par2World.I) {
            return par1ItemStack;
        }
        if (par1ItemStack.e == null) {
            par1ItemStack.e = new by();
        }
        new MoneyBagContents(par3EntityPlayer);
        NoppesUtil.openGUI(par3EntityPlayer, new awe());
        return par1ItemStack;
    }
}

