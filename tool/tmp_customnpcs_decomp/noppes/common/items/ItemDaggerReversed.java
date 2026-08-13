/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mt
 *  org.lwjgl.opengl.GL11
 *  yd
 */
package noppes.common.items;

import noppes.common.items.ItemDagger;
import org.lwjgl.opengl.GL11;

public class ItemDaggerReversed
extends ItemDagger {
    private final ItemDagger dagger;

    public ItemDaggerReversed(int par1, ItemDagger dagger, yd tool) {
        super(par1, tool);
        this.dagger = dagger;
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
        GL11.glTranslatef((float)-0.26f, (float)0.5f, (float)0.26f);
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)1.0f);
    }

    @Override
    public boolean o_() {
        return true;
    }

    public void a(mt par1IconRegister) {
        this.cz = this.dagger.b_(0);
    }
}

