/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bfj
 *  bfq
 *  bim
 *  bjo
 *  ms
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  of
 *  org.lwjgl.opengl.GL11
 *  ye
 */
package noppes.client.renderer;

import net.minecraftforge.client.IItemRenderer;
import noppes.common.items.ItemRenderInterface;
import org.lwjgl.opengl.GL11;

public class NpcItemRenderer
implements IItemRenderer {
    private static final bjo RES_ITEM_GLINT = new bjo("textures/misc/enchanted_item_glint.png");

    public boolean handleRenderType(ye item, IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ye item, IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ye par2ItemStack, Object ... data) {
        if (par2ItemStack.b() instanceof ItemRenderInterface) {
            GL11.glTranslatef((float)0.9375f, (float)0.0625f, (float)0.0f);
            GL11.glRotatef((float)-335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)-50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            ((ItemRenderInterface)par2ItemStack.b()).renderSpecial();
            this.renderItem3d((of)data[1], par2ItemStack);
        }
    }

    public void renderItem3d(of par1EntityLiving, ye par2ItemStack) {
        atv mc = atv.w();
        bim texturemanager = mc.J();
        int par3 = 0;
        texturemanager.a(texturemanager.a(par2ItemStack.d()));
        bfq tessellator = bfq.a;
        ms icon = par1EntityLiving.b(par2ItemStack, par3);
        if (icon != null) {
            float f = icon.c();
            float f1 = icon.d();
            float f2 = icon.e();
            float f3 = icon.f();
            float f4 = 0.0f;
            float f5 = 0.3f;
            GL11.glEnable((int)32826);
            GL11.glTranslatef((float)(-f4), (float)(-f5), (float)0.0f);
            float f6 = 1.5f;
            GL11.glScalef((float)f6, (float)f6, (float)f6);
            GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)-0.9375f, (float)-0.0625f, (float)0.0f);
            bfj.a((bfq)tessellator, (float)f1, (float)f2, (float)f, (float)f3, (int)icon.a(), (int)icon.b(), (float)0.0625f);
            if (par2ItemStack.hasEffect(par3)) {
                GL11.glDepthFunc((int)514);
                GL11.glDisable((int)2896);
                texturemanager.a(RES_ITEM_GLINT);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)768, (int)1);
                float f7 = 0.76f;
                GL11.glColor4f((float)(0.5f * f7), (float)(0.25f * f7), (float)(0.8f * f7), (float)1.0f);
                GL11.glMatrixMode((int)5890);
                GL11.glPushMatrix();
                float f8 = 0.125f;
                GL11.glScalef((float)f8, (float)f8, (float)f8);
                float f9 = (float)(atv.F() % 3000L) / 3000.0f * 8.0f;
                GL11.glTranslatef((float)f9, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                bfj.a((bfq)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)256, (int)256, (float)0.0625f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef((float)f8, (float)f8, (float)f8);
                f9 = (float)(atv.F() % 4873L) / 4873.0f * 8.0f;
                GL11.glTranslatef((float)(-f9), (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                bfj.a((bfq)tessellator, (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (int)256, (int)256, (float)0.0625f);
                GL11.glPopMatrix();
                GL11.glMatrixMode((int)5888);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2896);
                GL11.glDepthFunc((int)515);
            }
            GL11.glDisable((int)32826);
        }
    }
}

