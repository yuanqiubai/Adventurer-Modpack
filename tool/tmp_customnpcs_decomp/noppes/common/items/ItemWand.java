/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  ye
 */
package noppes.common.items;

import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemWand
extends ItemNpcInterface {
    public ItemWand(int par1) {
        super(par1);
        this.a(CustomItems.tabMisc);
    }

    public boolean e(ye par1ItemStack) {
        return true;
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.54f, (float)0.54f, (float)0.54f);
        GL11.glTranslatef((float)0.0f, (float)0.4f, (float)-0.04f);
    }
}

