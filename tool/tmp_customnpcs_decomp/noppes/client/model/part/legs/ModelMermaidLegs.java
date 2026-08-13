/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  ls
 *  nn
 */
package noppes.client.model.part.legs;

public class ModelMermaidLegs
extends bcu {
    bcu top;
    bcu middle;
    bcu bottom;
    bcu fin1;
    bcu fin2;

    public ModelMermaidLegs(bbo base) {
        super(base);
        this.a = 64.0f;
        this.b = 32.0f;
        this.top = new bcu(base, 0, 16);
        this.top.a(-2.0f, -2.5f, -2.0f, 8, 9, 4);
        this.top.a(-2.0f, 14.0f, 1.0f);
        this.setRotation(this.top, 0.26f, 0.0f, 0.0f);
        this.middle = new bcu(base, 28, 0);
        this.middle.a(0.0f, 0.0f, 0.0f, 7, 6, 4);
        this.middle.a(-1.5f, 6.5f, -1.0f);
        this.setRotation(this.middle, 0.86f, 0.0f, 0.0f);
        this.top.a(this.middle);
        this.bottom = new bcu(base, 24, 16);
        this.bottom.a(0.0f, 0.0f, 0.0f, 6, 7, 3);
        this.bottom.a(0.5f, 6.0f, 0.5f);
        this.setRotation(this.bottom, 0.15f, 0.0f, 0.0f);
        this.middle.a(this.bottom);
        this.fin1 = new bcu(base, 0, 0);
        this.fin1.a(0.0f, 0.0f, 0.0f, 5, 9, 1);
        this.fin1.a(0.0f, 4.5f, 1.0f);
        this.setRotation(this.fin1, 0.05f, 0.0f, 0.5911399f);
        this.bottom.a(this.fin1);
        this.fin2 = new bcu(base, 0, 0);
        this.fin2.i = true;
        this.fin2.a(-5.0f, 0.0f, 0.0f, 5, 9, 1);
        this.fin2.a(6.0f, 4.5f, 1.0f);
        this.setRotation(this.fin2, 0.05f, 0.0f, -0.591143f);
        this.bottom.a(this.fin2);
    }

    public void a(float f5) {
        if (!this.k && this.j) {
            this.top.a(f5);
        }
    }

    private void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        float ani = ls.a((float)(par1 * 0.6662f));
        if ((double)ani > 0.2) {
            ani /= 3.0f;
        }
        this.top.f = 0.26f - ani * 0.2f * par2;
        this.middle.f = 0.86f - ani * 0.24f * par2;
        this.bottom.f = 0.15f - ani * 0.28f * par2;
        this.fin2.f = this.fin1.f = 0.05f - ani * 0.35f * par2;
    }
}

