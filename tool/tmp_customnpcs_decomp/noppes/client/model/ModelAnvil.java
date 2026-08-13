/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 */
package noppes.client.model;

public class ModelAnvil
extends bbo {
    bcu Tail;
    bcu Nose1;
    bcu Nose2;
    bcu Nose3;
    bcu Nose4;
    bcu Head1;
    bcu Head2;
    bcu Neck2;
    bcu Bottom2;
    bcu Bottom3;
    bcu Foot4;

    public ModelAnvil() {
        this.t = 64;
        this.u = 32;
        this.Tail = new bcu((bbo)this, 0, 0);
        this.Tail.a(0.0f, 0.0f, 0.0f, 1, 2, 4);
        this.Tail.a(-7.0f, 12.0f, -2.0f);
        this.Nose1 = new bcu((bbo)this, 0, 0);
        this.Nose1.a(0.0f, 0.0f, 0.0f, 1, 5, 6);
        this.Nose1.a(6.0f, 10.0f, -3.0f);
        this.Nose2 = new bcu((bbo)this, 0, 0);
        this.Nose2.a(0.0f, 0.0f, 0.0f, 1, 4, 5);
        this.Nose2.a(7.0f, 10.0f, -2.5f);
        this.Nose3 = new bcu((bbo)this, 0, 0);
        this.Nose3.a(0.0f, 0.0f, 0.0f, 1, 3, 4);
        this.Nose3.a(8.0f, 10.0f, -2.0f);
        this.Nose4 = new bcu((bbo)this, 0, 0);
        this.Nose4.a(0.0f, 0.0f, 0.0f, 1, 2, 2);
        this.Nose4.a(9.0f, 10.0f, -1.0f);
        this.Head1 = new bcu((bbo)this, 0, 0);
        this.Head1.a(0.0f, 0.0f, 0.0f, 12, 4, 7);
        this.Head1.a(-6.0f, 12.0f, -3.5f);
        this.Head2 = new bcu((bbo)this, 0, 0);
        this.Head2.a(0.0f, 0.0f, 0.0f, 14, 2, 9);
        this.Head2.a(-8.0f, 10.0f, -4.5f);
        this.Neck2 = new bcu((bbo)this, 0, 0);
        this.Neck2.a(0.0f, 0.0f, 0.0f, 10, 1, 6);
        this.Neck2.a(-5.0f, 16.0f, -3.0f);
        this.Bottom2 = new bcu((bbo)this, 0, 0);
        this.Bottom2.a(0.0f, 0.0f, 0.0f, 10, 2, 7);
        this.Bottom2.a(-5.0f, 20.0f, -3.5f);
        this.Bottom3 = new bcu((bbo)this, 0, 0);
        this.Bottom3.a(0.0f, 0.0f, 0.0f, 8, 3, 4);
        this.Bottom3.a(-4.0f, 17.0f, -2.0f);
        this.Foot4 = new bcu((bbo)this, 0, 0);
        this.Foot4.a(0.0f, 0.0f, 0.0f, 14, 2, 10);
        this.Foot4.a(-7.0f, 22.0f, -5.0f);
    }

    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.a(entity, f, f1, f2, f3, f4, f5);
        this.a(f, f1, f2, f3, f4, f5, entity);
        this.Tail.a(f5);
        this.Nose1.a(f5);
        this.Nose2.a(f5);
        this.Nose3.a(f5);
        this.Nose4.a(f5);
        this.Head1.a(f5);
        this.Head2.a(f5);
        this.Neck2.a(f5);
        this.Bottom2.a(f5);
        this.Bottom3.a(f5);
        this.Foot4.a(f5);
    }
}

