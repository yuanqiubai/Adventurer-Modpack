/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ls
 *  nn
 *  nw
 *  of
 *  on
 *  ra
 *  re
 *  tm
 */
package noppes.common.entity.ai;

import java.util.Collections;
import java.util.List;

public class EntityAIClosestTarget
extends re {
    private final Class targetClass;
    private final int targetChance;
    private final ra theNearestAttackableTargetSorter;
    private final nw targetEntitySelector;
    private of targetEntity;

    public EntityAIClosestTarget(on par1EntityCreature, Class par2Class, int par3, boolean par4) {
        this(par1EntityCreature, par2Class, par3, par4, false);
    }

    public EntityAIClosestTarget(on par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5) {
        this(par1EntityCreature, par2Class, par3, par4, par5, null);
    }

    public EntityAIClosestTarget(on par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, nw par6IEntitySelector) {
        super(par1EntityCreature, par4, par5);
        this.targetClass = par2Class;
        this.targetChance = par3;
        this.theNearestAttackableTargetSorter = new ra((nn)par1EntityCreature);
        this.a(1);
        this.targetEntitySelector = par6IEntitySelector;
    }

    public boolean a() {
        if (this.targetChance > 0 && this.c.aD().nextInt(this.targetChance) != 0) {
            return false;
        }
        double d0 = this.f();
        List list = this.c.q.a(this.targetClass, this.c.E.b(d0, (double)ls.f((double)(d0 / 2.0)), d0), this.targetEntitySelector);
        Collections.sort(list, this.theNearestAttackableTargetSorter);
        if (list.isEmpty()) {
            return false;
        }
        this.targetEntity = (of)list.get(0);
        return true;
    }

    public void c() {
        this.c.d(this.targetEntity);
        if (this.targetEntity instanceof tm && ((tm)this.targetEntity).m() == null) {
            ((tm)this.targetEntity).d((of)this.c);
            ((tm)this.targetEntity).b((nn)this.c);
        }
        super.c();
    }
}

