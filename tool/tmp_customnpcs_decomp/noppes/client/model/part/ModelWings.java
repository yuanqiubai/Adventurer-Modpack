/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bjo
 *  ls
 *  nn
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.Model2DRenderer;
import noppes.client.model.util.ModelPartInterface;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;

public class ModelWings
extends ModelPartInterface {
    private final Model2DRenderer lWing;
    private final Model2DRenderer rWing;

    public ModelWings(ModelMPM base) {
        super(base);
        this.lWing = new Model2DRenderer(base, 56.0f, 16.0f, 8, 16, 64.0f, 32.0f);
        this.lWing.i = true;
        this.lWing.a(2.0f, 4.0f, 2.0f);
        this.lWing.setRotationOffset(-16.0f, -12.0f);
        this.setRotation(this.lWing, 0.7141593f, -0.5235988f, -0.5090659f);
        this.a(this.lWing);
        this.rWing = new Model2DRenderer(base, 56.0f, 16.0f, 8, 16, 64.0f, 32.0f);
        this.rWing.a(-2.0f, 4.0f, 2.0f);
        this.rWing.setRotationOffset(-16.0f, -12.0f);
        this.setRotation(this.rWing, 0.7141593f, 0.5235988f, 0.5090659f);
        this.a(this.rWing);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        this.rWing.f = 0.7141593f;
        this.rWing.h = 0.5090659f;
        this.lWing.f = 0.7141593f;
        this.lWing.h = -0.5090659f;
        float motion = Math.abs(ls.a((float)(par1 * 0.033f + (float)Math.PI)) * 0.4f) * par2;
        if (entity.F && (double)motion <= 0.01) {
            this.lWing.h += ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
            this.rWing.h -= ls.b((float)(par3 * 0.09f)) * 0.05f + 0.05f;
            this.lWing.f += ls.a((float)(par3 * 0.067f)) * 0.05f;
            this.rWing.f += ls.a((float)(par3 * 0.067f)) * 0.05f;
        } else {
            float speed = 0.55f + 0.5f * motion;
            float y = ls.a((float)(par3 * 0.67f));
            this.rWing.h += y * 0.5f * speed;
            this.rWing.f += y * 0.5f * speed;
            this.lWing.h -= y * 0.5f * speed;
            this.lWing.f += y * 0.5f * speed;
        }
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("wings");
        if (config == null) {
            this.k = true;
        } else {
            this.color = config.color;
            this.k = false;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        }
    }
}

