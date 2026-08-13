/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bch
 *  nn
 *  of
 *  og
 */
package noppes.client.renderer;

import noppes.client.renderer.RenderNPCInterface;
import noppes.common.entity.model_entity.EntityNPCVillager;

public class RenderNpcVillager
extends RenderNPCInterface {
    public RenderNpcVillager() {
        super((bbo)new bch(0.0f), 0.5f);
    }

    public void renderVillager(EntityNPCVillager entityvillager, double d, double d1, double d2, float f, float f1) {
        super.a((og)entityvillager, d, d1, d2, f, f1);
    }

    protected void func_40290_a(EntityNPCVillager entityvillager, double d, double d1, double d2) {
    }

    protected void func_40291_a(EntityNPCVillager entityvillager, float f) {
        super.c((of)entityvillager, f);
    }

    protected void renderEquippedItems(og entityliving, float f) {
        this.func_40291_a((EntityNPCVillager)entityliving, f);
    }

    @Override
    public void a(og entityliving, double d, double d1, double d2, float f, float f1) {
        this.renderVillager((EntityNPCVillager)entityliving, d, d1, d2, f, f1);
    }

    public void a(nn entity, double d, double d1, double d2, float f, float f1) {
        this.renderVillager((EntityNPCVillager)entity, d, d1, d2, f, f1);
    }
}

