/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nn
 *  ps
 *  uf
 */
package noppes.common.entity.ai;

import noppes.common.constants.EnumStandingType;
import noppes.common.entity.EntityNPCInterface;

public class EntityAILook
extends ps {
    private final EntityNPCInterface npc;
    boolean rotatebody;
    private int idle = 0;
    private double lookX;
    private double lookZ;

    public EntityAILook(EntityNPCInterface npc) {
        this.npc = npc;
        this.a(1);
    }

    public boolean a() {
        return !this.npc.isAttacking() && this.npc.k().g() && !this.npc.bh() && !this.npc.isKilled();
    }

    public void c() {
        this.rotatebody = this.npc.ai.standingType == EnumStandingType.RotateBody || this.npc.ai.standingType == EnumStandingType.HeadRotation;
    }

    public void d() {
        this.rotatebody = false;
    }

    public void e() {
        if (this.npc.ai.standingType == EnumStandingType.Stalking) {
            uf var1 = this.npc.q.a((nn)this.npc, 16.0);
            if (var1 == null) {
                this.rotatebody = true;
            } else {
                this.npc.h().a((nn)var1, 10.0f, (float)this.npc.bp());
            }
        }
        if (this.rotatebody) {
            if (this.idle == 0 && this.npc.aD().nextFloat() < 0.02f) {
                double var11 = Math.PI * 2 * this.npc.aD().nextDouble();
                if (this.npc.ai.standingType == EnumStandingType.HeadRotation) {
                    var11 = Math.PI / 180 * (double)this.npc.ai.orientation + 0.6283185307179586 + 1.8849555921538759 * this.npc.aD().nextDouble();
                }
                this.lookX = Math.cos(var11);
                this.lookZ = Math.sin(var11);
                this.idle = 20 + this.npc.aD().nextInt(20);
            }
            if (this.idle > 0) {
                --this.idle;
                this.npc.h().a(this.npc.u + this.lookX, this.npc.v + (double)this.npc.f(), this.npc.w + this.lookZ, 10.0f, (float)this.npc.bp());
            }
        }
        if (this.npc.ai.standingType == EnumStandingType.NoRotation) {
            this.npc.A = this.npc.aN = (float)this.npc.ai.orientation;
            this.npc.aP = this.npc.aN;
        }
    }
}

