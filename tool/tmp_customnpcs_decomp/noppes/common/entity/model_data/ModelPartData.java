/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package noppes.common.entity.model_data;

import noppes.CustomNpcs;

public class ModelPartData {
    public int color = 0xFFFFFF;
    public String texture;
    public byte type = 0;
    public boolean playerTexture;
    private Object location;

    public ModelPartData() {
        this.playerTexture = true;
    }

    public ModelPartData(String texture) {
        this.texture = texture;
        this.playerTexture = false;
    }

    public by writeToNBT() {
        by compound = new by();
        compound.a("Type", this.type);
        compound.a("Color", this.color);
        if (this.texture != null && !this.texture.isEmpty()) {
            compound.a("Texture", this.texture);
        }
        compound.a("PlayerTexture", this.playerTexture);
        return compound;
    }

    public void readFromNBT(by compound) {
        this.type = compound.c("Type");
        this.color = compound.e("Color");
        this.texture = compound.i("Texture");
        this.playerTexture = compound.n("PlayerTexture");
        this.location = null;
    }

    public Object getResource() {
        if (this.location != null) {
            return this.location;
        }
        this.location = CustomNpcs.proxy.loadResource(this.texture);
        return this.location;
    }

    public void setTexture(String texture, int type) {
        this.type = (byte)type;
        this.location = null;
        if (texture.isEmpty()) {
            this.playerTexture = true;
            this.texture = texture;
        } else {
            this.texture = "moreplayermodels:textures/" + texture + ".png";
            this.playerTexture = false;
        }
    }

    public String toString() {
        return "Color: " + this.color + " Type: " + this.type;
    }

    public String getColor() {
        String str = Integer.toHexString(this.color);
        while (str.length() < 6) {
            str = "0" + str;
        }
        return str;
    }
}

