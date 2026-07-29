/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.model.rc;

import com.adventurer_engine.client.model.rc.IRCsRenderObject;
import com.adventurer_engine.client.model.rc.RCsAnimationManager;
import com.adventurer_engine.client.model.rc.RCsModel;
import com.adventurer_engine.client.model.rc.RCsModelElement;
import com.adventurer_engine.util.blockbench.BBModelElement;
import com.adventurer_engine.util.blockbench.BBModelOutliner;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class RCsModelBone
implements IRCsRenderObject {
    public float textureX;
    public float textureY;
    private int textureOffsetX;
    private int textureOffsetY;
    public float originX;
    public float originY;
    public float originZ;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float rotateX;
    public float rotateY;
    public float rotateZ;
    public boolean mirror;
    public boolean showModel;
    public BBModelOutliner bb;
    public boolean isHidden;
    public List<IRCsRenderObject> childModels;
    public final String name;
    public RCsModel model;

    public void addChild(RCsModelBone childBone) {
        if (this.childModels == null) {
            this.childModels = new ArrayList<IRCsRenderObject>();
        }
        this.childModels.add(childBone);
    }

    public RCsModelBone setTextureOffset(int textureX, int textureY) {
        this.textureOffsetX = textureX;
        this.textureOffsetY = textureY;
        return this;
    }

    public void setRotationPoint(float rotateX, float rotateY, float rotateZ) {
        this.originX = rotateX;
        this.originY = rotateY;
        this.originZ = rotateZ;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void render(float magic, RCsAnimationManager animation, float partialTicks) {
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
        animation.doRender(this.bb.uuid, partialTicks, magic);
        GL11.glTranslatef((float)(-this.originX * magic), (float)(-this.originY * magic), (float)(-this.originZ * magic));
        this.renderChildren(magic, animation, partialTicks);
        GL11.glPopMatrix();
    }

    public RCsModelBone setTextureSize(int textureX, int textureY) {
        this.textureX = textureX;
        this.textureY = textureY;
        return this;
    }

    private void renderChildren(float magic, RCsAnimationManager animation, float partialTicks) {
        if (this.childModels == null) {
            return;
        }
        for (IRCsRenderObject obj : this.childModels) {
            obj.render(magic, animation, partialTicks);
        }
    }

    public RCsModelBone(BBModelOutliner bb, RCsModel model) {
        this.name = bb.name;
        this.textureX = model.textureX;
        this.textureY = model.textureY;
        this.childModels = new LinkedList<IRCsRenderObject>();
        this.originX = (float)bb.origin[0];
        this.originY = (float)bb.origin[1];
        this.originZ = (float)bb.origin[2];
        this.rotateX = (float)bb.rotation[0];
        this.rotateY = (float)bb.rotation[1];
        this.rotateZ = (float)bb.rotation[2];
        this.bb = bb;
        for (BBModelOutliner bBModelOutliner : bb.children_outliner) {
            this.childModels.add(new RCsModelBone(bBModelOutliner, model));
        }
        for (BBModelElement bBModelElement : bb.children_element) {
            this.childModels.add(new RCsModelElement(this, bBModelElement));
        }
    }
}

