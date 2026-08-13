/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIRole
extends ps {
    private final EntityNPCInterface npc;

    public EntityAIRole(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean a() {
        return !this.npc.isKilled() && this.npc.roleInterface != null && this.npc.roleInterface.aiShouldExecute();
    }

    public void c() {
        this.npc.roleInterface.aiStartExecuting();
    }

    public boolean b() {
        return !this.npc.isKilled() && this.npc.roleInterface != null && this.npc.roleInterface.aiContinueExecute();
    }

    public void e() {
        if (this.npc.roleInterface != null) {
            this.npc.roleInterface.aiUpdateTask();
        }
    }
}

