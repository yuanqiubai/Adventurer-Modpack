/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bbo
 *  bcu
 *  bim
 *  bjo
 *  nn
 *  of
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model.util;

import noppes.client.model.ModelMPM;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;
import org.lwjgl.opengl.GL11;

public abstract class ModelPartInterface
extends bcu {
    public float scale = 1.0f;
    public int color = 0xFFFFFF;
    public ModelMPM base;
    protected bjo location;
    private EntityCustomNpc entity;

    public ModelPartInterface(ModelMPM par1ModelBase) {
        super((bbo)par1ModelBase);
        this.base = par1ModelBase;
        this.b(0, 0);
    }

    public void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
    }

    public void setLivingAnimations(ModelPartData data, of entityliving, float f, float f1, float f2) {
    }

    public void setData(EntityCustomNpc entity) {
        this.entity = entity;
        this.initData(entity);
    }

    public void a(float par1) {
        boolean bo1;
        if (!this.base.isArmor) {
            bim bo;
            if (this.location != null) {
                bo = atv.w().J();
                bo.a(this.location);
                this.base.currentlyPlayerTexture = false;
            } else if (!this.base.currentlyPlayerTexture) {
                bo = atv.w().J();
                bo.a((bjo)this.entity.textureLocation);
                this.base.currentlyPlayerTexture = true;
            }
        }
        boolean bl = bo1 = this.entity.ay <= 0 && this.entity.aB <= 0 && !this.base.isArmor;
        if (bo1) {
            float red = (float)(this.color >> 16 & 0xFF) / 255.0f;
            float green = (float)(this.color >> 8 & 0xFF) / 255.0f;
            float blue = (float)(this.color & 0xFF) / 255.0f;
            GL11.glColor4f((float)red, (float)green, (float)blue, (float)1.0f);
        }
        super.a(par1);
        if (bo1) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public abstract void initData(EntityCustomNpc var1);
}

