/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atu
 *  bbo
 *  bcp
 *  bcu
 *  bfq
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model.util;

import noppes.common.entity.model_data.ModelPartConfig;
import org.lwjgl.opengl.GL11;

public class ModelScaleRenderer
extends bcu {
    public boolean t;
    public int u;
    public float x;
    public float y;
    public float z;
    protected ModelPartConfig config;

    public ModelScaleRenderer(bbo par1ModelBase) {
        super(par1ModelBase);
    }

    public ModelScaleRenderer(bbo par1ModelBase, int par2, int par3) {
        this(par1ModelBase);
        this.a(par2, par3);
    }

    public void setConfig(ModelPartConfig config, float x, float y, float z) {
        this.config = config;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }

    public void renderChilderen(float par1) {
        if (this.j && !this.k) {
            if (!this.t) {
                this.d(par1);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)this.x, (float)this.y, (float)this.z);
            GL11.glTranslatef((float)this.config.transX, (float)this.config.transY, (float)this.config.transZ);
            this.c(par1);
            GL11.glScalef((float)this.config.scaleX, (float)this.config.scaleY, (float)this.config.scaleZ);
            GL11.glCallList((int)this.u);
            if (this.m != null) {
                for (int i = 0; i < this.m.size(); ++i) {
                    ((bcu)this.m.get(i)).a(par1);
                }
            }
            GL11.glPopMatrix();
        }
    }

    public void renderChild(float par1, bcu model) {
        if (this.j && !this.k) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)this.x, (float)this.y, (float)this.z);
            GL11.glTranslatef((float)this.config.transX, (float)this.config.transY, (float)this.config.transZ);
            this.c(par1);
            GL11.glScalef((float)this.config.scaleX, (float)this.config.scaleY, (float)this.config.scaleZ);
            model.a(par1);
            GL11.glPopMatrix();
        }
    }

    public void a(float par1) {
        if (this.j && !this.k) {
            if (!this.t) {
                this.d(par1);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)this.x, (float)this.y, (float)this.z);
            GL11.glTranslatef((float)this.config.transX, (float)this.config.transY, (float)this.config.transZ);
            this.c(par1);
            GL11.glScalef((float)this.config.scaleX, (float)this.config.scaleY, (float)this.config.scaleZ);
            GL11.glCallList((int)this.u);
            if (this.m != null) {
                for (int i = 0; i < this.m.size(); ++i) {
                    ((bcu)this.m.get(i)).a(par1);
                }
            }
            GL11.glPopMatrix();
        }
    }

    public void parentRender(float par1) {
        super.a(par1);
    }

    public void d(float par1) {
        this.u = atu.a((int)1);
        GL11.glNewList((int)this.u, (int)4864);
        bfq tessellator = bfq.a;
        for (int i = 0; i < this.l.size(); ++i) {
            ((bcp)this.l.get(i)).a(tessellator, par1);
        }
        GL11.glEndList();
        this.t = true;
    }
}

