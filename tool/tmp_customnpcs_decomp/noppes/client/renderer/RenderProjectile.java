/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aqz
 *  atv
 *  bfj
 *  bfq
 *  bfr
 *  bgm
 *  bik
 *  bim
 *  bjo
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ls
 *  ms
 *  nn
 *  of
 *  org.lwjgl.opengl.GL11
 *  yc
 *  ye
 *  yp
 *  zp
 */
package noppes.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.client.ClientProxy;
import noppes.common.entity.EntityProjectile;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderProjectile
extends bgm {
    private static final bjo arrowTextures = new bjo("textures/entity/arrow.png");
    private static final bjo RES_ITEM_GLINT = new bjo("textures/misc/enchanted_item_glint.png");
    public boolean renderWithColor = true;
    private final bfr itemRenderBlocks = new bfr();

    public void doRenderProjectile(EntityProjectile par1EntityProjectile, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        GL11.glEnable((int)32826);
        float f = (float)par1EntityProjectile.v().c(23) / 10.0f;
        ye item = par1EntityProjectile.getItemDisplay();
        GL11.glScalef((float)f, (float)f, (float)f);
        bfq tessellator = bfq.a;
        if (par1EntityProjectile.isArrow()) {
            this.b(par1EntityProjectile);
            GL11.glRotatef((float)(par1EntityProjectile.C + (par1EntityProjectile.A - par1EntityProjectile.C) * par9 - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(par1EntityProjectile.D + (par1EntityProjectile.B - par1EntityProjectile.D) * par9), (float)0.0f, (float)0.0f, (float)1.0f);
            int icon = 0;
            float i = 0.0f;
            float f2 = 0.5f;
            float f3 = (float)(icon * 10) / 32.0f;
            float f4 = (float)(5 + icon * 10) / 32.0f;
            float f6 = 0.0f;
            float l = 0.15625f;
            float f9 = (float)(5 + icon * 10) / 32.0f;
            float f91 = (float)(10 + icon * 10) / 32.0f;
            float f10 = 0.05625f;
            GL11.glEnable((int)32826);
            float f11 = (float)par1EntityProjectile.arrowShake - par9;
            if (f11 > 0.0f) {
                float i1 = -ls.a((float)(f11 * 3.0f)) * f11;
                GL11.glRotatef((float)i1, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glRotatef((float)45.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)f10, (float)f10, (float)f10);
            GL11.glTranslatef((float)-4.0f, (float)0.0f, (float)0.0f);
            GL11.glNormal3f((float)f10, (float)0.0f, (float)0.0f);
            tessellator.b();
            tessellator.a(-7.0, -2.0, -2.0, (double)f6, (double)f9);
            tessellator.a(-7.0, -2.0, 2.0, (double)l, (double)f9);
            tessellator.a(-7.0, 2.0, 2.0, (double)l, (double)f91);
            tessellator.a(-7.0, 2.0, -2.0, (double)f6, (double)f91);
            tessellator.a();
            GL11.glNormal3f((float)(-f10), (float)0.0f, (float)0.0f);
            tessellator.b();
            tessellator.a(-7.0, 2.0, -2.0, (double)f6, (double)f9);
            tessellator.a(-7.0, 2.0, 2.0, (double)l, (double)f9);
            tessellator.a(-7.0, -2.0, 2.0, (double)l, (double)f91);
            tessellator.a(-7.0, -2.0, -2.0, (double)f6, (double)f91);
            tessellator.a();
            for (int var33 = 0; var33 < 4; ++var33) {
                GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glNormal3f((float)0.0f, (float)0.0f, (float)f10);
                tessellator.b();
                tessellator.a(-8.0, -2.0, 0.0, (double)i, (double)f3);
                tessellator.a(8.0, -2.0, 0.0, (double)f2, (double)f3);
                tessellator.a(8.0, 2.0, 0.0, (double)f2, (double)f4);
                tessellator.a(-8.0, 2.0, 0.0, (double)i, (double)f4);
                tessellator.a();
            }
        } else if (par1EntityProjectile.is3D()) {
            GL11.glRotatef((float)(par1EntityProjectile.C + (par1EntityProjectile.A - par1EntityProjectile.C) * par9 - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(par1EntityProjectile.D + (par1EntityProjectile.B - par1EntityProjectile.D) * par9 - 180.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            aqz var25 = null;
            if (item.d < aqz.s.length) {
                var25 = aqz.s[item.d];
            }
            if (item.d() == 0 && var25 != null && bfr.a((int)aqz.s[item.d].d())) {
                this.a(bik.b);
                float i = 0.25f;
                int var27 = var25.d();
                if (var27 == 1 || var27 == 19 || var27 == 12 || var27 == 2) {
                    i = 0.5f;
                }
                float f3 = 1.0f;
                this.c.a(var25, item.k(), f3);
            } else if (item.b().b()) {
                for (int var31 = 0; var31 < item.b().getRenderPasses(item.k()); ++var31) {
                    item.b().getIcon(item, var31);
                    float f4 = 1.0f;
                    if (this.renderWithColor) {
                        int var32 = yc.g[item.d].a(item, var31);
                        float f2 = (float)(var32 >> 16 & 0xFF) / 255.0f;
                        float i = (float)(var32 >> 8 & 0xFF) / 255.0f;
                        float f3 = (float)(var32 & 0xFF) / 255.0f;
                        GL11.glColor4f((float)(f2 * f4), (float)(i * f4), (float)(f3 * f4), (float)1.0f);
                        this.b.f.a((of)atv.w().h, item, 0);
                        continue;
                    }
                    this.b.f.a((of)atv.w().h, item, 0);
                }
            } else {
                ms var30 = item.c();
                if (this.renderWithColor) {
                    int var29 = yc.g[item.d].a(item, 0);
                    float f4 = (float)(var29 >> 16 & 0xFF) / 255.0f;
                    float f9 = (float)(var29 >> 8 & 0xFF) / 255.0f;
                    float f2 = (float)(var29 & 0xFF) / 255.0f;
                    float i = 1.0f;
                    this.renderDroppedItem(item, var30, par9, f4 * i, f9 * i, f2 * i, f);
                } else {
                    this.renderDroppedItem(item, var30, par9, 1.0f, 1.0f, 1.0f, f);
                }
            }
        } else {
            float f4;
            float f3;
            int var28;
            ms var26 = item.c();
            this.a(bik.c);
            if (item.b().b()) {
                for (var28 = 0; var28 < item.b().getRenderPasses(item.k()); ++var28) {
                    int var27 = yc.g[item.d].a(item, var28);
                    f3 = (float)(var27 >> 16 & 0xFF) / 255.0f;
                    f4 = (float)(var27 >> 8 & 0xFF) / 255.0f;
                    float f6 = (float)(var27 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)f3, (float)f4, (float)f6, (float)1.0f);
                }
            }
            if (var26 == yp.e((String)"potion_splash")) {
                var28 = zp.a((int)item.k(), (boolean)false);
                float f2 = (float)(var28 >> 16 & 0xFF) / 255.0f;
                f3 = (float)(var28 >> 8 & 0xFF) / 255.0f;
                f4 = (float)(var28 & 0xFF) / 255.0f;
                GL11.glColor3f((float)f2, (float)f3, (float)f4);
                GL11.glPushMatrix();
                this.renderSprite(tessellator, yp.e((String)"potion_contents"));
                GL11.glPopMatrix();
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            }
            this.renderSprite(tessellator, var26);
        }
        if (par1EntityProjectile.is3D() && par1EntityProjectile.glows()) {
            GL11.glDisable((int)2896);
        }
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
    }

    private void renderSprite(bfq par1Tessellator, ms par2Icon) {
        float f = par2Icon.c();
        float f1 = par2Icon.d();
        float f2 = par2Icon.e();
        float f3 = par2Icon.f();
        float f4 = 1.0f;
        float f5 = 0.5f;
        float f6 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.b.j), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-this.b.k), (float)1.0f, (float)0.0f, (float)0.0f);
        par1Tessellator.b();
        par1Tessellator.b(0.0f, 1.0f, 0.0f);
        par1Tessellator.a((double)(0.0f - f5), (double)(0.0f - f6), 0.0, (double)f, (double)f3);
        par1Tessellator.a((double)(f4 - f5), (double)(0.0f - f6), 0.0, (double)f1, (double)f3);
        par1Tessellator.a((double)(f4 - f5), (double)(f4 - f6), 0.0, (double)f1, (double)f2);
        par1Tessellator.a((double)(0.0f - f5), (double)(f4 - f6), 0.0, (double)f, (double)f2);
        par1Tessellator.a();
    }

    private void renderDroppedItem(ye item, ms par2Icon, float par4, float par5, float par6, float par7, float par8) {
        bfq tessellator = bfq.a;
        if (par2Icon == null) {
            bim f4 = atv.w().J();
            bjo f5 = f4.a(item.d());
            par2Icon = ((bik)f4.b(f5)).a("missingno");
        }
        float f41 = par2Icon.c();
        float f51 = par2Icon.d();
        float f6 = par2Icon.e();
        float f7 = par2Icon.f();
        float f8 = 1.0f;
        float f9 = 0.5f;
        float f10 = 0.25f;
        float f12 = 0.0625f;
        if (item.d() == 0) {
            this.a(bik.b);
        } else {
            this.a(bik.c);
        }
        GL11.glColor4f((float)par5, (float)par6, (float)par7, (float)1.0f);
        bfj.a((bfq)tessellator, (float)f51, (float)f6, (float)f41, (float)f7, (int)par2Icon.a(), (int)par2Icon.b(), (float)f12);
        if (item != null && item.hasEffect(0)) {
            GL11.glDepthFunc((int)514);
            GL11.glDisable((int)2896);
            ClientProxy.bindTexture(RES_ITEM_GLINT);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)768, (int)1);
            float f13 = 0.76f;
            GL11.glColor4f((float)(0.5f * f13), (float)(0.25f * f13), (float)(0.8f * f13), (float)1.0f);
            GL11.glMatrixMode((int)5890);
            GL11.glPushMatrix();
            GL11.glScalef((float)par8, (float)par8, (float)par8);
            float f15 = (float)(atv.F() % 3000L) / 3000.0f * 8.0f;
            GL11.glTranslatef((float)f15, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            bfj.a((bfq)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)255, (int)255, (float)f12);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef((float)par8, (float)par8, (float)par8);
            f15 = (float)(atv.F() % 4873L) / 4873.0f * 8.0f;
            GL11.glTranslatef((float)(-f15), (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            bfj.a((bfq)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)255, (int)255, (float)f12);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5888);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glDepthFunc((int)515);
        }
    }

    public void a(nn par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRenderProjectile((EntityProjectile)par1Entity, par2, par4, par6, par8, par9);
    }

    protected bjo getArrowTextures(EntityProjectile par1EntityProjectile) {
        return par1EntityProjectile.isArrow() ? arrowTextures : this.b.e.a(par1EntityProjectile.getItemDisplay().d());
    }

    protected bjo a(nn par1Entity) {
        return this.getArrowTextures((EntityProjectile)par1Entity);
    }
}

