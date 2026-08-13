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

public class ModelMohawk
extends ModelPartInterface {
    private final Model2DRenderer model;

    public ModelMohawk(ModelMPM base) {
        super(base);
        this.model = new Model2DRenderer(base, 0, 0, 13, 13);
        this.model.a(-0.5f, 0.0f, 9.0f);
        this.setRotation(this.model, 0.0f, 1.5707964f, 0.0f);
        this.model.setScale(0.825f);
        this.a(this.model);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("mohawk");
        if (config == null) {
            this.k = true;
        } else {
            this.color = config.color;
            this.k = false;
            this.location = (bjo)config.getResource();
        }
    }
}

