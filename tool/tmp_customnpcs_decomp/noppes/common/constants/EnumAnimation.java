/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.constants;

public enum EnumAnimation {
    NONE,
    SITTING,
    LYING,
    SNEAKING,
    DANCING,
    Aiming,
    CRAWLING,
    HUG,
    CRY,
    WAVING,
    BOW;


    public int getWalkingAnimation() {
        return this == SNEAKING ? 1 : (this == Aiming ? 2 : (this == DANCING ? 3 : 0));
    }
}

