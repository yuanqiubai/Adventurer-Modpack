/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aqz
 *  bfr
 *  bgu
 *  bjo
 *  bma
 *  ls
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  net.minecraftforge.client.MinecraftForgeClient
 *  nn
 *  of
 *  og
 *  org.lwjgl.opengl.GL11
 *  wh
 *  yc
 *  ye
 *  zh
 */
package noppes.client.renderer;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import noppes.client.model.ModelNPCMale;
import noppes.client.renderer.RenderNPCInterface;
import noppes.common.constants.EnumAnimation;
import noppes.common.constants.EnumModelType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.items.ItemClaw;
import noppes.common.items.ItemShield;
import org.lwjgl.opengl.GL11;

public class RenderNPCHumanMale
extends RenderNPCInterface {
    protected ModelNPCMale modelArmorChestplate;
    protected ModelNPCMale modelArmor;
    private final ModelNPCMale modelBipedMain;

    public RenderNPCHumanMale(ModelNPCMale mainmodel, ModelNPCMale armorChest, ModelNPCMale armor) {
        super(mainmodel, 0.5f);
        this.modelBipedMain = mainmodel;
        this.modelArmorChestplate = armorChest;
        this.modelArmor = armor;
    }

    protected int func_130006_a(og par1EntityLiving, int par2, float par3) {
        yc item;
        ye itemstack = par1EntityLiving.o(par2);
        if (itemstack != null && (item = itemstack.b()) instanceof wh) {
            wh itemarmor = (wh)item;
            this.a(bgu.getArmorResource((nn)par1EntityLiving, (ye)itemstack, (int)par2, null));
            ModelNPCMale modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
            modelbiped.bipedHead.j = par2 == 0;
            modelbiped.bipedHeadwear.j = par2 == 0;
            modelbiped.bipedBody.j = par2 == 1 || par2 == 2;
            modelbiped.bipedRightArm.j = par2 == 1;
            modelbiped.bipedLeftArm.j = par2 == 1;
            modelbiped.bipedRightLeg.j = par2 == 2 || par2 == 3;
            modelbiped.bipedLeftLeg.j = par2 == 2 || par2 == 3;
            this.a(modelbiped);
            modelbiped.p = this.i.p;
            modelbiped.q = this.i.q;
            modelbiped.s = this.i.s;
            float f1 = 1.0f;
            int j = itemarmor.b(itemstack);
            if (j != -1) {
                float f2 = (float)(j >> 16 & 0xFF) / 255.0f;
                float f3 = (float)(j >> 8 & 0xFF) / 255.0f;
                float f4 = (float)(j & 0xFF) / 255.0f;
                GL11.glColor3f((float)(f1 * f2), (float)(f1 * f3), (float)(f1 * f4));
                if (itemstack.y()) {
                    return 31;
                }
                return 16;
            }
            GL11.glColor3f((float)f1, (float)f1, (float)f1);
            if (itemstack.y()) {
                return 15;
            }
            return 1;
        }
        return -1;
    }

    protected int a(of par1EntityLivingBase, int par2, float par3) {
        return this.func_130006_a((og)par1EntityLivingBase, par2, par3);
    }

    public void renderPlayer(EntityNPCInterface npc, double d, double d1, double d2, float f, float f1) {
        ye itemstack = npc.aZ();
        this.modelBipedMain.heldItemRight = itemstack == null ? 0 : 1;
        this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight;
        this.modelArmorChestplate.heldItemRight = this.modelBipedMain.heldItemRight;
        this.modelArmor.isSneak = this.modelBipedMain.isSneak = npc.ah();
        this.modelArmorChestplate.isSneak = this.modelBipedMain.isSneak;
        this.modelArmor.isSleeping = this.modelBipedMain.isSleeping = npc.bh();
        this.modelArmorChestplate.isSleeping = this.modelBipedMain.isSleeping;
        this.modelBipedMain.isDancing = npc.currentAnimation == EnumAnimation.DANCING;
        this.modelArmor.isDancing = this.modelBipedMain.isDancing;
        this.modelArmorChestplate.isDancing = this.modelBipedMain.isDancing;
        this.modelBipedMain.aimedBow = npc.currentAnimation == EnumAnimation.Aiming;
        this.modelArmor.aimedBow = this.modelBipedMain.aimedBow;
        this.modelArmorChestplate.aimedBow = this.modelBipedMain.aimedBow;
        this.modelArmor.q = this.modelBipedMain.q = npc.ag();
        this.modelArmorChestplate.q = this.modelBipedMain.q;
        double d3 = d1 - (double)npc.N;
        if (npc.ah()) {
            d3 -= 0.125;
        }
        super.a((og)npc, d, d3, d2, f, f1);
        this.modelBipedMain.aimedBow = false;
        this.modelArmor.aimedBow = false;
        this.modelArmorChestplate.aimedBow = false;
        this.modelBipedMain.isSneak = false;
        this.modelArmor.isSneak = false;
        this.modelArmorChestplate.isSneak = false;
        this.modelBipedMain.heldItemRight = 0;
        this.modelArmor.heldItemRight = 0;
        this.modelArmorChestplate.heldItemRight = 0;
    }

    protected void renderSpecials(EntityNPCInterface npc, float f) {
        boolean is3D;
        IItemRenderer var251;
        float var6;
        super.c((of)npc, f);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        int i = npc.c(f);
        int j = i % 65536;
        int k = i / 65536;
        bma.a((int)bma.b, (float)j, (float)k);
        if (!npc.display.cloakTexture.isEmpty()) {
            if (npc.textureCloakLocation == null) {
                npc.textureCloakLocation = new bjo(npc.display.cloakTexture);
            }
            this.a((bjo)npc.textureCloakLocation);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)0.125f);
            double itemstack = npc.cloakX + (npc.prev_cloakX - npc.cloakX) * (double)f - (npc.r + (npc.u - npc.r) * (double)f);
            double var7 = npc.cloakY + (npc.prev_cloakY - npc.cloakY) * (double)f - (npc.s + (npc.v - npc.s) * (double)f);
            double customRenderer = npc.cloakZ + (npc.prev_cloakZ - npc.cloakZ) * (double)f - (npc.t + (npc.w - npc.t) * (double)f);
            float var25 = npc.aO + (npc.aN - npc.aO) * f;
            double var24 = ls.a((float)(var25 * (float)Math.PI / 180.0f));
            double var9 = -ls.b((float)(var25 * (float)Math.PI / 180.0f));
            float f14 = (float)(itemstack * var24 + customRenderer * var9) * 100.0f;
            float f15 = (float)(itemstack * var9 - customRenderer * var24) * 100.0f;
            if (f14 < 0.0f) {
                f14 = 0.0f;
            }
            float var10000 = npc.C + (npc.A - npc.C) * f;
            float f13 = 5.0f;
            if (npc.ah()) {
                f13 += 25.0f;
            }
            GL11.glRotatef((float)(6.0f + f14 / 2.0f + f13), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(f15 / 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(-f15 / 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.modelBipedMain.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        ye var21 = npc.inventory.armorItemInSlot(0);
        if (var21 != null && var21.b().cv < 256) {
            boolean var241;
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.c(0.0625f);
            IItemRenderer itemstack2 = MinecraftForgeClient.getItemRenderer((ye)var21, (IItemRenderer.ItemRenderType)IItemRenderer.ItemRenderType.EQUIPPED);
            boolean bl = var241 = itemstack2 != null && itemstack2.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, var21, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (var21.b() instanceof zh) {
                if (var241 || bfr.a((int)aqz.s[var21.d].d())) {
                    var6 = 0.625f;
                    GL11.glTranslatef((float)0.0f, (float)-0.25f, (float)0.0f);
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glScalef((float)var6, (float)(-var6), (float)(-var6));
                }
                this.b.f.a((of)npc, var21, 0);
            }
            GL11.glPopMatrix();
        }
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        ye var22 = npc.aZ();
        if (var22 != null) {
            float var23;
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.c(0.0625f);
            var6 = npc.display.modelType == EnumModelType.EnderMan ? 1.0f : 0.0f;
            GL11.glTranslatef((float)-0.0625f, (float)(0.4375f + var6), (float)0.0625f);
            var251 = MinecraftForgeClient.getItemRenderer((ye)var22, (IItemRenderer.ItemRenderType)IItemRenderer.ItemRenderType.EQUIPPED);
            boolean bl = is3D = var251 != null && var251.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, var22, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (var22.b() instanceof zh && (is3D || bfr.a((int)aqz.s[var22.d].d()))) {
                var23 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)(-(var23 *= 0.75f)), (float)(-var23), (float)var23);
            } else if (var22.d == yc.m.cv) {
                var23 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)0.125f, (float)0.3125f);
                GL11.glRotatef((float)-20.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var23, (float)(-var23), (float)var23);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (yc.g[var22.d].n_()) {
                var23 = 0.625f;
                if (yc.g[var22.d].o_()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
                GL11.glScalef((float)var23, (float)(-var23), (float)var23);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                var23 = 0.375f;
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                GL11.glScalef((float)var23, (float)var23, (float)var23);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            this.b.f.a((of)npc, var22, 0);
            if (var22.b().b()) {
                for (int var261 = 1; var261 < var22.b().getRenderPasses(var22.k()); ++var261) {
                    this.b.f.a((of)npc, var22, var261);
                }
            }
            GL11.glPopMatrix();
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var22 = npc.getOffHand();
        if (var22 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedLeftArm.c(0.0625f);
            float var23 = npc.display.modelType == EnumModelType.EnderMan ? 1.0f : 0.0f;
            GL11.glTranslatef((float)0.0625f, (float)(0.4375f + var23), (float)0.0625f);
            var251 = MinecraftForgeClient.getItemRenderer((ye)var22, (IItemRenderer.ItemRenderType)IItemRenderer.ItemRenderType.EQUIPPED);
            boolean bl = is3D = var251 != null && var251.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, var22, IItemRenderer.ItemRendererHelper.BLOCK_3D);
            if (var22.b() instanceof ItemShield || var22.b() instanceof ItemClaw) {
                GL11.glTranslatef((float)0.3f, (float)0.0f, (float)0.0f);
            }
            if (var22.b() instanceof zh && (is3D || bfr.a((int)aqz.s[var22.d].d()))) {
                var6 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)(var6 *= 0.75f), (float)(-var6), (float)var6);
            } else if (var22.d == yc.m.cv) {
                var6 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)0.125f, (float)0.3125f);
                GL11.glRotatef((float)-20.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var6, (float)(-var6), (float)var6);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (yc.g[var22.d].n_()) {
                var6 = 0.625f;
                if (yc.g[var22.d].o_()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
                GL11.glScalef((float)var6, (float)(-var6), (float)var6);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                var6 = 0.375f;
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                GL11.glScalef((float)var6, (float)var6, (float)var6);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            if (var22.b().b()) {
                for (int var261 = 0; var261 < var22.b().getRenderPasses(var22.k()); ++var261) {
                    int var27 = var22.b().a(var22, var261);
                    float var26 = (float)(var27 >> 16 & 0xFF) / 255.0f;
                    float var28 = (float)(var27 >> 8 & 0xFF) / 255.0f;
                    float var10 = (float)(var27 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)var26, (float)var28, (float)var10, (float)1.0f);
                    this.b.f.a((of)npc, var22, var261);
                }
            } else {
                this.b.f.a((of)npc, var22, 0);
            }
            GL11.glPopMatrix();
        }
    }

    protected void c(of entityliving, float f) {
        this.renderSpecials((EntityNPCInterface)entityliving, f);
    }

    @Override
    public void a(og entityliving, double d, double d1, double d2, float f, float f1) {
        this.renderPlayer((EntityNPCInterface)entityliving, d, d1, d2, f, f1);
    }

    public void a(nn entity, double d, double d1, double d2, float f, float f1) {
        this.renderPlayer((EntityNPCInterface)entity, d, d1, d2, f, f1);
    }
}

