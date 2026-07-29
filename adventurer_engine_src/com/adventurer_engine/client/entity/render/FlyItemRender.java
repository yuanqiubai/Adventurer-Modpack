/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bfq
 *  bgm
 *  bik
 *  bjo
 *  ms
 *  nn
 *  org.lwjgl.opengl.GL11
 *  yc
 */
package com.adventurer_engine.client.entity.render;

import com.adventurer_engine.common.entity.projectiles.EntityBulletModelItem;
import org.lwjgl.opengl.GL11;

public class FlyItemRender
extends bgm {
    public void a(nn par1Entity, double par2, double par4, double par6, float par8, float par9) {
        if (!(par1Entity instanceof EntityBulletModelItem)) {
            return;
        }
        EntityBulletModelItem bullet = (EntityBulletModelItem)par1Entity;
        int i = bullet.renderItemID;
        if (i == 0) {
            return;
        }
        ms icon = yc.g[i].b_(0);
        if (icon != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
            GL11.glEnable((int)32826);
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            this.b(par1Entity);
            bfq tessellator = bfq.a;
            this.renderItem(tessellator, icon);
            GL11.glDisable((int)32826);
            GL11.glPopMatrix();
        }
    }

    protected bjo a(nn entity) {
        return bik.c;
    }

    private void renderItem(bfq par1Tessellator, ms par2Icon) {
        float f = par2Icon.c();
        float f1 = par2Icon.d();
        float f2 = par2Icon.e();
        float f3 = par2Icon.f();
        float f4 = 1.0f;
        float f5 = 0.5f;
        float f6 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.b.j), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-this.b.k), (float)1.0f, (float)0.0f, (float)0.0f);
        par1Tessellator.b();
        par1Tessellator.b(0.0f, 1.0f, 0.0f);
        par1Tessellator.a((double)(0.0f - f5), (double)(0.0f - f6), 0.0, (double)f, (double)f3);
        par1Tessellator.a((double)(f4 - f5), (double)(0.0f - f6), 0.0, (double)f1, (double)f3);
        par1Tessellator.a((double)(f4 - f5), (double)(f4 - f6), 0.0, (double)f1, (double)f2);
        par1Tessellator.a((double)(0.0f - f5), (double)(f4 - f6), 0.0, (double)f, (double)f2);
        par1Tessellator.a();
    }
}

