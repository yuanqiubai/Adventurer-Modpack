/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ls
 *  nn
 *  of
 *  ps
 *  to
 */
package noppes.common.entity.ai;

import noppes.common.constants.EnumAnimation;
import noppes.common.constants.EnumNavType;
import noppes.common.entity.EntityNPCInterface;

public class EntityAIRangedAttack
extends ps {
    private final EntityNPCInterface entityHost;
    private final to rangedAttackEntityHost;
    private of attackTarget;
    private int rangedAttackTime = 0;
    private int field_75318_f = 0;
    private int field_70846_g = 0;
    private final int attackTick = 0;
    private boolean hasFired = false;
    private boolean navOverride = false;

    public EntityAIRangedAttack(to par1IRangedAttackMob) {
        if (!(par1IRangedAttackMob instanceof of)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.rangedAttackEntityHost = par1IRangedAttackMob;
        this.entityHost = (EntityNPCInterface)par1IRangedAttackMob;
        this.rangedAttackTime = this.entityHost.stats.fireDelay / 2;
        this.a(this.entityHost.ai.useRangeMelee != 2 && this.entityHost.ai.tacticalVariant != EnumNavType.Surround && this.entityHost.ai.tacticalVariant != EnumNavType.Stalk ? 3 : 4);
    }

    public boolean a() {
        of var1 = this.entityHost.m();
        if (var1 != null && var1.T()) {
            if (this.entityHost.inventory.getProjectile() == null) {
                return false;
            }
            double var2 = this.entityHost.e(var1.u, var1.E.b, var1.w);
            double var3 = this.entityHost.ai.distanceToMelee * this.entityHost.ai.distanceToMelee;
            if (this.entityHost.ai.useRangeMelee == 1 && var2 <= var3) {
                return false;
            }
            if (this.entityHost.ai.useRangeMelee == 2 && var2 <= 16.0) {
                return false;
            }
            this.attackTarget = var1;
            return true;
        }
        return false;
    }

    public boolean b() {
        return this.a() || !this.entityHost.k().g();
    }

    public void d() {
        this.attackTarget = null;
        this.entityHost.d((of)null);
        this.entityHost.k().h();
        this.field_75318_f = 0;
        this.hasFired = false;
        if (this.entityHost.ai.useRangeMelee != 2) {
            this.rangedAttackTime = this.entityHost.stats.fireDelay / 2;
        }
    }

    public void e() {
        double var1 = this.entityHost.e(this.attackTarget.u, this.attackTarget.E.b, this.attackTarget.w);
        float field_82642_h = this.entityHost.stats.rangedRange * this.entityHost.stats.rangedRange;
        this.field_75318_f = this.entityHost.ai.directLOS && !this.entityHost.l().a((nn)this.attackTarget) ? 0 : ++this.field_75318_f;
        if (this.entityHost.ai.useRangeMelee != 2 && !this.navOverride) {
            int i;
            int n = i = this.entityHost.ai.tacticalVariant != EnumNavType.Default ? 5 : 20;
            if (var1 <= (double)field_82642_h && this.field_75318_f >= i) {
                this.entityHost.k().h();
            } else {
                this.entityHost.k().a((nn)this.attackTarget, 1.0);
            }
            this.entityHost.h().a((nn)this.attackTarget, 30.0f, 30.0f);
        }
        this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);
        if (this.rangedAttackTime <= 0 && var1 <= (double)field_82642_h && (this.entityHost.l().a((nn)this.attackTarget) || !this.entityHost.ai.directLOS && this.entityHost.ai.canFireIndirect)) {
            if (this.field_70846_g++ <= this.entityHost.stats.burstCount) {
                this.rangedAttackTime = this.entityHost.stats.fireRate;
            } else {
                this.field_70846_g = 0;
                this.hasFired = true;
                this.rangedAttackTime = this.entityHost.stats.fireDelay + ls.d((float)(this.entityHost.aD().nextFloat() * (float)this.entityHost.stats.delayVariance));
            }
            if (this.field_70846_g > 1) {
                this.rangedAttackEntityHost.a(this.attackTarget, !this.entityHost.l().a((nn)this.attackTarget) ? 1.0f : 0.0f);
                if (this.entityHost.currentAnimation != EnumAnimation.Aiming) {
                    this.entityHost.aV();
                }
            }
        }
    }

    public boolean hasFired() {
        return this.hasFired;
    }

    public void navOverride(boolean nav) {
        this.navOverride = nav;
    }
}

