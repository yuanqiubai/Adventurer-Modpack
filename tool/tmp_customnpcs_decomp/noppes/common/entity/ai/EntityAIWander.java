/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.config.CustomNPCConfig;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.ai.RandomPositionGeneratorAlt;

public class EntityAIWander
extends ps {
    private final EntityNPCInterface entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;

    public EntityAIWander(EntityNPCInterface par1EntityNPCInterface) {
        this.entity = par1EntityNPCInterface;
        this.a(1);
    }

    public boolean a() {
        if (this.entity.aI() >= 100) {
            return false;
        }
        if (this.entity.aD().nextInt(80) != 0) {
            return false;
        }
        atc vec = this.getVec();
        if (vec == null) {
            return false;
        }
        this.xPosition = vec.c;
        this.yPosition = vec.d;
        this.zPosition = vec.e;
        return true;
    }

    private atc getVec() {
        if (this.entity.ai.walkingRange > 0) {
            double distance = this.entity.f(this.entity.getStartXPos(), this.entity.getStartYPos(), this.entity.getStartZPos());
            int range = (int)((double)this.entity.ai.walkingRange - distance);
            if (range > CustomNPCConfig.NpcNavRange) {
                range = CustomNPCConfig.NpcNavRange;
            }
            if (range < 3) {
                range = this.entity.ai.walkingRange;
                if (range > CustomNPCConfig.NpcNavRange) {
                    range = CustomNPCConfig.NpcNavRange;
                }
                atc start = this.entity.q.V().a((double)this.entity.getStartXPos(), this.entity.getStartYPos(), (double)this.entity.getStartZPos());
                return RandomPositionGeneratorAlt.findRandomTargetBlockTowards(this.entity, range / 2, 7, start);
            }
            return RandomPositionGeneratorAlt.findRandomTarget(this.entity, range, 7);
        }
        return RandomPositionGeneratorAlt.findRandomTarget(this.entity, CustomNPCConfig.NpcNavRange, 7);
    }

    public boolean b() {
        return !this.entity.k().g();
    }

    public void c() {
        this.entity.k().a(this.xPosition, this.yPosition, this.zPosition, 0.7);
    }
}

