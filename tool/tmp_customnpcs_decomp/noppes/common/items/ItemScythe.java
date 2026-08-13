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

public class ItemScythe
extends ItemNpcWeaponInterface {
    public ItemScythe(int par1, yd tool) {
        super(par1, tool);
    }

    @Override
    public void renderSpecial() {
        GL11.glTranslatef((float)-0.1f, (float)0.0f, (float)0.1f);
    }

    public boolean o_() {
        return true;
    }
}

