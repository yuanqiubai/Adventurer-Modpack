/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bcu
 *  og
 */
package noppes.client.renderer;

import noppes.client.model.ModelNPCFemale;
import noppes.client.renderer.RenderNPCHumanMale;
import noppes.common.entity.EntityNPCInterface;

public class RenderNPCHumanFemale
extends RenderNPCHumanMale {
    public RenderNPCHumanFemale(ModelNPCFemale mainmodel, ModelNPCFemale armorChest, ModelNPCFemale armor) {
        super(mainmodel, armorChest, armor);
    }

    protected int setArmorModel(EntityNPCInterface entityplayer, int i, float f) {
        ((ModelNPCFemale)this.i).Breasts.j = entityplayer.inventory.armorItemInSlot(1) == null;
        for (Object child : ((ModelNPCFemale)this.i).Breasts.m) {
            ((bcu)child).j = entityplayer.inventory.armorItemInSlot(1) == null;
        }
        return super.func_130006_a((og)entityplayer, i, f);
    }

    protected int shouldRenderPass(og entityliving, int i, float f) {
        return this.setArmorModel((EntityNPCInterface)entityliving, i, f);
    }
}

