/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nn
 *  nw
 *  of
 *  uf
 */
package noppes.common.entity.ai;

import noppes.common.constants.EnumJobType;
import noppes.common.constants.EnumMovingType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobGuard;

public class AttackSelectorNPC
implements nw {
    private final EntityNPCInterface npc;

    public AttackSelectorNPC(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean a(nn entity) {
        if (entity != this.npc && entity instanceof of && this.npc.d(entity) <= (float)this.npc.stats.aggroRange) {
            if (this.npc.ai.directLOS && !this.npc.l().a(entity)) {
                return false;
            }
            if (!this.npc.isFollowerWithOwner() && this.npc.ai.returnToStart) {
                int allowedDistance = this.npc.stats.aggroRange * 2;
                if (this.npc.ai.movingType == EnumMovingType.Wandering) {
                    allowedDistance += this.npc.ai.walkingRange;
                }
                if (entity.f((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos()) > (double)allowedDistance) {
                    return false;
                }
            }
            if (this.npc.advanced.job == EnumJobType.Guard && ((JobGuard)this.npc.jobInterface).isEntityApplicable((of)entity)) {
                return true;
            }
            if (entity instanceof uf) {
                return this.npc.getFaction().isAggressiveToPlayer((uf)entity);
            }
            if (entity instanceof EntityNPCInterface) {
                if (((EntityNPCInterface)entity).isKilled()) {
                    return false;
                }
                if (this.npc.advanced.attackOtherFactions) {
                    return this.npc.getFaction().isAggressiveToNpc((EntityNPCInterface)entity);
                }
            }
            return false;
        }
        return false;
    }
}

