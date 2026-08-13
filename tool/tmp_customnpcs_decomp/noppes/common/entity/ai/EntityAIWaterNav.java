/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIWaterNav
extends ps {
    private final EntityNPCInterface theEntity;

    public EntityAIWaterNav(EntityNPCInterface par1EntityNPCInterface) {
        this.theEntity = par1EntityNPCInterface;
        this.a(4);
        par1EntityNPCInterface.k().e(true);
    }

    public boolean a() {
        if (this.theEntity.H() || this.theEntity.J()) {
            if (this.theEntity.ai.canSwim) {
                return true;
            }
            return this.theEntity.G;
        }
        return false;
    }

    public void e() {
        if (this.theEntity.aD().nextFloat() < 0.8f) {
            this.theEntity.j().a();
        }
    }
}

