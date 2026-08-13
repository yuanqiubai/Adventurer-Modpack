/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package noppes.common.items;

import noppes.common.items.EnumNpcToolMaterial;
import noppes.common.items.ItemShield;
import org.lwjgl.opengl.GL11;

public class ItemRotatedShield
extends ItemShield {
    public ItemRotatedShield(int par1, EnumNpcToolMaterial material) {
        super(par1, material);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glTranslatef((float)-0.1f, (float)1.0f, (float)-0.18f);
        GL11.glRotatef((float)120.0f, (float)1.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)-6.0f, (float)0.0f, (float)1.0f, (float)0.0f);
    }
}

