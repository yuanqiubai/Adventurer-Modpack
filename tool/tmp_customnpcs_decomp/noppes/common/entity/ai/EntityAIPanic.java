/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  on
 *  ps
 *  rh
 */
package noppes.common.entity.ai;

public class EntityAIPanic
extends ps {
    private final on theEntityCreature;
    private final float speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIPanic(on par1EntityCreature, float par2) {
        this.theEntityCreature = par1EntityCreature;
        this.speed = par2;
        this.a(1);
    }

    public boolean a() {
        if (this.theEntityCreature.m() == null && !this.theEntityCreature.af()) {
            return false;
        }
        atc var1 = rh.a((on)this.theEntityCreature, (int)5, (int)4);
        if (var1 == null) {
            return false;
        }
        this.randPosX = var1.c;
        this.randPosY = var1.d;
        this.randPosZ = var1.e;
        return true;
    }

    public void c() {
        this.theEntityCreature.k().a(this.randPosX, this.randPosY, this.randPosZ, (double)this.speed);
    }

    public boolean b() {
        return !this.theEntityCreature.k().g();
    }
}

