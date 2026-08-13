/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atu
 *  bbo
 *  bcu
 *  bfj
 *  bfq
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class Model2DRenderer
extends bcu {
    private boolean t;
    private int u;
    private final float x1;
    private final float x2;
    private final float y1;
    private final float y2;
    private final int width;
    private final int height;
    private float rotationOffsetX;
    private float rotationOffsetY;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float thickness = 1.0f;

    public Model2DRenderer(bbo par1ModelBase, float x, float y, int width, int height, float textureWidth, float textureHeight) {
        super(par1ModelBase);
        this.width = width;
        this.height = height;
        this.a = textureWidth;
        this.b = textureHeight;
        this.x1 = x / textureWidth;
        this.y1 = y / textureHeight;
        this.x2 = (x + (float)width) / textureWidth;
        this.y2 = (y + (float)height) / textureHeight;
    }

    public Model2DRenderer(bbo base, int x, int y, int width, int height) {
        this(base, x, y, width, height, width, height);
    }

    public void a(float par1) {
        if (this.j && !this.k) {
            if (!this.t) {
                this.d(par1);
            }
            GL11.glPushMatrix();
            this.c(par1);
            GL11.glCallList((int)this.u);
            GL11.glPopMatrix();
        }
    }

    public void setRotationOffset(float x, float y) {
        this.rotationOffsetX = x;
        this.rotationOffsetY = y;
    }

    public void setScale(float scale) {
        this.scaleX = scale;
        this.scaleY = scale;
    }

    public void setScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    @SideOnly(value=Side.CLIENT)
    private void d(float par1) {
        this.u = atu.a((int)1);
        GL11.glNewList((int)this.u, (int)4864);
        GL11.glScalef((float)(this.scaleX * (float)this.width / (float)this.height), (float)this.scaleY, (float)this.thickness);
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        if (this.i) {
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(-1.0f * par1));
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        }
        GL11.glTranslated((double)(this.rotationOffsetX * par1), (double)(this.rotationOffsetY * par1), (double)0.0);
        bfj.a((bfq)bfq.a, (float)this.x1, (float)this.y1, (float)this.x2, (float)this.y2, (int)this.width, (int)this.height, (float)par1);
        GL11.glEndList();
        this.t = true;
    }
}

