/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nn
 *  og
 */
package noppes.client.renderer;

import noppes.client.model.ModelNpcCrystal;
import noppes.client.renderer.RenderNPCInterface;
import noppes.common.entity.model_entity.EntityNpcCrystal;

public class RenderNpcCrystal
extends RenderNPCInterface {
    ModelNpcCrystal mainmodel;

    public RenderNpcCrystal(ModelNpcCrystal model) {
        super(model, 0.0f);
        this.mainmodel = model;
    }

    public void func_41035_a(EntityNpcCrystal par1EntityEnderCrystal, double par2, double par4, double par6, float par8, float par9) {
        super.a((og)par1EntityEnderCrystal, par2, par4, par6, par8, par9);
    }

    public void a(nn par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.func_41035_a((EntityNpcCrystal)par1Entity, par2, par4, par6, par8, par9);
    }
}

