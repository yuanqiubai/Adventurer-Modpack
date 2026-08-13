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
import org.lwjgl.opengl.GL11;

public class ModelEnderChibi
extends ModelNPCMale {
    public ModelEnderChibi(float f) {
        super(f);
    }

    @Override
    public void init(float f, float f1) {
        super.init(f, f1);
        this.bipedRightArm = new bcu((bbo)this, 44, 18);
        this.bipedRightArm.a(-1.0f, 4.0f, -1.0f, 2, 12, 2);
        this.bipedRightArm.a(-5.0f, 2.0f, 0.0f);
        bcu extension = new bcu((bbo)this, 44, 18);
        extension.a(-1.0f, -2.0f, -1.0f, 2, 6, 2);
        this.bipedRightArm.a(extension);
        this.bipedLeftArm = new bcu((bbo)this, 44, 18);
        this.bipedLeftArm.i = true;
        this.bipedLeftArm.a(-1.0f, 4.0f, -1.0f, 2, 12, 2);
        this.bipedLeftArm.a(5.0f, 2.0f, 0.0f);
        bcu extension2 = new bcu((bbo)this, 44, 18);
        extension2.i = true;
        extension2.a(-1.0f, -2.0f, -1.0f, 2, 6, 2);
        this.bipedLeftArm.a(extension2);
        this.bipedRightLeg = new bcu((bbo)this, 4, 20);
        this.bipedRightLeg.a(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.bipedRightLeg.a(-2.0f, 12.0f, 0.0f);
        this.bipedLeftLeg = new bcu((bbo)this, 4, 20);
        this.bipedLeftLeg.i = true;
        this.bipedLeftLeg.a(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.bipedLeftLeg.a(2.0f, 12.0f, 0.0f);
    }

    @Override
    public void a(nn entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.14f, (float)0.0f);
        super.a(entity, f, f1, f2, f3, f4, f5);
        GL11.glPopMatrix();
    }
}

