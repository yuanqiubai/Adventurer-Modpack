/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  ls
 */
package com.adventurer_engine.client.model.tmt;

import com.adventurer_engine.client.model.tmt.Coord2D;
import com.adventurer_engine.client.model.tmt.PositionTextureVertex;
import com.adventurer_engine.client.model.tmt.PositionTransformVertex;
import com.adventurer_engine.client.model.tmt.Shape3D;
import com.adventurer_engine.client.model.tmt.TexturedPolygon;
import java.util.ArrayList;

public class Shape2D {
    public ArrayList<Coord2D> coords;

    public Shape2D() {
        this.coords = new ArrayList();
    }

    public Shape2D(Coord2D[] coordArray) {
        this.coords = new ArrayList();
        for (int idx = 0; idx < coordArray.length; ++idx) {
            this.coords.add(coordArray[idx]);
        }
    }

    public Shape2D(ArrayList<Coord2D> coordList) {
        this.coords = coordList;
    }

    public Coord2D[] getCoordArray() {
        return (Coord2D[])this.coords.toArray();
    }

    public Shape3D extrude(float x, float y, float z, float rotX, float rotY, float rotZ, float depth, int u, int v, float textureWidth, float textureHeight, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float[] faceLengths) {
        PositionTransformVertex[] verts = new PositionTransformVertex[this.coords.size() * 2];
        PositionTextureVertex[] vertsTop = new PositionTransformVertex[this.coords.size()];
        PositionTextureVertex[] vertsBottom = new PositionTransformVertex[this.coords.size()];
        TexturedPolygon[] poly = new TexturedPolygon[this.coords.size() + 2];
        atc extrudeVector = atc.a((double)0.0, (double)0.0, (double)depth);
        this.setVectorRotations(extrudeVector, rotX, rotY, rotZ);
        if (faceLengths != null && faceLengths.length < this.coords.size()) {
            faceLengths = null;
        }
        float totalLength = 0.0f;
        for (int idx = 0; idx < this.coords.size(); ++idx) {
            Coord2D curCoord = this.coords.get(idx);
            Coord2D nextCoord = this.coords.get((idx + 1) % this.coords.size());
            float texU1 = (float)(curCoord.uCoord + u) / textureWidth;
            float texU2 = (float)(shapeTextureWidth * 2 - curCoord.uCoord + u) / textureWidth;
            float texV = (float)(curCoord.vCoord + v) / textureHeight;
            atc vecCoord = atc.a((double)curCoord.xCoord, (double)curCoord.yCoord, (double)0.0);
            this.setVectorRotations(vecCoord, rotX, rotY, rotZ);
            verts[idx] = new PositionTransformVertex(x + (float)vecCoord.c, y + (float)vecCoord.d, z + (float)vecCoord.e, texU1, texV);
            verts[idx + this.coords.size()] = new PositionTransformVertex(x + (float)vecCoord.c - (float)extrudeVector.c, y + (float)vecCoord.d - (float)extrudeVector.d, z + (float)vecCoord.e - (float)extrudeVector.e, texU2, texV);
            vertsTop[idx] = new PositionTransformVertex(verts[idx]);
            vertsBottom[this.coords.size() - idx - 1] = new PositionTransformVertex(verts[idx + this.coords.size()]);
            if (faceLengths != null) {
                totalLength += faceLengths[idx];
                continue;
            }
            totalLength = (float)((double)totalLength + Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2.0) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2.0)));
        }
        poly[this.coords.size()] = new TexturedPolygon(vertsTop);
        poly[this.coords.size() + 1] = new TexturedPolygon(vertsBottom);
        float currentLengthPosition = totalLength;
        for (int idx = 0; idx < this.coords.size(); ++idx) {
            Coord2D curCoord = this.coords.get(idx);
            Coord2D nextCoord = this.coords.get((idx + 1) % this.coords.size());
            float currentLength = (float)Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2.0) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2.0));
            if (faceLengths != null) {
                currentLength = faceLengths[faceLengths.length - idx - 1];
            }
            float ratioPosition = currentLengthPosition / totalLength;
            float ratioLength = (currentLengthPosition - currentLength) / totalLength;
            float texU1 = (ratioLength * (float)sideTextureWidth + (float)u) / textureWidth;
            float texU2 = (ratioPosition * (float)sideTextureWidth + (float)u) / textureWidth;
            float texV1 = ((float)v + (float)shapeTextureHeight) / textureHeight;
            float texV2 = ((float)v + (float)shapeTextureHeight + (float)sideTextureHeight) / textureHeight;
            PositionTextureVertex[] polySide = new PositionTransformVertex[]{new PositionTransformVertex(verts[idx], texU2, texV1), new PositionTransformVertex(verts[this.coords.size() + idx], texU2, texV2), new PositionTransformVertex(verts[this.coords.size() + (idx + 1) % this.coords.size()], texU1, texV2), new PositionTransformVertex(verts[(idx + 1) % this.coords.size()], texU1, texV1)};
            poly[idx] = new TexturedPolygon(polySide);
            currentLengthPosition -= currentLength;
        }
        return new Shape3D(verts, poly);
    }

    protected void setVectorRotations(atc vector, float xRot, float yRot, float zRot) {
        float x = xRot;
        float y = yRot;
        float z = zRot;
        float xC = ls.b((float)x);
        float xS = ls.a((float)x);
        float yC = ls.b((float)y);
        float yS = ls.a((float)y);
        float zC = ls.b((float)z);
        float zS = ls.a((float)z);
        double xVec = vector.c;
        double yVec = vector.d;
        double zVec = vector.e;
        double xy = (double)xC * yVec - (double)xS * zVec;
        double xz = (double)xC * zVec + (double)xS * yVec;
        double yz = (double)yC * xz - (double)yS * xVec;
        double yx = (double)yC * xVec + (double)yS * xz;
        double zx = (double)zC * yx - (double)zS * xy;
        double zy = (double)zC * xy + (double)zS * yx;
        xVec = zx;
        yVec = zy;
        zVec = yz;
        vector.c = xVec;
        vector.d = yVec;
        vector.e = zVec;
    }
}

