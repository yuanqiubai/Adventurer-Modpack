/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package noppes.common.entity.model_data;

public class ModelPartConfig {
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float scaleZ = 1.0f;
    public float transX = 0.0f;
    public float transY = 0.0f;
    public float transZ = 0.0f;

    public by writeToNBT() {
        by compound = new by();
        compound.a("ScaleX", this.scaleX);
        compound.a("ScaleY", this.scaleY);
        compound.a("ScaleZ", this.scaleZ);
        compound.a("TransX", this.transX);
        compound.a("TransY", this.transY);
        compound.a("TransZ", this.transZ);
        return compound;
    }

    public void readFromNBT(by compound) {
        this.scaleX = compound.g("ScaleX");
        this.scaleY = compound.g("ScaleY");
        this.scaleZ = compound.g("ScaleZ");
        this.transX = compound.g("TransX");
        this.transY = compound.g("TransY");
        this.transZ = compound.g("TransZ");
    }

    public String toString() {
        return "ScaleX: " + this.scaleX + " - ScaleY: " + this.scaleY + " - ScaleZ: " + this.scaleZ;
    }

    public void setScale(float x, float y, float z2) {
        this.scaleX = x;
        this.scaleY = y;
        this.scaleZ = z2;
    }

    public void setScale(float x, float y) {
        this.scaleZ = this.scaleX = x;
        this.scaleY = y;
    }
}

