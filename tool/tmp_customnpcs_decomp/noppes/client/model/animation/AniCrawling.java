/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ls
 *  nn
 */
package noppes.client.model.animation;

import noppes.client.model.ModelMPM;

public class AniCrawling {
    public static void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity, ModelMPM model) {
        model.bipedHead.h = -par4 / 57.295776f;
        model.bipedHead.g = 0.0f;
        model.bipedHeadwear.f = model.bipedHead.f = -0.95993114f;
        model.bipedHeadwear.g = model.bipedHead.g;
        model.bipedHeadwear.h = model.bipedHead.h;
        if ((double)par2 > 0.25) {
            par2 = 0.25f;
        }
        float movement = ls.b((float)(par1 * 0.8f + (float)Math.PI)) * par2;
        model.bipedLeftArm.f = (float)Math.PI - movement * 0.25f;
        model.bipedLeftArm.g = movement * -0.46f;
        model.bipedLeftArm.h = movement * -0.2f;
        model.bipedLeftArm.d = 2.0f - movement * 9.0f;
        model.bipedRightArm.f = (float)Math.PI + movement * 0.25f;
        model.bipedRightArm.g = movement * -0.4f;
        model.bipedRightArm.h = movement * -0.2f;
        model.bipedRightArm.d = 2.0f + movement * 9.0f;
        model.bipedBody.g = movement * 0.1f;
        model.bipedBody.f = 0.0f;
        model.bipedBody.h = movement * 0.1f;
        model.bipedLeftLeg.f = movement * 0.1f;
        model.bipedLeftLeg.g = movement * 0.1f;
        model.bipedLeftLeg.h = -0.122173056f - movement * 0.25f;
        model.bipedLeftLeg.d = 10.4f + movement * 9.0f;
        model.bipedLeftLeg.e = movement * 0.6f;
        model.bipedRightLeg.f = movement * -0.1f;
        model.bipedRightLeg.g = movement * 0.1f;
        model.bipedRightLeg.h = 0.122173056f - movement * 0.25f;
        model.bipedRightLeg.d = 10.4f - movement * 9.0f;
        model.bipedRightLeg.e = movement * -0.6f;
    }
}

