/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 */
package noppes.client.model.part.legs;

import noppes.client.model.ModelMPM;

public class ModelDigitigradeLegs
extends bcu {
    public boolean isRiding = false;
    public boolean isSneaking = false;
    public boolean isSleeping = false;
    public boolean isCrawling = false;
    private final bcu rightleg;
    private final bcu rightleg2;
    private final bcu rightleglow;
    private final bcu rightfoot;
    private final bcu leftleg;
    private final bcu leftleg2;
    private final bcu leftleglow;
    private final bcu leftfoot;
    private final ModelMPM base;

    public ModelDigitigradeLegs(ModelMPM base) {
        super((bbo)base);
        this.base = base;
        this.rightleg = new bcu((bbo)base, 0, 16);
        this.rightleg.a(-2.0f, 0.0f, -2.0f, 4, 6, 4);
        this.rightleg.a(-2.1f, 11.0f, 0.0f);
        this.setRotation(this.rightleg, -0.3f, 0.0f, 0.0f);
        this.a(this.rightleg);
        this.rightleg2 = new bcu((bbo)base, 0, 20);
        this.rightleg2.a(-1.5f, -1.0f, -2.0f, 3, 7, 3);
        this.rightleg2.a(0.0f, 4.1f, 0.0f);
        this.setRotation(this.rightleg2, 1.1f, 0.0f, 0.0f);
        this.rightleg.a(this.rightleg2);
        this.rightleglow = new bcu((bbo)base, 0, 24);
        this.rightleglow.a(-1.5f, 0.0f, -1.0f, 3, 5, 2);
        this.rightleglow.a(0.0f, 5.0f, 0.0f);
        this.setRotation(this.rightleglow, -1.35f, 0.0f, 0.0f);
        this.rightleg2.a(this.rightleglow);
        this.rightfoot = new bcu((bbo)base, 1, 26);
        this.rightfoot.a(-1.5f, 0.0f, -5.0f, 3, 2, 4);
        this.rightfoot.a(0.0f, 3.7f, 1.2f);
        this.setRotation(this.rightfoot, 0.55f, 0.0f, 0.0f);
        this.rightleglow.a(this.rightfoot);
        this.leftleg = new bcu((bbo)base, 0, 16);
        this.leftleg.i = true;
        this.leftleg.a(-2.0f, 0.0f, -2.0f, 4, 6, 4);
        this.leftleg.a(2.1f, 11.0f, 0.0f);
        this.setRotation(this.leftleg, -0.3f, 0.0f, 0.0f);
        this.a(this.leftleg);
        this.leftleg2 = new bcu((bbo)base, 0, 20);
        this.leftleg2.i = true;
        this.leftleg2.a(-1.5f, -1.0f, -2.0f, 3, 7, 3);
        this.leftleg2.a(0.0f, 4.1f, 0.0f);
        this.setRotation(this.leftleg2, 1.1f, 0.0f, 0.0f);
        this.leftleg.a(this.leftleg2);
        this.leftleglow = new bcu((bbo)base, 0, 24);
        this.leftleglow.i = true;
        this.leftleglow.a(-1.5f, 0.0f, -1.0f, 3, 5, 2);
        this.leftleglow.a(0.0f, 5.0f, 0.0f);
        this.setRotation(this.leftleglow, -1.35f, 0.0f, 0.0f);
        this.leftleg2.a(this.leftleglow);
        this.leftfoot = new bcu((bbo)base, 1, 26);
        this.leftfoot.i = true;
        this.leftfoot.a(-1.5f, 0.0f, -5.0f, 3, 2, 4);
        this.leftfoot.a(0.0f, 3.7f, 1.2f);
        this.setRotation(this.leftfoot, 0.55f, 0.0f, 0.0f);
        this.leftleglow.a(this.leftfoot);
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        this.rightleg.f = this.base.bipedRightLeg.f - 0.3f;
        this.leftleg.f = this.base.bipedLeftLeg.f - 0.3f;
        this.rightleg.d = this.base.bipedRightLeg.d;
        this.leftleg.d = this.base.bipedLeftLeg.d;
        this.rightleg.e = this.base.bipedRightLeg.e;
        this.leftleg.e = this.base.bipedLeftLeg.e;
        if (!this.base.isSneak) {
            this.leftleg.d -= 1.0f;
            this.rightleg.d -= 1.0f;
        }
    }

    private void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }
}

