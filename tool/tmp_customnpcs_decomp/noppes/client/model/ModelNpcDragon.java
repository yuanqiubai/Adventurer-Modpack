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

import noppes.common.entity.model_entity.EntityNpcDragon;
import org.lwjgl.opengl.GL11;

public class ModelNpcDragon
extends bbo {
    private final bcu head;
    private final bcu neck;
    private final bcu jaw;
    private final bcu body;
    private final bcu rearLeg;
    private final bcu frontLeg;
    private final bcu rearLegTip;
    private final bcu frontLegTip;
    private final bcu rearFoot;
    private final bcu frontFoot;
    private final bcu wing;
    private final bcu wingTip;
    private float field_40317_s;

    public ModelNpcDragon(float f) {
        this.t = 256;
        this.u = 256;
        this.a("body.body", 0, 0);
        this.a("wing.skin", -56, 88);
        this.a("wingtip.skin", -56, 144);
        this.a("rearleg.main", 0, 0);
        this.a("rearfoot.main", 112, 0);
        this.a("rearlegtip.main", 196, 0);
        this.a("head.upperhead", 112, 30);
        this.a("wing.bone", 112, 88);
        this.a("head.upperlip", 176, 44);
        this.a("jaw.jaw", 176, 65);
        this.a("frontleg.main", 112, 104);
        this.a("wingtip.bone", 112, 136);
        this.a("frontfoot.main", 144, 104);
        this.a("neck.box", 192, 104);
        this.a("frontlegtip.main", 226, 138);
        this.a("body.scale", 220, 53);
        this.a("head.scale", 0, 0);
        this.a("neck.scale", 48, 0);
        this.a("head.nostril", 112, 0);
        float f1 = -16.0f;
        this.head = new bcu((bbo)this, "head");
        this.head.a("upperlip", -6.0f, -1.0f, -8.0f + f1, 12, 5, 16);
        this.head.a("upperhead", -8.0f, -8.0f, 6.0f + f1, 16, 16, 16);
        this.head.i = true;
        this.head.a("scale", -5.0f, -12.0f, 12.0f + f1, 2, 4, 6);
        this.head.a("nostril", -5.0f, -3.0f, -6.0f + f1, 2, 2, 4);
        this.head.i = false;
        this.head.a("scale", 3.0f, -12.0f, 12.0f + f1, 2, 4, 6);
        this.head.a("nostril", 3.0f, -3.0f, -6.0f + f1, 2, 2, 4);
        this.jaw = new bcu((bbo)this, "jaw");
        this.jaw.a(0.0f, 4.0f, 8.0f + f1);
        this.jaw.a("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16);
        this.head.a(this.jaw);
        this.neck = new bcu((bbo)this, "neck");
        this.neck.a("box", -5.0f, -5.0f, -5.0f, 10, 10, 10);
        this.neck.a("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6);
        this.body = new bcu((bbo)this, "body");
        this.body.a(0.0f, 4.0f, 8.0f);
        this.body.a("body", -12.0f, 0.0f, -16.0f, 24, 24, 64);
        this.body.a("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12);
        this.body.a("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12);
        this.body.a("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12);
        this.wing = new bcu((bbo)this, "wing");
        this.wing.a(-12.0f, 5.0f, 2.0f);
        this.wing.a("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        this.wing.a("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.wingTip = new bcu((bbo)this, "wingtip");
        this.wingTip.a(-56.0f, 0.0f, 0.0f);
        this.wingTip.a("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        this.wingTip.a("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.wing.a(this.wingTip);
        this.frontLeg = new bcu((bbo)this, "frontleg");
        this.frontLeg.a(-12.0f, 20.0f, 2.0f);
        this.frontLeg.a("main", -4.0f, -4.0f, -4.0f, 8, 24, 8);
        this.frontLegTip = new bcu((bbo)this, "frontlegtip");
        this.frontLegTip.a(0.0f, 20.0f, -1.0f);
        this.frontLegTip.a("main", -3.0f, -1.0f, -3.0f, 6, 24, 6);
        this.frontLeg.a(this.frontLegTip);
        this.frontFoot = new bcu((bbo)this, "frontfoot");
        this.frontFoot.a(0.0f, 23.0f, 0.0f);
        this.frontFoot.a("main", -4.0f, 0.0f, -12.0f, 8, 4, 16);
        this.frontLegTip.a(this.frontFoot);
        this.rearLeg = new bcu((bbo)this, "rearleg");
        this.rearLeg.a(-16.0f, 16.0f, 42.0f);
        this.rearLeg.a("main", -8.0f, -4.0f, -8.0f, 16, 32, 16);
        this.rearLegTip = new bcu((bbo)this, "rearlegtip");
        this.rearLegTip.a(0.0f, 32.0f, -4.0f);
        this.rearLegTip.a("main", -6.0f, -2.0f, 0.0f, 12, 32, 12);
        this.rearLeg.a(this.rearLegTip);
        this.rearFoot = new bcu((bbo)this, "rearfoot");
        this.rearFoot.a(0.0f, 31.0f, 4.0f);
        this.rearFoot.a("main", -9.0f, 0.0f, -20.0f, 18, 6, 24);
        this.rearLegTip.a(this.rearFoot);
    }

    public void a(of entityliving, float f, float f1, float f2) {
        this.field_40317_s = f2;
    }

    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        int var25;
        EntityNpcDragon entitydragon = (EntityNpcDragon)entity;
        GL11.glPushMatrix();
        float f6 = entitydragon.field_40173_aw + (entitydragon.field_40172_ax - entitydragon.field_40173_aw) * this.field_40317_s;
        this.jaw.f = (float)(Math.sin(f6 * (float)Math.PI * 2.0f) + 1.0) * 0.2f;
        float f7 = (float)(Math.sin(f6 * (float)Math.PI * 2.0f - 1.0f) + 1.0);
        f7 = (f7 * f7 * 1.0f + f7 * 2.0f) * 0.05f;
        GL11.glTranslatef((float)0.0f, (float)(f7 - 2.0f), (float)-3.0f);
        GL11.glRotatef((float)(f7 * 2.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        float f8 = -30.0f;
        float f9 = 22.0f;
        float f10 = 0.0f;
        float f11 = 1.5f;
        double[] ad2 = entitydragon.func_40160_a(6, this.field_40317_s);
        float f12 = this.func_40307_a(entitydragon.func_40160_a(5, this.field_40317_s)[0] - entitydragon.func_40160_a(10, this.field_40317_s)[0]);
        float f13 = this.func_40307_a(entitydragon.func_40160_a(5, this.field_40317_s)[0] + (double)(f12 / 2.0f));
        f8 += 2.0f;
        float f14 = 0.0f;
        float f15 = f6 * (float)Math.PI * 2.0f;
        f8 = 20.0f;
        f9 = -12.0f;
        for (int ad1 = 0; ad1 < 5; ++ad1) {
            double[] k = entitydragon.func_40160_a(5 - ad1, this.field_40317_s);
            f14 = (float)Math.cos((float)ad1 * 0.45f + f15) * 0.15f;
            this.neck.g = this.func_40307_a(k[0] - ad2[0]) * (float)Math.PI / 180.0f * f11;
            this.neck.f = f14 + (float)(k[1] - ad2[1]) * (float)Math.PI / 180.0f * f11 * 5.0f;
            this.neck.h = -this.func_40307_a(k[0] - (double)f13) * (float)Math.PI / 180.0f * f11;
            this.neck.d = f8;
            this.neck.e = f9;
            this.neck.c = f10;
            f8 = (float)((double)f8 + Math.sin(this.neck.f) * 10.0);
            f9 = (float)((double)f9 - Math.cos(this.neck.g) * Math.cos(this.neck.f) * 10.0);
            f10 = (float)((double)f10 - Math.sin(this.neck.g) * Math.cos(this.neck.f) * 10.0);
            this.neck.a(f5);
        }
        this.head.d = f8;
        this.head.e = f9;
        this.head.c = f10;
        double[] var23 = entitydragon.func_40160_a(0, this.field_40317_s);
        this.head.g = this.func_40307_a(var23[0] - ad2[0]) * (float)Math.PI / 180.0f;
        this.head.h = -this.func_40307_a(var23[0] - (double)f13) * (float)Math.PI / 180.0f;
        this.head.a(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)1.0f, (float)0.0f);
        if (entitydragon.F) {
            GL11.glRotatef((float)(-f12 * f11 * 0.3f), (float)0.0f, (float)0.0f, (float)1.0f);
        } else {
            GL11.glRotatef((float)(-f12 * f11 * 1.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GL11.glTranslatef((float)0.0f, (float)-1.0f, (float)0.0f);
        this.body.h = 0.0f;
        this.body.a(f5);
        if (entitydragon.F) {
            for (var25 = 0; var25 < 2; ++var25) {
                GL11.glEnable((int)2884);
                this.wing.f = 0.25f;
                this.wing.g = 0.95f;
                this.wing.h = -0.5f;
                this.wingTip.h = -0.4f;
                this.frontLeg.f = ls.b((float)((float)((double)(f * 0.6662f) + (var25 == 0 ? 0.0 : Math.PI)))) * 0.6f * f1 + 0.45f + f7 * 0.5f;
                this.frontLegTip.f = -1.3f - f7 * 1.2f;
                this.frontFoot.f = 0.85f + f7 * 0.5f;
                this.frontLeg.a(f5);
                this.rearLeg.f = ls.b((float)((float)((double)(f * 0.6662f) + (var25 == 0 ? Math.PI : 0.0)))) * 0.6f * f1 + 0.75f + f7 * 0.5f;
                this.rearLegTip.f = -1.6f - f7 * 0.8f;
                this.rearLegTip.d = 20.0f;
                this.rearLegTip.e = 2.0f;
                this.rearFoot.f = 0.85f + f7 * 0.2f;
                this.rearLeg.a(f5);
                this.wing.a(f5);
                GL11.glScalef((float)-1.0f, (float)1.0f, (float)1.0f);
                if (var25 != 0) continue;
                GL11.glCullFace((int)1028);
            }
        } else {
            for (var25 = 0; var25 < 2; ++var25) {
                GL11.glEnable((int)2884);
                float ad22 = f6 * (float)Math.PI * 2.0f;
                this.wing.f = 0.125f - (float)Math.cos(ad22) * 0.2f;
                this.wing.g = 0.25f;
                this.wing.h = (float)(Math.sin(ad22) + 0.125) * 0.8f;
                this.wingTip.h = -((float)(Math.sin(ad22 + 2.0f) + 0.5)) * 0.75f;
                this.rearLegTip.d = 32.0f;
                this.rearLegTip.e = -2.0f;
                this.rearLeg.f = 1.0f + f7 * 0.1f;
                this.rearLegTip.f = 0.5f + f7 * 0.1f;
                this.rearFoot.f = 0.75f + f7 * 0.1f;
                this.frontLeg.f = 1.3f + f7 * 0.1f;
                this.frontLegTip.f = -0.5f - f7 * 0.1f;
                this.frontFoot.f = 0.75f + f7 * 0.1f;
                this.wing.a(f5);
                this.frontLeg.a(f5);
                this.rearLeg.a(f5);
                GL11.glScalef((float)-1.0f, (float)1.0f, (float)1.0f);
                if (var25 != 0) continue;
                GL11.glCullFace((int)1028);
            }
        }
        GL11.glPopMatrix();
        GL11.glCullFace((int)1029);
        GL11.glDisable((int)2884);
        f14 = -((float)Math.sin(f6 * (float)Math.PI * 2.0f)) * 0.0f;
        f15 = f6 * (float)Math.PI * 2.0f;
        f8 = 10.0f;
        f9 = 60.0f;
        f10 = 0.0f;
        ad2 = entitydragon.func_40160_a(11, this.field_40317_s);
        for (var25 = 0; var25 < 12; ++var25) {
            double[] var24 = entitydragon.func_40160_a(12 + var25, this.field_40317_s);
            f14 = (float)((double)f14 + Math.sin((float)var25 * 0.45f + f15) * (double)0.05f);
            this.neck.g = (this.func_40307_a(var24[0] - ad2[0]) * f11 + 180.0f) * (float)Math.PI / 180.0f;
            this.neck.f = f14 + (float)(var24[1] - ad2[1]) * (float)Math.PI / 180.0f * f11 * 5.0f;
            this.neck.h = this.func_40307_a(var24[0] - (double)f13) * (float)Math.PI / 180.0f * f11;
            this.neck.d = f8;
            this.neck.e = f9;
            this.neck.c = f10;
            f8 = (float)((double)f8 + Math.sin(this.neck.f) * 10.0);
            f9 = (float)((double)f9 - Math.cos(this.neck.g) * Math.cos(this.neck.f) * 10.0);
            f10 = (float)((double)f10 - Math.sin(this.neck.g) * Math.cos(this.neck.f) * 10.0);
            this.neck.a(f5);
        }
        GL11.glPopMatrix();
    }

    private float func_40307_a(double d) {
        while (d >= 180.0) {
            d -= 360.0;
        }
        while (d < -180.0) {
            d += 360.0;
        }
        return (float)d;
    }
}

