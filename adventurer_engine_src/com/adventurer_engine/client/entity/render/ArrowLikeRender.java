/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bfq
 *  bgm
 *  bjo
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.entity.render;

import com.adventurer_engine.common.entity.projectiles.EntityBulletGeneral;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ArrowLikeRender
extends bgm {
    private static final bjo arrowTextures = new bjo("textures/entity/arrow.png");

    public void renderArrow(EntityBulletGeneral entity, double par2, double par4, double par6, float par8, float par9) {
        this.b(entity);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        GL11.glRotatef((float)(entity.C + (entity.A - entity.C) * par9 - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(entity.D + (entity.B - entity.D) * par9), (float)0.0f, (float)0.0f, (float)1.0f);
        bfq tessellator = bfq.a;
        int b0 = 0;
        float f2 = 0.0f;
        float f3 = 0.5f;
        float f4 = (float)(b0 * 10) / 32.0f;
        float f5 = (float)(5 + b0 * 10) / 32.0f;
        float f6 = 0.0f;
        float f7 = 0.15625f;
        float f8 = (float)(5 + b0 * 10) / 32.0f;
        float f9 = (float)(10 + b0 * 10) / 32.0f;
        float f10 = 0.05625f;
        GL11.glEnable((int)32826);
        GL11.glRotatef((float)45.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)f10, (float)f10, (float)f10);
        GL11.glTranslatef((float)-4.0f, (float)0.0f, (float)0.0f);
        GL11.glNormal3f((float)f10, (float)0.0f, (float)0.0f);
        tessellator.b();
        tessellator.a(-7.0, -2.0, -2.0, (double)f6, (double)f8);
        tessellator.a(-7.0, -2.0, 2.0, (double)f7, (double)f8);
        tessellator.a(-7.0, 2.0, 2.0, (double)f7, (double)f9);
        tessellator.a(-7.0, 2.0, -2.0, (double)f6, (double)f9);
        tessellator.a();
        GL11.glNormal3f((float)(-f10), (float)0.0f, (float)0.0f);
        tessellator.b();
        tessellator.a(-7.0, 2.0, -2.0, (double)f6, (double)f8);
        tessellator.a(-7.0, 2.0, 2.0, (double)f7, (double)f8);
        tessellator.a(-7.0, -2.0, 2.0, (double)f7, (double)f9);
        tessellator.a(-7.0, -2.0, -2.0, (double)f6, (double)f9);
        tessellator.a();
        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glNormal3f((float)0.0f, (float)0.0f, (float)f10);
            tessellator.b();
            tessellator.a(-8.0, -2.0, 0.0, (double)f2, (double)f4);
            tessellator.a(8.0, -2.0, 0.0, (double)f3, (double)f4);
            tessellator.a(8.0, 2.0, 0.0, (double)f3, (double)f5);
            tessellator.a(-8.0, 2.0, 0.0, (double)f2, (double)f5);
            tessellator.a();
        }
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    protected bjo getArrowTextures(EntityBulletGeneral par1EntityArrow) {
        return arrowTextures;
    }

    protected bjo a(nn par1Entity) {
        return this.getArrowTextures((EntityBulletGeneral)par1Entity);
    }

    public void a(nn par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderArrow((EntityBulletGeneral)par1Entity, par2, par4, par6, par8, par9);
    }
}

