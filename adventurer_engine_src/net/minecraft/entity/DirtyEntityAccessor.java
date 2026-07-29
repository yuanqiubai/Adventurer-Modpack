/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nb
 *  of
 */
package net.minecraft.entity;

public class DirtyEntityAccessor {
    public static void damageEntity(of target, nb source, float amount) {
        target.d(source, amount);
    }

    public static float getModifiedDamage(of entity, nb source, float amount) {
        amount = entity.b(source, amount);
        amount = entity.c(source, amount);
        return Math.max(amount - entity.bn(), 0.0f);
    }
}

