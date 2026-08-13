/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIJob
extends ps {
    private final EntityNPCInterface npc;

    public EntityAIJob(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean a() {
        return !this.npc.isKilled() && this.npc.jobInterface != null && this.npc.jobInterface.aiShouldExecute();
    }

    public void c() {
        this.npc.jobInterface.aiStartExecuting();
    }

    public boolean b() {
        return !this.npc.isKilled() && this.npc.jobInterface != null && this.npc.jobInterface.aiContinueExecute();
    }

    public void e() {
        this.npc.jobInterface.aiUpdateTask();
    }

    public void d() {
        if (this.npc.jobInterface != null) {
            this.npc.jobInterface.resetTask();
        }
    }
}

