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

public class ModelPonyArmor
extends bbo {
    public bcu head;
    public bcu Body;
    public bcu BodyBack;
    public bcu rightarm;
    public bcu LeftArm;
    public bcu RightLeg;
    public bcu LeftLeg;
    public bcu rightarm2;
    public bcu LeftArm2;
    public bcu RightLeg2;
    public bcu LeftLeg2;
    public boolean isPegasus = false;
    public boolean isUnicorn = false;
    public boolean isSleeping = false;
    public boolean isFlying = false;
    public boolean isGlow = false;
    public boolean isSneak = false;
    public boolean aimedBow;
    public int heldItemRight;
    private boolean rainboom;

    public ModelPonyArmor(float f) {
        this.init(f, 0.0f);
    }

    public void init(float strech, float f) {
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        this.head = new bcu((bbo)this, 0, 0);
        this.head.a(-4.0f, -4.0f, -6.0f, 8, 8, 8, strech);
        this.head.a(f2, f3, f4);
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        this.Body = new bcu((bbo)this, 16, 16);
        this.Body.a(-4.0f, 4.0f, -2.0f, 8, 8, 4, strech);
        this.Body.a(f5, f6 + f, f7);
        this.BodyBack = new bcu((bbo)this, 0, 0);
        this.BodyBack.a(-4.0f, 4.0f, 6.0f, 8, 8, 8, strech);
        this.BodyBack.a(f5, f6 + f, f7);
        this.rightarm = new bcu((bbo)this, 0, 16);
        this.rightarm.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.rightarm.a(-3.0f, 8.0f + f, 0.0f);
        this.LeftArm = new bcu((bbo)this, 0, 16);
        this.LeftArm.i = true;
        this.LeftArm.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.LeftArm.a(3.0f, 8.0f + f, 0.0f);
        this.RightLeg = new bcu((bbo)this, 0, 16);
        this.RightLeg.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.RightLeg.a(-3.0f, 0.0f + f, 0.0f);
        this.LeftLeg = new bcu((bbo)this, 0, 16);
        this.LeftLeg.i = true;
        this.LeftLeg.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.LeftLeg.a(3.0f, 0.0f + f, 0.0f);
        this.rightarm2 = new bcu((bbo)this, 0, 16);
        this.rightarm2.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech * 0.5f);
        this.rightarm2.a(-3.0f, 8.0f + f, 0.0f);
        this.LeftArm2 = new bcu((bbo)this, 0, 16);
        this.LeftArm2.i = true;
        this.LeftArm2.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech * 0.5f);
        this.LeftArm2.a(3.0f, 8.0f + f, 0.0f);
        this.RightLeg2 = new bcu((bbo)this, 0, 16);
        this.RightLeg2.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech * 0.5f);
        this.RightLeg2.a(-3.0f, 0.0f + f, 0.0f);
        this.LeftLeg2 = new bcu((bbo)this, 0, 16);
        this.LeftLeg2.i = true;
        this.LeftLeg2.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech * 0.5f);
        this.LeftLeg2.a(3.0f, 0.0f + f, 0.0f);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float f32;
        float f29;
        float f25;
        float f20;
        float f11;
        float f10;
        float f9;
        float f8;
        float f7;
        float f6;
        this.rainboom = false;
        if (this.isSleeping) {
            f6 = 1.4f;
            f7 = 0.1f;
        } else {
            f6 = f3 / 57.295776f;
            f7 = f4 / 57.295776f;
        }
        this.head.g = f6;
        this.head.f = f7;
        if (this.isFlying && this.isPegasus) {
            if (f1 < 0.9999f) {
                this.rainboom = false;
                f8 = ls.a((float)(0.0f - f1 * 0.5f));
                f9 = ls.a((float)(0.0f - f1 * 0.5f));
                f10 = ls.a((float)(f1 * 0.5f));
                f11 = ls.a((float)(f1 * 0.5f));
            } else {
                this.rainboom = true;
                f8 = 4.712f;
                f9 = 4.712f;
                f10 = 1.571f;
                f11 = 1.571f;
            }
            this.rightarm.g = 0.2f;
            this.LeftArm.g = -0.2f;
            this.RightLeg.g = -0.2f;
            this.LeftLeg.g = 0.2f;
            this.rightarm2.g = 0.2f;
            this.LeftArm2.g = -0.2f;
            this.RightLeg2.g = -0.2f;
            this.LeftLeg2.g = 0.2f;
        } else {
            f8 = ls.b((float)(f * 0.6662f + (float)Math.PI)) * 0.6f * f1;
            f9 = ls.b((float)(f * 0.6662f)) * 0.6f * f1;
            f10 = ls.b((float)(f * 0.6662f)) * 0.3f * f1;
            f11 = ls.b((float)(f * 0.6662f + (float)Math.PI)) * 0.3f * f1;
            this.rightarm.g = 0.0f;
            this.LeftArm.g = 0.0f;
            this.RightLeg.g = 0.0f;
            this.LeftLeg.g = 0.0f;
            this.rightarm2.g = 0.0f;
            this.LeftArm2.g = 0.0f;
            this.RightLeg2.g = 0.0f;
            this.LeftLeg2.g = 0.0f;
        }
        if (this.isSleeping) {
            f8 = 4.712f;
            f9 = 4.712f;
            f10 = 1.571f;
            f11 = 1.571f;
        }
        this.rightarm.f = f8;
        this.LeftArm.f = f9;
        this.RightLeg.f = f10;
        this.LeftLeg.f = f11;
        this.rightarm.h = 0.0f;
        this.LeftArm.h = 0.0f;
        this.rightarm2.f = f8;
        this.LeftArm2.f = f9;
        this.RightLeg2.f = f10;
        this.LeftLeg2.f = f11;
        this.rightarm2.h = 0.0f;
        this.LeftArm2.h = 0.0f;
        if (this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
            this.rightarm.f = this.rightarm.f * 0.5f - 0.31415927f;
            this.rightarm2.f = this.rightarm2.f * 0.5f - 0.31415927f;
        }
        float f12 = 0.0f;
        if (f5 > -9990.0f && !this.isUnicorn) {
            f12 = ls.a((float)(ls.c((float)f5) * (float)Math.PI * 2.0f)) * 0.2f;
        }
        this.Body.g = (float)((double)f12 * 0.2);
        this.BodyBack.g = (float)((double)f12 * 0.2);
        float f13 = ls.a((float)this.Body.g) * 5.0f;
        float f14 = ls.b((float)this.Body.g) * 5.0f;
        float f15 = 4.0f;
        if (this.isSneak && !this.isFlying) {
            f15 = 0.0f;
        }
        if (this.isSleeping) {
            f15 = 2.6f;
        }
        if (this.rainboom) {
            this.rightarm.e = f13 + 2.0f;
            this.rightarm2.e = f13 + 2.0f;
            this.LeftArm.e = 0.0f - f13 + 2.0f;
            this.LeftArm2.e = 0.0f - f13 + 2.0f;
        } else {
            this.rightarm.e = f13 + 1.0f;
            this.rightarm2.e = f13 + 1.0f;
            this.LeftArm.e = 0.0f - f13 + 1.0f;
            this.LeftArm2.e = 0.0f - f13 + 1.0f;
        }
        this.rightarm.c = 0.0f - f14 - 1.0f + f15;
        this.rightarm2.c = 0.0f - f14 - 1.0f + f15;
        this.LeftArm.c = f14 + 1.0f - f15;
        this.LeftArm2.c = f14 + 1.0f - f15;
        this.RightLeg.c = 0.0f - f14 - 1.0f + f15;
        this.RightLeg2.c = 0.0f - f14 - 1.0f + f15;
        this.LeftLeg.c = f14 + 1.0f - f15;
        this.LeftLeg2.c = f14 + 1.0f - f15;
        this.rightarm.g += this.Body.g;
        this.rightarm2.g += this.Body.g;
        this.LeftArm.g += this.Body.g;
        this.LeftArm2.g += this.Body.g;
        this.LeftArm.f += this.Body.g;
        this.LeftArm2.f += this.Body.g;
        this.rightarm.d = 8.0f;
        this.LeftArm.d = 8.0f;
        this.RightLeg.d = 4.0f;
        this.LeftLeg.d = 4.0f;
        this.rightarm2.d = 8.0f;
        this.LeftArm2.d = 8.0f;
        this.RightLeg2.d = 4.0f;
        this.LeftLeg2.d = 4.0f;
        if (f5 > -9990.0f && !this.isUnicorn) {
            f20 = 1.0f - f5;
            f20 *= f20 * f20;
            f20 = 1.0f - f20;
            f25 = ls.a((float)(f20 * (float)Math.PI));
            f29 = ls.a((float)(f5 * (float)Math.PI));
            float f16 = f29 * -(this.head.f - 0.7f) * 0.75f;
        }
        if (this.isSneak && !this.isFlying) {
            float f36;
            float f34;
            f20 = 0.4f;
            f25 = 7.0f;
            f29 = -4.0f;
            this.Body.f = f20;
            this.Body.d = f25;
            this.Body.e = f29;
            this.BodyBack.f = f20;
            this.BodyBack.d = f25;
            this.BodyBack.e = f29;
            this.RightLeg.f -= 0.0f;
            this.LeftLeg.f -= 0.0f;
            this.rightarm.f -= 0.4f;
            this.LeftArm.f -= 0.4f;
            this.RightLeg.e = 10.0f;
            this.LeftLeg.e = 10.0f;
            this.RightLeg.d = 7.0f;
            this.LeftLeg.d = 7.0f;
            this.RightLeg2.f -= 0.0f;
            this.LeftLeg2.f -= 0.0f;
            this.rightarm2.f -= 0.4f;
            this.LeftArm2.f -= 0.4f;
            this.RightLeg2.e = 10.0f;
            this.LeftLeg2.e = 10.0f;
            this.RightLeg2.d = 7.0f;
            this.LeftLeg2.d = 7.0f;
            if (this.isSleeping) {
                f32 = 2.0f;
                f34 = -1.0f;
                f36 = 1.0f;
            } else {
                f32 = 6.0f;
                f34 = -2.0f;
                f36 = 0.0f;
            }
            this.head.d = f32;
            this.head.e = f34;
            this.head.c = f36;
        } else {
            f20 = 0.0f;
            f25 = 0.0f;
            f29 = 0.0f;
            this.Body.f = f20;
            this.Body.d = f25;
            this.Body.e = f29;
            this.BodyBack.f = f20;
            this.BodyBack.d = f25;
            this.BodyBack.e = f29;
            this.RightLeg.e = 10.0f;
            this.LeftLeg.e = 10.0f;
            this.RightLeg.d = 8.0f;
            this.LeftLeg.d = 8.0f;
            this.RightLeg2.e = 10.0f;
            this.LeftLeg2.e = 10.0f;
            this.RightLeg2.d = 8.0f;
            this.LeftLeg2.d = 8.0f;
            f32 = ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
            float f34 = ls.a((float)(f2 * 0.067f)) * 0.05f;
            float f36 = 0.0f;
            float f37 = 0.0f;
            this.head.d = f36;
            this.head.e = f37;
        }
        if (this.isSleeping) {
            this.rightarm.e += 6.0f;
            this.LeftArm.e += 6.0f;
            this.RightLeg.e -= 8.0f;
            this.LeftLeg.e -= 8.0f;
            this.rightarm.d += 2.0f;
            this.LeftArm.d += 2.0f;
            this.RightLeg.d += 2.0f;
            this.LeftLeg.d += 2.0f;
            this.rightarm2.e += 6.0f;
            this.LeftArm2.e += 6.0f;
            this.RightLeg2.e -= 8.0f;
            this.LeftLeg2.e -= 8.0f;
            this.rightarm2.d += 2.0f;
            this.LeftArm2.d += 2.0f;
            this.RightLeg2.d += 2.0f;
            this.LeftLeg2.d += 2.0f;
        }
        if (this.aimedBow && !this.isUnicorn) {
            f20 = 0.0f;
            f25 = 0.0f;
            this.rightarm.h = 0.0f;
            this.rightarm.g = -(0.1f - f20 * 0.6f) + this.head.g;
            this.rightarm.f = 4.712f + this.head.f;
            this.rightarm.f -= f20 * 1.2f - f25 * 0.4f;
            this.rightarm.h += ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
            this.rightarm.f += ls.a((float)(f2 * 0.067f)) * 0.05f;
            this.rightarm2.h = 0.0f;
            this.rightarm2.g = -(0.1f - f20 * 0.6f) + this.head.g;
            this.rightarm2.f = 4.712f + this.head.f;
            this.rightarm2.f -= f20 * 1.2f - f25 * 0.4f;
            this.rightarm2.h += ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
            this.rightarm2.f += ls.a((float)(f2 * 0.067f)) * 0.05f;
            this.rightarm.e += 1.0f;
            this.rightarm2.e += 1.0f;
        }
    }

    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.head.a(f5);
        this.Body.a(f5);
        this.BodyBack.a(f5);
        this.LeftArm.a(f5);
        this.rightarm.a(f5);
        this.LeftLeg.a(f5);
        this.RightLeg.a(f5);
        this.LeftArm2.a(f5);
        this.rightarm2.a(f5);
        this.LeftLeg2.a(f5);
        this.RightLeg2.a(f5);
    }
}

