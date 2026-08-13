/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model;

import noppes.client.model.ModelNPCMale;
import org.lwjgl.opengl.GL11;

public class ModelDwarfMale
extends ModelNPCMale {
    public ModelDwarfMale(float f) {
        super(f);
    }

    @Override
    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.renderHead(entity, f5);
        GL11.glPushMatrix();
        GL11.glScalef((float)1.1f, (float)1.0f, (float)1.0f);
        this.renderArms(entity, f5);
        this.renderLegs(entity, f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef((float)1.1f, (float)1.0f, (float)1.55f);
        this.renderBody(entity, f5);
        GL11.glPopMatrix();
    }
}

