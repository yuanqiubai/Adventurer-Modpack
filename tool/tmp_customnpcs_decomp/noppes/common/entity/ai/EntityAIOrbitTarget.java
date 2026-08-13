/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ls
 *  nn
 *  of
 *  ps
 */
package noppes.common.entity.ai;

import java.util.Random;
import noppes.common.entity.EntityNPCInterface;

public class EntityAIOrbitTarget
extends ps {
    private final EntityNPCInterface theEntity;
    private of targetEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private final double speed;
    private final float distance;
    private int delay = 0;
    private float angle = 0.0f;
    private int direction = 1;
    private float targetDistance;
    private final boolean decay;
    private boolean canNavigate = true;
    private float decayRate = 1.0f;
    private int tick = 0;

    public EntityAIOrbitTarget(EntityNPCInterface par1EntityCreature, double par2, float par4, boolean par5) {
        this.theEntity = par1EntityCreature;
        this.speed = par2;
        this.distance = par4;
        this.decay = par5;
        this.a(3);
    }

    public boolean a() {
        if (--this.delay > 0) {
            return false;
        }
        this.targetEntity = this.theEntity.m();
        if (this.targetEntity == null) {
            return false;
        }
        double d0 = this.theEntity.d((nn)this.targetEntity);
        return d0 >= (double)(this.distance / 2.0f) && (this.theEntity.inventory.getProjectile() != null || d0 <= (double)this.distance);
    }

    public boolean b() {
        double d0 = this.targetEntity.d((nn)this.theEntity);
        return this.targetEntity.T() && d0 >= (double)(this.distance / 2.0f) && d0 <= (double)(this.distance * 1.5f) && !this.theEntity.H() && this.canNavigate;
    }

    public void d() {
        this.theEntity.k().h();
        this.delay = 60;
        if (this.theEntity.inventory.getProjectile() != null) {
            this.theEntity.getRangedTask().navOverride(false);
        }
    }

    public void c() {
        this.canNavigate = true;
        Random random = this.theEntity.aD();
        this.direction = random.nextInt(10) > 5 ? 1 : -1;
        this.decayRate = random.nextFloat() + this.distance / 16.0f;
        this.targetDistance = this.theEntity.d((nn)this.targetEntity);
        double d0 = this.theEntity.u - this.targetEntity.u;
        double d1 = this.theEntity.w - this.targetEntity.w;
        this.angle = (float)(Math.atan2(d1, d0) * 180.0 / Math.PI);
        if (this.theEntity.inventory.getProjectile() != null) {
            this.theEntity.getRangedTask().navOverride(true);
        }
    }

    public void e() {
        this.theEntity.h().a((nn)this.targetEntity, 30.0f, 30.0f);
        if (this.theEntity.k().g() && this.tick >= 0 && this.theEntity.F && !this.theEntity.H()) {
            double d0 = (double)this.targetDistance * (double)ls.b((float)(this.angle / 180.0f * (float)Math.PI));
            double d1 = (double)this.targetDistance * (double)ls.a((float)(this.angle / 180.0f * (float)Math.PI));
            this.movePosX = this.targetEntity.u + d0;
            this.movePosY = this.targetEntity.E.e;
            this.movePosZ = this.targetEntity.w + d1;
            this.theEntity.k().a(this.movePosX, this.movePosY, this.movePosZ, this.speed);
            this.angle += 15.0f * (float)this.direction;
            this.tick = ls.f((double)(this.theEntity.f(this.movePosX, this.movePosY, this.movePosZ) / (double)(this.theEntity.getSpeed() / 20.0f)));
            if (this.decay) {
                this.targetDistance -= this.decayRate;
            }
        }
        if (this.tick >= 0) {
            --this.tick;
        }
    }
}

