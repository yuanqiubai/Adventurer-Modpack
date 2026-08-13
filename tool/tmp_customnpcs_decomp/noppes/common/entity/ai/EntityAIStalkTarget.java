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
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIStalkTarget
extends ps {
    private final EntityNPCInterface theEntity;
    private of targetEntity;
    private atc movePosition;
    private final double distance;
    private boolean overRide;
    private final abw theWorld;
    private int delay;
    private int tick = 0;

    public EntityAIStalkTarget(EntityNPCInterface par1EntityCreature, double par2) {
        this.theEntity = par1EntityCreature;
        this.theWorld = par1EntityCreature.q;
        this.distance = par2 * par2;
        this.overRide = false;
        this.delay = 0;
        this.a(3);
    }

    public boolean a() {
        this.targetEntity = this.theEntity.m();
        if (this.targetEntity == null) {
            return false;
        }
        if (this.tick > 0) {
            --this.tick;
            return false;
        }
        return this.targetEntity.e((nn)this.theEntity) > this.distance;
    }

    public void d() {
        this.theEntity.k().h();
        if (this.theEntity.m() == null && this.targetEntity != null) {
            this.theEntity.d(this.targetEntity);
        }
        if (this.theEntity.inventory.getProjectile() != null) {
            this.theEntity.getRangedTask().navOverride(false);
        }
    }

    public void c() {
        if (this.theEntity.inventory.getProjectile() != null) {
            this.theEntity.getRangedTask().navOverride(true);
        }
    }

    public void e() {
        this.theEntity.h().a((nn)this.targetEntity, 30.0f, 30.0f);
        if (this.theEntity.k().g() || this.overRide) {
            if (this.isLookingAway()) {
                this.movePosition = this.stalkTarget();
                if (this.movePosition != null) {
                    this.theEntity.k().a(this.movePosition.c, this.movePosition.d, this.movePosition.e, 1.0);
                    this.overRide = false;
                } else {
                    this.tick = 100;
                }
            } else if (this.targetEntity.o((nn)this.theEntity)) {
                this.movePosition = this.hideFromTarget();
                if (this.movePosition != null) {
                    this.theEntity.k().a(this.movePosition.c, this.movePosition.d, this.movePosition.e, 1.33);
                    this.overRide = false;
                } else {
                    this.tick = 100;
                }
            }
        }
        if (this.delay > 0) {
            --this.delay;
        }
        if (!this.isLookingAway() && this.targetEntity.o((nn)this.theEntity) && this.delay == 0) {
            this.overRide = true;
            this.delay = 60;
        }
    }

    private atc hideFromTarget() {
        for (int i = 1; i <= 8; ++i) {
            atc vec = this.findSecludedXYZ(i, false);
            if (vec == null) continue;
            return vec;
        }
        return null;
    }

    private atc stalkTarget() {
        for (int i = 8; i >= 1; --i) {
            atc vec = this.findSecludedXYZ(i, true);
            if (vec == null) continue;
            return vec;
        }
        return null;
    }

    private atc findSecludedXYZ(int radius, boolean nearest) {
        atc idealPos = null;
        double dist = this.targetEntity.e((nn)this.theEntity);
        double u = 0.0;
        double v2 = 0.0;
        double w = 0.0;
        if (this.movePosition != null) {
            u = this.movePosition.c;
            v2 = this.movePosition.d;
            w = this.movePosition.e;
        }
        for (int y = -2; y <= 2; ++y) {
            for (int x = -radius; x <= radius; ++x) {
                for (int z2 = -radius; z2 <= radius; ++z2) {
                    boolean weight;
                    atc vec2;
                    atc vec1;
                    ata movingobjectposition;
                    double l;
                    double k;
                    double j = (double)ls.c((double)(this.theEntity.u + (double)x)) + 0.5;
                    if (this.theWorld.c((int)j, (int)(k = (double)ls.c((double)(this.theEntity.E.b + (double)y)) + 0.5), (int)(l = (double)ls.c((double)(this.theEntity.w + (double)z2)) + 0.5)) || !this.theWorld.c((int)j, (int)k + 1, (int)l) || !this.theWorld.c((int)j, (int)k + 2, (int)l) || (movingobjectposition = this.theWorld.a(vec1 = this.theWorld.V().a(this.targetEntity.u, this.targetEntity.v + (double)this.targetEntity.f(), this.targetEntity.w), vec2 = this.theWorld.V().a(j, k + (double)this.theEntity.f(), l))) == null) continue;
                    boolean bl = weight = !nearest || this.targetEntity.e(j, k, l) <= dist;
                    if (!weight || j == u && k == v2 && l == w) continue;
                    idealPos = this.theWorld.V().a(j, k, l);
                    if (!nearest) continue;
                    dist = this.targetEntity.e(j, k, l);
                }
            }
        }
        return idealPos;
    }

    private boolean isLookingAway() {
        atc vec3 = this.targetEntity.j(1.0f).a();
        atc vec31 = this.theWorld.V().a(this.theEntity.u - this.targetEntity.u, this.theEntity.E.b + (double)(this.theEntity.P / 2.0f) - (this.targetEntity.v + (double)this.targetEntity.f()), this.theEntity.w - this.targetEntity.w);
        double d0 = vec31.b();
        double d1 = vec3.b(vec31 = vec31.a());
        return d1 < 0.6;
    }
}

