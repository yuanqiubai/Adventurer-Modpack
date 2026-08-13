/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  nn
 *  of
 *  on
 *  ps
 *  rh
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIDodgeShoot
extends ps {
    private final EntityNPCInterface entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;

    public EntityAIDodgeShoot(EntityNPCInterface par1EntityNPCInterface) {
        this.entity = par1EntityNPCInterface;
        this.a(1);
    }

    public boolean a() {
        of var1 = this.entity.m();
        if (var1 != null && var1.T()) {
            atc vec;
            if (this.entity.inventory.getProjectile() == null) {
                return false;
            }
            if (this.entity.getRangedTask() == null) {
                return false;
            }
            atc atc2 = vec = this.entity.getRangedTask().hasFired() ? rh.a((on)this.entity, (int)4, (int)1) : null;
            if (vec == null) {
                return false;
            }
            this.xPosition = vec.c;
            this.yPosition = vec.d;
            this.zPosition = vec.e;
            return true;
        }
        return false;
    }

    public boolean b() {
        return !this.entity.k().g();
    }

    public void c() {
        this.entity.k().a(this.xPosition, this.yPosition, this.zPosition, 1.2);
    }

    public void e() {
        if (this.entity.m() != null) {
            this.entity.h().a((nn)this.entity.m(), 30.0f, 30.0f);
        }
    }
}

