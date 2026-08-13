/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bjo
 *  nn
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.Model2DRenderer;
import noppes.client.model.util.ModelPartInterface;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;

public class ModelBeard
extends ModelPartInterface {
    private final Model2DRenderer model;

    public ModelBeard(ModelMPM base) {
        super(base);
        this.model = new Model2DRenderer(base, 56.0f, 20.0f, 8, 12, 64.0f, 32.0f);
        this.model.a(-3.99f, 11.9f, -4.0f);
        this.model.setScale(0.74f);
        this.a(this.model);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        this.f = this.base.bipedHead.f > 0.0f ? -this.base.bipedHead.f : 0.0f;
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("beard");
        if (config == null) {
            this.k = true;
        } else {
            this.color = config.color;
            this.k = false;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        }
    }
}

