/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.constants;

import java.util.ArrayList;

public enum EnumNavType {
    Default("aitactics.rush"),
    Dodge("aitactics.stagger"),
    Surround("aitactics.orbit"),
    Ambush("aitactics.ambush"),
    Stalk("aitactics.stalk");

    String name;

    private EnumNavType(String name) {
        this.name = name;
    }

    public static String[] names() {
        ArrayList<String> list = new ArrayList<String>();
        for (EnumNavType e : EnumNavType.values()) {
            list.add(e.name);
        }
        return list.toArray(new String[list.size()]);
    }
}

