/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 */
package noppes.client.model;

import noppes.client.model.ModelZombieMale;

public class ModelNpcSkeleton
extends ModelZombieMale {
    public ModelNpcSkeleton(float f) {
        super(f);
        this.bipedRightArm = new bcu((bbo)this, 40, 16);
        this.bipedRightArm.a(-1.0f, -2.0f, -1.0f, 2, 12, 2, f);
        this.bipedRightArm.a(-5.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new bcu((bbo)this, 40, 16);
        this.bipedLeftArm.i = true;
        this.bipedLeftArm.a(-1.0f, -2.0f, -1.0f, 2, 12, 2, f);
        this.bipedLeftArm.a(5.0f, 2.0f, 0.0f);
        this.bipedRightLeg = new bcu((bbo)this, 0, 16);
        this.bipedRightLeg.a(-1.0f, 0.0f, -1.0f, 2, 12, 2, f);
        this.bipedRightLeg.a(-2.0f, 12.0f, 0.0f);
        this.bipedLeftLeg = new bcu((bbo)this, 0, 16);
        this.bipedLeftLeg.i = true;
        this.bipedLeftLeg.a(-1.0f, 0.0f, -1.0f, 2, 12, 2, f);
        this.bipedLeftLeg.a(2.0f, 12.0f, 0.0f);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.aimedBow = true;
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }
}

