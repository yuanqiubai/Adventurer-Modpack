/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bbo
 *  bcu
 *  bim
 *  bjo
 *  nn
 *  of
 *  org.lwjgl.opengl.GL11
 *  uf
 */
package noppes.client.model;

import java.util.Random;
import noppes.client.model.ModelNPCMale;
import noppes.client.model.animation.AniCrawling;
import noppes.client.model.animation.AniHug;
import noppes.client.model.part.ModelBeard;
import noppes.client.model.part.ModelBreasts;
import noppes.client.model.part.ModelClaws;
import noppes.client.model.part.ModelEars;
import noppes.client.model.part.ModelFin;
import noppes.client.model.part.ModelHair;
import noppes.client.model.part.ModelHeadwear;
import noppes.client.model.part.ModelLegs;
import noppes.client.model.part.ModelMohawk;
import noppes.client.model.part.ModelSnout;
import noppes.client.model.part.ModelTail;
import noppes.client.model.part.ModelWings;
import noppes.client.model.util.ModelPartInterface;
import noppes.client.model.util.ModelScaleRenderer;
import noppes.common.constants.EnumAnimation;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartConfig;
import noppes.common.entity.model_data.ModelPartData;
import org.lwjgl.opengl.GL11;

public class ModelMPM
extends ModelNPCMale {
    public boolean currentlyPlayerTexture;
    public boolean isArmor;
    private ModelPartInterface wings;
    private ModelPartInterface mohawk;
    private ModelPartInterface hair;
    private ModelPartInterface beard;
    private final ModelPartInterface breasts;
    private ModelPartInterface snout;
    private ModelPartInterface ears;
    private ModelPartInterface fin;
    private ModelPartInterface clawsR;
    private ModelPartInterface clawsL;
    private final ModelLegs legs;
    private final ModelScaleRenderer headwear;
    private ModelTail tail;

    public ModelMPM(float par1) {
        super(par1);
        this.isArmor = par1 > 0.0f;
        float par2 = 0.0f;
        this.bipedCloak = new bcu((bbo)this, 0, 0);
        this.bipedCloak.a(-5.0f, 0.0f, -1.0f, 10, 16, 1, par1);
        this.bipedEars = new bcu((bbo)this, 24, 0);
        this.bipedEars.a(-3.0f, -6.0f, -1.0f, 6, 6, 1, par1);
        this.bipedHead = new ModelScaleRenderer(this, 0, 0);
        this.bipedHead.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, par1);
        this.bipedHead.a(0.0f, 0.0f + par2, 0.0f);
        this.bipedHeadwear = new ModelScaleRenderer(this, 32, 0);
        this.bipedHeadwear.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, par1 + 0.5f);
        this.bipedHeadwear.a(0.0f, 0.0f + par2, 0.0f);
        this.bipedBody = new ModelScaleRenderer(this, 16, 16);
        this.bipedBody.a(-4.0f, 0.0f, -2.0f, 8, 12, 4, par1);
        this.bipedBody.a(0.0f, 0.0f + par2, 0.0f);
        this.bipedRightArm = new ModelScaleRenderer(this, 40, 16);
        this.bipedRightArm.a(-3.0f, -2.0f, -2.0f, 4, 12, 4, par1);
        this.bipedRightArm.a(-5.0f, 2.0f + par2, 0.0f);
        this.bipedLeftArm = new ModelScaleRenderer(this, 40, 16);
        this.bipedLeftArm.i = true;
        this.bipedLeftArm.a(-1.0f, -2.0f, -2.0f, 4, 12, 4, par1);
        this.bipedLeftArm.a(5.0f, 2.0f + par2, 0.0f);
        this.bipedRightLeg = new ModelScaleRenderer(this, 0, 16);
        this.bipedRightLeg.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
        this.bipedRightLeg.a(-1.9f, 12.0f + par2, 0.0f);
        this.bipedLeftLeg = new ModelScaleRenderer(this, 0, 16);
        this.bipedLeftLeg.i = true;
        this.bipedLeftLeg.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
        this.bipedLeftLeg.a(1.9f, 12.0f + par2, 0.0f);
        this.headwear = new ModelHeadwear(this);
        this.legs = new ModelLegs(this, (ModelScaleRenderer)this.bipedRightLeg, (ModelScaleRenderer)this.bipedLeftLeg);
        this.breasts = new ModelBreasts(this);
        this.bipedBody.a((bcu)this.breasts);
        if (!this.isArmor) {
            this.ears = new ModelEars(this);
            this.bipedHead.a((bcu)this.ears);
            this.mohawk = new ModelMohawk(this);
            this.bipedHead.a((bcu)this.mohawk);
            this.hair = new ModelHair(this);
            this.bipedHead.a((bcu)this.hair);
            this.beard = new ModelBeard(this);
            this.bipedHead.a((bcu)this.beard);
            this.snout = new ModelSnout(this);
            this.bipedHead.a((bcu)this.snout);
            this.tail = new ModelTail(this);
            this.wings = new ModelWings(this);
            this.bipedBody.a((bcu)this.wings);
            this.fin = new ModelFin(this);
            this.bipedBody.a((bcu)this.fin);
            this.clawsL = new ModelClaws(this, false);
            this.bipedLeftArm.a((bcu)this.clawsL);
            this.clawsR = new ModelClaws(this, true);
            this.bipedRightArm.a((bcu)this.clawsR);
        }
    }

    private void setPlayerData(EntityCustomNpc entity) {
        if (!this.isArmor) {
            this.mohawk.setData(entity);
            this.beard.setData(entity);
            this.hair.setData(entity);
            this.snout.setData(entity);
            this.tail.setData(entity);
            this.fin.setData(entity);
            this.wings.setData(entity);
            this.ears.setData(entity);
            this.clawsL.setData(entity);
            this.clawsR.setData(entity);
        }
        this.breasts.setData(entity);
        this.legs.setData(entity);
    }

    @Override
    public void a(nn par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        EntityCustomNpc npc = (EntityCustomNpc)par1Entity;
        this.setPlayerData(npc);
        this.currentlyPlayerTexture = true;
        this.a(par2, par3, par4, par5, par6, par7, par1Entity);
        if (npc.currentAnimation == EnumAnimation.Aiming) {
            GL11.glPushMatrix();
            float ticks = (float)(par1Entity.ac - npc.animationStart) / 10.0f;
            if (ticks > 1.0f) {
                ticks = 1.0f;
            }
            float scale = 2.0f - npc.body.scaleY;
            GL11.glTranslatef((float)0.0f, (float)(12.0f * scale * par7), (float)0.0f);
            GL11.glRotatef((float)(60.0f * ticks), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)(-12.0f * scale * par7), (float)0.0f);
        }
        this.renderHead(npc, par7);
        this.renderArms(npc, par7, false);
        this.renderBody(npc, par7);
        if (npc.currentAnimation == EnumAnimation.Aiming) {
            GL11.glPopMatrix();
        }
        this.renderLegs(npc, par7);
    }

    public void a(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        EntityCustomNpc npc = (EntityCustomNpc)entity;
        if (!this.q) {
            boolean bl = this.q = npc.currentAnimation == EnumAnimation.SITTING;
        }
        if (this.isSneak && (npc.currentAnimation == EnumAnimation.CRAWLING || npc.isSleeping())) {
            this.isSneak = false;
        }
        this.bipedBody.e = 0.0f;
        this.bipedBody.d = 0.0f;
        this.bipedHead.h = 0.0f;
        this.bipedHeadwear.h = 0.0f;
        this.bipedLeftLeg.f = 0.0f;
        this.bipedLeftLeg.g = 0.0f;
        this.bipedLeftLeg.h = 0.0f;
        this.bipedRightLeg.f = 0.0f;
        this.bipedRightLeg.g = 0.0f;
        this.bipedRightLeg.h = 0.0f;
        this.bipedLeftArm.d = 2.0f;
        this.bipedLeftArm.e = 0.0f;
        this.bipedRightArm.d = 2.0f;
        this.bipedRightArm.e = 0.0f;
        super.a(par1, par2, par3, par4, par5, par6, entity);
        if (!this.isArmor) {
            this.hair.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            this.beard.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            this.wings.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            this.tail.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
        }
        this.legs.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
        if (this.isSleeping(entity)) {
            if (this.bipedHead.f < 0.0f) {
                this.bipedHead.f = 0.0f;
                this.bipedHeadwear.f = 0.0f;
            }
        } else if (npc.currentAnimation == EnumAnimation.CRY) {
            this.bipedHead.f = 0.7f;
            this.bipedHeadwear.f = 0.7f;
        } else if (npc.currentAnimation == EnumAnimation.HUG) {
            AniHug.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
        } else if (npc.currentAnimation == EnumAnimation.CRAWLING) {
            AniCrawling.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
        } else if (npc.currentAnimation == EnumAnimation.WAVING) {
            this.bipedRightArm.f = -0.1f;
            this.bipedRightArm.g = 0.0f;
            this.bipedRightArm.h = (float)(2.141592653589793 - Math.sin((float)entity.ac * 0.27f) * 0.5);
        } else if (this.isSneak) {
            this.bipedBody.f = 0.5f / npc.body.scaleY;
        }
    }

    @Override
    public void a(of par1EntityLivingBase, float par2, float par3, float par4) {
        ModelPartData partData;
        EntityCustomNpc npc = (EntityCustomNpc)par1EntityLivingBase;
        if (!this.isArmor && (partData = npc.getPartData("tail")) != null) {
            this.tail.setLivingAnimations(partData, par1EntityLivingBase, par2, par3, par4);
        }
    }

    public void loadPlayerTexture(EntityCustomNpc npc) {
        if (!this.isArmor && !this.currentlyPlayerTexture) {
            bim texturemanager = atv.w().J();
            texturemanager.a((bjo)npc.textureLocation);
            this.currentlyPlayerTexture = true;
        }
    }

    private void renderHead(EntityCustomNpc entity, float f) {
        this.loadPlayerTexture(entity);
        float x = 0.0f;
        float y = entity.getBodyY();
        float z = 0.0f;
        GL11.glPushMatrix();
        if (entity.currentAnimation == EnumAnimation.DANCING) {
            float head = (float)entity.ac / 4.0f;
            GL11.glTranslatef((float)((float)Math.sin(head) * 0.075f), (float)((float)Math.abs(Math.cos(head)) * 0.125f - 0.02f), (float)((float)(-Math.abs(Math.cos(head))) * 0.075f));
        }
        ModelPartConfig head1 = entity.head;
        ((ModelScaleRenderer)this.bipedHeadwear).setConfig(head1, x, y, z);
        this.bipedHeadwear.a(f);
        ((ModelScaleRenderer)this.bipedHead).setConfig(head1, x, y, z);
        this.bipedHead.a(f);
        GL11.glPopMatrix();
    }

    private void renderBody(EntityCustomNpc entity, float f) {
        this.loadPlayerTexture(entity);
        float x = 0.0f;
        float y = entity.getBodyY();
        float z = 0.0f;
        GL11.glPushMatrix();
        if (entity.currentAnimation == EnumAnimation.DANCING) {
            float body = (float)entity.ac / 4.0f;
            GL11.glTranslatef((float)((float)Math.sin(body) * 0.015f), (float)0.0f, (float)0.0f);
        }
        ModelPartConfig body1 = entity.body;
        ((ModelScaleRenderer)this.bipedBody).setConfig(body1, x, y, z);
        this.bipedBody.a(f);
        GL11.glPopMatrix();
    }

    public void renderArms(EntityCustomNpc entity, float f, boolean bo) {
        this.loadPlayerTexture(entity);
        ModelPartConfig arms = entity.arms;
        float x = (1.0f - entity.body.scaleX) * 0.25f + (1.0f - arms.scaleX) * 0.075f;
        float y = entity.getBodyY() + (1.0f - arms.scaleY) * -0.1f;
        float z = 0.0f;
        GL11.glPushMatrix();
        if (entity.currentAnimation == EnumAnimation.DANCING) {
            float dancing = (float)entity.ac / 4.0f;
            GL11.glTranslatef((float)((float)Math.sin(dancing) * 0.025f), (float)((float)Math.abs(Math.cos(dancing)) * 0.125f - 0.02f), (float)0.0f);
        }
        if (!bo) {
            ((ModelScaleRenderer)this.bipedLeftArm).setConfig(arms, -x, y, z);
            this.bipedLeftArm.a(f);
            ((ModelScaleRenderer)this.bipedRightArm).setConfig(arms, x, y, z);
            this.bipedRightArm.a(f);
        } else {
            ((ModelScaleRenderer)this.bipedRightArm).setConfig(arms, 0.0f, 0.0f, 0.0f);
            this.bipedRightArm.a(f);
        }
        GL11.glPopMatrix();
    }

    private void renderLegs(EntityCustomNpc entity, float f) {
        this.loadPlayerTexture(entity);
        ModelPartConfig legs = entity.legs;
        float x = (1.0f - legs.scaleX) * 0.125f;
        float y = entity.getLegsY();
        float z = 0.0f;
        GL11.glPushMatrix();
        this.legs.setConfig(legs, x, y, z);
        this.legs.a(f);
        if (!this.isArmor) {
            this.tail.setConfig(legs, 0.0f, y, z);
            this.tail.a(f);
        }
        GL11.glPopMatrix();
    }

    public bcu a(Random par1Random) {
        int random = par1Random.nextInt(5);
        switch (random) {
            case 0: {
                return this.bipedRightLeg;
            }
            case 1: {
                return this.bipedHead;
            }
            case 2: {
                return this.bipedLeftArm;
            }
            case 3: {
                return this.bipedRightArm;
            }
            case 4: {
                return this.bipedLeftLeg;
            }
        }
        return this.bipedBody;
    }

    public boolean isSleeping(nn entity) {
        return entity instanceof uf && ((uf)entity).bh() || ((EntityCustomNpc)entity).isSleeping();
    }
}

