/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ko
 *  yc
 */
package noppes.client.quest;

import noppes.common.objcenter.CustomItems;

public class QuestAchievement
extends ko {
    private final String description;

    public QuestAchievement(String par2Str, String description) {
        super(-1, par2Str, 0, 0, CustomItems.letter == null ? yc.aM : CustomItems.letter, null);
        this.description = description;
    }

    public String i() {
        return this.a.substring(12);
    }

    public String e() {
        return this.description;
    }
}

