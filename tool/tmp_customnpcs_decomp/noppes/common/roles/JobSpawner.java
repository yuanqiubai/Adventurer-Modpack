/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  nn
 *  nt
 *  of
 *  og
 *  org.apache.commons.lang3.RandomStringUtils
 *  uf
 */
package noppes.common.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobInterface;
import org.apache.commons.lang3.RandomStringUtils;

public class JobSpawner
extends JobInterface {
    public by compound6;
    public by compound5;
    public by compound4;
    public by compound3;
    public by compound2;
    public by compound1;
    public boolean doesntDie = false;
    public int spawnType = 0;
    public int xOffset = 0;
    public int yOffset = 0;
    public int zOffset = 0;
    private int number = 0;
    private List<of> spawned = new ArrayList<of>();
    private String id = RandomStringUtils.random((int)8, (boolean)true, (boolean)true);
    private of target;

    public JobSpawner(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public void writeEntityToNBT(by compound) {
        this.saveCompound(this.compound1, "SpawnerNBT1", compound);
        this.saveCompound(this.compound2, "SpawnerNBT2", compound);
        this.saveCompound(this.compound3, "SpawnerNBT3", compound);
        this.saveCompound(this.compound4, "SpawnerNBT4", compound);
        this.saveCompound(this.compound5, "SpawnerNBT5", compound);
        this.saveCompound(this.compound6, "SpawnerNBT6", compound);
        compound.a("SpawnerId", this.id);
        compound.a("SpawnerDoesntDie", this.doesntDie);
        compound.a("SpawnerType", this.spawnType);
        compound.a("SpawnerXOffset", this.xOffset);
        compound.a("SpawnerYOffset", this.yOffset);
        compound.a("SpawnerZOffset", this.zOffset);
    }

    private void saveCompound(by save, String name, by compound) {
        if (save != null) {
            compound.a(name, save);
        }
    }

    @Override
    public void readEntityFromNBT(by compound) {
        this.compound1 = compound.l("SpawnerNBT1");
        this.compound2 = compound.l("SpawnerNBT2");
        this.compound3 = compound.l("SpawnerNBT3");
        this.compound4 = compound.l("SpawnerNBT4");
        this.compound5 = compound.l("SpawnerNBT5");
        this.compound6 = compound.l("SpawnerNBT6");
        this.id = compound.i("SpawnerId");
        this.doesntDie = compound.n("SpawnerDoesntDie");
        this.spawnType = compound.e("SpawnerType");
        this.xOffset = compound.e("SpawnerXOffset");
        this.yOffset = compound.e("SpawnerYOffset");
        this.zOffset = compound.e("SpawnerZOffset");
    }

    public void setJobCompound(int i, by compound) {
        if (i == 1) {
            this.compound1 = compound;
        }
        if (i == 2) {
            this.compound2 = compound;
        }
        if (i == 3) {
            this.compound3 = compound;
        }
        if (i == 4) {
            this.compound4 = compound;
        }
        if (i == 5) {
            this.compound5 = compound;
        }
        if (i == 6) {
            this.compound6 = compound;
        }
    }

    @Override
    public void aiUpdateTask() {
        if (!this.spawned.isEmpty()) {
            Iterator<of> iterator = this.spawned.iterator();
            while (iterator.hasNext()) {
                of list = iterator.next();
                if (this.npc.d((nn)list) <= 60.0f && !list.M && list.aN() > 0.0f) {
                    if (!(list instanceof og)) continue;
                    ((og)list).d(this.target);
                    continue;
                }
                list.M = true;
                iterator.remove();
            }
            if (this.spawnType == 0 && this.spawned.isEmpty() && !this.spawnEntity(this.number + 1) && !this.doesntDie) {
                this.npc.x();
            }
            if (this.spawnType == 1 && this.spawned.isEmpty()) {
                if (this.number >= 6 && !this.doesntDie) {
                    this.npc.x();
                } else {
                    this.spawnEntity(this.compound1);
                    this.spawnEntity(this.compound2);
                    this.spawnEntity(this.compound3);
                    this.spawnEntity(this.compound4);
                    this.spawnEntity(this.compound5);
                    this.spawnEntity(this.compound6);
                    this.number = 6;
                }
            }
            if (this.spawnType == 2 && this.spawned.isEmpty()) {
                ArrayList<by> list1 = new ArrayList<by>();
                if (this.compound1 != null && this.compound1.b("id")) {
                    list1.add(this.compound1);
                }
                if (this.compound2 != null && this.compound2.b("id")) {
                    list1.add(this.compound2);
                }
                if (this.compound3 != null && this.compound3.b("id")) {
                    list1.add(this.compound3);
                }
                if (this.compound4 != null && this.compound4.b("id")) {
                    list1.add(this.compound4);
                }
                if (this.compound5 != null && this.compound5.b("id")) {
                    list1.add(this.compound5);
                }
                if (this.compound6 != null && this.compound6.b("id")) {
                    list1.add(this.compound6);
                }
                if (!list1.isEmpty()) {
                    by compound = (by)list1.get(this.npc.aD().nextInt(list1.size()));
                    this.spawnEntity(compound);
                }
            }
        }
    }

    private of getTarget() {
        of entity;
        of target = this.npc.m();
        if (target == null || target.M || target.aN() <= 0.0f) {
            target = this.npc.aE();
        }
        if (target != null && !target.M && target.aN() > 0.0f) {
            return target;
        }
        Iterator<of> i$ = this.spawned.iterator();
        do {
            if (!i$.hasNext()) {
                return null;
            }
            entity = i$.next();
            if (!(entity instanceof og) || (target = ((og)entity).m()) == null || target.M || !(target.aN() > 0.0f)) continue;
            return target;
        } while ((target = entity.aE()) == null || target.M || target.aN() <= 0.0f);
        return target;
    }

    private boolean isEmpty() {
        return !(this.compound1 != null && this.compound1.b("id") || this.compound2 != null && this.compound2.b("id") || this.compound3 != null && this.compound3.b("id") || this.compound4 != null && this.compound4.b("id") || this.compound5 != null && this.compound5.b("id") || this.compound6 != null && this.compound6.b("id"));
    }

    private void setTarget(of base, of target) {
        if (!nt.b((nn)base).equals("Pixelmon") || !(target instanceof uf)) {
            if (base instanceof og) {
                ((og)base).d(target);
            } else {
                base.b(target);
            }
        }
    }

    private void addEntityToList(ArrayList<Object> team, int i) {
        by compound = this.getCompound(i);
        if (compound == null) {
            this.npc.x();
        } else {
            nn entity = nt.a((by)compound, (abw)this.npc.q);
            if (entity != null && nt.b((nn)entity).equals("Pixelmon")) {
                team.add(entity);
            }
        }
    }

    @Override
    public boolean aiShouldExecute() {
        if (!this.isEmpty() && !this.npc.isKilled()) {
            this.target = this.getTarget();
            if (this.npc.aD().nextInt(30) == 1) {
                if (this.spawned.isEmpty()) {
                    this.spawned = this.getNearbySpawned();
                }
                if (this.target == null) {
                    this.reset();
                }
            }
            return this.target != null;
        }
        return false;
    }

    @Override
    public boolean aiContinueExecute() {
        return this.aiShouldExecute();
    }

    @Override
    public void resetTask() {
        this.reset();
    }

    @Override
    public void aiStartExecuting() {
        if (this.spawned.isEmpty()) {
            this.spawned = this.getNearbySpawned();
            if (this.spawned.isEmpty() && !this.spawnEntity(1) && !this.doesntDie) {
                this.npc.x();
            }
        }
        this.number = 0;
        for (of entity : this.spawned) {
            int i = entity.getEntityData().e("NpcSpawnerNr");
            if (i > this.number) {
                this.number = i;
            }
            this.setTarget(entity, this.npc.m());
        }
    }

    @Override
    public void reset() {
        this.number = 0;
        if (!this.spawned.isEmpty()) {
            for (of i$ : this.spawned) {
                i$.M = true;
            }
        } else {
            List list1 = this.npc.q.a(of.class, this.npc.E.b(40.0, 40.0, 40.0));
            for (nn entity : list1) {
                if (!entity.getEntityData().i("NpcSpawnerId").equals(this.id)) continue;
                entity.M = true;
            }
        }
        this.spawned.clear();
    }

    @Override
    public void killed() {
        this.reset();
    }

    private boolean spawnEntity(int i) {
        by compound = this.getCompound(i);
        if (compound == null) {
            return false;
        }
        this.spawnEntity(compound);
        return true;
    }

    private void spawnEntity(by compound) {
        of entity;
        if (compound != null && compound.b("id") && (entity = (of)nt.a((by)compound, (abw)this.npc.q)) != null) {
            entity.getEntityData().a("NpcSpawnerId", this.id);
            entity.getEntityData().a("NpcSpawnerNr", this.number);
            this.setTarget(entity, this.npc.m());
            entity.b(this.npc.u + (double)this.xOffset, this.npc.v + (double)this.yOffset, this.npc.w + (double)this.zOffset);
            if (entity instanceof EntityNPCInterface) {
                EntityNPCInterface snpc = (EntityNPCInterface)entity;
                snpc.stats.spawnCycle = 3;
                snpc.ai.returnToStart = false;
            }
            this.npc.q.d((nn)entity);
            this.spawned.add(entity);
        }
    }

    private by getCompound(int i) {
        if (i <= 1 && this.compound1 != null && this.compound1.b("id")) {
            this.number = 1;
            return this.compound1;
        }
        if (i <= 2 && this.compound2 != null && this.compound2.b("id")) {
            this.number = 2;
            return this.compound2;
        }
        if (i <= 3 && this.compound3 != null && this.compound3.b("id")) {
            this.number = 3;
            return this.compound3;
        }
        if (i <= 4 && this.compound4 != null && this.compound4.b("id")) {
            this.number = 4;
            return this.compound4;
        }
        if (i <= 5 && this.compound5 != null && this.compound5.b("id")) {
            this.number = 5;
            return this.compound5;
        }
        if (i <= 6 && this.compound6 != null && this.compound6.b("id")) {
            this.number = 6;
            return this.compound6;
        }
        return null;
    }

    private List<of> getNearbySpawned() {
        ArrayList<of> spawnList = new ArrayList<of>();
        List list = this.npc.q.a(of.class, this.npc.E.b(40.0, 40.0, 40.0));
        for (of entity : list) {
            if (!entity.getEntityData().i("NpcSpawnerId").equals(this.id) || entity.M) continue;
            spawnList.add(entity);
        }
        return spawnList;
    }
}

