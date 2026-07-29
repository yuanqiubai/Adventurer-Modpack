/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atu
 *  bfq
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.model.rc;

import com.adventurer_engine.client.model.rc.IRCsRenderObject;
import com.adventurer_engine.client.model.rc.RCsAnimationManager;
import com.adventurer_engine.client.model.rc.RCsModelBone;
import com.adventurer_engine.client.model.rc.RCsModelBox;
import com.adventurer_engine.util.blockbench.BBModelElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class RCsModelElement
implements IRCsRenderObject {
    public String name;
    public RCsModelBox box;
    public float rotateX;
    public float rotateY;
    public float rotateZ;
    public float originX;
    public float originY;
    public float originZ;
    private boolean compiled = false;
    private int displayList;

    public RCsModelElement(RCsModelBone bone, BBModelElement bb) {
        this.rotateX = (float)bb.rotation[0];
        this.rotateY = (float)bb.rotation[1];
        this.rotateZ = (float)bb.rotation[2];
        this.originX = (float)bb.origin[0];
        this.originY = (float)bb.origin[1];
        this.originZ = (float)bb.origin[2];
        this.name = bb.name;
        this.box = new RCsModelBox(bone, bb);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void render(float magic, RCsAnimationManager animation, float partialTicks) {
        if (!this.compiled) {
            this.compileDisplayList(magic);
        }
        if (this.rotateX == 0.0f && this.rotateY == 0.0f && this.rotateZ == 0.0f) {
            GL11.glCallList((int)this.displayList);
        } else {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.originX * magic), (float)(this.originY * magic), (float)(this.originZ * magic));
            if (this.rotateX != 0.0f) {
                GL11.glRotatef((float)this.rotateX, (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (this.rotateY != 0.0f) {
                GL11.glRotatef((float)this.rotateY, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (this.rotateZ != 0.0f) {
                GL11.glRotatef((float)this.rotateZ, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glTranslatef((float)(-this.originX * magic), (float)(-this.originY * magic), (float)(-this.originZ * magic));
            GL11.glCallList((int)this.displayList);
            GL11.glPopMatrix();
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void compileDisplayList(float magic) {
        this.displayList = atu.a((int)1);
        GL11.glNewList((int)this.displayList, (int)4864);
        bfq tessellator = bfq.a;
        this.box.render(tessellator, magic);
        GL11.glEndList();
        this.compiled = true;
    }
}

