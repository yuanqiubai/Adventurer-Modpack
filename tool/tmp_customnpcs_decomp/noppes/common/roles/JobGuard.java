/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  of
 *  oq
 *  rp
 *  sk
 *  tf
 *  th
 *  uf
 */
package noppes.common.roles;

import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobInterface;

public class JobGuard
extends JobInterface {
    public boolean attacksAnimals = false;
    public boolean attackHostileMobs = true;
    public boolean attackCreepers = false;
    public boolean attackAll = false;

    public JobGuard(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("GuardAttackAnimals", this.attacksAnimals);
        nbttagcompound.a("GuardAttackMobs", this.attackHostileMobs);
        nbttagcompound.a("GuardAttackCreepers", this.attackCreepers);
        nbttagcompound.a("GuardAttackAll", this.attackAll);
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.attacksAnimals = nbttagcompound.n("GuardAttackAnimals");
        this.attackHostileMobs = nbttagcompound.n("GuardAttackMobs");
        this.attackCreepers = nbttagcompound.n("GuardAttackCreepers");
        this.attackAll = nbttagcompound.n("GuardAttackAll");
    }

    public boolean isEntityApplicable(of entity) {
        return !entity.M && entity.aN() >= 1.0f && !(entity instanceof uf) && !(entity instanceof EntityNPCInterface) && (!(entity instanceof rp) ? (entity instanceof tf ? this.attackCreepers : (!(entity instanceof th) && !(entity instanceof sk) ? this.attackAll : this.attackHostileMobs)) : this.attacksAnimals && (!(entity instanceof oq) || ((oq)entity).h_().isEmpty()));
    }
}

