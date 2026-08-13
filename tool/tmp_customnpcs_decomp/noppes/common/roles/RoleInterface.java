/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  uf
 */
package noppes.common.roles;

import java.util.HashMap;
import noppes.common.entity.EntityNPCInterface;

public abstract class RoleInterface {
    public EntityNPCInterface npc;
    public HashMap<String, String> dataString = new HashMap();

    public RoleInterface(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public abstract void writeEntityToNBT(by var1);

    public abstract void readEntityFromNBT(by var1);

    public abstract boolean interact(uf var1);

    public void killed() {
    }

    public void delete() {
    }

    public boolean aiShouldExecute() {
        return false;
    }

    public boolean aiContinueExecute() {
        return false;
    }

    public void aiStartExecuting() {
    }

    public void aiUpdateTask() {
    }
}

