/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model;

import noppes.client.model.ModelNPCMale;
import noppes.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelNPCFemale
extends ModelNPCMale {
    public bcu Breasts;

    public ModelNPCFemale(float f) {
        super(f);
    }

    public ModelNPCFemale(int width, int height, float f) {
        super(width, height, f);
    }

    @Override
    public void init(float f, float f1) {
        super.init(f, f1);
        this.Breasts = new bcu((bbo)this, 24, 0);
        this.Breasts.a(0.0f, 0.0f, 0.0f, 7, 3, 1, 0.0f);
        this.Breasts.a(-3.5f, 1.8f, -2.85f);
        ModelPlaneRenderer BreastsTop = new ModelPlaneRenderer(this, 56, 0);
        BreastsTop.addTopPlane(0.0f, 0.0f, 0.0f, 7, 1, f);
        this.Breasts.a((bcu)BreastsTop);
        ModelPlaneRenderer BreastsFront = new ModelPlaneRenderer(this, 56, 1);
        BreastsFront.addBackPlane(0.0f, 0.0f, 0.0f, 7, 3, f);
        this.Breasts.a((bcu)BreastsFront);
        ModelPlaneRenderer BreastsBottom = new ModelPlaneRenderer(this, 56, 4);
        BreastsBottom.addTopPlane(0.0f, -3.0f, -1.0f, 7, 1, f);
        BreastsBottom.f = (float)Math.PI;
        this.Breasts.a((bcu)BreastsBottom);
        ModelPlaneRenderer BreastsLeft = new ModelPlaneRenderer(this, 63, 0);
        BreastsLeft.addSidePlane(0.0f, 0.0f, 0.0f, 3, 1, f);
        this.Breasts.a((bcu)BreastsLeft);
        ModelPlaneRenderer BreastsRight = new ModelPlaneRenderer(this, 63, 3);
        BreastsRight.addSidePlane(-7.0f, 0.0f, -1.0f, 3, 1, f);
        BreastsRight.g = (float)Math.PI;
        this.Breasts.a((bcu)BreastsRight);
        this.bipedBody.a(this.Breasts);
    }

    @Override
    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        float f6 = 0.85f;
        GL11.glPushMatrix();
        GL11.glScalef((float)f6, (float)f6, (float)f6);
        GL11.glTranslatef((float)0.0f, (float)-0.015f, (float)0.0f);
        this.renderHead(entity, f5);
        GL11.glPopMatrix();
        f6 = 0.8f;
        GL11.glPushMatrix();
        GL11.glScalef((float)f6, (float)0.96f, (float)f6);
        GL11.glTranslatef((float)0.07f, (float)0.0f, (float)0.0f);
        this.renderLeftArm(entity, f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef((float)f6, (float)0.96f, (float)f6);
        GL11.glTranslatef((float)-0.07f, (float)0.0f, (float)0.0f);
        this.renderRightArm(entity, f5);
        GL11.glPopMatrix();
        this.renderLegs(entity, f5);
        this.renderBody(entity, f5);
    }
}

