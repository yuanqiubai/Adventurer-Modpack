/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bjo
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.Model2DRenderer;
import noppes.client.model.util.ModelPartInterface;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;

public class ModelFin
extends ModelPartInterface {
    private final Model2DRenderer model;

    public ModelFin(ModelMPM base) {
        super(base);
        this.model = new Model2DRenderer(base, 56.0f, 20.0f, 8, 12, 64.0f, 32.0f);
        this.model.a(-0.5f, 12.0f, 10.0f);
        this.model.setScale(0.74f);
        this.model.g = 1.5707964f;
        this.a(this.model);
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("fin");
        if (config == null) {
            this.k = true;
        } else {
            this.color = config.color;
            this.k = false;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        }
    }
}

