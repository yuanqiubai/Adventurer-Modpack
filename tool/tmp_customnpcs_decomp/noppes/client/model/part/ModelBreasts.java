/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.Model2DRenderer;
import noppes.client.model.util.ModelPartInterface;
import noppes.common.entity.EntityCustomNpc;

public class ModelBreasts
extends ModelPartInterface {
    private final Model2DRenderer breasts;
    private final bcu breasts2;
    private final bcu breasts3;

    public ModelBreasts(ModelMPM base) {
        super(base);
        this.breasts = new Model2DRenderer(base, 20.0f, 22.0f, 8, 3, 64.0f, 32.0f);
        this.breasts.a(-3.6f, 5.2f, -3.0f);
        this.breasts.setScale(0.17f, 0.19f);
        this.breasts.setThickness(1.0f);
        this.a(this.breasts);
        this.breasts2 = new bcu((bbo)base);
        this.a(this.breasts2);
        Model2DRenderer bottom = new Model2DRenderer(base, 20.0f, 22.0f, 8, 4, 64.0f, 32.0f);
        bottom.a(-3.6f, 5.0f, -3.1f);
        bottom.setScale(0.225f, 0.2f);
        bottom.setThickness(2.0f);
        bottom.f = -0.31415927f;
        this.breasts2.a((bcu)bottom);
        this.breasts3 = new bcu((bbo)base);
        this.a(this.breasts3);
        Model2DRenderer right = new Model2DRenderer(base, 20.0f, 22.0f, 3, 2, 64.0f, 32.0f);
        right.a(-3.8f, 5.3f, -3.6f);
        right.setScale(0.12f, 0.14f);
        right.setThickness(1.75f);
        this.breasts3.a((bcu)right);
        Model2DRenderer right2 = new Model2DRenderer(base, 20.0f, 22.0f, 3, 1, 64.0f, 32.0f);
        right2.a(-3.8f, 4.1f, -3.14f);
        right2.setScale(0.06f, 0.07f);
        right2.setThickness(1.75f);
        right2.f = 0.34906584f;
        this.breasts3.a((bcu)right2);
        Model2DRenderer right3 = new Model2DRenderer(base, 20.0f, 24.0f, 3, 1, 64.0f, 32.0f);
        right3.a(-3.8f, 5.3f, -3.6f);
        right3.setScale(0.06f, 0.07f);
        right3.setThickness(1.75f);
        right3.f = -0.34906584f;
        this.breasts3.a((bcu)right3);
        Model2DRenderer right4 = new Model2DRenderer(base, 23.0f, 22.0f, 1, 2, 64.0f, 32.0f);
        right4.a(-1.8f, 5.3f, -3.14f);
        right4.setScale(0.12f, 0.14f);
        right4.setThickness(1.75f);
        right4.g = 0.34906584f;
        this.breasts3.a((bcu)right4);
        Model2DRenderer left = new Model2DRenderer(base, 25.0f, 22.0f, 3, 2, 64.0f, 32.0f);
        left.a(0.8f, 5.3f, -3.6f);
        left.setScale(0.12f, 0.14f);
        left.setThickness(1.75f);
        this.breasts3.a((bcu)left);
        Model2DRenderer left2 = new Model2DRenderer(base, 25.0f, 22.0f, 3, 1, 64.0f, 32.0f);
        left2.a(0.8f, 4.1f, -3.18f);
        left2.setScale(0.06f, 0.07f);
        left2.setThickness(1.75f);
        left2.f = 0.34906584f;
        this.breasts3.a((bcu)left2);
        Model2DRenderer left3 = new Model2DRenderer(base, 25.0f, 24.0f, 3, 1, 64.0f, 32.0f);
        left3.a(0.8f, 5.3f, -3.6f);
        left3.setScale(0.06f, 0.07f);
        left3.setThickness(1.75f);
        left3.f = -0.34906584f;
        this.breasts3.a((bcu)left3);
        Model2DRenderer left4 = new Model2DRenderer(base, 24.0f, 22.0f, 1, 2, 64.0f, 32.0f);
        left4.a(0.8f, 5.3f, -3.6f);
        left4.setScale(0.12f, 0.14f);
        left4.setThickness(1.75f);
        left4.g = -0.34906584f;
        this.breasts3.a((bcu)left4);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
    }

    @Override
    public void initData(EntityCustomNpc data) {
        this.k = data.breasts == 0;
        this.breasts.k = data.breasts != 1;
        this.breasts2.k = data.breasts != 2;
        this.breasts3.k = data.breasts != 3;
    }
}

