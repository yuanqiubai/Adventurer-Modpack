/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIWorldLines
extends ps {
    private final EntityNPCInterface npc;

    public EntityAIWorldLines(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean a() {
        return !this.npc.isAttacking() && !this.npc.isKilled() && this.npc.advanced.hasWorldLines() && this.npc.aD().nextInt(900) == 1;
    }

    public void c() {
        this.npc.saySurrounding(this.npc.advanced.getWorldLine());
    }
}

