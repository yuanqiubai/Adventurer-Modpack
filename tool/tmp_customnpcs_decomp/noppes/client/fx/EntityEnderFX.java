/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bej
 *  bfq
 *  bgl
 *  bjo
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.fx;

import noppes.client.ClientProxy;
import noppes.client.renderer.RenderNPCInterface;
import noppes.common.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class EntityEnderFX
extends bej {
    private static final bjo particleTextures = new bjo("textures/particle/particles.png");
    private final float a;
    private final int particleNumber;
    private final RenderNPCInterface npcRenderer;
    private final EntityNPCInterface npc;

    public EntityEnderFX(EntityNPCInterface npc, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(npc.q, par2, par4, par6, par8, par10, par12);
        this.npcRenderer = (RenderNPCInterface)bgl.a.a((nn)npc);
        this.npc = npc;
        this.particleNumber = npc.q.s.nextInt(2);
        this.a = this.h = this.ab.nextFloat() * 0.2f + 0.5f;
        this.av = 1.0f;
        this.au = 1.0f;
        this.j = 1.0f;
    }

    public void a(bfq par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        bfq tessellator = bfq.a;
        tessellator.a();
        float scale = ((float)this.f + par2) / (float)this.g;
        scale = 1.0f - scale;
        scale *= scale;
        scale = 1.0f - scale;
        this.h = this.a * scale;
        atv mc = atv.w();
        ClientProxy.bindTexture(this.npcRenderer.a((nn)this.npc));
        float f = 0.875f;
        float f1 = f + 0.125f;
        float f2 = 0.75f - (float)this.particleNumber * 0.25f;
        float f3 = f2 + 0.25f;
        float f4 = 0.1f * this.h;
        float f5 = (float)(this.r + (this.u - this.r) * (double)par2 - ay);
        float f6 = (float)(this.s + (this.v - this.s) * (double)par2 - az);
        float f7 = (float)(this.t + (this.w - this.t) * (double)par2 - aA);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        tessellator.b();
        tessellator.c(this.c(par2));
        par1Tessellator.a(1.0f, 1.0f, 1.0f);
        par1Tessellator.a((double)(f5 - par3 * f4 - par6 * f4), (double)(f6 - par4 * f4), (double)(f7 - par5 * f4 - par7 * f4), (double)f1, (double)f3);
        par1Tessellator.a((double)(f5 - par3 * f4 + par6 * f4), (double)(f6 + par4 * f4), (double)(f7 - par5 * f4 + par7 * f4), (double)f1, (double)f2);
        par1Tessellator.a((double)(f5 + par3 * f4 + par6 * f4), (double)(f6 + par4 * f4), (double)(f7 + par5 * f4 + par7 * f4), (double)f, (double)f2);
        par1Tessellator.a((double)(f5 + par3 * f4 - par6 * f4), (double)(f6 - par4 * f4), (double)(f7 + par5 * f4 - par7 * f4), (double)f, (double)f3);
        tessellator.a();
        ClientProxy.bindTexture(particleTextures);
        tessellator.b();
    }

    public int b() {
        return 0;
    }
}

