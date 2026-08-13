/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bgl
 *  bgm
 *  nn
 *  og
 */
package noppes.client.renderer;

import noppes.client.model.ModelMPM;
import noppes.client.renderer.RenderNPCHumanMale;
import noppes.common.entity.EntityCustomNpc;

public class RenderCustomNpc
extends RenderNPCHumanMale {
    public RenderCustomNpc() {
        super(new ModelMPM(0.0f), new ModelMPM(0.5f), new ModelMPM(1.0f));
    }

    @Override
    public void a(og entityliving, double d, double d1, double d2, float f, float f1) {
        this.renderNPC((EntityCustomNpc)entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void a(nn entity, double d, double d1, double d2, float f, float f1) {
        this.renderNPC((EntityCustomNpc)entity, d, d1, d2, f, f1);
    }

    private void renderNPC(EntityCustomNpc npc, double d, double d1, double d2, float f, float f1) {
        if (npc.renderEntity == null) {
            super.a((og)npc, d, d1, d2, f, f1);
        } else {
            bgm bgm2 = bgl.a.a(npc.renderEntity);
        }
    }
}

