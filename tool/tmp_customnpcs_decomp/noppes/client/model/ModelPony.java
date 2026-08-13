/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  bfq
 *  bgl
 *  ls
 *  nn
 *  org.lwjgl.opengl.GL11
 *  uf
 *  ye
 */
package noppes.client.model;

import noppes.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelPony
extends bbo {
    public bcu Head;
    public bcu[] Headpiece;
    public bcu Helmet;
    public bcu Body;
    public ModelPlaneRenderer[] Bodypiece;
    public bcu RightArm;
    public bcu LeftArm;
    public bcu RightLeg;
    public bcu LeftLeg;
    public bcu unicornarm;
    public ModelPlaneRenderer[] Tail;
    public bcu[] LeftWing;
    public bcu[] RightWing;
    public bcu[] LeftWingExt;
    public bcu[] RightWingExt;
    public boolean isPegasus;
    public boolean isUnicorn;
    public boolean isFlying;
    public boolean isGlow;
    public boolean isSleeping;
    public boolean isSneak;
    public boolean aimedBow;
    public int heldItemRight;
    private boolean rainboom;
    private float WingRotateAngleX;
    private float WingRotateAngleY;
    private float WingRotateAngleZ;
    private float TailRotateAngleY;

    public ModelPony(float f) {
        this.init(f, 0.0f);
    }

    public void init(float strech, float f) {
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        this.Head = new bcu((bbo)this, 0, 0);
        this.Head.a(-4.0f, -4.0f, -6.0f, 8, 8, 8, strech);
        this.Head.a(f2, f3 + f, f4);
        this.Headpiece = new bcu[3];
        this.Headpiece[0] = new bcu((bbo)this, 12, 16);
        this.Headpiece[0].a(-4.0f, -6.0f, -1.0f, 2, 2, 2, strech);
        this.Headpiece[0].a(f2, f3 + f, f4);
        this.Headpiece[1] = new bcu((bbo)this, 12, 16);
        this.Headpiece[1].a(2.0f, -6.0f, -1.0f, 2, 2, 2, strech);
        this.Headpiece[1].a(f2, f3 + f, f4);
        this.Headpiece[2] = new bcu((bbo)this, 56, 0);
        this.Headpiece[2].a(-0.5f, -10.0f, -4.0f, 1, 4, 1, strech);
        this.Headpiece[2].a(f2, f3 + f, f4);
        this.Helmet = new bcu((bbo)this, 32, 0);
        this.Helmet.a(-4.0f, -4.0f, -6.0f, 8, 8, 8, strech + 0.5f);
        this.Helmet.a(f2, f3, f4);
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        this.Body = new bcu((bbo)this, 16, 16);
        this.Body.a(-4.0f, 4.0f, -2.0f, 8, 8, 4, strech);
        this.Body.a(f5, f6 + f, f7);
        this.Bodypiece = new ModelPlaneRenderer[13];
        this.Bodypiece[0] = new ModelPlaneRenderer(this, 24, 0);
        this.Bodypiece[0].addSidePlane(-4.0f, 4.0f, 2.0f, 8, 8, strech);
        this.Bodypiece[0].a(f5, f6 + f, f7);
        this.Bodypiece[1] = new ModelPlaneRenderer(this, 24, 0);
        this.Bodypiece[1].addSidePlane(4.0f, 4.0f, 2.0f, 8, 8, strech);
        this.Bodypiece[1].a(f5, f6 + f, f7);
        this.Bodypiece[2] = new ModelPlaneRenderer(this, 24, 0);
        this.Bodypiece[2].addTopPlane(-4.0f, 4.0f, 2.0f, 8, 8, strech);
        this.Bodypiece[2].a(f2, f3 + f, f4);
        this.Bodypiece[3] = new ModelPlaneRenderer(this, 24, 0);
        this.Bodypiece[3].addTopPlane(-4.0f, 12.0f, 2.0f, 8, 8, strech);
        this.Bodypiece[3].a(f2, f3 + f, f4);
        this.Bodypiece[4] = new ModelPlaneRenderer(this, 0, 20);
        this.Bodypiece[4].addSidePlane(-4.0f, 4.0f, 10.0f, 8, 4, strech);
        this.Bodypiece[4].a(f5, f6 + f, f7);
        this.Bodypiece[5] = new ModelPlaneRenderer(this, 0, 20);
        this.Bodypiece[5].addSidePlane(4.0f, 4.0f, 10.0f, 8, 4, strech);
        this.Bodypiece[5].a(f5, f6 + f, f7);
        this.Bodypiece[6] = new ModelPlaneRenderer(this, 24, 0);
        this.Bodypiece[6].addTopPlane(-4.0f, 4.0f, 10.0f, 8, 4, strech);
        this.Bodypiece[6].a(f2, f3 + f, f4);
        this.Bodypiece[7] = new ModelPlaneRenderer(this, 24, 0);
        this.Bodypiece[7].addTopPlane(-4.0f, 12.0f, 10.0f, 8, 4, strech);
        this.Bodypiece[7].a(f2, f3 + f, f4);
        this.Bodypiece[8] = new ModelPlaneRenderer(this, 24, 0);
        this.Bodypiece[8].addBackPlane(-4.0f, 4.0f, 14.0f, 8, 8, strech);
        this.Bodypiece[8].a(f2, f3 + f, f4);
        this.Bodypiece[9] = new ModelPlaneRenderer(this, 32, 0);
        this.Bodypiece[9].addTopPlane(-1.0f, 10.0f, 8.0f, 2, 6, strech);
        this.Bodypiece[9].a(f2, f3 + f, f4);
        this.Bodypiece[10] = new ModelPlaneRenderer(this, 32, 0);
        this.Bodypiece[10].addTopPlane(-1.0f, 12.0f, 8.0f, 2, 6, strech);
        this.Bodypiece[10].a(f2, f3 + f, f4);
        this.Bodypiece[11] = new ModelPlaneRenderer(this, 32, 0);
        this.Bodypiece[11].i = true;
        this.Bodypiece[11].addSidePlane(-1.0f, 10.0f, 8.0f, 2, 6, strech);
        this.Bodypiece[11].a(f2, f3 + f, f4);
        this.Bodypiece[12] = new ModelPlaneRenderer(this, 32, 0);
        this.Bodypiece[12].addSidePlane(1.0f, 10.0f, 8.0f, 2, 6, strech);
        this.Bodypiece[12].a(f2, f3 + f, f4);
        this.RightArm = new bcu((bbo)this, 40, 16);
        this.RightArm.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.RightArm.a(-3.0f, 8.0f + f, 0.0f);
        this.LeftArm = new bcu((bbo)this, 40, 16);
        this.LeftArm.i = true;
        this.LeftArm.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.LeftArm.a(3.0f, 8.0f + f, 0.0f);
        this.RightLeg = new bcu((bbo)this, 40, 16);
        this.RightLeg.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.RightLeg.a(-3.0f, 0.0f + f, 0.0f);
        this.LeftLeg = new bcu((bbo)this, 40, 16);
        this.LeftLeg.i = true;
        this.LeftLeg.a(-2.0f, 4.0f, -2.0f, 4, 12, 4, strech);
        this.LeftLeg.a(3.0f, 0.0f + f, 0.0f);
        this.unicornarm = new bcu((bbo)this, 40, 16);
        this.unicornarm.a(-3.0f, -2.0f, -2.0f, 4, 12, 4, strech);
        this.unicornarm.a(-5.0f, 2.0f + f, 0.0f);
        float f8 = 0.0f;
        float f9 = 8.0f;
        float f10 = -14.0f;
        float f11 = 0.0f - f8;
        float f12 = 10.0f - f9;
        float f13 = 0.0f;
        this.Tail = new ModelPlaneRenderer[10];
        this.Tail[0] = new ModelPlaneRenderer(this, 32, 0);
        this.Tail[0].addTopPlane(-2.0f + f8, -7.0f + f9, 16.0f + f10, 4, 4, strech);
        this.Tail[0].a(f11, f12 + f, f13);
        this.Tail[1] = new ModelPlaneRenderer(this, 32, 0);
        this.Tail[1].addTopPlane(-2.0f + f8, 9.0f + f9, 16.0f + f10, 4, 4, strech);
        this.Tail[1].a(f11, f12 + f, f13);
        this.Tail[2] = new ModelPlaneRenderer(this, 32, 0);
        this.Tail[2].addBackPlane(-2.0f + f8, -7.0f + f9, 16.0f + f10, 4, 8, strech);
        this.Tail[2].a(f11, f12 + f, f13);
        this.Tail[3] = new ModelPlaneRenderer(this, 32, 0);
        this.Tail[3].addBackPlane(-2.0f + f8, -7.0f + f9, 20.0f + f10, 4, 8, strech);
        this.Tail[3].a(f11, f12 + f, f13);
        this.Tail[4] = new ModelPlaneRenderer(this, 32, 0);
        this.Tail[4].addBackPlane(-2.0f + f8, 1.0f + f9, 16.0f + f10, 4, 8, strech);
        this.Tail[4].a(f11, f12 + f, f13);
        this.Tail[5] = new ModelPlaneRenderer(this, 32, 0);
        this.Tail[5].addBackPlane(-2.0f + f8, 1.0f + f9, 20.0f + f10, 4, 8, strech);
        this.Tail[5].a(f11, f12 + f, f13);
        this.Tail[6] = new ModelPlaneRenderer(this, 36, 0);
        this.Tail[6].i = true;
        this.Tail[6].addSidePlane(2.0f + f8, -7.0f + f9, 16.0f + f10, 8, 4, strech);
        this.Tail[6].a(f11, f12 + f, f13);
        this.Tail[7] = new ModelPlaneRenderer(this, 36, 0);
        this.Tail[7].addSidePlane(-2.0f + f8, -7.0f + f9, 16.0f + f10, 8, 4, strech);
        this.Tail[7].a(f11, f12 + f, f13);
        this.Tail[8] = new ModelPlaneRenderer(this, 36, 0);
        this.Tail[8].i = true;
        this.Tail[8].addSidePlane(2.0f + f8, 1.0f + f9, 16.0f + f10, 8, 4, strech);
        this.Tail[8].a(f11, f12 + f, f13);
        this.Tail[9] = new ModelPlaneRenderer(this, 36, 0);
        this.Tail[9].addSidePlane(-2.0f + f8, 1.0f + f9, 16.0f + f10, 8, 4, strech);
        this.Tail[9].a(f11, f12 + f, f13);
        this.TailRotateAngleY = this.Tail[0].g;
        this.TailRotateAngleY = this.Tail[0].g;
        float f14 = 0.0f;
        float f15 = 0.0f;
        float f16 = 0.0f;
        this.LeftWing = new bcu[3];
        this.LeftWing[0] = new bcu((bbo)this, 56, 16);
        this.LeftWing[0].i = true;
        this.LeftWing[0].a(4.0f, 5.0f, 2.0f, 2, 6, 2, strech);
        this.LeftWing[0].a(f14, f15 + f, f16);
        this.LeftWing[1] = new bcu((bbo)this, 56, 16);
        this.LeftWing[1].i = true;
        this.LeftWing[1].a(4.0f, 5.0f, 4.0f, 2, 8, 2, strech);
        this.LeftWing[1].a(f14, f15 + f, f16);
        this.LeftWing[2] = new bcu((bbo)this, 56, 16);
        this.LeftWing[2].i = true;
        this.LeftWing[2].a(4.0f, 5.0f, 6.0f, 2, 6, 2, strech);
        this.LeftWing[2].a(f14, f15 + f, f16);
        this.RightWing = new bcu[3];
        this.RightWing[0] = new bcu((bbo)this, 56, 16);
        this.RightWing[0].a(-6.0f, 5.0f, 2.0f, 2, 6, 2, strech);
        this.RightWing[0].a(f14, f15 + f, f16);
        this.RightWing[1] = new bcu((bbo)this, 56, 16);
        this.RightWing[1].a(-6.0f, 5.0f, 4.0f, 2, 8, 2, strech);
        this.RightWing[1].a(f14, f15 + f, f16);
        this.RightWing[2] = new bcu((bbo)this, 56, 16);
        this.RightWing[2].a(-6.0f, 5.0f, 6.0f, 2, 6, 2, strech);
        this.RightWing[2].a(f14, f15 + f, f16);
        float f17 = f2 + 4.5f;
        float f18 = f3 + 5.0f;
        float f19 = f4 + 6.0f;
        this.LeftWingExt = new bcu[7];
        this.LeftWingExt[0] = new bcu((bbo)this, 56, 19);
        this.LeftWingExt[0].i = true;
        this.LeftWingExt[0].a(0.0f, 0.0f, 0.0f, 1, 8, 2, strech + 0.1f);
        this.LeftWingExt[0].a(f17, f18 + f, f19);
        this.LeftWingExt[1] = new bcu((bbo)this, 56, 19);
        this.LeftWingExt[1].i = true;
        this.LeftWingExt[1].a(0.0f, 8.0f, 0.0f, 1, 6, 2, strech + 0.1f);
        this.LeftWingExt[1].a(f17, f18 + f, f19);
        this.LeftWingExt[2] = new bcu((bbo)this, 56, 19);
        this.LeftWingExt[2].i = true;
        this.LeftWingExt[2].a(0.0f, -1.2f, -0.2f, 1, 8, 2, strech - 0.2f);
        this.LeftWingExt[2].a(f17, f18 + f, f19);
        this.LeftWingExt[3] = new bcu((bbo)this, 56, 19);
        this.LeftWingExt[3].i = true;
        this.LeftWingExt[3].a(0.0f, 1.8f, 1.3f, 1, 8, 2, strech - 0.1f);
        this.LeftWingExt[3].a(f17, f18 + f, f19);
        this.LeftWingExt[4] = new bcu((bbo)this, 56, 19);
        this.LeftWingExt[4].i = true;
        this.LeftWingExt[4].a(0.0f, 5.0f, 2.0f, 1, 8, 2, strech);
        this.LeftWingExt[4].a(f17, f18 + f, f19);
        this.LeftWingExt[5] = new bcu((bbo)this, 56, 19);
        this.LeftWingExt[5].i = true;
        this.LeftWingExt[5].a(0.0f, 0.0f, -0.2f, 1, 6, 2, strech + 0.3f);
        this.LeftWingExt[5].a(f17, f18 + f, f19);
        this.LeftWingExt[6] = new bcu((bbo)this, 56, 19);
        this.LeftWingExt[6].i = true;
        this.LeftWingExt[6].a(0.0f, 0.0f, 0.2f, 1, 3, 2, strech + 0.2f);
        this.LeftWingExt[6].a(f17, f18 + f, f19);
        float f20 = f2 - 4.5f;
        float f21 = f3 + 5.0f;
        float f22 = f4 + 6.0f;
        this.RightWingExt = new bcu[7];
        this.RightWingExt[0] = new bcu((bbo)this, 56, 19);
        this.RightWingExt[0].i = true;
        this.RightWingExt[0].a(0.0f, 0.0f, 0.0f, 1, 8, 2, strech + 0.1f);
        this.RightWingExt[0].a(f20, f21 + f, f22);
        this.RightWingExt[1] = new bcu((bbo)this, 56, 19);
        this.RightWingExt[1].i = true;
        this.RightWingExt[1].a(0.0f, 8.0f, 0.0f, 1, 6, 2, strech + 0.1f);
        this.RightWingExt[1].a(f20, f21 + f, f22);
        this.RightWingExt[2] = new bcu((bbo)this, 56, 19);
        this.RightWingExt[2].i = true;
        this.RightWingExt[2].a(0.0f, -1.2f, -0.2f, 1, 8, 2, strech - 0.2f);
        this.RightWingExt[2].a(f20, f21 + f, f22);
        this.RightWingExt[3] = new bcu((bbo)this, 56, 19);
        this.RightWingExt[3].i = true;
        this.RightWingExt[3].a(0.0f, 1.8f, 1.3f, 1, 8, 2, strech - 0.1f);
        this.RightWingExt[3].a(f20, f21 + f, f22);
        this.RightWingExt[4] = new bcu((bbo)this, 56, 19);
        this.RightWingExt[4].i = true;
        this.RightWingExt[4].a(0.0f, 5.0f, 2.0f, 1, 8, 2, strech);
        this.RightWingExt[4].a(f20, f21 + f, f22);
        this.RightWingExt[5] = new bcu((bbo)this, 56, 19);
        this.RightWingExt[5].i = true;
        this.RightWingExt[5].a(0.0f, 0.0f, -0.2f, 1, 6, 2, strech + 0.3f);
        this.RightWingExt[5].a(f20, f21 + f, f22);
        this.RightWingExt[6] = new bcu((bbo)this, 56, 19);
        this.RightWingExt[6].i = true;
        this.RightWingExt[6].a(0.0f, 0.0f, 0.2f, 1, 3, 2, strech + 0.2f);
        this.RightWingExt[6].a(f20, f21 + f, f22);
        this.WingRotateAngleX = this.LeftWingExt[0].f;
        this.WingRotateAngleY = this.LeftWingExt[0].g;
        this.WingRotateAngleZ = this.LeftWingExt[0].h;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float f61;
        float f59;
        float f57;
        float f55;
        float f53;
        float f51;
        float f49;
        float var40;
        int f47;
        float f45;
        float var39;
        int f42;
        float f39;
        int var38;
        float f36;
        float f32;
        float f27;
        float f21;
        int f13;
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
        this.Head.g = f6;
        this.Head.f = f7;
        this.Headpiece[0].g = f6;
        this.Headpiece[0].f = f7;
        this.Headpiece[1].g = f6;
        this.Headpiece[1].f = f7;
        this.Headpiece[2].g = f6;
        this.Headpiece[2].f = f7;
        this.Helmet.g = f6;
        this.Helmet.f = f7;
        this.Headpiece[2].f = f7 + 0.5f;
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
            this.RightArm.g = 0.2f;
            this.LeftArm.g = -0.2f;
            this.RightLeg.g = -0.2f;
            this.LeftLeg.g = 0.2f;
        } else {
            f8 = ls.b((float)(f * 0.6662f + (float)Math.PI)) * 0.6f * f1;
            f9 = ls.b((float)(f * 0.6662f)) * 0.6f * f1;
            f10 = ls.b((float)(f * 0.6662f)) * 0.3f * f1;
            f11 = ls.b((float)(f * 0.6662f + (float)Math.PI)) * 0.3f * f1;
            this.RightArm.g = 0.0f;
            this.unicornarm.g = 0.0f;
            this.LeftArm.g = 0.0f;
            this.RightLeg.g = 0.0f;
            this.LeftLeg.g = 0.0f;
        }
        if (this.isSleeping) {
            f8 = 4.712f;
            f9 = 4.712f;
            f10 = 1.571f;
            f11 = 1.571f;
        }
        this.RightArm.f = f8;
        this.unicornarm.f = 0.0f;
        this.LeftArm.f = f9;
        this.RightLeg.f = f10;
        this.LeftLeg.f = f11;
        this.RightArm.h = 0.0f;
        this.unicornarm.h = 0.0f;
        this.LeftArm.h = 0.0f;
        for (int f12 = 0; f12 < this.Tail.length; ++f12) {
            this.Tail[f12].h = this.rainboom ? 0.0f : ls.b((float)(f * 0.8f)) * 0.2f * f1;
        }
        if (this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
            this.RightArm.f = this.RightArm.f * 0.5f - 0.31415927f;
        }
        float var35 = 0.0f;
        if (f5 > -9990.0f && !this.isUnicorn) {
            var35 = ls.a((float)(ls.c((float)f5) * (float)Math.PI * 2.0f)) * 0.2f;
        }
        this.Body.g = (float)((double)var35 * 0.2);
        for (f13 = 0; f13 < this.Bodypiece.length; ++f13) {
            this.Bodypiece[f13].g = (float)((double)var35 * 0.2);
        }
        for (f13 = 0; f13 < this.LeftWing.length; ++f13) {
            this.LeftWing[f13].g = (float)((double)var35 * 0.2);
        }
        for (f13 = 0; f13 < this.RightWing.length; ++f13) {
            this.RightWing[f13].g = (float)((double)var35 * 0.2);
        }
        for (f13 = 0; f13 < this.Tail.length; ++f13) {
            this.Tail[f13].g = var35;
        }
        float var36 = ls.a((float)this.Body.g) * 5.0f;
        float f14 = ls.b((float)this.Body.g) * 5.0f;
        float f15 = 4.0f;
        if (this.isSneak && !this.isFlying) {
            f15 = 0.0f;
        }
        if (this.isSleeping) {
            f15 = 2.6f;
        }
        if (this.rainboom) {
            this.RightArm.e = var36 + 2.0f;
            this.LeftArm.e = 0.0f - var36 + 2.0f;
        } else {
            this.RightArm.e = var36 + 1.0f;
            this.LeftArm.e = 0.0f - var36 + 1.0f;
        }
        this.RightArm.c = 0.0f - f14 - 1.0f + f15;
        this.LeftArm.c = f14 + 1.0f - f15;
        this.RightLeg.c = 0.0f - f14 - 1.0f + f15;
        this.LeftLeg.c = f14 + 1.0f - f15;
        this.RightArm.g += this.Body.g;
        this.LeftArm.g += this.Body.g;
        this.LeftArm.f += this.Body.g;
        this.RightArm.d = 8.0f;
        this.LeftArm.d = 8.0f;
        this.RightLeg.d = 4.0f;
        this.LeftLeg.d = 4.0f;
        if (f5 > -9990.0f) {
            f21 = 1.0f - f5;
            f21 *= f21 * f21;
            f21 = 1.0f - f21;
            f27 = ls.a((float)(f21 * (float)Math.PI));
            f32 = ls.a((float)(f5 * (float)Math.PI));
            f36 = f32 * -(this.Head.f - 0.7f) * 0.75f;
            if (this.isUnicorn) {
                this.unicornarm.f = (float)((double)this.unicornarm.f - ((double)f27 * 1.2 + (double)f36));
                this.unicornarm.g += this.Body.g * 2.0f;
                this.unicornarm.h = f32 * -0.4f;
            } else {
                this.unicornarm.f = (float)((double)this.unicornarm.f - ((double)f27 * 1.2 + (double)f36));
                this.unicornarm.g += this.Body.g * 2.0f;
                this.unicornarm.h = f32 * -0.4f;
            }
        }
        if (this.isSneak && !this.isFlying) {
            f21 = 0.4f;
            f27 = 7.0f;
            f32 = -4.0f;
            this.Body.f = f21;
            this.Body.d = f27;
            this.Body.e = f32;
            for (var38 = 0; var38 < this.Bodypiece.length; ++var38) {
                this.Bodypiece[var38].f = f21;
                this.Bodypiece[var38].d = f27;
                this.Bodypiece[var38].e = f32;
            }
            f36 = 3.5f;
            f39 = 6.0f;
            for (f42 = 0; f42 < this.LeftWingExt.length; ++f42) {
                this.LeftWingExt[f42].f = (float)((double)f21 + 2.3561947345733643);
                this.LeftWingExt[f42].d = f27 + f36;
                this.LeftWingExt[f42].e = f32 + f39;
                this.LeftWingExt[f42].f = 2.5f;
                this.LeftWingExt[f42].h = -6.0f;
            }
            var39 = 4.5f;
            f45 = 6.0f;
            for (f47 = 0; f47 < this.LeftWingExt.length; ++f47) {
                this.RightWingExt[f47].f = (float)((double)f21 + 2.3561947345733643);
                this.RightWingExt[f47].d = f27 + var39;
                this.RightWingExt[f47].e = f32 + f45;
                this.RightWingExt[f47].f = 2.5f;
                this.RightWingExt[f47].h = 6.0f;
            }
            this.RightLeg.f -= 0.0f;
            this.LeftLeg.f -= 0.0f;
            this.RightArm.f -= 0.4f;
            this.unicornarm.f += 0.4f;
            this.LeftArm.f -= 0.4f;
            this.RightLeg.e = 10.0f;
            this.LeftLeg.e = 10.0f;
            this.RightLeg.d = 7.0f;
            this.LeftLeg.d = 7.0f;
            if (this.isSleeping) {
                var40 = 2.0f;
                f49 = -1.0f;
                f51 = 1.0f;
            } else {
                var40 = 6.0f;
                f49 = -2.0f;
                f51 = 0.0f;
            }
            this.Head.d = var40;
            this.Head.e = f49;
            this.Head.c = f51;
            this.Helmet.d = var40;
            this.Helmet.e = f49;
            this.Helmet.c = f51;
            this.Headpiece[0].d = var40;
            this.Headpiece[0].e = f49;
            this.Headpiece[0].c = f51;
            this.Headpiece[1].d = var40;
            this.Headpiece[1].e = f49;
            this.Headpiece[1].c = f51;
            this.Headpiece[2].d = var40;
            this.Headpiece[2].e = f49;
            this.Headpiece[2].c = f51;
            f53 = 0.0f;
            f55 = 8.0f;
            f57 = -14.0f;
            f59 = 0.0f - f53;
            f61 = 9.0f - f55;
            float var41 = -4.0f - f57;
            float f63 = 0.0f;
            for (int i6 = 0; i6 < this.Tail.length; ++i6) {
                this.Tail[i6].c = f59;
                this.Tail[i6].d = f61;
                this.Tail[i6].e = var41;
                this.Tail[i6].f = f63;
            }
        } else {
            int l5;
            f21 = 0.0f;
            f27 = 0.0f;
            f32 = 0.0f;
            this.Body.f = f21;
            this.Body.d = f27;
            this.Body.e = f32;
            for (var38 = 0; var38 < this.Bodypiece.length; ++var38) {
                this.Bodypiece[var38].f = f21;
                this.Bodypiece[var38].d = f27;
                this.Bodypiece[var38].e = f32;
            }
            if (this.isPegasus) {
                if (!this.isFlying) {
                    for (var38 = 0; var38 < this.LeftWing.length; ++var38) {
                        this.LeftWing[var38].f = (float)((double)f21 + 1.5707963267948966);
                        this.LeftWing[var38].d = f27 + 13.0f;
                        this.LeftWing[var38].e = f32 - 3.0f;
                    }
                    for (var38 = 0; var38 < this.RightWing.length; ++var38) {
                        this.RightWing[var38].f = (float)((double)f21 + 1.5707963267948966);
                        this.RightWing[var38].d = f27 + 13.0f;
                        this.RightWing[var38].e = f32 - 3.0f;
                    }
                } else {
                    f36 = 5.5f;
                    f39 = 3.0f;
                    for (f42 = 0; f42 < this.LeftWingExt.length; ++f42) {
                        this.LeftWingExt[f42].f = (float)((double)f21 + 1.5707963267948966);
                        this.LeftWingExt[f42].d = f27 + f36;
                        this.LeftWingExt[f42].e = f32 + f39;
                    }
                    var39 = 6.5f;
                    f45 = 3.0f;
                    for (f47 = 0; f47 < this.RightWingExt.length; ++f47) {
                        this.RightWingExt[f47].f = (float)((double)f21 + 1.5707963267948966);
                        this.RightWingExt[f47].d = f27 + var39;
                        this.RightWingExt[f47].e = f32 + f45;
                    }
                }
            }
            this.RightLeg.e = 10.0f;
            this.LeftLeg.e = 10.0f;
            this.RightLeg.d = 8.0f;
            this.LeftLeg.d = 8.0f;
            f36 = ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
            f39 = ls.a((float)(f2 * 0.067f)) * 0.05f;
            this.unicornarm.h += f36;
            this.unicornarm.f += f39;
            if (this.isPegasus && this.isFlying) {
                this.WingRotateAngleY = ls.a((float)(f2 * 0.067f * 8.0f));
                this.WingRotateAngleZ = ls.a((float)(f2 * 0.067f * 8.0f));
                for (f42 = 0; f42 < this.LeftWingExt.length; ++f42) {
                    this.LeftWingExt[f42].f = 2.5f;
                    this.LeftWingExt[f42].h = -this.WingRotateAngleZ - 4.712f - 0.4f;
                }
                for (f42 = 0; f42 < this.RightWingExt.length; ++f42) {
                    this.RightWingExt[f42].f = 2.5f;
                    this.RightWingExt[f42].h = this.WingRotateAngleZ + 4.712f + 0.4f;
                }
            }
            if (this.isSleeping) {
                var39 = 2.0f;
                f45 = 1.0f;
                var40 = 1.0f;
            } else {
                var39 = 0.0f;
                f45 = 0.0f;
                var40 = 0.0f;
            }
            this.Head.d = var39;
            this.Head.e = f45;
            this.Head.c = var40;
            this.Helmet.d = var39;
            this.Helmet.e = f45;
            this.Helmet.c = var40;
            this.Headpiece[0].d = var39;
            this.Headpiece[0].e = f45;
            this.Headpiece[0].c = var40;
            this.Headpiece[1].d = var39;
            this.Headpiece[1].e = f45;
            this.Headpiece[1].c = var40;
            this.Headpiece[2].d = var39;
            this.Headpiece[2].e = f45;
            this.Headpiece[2].c = var40;
            f49 = 0.0f;
            f51 = 8.0f;
            f53 = -14.0f;
            f55 = 0.0f - f49;
            f57 = 9.0f - f51;
            f59 = 0.0f - f53;
            f61 = 0.5f * f1;
            for (l5 = 0; l5 < this.Tail.length; ++l5) {
                this.Tail[l5].c = f55;
                this.Tail[l5].d = f57;
                this.Tail[l5].e = f59;
                this.Tail[l5].f = this.rainboom ? 1.571f + 0.1f * ls.a((float)f) : f61;
            }
            for (l5 = 0; l5 < this.Tail.length; ++l5) {
                if (this.rainboom) continue;
                this.Tail[l5].f += f39;
            }
        }
        this.LeftWingExt[2].f -= 0.85f;
        this.LeftWingExt[3].f -= 0.75f;
        this.LeftWingExt[4].f -= 0.5f;
        this.LeftWingExt[6].f -= 0.85f;
        this.RightWingExt[2].f -= 0.85f;
        this.RightWingExt[3].f -= 0.75f;
        this.RightWingExt[4].f -= 0.5f;
        this.RightWingExt[6].f -= 0.85f;
        this.Bodypiece[9].f += 0.5f;
        this.Bodypiece[10].f += 0.5f;
        this.Bodypiece[11].f += 0.5f;
        this.Bodypiece[12].f += 0.5f;
        if (this.rainboom) {
            for (int var37 = 0; var37 < this.Tail.length; ++var37) {
                this.Tail[var37].d += 6.0f;
                this.Tail[var37].e += 1.0f;
            }
        }
        if (this.isSleeping) {
            this.RightArm.e += 6.0f;
            this.LeftArm.e += 6.0f;
            this.RightLeg.e -= 8.0f;
            this.LeftLeg.e -= 8.0f;
            this.RightArm.d += 2.0f;
            this.LeftArm.d += 2.0f;
            this.RightLeg.d += 2.0f;
            this.LeftLeg.d += 2.0f;
        }
        if (this.aimedBow) {
            if (this.isUnicorn) {
                f21 = 0.0f;
                f27 = 0.0f;
                this.unicornarm.h = 0.0f;
                this.unicornarm.g = -(0.1f - f21 * 0.6f) + this.Head.g;
                this.unicornarm.f = 4.712f + this.Head.f;
                this.unicornarm.f -= f21 * 1.2f - f27 * 0.4f;
                this.unicornarm.h += ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
                this.unicornarm.f += ls.a((float)(f2 * 0.067f)) * 0.05f;
            } else {
                f21 = 0.0f;
                f27 = 0.0f;
                this.RightArm.h = 0.0f;
                this.RightArm.g = -(0.1f - f21 * 0.6f) + this.Head.g;
                this.RightArm.f = 4.712f + this.Head.f;
                this.RightArm.f -= f21 * 1.2f - f27 * 0.4f;
                this.RightArm.h += ls.b((float)(f2 * 0.09f)) * 0.05f + 0.05f;
                this.RightArm.f += ls.a((float)(f2 * 0.067f)) * 0.05f;
                this.RightArm.e += 1.0f;
            }
        }
    }

    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        int j1;
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        GL11.glPushMatrix();
        if (this.isSleeping) {
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.5f, (float)-0.9f);
        }
        float scale = f5;
        this.Head.a(f5);
        this.Headpiece[0].a(f5);
        this.Headpiece[1].a(f5);
        if (this.isUnicorn) {
            this.Headpiece[2].a(f5);
        }
        this.Helmet.a(f5);
        this.Body.a(f5);
        for (j1 = 0; j1 < this.Bodypiece.length; ++j1) {
            this.Bodypiece[j1].a(scale);
        }
        this.LeftArm.a(scale);
        this.RightArm.a(scale);
        this.LeftLeg.a(scale);
        this.RightLeg.a(scale);
        for (j1 = 0; j1 < this.Tail.length; ++j1) {
            this.Tail[j1].a(scale);
        }
        if (this.isPegasus) {
            if (!this.isFlying && !this.isSneak) {
                for (j1 = 0; j1 < this.LeftWing.length; ++j1) {
                    this.LeftWing[j1].a(scale);
                }
                for (j1 = 0; j1 < this.RightWing.length; ++j1) {
                    this.RightWing[j1].a(scale);
                }
            } else {
                for (j1 = 0; j1 < this.LeftWingExt.length; ++j1) {
                    this.LeftWingExt[j1].a(scale);
                }
                for (j1 = 0; j1 < this.RightWingExt.length; ++j1) {
                    this.RightWingExt[j1].a(scale);
                }
            }
        }
        GL11.glPopMatrix();
    }

    protected void renderGlow(bgl rendermanager, uf entityplayer) {
        ye itemstack = entityplayer.bn.h();
        if (itemstack != null) {
            GL11.glPushMatrix();
            double d = entityplayer.u;
            double d1 = entityplayer.v;
            double d2 = entityplayer.w;
            GL11.glEnable((int)32826);
            GL11.glTranslatef((float)((float)d + 0.0f), (float)((float)d1 + 2.3f), (float)((float)d2));
            GL11.glScalef((float)5.0f, (float)5.0f, (float)5.0f);
            GL11.glRotatef((float)(-rendermanager.j), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)rendermanager.k, (float)1.0f, (float)0.0f, (float)0.0f);
            bfq tessellator = bfq.a;
            float f = 0.0f;
            float f1 = 0.25f;
            float f2 = 0.0f;
            float f3 = 0.25f;
            float f4 = 1.0f;
            float f5 = 0.5f;
            float f6 = 0.25f;
            tessellator.b();
            tessellator.b(0.0f, 1.0f, 0.0f);
            tessellator.a(-1.0, -1.0, 0.0, 0.0, 1.0);
            tessellator.a(-1.0, 1.0, 0.0, 1.0, 1.0);
            tessellator.a(1.0, 1.0, 0.0, 1.0, 0.0);
            tessellator.a(1.0, -1.0, 0.0, 0.0, 0.0);
            tessellator.a();
            GL11.glDisable((int)32826);
            GL11.glPopMatrix();
        }
    }
}

