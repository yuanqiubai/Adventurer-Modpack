/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.PositionTextureVertex;
import com.adventurer_engine.client.model.tmt.PositionTransformVertex;
import com.adventurer_engine.client.model.tmt.TmtTessellator;
import java.util.ArrayList;

public class TexturedPolygon {
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal = false;
    private float[] normals;
    private ArrayList<atc> iNormals;

    public TexturedPolygon(PositionTextureVertex[] apositionTexturevertex) {
        this.vertexPositions = apositionTexturevertex;
        this.nVertices = apositionTexturevertex.length;
        this.iNormals = new ArrayList();
        this.normals = new float[0];
    }

    public TexturedPolygon(PositionTextureVertex[] apositionTexturevertex, int par2, int par3, int par4, int par5, float par6, float par7) {
        this(apositionTexturevertex);
        float var8 = 0.0f / par6;
        float var9 = 0.0f / par7;
        apositionTexturevertex[0] = apositionTexturevertex[0].setTexturePosition((float)par4 / par6 - var8, (float)par3 / par7 + var9);
        apositionTexturevertex[1] = apositionTexturevertex[1].setTexturePosition((float)par2 / par6 + var8, (float)par3 / par7 + var9);
        apositionTexturevertex[2] = apositionTexturevertex[2].setTexturePosition((float)par2 / par6 + var8, (float)par5 / par7 - var9);
        apositionTexturevertex[3] = apositionTexturevertex[3].setTexturePosition((float)par4 / par6 - var8, (float)par5 / par7 - var9);
    }

    public void setInvertNormal(boolean isSet) {
        this.invertNormal = isSet;
    }

    public void setNormals(float x, float y, float z) {
        this.normals = new float[]{x, y, z};
    }

    public void flipFace() {
        PositionTextureVertex[] var1 = new PositionTextureVertex[this.vertexPositions.length];
        for (int var2 = 0; var2 < this.vertexPositions.length; ++var2) {
            var1[var2] = this.vertexPositions[this.vertexPositions.length - var2 - 1];
        }
        this.vertexPositions = var1;
    }

    public void setNormals(ArrayList<atc> vec) {
        this.iNormals = vec;
    }

    public void draw(TmtTessellator tessellator, float f) {
        if (this.nVertices == 3) {
            tessellator.b(4);
        } else if (this.nVertices == 4) {
            tessellator.b();
        } else {
            tessellator.b(9);
        }
        if (this.iNormals.isEmpty()) {
            if (this.normals.length == 3) {
                if (this.invertNormal) {
                    tessellator.b(-this.normals[0], -this.normals[1], -this.normals[2]);
                } else {
                    tessellator.b(this.normals[0], this.normals[1], this.normals[2]);
                }
            } else if (this.vertexPositions.length >= 3) {
                atc Vec3 = this.vertexPositions[1].a.a(this.vertexPositions[0].a);
                atc Vec31 = this.vertexPositions[1].a.a(this.vertexPositions[2].a);
                atc Vec32 = Vec31.c(Vec3).a();
                if (this.invertNormal) {
                    tessellator.b(-((float)Vec32.c), -((float)Vec32.d), -((float)Vec32.e));
                } else {
                    tessellator.b((float)Vec32.c, (float)Vec32.d, (float)Vec32.e);
                }
            } else {
                return;
            }
        }
        for (int i = 0; i < this.nVertices; ++i) {
            PositionTextureVertex positionTexturevertex = this.vertexPositions[i];
            if (positionTexturevertex instanceof PositionTransformVertex) {
                ((PositionTransformVertex)positionTexturevertex).setTransformation();
            }
            if (i < this.iNormals.size()) {
                if (this.invertNormal) {
                    tessellator.b(-((float)this.iNormals.get((int)i).c), -((float)this.iNormals.get((int)i).d), -((float)this.iNormals.get((int)i).e));
                } else {
                    tessellator.b((float)this.iNormals.get((int)i).c, (float)this.iNormals.get((int)i).d, (float)this.iNormals.get((int)i).e);
                }
            }
            tessellator.addVertexWithUVW((float)positionTexturevertex.a.c * f, (float)positionTexturevertex.a.d * f, (float)positionTexturevertex.a.e * f, positionTexturevertex.b, positionTexturevertex.c, positionTexturevertex.texturePositionW);
        }
        tessellator.a();
    }
}

