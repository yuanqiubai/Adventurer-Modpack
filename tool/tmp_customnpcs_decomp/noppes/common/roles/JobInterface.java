/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ye
 */
package noppes.common.roles;

import noppes.common.entity.EntityNPCInterface;

public abstract class JobInterface {
    public EntityNPCInterface npc;
    public boolean overrideMainHand = false;
    public boolean overrideOffHand = false;
    public ye mainhand = null;
    public ye offhand = null;

    public JobInterface(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public abstract void writeEntityToNBT(by var1);

    public abstract void readEntityFromNBT(by var1);

    public void killed() {
    }

    public void delete() {
    }

    public boolean aiShouldExecute() {
        return false;
    }

    public boolean aiContinueExecute() {
        return this.aiShouldExecute();
    }

    public void aiStartExecuting() {
    }

    public void aiUpdateTask() {
    }

    public void reset() {
    }

    public void resetTask() {
    }
}

