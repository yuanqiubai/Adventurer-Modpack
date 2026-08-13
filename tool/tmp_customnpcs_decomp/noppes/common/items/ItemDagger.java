/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  yd
 */
package noppes.common.items;

import noppes.common.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemDagger
extends ItemNpcWeaponInterface {
    public ItemDagger(int par1, yd tool) {
        super(par1, tool);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glTranslatef((float)-0.05f, (float)0.32f, (float)0.05f);
    }

    public boolean o_() {
        return true;
    }
}

