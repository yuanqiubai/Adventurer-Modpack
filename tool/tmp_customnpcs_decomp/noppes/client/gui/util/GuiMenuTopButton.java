/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  aut
 *  avi
 *  bjo
 *  bu
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.gui.util;

import noppes.client.gui.util.IButtonListener;
import org.lwjgl.opengl.GL11;

public class GuiMenuTopButton
extends aut {
    public static final bjo resource = new bjo("customnpcs", "textures/gui/menutopbutton.png");
    public boolean active = false;
    public boolean hover = false;
    public boolean rotated = false;
    public IButtonListener listener;
    protected int c;

    public GuiMenuTopButton(int i, int j, int k, String s) {
        super(i, j, k, bu.a((String)s));
        this.b = atv.w().l.a(this.f) + 12;
        this.c = 20;
    }

    public GuiMenuTopButton(int i, GuiMenuTopButton parent, String s) {
        this(i, parent.d + parent.b, parent.e, s);
    }

    public GuiMenuTopButton(int i, GuiMenuTopButton parent, String s, IButtonListener listener) {
        this(i, parent, s);
        this.listener = listener;
    }

    protected int a(boolean flag) {
        int byte0 = 1;
        if (this.active) {
            byte0 = 0;
        } else if (flag) {
            byte0 = 2;
        }
        return byte0;
    }

    public void a(atv minecraft, int i, int j) {
        if (this.i) {
            GL11.glPushMatrix();
            minecraft.N.a(resource);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int height = this.c - (this.active ? 0 : 2);
            this.hover = i >= this.d && j >= this.e && i < this.d + this.b && j < this.e + height;
            int k = this.a(this.hover);
            this.b(this.d, this.e, 0, k * 20, this.b / 2, height);
            this.b(this.d + this.b / 2, this.e, 200 - this.b / 2, k * 20, this.b / 2, height);
            this.b(minecraft, i, j);
            avi fontrenderer = minecraft.l;
            if (this.rotated) {
                GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (this.active) {
                this.a(fontrenderer, this.f, this.d + this.b / 2, this.e + (height - 8) / 2, 0xFFFFA0);
            } else if (this.hover) {
                this.a(fontrenderer, this.f, this.d + this.b / 2, this.e + (height - 8) / 2, 0xFFFFA0);
            } else {
                this.a(fontrenderer, this.f, this.d + this.b / 2, this.e + (height - 8) / 2, 0xE0E0E0);
            }
            GL11.glPopMatrix();
        }
    }

    protected void b(atv minecraft, int i, int j) {
    }

    public void a(int i, int j) {
    }

    public boolean c(atv minecraft, int i, int j) {
        boolean bo;
        boolean bl = bo = !this.active && this.i && this.hover;
        if (bo && this.listener != null) {
            this.listener.actionPerformed(this);
            return false;
        }
        return bo;
    }

    public int getWidth() {
        return this.b;
    }
}

