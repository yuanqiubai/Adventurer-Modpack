/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  bjo
 *  nn
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.util.ModelPartInterface;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;

public class ModelSnout
extends ModelPartInterface {
    private final bcu small;
    private final bcu medium;
    private final bcu large;
    private final bcu bunny;

    public ModelSnout(ModelMPM base) {
        super(base);
        this.small = new bcu((bbo)base, 24, 0);
        this.small.a(0.0f, 0.0f, 0.0f, 4, 3, 1);
        this.small.a(-2.0f, -3.0f, -5.0f);
        this.a(this.small);
        this.medium = new bcu((bbo)base, 24, 0);
        this.medium.a(0.0f, 0.0f, 0.0f, 4, 3, 2);
        this.medium.a(-2.0f, -3.0f, -6.0f);
        this.a(this.medium);
        this.large = new bcu((bbo)base, 24, 0);
        this.large.a(0.0f, 0.0f, 0.0f, 4, 3, 3);
        this.large.a(-2.0f, -3.0f, -7.0f);
        this.a(this.large);
        this.bunny = new bcu((bbo)base, 24, 0);
        this.bunny.a(1.0f, 1.0f, 0.0f, 4, 2, 1);
        this.bunny.a(-3.0f, -4.0f, -5.0f);
        this.a(this.bunny);
        bcu tooth = new bcu((bbo)base, 24, 3);
        tooth.a(2.0f, 3.0f, 0.0f, 2, 1, 1);
        tooth.a(0.0f, 0.0f, 0.0f);
        this.bunny.a(tooth);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
    }

    @Override
    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("snout");
        if (config == null) {
            this.k = true;
        } else {
            this.color = config.color;
            this.k = false;
            this.small.k = config.type != 0;
            this.medium.k = config.type != 1;
            this.large.k = config.type != 2;
            this.bunny.k = config.type != 3;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        }
    }
}

