/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model;

import noppes.client.model.ModelNPCFemale;
import org.lwjgl.opengl.GL11;

public class ModelElfFemale
extends ModelNPCFemale {
    public ModelElfFemale(float f) {
        super(f);
    }

    @Override
    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        float f6 = 0.85f;
        GL11.glPushMatrix();
        GL11.glScalef((float)f6, (float)(f6 - 0.1f), (float)f6);
        GL11.glTranslatef((float)0.0f, (float)-0.015f, (float)0.0f);
        this.renderHead(entity, f5);
        GL11.glPopMatrix();
        f6 = 0.74f;
        GL11.glPushMatrix();
        GL11.glScalef((float)f6, (float)0.9f, (float)f6);
        GL11.glTranslatef((float)0.09f, (float)0.0f, (float)0.0f);
        this.renderLeftArm(entity, f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef((float)f6, (float)0.9f, (float)f6);
        GL11.glTranslatef((float)-0.09f, (float)0.0f, (float)0.0f);
        this.renderRightArm(entity, f5);
        GL11.glPopMatrix();
        this.renderBody(entity, f5);
        this.renderLegs(entity, f5);
    }
}

