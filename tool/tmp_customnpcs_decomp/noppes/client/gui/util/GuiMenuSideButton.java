/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  avi
 *  bjo
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.util;

import noppes.client.gui.util.GuiNpcButton;
import org.lwjgl.opengl.GL11;

public class GuiMenuSideButton
extends GuiNpcButton {
    public static final bjo resource = new bjo("customnpcs", "textures/gui/menusidebutton.png");
    public int d;
    public int e;
    public String f;
    public int g;
    public boolean active = false;
    public boolean i = true;
    public boolean hover = false;
    protected int b = 200;
    protected int c = 20;

    public GuiMenuSideButton(int i, int j, int k, String s) {
        this(i, j, k, 200, 20, s);
    }

    public GuiMenuSideButton(int i, int j, int k, int l, int i1, String s) {
        super(i, j, k, l, i1, s);
        this.g = i;
        this.d = j;
        this.e = k;
        this.b = l;
        this.c = i1;
        this.f = s;
    }

    protected int a(boolean flag) {
        int byte0 = 1;
        if (this.active) {
            byte0 = 0;
        } else if (flag) {
            byte0 = 1;
        }
        return byte0;
    }

    @Override
    public void a(atv minecraft, int i, int j) {
        if (this.i) {
            avi fontrenderer = minecraft.l;
            minecraft.N.a(resource);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int width = this.b + (this.active ? 2 : 0);
            this.hover = i >= this.d && j >= this.e && i < this.d + width && j < this.e + this.c;
            int k = this.a(this.hover);
            this.b(this.d, this.e, 0, k * 22, width, this.c);
            this.b(minecraft, i, j);
            if (this.active) {
                this.a(fontrenderer, this.f, this.d + width / 2, this.e + (this.c - 8) / 2, 0xFFFFA0);
            } else if (this.hover) {
                this.a(fontrenderer, this.f, this.d + width / 2, this.e + (this.c - 8) / 2, 0xFFFFA0);
            } else {
                this.a(fontrenderer, this.f, this.d + width / 2, this.e + (this.c - 8) / 2, 0xE0E0E0);
            }
        }
    }

    protected void b(atv minecraft, int i, int j) {
    }

    public void a(int i, int j) {
    }

    @Override
    public boolean c(atv minecraft, int i, int j) {
        return !this.active && this.i && this.hover;
    }
}

