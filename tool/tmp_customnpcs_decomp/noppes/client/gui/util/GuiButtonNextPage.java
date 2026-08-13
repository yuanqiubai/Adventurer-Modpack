/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  aut
 *  bjo
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.util;

import org.lwjgl.opengl.GL11;

public class GuiButtonNextPage
extends aut {
    private static final bjo bookGuiTextures = new bjo("textures/gui/book.png");
    private final boolean nextPage;

    public GuiButtonNextPage(int par1, int par2, int par3, boolean par4) {
        super(par1, par2, par3, 23, 13, "");
        this.nextPage = par4;
    }

    public void a(atv par1Minecraft, int par2, int par3) {
        if (this.i) {
            boolean var4 = par2 >= this.d && par3 >= this.e && par2 < this.d + this.b && par3 < this.e + this.c;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            par1Minecraft.N.a(bookGuiTextures);
            int var5 = 0;
            int var6 = 192;
            if (var4) {
                var5 += 23;
            }
            if (!this.nextPage) {
                var6 += 13;
            }
            this.b(this.d, this.e, var5, var6, 23, 13);
        }
    }
}

