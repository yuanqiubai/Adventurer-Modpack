/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ls
 *  nn
 */
package noppes.client.model.animation;

import noppes.client.model.ModelMPM;

public class AniHug {
    public static void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity, ModelMPM base) {
        float f6 = ls.a((float)(base.p * (float)Math.PI));
        float f7 = ls.a((float)((1.0f - (1.0f - base.p) * (1.0f - base.p)) * (float)Math.PI));
        base.bipedRightArm.h = 0.0f;
        base.bipedLeftArm.h = 0.0f;
        base.bipedRightArm.g = -(0.1f - f6 * 0.6f);
        base.bipedLeftArm.g = 0.1f;
        base.bipedRightArm.f = -1.5707964f;
        base.bipedLeftArm.f = -1.5707964f;
        base.bipedRightArm.f -= f6 * 1.2f - f7 * 0.4f;
        base.bipedRightArm.h += ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
        base.bipedLeftArm.h -= ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
        base.bipedRightArm.f += ls.a((float)(par3 * 0.067f)) * 0.05f;
        base.bipedLeftArm.f -= ls.a((float)(par3 * 0.067f)) * 0.05f;
    }
}

