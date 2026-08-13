/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.constants;

public enum EnumParticleType {
    None(""),
    Smoke("smoke"),
    Portal("portal"),
    Redstone("reddust"),
    Lightning("magicCrit"),
    LargeSmoke("largesmoke"),
    Magic("witchMagic"),
    Enchant("enchantmenttable"),
    Crit("crit");

    public String particleName;

    private EnumParticleType(String name) {
        this.particleName = name;
    }
}

