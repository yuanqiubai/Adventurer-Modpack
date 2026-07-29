/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.items.blank.ranged_weapon;

public enum ShootMode {
    SEMI,
    AUTO;


    public static ShootMode getByString(String name) {
        if (name.equalsIgnoreCase("semi")) {
            return SEMI;
        }
        if (name.equalsIgnoreCase("auto")) {
            return AUTO;
        }
        return null;
    }

    public static ShootMode getByInt(int id) {
        switch (id) {
            case 0: {
                return SEMI;
            }
            case 1: {
                return AUTO;
            }
        }
        return SEMI;
    }
}

