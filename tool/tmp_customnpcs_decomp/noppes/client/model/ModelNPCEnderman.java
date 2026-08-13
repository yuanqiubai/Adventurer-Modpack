/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 */
package noppes.client.model;

import noppes.client.model.ModelNPCMale;

public class ModelNPCEnderman
extends ModelNPCMale {
    public ModelNPCEnderman(float f) {
        super(f);
    }

    @Override
    public void init(float f, float f1) {
        super.init(f, f1);
        this.bipedHeadwear = new bcu((bbo)this, 0, 16);
        this.bipedHeadwear.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, f1 + 0.5f);
        this.bipedHeadwear.a(0.0f, 0.0f + f1, 0.0f);
        this.bipedBody = new bcu((bbo)this, 40, 0);
        this.bipedBody.a(-4.0f, 0.0f, -2.0f, 8, 12, 4, f1);
        this.bipedBody.a(0.0f, -14.0f, 0.0f);
        this.bipedRightArm = new bcu((bbo)this, 32, 0);
        this.bipedRightArm.a(-1.0f, -2.0f, -1.0f, 2, 30, 2, f1);
        this.bipedRightArm.a(-3.0f, -12.0f, 0.0f);
        this.bipedLeftArm = new bcu((bbo)this, 32, 0);
        this.bipedLeftArm.i = true;
        this.bipedLeftArm.a(-1.0f, -2.0f, -1.0f, 2, 30, 2, f1);
        this.bipedLeftArm.a(5.0f, -12.0f, 0.0f);
        this.bipedRightLeg = new bcu((bbo)this, 32, 0);
        this.bipedRightLeg.a(-1.0f, 0.0f, -1.0f, 2, 30, 2, f1);
        this.bipedRightLeg.a(-2.0f, -2.0f, 0.0f);
        this.bipedLeftLeg = new bcu((bbo)this, 32, 0);
        this.bipedLeftLeg.i = true;
        this.bipedLeftLeg.a(-1.0f, 0.0f, -1.0f, 2, 30, 2, f1);
        this.bipedLeftLeg.a(2.0f, -2.0f, 0.0f);
        this.bipedCloak.a(0.0f, -12.0f, -2.0f);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
        float f6 = -14.0f;
        this.bipedBody.f = 0.0f;
        this.bipedBody.d = f6;
        this.bipedBody.e = -0.0f;
        this.bipedRightLeg.f -= 0.0f;
        this.bipedLeftLeg.f -= 0.0f;
        this.bipedRightArm.f = (float)((double)this.bipedRightArm.f * 0.5);
        this.bipedLeftArm.f = (float)((double)this.bipedLeftArm.f * 0.5);
        this.bipedRightLeg.f = (float)((double)this.bipedRightLeg.f * 0.5);
        this.bipedLeftLeg.f = (float)((double)this.bipedLeftLeg.f * 0.5);
        float f7 = 0.4f;
        if (this.bipedRightLeg.f > f7) {
            this.bipedRightLeg.f = f7;
        }
        if (this.bipedLeftLeg.f > f7) {
            this.bipedLeftLeg.f = f7;
        }
        if (this.bipedRightLeg.f < -f7) {
            this.bipedRightLeg.f = -f7;
        }
        if (this.bipedLeftLeg.f < -f7) {
            this.bipedLeftLeg.f = -f7;
        }
        if (this.heldItemLeft != 0) {
            this.bipedRightArm.f = -0.5f;
            this.bipedLeftArm.f = -0.5f;
            this.bipedRightArm.h = 0.05f;
            this.bipedLeftArm.h = -0.05f;
        }
        this.bipedRightArm.e = 0.0f;
        this.bipedLeftArm.e = 0.0f;
        this.bipedRightLeg.e = 0.0f;
        this.bipedLeftLeg.e = 0.0f;
        this.bipedRightLeg.d = 9.0f + f6;
        this.bipedLeftLeg.d = 9.0f + f6;
        this.bipedHead.e = -0.0f;
        this.bipedHead.d = f6 + 1.0f;
        this.bipedHeadwear.c = this.bipedHead.c;
        this.bipedHeadwear.d = this.bipedHead.d;
        this.bipedHeadwear.e = this.bipedHead.e;
        this.bipedHeadwear.f = this.bipedHead.f;
        this.bipedHeadwear.g = this.bipedHead.g;
        this.bipedHeadwear.h = this.bipedHead.h;
    }
}

