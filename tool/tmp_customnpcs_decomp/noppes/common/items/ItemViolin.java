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

import noppes.common.items.ItemMusic;
import org.lwjgl.opengl.GL11;

public class ItemViolin
extends ItemMusic {
    public ItemViolin(int par1) {
        super(par1);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.66f, (float)0.66f, (float)0.66f);
        GL11.glRotatef((float)-80.0f, (float)1.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)-0.2f, (float)-0.9f, (float)-0.7f);
    }

    public zj c_(ye par1ItemStack) {
        return zj.e;
    }

    @Override
    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        par3EntityPlayer.a(par1ItemStack, this.d_(par1ItemStack));
        return super.a(par1ItemStack, par2World, par3EntityPlayer);
    }

    public int d_(ye par1ItemStack) {
        return 72000;
    }
}

