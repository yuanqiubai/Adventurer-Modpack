/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 */
package noppes.client.model;

public class ModelMailboxWow
extends bbo {
    bcu Shape4;
    bcu Shape1;
    bcu Shape2;
    bcu Shape3;

    public ModelMailboxWow() {
        this.t = 128;
        this.u = 64;
        this.Shape4 = new bcu((bbo)this, 59, 0);
        this.Shape4.a(0.0f, 0.0f, 0.0f, 8, 6, 0);
        this.Shape4.a(-4.0f, -4.0f, 0.0f);
        this.Shape1 = new bcu((bbo)this, 0, 39);
        this.Shape1.a(0.0f, 0.0f, 0.0f, 8, 5, 8);
        this.Shape1.a(-4.0f, 19.0f, -4.0f);
        this.Shape2 = new bcu((bbo)this, 0, 21);
        this.Shape2.a(0.0f, 0.0f, 0.0f, 6, 9, 6);
        this.Shape2.a(-3.0f, 10.0f, -3.0f);
        this.Shape3 = new bcu((bbo)this, 0, 0);
        this.Shape3.a(0.0f, 0.0f, 0.0f, 12, 8, 12);
        this.Shape3.a(-6.0f, 2.0f, -6.0f);
    }

    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.a(entity, f, f1, f2, f3, f4, f5);
        this.Shape4.a(f5);
        this.Shape1.a(f5);
        this.Shape2.a(f5);
        this.Shape3.a(f5);
    }
}

