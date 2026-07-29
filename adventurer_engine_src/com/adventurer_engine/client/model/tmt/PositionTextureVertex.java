/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  bcf
 */
package com.adventurer_engine.client.model.tmt;

public class PositionTextureVertex
extends bcf {
    public float texturePositionW = 1.0f;

    public PositionTextureVertex(float par1, float par2, float par3, float par4, float par5) {
        this(par1, par2, par3, par4, par5, 1.0f);
    }

    public PositionTextureVertex(float par1, float par2, float par3, float par4, float par5, float par6) {
        this(atc.a((double)par1, (double)par2, (double)par3), par4, par5);
    }

    public PositionTextureVertex setTexturePosition(float par1, float par2) {
        return new PositionTextureVertex(this, par1, par2, 1.0f);
    }

    public PositionTextureVertex setTexturePosition(float par1, float par2, float q) {
        return new PositionTextureVertex(this, par1, par2, q);
    }

    public PositionTextureVertex(PositionTextureVertex par1PositionTextureVertex, float par2, float par3) {
        this(par1PositionTextureVertex, par2, par3, 1.0f);
    }

    public PositionTextureVertex(PositionTextureVertex par1PositionTextureVertex, float par2, float par3, float q) {
        super((bcf)par1PositionTextureVertex, par2, par3);
        this.texturePositionW = q;
    }

    public PositionTextureVertex(atc par1Vec3, float par2, float par3) {
        this(par1Vec3, par2, par3, 1.0f);
    }

    public PositionTextureVertex(atc par1Vec3, float par2, float par3, float par4) {
        super(par1Vec3, par2, par3);
        this.texturePositionW = par4;
    }
}

