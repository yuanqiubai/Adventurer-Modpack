/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atc
 *  bbs
 *  bcf
 *  bcp
 *  bcu
 *  bfq
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.model.rc;

import com.adventurer_engine.client.model.rc.RCsModelBone;
import com.adventurer_engine.util.DebugGadgets;
import com.adventurer_engine.util.blockbench.BBModelElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class RCsModelBox {
    private bcf[] vertexPositions;
    private bbs[] quadList;
    public float posX1;
    public float posY1;
    public float posZ1;
    public float posX2;
    public float posY2;
    public float posZ2;
    public String name;

    public RCsModelBox(RCsModelBone bone, BBModelElement bb) {
        this.posX1 = (float)bb.from[0];
        this.posY1 = (float)bb.from[1];
        this.posZ1 = (float)bb.from[2];
        this.posX2 = (float)bb.to[0];
        this.posY2 = (float)bb.to[1];
        this.posZ2 = (float)bb.to[2];
        this.vertexPositions = new bcf[8];
        this.quadList = new bbs[6];
        this.name = bb.name;
        if (bone.mirror) {
            float tmp = this.posX2;
            this.posX2 = this.posX1;
            this.posX1 = tmp;
        }
        bcf v1 = new bcf(this.posX1, this.posY1, this.posZ1, 0.0f, 0.0f);
        bcf v2 = new bcf(this.posX2, this.posY1, this.posZ1, 0.0f, 8.0f);
        bcf v3 = new bcf(this.posX2, this.posY2, this.posZ1, 8.0f, 8.0f);
        bcf v4 = new bcf(this.posX1, this.posY2, this.posZ1, 8.0f, 0.0f);
        bcf v5 = new bcf(this.posX1, this.posY1, this.posZ2, 0.0f, 0.0f);
        bcf v6 = new bcf(this.posX2, this.posY1, this.posZ2, 0.0f, 8.0f);
        bcf v7 = new bcf(this.posX2, this.posY2, this.posZ2, 8.0f, 8.0f);
        bcf v8 = new bcf(this.posX1, this.posY2, this.posZ2, 8.0f, 0.0f);
        this.vertexPositions[0] = v1;
        this.vertexPositions[1] = v2;
        this.vertexPositions[2] = v3;
        this.vertexPositions[3] = v4;
        this.vertexPositions[4] = v5;
        this.vertexPositions[5] = v6;
        this.vertexPositions[6] = v7;
        this.vertexPositions[7] = v8;
        BBModelElement.FaceUV faceUV = bb.faces[3];
        this.quadList[0] = new bbs(new bcf[]{v6, v2, v3, v7}, faceUV.uv[2], faceUV.uv[3], faceUV.uv[0], faceUV.uv[1], bone.textureX, bone.textureY);
        faceUV = bb.faces[1];
        this.quadList[1] = new bbs(new bcf[]{v1, v5, v8, v4}, faceUV.uv[2], faceUV.uv[3], faceUV.uv[0], faceUV.uv[1], bone.textureX, bone.textureY);
        faceUV = bb.faces[5];
        this.quadList[2] = new bbs(new bcf[]{v6, v5, v1, v2}, faceUV.uv[2], faceUV.uv[3], faceUV.uv[0], faceUV.uv[1], bone.textureX, bone.textureY);
        faceUV = bb.faces[4];
        this.quadList[3] = new bbs(new bcf[]{v3, v4, v8, v7}, faceUV.uv[2], faceUV.uv[3], faceUV.uv[0], faceUV.uv[1], bone.textureX, bone.textureY);
        faceUV = bb.faces[0];
        this.quadList[4] = new bbs(new bcf[]{v2, v1, v4, v3}, faceUV.uv[2], faceUV.uv[3], faceUV.uv[0], faceUV.uv[1], bone.textureX, bone.textureY);
        faceUV = bb.faces[2];
        this.quadList[5] = new bbs(new bcf[]{v5, v6, v7, v8}, faceUV.uv[2], faceUV.uv[3], faceUV.uv[0], faceUV.uv[1], bone.textureX, bone.textureY);
        if (bone.mirror) {
            for (bbs texturedQuad : this.quadList) {
                texturedQuad.a();
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void render(bfq tessellator, float magic) {
        for (bbs texturedQuad : this.quadList) {
            this.draw(tessellator, magic, texturedQuad);
        }
    }

    public RCsModelBox rename(String newName) {
        this.name = newName;
        return this;
    }

    private void draw(bfq tessellator, float magic, bbs quad) {
        GL11.glEnable((int)32826);
        atc vec1 = quad.a[1].a.a(quad.a[0].a);
        atc vec2 = quad.a[1].a.a(quad.a[2].a);
        atc vec3 = vec2.c(vec1).a();
        tessellator.b();
        tessellator.b(-((float)vec3.c), -((float)vec3.d), -((float)vec3.e));
        for (bcf v : quad.a) {
            tessellator.a((double)((float)v.a.c * magic), (double)((float)v.a.d * magic), (double)((float)v.a.e * magic), (double)v.b, (double)v.c);
        }
        tessellator.a();
        GL11.glDisable((int)32826);
    }

    private void debugInfo() {
        DebugGadgets.debugInfo("================================");
        DebugGadgets.debugInfo("Box name = " + this.name);
        DebugGadgets.debugInfo(String.format("pos1 = (%f, %f, %f)", Float.valueOf(this.posX1), Float.valueOf(this.posY1), Float.valueOf(this.posZ1)));
        DebugGadgets.debugInfo(String.format("pos2 = (%f, %f, %f)", Float.valueOf(this.posX2), Float.valueOf(this.posY2), Float.valueOf(this.posZ2)));
        for (int i = 0; i < 8; ++i) {
            DebugGadgets.debugInfo(String.format("vertexPositions[%d] = (%f, %f, %f)", i, this.vertexPositions[i].a.c, this.vertexPositions[i].a.d, this.vertexPositions[i].a.e));
        }
        DebugGadgets.debugInfo("================================");
    }

    public bcp toBox(bcu renderer) {
        bcp box = new bcp(renderer, 0, 0, 0.0f, 0.0f, 0.0f, 0, 0, 0, 0.0f);
        box.h = this.vertexPositions;
        box.i = this.quadList;
        return box;
    }

    public RCsModelBox(RCsModelBone bone, int textureX, int textureY, float originX, float originY, float originZ, int xLen, int yLen, int zLen, float scaleFactor) {
        this.posX1 = originX;
        this.posY1 = originY;
        this.posZ1 = originZ;
        this.posX2 = originX + (float)xLen;
        this.posY2 = originY + (float)yLen;
        this.posZ2 = originZ + (float)zLen;
        this.vertexPositions = new bcf[8];
        this.quadList = new bbs[6];
        float x2 = originX + (float)xLen;
        float y2 = originY + (float)yLen;
        float z2 = originZ + (float)zLen;
        originX -= scaleFactor;
        originY -= scaleFactor;
        originZ -= scaleFactor;
        x2 += scaleFactor;
        y2 += scaleFactor;
        z2 += scaleFactor;
        if (bone.mirror) {
            float tmp = x2;
            x2 = originX;
            originX = tmp;
        }
        bcf v1 = new bcf(originX, originY, originZ, 0.0f, 0.0f);
        bcf v2 = new bcf(x2, originY, originZ, 0.0f, 8.0f);
        bcf v3 = new bcf(x2, y2, originZ, 8.0f, 8.0f);
        bcf v4 = new bcf(originX, y2, originZ, 8.0f, 0.0f);
        bcf v5 = new bcf(originX, originY, z2, 0.0f, 0.0f);
        bcf v6 = new bcf(x2, originY, z2, 0.0f, 8.0f);
        bcf v7 = new bcf(x2, y2, z2, 8.0f, 8.0f);
        bcf v8 = new bcf(originX, y2, z2, 8.0f, 0.0f);
        this.vertexPositions[0] = v1;
        this.vertexPositions[1] = v2;
        this.vertexPositions[2] = v3;
        this.vertexPositions[3] = v4;
        this.vertexPositions[4] = v5;
        this.vertexPositions[5] = v6;
        this.vertexPositions[6] = v7;
        this.vertexPositions[7] = v8;
        this.quadList[0] = new bbs(new bcf[]{v6, v2, v3, v7}, textureX + zLen + xLen, textureY + zLen, textureX + zLen + xLen + zLen, textureY + zLen + yLen, bone.textureX, bone.textureY);
        this.quadList[1] = new bbs(new bcf[]{v1, v5, v8, v4}, textureX, textureY + zLen, textureX + zLen, textureY + zLen + yLen, bone.textureX, bone.textureY);
        this.quadList[2] = new bbs(new bcf[]{v6, v5, v1, v2}, textureX + zLen, textureY, textureX + zLen + xLen, textureY + zLen, bone.textureX, bone.textureY);
        this.quadList[3] = new bbs(new bcf[]{v3, v4, v8, v7}, textureX + zLen + xLen, textureY + zLen, textureX + zLen + xLen + xLen, textureY, bone.textureX, bone.textureY);
        this.quadList[4] = new bbs(new bcf[]{v2, v1, v4, v3}, textureX + zLen, textureY + zLen, textureX + zLen + xLen, textureY + zLen + yLen, bone.textureX, bone.textureY);
        this.quadList[5] = new bbs(new bcf[]{v5, v6, v7, v8}, textureX + zLen + xLen + zLen, textureY + zLen, textureX + zLen + xLen + zLen + xLen, textureY + zLen + yLen, bone.textureX, bone.textureY);
        if (bone.mirror) {
            for (bbs texturedQuad : this.quadList) {
                texturedQuad.a();
            }
        }
    }
}

