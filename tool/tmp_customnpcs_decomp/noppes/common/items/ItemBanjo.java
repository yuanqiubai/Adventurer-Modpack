/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package noppes.common.items;

import noppes.common.items.ItemMusic;
import org.lwjgl.opengl.GL11;

public class ItemBanjo
extends ItemMusic {
    public ItemBanjo(int par1) {
        super(par1);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.85f, (float)0.85f, (float)0.85f);
        GL11.glTranslatef((float)0.0f, (float)0.4f, (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)-1.0f, (float)0.0f, (float)1.0f);
    }
}

