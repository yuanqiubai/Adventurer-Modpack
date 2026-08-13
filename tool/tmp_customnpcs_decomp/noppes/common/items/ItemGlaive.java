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

public class ItemGlaive
extends ItemNpcWeaponInterface {
    public ItemGlaive(int par1, yd tool) {
        super(par1, tool);
    }

    @Override
    public void renderSpecial() {
        GL11.glTranslatef((float)0.16f, (float)-0.34f, (float)-0.14f);
    }
}

