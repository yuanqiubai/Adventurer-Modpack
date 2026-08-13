/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  nn
 *  nt
 */
package noppes.common.roles;

import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobInterface;

public class JobBoss
extends JobInterface {
    public boolean hideName = false;
    public int resetTime = 300;
    public by compound9;
    public by compound8;
    public by compound7;
    public by compound6;
    public by compound5;
    public by compound4;
    public by compound3;
    public by compound2;
    public by compound1;
    private by original;
    private int type = 10;
    private long timeStart;

    public JobBoss(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by compound) {
        compound.a("BossHideName", this.hideName);
        this.saveCompound(this.original, "BossOriginal", compound);
        this.saveCompound(this.compound1, "BossNBT1", compound);
        this.saveCompound(this.compound2, "BossNBT2", compound);
        this.saveCompound(this.compound3, "BossNBT3", compound);
        this.saveCompound(this.compound4, "BossNBT4", compound);
        this.saveCompound(this.compound5, "BossNBT5", compound);
        this.saveCompound(this.compound6, "BossNBT6", compound);
        this.saveCompound(this.compound7, "BossNBT7", compound);
        this.saveCompound(this.compound8, "BossNBT8", compound);
        this.saveCompound(this.compound9, "BossNBT9", compound);
    }

    private void saveCompound(by save, String name, by compound) {
        if (save != null) {
            compound.a(name, save);
        }
    }

    @Override
    public void readEntityFromNBT(by compound) {
        this.hideName = compound.n("BossHideName");
        this.original = compound.l("BossOriginal");
        this.compound1 = compound.l("BossNBT1");
        this.compound2 = compound.l("BossNBT2");
        this.compound3 = compound.l("BossNBT3");
        this.compound4 = compound.l("BossNBT4");
        this.compound5 = compound.l("BossNBT5");
        this.compound6 = compound.l("BossNBT6");
        this.compound7 = compound.l("BossNBT7");
        this.compound8 = compound.l("BossNBT8");
        this.compound9 = compound.l("BossNBT9");
    }

    @Override
    public boolean aiShouldExecute() {
        return false;
    }

    @Override
    public void aiStartExecuting() {
        this.timeStart = System.currentTimeMillis();
    }

    @Override
    public void aiUpdateTask() {
        if (this.timeStart - System.currentTimeMillis() >= (long)this.resetTime * 1000L) {
            this.npc.M = true;
            this.type = 10;
            this.spawnEntity(this.original);
        }
    }

    public boolean applyDamage(float damage) {
        return false;
    }

    private by getNBT(int i) {
        return i == 9 ? this.compound9 : (i == 8 ? this.compound8 : (i == 7 ? this.compound7 : (i == 6 ? this.compound6 : (i == 5 ? this.compound5 : (i == 4 ? this.compound4 : (i == 3 ? this.compound3 : (i == 2 ? this.compound2 : (i == 1 ? this.compound1 : null))))))));
    }

    public void setNBT(int i, by compound) {
        if (i == 9) {
            this.compound9 = compound;
        }
        if (i == 8) {
            this.compound8 = compound;
        }
        if (i == 7) {
            this.compound7 = compound;
        }
        if (i == 6) {
            this.compound6 = compound;
        }
        if (i == 5) {
            this.compound5 = compound;
        }
        if (i == 4) {
            this.compound4 = compound;
        }
        if (i == 3) {
            this.compound3 = compound;
        }
        if (i == 2) {
            this.compound2 = compound;
        }
        if (i == 1) {
            this.compound1 = compound;
        }
    }

    @Override
    public void reset() {
        if (this.type != 10) {
            this.type = 10;
            this.spawnEntity(this.original);
        }
    }

    private boolean spawnEntity(by compound) {
        nn entity = nt.a((by)compound, (abw)this.npc.q);
        return entity != null && entity instanceof EntityNPCInterface;
    }
}

