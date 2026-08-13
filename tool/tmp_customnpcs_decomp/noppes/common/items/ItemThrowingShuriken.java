/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package noppes.common.items;

import noppes.common.items.ItemThrowingWeapon;
import org.lwjgl.opengl.GL11;

public class ItemThrowingShuriken
extends ItemThrowingWeapon {
    public ItemThrowingShuriken(int par1) {
        super(par1);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glTranslatef((float)-0.25f, (float)0.2f, (float)0.3f);
    }

    public boolean o_() {
        return true;
    }
}

