/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  org.lwjgl.opengl.GL11
 *  uf
 *  ye
 *  zj
 */
package noppes.common.items;

import noppes.common.items.EnumNpcToolMaterial;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemShield
extends ItemNpcInterface {
    public ItemShield(int par1, EnumNpcToolMaterial material) {
        super(par1);
        this.e(material.getMaxUses());
        this.a(CustomItems.tabWeapon);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.26f);
        GL11.glRotatef((float)-6.0f, (float)0.0f, (float)1.0f, (float)0.0f);
    }

    public zj c_(ye par1ItemStack) {
        return zj.d;
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        par3EntityPlayer.a(par1ItemStack, this.d_(par1ItemStack));
        return par1ItemStack;
    }

    public int d_(ye par1ItemStack) {
        return 72000;
    }
}

