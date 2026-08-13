/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.constants;

import java.util.ArrayList;

public enum EnumMovingType {
    Standing("ai.standing"),
    Wandering("ai.wandering"),
    MovingPath("ai.movingpath");

    String name;

    private EnumMovingType(String name) {
        this.name = name;
    }

    public static String[] names() {
        ArrayList<String> list = new ArrayList<String>();
        for (EnumMovingType e : EnumMovingType.values()) {
            list.add(e.name);
        }
        return list.toArray(new String[list.size()]);
    }
}

