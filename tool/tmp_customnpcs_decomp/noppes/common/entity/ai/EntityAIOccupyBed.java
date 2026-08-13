/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  anb
 *  aqz
 *  ps
 */
package noppes.common.entity.ai;

import noppes.common.entity.EntityNPCInterface;

public class EntityAIOccupyBed
extends ps {
    private final EntityNPCInterface npc;
    private final int maxSleepingTicks = 0;
    private int bedX = 0;
    private int bedY = 0;
    private int bedZ = 0;

    public EntityAIOccupyBed(EntityNPCInterface par1EntityNPCInterface) {
        this.npc = par1EntityNPCInterface;
        this.a(5);
    }

    public boolean a() {
        return !this.npc.isSleeping() && this.getNearbyBedDistance() && !this.npc.q.v();
    }

    public boolean b() {
        return !this.npc.q.v() && this.isSittableBlock(this.npc.q, this.bedX, this.bedY, this.bedZ);
    }

    public void c() {
        this.npc.k().a((double)this.bedX + 0.5, (double)(this.bedY + 1), (double)this.bedZ + 0.5, 1.0);
        this.npc.setSleeping(false);
    }

    public void d() {
        this.npc.setSleeping(false);
        this.occupyBed(this.npc.q, this.bedX, this.bedY, this.bedZ, false);
    }

    public void e() {
        if (this.npc.e(this.bedX, this.bedY + 1, this.bedZ) > 1.5) {
            this.npc.setSleeping(false);
            this.npc.k().a((double)this.bedX, (double)(this.bedY + 1), (double)this.bedZ, 1.0);
        } else if (!this.npc.isSleeping()) {
            this.npc.aO = this.npc.aN = (float)this.getDirection(this.npc.q, this.bedX, this.bedY, this.bedZ);
            this.npc.A = this.npc.aN;
            this.npc.C = this.npc.aN;
            this.npc.setSleeping(true);
            this.occupyBed(this.npc.q, this.bedX, this.bedY, this.bedZ, true);
        }
    }

    protected boolean getNearbyBedDistance() {
        int var1 = (int)this.npc.v;
        double var2 = 2.147483647E9;
        int var4 = (int)this.npc.u - 8;
        while ((double)var4 < this.npc.u + 8.0) {
            int var5 = (int)this.npc.w - 8;
            while ((double)var5 < this.npc.w + 8.0) {
                double var6;
                if (this.isSittableBlock(this.npc.q, var4, var1, var5) && this.npc.q.c(var4, var1 + 1, var5) && (var6 = this.npc.e(var4, var1, var5)) < var2) {
                    this.bedX = var4;
                    this.bedY = var1;
                    this.bedZ = var5;
                    var2 = var6;
                }
                ++var5;
            }
            ++var4;
        }
        return var2 < 2.147483647E9;
    }

    protected boolean isSittableBlock(abw par1World, int par2, int par3, int par4) {
        int var5 = par1World.a(par2, par3, par4);
        int var6 = par1World.h(par2, par3, par4);
        return var5 == aqz.X.cF && !anb.f_((int)var6);
    }

    protected int getDirection(abw par1World, int par2, int par3, int par4) {
        int orientation = -1;
        int var6 = par1World.h(par2, par3, par4);
        int var7 = anb.j((int)var6);
        switch (var7) {
            case 0: {
                orientation = 180;
                break;
            }
            case 1: {
                orientation = 270;
                break;
            }
            case 2: {
                orientation = 0;
                break;
            }
            case 3: {
                orientation = 90;
            }
        }
        return orientation;
    }

    protected void occupyBed(abw par1World, int par2, int par3, int par4, boolean par5) {
        int var5 = par1World.a(par2, par3, par4);
        if (var5 == aqz.X.cF) {
            anb.a((abw)par1World, (int)par2, (int)par3, (int)par4, (boolean)par5);
        }
    }
}

