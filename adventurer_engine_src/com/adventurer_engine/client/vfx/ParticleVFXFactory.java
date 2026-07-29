/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  bdq
 *  beg
 *  nn
 */
package com.adventurer_engine.client.vfx;

public class ParticleVFXFactory {
    public static final short CRIT = 0;
    public static final short FIRE_CRIT = 1;

    public static beg generateParticleFX(abw world, nn entity, int id) {
        switch (id) {
            case 0: {
                return new bdq(world, entity);
            }
            case 1: {
                return new bdq(world, entity, "flame");
            }
        }
        return null;
    }
}

