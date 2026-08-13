/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 */
package noppes.client.model;

public class ModelCarpentryBench
extends bbo {
    bcu Leg1;
    bcu Leg2;
    bcu Leg3;
    bcu Leg4;
    bcu Bottom_plate;
    bcu Desktop;
    bcu Backboard;
    bcu Vice_Jaw1;
    bcu Vice_Jaw2;
    bcu Vice_Base1;
    bcu Vice_Base2;
    bcu Vice_Crank;
    bcu Vice_Screw;
    bcu Blueprint;

    public ModelCarpentryBench() {
        this.t = 128;
        this.u = 64;
        this.Leg1 = new bcu((bbo)this, 0, 0);
        this.Leg1.a(0.0f, 0.0f, 0.0f, 2, 14, 2);
        this.Leg1.a(6.0f, 10.0f, 5.0f);
        this.Leg2 = new bcu((bbo)this, 0, 0);
        this.Leg2.a(0.0f, 0.0f, 0.0f, 2, 14, 2);
        this.Leg2.a(6.0f, 10.0f, -5.0f);
        this.Leg3 = new bcu((bbo)this, 0, 0);
        this.Leg3.a(0.0f, 0.0f, 0.0f, 2, 14, 2);
        this.Leg3.a(-8.0f, 10.0f, 5.0f);
        this.Leg4 = new bcu((bbo)this, 0, 0);
        this.Leg4.a(0.0f, 0.0f, 0.0f, 2, 14, 2);
        this.Leg4.a(-8.0f, 10.0f, -5.0f);
        this.Bottom_plate = new bcu((bbo)this, 0, 24);
        this.Bottom_plate.a(0.0f, 0.0f, 0.0f, 14, 1, 10);
        this.Bottom_plate.a(-7.0f, 21.0f, -4.0f);
        this.Bottom_plate.b(130, 64);
        this.Desktop = new bcu((bbo)this, 0, 3);
        this.Desktop.a(0.0f, 0.0f, 0.0f, 18, 2, 13);
        this.Desktop.a(-9.0f, 9.0f, -6.0f);
        this.Backboard = new bcu((bbo)this, 0, 18);
        this.Backboard.a(-1.0f, 0.0f, 0.0f, 18, 5, 1);
        this.Backboard.a(-8.0f, 7.0f, 7.0f);
        this.Vice_Jaw1 = new bcu((bbo)this, 54, 18);
        this.Vice_Jaw1.a(0.0f, 0.0f, 0.0f, 3, 2, 1);
        this.Vice_Jaw1.a(3.0f, 6.0f, -8.0f);
        this.Vice_Jaw2 = new bcu((bbo)this, 54, 21);
        this.Vice_Jaw2.a(0.0f, 0.0f, 0.0f, 3, 2, 1);
        this.Vice_Jaw2.a(3.0f, 6.0f, -6.0f);
        this.Vice_Base1 = new bcu((bbo)this, 38, 30);
        this.Vice_Base1.a(0.0f, 0.0f, 0.0f, 3, 1, 3);
        this.Vice_Base1.a(3.0f, 8.0f, -5.0f);
        this.Vice_Base2 = new bcu((bbo)this, 38, 25);
        this.Vice_Base2.a(0.0f, 0.0f, 0.0f, 1, 2, 2);
        this.Vice_Base2.a(4.0f, 7.0f, -5.0f);
        this.Vice_Crank = new bcu((bbo)this, 54, 24);
        this.Vice_Crank.a(0.0f, 0.0f, 0.0f, 1, 5, 1);
        this.Vice_Crank.a(6.0f, 6.0f, -9.0f);
        this.Vice_Screw = new bcu((bbo)this, 44, 25);
        this.Vice_Screw.a(0.0f, 0.0f, 0.0f, 1, 1, 4);
        this.Vice_Screw.a(4.0f, 8.0f, -8.0f);
        this.Blueprint = new bcu((bbo)this, 31, 18);
        this.Blueprint.a(0.0f, 0.0f, 0.0f, 8, 0, 7);
        this.Blueprint.a(0.0f, 9.0f, 1.0f);
        this.setRotation(this.Blueprint, 0.3271718f, 0.1487144f, 0.0f);
    }

    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.a(entity, f, f1, f2, f3, f4, f5);
        this.a(f, f1, f2, f3, f4, f5, entity);
        this.Leg1.a(f5);
        this.Leg2.a(f5);
        this.Leg3.a(f5);
        this.Leg4.a(f5);
        this.Bottom_plate.a(f5);
        this.Desktop.a(f5);
        this.Backboard.a(f5);
        this.Vice_Jaw1.a(f5);
        this.Vice_Jaw2.a(f5);
        this.Vice_Base1.a(f5);
        this.Vice_Base2.a(f5);
        this.Vice_Crank.a(f5);
        this.Vice_Screw.a(f5);
        this.Blueprint.a(f5);
    }

    private void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }
}

