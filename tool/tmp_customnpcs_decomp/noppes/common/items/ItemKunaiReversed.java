/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mt
 *  org.lwjgl.opengl.GL11
 *  yd
 */
package noppes.common.items;

import noppes.common.items.ItemKunai;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemKunaiReversed
extends ItemKunai {
    public ItemKunaiReversed(int par1, yd tool) {
        super(par1, tool);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.4f, (float)0.4f, (float)0.4f);
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)0.4f, (float)-0.9f, (float)-0.4f);
    }

    public void a(mt par1IconRegister) {
        this.cz = CustomItems.kunai.b_(0);
    }
}

