/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  ata
 *  atc
 *  ls
 *  nn
 *  of
 *  on
 *  ps
 */
package noppes.common.entity.ai;

import java.util.Random;

public class EntityAIAmbushTarget
extends ps {
    private final on theEntity;
    private of targetEntity;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double movementSpeed;
    private final double distance;
    private int delay = 0;
    private final abw theWorld;
    private int tick;
    private final boolean attackFromBehind;

    public EntityAIAmbushTarget(on par1EntityCreature, double par2, double par3, boolean par4) {
        this.theEntity = par1EntityCreature;
        this.movementSpeed = par2;
        this.theWorld = par1EntityCreature.q;
        this.distance = par3 * par3;
        this.attackFromBehind = par4;
        this.a(3);
    }

    public boolean a() {
        this.targetEntity = this.theEntity.m();
        if (this.targetEntity == null) {
            return false;
        }
        if (this.delay > 0) {
            --this.delay;
            return false;
        }
        if (this.targetEntity.e((nn)this.theEntity) > this.distance && this.targetEntity.o((nn)this.theEntity)) {
            atc vec3 = this.findHidingSpot();
            if (vec3 == null) {
                return false;
            }
            this.shelterX = vec3.c;
            this.shelterY = vec3.d;
            this.shelterZ = vec3.e;
            return true;
        }
        return false;
    }

    public boolean b() {
        if (this.targetEntity.T()) {
            if (this.attackFromBehind) {
                this.theEntity.k().g();
            } else {
                boolean bl = true;
            }
            boolean var10000 = this.attackFromBehind ? this.isLookingAway() && !this.targetEntity.o((nn)this.theEntity) : false;
            return this.targetEntity.e((nn)this.theEntity) > this.distance && (!this.theEntity.k().g() || !this.targetEntity.o((nn)this.theEntity));
        }
        return false;
    }

    public void c() {
        this.theEntity.k().a(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    public void d() {
        this.theEntity.k().h();
        if (this.theEntity.m() == null && this.targetEntity != null) {
            this.theEntity.d(this.targetEntity);
        }
    }

    private atc findHidingSpot() {
        Random random = this.theEntity.aD();
        atc idealPos = null;
        for (int i = 1; i <= 8; ++i) {
            for (int y = -2; y <= 2; ++y) {
                for (int x = -i; x <= i; ++x) {
                    for (int z2 = -i; z2 <= i; ++z2) {
                        atc vec2;
                        atc vec1;
                        ata movingobjectposition;
                        double l;
                        double k;
                        double j = (double)ls.c((double)(this.theEntity.u + (double)x)) + 0.5;
                        if (this.theWorld.c((int)j, (int)(k = (double)ls.c((double)(this.theEntity.E.b + (double)y)) + 0.5), (int)(l = (double)ls.c((double)(this.theEntity.w + (double)z2)) + 0.5)) || !this.theWorld.c((int)j, (int)k + 1, (int)l) || (movingobjectposition = this.theWorld.a(vec1 = this.theWorld.V().a(this.targetEntity.u, this.targetEntity.v + (double)this.targetEntity.f(), this.targetEntity.w), vec2 = this.theWorld.V().a(j, k + (double)this.theEntity.f(), l))) == null || this.shelterX == j || this.shelterY == k || this.shelterZ == l) continue;
                        idealPos = this.theWorld.V().a(j, k, l);
                    }
                }
            }
            if (idealPos == null) continue;
            return idealPos;
        }
        this.delay = 60;
        return null;
    }

    private boolean isLookingAway() {
        atc vec3 = this.targetEntity.j(1.0f).a();
        atc vec31 = this.theWorld.V().a(this.theEntity.u - this.targetEntity.u, this.theEntity.E.b + (double)(this.theEntity.P / 2.0f) - (this.targetEntity.v + (double)this.targetEntity.f()), this.theEntity.w - this.targetEntity.w);
        double d0 = vec31.b();
        double d1 = vec3.b(vec31 = vec31.a());
        return d1 < 0.6;
    }
}

