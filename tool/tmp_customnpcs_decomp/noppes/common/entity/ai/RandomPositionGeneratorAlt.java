/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  ls
 *  on
 */
package noppes.common.entity.ai;

import java.util.Random;

public class RandomPositionGeneratorAlt {
    private static final atc staticVector = atc.a((double)0.0, (double)0.0, (double)0.0);

    public static atc findRandomTarget(on par0EntityCreature, int par1, int par2) {
        return RandomPositionGeneratorAlt.findRandomTargetBlock(par0EntityCreature, par1, par2, null);
    }

    public static atc findRandomTargetBlockTowards(on par0EntityCreature, int par1, int par2, atc par3Vec3) {
        RandomPositionGeneratorAlt.staticVector.c = par3Vec3.c - par0EntityCreature.u;
        RandomPositionGeneratorAlt.staticVector.d = par3Vec3.d - par0EntityCreature.v;
        RandomPositionGeneratorAlt.staticVector.e = par3Vec3.e - par0EntityCreature.w;
        return RandomPositionGeneratorAlt.findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
    }

    public static atc findRandomTargetBlockAwayFrom(on par0EntityCreature, int par1, int par2, atc par3Vec3) {
        RandomPositionGeneratorAlt.staticVector.c = par0EntityCreature.u - par3Vec3.c;
        RandomPositionGeneratorAlt.staticVector.d = par0EntityCreature.v - par3Vec3.d;
        RandomPositionGeneratorAlt.staticVector.e = par0EntityCreature.w - par3Vec3.e;
        return RandomPositionGeneratorAlt.findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
    }

    private static atc findRandomTargetBlock(on par0EntityCreature, int par1, int par2, atc par3Vec3) {
        double i2;
        double l1;
        if (par1 <= 0) {
            par1 = 1;
        }
        Random random = par0EntityCreature.aD();
        boolean flag = false;
        int k = 0;
        int l = 0;
        int i1 = 0;
        float f = -99999.0f;
        boolean flag1 = par0EntityCreature.bS() ? (l1 = (double)(par0EntityCreature.bP().e(ls.c((double)par0EntityCreature.u), ls.c((double)par0EntityCreature.v), ls.c((double)par0EntityCreature.w)) + 4.0f)) < (i2 = (double)(par0EntityCreature.bQ() + (float)par1)) * i2 : false;
        for (int var16 = 0; var16 < 10; ++var16) {
            float f1;
            int j1 = random.nextInt(2 * par1) - par1;
            int var17 = random.nextInt(2 * par2) - par2;
            int k1 = random.nextInt(2 * par1) - par1;
            if (par3Vec3 != null && !((double)j1 * par3Vec3.c + (double)k1 * par3Vec3.e >= 0.0)) continue;
            if (random.nextBoolean()) {
                j1 += ls.c((double)par0EntityCreature.u);
                var17 += ls.c((double)par0EntityCreature.v);
                k1 += ls.c((double)par0EntityCreature.w);
            } else {
                j1 += ls.f((double)par0EntityCreature.u);
                var17 += ls.f((double)par0EntityCreature.v);
                k1 += ls.f((double)par0EntityCreature.w);
            }
            if (flag1 && !par0EntityCreature.b(j1, var17, k1) || !((f1 = par0EntityCreature.a(j1, var17, k1)) > f)) continue;
            f = f1;
            k = j1;
            l = var17;
            i1 = k1;
            flag = true;
        }
        if (flag) {
            return par0EntityCreature.q.V().a((double)k, (double)l, (double)i1);
        }
        return null;
    }
}

