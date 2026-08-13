/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 */
package noppes.client.model;

import noppes.client.model.ModelNPCMale;

public class ModelFurryMale
extends ModelNPCMale {
    public bcu Snout;
    public bcu Snout2;
    public bcu Tail;
    public bcu LeftEar;
    public bcu RightEar;
    public bcu LeftWing;
    public bcu RightWing;
    public bcu LeftHorn;
    public bcu RightHorn;

    public ModelFurryMale(int width, int height, float f) {
        super(width, height, f);
    }

    @Override
    public void init(float f, float f1) {
        super.init(f, f1);
        this.Snout = new bcu((bbo)this, 0, 32);
        this.Snout.a(0.0f, 0.0f, 0.0f, 4, 3, 3);
        this.Snout.a(-2.0f, -3.0f, -7.0f);
        this.bipedHead.a(this.Snout);
        this.Snout2 = new bcu((bbo)this, 0, 38);
        this.Snout2.a(0.0f, 0.0f, 0.0f, 4, 3, 1);
        this.Snout2.a(-2.0f, -3.0f, -5.0f);
        this.bipedHead.a(this.Snout2);
        this.LeftEar = new bcu((bbo)this, 14, 32);
        this.LeftEar.a(-1.5f, -3.0f, -0.1f, 3, 3, 2);
        this.LeftEar.a(3.0f, -7.5f, 2.0f);
        this.bipedHead.a(this.LeftEar);
        this.RightEar = new bcu((bbo)this, 14, 32);
        this.RightEar.i = true;
        this.RightEar.a(-1.5f, -3.0f, -0.1f, 3, 3, 2);
        this.RightEar.a(-3.0f, -7.5f, 2.0f);
        this.bipedHead.a(this.RightEar);
        this.Tail = new bcu((bbo)this, 24, 32);
        this.Tail.a(0.0f, 0.0f, 0.0f, 2, 9, 2);
        this.Tail.a(-1.0f, 11.0f, 1.0f);
        this.setRotation(this.Tail, 0.8714253f, 0.0f, 0.0f);
        this.bipedBody.a(this.Tail);
        this.LeftWing = new bcu((bbo)this, 32, 32);
        this.LeftWing.i = true;
        this.LeftWing.a(0.0f, 0.0f, 0.0f, 15, 20, 0);
        this.LeftWing.a(1.0f, -2.0f, 0.0f);
        this.setRotation(this.LeftWing, 0.31415927f, -0.5235988f, -0.3490659f);
        this.bipedBody.a(this.LeftWing);
        this.RightWing = new bcu((bbo)this, 32, 32);
        this.RightWing.a(-15.0f, 0.0f, 0.0f, 15, 20, 0);
        this.RightWing.a(-1.0f, -2.0f, 0.0f);
        this.setRotation(this.RightWing, 0.31415927f, 0.5235988f, 0.3490659f);
        this.bipedBody.a(this.RightWing);
        this.LeftHorn = new bcu((bbo)this, 0, 42);
        this.LeftHorn.i = true;
        this.LeftHorn.a(0.0f, 0.0f, 0.0f, 5, 2, 2);
        this.LeftHorn.a(4.0f, -7.0f, 0.0f);
        this.bipedHead.a(this.LeftHorn);
        this.RightHorn = new bcu((bbo)this, 0, 42);
        this.RightHorn.a(0.0f, 0.0f, 0.0f, 5, 2, 2);
        this.RightHorn.a(-9.0f, -7.0f, 0.0f);
        this.bipedHead.a(this.RightHorn);
        bcu earleft = new bcu((bbo)this, 56, 23);
        earleft.i = true;
        earleft.a(-1.466667f, -4.0f, 0.0f, 3, 8, 1);
        earleft.a(2.533333f, -12.0f, 0.0f);
        this.bipedHead.a(earleft);
        bcu earright = new bcu((bbo)this, 56, 23);
        earright.a(-1.5f, -4.0f, 0.0f, 3, 8, 1);
        earright.a(-2.466667f, -12.0f, 0.0f);
        this.bipedHead.a(earright);
        bcu tailbottom = new bcu((bbo)this, 56, 18);
        tailbottom.a(-1.5f, -2.0f, 0.0f, 3, 4, 1);
        tailbottom.a(0.0f, 8.5f, 2.0f);
        tailbottom.b(64, 32);
        tailbottom.i = true;
        this.setRotation(tailbottom, 0.0f, 0.0f, -1.5707964f);
        this.bipedBody.a(tailbottom);
        bcu tailtop = new bcu((bbo)this, 56, 16);
        tailtop.a(0.0f, 0.0f, 0.0f, 2, 1, 1);
        tailtop.a(-1.0f, 6.0f, 2.0f);
        tailtop.b(64, 32);
        tailtop.i = true;
        this.bipedBody.a(tailtop);
        bcu tooth = new bcu((bbo)this, 14, 40);
        tooth.a(-1.0f, -2.0f, -5.0f, 2, 1, 1);
        tooth.a(0.0f, 0.0f, 0.0f);
        this.bipedHead.a(tooth);
        bcu face = new bcu((bbo)this, 14, 37);
        face.a(1.0f, 0.0f, 0.0f, 4, 2, 1);
        face.a(-3.0f, -4.0f, -5.0f);
        this.bipedHead.a(face);
    }
}

