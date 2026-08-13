/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  nn
 *  of
 *  uf
 */
package noppes.common.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobInterface;
import noppes.util.NoppesUtilServer;

public class JobHealer
extends JobInterface {
    public int range = 5;
    public int speed = 5;
    private long healTicks = 0L;
    private final List<of> toHeal = new ArrayList<of>();

    public JobHealer(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("HealerRange", this.range);
        nbttagcompound.a("HealerSpeed", this.speed);
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.range = nbttagcompound.e("HealerRange");
        this.speed = nbttagcompound.e("HealerSpeed");
    }

    @Override
    public boolean aiShouldExecute() {
        ++this.healTicks;
        if (this.healTicks < (long)this.speed * 10L) {
            return false;
        }
        for (Object plObj : this.npc.q.a(of.class, this.npc.E.b((double)this.range, (double)(this.range / 2), (double)this.range))) {
            EntityNPCInterface npc1;
            uf npc;
            of entity = (of)plObj;
            if (entity instanceof uf && (npc = (uf)entity).aN() < npc.aT() && !this.npc.getFaction().isAggressiveToPlayer(npc)) {
                this.toHeal.add((of)npc);
            }
            if (!(entity instanceof EntityNPCInterface) || !((npc1 = (EntityNPCInterface)entity).aN() < npc1.aT()) || this.npc.getFaction().isAggressiveToNpc(npc1)) continue;
            this.toHeal.add((of)npc1);
        }
        this.healTicks = 0L;
        return !this.toHeal.isEmpty();
    }

    @Override
    public void aiStartExecuting() {
        Iterator<of> i$ = this.toHeal.iterator();
        while (i$.hasNext()) {
            of entity;
            float heal = (entity = i$.next()).aT() / 20.0f;
            entity.f(heal > 0.0f ? heal : 1.0f);
            NoppesUtilServer.spawnParticle((nn)entity, "heal", entity.ar);
        }
        this.toHeal.clear();
    }
}

