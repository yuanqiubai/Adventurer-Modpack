/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ls
 */
package noppes.client.model;

import noppes.client.model.ModelNPCMale;

public class ModelZombieMale
extends ModelNPCMale {
    public ModelZombieMale(float f) {
        super(f);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float f6 = ls.a((float)(this.p * (float)Math.PI));
        float f7 = ls.a((float)((1.0f - (1.0f - this.p) * (1.0f - this.p)) * (float)Math.PI));
        this.bipedRightArm.h = 0.0f;
        this.bipedLeftArm.h = 0.0f;
        this.bipedRightArm.g = -(0.1f - f6 * 0.6f);
        this.bipedLeftArm.g = 0.1f - f6 * 0.6f;
        this.bipedRightArm.f = -1.5707964f;
        this.bipedLeftArm.f = -1.5707964f;
        this.bipedRightArm.f -= f6 * 1.2f - f7 * 0.4f;
        this.bipedLeftArm.f -= f6 * 1.2f - f7 * 0.4f;
        this.bipedRightArm.h += ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
        this.bipedLeftArm.h -= ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
        this.bipedRightArm.f += ls.a((float)(f2 * 0.067f)) * 0.05f;
        this.bipedLeftArm.f -= ls.a((float)(f2 * 0.067f)) * 0.05f;
    }
}

