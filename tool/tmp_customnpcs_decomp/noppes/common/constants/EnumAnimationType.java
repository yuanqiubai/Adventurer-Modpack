/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.constants;

public enum EnumAnimationType {
    Normal,
    Sitting,
    Lying,
    Sneaking,
    Dancing,
    Aiming;


    public int getWalkingAnimation() {
        return this == Sneaking ? 1 : (this == Aiming ? 2 : (this == Dancing ? 3 : 0));
    }
}

