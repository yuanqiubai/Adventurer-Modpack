/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nn
 *  of
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAISprintToTarget
extends ps {
    EntityNPCInterface runner;
    of runTarget;

    public EntityAISprintToTarget(EntityNPCInterface par1EntityLiving) {
        this.runner = par1EntityLiving;
        this.a(8);
    }

    public boolean a() {
        this.runTarget = this.runner.m();
        if (this.runTarget == null) {
            return false;
        }
        if (this.runner.k().g()) {
            return false;
        }
        switch (this.runner.ai.onAttack) {
            case 0: {
                return this.runner.e((nn)this.runTarget) >= 64.0 && this.runner.F;
            }
            case 2: {
                return this.runner.e((nn)this.runTarget) <= 49.0 && this.runner.F;
            }
        }
        return false;
    }

    public boolean b() {
        return this.runner.T() && this.runner.F && this.runner.ay <= 0 && this.runner.x != 0.0 && this.runner.z != 0.0;
    }

    public void c() {
        this.runner.c(true);
    }

    public void d() {
        this.runner.c(false);
    }
}

