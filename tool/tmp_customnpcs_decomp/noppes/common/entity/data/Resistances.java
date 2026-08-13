/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  nb
 */
package noppes.common.entity.data;

public class Resistances {
    public float knockback = 1.0f;
    public float arrow = 1.0f;
    public float playermelee = 1.0f;

    public by writeToNBT() {
        by compound = new by();
        compound.a("Knockback", this.knockback);
        compound.a("Arrow", this.arrow);
        compound.a("Melee", this.playermelee);
        return compound;
    }

    public void readToNBT(by compound) {
        this.knockback = compound.g("Knockback");
        this.arrow = compound.g("Arrow");
        this.playermelee = compound.g("Melee");
    }

    public float applyResistance(nb source, float damage) {
        if (!source.o.equals("arrow") && !source.o.equals("thrown")) {
            if (source.o.equals("player") || source.o.equals("mob")) {
                damage *= 2.0f - this.playermelee;
            }
        } else {
            damage *= 2.0f - this.arrow;
        }
        return damage;
    }
}

