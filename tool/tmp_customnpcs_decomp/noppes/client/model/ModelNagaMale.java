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

import noppes.client.model.ModelNPCMale;
import noppes.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelNagaMale
extends ModelNPCMale {
    bcu leg;
    bcu leg2;
    bcu leg3;
    bcu leg4;
    bcu leg5;

    public ModelNagaMale(int width, int height, float f) {
        super(width, height, f);
    }

    @Override
    public void init(float f, float f1) {
        super.init(f, f1);
        this.bipedRightLeg = new bcu((bbo)this, 0, 0);
        this.bipedLeftLeg = new bcu((bbo)this, 0, 0);
        this.leg = new bcu((bbo)this, 0, 0);
        bcu legPart = new bcu((bbo)this, 0, 16);
        legPart.a(0.0f, -2.0f, -2.0f, 4, 4, 4);
        legPart.a(-4.0f, 0.0f, 0.0f);
        this.leg.a(legPart);
        legPart = new bcu((bbo)this, 0, 16);
        legPart.i = true;
        legPart.a(0.0f, -2.0f, -2.0f, 4, 4, 4);
        this.leg.a(legPart);
        this.leg2 = new bcu((bbo)this, 0, 0);
        this.leg2.m = this.leg.m;
        this.leg3 = new bcu((bbo)this, 0, 0);
        ModelPlaneRenderer plane = new ModelPlaneRenderer(this, 4, 24);
        plane.b(this.t, this.u);
        plane.addBackPlane(0.0f, -2.0f, 0.0f, 4, 4);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 4, 24);
        plane.b(this.t, this.u);
        plane.i = true;
        plane.addBackPlane(0.0f, -2.0f, 0.0f, 4, 4);
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 8, 24);
        plane.b(this.t, this.u);
        plane.addBackPlane(0.0f, -2.0f, 6.0f, 4, 4);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 8, 24);
        plane.b(this.t, this.u);
        plane.i = true;
        plane.addBackPlane(0.0f, -2.0f, 6.0f, 4, 4);
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 4, 26);
        plane.b(this.t, this.u);
        plane.addTopPlane(0.0f, -2.0f, -6.0f, 4, 6);
        plane.a(-4.0f, 0.0f, 0.0f);
        plane.f = (float)Math.PI;
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 4, 26);
        plane.b(this.t, this.u);
        plane.i = true;
        plane.addTopPlane(0.0f, -2.0f, -6.0f, 4, 6);
        plane.f = (float)Math.PI;
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 8, 26);
        plane.b(this.t, this.u);
        plane.addTopPlane(0.0f, -2.0f, 0.0f, 4, 6);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 8, 26);
        plane.b(this.t, this.u);
        plane.i = true;
        plane.addTopPlane(0.0f, -2.0f, 0.0f, 4, 6);
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 0, 26);
        plane.b(this.t, this.u);
        plane.f = 1.5707964f;
        plane.addSidePlane(0.0f, 0.0f, -2.0f, 6, 4);
        plane.a(-4.0f, 0.0f, 0.0f);
        this.leg3.a((bcu)plane);
        plane = new ModelPlaneRenderer(this, 0, 26);
        plane.b(this.t, this.u);
        plane.f = 1.5707964f;
        plane.addSidePlane(4.0f, 0.0f, -2.0f, 6, 4);
        this.leg3.a((bcu)plane);
        this.leg4 = new bcu((bbo)this, 0, 0);
        this.leg4.b(this.t, this.u);
        this.leg4.m = this.leg3.m;
        this.leg5 = new bcu((bbo)this, 0, 0);
        legPart = new bcu((bbo)this, 56, 20);
        legPart.a(0.0f, 0.0f, -2.0f, 2, 5, 2);
        legPart.a(-2.0f, 0.0f, 0.0f);
        legPart.f = 1.5707964f;
        this.leg5.a(legPart);
        legPart = new bcu((bbo)this, 56, 20);
        legPart.i = true;
        legPart.a(0.0f, 0.0f, -2.0f, 2, 5, 2);
        legPart.f = 1.5707964f;
        this.leg5.a(legPart);
        this.defaultRotation();
        if (this.u != 32) {
            bcu Snout = new bcu((bbo)this, 0, 32);
            Snout.a(0.0f, 0.0f, 0.0f, 4, 3, 3);
            Snout.a(-2.0f, -3.0f, -7.0f);
            this.bipedHead.a(Snout);
            bcu Snout2 = new bcu((bbo)this, 0, 38);
            Snout2.a(0.0f, 0.0f, 0.0f, 4, 3, 1);
            Snout2.a(-2.0f, -3.0f, -5.0f);
            this.bipedHead.a(Snout2);
            ModelPlaneRenderer headFin = new ModelPlaneRenderer(this, 14, 32);
            headFin.b(64, 64);
            headFin.addSidePlane(0.0f, -12.0f, -1.0f, 9, 9);
            this.bipedHead.a((bcu)headFin);
            ModelPlaneRenderer backFin = new ModelPlaneRenderer(this, 23, 32);
            backFin.b(64, 64);
            backFin.addSidePlane(0.0f, 0.0f, 2.0f, 12, 7);
            this.bipedBody.a((bcu)backFin);
        }
    }

    private void defaultRotation() {
        this.leg.a(0.0f, 14.0f, 0.0f);
        this.leg2.a(0.0f, 18.0f, 0.6f);
        this.leg3.a(0.0f, 22.0f, -0.3f);
        this.leg4.a(0.0f, 22.0f, 5.0f);
        this.leg5.a(0.0f, 22.0f, 10.0f);
        this.leg.f = 0.0f;
        this.leg2.f = 0.0f;
        this.leg3.f = 0.0f;
        this.leg4.f = 0.0f;
        this.leg5.f = 0.0f;
    }

    @Override
    public void a(nn par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        super.a(par1Entity, par2, par3, par4, par5, par6, par7);
        this.leg.a(par7);
        this.leg3.a(par7);
        if (!this.q) {
            this.leg2.a(par7);
        }
        GL11.glPushMatrix();
        GL11.glScalef((float)0.64f, (float)0.7f, (float)0.85f);
        GL11.glTranslatef((float)this.leg3.g, (float)0.66f, (float)0.06f);
        this.leg4.a(par7);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.leg3.g + this.leg4.g), (float)0.0f, (float)0.0f);
        this.leg5.a(par7);
        GL11.glPopMatrix();
    }

    @Override
    public void a(of entityliving, float f, float f1, float f2) {
        super.a(entityliving, f, f1, f2);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
        this.leg.g = ls.b((float)(par1 * 0.6662f)) * 0.26f * par2;
        this.leg2.g = ls.b((float)(par1 * 0.6662f)) * 0.5f * par2;
        this.leg3.g = ls.b((float)(par1 * 0.6662f)) * 0.26f * par2;
        this.leg4.g = -ls.b((float)(par1 * 0.6662f)) * 0.16f * par2;
        this.leg5.g = -ls.b((float)(par1 * 0.6662f)) * 0.3f * par2;
        this.defaultRotation();
        if (this.isSleeping) {
            this.leg3.f = -1.5707964f;
            this.leg4.f = -1.5707964f;
            this.leg5.f = -1.5707964f;
            this.leg3.d -= 2.0f;
            this.leg3.e = 0.9f;
            this.leg4.d += 4.0f;
            this.leg4.e = 0.9f;
            this.leg5.d += 7.0f;
            this.leg5.e = 2.9f;
        }
        if (this.q) {
            this.leg.d -= 1.0f;
            this.leg.f = -0.19634955f;
            this.leg.e = -1.0f;
            this.leg2.d -= 4.0f;
            this.leg2.e = -1.0f;
            this.leg3.d -= 9.0f;
            this.leg3.e -= 1.0f;
            this.leg4.d -= 13.0f;
            this.leg4.e -= 1.0f;
            this.leg5.d -= 9.0f;
            this.leg5.e -= 1.0f;
            if (this.isSneak) {
                this.leg.e += 5.0f;
                this.leg3.e += 5.0f;
                this.leg4.e += 5.0f;
                this.leg5.e += 4.0f;
                this.leg.d -= 1.0f;
                this.leg2.d -= 1.0f;
                this.leg3.d -= 1.0f;
                this.leg4.d -= 1.0f;
                this.leg5.d -= 1.0f;
            }
        } else if (this.isSneak) {
            this.leg.d -= 1.0f;
            this.leg2.d -= 1.0f;
            this.leg3.d -= 1.0f;
            this.leg4.d -= 1.0f;
            this.leg5.d -= 1.0f;
            this.leg.e = 5.0f;
            this.leg2.e = 3.0f;
        }
    }
}

