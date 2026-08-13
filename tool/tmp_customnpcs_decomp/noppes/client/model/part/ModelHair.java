/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bcu
 *  bjo
 *  nn
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.Model2DRenderer;
import noppes.client.model.util.ModelPartInterface;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;

public class ModelHair
extends ModelPartInterface {
    private final Model2DRenderer model;

    public ModelHair(ModelMPM base) {
        super(base);
        this.model = new Model2DRenderer(base, 56.0f, 20.0f, 8, 12, 64.0f, 32.0f);
        this.model.a(-4.0f, 12.0f, 3.0f);
        this.model.setScale(0.75f);
        this.a(this.model);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        bcu parent = this.base.bipedHead;
        if (parent.f < 0.0f) {
            this.f = -parent.f * 1.2f;
            if (parent.f > -1.0f) {
                this.d = -parent.f * 1.5f;
                this.e = -parent.f * 1.5f;
            }
        } else {
            this.f = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
        }
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("hair");
        if (config == null) {
            this.k = true;
        } else {
            this.color = config.color;
            this.k = false;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        }
    }
}

