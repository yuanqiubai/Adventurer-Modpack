/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  alf
 *  atc
 *  nn
 *  of
 *  on
 *  ps
 *  rf
 *  rh
 *  uf
 */
package noppes.common.entity.ai;

import java.util.List;
import noppes.common.entity.EntityNPCInterface;

public class EntityAIAvoidTarget
extends ps {
    private final EntityNPCInterface theEntity;
    private nn closestLivingEntity;
    private final float distanceFromEntity;
    private alf entityPathEntity;
    private final rf entityPathNavigate;
    private Class targetEntityClass;

    public EntityAIAvoidTarget(EntityNPCInterface par1EntityNPC) {
        this.theEntity = par1EntityNPC;
        this.distanceFromEntity = this.theEntity.stats.aggroRange;
        this.entityPathNavigate = par1EntityNPC.k();
        this.a(1);
    }

    public boolean a() {
        of target = this.theEntity.m();
        if (target == null) {
            return false;
        }
        this.targetEntityClass = target.getClass();
        if (this.targetEntityClass == uf.class) {
            this.closestLivingEntity = this.theEntity.q.a((nn)this.theEntity, (double)this.distanceFromEntity);
            if (this.closestLivingEntity == null) {
                return false;
            }
        } else {
            List var2 = this.theEntity.q.a(this.targetEntityClass, this.theEntity.E.b((double)this.distanceFromEntity, 3.0, (double)this.distanceFromEntity));
            if (var2.isEmpty()) {
                return false;
            }
            this.closestLivingEntity = (nn)var2.get(0);
        }
        if (!this.theEntity.l().a(this.closestLivingEntity)) {
            return false;
        }
        atc var21 = rh.b((on)this.theEntity, (int)16, (int)7, (atc)this.theEntity.q.V().a(this.closestLivingEntity.u, this.closestLivingEntity.v, this.closestLivingEntity.w));
        if (var21 == null) {
            return false;
        }
        if (this.closestLivingEntity.e(var21.c, var21.d, var21.e) < this.closestLivingEntity.e((nn)this.theEntity)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.a(var21.c, var21.d, var21.e);
        return this.entityPathEntity != null && this.entityPathEntity.b(var21);
    }

    public boolean b() {
        return !this.entityPathNavigate.g();
    }

    public void c() {
        this.entityPathNavigate.a(this.entityPathEntity, 1.0);
    }

    public void d() {
        this.closestLivingEntity = null;
        this.theEntity.d((of)null);
    }

    public void e() {
        if (this.theEntity.e(this.closestLivingEntity) < 49.0) {
            this.theEntity.k().a(1.2);
        } else {
            this.theEntity.k().a(1.0);
        }
    }
}

