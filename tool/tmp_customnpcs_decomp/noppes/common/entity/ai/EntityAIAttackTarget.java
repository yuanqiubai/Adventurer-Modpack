/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  alf
 *  ls
 *  nn
 *  of
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIAttackTarget
extends ps {
    abw worldObj;
    EntityNPCInterface attacker;
    of entityTarget;
    int attackTick = 0;
    boolean longMemory;
    alf entityPathEntity;
    private int field_75445_i;

    public EntityAIAttackTarget(EntityNPCInterface par1EntityLiving, boolean par3) {
        this.attacker = par1EntityLiving;
        this.worldObj = par1EntityLiving.q;
        this.longMemory = par3;
        this.a(3);
    }

    public boolean a() {
        of entitylivingbase = this.attacker.m();
        if (entitylivingbase == null) {
            return false;
        }
        if (!entitylivingbase.T()) {
            return false;
        }
        if (this.attacker.inventory.getProjectile() != null && this.attacker.ai.useRangeMelee == 0) {
            return false;
        }
        double var2 = this.attacker.e(entitylivingbase.u, entitylivingbase.E.b, entitylivingbase.w);
        double var3 = this.attacker.ai.distanceToMelee * this.attacker.ai.distanceToMelee;
        if (this.attacker.ai.useRangeMelee == 1 && var2 > var3) {
            return false;
        }
        this.entityTarget = entitylivingbase;
        this.entityPathEntity = this.attacker.k().a((nn)entitylivingbase);
        return this.entityPathEntity != null;
    }

    public boolean b() {
        this.entityTarget = this.attacker.m();
        return this.entityTarget != null && this.attacker.e((nn)this.entityTarget) <= this.attacker.e((nn)this.entityTarget) && (this.attacker.ai.useRangeMelee != 1 || !(this.attacker.e((nn)this.entityTarget) > (double)(this.attacker.ai.distanceToMelee * this.attacker.ai.distanceToMelee))) && this.entityTarget.T() && (!this.longMemory ? !this.attacker.k().g() : this.attacker.b(ls.c((double)this.entityTarget.u), ls.c((double)this.entityTarget.v), ls.c((double)this.entityTarget.w)));
    }

    public void c() {
        this.attacker.k().a(this.entityPathEntity, 1.0);
        this.field_75445_i = 0;
    }

    public void d() {
        this.entityPathEntity = null;
        this.entityTarget = null;
        this.attacker.d((of)null);
        this.attacker.k().h();
    }

    public void e() {
        this.attacker.h().a((nn)this.entityTarget, 30.0f, 30.0f);
        if ((this.longMemory || this.attacker.l().a((nn)this.entityTarget)) && --this.field_75445_i <= 0) {
            this.field_75445_i = 4 + this.attacker.aD().nextInt(7);
            this.attacker.k().a((nn)this.entityTarget, 1.0);
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (this.attacker.f(this.entityTarget.u, this.entityTarget.E.b, this.entityTarget.w) <= (double)this.attacker.stats.attackRange && this.attacker.o((nn)this.entityTarget) && this.attackTick <= 0) {
            this.attackTick = this.attacker.stats.attackSpeed;
            this.attacker.aV();
            this.attacker.m((nn)this.entityTarget);
        }
    }
}

