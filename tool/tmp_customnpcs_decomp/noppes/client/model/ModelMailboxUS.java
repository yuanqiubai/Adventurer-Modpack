/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 */
package noppes.client.model;

public class ModelMailboxUS
extends bbo {
    bcu Shape1;
    bcu Shape2;
    bcu Shape3;
    bcu Shape4;
    bcu Shape5;
    bcu Shape6;
    bcu Shape7;
    bcu Shape8;
    bcu Shape9;
    bcu Shape10;
    bcu Shape11;
    bcu Shape12;
    bcu Shape13;

    public ModelMailboxUS() {
        this.t = 64;
        this.u = 128;
        this.Shape1 = new bcu((bbo)this, 0, 48);
        this.Shape1.a(0.0f, 0.0f, 0.0f, 16, 14, 16);
        this.Shape1.a(-8.0f, 8.0f, -8.0f);
        this.Shape2 = new bcu((bbo)this, 0, 79);
        this.Shape2.a(0.0f, 0.0f, 0.0f, 1, 2, 1);
        this.Shape2.a(-8.0f, 22.0f, -8.0f);
        this.Shape3 = new bcu((bbo)this, 5, 79);
        this.Shape3.a(0.0f, 0.0f, 0.0f, 1, 2, 1);
        this.Shape3.a(-8.0f, 22.0f, 7.0f);
        this.Shape4 = new bcu((bbo)this, 10, 79);
        this.Shape4.a(0.0f, 0.0f, 0.0f, 1, 2, 1);
        this.Shape4.a(7.0f, 22.0f, -8.0f);
        this.Shape5 = new bcu((bbo)this, 15, 79);
        this.Shape5.a(0.0f, 0.0f, 0.0f, 1, 2, 1);
        this.Shape5.a(7.0f, 22.0f, 7.0f);
        this.Shape6 = new bcu((bbo)this, 0, 14);
        this.Shape6.a(0.0f, 0.0f, 0.0f, 16, 3, 7);
        this.Shape6.a(-8.0f, 5.0f, 0.0f);
        this.Shape7 = new bcu((bbo)this, 0, 6);
        this.Shape7.a(0.0f, 0.0f, 0.0f, 16, 2, 6);
        this.Shape7.a(-8.0f, 3.0f, 0.0f);
        this.Shape8 = new bcu((bbo)this, 0, 0);
        this.Shape8.a(0.0f, 0.0f, 0.0f, 16, 1, 5);
        this.Shape8.a(-8.0f, 2.0f, 0.0f);
        this.Shape9 = new bcu((bbo)this, 0, 37);
        this.Shape9.a(0.0f, 0.0f, 0.0f, 1, 3, 7);
        this.Shape9.a(-8.0f, 5.0f, -7.0f);
        this.Shape10 = new bcu((bbo)this, 16, 37);
        this.Shape10.a(0.0f, 0.0f, 0.0f, 1, 3, 7);
        this.Shape10.a(7.0f, 5.0f, -7.0f);
        this.Shape11 = new bcu((bbo)this, 0, 29);
        this.Shape11.a(0.0f, 0.0f, 0.0f, 1, 2, 6);
        this.Shape11.a(-8.0f, 3.0f, -6.0f);
        this.Shape12 = new bcu((bbo)this, 14, 29);
        this.Shape12.a(0.0f, 0.0f, 0.0f, 1, 2, 6);
        this.Shape12.a(7.0f, 3.0f, -6.0f);
        this.Shape13 = new bcu((bbo)this, 0, 25);
        this.Shape13.a(0.0f, 0.0f, 0.0f, 16, 1, 3);
        this.Shape13.a(-8.0f, 2.0f, -3.0f);
    }

    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.a(entity, f, f1, f2, f3, f4, f5);
        this.Shape1.a(f5);
        this.Shape2.a(f5);
        this.Shape3.a(f5);
        this.Shape4.a(f5);
        this.Shape5.a(f5);
        this.Shape6.a(f5);
        this.Shape7.a(f5);
        this.Shape8.a(f5);
        this.Shape9.a(f5);
        this.Shape10.a(f5);
        this.Shape11.a(f5);
        this.Shape12.a(f5);
        this.Shape13.a(f5);
    }
}

