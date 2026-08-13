/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aqz
 *  atv
 *  bcu
 *  bfr
 *  bgl
 *  bgu
 *  bjn
 *  bjo
 *  nn
 *  of
 *  og
 *  org.lwjgl.opengl.GL11
 *  wh
 *  wj
 *  yc
 *  ye
 */
package noppes.client.renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import noppes.client.model.ModelPony;
import noppes.client.model.ModelPonyArmor;
import noppes.client.renderer.RenderNPCInterface;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.model_entity.EntityNPCPony;
import org.lwjgl.opengl.GL11;

public class RenderNPCPony
extends RenderNPCInterface {
    private final ModelPony modelBipedMain;
    private final ModelPonyArmor modelArmorChestplate;
    private final ModelPonyArmor modelArmor;

    public RenderNPCPony() {
        super(new ModelPony(0.0f), 0.5f);
        this.modelBipedMain = (ModelPony)this.i;
        this.modelArmorChestplate = new ModelPonyArmor(1.0f);
        this.modelArmor = new ModelPonyArmor(0.5f);
    }

    protected int setArmorModel(EntityNPCInterface entityplayer, int i, float f) {
        yc item;
        ye itemstack = entityplayer.inventory.armorItemInSlot(i);
        if (itemstack != null && (item = itemstack.b()) instanceof wh) {
            wh itemarmor = (wh)item;
            this.a(bgu.getArmorResource((nn)entityplayer, (ye)itemstack, (int)i, null));
            ModelPonyArmor modelbiped = i != 2 ? this.modelArmorChestplate : this.modelArmor;
            modelbiped.head.j = i == 0;
            modelbiped.Body.j = i == 1;
            modelbiped.BodyBack.j = i == 1;
            modelbiped.rightarm.j = i == 3;
            modelbiped.LeftArm.j = i == 3;
            modelbiped.RightLeg.j = i == 3;
            modelbiped.LeftLeg.j = i == 3;
            modelbiped.rightarm2.j = i == 2;
            modelbiped.LeftArm2.j = i == 2;
            modelbiped.RightLeg2.j = i == 2;
            modelbiped.LeftLeg2.j = i == 2;
            this.a(modelbiped);
            float var8 = 1.0f;
            if (itemarmor.d() == wj.a) {
                int var9 = itemarmor.b(itemstack);
                float var10 = (float)(var9 >> 16 & 0xFF) / 255.0f;
                float var11 = (float)(var9 >> 8 & 0xFF) / 255.0f;
                float var12 = (float)(var9 & 0xFF) / 255.0f;
                GL11.glColor3f((float)(var8 * var10), (float)(var8 * var11), (float)(var8 * var12));
                if (itemstack.y()) {
                    return 31;
                }
                return 16;
            }
            GL11.glColor3f((float)var8, (float)var8, (float)var8);
            return !itemstack.y() ? 1 : 15;
        }
        return -1;
    }

    @Override
    public bjo a(nn entity) {
        EntityNPCPony pony = (EntityNPCPony)entity;
        boolean check = pony.textureLocation == null;
        bjo loc = super.a((nn)pony);
        if (check) {
            try {
                bjn e = atv.w().K().a(loc);
                BufferedImage bufferedimage = ImageIO.read(e.b());
                pony.isPegasus = false;
                pony.isUnicorn = false;
                Color color = new Color(bufferedimage.getRGB(0, 0), true);
                Color color1 = new Color(249, 177, 49, 255);
                Color color2 = new Color(136, 202, 240, 255);
                Color color3 = new Color(209, 159, 228, 255);
                Color color4 = new Color(254, 249, 252, 255);
                if (color.equals(color1)) {
                    // empty if block
                }
                if (color.equals(color2)) {
                    pony.isPegasus = true;
                }
                if (color.equals(color3)) {
                    pony.isUnicorn = true;
                }
                if (color.equals(color4)) {
                    pony.isPegasus = true;
                    pony.isUnicorn = true;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return loc;
    }

    public void renderPlayer(EntityNPCPony pony, double d, double d1, double d2, float f, float f1) {
        ye itemstack = pony.aZ();
        this.a(this.modelBipedMain);
        this.modelBipedMain.heldItemRight = itemstack == null ? 0 : 1;
        this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight;
        this.modelArmorChestplate.heldItemRight = this.modelBipedMain.heldItemRight;
        this.modelArmor.isSneak = this.modelBipedMain.isSneak = pony.ah();
        this.modelArmorChestplate.isSneak = this.modelBipedMain.isSneak;
        this.modelBipedMain.q = false;
        this.modelArmor.q = false;
        this.modelArmorChestplate.q = false;
        this.modelArmor.isSleeping = this.modelBipedMain.isSleeping = pony.bh();
        this.modelArmorChestplate.isSleeping = this.modelBipedMain.isSleeping;
        this.modelArmor.isUnicorn = this.modelBipedMain.isUnicorn = pony.isUnicorn;
        this.modelArmorChestplate.isUnicorn = this.modelBipedMain.isUnicorn;
        this.modelArmor.isPegasus = this.modelBipedMain.isPegasus = pony.isPegasus;
        this.modelArmorChestplate.isPegasus = this.modelBipedMain.isPegasus;
        double d3 = d1 - (double)pony.N;
        if (pony.ah()) {
            d3 -= 0.125;
        }
        super.a((og)pony, d, d3, d2, f, f1);
        this.modelBipedMain.aimedBow = false;
        this.modelArmor.aimedBow = false;
        this.modelArmorChestplate.aimedBow = false;
        this.modelBipedMain.q = false;
        this.modelArmor.q = false;
        this.modelArmorChestplate.q = false;
        this.modelBipedMain.isSneak = false;
        this.modelArmor.isSneak = false;
        this.modelArmorChestplate.isSneak = false;
        this.modelBipedMain.heldItemRight = 0;
        this.modelArmor.heldItemRight = 0;
        this.modelArmorChestplate.heldItemRight = 0;
    }

    protected void renderSpecials(EntityNPCPony entityplayer, float f) {
        super.c((of)entityplayer, f);
        if (!entityplayer.bh()) {
            if (entityplayer.isUnicorn) {
                this.renderDrop(this.b, entityplayer, this.modelBipedMain.unicornarm, 1.0f, 0.35f, 0.5375f, -0.45f);
            } else {
                this.renderDrop(this.b, entityplayer, this.modelBipedMain.RightArm, 1.0f, -0.0625f, 0.8375f, 0.0625f);
            }
        }
    }

    protected void renderDrop(bgl rendermanager, EntityNPCPony entityplayer, bcu modelrenderer, float f, float f1, float f2, float f3) {
        ye itemstack = entityplayer.aZ();
        if (itemstack != null) {
            float j;
            GL11.glPushMatrix();
            if (modelrenderer != null) {
                modelrenderer.c(f * 0.0625f);
            }
            GL11.glTranslatef((float)f1, (float)f2, (float)f3);
            if (itemstack.d < 256 && bfr.a((int)aqz.s[itemstack.d].d())) {
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                j = 0.375f * f;
                GL11.glScalef((float)j, (float)(-j), (float)j);
            } else if (itemstack.d == yc.m.cv) {
                GL11.glTranslatef((float)0.0f, (float)0.125f, (float)0.3125f);
                GL11.glRotatef((float)-20.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                j = 0.625f * f;
                GL11.glScalef((float)j, (float)(-j), (float)j);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (yc.g[itemstack.d].n_()) {
                if (yc.g[itemstack.d].o_()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
                j = 0.625f * f;
                GL11.glScalef((float)j, (float)(-j), (float)j);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                j = 0.375f * f;
                GL11.glScalef((float)j, (float)j, (float)j);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            if (itemstack.d == yc.bu.cv) {
                for (int var14 = 0; var14 <= 1; ++var14) {
                    int k = itemstack.b().a(itemstack, var14);
                    float f9 = (float)(k >> 16 & 0xFF) / 255.0f;
                    float f10 = (float)(k >> 8 & 0xFF) / 255.0f;
                    float f12 = (float)(k & 0xFF) / 255.0f;
                    GL11.glColor4f((float)f9, (float)f10, (float)f12, (float)1.0f);
                    this.b.f.a((of)entityplayer, itemstack, var14);
                }
            } else {
                rendermanager.f.a((of)entityplayer, itemstack, 0);
            }
            GL11.glPopMatrix();
        }
    }

    protected int a(of entityliving, int i, float f) {
        return this.setArmorModel((EntityNPCInterface)entityliving, i, f);
    }

    protected void c(of entityliving, float f) {
        this.renderSpecials((EntityNPCPony)entityliving, f);
    }

    @Override
    public void a(og entityliving, double d, double d1, double d2, float f, float f1) {
        this.renderPlayer((EntityNPCPony)entityliving, d, d1, d2, f, f1);
    }

    public void a(nn entity, double d, double d1, double d2, float f, float f1) {
        this.renderPlayer((EntityNPCPony)entity, d, d1, d2, f, f1);
    }
}

