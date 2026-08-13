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

import noppes.CustomNpcs;
import org.lwjgl.opengl.GL11;

public class ModelNPCMale
extends bbo {
    public bcu bipedHead;
    public bcu bipedHeadwear;
    public bcu bipedBody;
    public bcu bipedRightArm;
    public bcu bipedLeftArm;
    public bcu bipedRightLeg;
    public bcu bipedLeftLeg;
    public bcu bipedEars;
    public bcu bipedCloak;
    public int heldItemLeft;
    public int heldItemRight;
    public boolean isSneak;
    public boolean aimedBow;
    public boolean isDancing;
    public boolean isSleeping;
    public float animationTick;
    public float dancingTicks;

    public ModelNPCMale(float f) {
        this.init(f, 0.0f);
    }

    public ModelNPCMale(int width, int height, float f) {
        this.u = height;
        this.t = width;
        this.init(f, 0.0f);
    }

    public void a(of par1EntityLiving, float f6, float f5, float par9) {
        this.animationTick += par9;
        this.dancingTicks = (float)CustomNpcs.ticks / 3.978873f;
    }

    public void init(float f, float f1) {
        this.heldItemLeft = 0;
        this.heldItemRight = 0;
        this.isSneak = false;
        this.aimedBow = false;
        this.bipedCloak = new bcu((bbo)this, 0, 0);
        this.bipedCloak.b = 32.0f;
        this.bipedCloak.a(-5.0f, 0.0f, -1.0f, 10, 16, 1, f);
        this.bipedEars = new bcu((bbo)this, 24, 0);
        this.bipedEars.a(-3.0f, -6.0f, -1.0f, 6, 6, 1, f);
        this.bipedHead = new bcu((bbo)this, 0, 0);
        this.bipedHead.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.a(0.0f, 0.0f + f1, 0.0f);
        this.bipedHeadwear = new bcu((bbo)this, 32, 0);
        this.bipedHeadwear.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
        this.bipedHeadwear.a(0.0f, 0.0f + f1, 0.0f);
        this.bipedBody = new bcu((bbo)this, 16, 16);
        this.bipedBody.a(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        this.bipedBody.a(0.0f, 0.0f + f1, 0.0f);
        this.bipedRightArm = new bcu((bbo)this, 40, 16);
        this.bipedRightArm.a(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightArm.a(-5.0f, 2.0f + f1, 0.0f);
        this.bipedLeftArm = new bcu((bbo)this, 40, 16);
        this.bipedLeftArm.i = true;
        this.bipedLeftArm.a(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftArm.a(5.0f, 2.0f + f1, 0.0f);
        this.bipedRightLeg = new bcu((bbo)this, 0, 16);
        this.bipedRightLeg.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightLeg.a(-2.0f, 12.0f + f1, 0.0f);
        this.bipedLeftLeg = new bcu((bbo)this, 0, 16);
        this.bipedLeftLeg.i = true;
        this.bipedLeftLeg.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftLeg.a(2.0f, 12.0f + f1, 0.0f);
    }

    public void a(nn par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7);
        if (!this.isDancing) {
            this.bipedHead.a(par7);
            this.bipedBody.a(par7);
            this.bipedRightArm.a(par7);
            this.bipedLeftArm.a(par7);
            this.bipedRightLeg.a(par7);
            this.bipedLeftLeg.a(par7);
            this.bipedHeadwear.a(par7);
        } else {
            this.renderHead(par1Entity, par7);
            this.renderArms(par1Entity, par7);
            this.renderBody(par1Entity, par7);
            this.renderLegs(par1Entity, par7);
        }
    }

    public void renderHead(nn entityliving, float par7) {
        if (this.isDancing) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)Math.sin(this.dancingTicks) * 0.075f), (float)((float)Math.abs(Math.cos(this.dancingTicks)) * 0.125f - 0.02f), (float)((float)(-Math.abs(Math.cos(this.dancingTicks))) * 0.075f));
            this.bipedHead.a(par7);
            this.bipedHeadwear.a(par7);
            GL11.glPopMatrix();
        } else {
            this.bipedHead.a(par7);
            this.bipedHeadwear.a(par7);
        }
    }

    public void renderLeftArm(nn entityliving, float par7) {
        if (this.isDancing) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)Math.sin(this.dancingTicks) * 0.025f), (float)((float)Math.abs(Math.cos(this.dancingTicks)) * 0.125f - 0.02f), (float)0.0f);
            this.bipedLeftArm.a(par7);
            GL11.glPopMatrix();
        } else {
            this.bipedLeftArm.a(par7);
        }
    }

    public void renderArms(nn entity, float par7) {
        this.renderLeftArm(entity, par7);
        this.renderRightArm(entity, par7);
    }

    public void renderRightArm(nn entityliving, float par7) {
        if (this.isDancing) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)Math.sin(this.dancingTicks) * 0.025f), (float)((float)Math.abs(Math.cos(this.dancingTicks)) * 0.125f - 0.02f), (float)0.0f);
            this.bipedRightArm.a(par7);
            GL11.glPopMatrix();
        } else {
            this.bipedRightArm.a(par7);
        }
    }

    public void renderBody(nn entityliving, float par7) {
        if (this.isDancing) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)Math.sin(this.dancingTicks) * 0.015f), (float)0.0f, (float)0.0f);
            this.bipedBody.a(par7);
            GL11.glPopMatrix();
        } else {
            this.bipedBody.a(par7);
        }
    }

    public void renderLegs(nn entityliving, float par7) {
        this.bipedRightLeg.a(par7);
        this.bipedLeftLeg.a(par7);
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
        float f3;
        float f1;
        this.bipedHead.g = par4 / 57.295776f;
        this.bipedHead.f = par5 / 57.295776f;
        this.bipedHeadwear.g = this.bipedHead.g;
        this.bipedHeadwear.f = this.bipedHead.f;
        this.bipedRightArm.f = ls.b((float)(par1 * 0.6662f + (float)Math.PI)) * 2.0f * par2 * 0.5f;
        this.bipedLeftArm.f = ls.b((float)(par1 * 0.6662f)) * 2.0f * par2 * 0.5f;
        this.bipedRightArm.h = 0.0f;
        this.bipedLeftArm.h = 0.0f;
        this.bipedRightLeg.f = ls.b((float)(par1 * 0.6662f)) * 1.4f * par2;
        this.bipedLeftLeg.f = ls.b((float)(par1 * 0.6662f + (float)Math.PI)) * 1.4f * par2;
        this.bipedRightLeg.g = 0.0f;
        this.bipedLeftLeg.g = 0.0f;
        if (this.q) {
            this.bipedRightArm.f += -0.62831855f;
            this.bipedLeftArm.f += -0.62831855f;
            this.bipedRightLeg.f = -1.2566371f;
            this.bipedLeftLeg.f = -1.2566371f;
            this.bipedRightLeg.g = 0.31415927f;
            this.bipedLeftLeg.g = -0.31415927f;
        }
        if (this.heldItemLeft != 0) {
            this.bipedLeftArm.f = this.bipedLeftArm.f * 0.5f - 0.31415927f * (float)this.heldItemLeft;
        }
        if (this.heldItemRight != 0) {
            this.bipedRightArm.f = this.bipedRightArm.f * 0.5f - 0.31415927f * (float)this.heldItemRight;
        }
        this.bipedRightArm.g = 0.0f;
        this.bipedLeftArm.g = 0.0f;
        if (this.p > -9990.0f) {
            f1 = this.p;
            this.bipedBody.g = ls.a((float)(ls.c((float)f1) * (float)Math.PI * 2.0f)) * 0.2f;
            this.bipedRightArm.e = ls.a((float)this.bipedBody.g) * 5.0f;
            this.bipedRightArm.c = -ls.b((float)this.bipedBody.g) * 5.0f;
            this.bipedLeftArm.e = -ls.a((float)this.bipedBody.g) * 5.0f;
            this.bipedLeftArm.c = ls.b((float)this.bipedBody.g) * 5.0f;
            this.bipedRightArm.g += this.bipedBody.g;
            this.bipedLeftArm.g += this.bipedBody.g;
            this.bipedLeftArm.f += this.bipedBody.g;
            f1 = 1.0f - this.p;
            f1 *= f1;
            f1 *= f1;
            f1 = 1.0f - f1;
            f3 = ls.a((float)(f1 * (float)Math.PI));
            float f4 = ls.a((float)(this.p * (float)Math.PI)) * -(this.bipedHead.f - 0.7f) * 0.75f;
            this.bipedRightArm.f = (float)((double)this.bipedRightArm.f - ((double)f3 * 1.2 + (double)f4));
            this.bipedRightArm.g += this.bipedBody.g * 2.0f;
            this.bipedRightArm.h = ls.a((float)(this.p * (float)Math.PI)) * -0.4f;
        }
        if (this.isSneak) {
            this.bipedBody.f = 0.5f;
            this.bipedRightLeg.f -= 0.0f;
            this.bipedLeftLeg.f -= 0.0f;
            this.bipedRightArm.f += 0.4f;
            this.bipedLeftArm.f += 0.4f;
            this.bipedRightLeg.e = 4.0f;
            this.bipedLeftLeg.e = 4.0f;
            this.bipedRightLeg.d = 9.0f;
            this.bipedLeftLeg.d = 9.0f;
            this.bipedHead.d = 1.0f;
        } else {
            this.bipedBody.f = 0.0f;
            this.bipedRightLeg.e = 0.0f;
            this.bipedLeftLeg.e = 0.0f;
            this.bipedRightLeg.d = 12.0f;
            this.bipedLeftLeg.d = 12.0f;
            this.bipedHead.d = 0.0f;
        }
        this.bipedRightArm.h += ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
        this.bipedLeftArm.h -= ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
        this.bipedRightArm.f += ls.a((float)(par3 * 0.067f)) * 0.05f;
        this.bipedLeftArm.f -= ls.a((float)(par3 * 0.067f)) * 0.05f;
        if (this.aimedBow) {
            f1 = 0.0f;
            f3 = 0.0f;
            this.bipedRightArm.h = 0.0f;
            this.bipedLeftArm.h = 0.0f;
            this.bipedRightArm.g = -(0.1f - f1 * 0.6f) + this.bipedHead.g;
            this.bipedLeftArm.g = 0.1f - f1 * 0.6f + this.bipedHead.g + 0.4f;
            this.bipedRightArm.f = -1.5707964f + this.bipedHead.f;
            this.bipedLeftArm.f = -1.5707964f + this.bipedHead.f;
            this.bipedRightArm.f -= f1 * 1.2f - f3 * 0.4f;
            this.bipedLeftArm.f -= f1 * 1.2f - f3 * 0.4f;
            this.bipedRightArm.h += ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
            this.bipedLeftArm.h -= ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
            this.bipedRightArm.f += ls.a((float)(par3 * 0.067f)) * 0.05f;
            this.bipedLeftArm.f -= ls.a((float)(par3 * 0.067f)) * 0.05f;
        }
    }

    public void renderEars(float f) {
        this.bipedEars.g = this.bipedHead.g;
        this.bipedEars.f = this.bipedHead.f;
        this.bipedEars.c = 0.0f;
        this.bipedEars.d = 0.0f;
        this.bipedEars.a(f);
    }

    public void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }

    public void renderCloak(float f) {
        this.bipedCloak.a(f);
    }
}

