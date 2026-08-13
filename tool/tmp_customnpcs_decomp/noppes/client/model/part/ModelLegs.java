/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bbo
 *  bcu
 *  bim
 *  bjo
 *  ls
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.part.legs.ModelDigitigradeLegs;
import noppes.client.model.part.legs.ModelMermaidLegs;
import noppes.client.model.part.legs.ModelNagaLegs;
import noppes.client.model.util.ModelScaleRenderer;
import noppes.common.constants.EnumAnimation;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;
import org.lwjgl.opengl.GL11;

public class ModelLegs
extends ModelScaleRenderer {
    private EntityCustomNpc entity;
    private final ModelScaleRenderer leg1;
    private final ModelScaleRenderer leg2;
    private bcu spider;
    private bcu horse;
    private ModelNagaLegs naga;
    private ModelDigitigradeLegs digitigrade;
    private ModelMermaidLegs mermaid;
    private bcu spiderLeg1;
    private bcu spiderLeg2;
    private bcu spiderLeg3;
    private bcu spiderLeg4;
    private bcu spiderLeg5;
    private bcu spiderLeg6;
    private bcu spiderLeg7;
    private bcu spiderLeg8;
    private bcu spiderBody;
    private bcu spiderNeck;
    private bcu backLeftLeg;
    private bcu backLeftShin;
    private bcu backLeftHoof;
    private bcu backRightLeg;
    private bcu backRightShin;
    private bcu backRightHoof;
    private bcu frontLeftLeg;
    private bcu frontLeftShin;
    private bcu frontLeftHoof;
    private bcu frontRightLeg;
    private bcu frontRightShin;
    private bcu frontRightHoof;
    private final ModelMPM base;

    public ModelLegs(ModelMPM base, ModelScaleRenderer leg1, ModelScaleRenderer leg2) {
        super(base);
        this.base = base;
        this.leg1 = leg1;
        this.leg2 = leg2;
        if (!base.isArmor) {
            this.spider = new bcu((bbo)base);
            this.a(this.spider);
            float var1 = 0.0f;
            int var2 = 15;
            this.spiderNeck = new bcu((bbo)base, 0, 0);
            this.spiderNeck.a(-3.0f, -3.0f, -3.0f, 6, 6, 6, var1);
            this.spiderNeck.a(0.0f, (float)var2, 2.0f);
            this.spider.a(this.spiderNeck);
            this.spiderBody = new bcu((bbo)base, 0, 12);
            this.spiderBody.a(-5.0f, -4.0f, -6.0f, 10, 8, 12, var1);
            this.spiderBody.a(0.0f, (float)var2, 11.0f);
            this.spider.a(this.spiderBody);
            this.spiderLeg1 = new bcu((bbo)base, 18, 0);
            this.spiderLeg1.a(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg1.a(-4.0f, (float)var2, 4.0f);
            this.spider.a(this.spiderLeg1);
            this.spiderLeg2 = new bcu((bbo)base, 18, 0);
            this.spiderLeg2.a(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg2.a(4.0f, (float)var2, 4.0f);
            this.spider.a(this.spiderLeg2);
            this.spiderLeg3 = new bcu((bbo)base, 18, 0);
            this.spiderLeg3.a(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg3.a(-4.0f, (float)var2, 3.0f);
            this.spider.a(this.spiderLeg3);
            this.spiderLeg4 = new bcu((bbo)base, 18, 0);
            this.spiderLeg4.a(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg4.a(4.0f, (float)var2, 3.0f);
            this.spider.a(this.spiderLeg4);
            this.spiderLeg5 = new bcu((bbo)base, 18, 0);
            this.spiderLeg5.a(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg5.a(-4.0f, (float)var2, 2.0f);
            this.spider.a(this.spiderLeg5);
            this.spiderLeg6 = new bcu((bbo)base, 18, 0);
            this.spiderLeg6.a(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg6.a(4.0f, (float)var2, 2.0f);
            this.spider.a(this.spiderLeg6);
            this.spiderLeg7 = new bcu((bbo)base, 18, 0);
            this.spiderLeg7.a(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg7.a(-4.0f, (float)var2, 1.0f);
            this.spider.a(this.spiderLeg7);
            this.spiderLeg8 = new bcu((bbo)base, 18, 0);
            this.spiderLeg8.a(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
            this.spiderLeg8.a(4.0f, (float)var2, 1.0f);
            this.spider.a(this.spiderLeg8);
            int zOffset = 10;
            float yOffset = 7.0f;
            this.horse = new bcu((bbo)base);
            this.a(this.horse);
            bcu body = new bcu((bbo)base, 0, 34);
            body.b(128, 128);
            body.a(-5.0f, -8.0f, -19.0f, 10, 10, 24);
            body.a(0.0f, 11.0f + yOffset, 9.0f + (float)zOffset);
            this.horse.a(body);
            this.backLeftLeg = new bcu((bbo)base, 78, 29);
            this.backLeftLeg.b(128, 128);
            this.backLeftLeg.a(-2.5f, -2.0f, -2.5f, 4, 9, 5);
            this.backLeftLeg.a(4.0f, 9.0f + yOffset, 11.0f + (float)zOffset);
            this.horse.a(this.backLeftLeg);
            this.backLeftShin = new bcu((bbo)base, 78, 43);
            this.backLeftShin.b(128, 128);
            this.backLeftShin.a(-2.0f, 0.0f, -1.5f, 3, 5, 3);
            this.backLeftShin.a(0.0f, 7.0f, 0.0f);
            this.backLeftLeg.a(this.backLeftShin);
            this.backLeftHoof = new bcu((bbo)base, 78, 51);
            this.backLeftHoof.b(128, 128);
            this.backLeftHoof.a(-2.5f, 5.1f, -2.0f, 4, 3, 4);
            this.backLeftHoof.a(0.0f, 7.0f, 0.0f);
            this.backLeftLeg.a(this.backLeftHoof);
            this.backRightLeg = new bcu((bbo)base, 96, 29);
            this.backRightLeg.b(128, 128);
            this.backRightLeg.a(-1.5f, -2.0f, -2.5f, 4, 9, 5);
            this.backRightLeg.a(-4.0f, 9.0f + yOffset, 11.0f + (float)zOffset);
            this.horse.a(this.backRightLeg);
            this.backRightShin = new bcu((bbo)base, 96, 43);
            this.backRightShin.b(128, 128);
            this.backRightShin.a(-1.0f, 0.0f, -1.5f, 3, 5, 3);
            this.backRightShin.a(0.0f, 7.0f, 0.0f);
            this.backRightLeg.a(this.backRightShin);
            this.backRightHoof = new bcu((bbo)base, 96, 51);
            this.backRightHoof.b(128, 128);
            this.backRightHoof.a(-1.5f, 5.1f, -2.0f, 4, 3, 4);
            this.backRightHoof.a(0.0f, 7.0f, 0.0f);
            this.backRightLeg.a(this.backRightHoof);
            this.frontLeftLeg = new bcu((bbo)base, 44, 29);
            this.frontLeftLeg.b(128, 128);
            this.frontLeftLeg.a(-1.9f, -1.0f, -2.1f, 3, 8, 4);
            this.frontLeftLeg.a(4.0f, 9.0f + yOffset, -8.0f + (float)zOffset);
            this.horse.a(this.frontLeftLeg);
            this.frontLeftShin = new bcu((bbo)base, 44, 41);
            this.frontLeftShin.b(128, 128);
            this.frontLeftShin.a(-1.9f, 0.0f, -1.6f, 3, 5, 3);
            this.frontLeftShin.a(0.0f, 7.0f, 0.0f);
            this.frontLeftLeg.a(this.frontLeftShin);
            this.frontLeftHoof = new bcu((bbo)base, 44, 51);
            this.frontLeftHoof.b(128, 128);
            this.frontLeftHoof.a(-2.4f, 5.1f, -2.1f, 4, 3, 4);
            this.frontLeftHoof.a(0.0f, 7.0f, 0.0f);
            this.frontLeftLeg.a(this.frontLeftHoof);
            this.frontRightLeg = new bcu((bbo)base, 60, 29);
            this.frontRightLeg.b(128, 128);
            this.frontRightLeg.a(-1.1f, -1.0f, -2.1f, 3, 8, 4);
            this.frontRightLeg.a(-4.0f, 9.0f + yOffset, -8.0f + (float)zOffset);
            this.horse.a(this.frontRightLeg);
            this.frontRightShin = new bcu((bbo)base, 60, 41);
            this.frontRightShin.b(128, 128);
            this.frontRightShin.a(-1.1f, 0.0f, -1.6f, 3, 5, 3);
            this.frontRightShin.a(0.0f, 7.0f, 0.0f);
            this.frontRightLeg.a(this.frontRightShin);
            this.frontRightHoof = new bcu((bbo)base, 60, 51);
            this.frontRightHoof.b(128, 128);
            this.frontRightHoof.a(-1.6f, 5.1f, -2.1f, 4, 3, 4);
            this.frontRightHoof.a(0.0f, 7.0f, 0.0f);
            this.frontRightLeg.a(this.frontRightHoof);
            this.naga = new ModelNagaLegs(base);
            this.a(this.naga);
            this.mermaid = new ModelMermaidLegs(base);
            this.a(this.mermaid);
            this.digitigrade = new ModelDigitigradeLegs(base);
            this.a(this.digitigrade);
        }
    }

    @Override
    public void setRotation(bcu model, float x, float y, float z) {
        model.f = x;
        model.g = y;
        model.h = z;
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        ModelPartData part = this.entity.legParts;
        this.e = 0.0f;
        this.d = 0.0f;
        if (!this.base.isArmor) {
            if (part.type == 2) {
                this.f = 0.0f;
                this.spiderBody.d = 15.0f;
                this.spiderBody.e = 11.0f;
                this.spiderNeck.f = 0.0f;
                float var8 = 0.7853982f;
                this.spiderLeg1.h = -var8;
                this.spiderLeg2.h = var8;
                this.spiderLeg3.h = -var8 * 0.74f;
                this.spiderLeg4.h = var8 * 0.74f;
                this.spiderLeg5.h = -var8 * 0.74f;
                this.spiderLeg6.h = var8 * 0.74f;
                this.spiderLeg7.h = -var8;
                this.spiderLeg8.h = var8;
                float var9 = -0.0f;
                float var10 = 0.3926991f;
                this.spiderLeg1.g = var10 * 2.0f + var9;
                this.spiderLeg2.g = -var10 * 2.0f - var9;
                this.spiderLeg3.g = var10 + var9;
                this.spiderLeg4.g = -var10 - var9;
                this.spiderLeg5.g = -var10 + var9;
                this.spiderLeg6.g = var10 - var9;
                this.spiderLeg7.g = -var10 * 2.0f + var9;
                this.spiderLeg8.g = var10 * 2.0f - var9;
                float var11 = -(ls.b((float)(par1 * 0.6662f * 2.0f + 0.0f)) * 0.4f) * par2;
                float var12 = -(ls.b((float)(par1 * 0.6662f * 2.0f + (float)Math.PI)) * 0.4f) * par2;
                float var13 = -(ls.b((float)(par1 * 0.6662f * 2.0f + 1.5707964f)) * 0.4f) * par2;
                float var14 = -(ls.b((float)(par1 * 0.6662f * 2.0f + 4.712389f)) * 0.4f) * par2;
                float var15 = Math.abs(ls.a((float)(par1 * 0.6662f + 0.0f)) * 0.4f) * par2;
                float var16 = Math.abs(ls.a((float)(par1 * 0.6662f + (float)Math.PI)) * 0.4f) * par2;
                float var17 = Math.abs(ls.a((float)(par1 * 0.6662f + 1.5707964f)) * 0.4f) * par2;
                float var18 = Math.abs(ls.a((float)(par1 * 0.6662f + 4.712389f)) * 0.4f) * par2;
                this.spiderLeg1.g += var11;
                this.spiderLeg2.g += -var11;
                this.spiderLeg3.g += var12;
                this.spiderLeg4.g += -var12;
                this.spiderLeg5.g += var13;
                this.spiderLeg6.g += -var13;
                this.spiderLeg7.g += var14;
                this.spiderLeg8.g += -var14;
                this.spiderLeg1.h += var15;
                this.spiderLeg2.h += -var15;
                this.spiderLeg3.h += var16;
                this.spiderLeg4.h += -var16;
                this.spiderLeg5.h += var17;
                this.spiderLeg6.h += -var17;
                this.spiderLeg7.h += var18;
                this.spiderLeg8.h += -var18;
                if (this.base.isSneak) {
                    this.e = 5.0f;
                    this.d = -1.0f;
                    this.spiderBody.d = 16.0f;
                    this.spiderBody.e = 10.0f;
                    this.spiderNeck.f = -0.3926991f;
                }
                if (this.base.isSleeping(entity) || this.entity.currentAnimation == EnumAnimation.CRAWLING) {
                    this.d = 12.0f * this.entity.legs.scaleY;
                    this.e = 15.0f * this.entity.legs.scaleY;
                    this.f = -1.5707964f;
                }
            } else if (part.type == 3) {
                this.frontLeftLeg.f = ls.b((float)(par1 * 0.6662f)) * 0.4f * par2;
                this.frontRightLeg.f = ls.b((float)(par1 * 0.6662f + (float)Math.PI)) * 0.4f * par2;
                this.backLeftLeg.f = ls.b((float)(par1 * 0.6662f + (float)Math.PI)) * 0.4f * par2;
                this.backRightLeg.f = ls.b((float)(par1 * 0.6662f)) * 0.4f * par2;
            } else if (part.type == 1) {
                this.naga.isRiding = this.base.q;
                this.naga.isSleeping = this.base.isSleeping(entity);
                this.naga.isCrawling = this.entity.currentAnimation == EnumAnimation.CRAWLING;
                this.naga.isSneaking = this.base.isSneak;
                this.naga.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            } else if (part.type == 4) {
                this.mermaid.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            } else if (part.type == 5) {
                this.digitigrade.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            }
        }
    }

    @Override
    public void a(float par1) {
        if (this.j && !this.k) {
            ModelPartData part = this.entity.legParts;
            if (part.type >= 0) {
                boolean bo2;
                GL11.glPushMatrix();
                if (part.type == 4) {
                    boolean bl = part.playerTexture = !this.entity.H();
                }
                if (!this.base.isArmor) {
                    if (!part.playerTexture) {
                        bjo bo = (bjo)part.getResource();
                        bim red = atv.w().J();
                        red.a(bo);
                        this.base.currentlyPlayerTexture = false;
                    } else if (!this.base.currentlyPlayerTexture) {
                        bim bo1 = atv.w().J();
                        bo1.a((bjo)this.entity.textureLocation);
                        this.base.currentlyPlayerTexture = true;
                    }
                }
                if (part.type == 0 || part.type == 4 && !this.entity.H()) {
                    this.leg1.setConfig(this.config, this.x, this.y, this.z);
                    this.leg1.a(par1);
                    this.leg2.setConfig(this.config, -this.x, this.y, this.z);
                    this.leg2.a(par1);
                }
                if (!this.base.isArmor) {
                    this.naga.k = part.type != 1;
                    this.spider.k = part.type != 2;
                    this.horse.k = part.type != 3;
                    this.mermaid.k = part.type != 4 || !this.entity.H();
                    boolean bl = this.digitigrade.k = part.type != 5;
                    if (!this.horse.k) {
                        this.x = 0.0f;
                        this.y *= 1.8f;
                        GL11.glScalef((float)0.9f, (float)0.9f, (float)0.9f);
                    } else if (!this.spider.k) {
                        this.x = 0.0f;
                        this.y *= 2.0f;
                    } else if (!this.naga.k) {
                        this.x = 0.0f;
                        this.y *= 2.0f;
                    } else if (!this.mermaid.k || !this.digitigrade.k) {
                        this.x = 0.0f;
                        this.y *= 2.0f;
                    }
                }
                boolean bl = bo2 = this.entity.ay <= 0 && this.entity.aB <= 0 && !this.base.isArmor;
                if (bo2) {
                    float red1 = (float)(this.entity.legParts.color >> 16 & 0xFF) / 255.0f;
                    float green = (float)(this.entity.legParts.color >> 8 & 0xFF) / 255.0f;
                    float blue = (float)(this.entity.legParts.color & 0xFF) / 255.0f;
                    GL11.glColor3f((float)red1, (float)green, (float)blue);
                }
                super.a(par1);
                if (bo2) {
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                }
                GL11.glPopMatrix();
            }
        }
    }

    public void setData(EntityCustomNpc entity) {
        this.entity = entity;
    }
}

