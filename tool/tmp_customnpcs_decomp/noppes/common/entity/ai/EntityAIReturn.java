/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.constants.EnumMovingType;
import noppes.common.entity.EntityNPCInterface;

public class EntityAIReturn
extends ps {
    private final EntityNPCInterface npc;
    private int stuckTicks = 0;
    private int returnTicks = 0;
    private double posX;
    private double posY;
    private double posZ;
    private boolean wasAttacked = false;
    private double[] preAttackPos;

    public EntityAIReturn(EntityNPCInterface npc) {
        this.npc = npc;
        this.a(1);
    }

    public boolean a() {
        if (this.npc.isFollowerWithOwner()) {
            return false;
        }
        if (!this.npc.ai.returnToStart) {
            return false;
        }
        if (!(this.npc.ai.findShelter != 0 || this.npc.q.v() && !this.npc.q.Q() || this.npc.q.t.g || !this.npc.q.l((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos()) && this.npc.q.m((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos()) > 8)) {
            return false;
        }
        if (this.npc.ai.findShelter == 1 && this.npc.q.v() && this.npc.q.l((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos())) {
            return false;
        }
        if (this.npc.isAttacking()) {
            if (!this.wasAttacked) {
                this.wasAttacked = true;
                this.preAttackPos = new double[]{this.npc.u, this.npc.v, this.npc.w};
            }
            return false;
        }
        return !this.npc.isAttacking() && this.wasAttacked || (this.npc.ai.movingType == EnumMovingType.Wandering ? this.npc.f(this.npc.getStartXPos(), this.npc.getStartYPos(), this.npc.getStartZPos()) > (double)this.npc.ai.walkingRange : this.npc.ai.movingType == EnumMovingType.Standing && !this.npc.isVeryNearAssignedPlace());
    }

    public boolean b() {
        return (!this.wasAttacked || this.returnTicks < 30 || !this.npc.k().g() && !this.isTooFar()) && !this.npc.isAttacking() && this.stuckTicks > 0 && !this.npc.isVeryNearAssignedPlace();
    }

    public void e() {
        if (this.returnTicks < 30) {
            ++this.returnTicks;
        } else if (this.returnTicks == 30) {
            ++this.returnTicks;
            if (this.isTooFar()) {
                this.npc.b(this.posX, this.posY, this.posZ);
            }
        } else if (this.npc.k().g()) {
            ++this.stuckTicks;
            if (this.stuckTicks == 30) {
                this.stuckTicks = 0;
                this.npc.b(this.posX, this.posY, this.posZ);
            }
        } else {
            this.stuckTicks = 1;
        }
    }

    private boolean isTooFar() {
        int allowedDistance = this.npc.stats.aggroRange * 2;
        if (this.npc.ai.movingType == EnumMovingType.Wandering) {
            allowedDistance += this.npc.ai.walkingRange;
        }
        return this.npc.f(this.posX, this.posY, this.posZ) > (double)allowedDistance;
    }

    public void c() {
        if (this.wasAttacked) {
            this.posX = this.preAttackPos[0];
            this.posY = this.preAttackPos[1];
            this.posZ = this.preAttackPos[2];
        } else {
            this.posX = this.npc.getStartXPos();
            this.posY = this.npc.getStartYPos();
            this.posZ = this.npc.getStartZPos();
        }
        this.npc.k().a(this.posX, this.posY, this.posZ, 1.0);
        this.stuckTicks = 1;
        this.returnTicks = 0;
    }

    public void d() {
        this.wasAttacked = false;
    }
}

