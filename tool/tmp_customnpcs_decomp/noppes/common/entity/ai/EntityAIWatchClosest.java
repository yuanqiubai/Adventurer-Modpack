/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nn
 *  og
 *  ps
 *  uf
 */
package noppes.common.entity.ai;

public class EntityAIWatchClosest
extends ps {
    protected nn closestEntity;
    private final og theWatcher;
    private final float maxDistanceForPlayer;
    private int lookTime;
    private final float field_75331_e;
    private final Class watchedClass;

    public EntityAIWatchClosest(og par1EntityLiving, Class par2Class, float par3) {
        this.theWatcher = par1EntityLiving;
        this.watchedClass = par2Class;
        this.maxDistanceForPlayer = par3;
        this.field_75331_e = 0.02f;
        this.a(2);
    }

    public EntityAIWatchClosest(og par1EntityLiving, Class par2Class, float par3, float par4) {
        this.theWatcher = par1EntityLiving;
        this.watchedClass = par2Class;
        this.maxDistanceForPlayer = par3;
        this.field_75331_e = par4;
        this.a(2);
    }

    public boolean a() {
        if (this.theWatcher.aD().nextFloat() >= this.field_75331_e) {
            return false;
        }
        if (this.theWatcher.m() != null) {
            this.closestEntity = this.theWatcher.m();
        }
        if (this.watchedClass == uf.class) {
            this.closestEntity = this.theWatcher.q.a((nn)this.theWatcher, (double)this.maxDistanceForPlayer);
        } else {
            this.closestEntity = this.theWatcher.q.a(this.watchedClass, this.theWatcher.E.b((double)this.maxDistanceForPlayer, 3.0, (double)this.maxDistanceForPlayer), (nn)this.theWatcher);
            if (this.closestEntity != null) {
                return this.theWatcher.o(this.closestEntity);
            }
        }
        return this.closestEntity != null;
    }

    public boolean b() {
        return this.closestEntity.T() && !(this.theWatcher.e(this.closestEntity) > (double)(this.maxDistanceForPlayer * this.maxDistanceForPlayer)) && this.lookTime > 0;
    }

    public void c() {
        this.lookTime = 40 + this.theWatcher.aD().nextInt(40);
    }

    public void d() {
        this.closestEntity = null;
    }

    public void e() {
        this.theWatcher.h().a(this.closestEntity.u, this.closestEntity.v + (double)this.closestEntity.f(), this.closestEntity.w, 10.0f, (float)this.theWatcher.bp());
        --this.lookTime;
    }
}

