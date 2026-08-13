/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  ls
 *  nn
 */
package noppes.client.model;

import noppes.client.model.ModelNPCMale;

public class ModelNPCGolem
extends ModelNPCMale {
    private bcu bipedLowerBody;

    public ModelNPCGolem(float f) {
        super(f);
    }

    @Override
    public void init(float f, float f1) {
        super.init(f, f1);
        int short1 = 128;
        int short2 = 128;
        float f2 = -7.0f;
        this.bipedHead = new bcu((bbo)this).b(short1, short2);
        this.bipedHead.a(0.0f, f2, -2.0f);
        this.bipedHead.a(0, 0).a(-4.0f, -12.0f, -5.5f, 8, 10, 8, f);
        this.bipedHead.a(24, 0).a(-1.0f, -5.0f, -7.5f, 2, 4, 2, f);
        this.bipedHeadwear = new bcu((bbo)this).b(short1, short2);
        this.bipedHeadwear.a(0.0f, f2, -2.0f);
        this.bipedHeadwear.a(0, 85).a(-4.0f, -12.0f, -5.5f, 8, 10, 8, f + 0.5f);
        this.bipedBody = new bcu((bbo)this).b(short1, short2);
        this.bipedBody.a(0.0f, 0.0f + f2, 0.0f);
        this.bipedBody.a(0, 40).a(-9.0f, -2.0f, -6.0f, 18, 12, 11, f + 0.2f);
        this.bipedBody.a(0, 21).a(-9.0f, -2.0f, -6.0f, 18, 8, 11, f);
        this.bipedLowerBody = new bcu((bbo)this).b(short1, short2);
        this.bipedLowerBody.a(0.0f, 0.0f + f2, 0.0f);
        this.bipedLowerBody.a(0, 70).a(-4.5f, 10.0f, -3.0f, 9, 5, 6, f + 0.5f);
        this.bipedLowerBody.a(30, 70).a(-4.5f, 6.0f, -3.0f, 9, 9, 6, f + 0.4f);
        this.bipedRightArm = new bcu((bbo)this).b(short1, short2);
        this.bipedRightArm.a(0.0f, f2, 0.0f);
        this.bipedRightArm.a(60, 21).a(-13.0f, -2.5f, -3.0f, 4, 30, 6, f + 0.2f);
        this.bipedRightArm.a(80, 21).a(-13.0f, -2.5f, -3.0f, 4, 20, 6, f);
        this.bipedRightArm.a(100, 21).a(-13.0f, -2.5f, -3.0f, 4, 20, 6, f + 1.0f);
        this.bipedLeftArm = new bcu((bbo)this).b(short1, short2);
        this.bipedLeftArm.a(0.0f, f2, 0.0f);
        this.bipedLeftArm.a(60, 58).a(9.0f, -2.5f, -3.0f, 4, 30, 6, f + 0.2f);
        this.bipedLeftArm.a(80, 58).a(9.0f, -2.5f, -3.0f, 4, 20, 6, f);
        this.bipedLeftArm.a(100, 58).a(9.0f, -2.5f, -3.0f, 4, 20, 6, f + 1.0f);
        this.bipedLeftLeg = new bcu((bbo)this, 0, 22).b(short1, short2);
        this.bipedLeftLeg.a(-4.0f, 18.0f + f2, 0.0f);
        this.bipedLeftLeg.a(37, 0).a(-3.5f, -3.0f, -3.0f, 6, 16, 5, f);
        this.bipedRightLeg = new bcu((bbo)this, 0, 22).b(short1, short2);
        this.bipedRightLeg.i = true;
        this.bipedRightLeg.a(60, 0).a(5.0f, 18.0f + f2, 0.0f);
        this.bipedRightLeg.a(-3.5f, -3.0f, -3.0f, 6, 16, 5, f);
    }

    @Override
    public void a(nn par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        super.a(par1Entity, par2, par3, par4, par5, par6, par7);
        this.bipedLowerBody.a(par7);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
        this.bipedHead.g = par4 / 57.295776f;
        this.bipedHead.f = par5 / 57.295776f;
        this.bipedHeadwear.g = this.bipedHead.g;
        this.bipedHeadwear.f = this.bipedHead.f;
        this.bipedLeftLeg.f = -1.5f * this.func_78172_a(par1, 13.0f) * par2;
        this.bipedRightLeg.f = 1.5f * this.func_78172_a(par1, 13.0f) * par2;
        this.bipedLeftLeg.g = 0.0f;
        this.bipedRightLeg.g = 0.0f;
        float f6 = ls.a((float)(this.p * (float)Math.PI));
        float f7 = ls.a((float)((16.0f - (1.0f - this.p) * (1.0f - this.p)) * (float)Math.PI));
        if ((double)this.p > 0.0) {
            this.bipedRightArm.h = 0.0f;
            this.bipedLeftArm.h = 0.0f;
            this.bipedRightArm.g = -(0.1f - f6 * 0.6f);
            this.bipedLeftArm.g = 0.1f - f6 * 0.6f;
            this.bipedRightArm.f = 0.0f;
            this.bipedLeftArm.f = 0.0f;
            this.bipedRightArm.f = -1.5707964f;
            this.bipedLeftArm.f = -1.5707964f;
            this.bipedRightArm.f -= f6 * 1.2f - f7 * 0.4f;
            this.bipedLeftArm.f -= f6 * 1.2f - f7 * 0.4f;
        } else if (this.aimedBow) {
            float f1 = 0.0f;
            float f3 = 0.0f;
            this.bipedRightArm.h = 0.0f;
            this.bipedRightArm.f = -1.5707964f + this.bipedHead.f;
            this.bipedRightArm.f -= f1 * 1.2f - f3 * 0.4f;
            this.bipedRightArm.h += ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
            this.bipedRightArm.f += ls.a((float)(par3 * 0.067f)) * 0.05f;
            this.bipedLeftArm.f = (-0.2f - 1.5f * this.func_78172_a(par1, 13.0f)) * par2;
            this.bipedBody.g = -(0.1f - f1 * 0.6f) + this.bipedHead.g;
            this.bipedRightArm.g = -(0.1f - f1 * 0.6f) + this.bipedHead.g;
            this.bipedLeftArm.g = 0.1f - f1 * 0.6f + this.bipedHead.g;
        } else {
            this.bipedRightArm.f = (-0.2f + 1.5f * this.func_78172_a(par1, 13.0f)) * par2;
            this.bipedLeftArm.f = (-0.2f - 1.5f * this.func_78172_a(par1, 13.0f)) * par2;
            this.bipedBody.g = 0.0f;
            this.bipedRightArm.g = 0.0f;
            this.bipedLeftArm.g = 0.0f;
            this.bipedRightArm.h = 0.0f;
            this.bipedLeftArm.h = 0.0f;
        }
        if (this.q) {
            this.bipedRightArm.f += -0.62831855f;
            this.bipedLeftArm.f += -0.62831855f;
            this.bipedLeftLeg.f = -1.2566371f;
            this.bipedRightLeg.f = -1.2566371f;
            this.bipedLeftLeg.g = 0.31415927f;
            this.bipedRightLeg.g = -0.31415927f;
        }
    }

    private float func_78172_a(float par1, float par2) {
        return (Math.abs(par1 % par2 - par2 * 0.5f) - par2 * 0.25f) / (par2 * 0.25f);
    }
}

