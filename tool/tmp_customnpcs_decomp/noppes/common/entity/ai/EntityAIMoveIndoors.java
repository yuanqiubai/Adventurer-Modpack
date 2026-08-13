/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  atc
 *  ls
 *  on
 *  ps
 */
package noppes.common.entity.ai;

import java.util.Random;

public class EntityAIMoveIndoors
extends ps {
    private final on theCreature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final abw theWorld;

    public EntityAIMoveIndoors(on par1EntityCreature) {
        this.theCreature = par1EntityCreature;
        this.theWorld = par1EntityCreature.q;
        this.a(1);
    }

    public boolean a() {
        int x = ls.c((double)this.theCreature.u);
        int y = (int)this.theCreature.E.b;
        int z2 = ls.c((double)this.theCreature.w);
        if (!(this.theCreature.q.v() && !this.theCreature.q.Q() || this.theCreature.q.t.g)) {
            if (!this.theWorld.l(x, y, z2) && this.theWorld.m(x, y, z2) > 8) {
                return false;
            }
            atc var1 = this.findPossibleShelter();
            if (var1 == null) {
                return false;
            }
            this.shelterX = var1.c;
            this.shelterY = var1.d;
            this.shelterZ = var1.e;
            return true;
        }
        return false;
    }

    public boolean b() {
        return !this.theCreature.k().g();
    }

    public void c() {
        this.theCreature.k().a(this.shelterX, this.shelterY, this.shelterZ, 1.0);
    }

    private atc findPossibleShelter() {
        Random var1 = this.theCreature.aD();
        for (int var2 = 0; var2 < 10; ++var2) {
            int var5;
            int var4;
            int var3 = ls.c((double)(this.theCreature.u + (double)var1.nextInt(20) - 10.0));
            if (this.theWorld.l(var3, var4 = ls.c((double)(this.theCreature.E.b + (double)var1.nextInt(6) - 3.0)), var5 = ls.c((double)(this.theCreature.w + (double)var1.nextInt(20) - 10.0))) || this.theWorld.m(var3, var4, var5) <= 8) continue;
            return this.theWorld.V().a((double)var3, (double)var4, (double)var5);
        }
        return null;
    }
}

