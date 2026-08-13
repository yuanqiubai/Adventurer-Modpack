/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.renderer;

import noppes.client.renderer.RenderNPCInterface;
import noppes.common.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class RenderNpcDragon
extends RenderNPCInterface {
    public RenderNpcDragon(bbo model, float f) {
        super(model, f);
    }

    @Override
    protected void renderPlayerScale(EntityNPCInterface npc, float f) {
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(0.120000005f * (float)npc.display.modelSize));
        super.renderPlayerScale(npc, f);
    }
}

