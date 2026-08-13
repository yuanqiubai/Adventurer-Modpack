/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  bjo
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.Model2DRenderer;
import noppes.client.model.util.ModelPartInterface;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;

public class ModelEars
extends ModelPartInterface {
    private final bcu ears;
    private final bcu bunny;

    public ModelEars(ModelMPM par1ModelBase) {
        super(par1ModelBase);
        this.ears = new bcu((bbo)this.base);
        this.a(this.ears);
        Model2DRenderer right = new Model2DRenderer(this.base, 56.0f, 0.0f, 8, 4, 64.0f, 32.0f);
        right.a(-7.44f, -7.3f, -0.0f);
        right.setScale(0.234f, 0.234f);
        right.setThickness(1.16f);
        this.ears.a((bcu)right);
        Model2DRenderer left = new Model2DRenderer(this.base, 56.0f, 0.0f, 8, 4, 64.0f, 32.0f);
        left.a(7.44f, -7.3f, 1.15f);
        left.setScale(0.234f, 0.234f);
        this.setRotation(left, 0.0f, (float)Math.PI, 0.0f);
        left.setThickness(1.16f);
        this.ears.a((bcu)left);
        Model2DRenderer right2 = new Model2DRenderer(this.base, 56.0f, 4.0f, 8, 4, 64.0f, 32.0f);
        right2.a(-7.44f, -7.3f, 1.14f);
        right2.setScale(0.234f, 0.234f);
        right2.setThickness(1.16f);
        this.ears.a((bcu)right2);
        Model2DRenderer left2 = new Model2DRenderer(this.base, 56.0f, 4.0f, 8, 4, 64.0f, 32.0f);
        left2.a(7.44f, -7.3f, 2.31f);
        left2.setScale(0.234f, 0.234f);
        this.setRotation(left2, 0.0f, (float)Math.PI, 0.0f);
        left2.setThickness(1.16f);
        this.ears.a((bcu)left2);
        this.bunny = new bcu((bbo)this.base);
        this.a(this.bunny);
        bcu earleft = new bcu((bbo)this.base, 56, 0);
        earleft.i = true;
        earleft.a(-1.466667f, -4.0f, 0.0f, 3, 7, 1);
        earleft.a(2.533333f, -11.0f, 0.0f);
        this.bunny.a(earleft);
        bcu earright = new bcu((bbo)this.base, 56, 0);
        earright.a(-1.5f, -4.0f, 0.0f, 3, 7, 1);
        earright.a(-2.466667f, -11.0f, 0.0f);
        this.bunny.a(earright);
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("ears");
        if (config == null) {
            this.k = true;
        } else {
            this.k = false;
            this.color = config.color;
            this.ears.k = config.type != 0;
            this.bunny.k = config.type != 1;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        }
    }
}

