/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbs
 *  bcf
 *  bcp
 *  bcu
 *  bfq
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package noppes.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.client.renderer.EnumPlanePosition;

public class ModelPlane
extends bcp {
    private final bcf[] h = new bcf[8];
    private bbs quad;

    public ModelPlane(bcu par1ModelRenderer, int textureOffsetX, int textureOffsetY, float par4, float par5, float par6, int par7, int par8, int par9, float par10, EnumPlanePosition position) {
        super(par1ModelRenderer, textureOffsetX, textureOffsetY, par4, par5, par6, par7, par8, par9, par10);
        float var11 = par4 + (float)par7;
        float var12 = par5 + (float)par8;
        float var13 = par6 + (float)par9;
        par4 -= par10;
        par5 -= par10;
        par6 -= par10;
        var11 += par10;
        var12 += par10;
        var13 += par10;
        if (par1ModelRenderer.i) {
            float var23 = var11;
            var11 = par4;
            par4 = var23;
        }
        bcf var231 = new bcf(par4, par5, par6, 0.0f, 0.0f);
        bcf var15 = new bcf(var11, par5, par6, 0.0f, 8.0f);
        bcf var16 = new bcf(var11, var12, par6, 8.0f, 8.0f);
        bcf var17 = new bcf(par4, var12, par6, 8.0f, 0.0f);
        bcf var18 = new bcf(par4, par5, var13, 0.0f, 0.0f);
        bcf var19 = new bcf(var11, par5, var13, 0.0f, 8.0f);
        bcf var20 = new bcf(var11, var12, var13, 8.0f, 8.0f);
        bcf var21 = new bcf(par4, var12, var13, 8.0f, 0.0f);
        this.h[0] = var231;
        this.h[1] = var15;
        this.h[2] = var16;
        this.h[3] = var17;
        this.h[4] = var18;
        this.h[5] = var19;
        this.h[6] = var20;
        this.h[7] = var21;
        if (position == EnumPlanePosition.LEFT) {
            this.quad = new bbs(new bcf[]{var19, var15, var16, var20}, textureOffsetX, textureOffsetY, textureOffsetX + par9, textureOffsetY + par8, par1ModelRenderer.a, par1ModelRenderer.b);
        }
        if (position == EnumPlanePosition.TOP) {
            this.quad = new bbs(new bcf[]{var19, var18, var231, var15}, textureOffsetX, textureOffsetY, textureOffsetX + par7, textureOffsetY + par9, par1ModelRenderer.a, par1ModelRenderer.b);
        }
        if (position == EnumPlanePosition.BACK) {
            this.quad = new bbs(new bcf[]{var15, var231, var17, var16}, textureOffsetX, textureOffsetY, textureOffsetX + par7, textureOffsetY + par8, par1ModelRenderer.a, par1ModelRenderer.b);
        }
        if (par1ModelRenderer.i) {
            this.quad.a();
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void a(bfq par1Tessellator, float num) {
        this.quad.a(par1Tessellator, num);
    }
}

