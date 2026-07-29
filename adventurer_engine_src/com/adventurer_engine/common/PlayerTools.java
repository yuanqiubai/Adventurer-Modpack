/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.common;

import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicRangedWeapon;
import java.util.Random;

public class PlayerTools {
    private static Random ran = new Random();

    public static boolean hasItem(uf player, yc item) {
        ye[] items;
        for (ye x : items = player.bn.a) {
            if (x == null || !x.b().equals(item)) continue;
            return true;
        }
        return false;
    }

    public static boolean consumeItem(uf player, yc item) {
        return true;
    }

    public static float randomGaussian(float expectation, float variance) {
        if (expectation == 0.0f) {
            return 0.0f;
        }
        if (variance == 0.0f) {
            return expectation;
        }
        return (float)(ran.nextGaussian() * Math.sqrt(variance) + (double)expectation);
    }

    public static boolean percent(float p) {
        return ran.nextFloat() < p;
    }

    public static double[] disturbVector(double x, double y, double z2, float acc) {
        double[] result = new double[3];
        double magnitude = Math.sqrt(x * x + y * y + z2 * z2);
        result[0] = x + (double)(1.0f - acc) * (ran.nextDouble() - 0.5) * magnitude;
        result[1] = y + (double)(1.0f - acc) * (ran.nextDouble() - 0.5) * magnitude;
        result[2] = z2 + (double)(1.0f - acc) * (ran.nextDouble() - 0.5) * magnitude;
        return result;
    }

    public static boolean consumeAmmo(uf player, String name) {
        if (player.bG.d) {
            return true;
        }
        if ("arrow".equals(name)) {
            return player.bn.d(yc.n.cv);
        }
        BasicAmmo am = (BasicAmmo)BasicAmmo.getAmmoByString(name);
        if (am.unbreakable) {
            return true;
        }
        if (am.maxAmmoAmount == 1) {
            return player.bn.d(am.cv);
        }
        int max = -1;
        int max_idx = 0;
        for (int i = 0; i < player.bn.a.length; ++i) {
            if (player.bn.a[i] == null || player.bn.a[i].d != am.cv || player.bn.a[i].k() <= max) continue;
            max = player.bn.a[i].k();
            max_idx = i;
        }
        if (max == -1) {
            return false;
        }
        ye stack = player.bn.a[max_idx];
        if (stack.b > 1) {
            ye newStack = stack.a(1);
            if (newStack.k() + 1 != am.maxAmmoAmount) {
                newStack.a(1, ran);
                player.bn.a(newStack);
            }
        } else if (stack.k() + 1 == am.maxAmmoAmount) {
            player.bn.a[max_idx] = null;
        } else {
            stack.a(1, ran);
        }
        return true;
    }

    public static int[] ammoAmountList(uf player, BasicRangedWeapon weapon) {
        int[] amounts = new int[weapon.allowed_ammo.size()];
        for (int i = 0; i < weapon.allowed_ammo.size(); ++i) {
            amounts[i] = PlayerTools.ammoAmount(player, weapon.allowed_ammo.get(i));
        }
        return amounts;
    }

    public static int ammoAmount(uf player, String ammoName) {
        int result = 0;
        if ("arrow".equals(ammoName)) {
            for (ye i : player.bn.a) {
                if (i == null || i.b().cv != yc.n.cv) continue;
                result += i.b;
            }
        } else {
            BasicAmmo am = (BasicAmmo)BasicAmmo.getAmmoByString(ammoName);
            for (ye i : player.bn.a) {
                if (i == null || am == null || i.b().cv != am.cv) continue;
                result += i.b * (am.maxAmmoAmount - i.k());
            }
        }
        return result;
    }
}

