/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ps
 */
package noppes.common.entity.ai;

import java.util.List;
import noppes.common.constants.EnumMovingType;
import noppes.common.entity.EntityNPCInterface;

public class EntityAIMovingPath
extends ps {
    private final EntityNPCInterface npc;
    private int[] pos;

    public EntityAIMovingPath(EntityNPCInterface par1EntityNPCInterface) {
        this.npc = par1EntityNPCInterface;
        this.a(1);
    }

    public boolean a() {
        if (!(this.npc.ai.movingType != EnumMovingType.MovingPath || this.npc.isAttacking() || this.npc.aD().nextInt(40) != 0 && this.npc.ai.movingPause)) {
            List<int[]> list = this.npc.ai.getMovingPath();
            if (list.size() < 2) {
                return false;
            }
            this.npc.ai.incrementMovingPath();
            this.pos = this.npc.ai.getCurrentMovingPath();
            return true;
        }
        return false;
    }

    public boolean b() {
        if (this.npc.isAttacking()) {
            this.npc.ai.decreaseMovingPath();
            return false;
        }
        return !this.npc.k().g();
    }

    public void c() {
        this.npc.k().a((double)this.pos[0] + 0.5, (double)this.pos[1], (double)this.pos[2] + 0.5, 1.0);
    }
}

