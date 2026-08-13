/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ale
 *  alf
 *  atc
 *  ls
 *  nn
 *  of
 *  on
 *  ps
 *  rh
 */
package noppes.common.entity.ai;

public class EntityAIZigZagTarget
extends ps {
    private final on theEntity;
    private of targetEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private int entityPosX;
    private int entityPosY;
    private int entityPosZ;
    private final double speed;
    private final float maxTargetDistance;

    public EntityAIZigZagTarget(on par1EntityCreature, double par2, float par4) {
        this.theEntity = par1EntityCreature;
        this.speed = par2;
        this.maxTargetDistance = par4;
        this.a(3);
    }

    public boolean a() {
        this.targetEntity = this.theEntity.m();
        if (this.targetEntity == null) {
            return false;
        }
        if (this.targetEntity.e((nn)this.theEntity) < (double)(this.maxTargetDistance * this.maxTargetDistance)) {
            return false;
        }
        alf pathentity = this.theEntity.k().a((nn)this.targetEntity);
        if (pathentity != null && (float)pathentity.d() >= this.maxTargetDistance) {
            ale pathpoint = pathentity.a(ls.c((double)((double)this.maxTargetDistance / 2.0)));
            this.entityPosX = pathpoint.a;
            this.entityPosY = pathpoint.b;
            this.entityPosZ = pathpoint.c;
            atc vec3 = rh.a((on)this.theEntity, (int)((int)this.maxTargetDistance), (int)3, (atc)this.theEntity.q.V().a((double)this.entityPosX, (double)this.entityPosY, (double)this.entityPosZ));
            if (vec3 != null && this.targetEntity.e(vec3.c, vec3.d, vec3.e) < this.targetEntity.e((double)this.entityPosX, (double)this.entityPosY, (double)this.entityPosZ)) {
                this.movePosX = vec3.c;
                this.movePosY = vec3.d;
                this.movePosZ = vec3.e;
                return true;
            }
        }
        return false;
    }

    public boolean b() {
        return !this.theEntity.k().g() && this.targetEntity.T() && this.targetEntity.e((nn)this.theEntity) > (double)(this.maxTargetDistance * this.maxTargetDistance) && this.theEntity.l().a((nn)this.targetEntity);
    }

    public void d() {
        this.targetEntity = null;
    }

    public void c() {
        this.theEntity.k().a(this.movePosX, this.movePosY, this.movePosZ, this.speed);
    }

    public void e() {
        this.theEntity.h().a((nn)this.targetEntity, 30.0f, 30.0f);
    }
}

