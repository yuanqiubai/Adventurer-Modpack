/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  of
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.renderer;

import noppes.client.renderer.RenderNPCInterface;
import noppes.common.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class RenderNpcSlime
extends RenderNPCInterface {
    private final bbo scaleAmount;

    public RenderNpcSlime(bbo par1ModelBase, bbo par2ModelBase, float par3) {
        super(par1ModelBase, par3);
        this.scaleAmount = par2ModelBase;
    }

    protected int shouldSlimeRenderPass(EntityNPCInterface par1EntitySlime, int par2, float par3) {
        if (par1EntitySlime.aj()) {
            return 0;
        }
        if (par2 == 0) {
            this.a(this.scaleAmount);
            GL11.glEnable((int)2977);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            return 1;
        }
        if (par2 == 1) {
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        return -1;
    }

    protected int a(of par1EntityLiving, int par2, float par3) {
        return this.shouldSlimeRenderPass((EntityNPCInterface)par1EntityLiving, par2, par3);
    }
}

