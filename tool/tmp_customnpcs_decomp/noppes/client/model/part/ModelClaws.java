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

public class ModelClaws
extends ModelPartInterface {
    private final Model2DRenderer model;
    private boolean isRight = false;

    public ModelClaws(ModelMPM base, boolean isRight) {
        super(base);
        this.isRight = isRight;
        this.model = new Model2DRenderer(base, 0.0f, 16.0f, 4, 4, 64.0f, 32.0f);
        if (isRight) {
            this.model.a(-2.0f, 14.0f, -2.0f);
        } else {
            this.model.a(3.0f, 14.0f, -2.0f);
        }
        this.model.g = -1.5707964f;
        this.model.setScale(0.25f);
        this.a(this.model);
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("claws");
        if (!(config == null || this.isRight && config.type == 1 || !this.isRight && config.type == 2)) {
            this.color = config.color;
            this.k = false;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        } else {
            this.k = true;
        }
    }
}

