/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  ls
 *  nn
 *  of
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model;

import noppes.common.entity.model_entity.EntityNpcCrystal;
import org.lwjgl.opengl.GL11;

public class ModelNpcCrystal
extends bbo {
    float ticks;
    private final bcu field_41057_g;
    private final bcu field_41058_h = new bcu((bbo)this, "glass");
    private final bcu field_41059_i;

    public ModelNpcCrystal(float par1) {
        this.field_41058_h.a(0, 0).a(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.field_41057_g = new bcu((bbo)this, "cube");
        this.field_41057_g.a(32, 0).a(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.field_41059_i = new bcu((bbo)this, "base");
        this.field_41059_i.a(0, 16).a(-6.0f, 16.0f, -6.0f, 12, 4, 12);
    }

    public void a(of par1EntityLiving, float f6, float f5, float par9) {
        this.ticks = par9;
    }

    public void a(nn par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        EntityNpcCrystal crystal = (EntityNpcCrystal)par1Entity;
        GL11.glPushMatrix();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        GL11.glTranslatef((float)0.0f, (float)-0.5f, (float)0.0f);
        this.field_41059_i.a(par7);
        float f = (float)crystal.innerRotation + this.ticks;
        float f1 = ls.a((float)(f * 0.2f)) / 2.0f + 0.5f;
        f1 += f1 * f1;
        par3 = f * 3.0f;
        par4 = f1 * 0.2f;
        GL11.glRotatef((float)par3, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)(0.1f + par4), (float)0.0f);
        GL11.glRotatef((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        this.field_41058_h.a(par7);
        float sca = 0.875f;
        GL11.glScalef((float)sca, (float)sca, (float)sca);
        GL11.glRotatef((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        GL11.glRotatef((float)par3, (float)0.0f, (float)1.0f, (float)0.0f);
        this.field_41058_h.a(par7);
        GL11.glScalef((float)sca, (float)sca, (float)sca);
        GL11.glRotatef((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        GL11.glRotatef((float)par3, (float)0.0f, (float)1.0f, (float)0.0f);
        this.field_41057_g.a(par7);
        GL11.glPopMatrix();
    }
}

