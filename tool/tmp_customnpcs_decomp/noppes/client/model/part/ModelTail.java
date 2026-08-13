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
 *  of
 *  org.lwjgl.opengl.GL11
 */
package noppes.client.model.part;

import noppes.client.model.ModelMPM;
import noppes.client.model.part.tails.ModelDragonTail;
import noppes.client.model.part.tails.ModelSquirrelTail;
import noppes.client.model.util.ModelScaleRenderer;
import noppes.common.constants.EnumAnimation;
import noppes.common.entity.EntityCustomNpc;
import noppes.common.entity.model_data.ModelPartData;
import org.lwjgl.opengl.GL11;

public class ModelTail
extends ModelScaleRenderer {
    private EntityCustomNpc entity;
    private final ModelMPM base;
    private final bcu tail;
    private final bcu dragon;
    private final bcu squirrel;
    private final bcu horse;
    private int color = 0xFFFFFF;
    private bjo location = null;

    public ModelTail(ModelMPM base) {
        super(base);
        this.base = base;
        this.d = 11.0f;
        this.tail = new bcu((bbo)base, 56, 21);
        this.tail.a(-1.0f, 0.0f, 0.0f, 2, 9, 2);
        this.tail.a(0.0f, 0.0f, 1.0f);
        this.setRotation(this.tail, 0.8714253f, 0.0f, 0.0f);
        this.a(this.tail);
        this.horse = new bcu((bbo)base);
        this.horse.b(32, 32);
        this.horse.a(0.0f, -1.0f, 1.0f);
        this.a(this.horse);
        bcu tailBase = new bcu((bbo)base, 0, 26);
        tailBase.b(32, 32);
        tailBase.a(-1.0f, -1.0f, 0.0f, 2, 2, 3);
        this.setRotation(tailBase, -1.134464f, 0.0f, 0.0f);
        this.horse.a(tailBase);
        bcu tailMiddle = new bcu((bbo)base, 0, 13);
        tailMiddle.b(32, 32);
        tailMiddle.a(-1.5f, -2.0f, 3.0f, 3, 4, 7);
        this.setRotation(tailMiddle, -1.134464f, 0.0f, 0.0f);
        this.horse.a(tailMiddle);
        bcu tailTip = new bcu((bbo)base, 0, 0);
        tailTip.b(32, 32);
        tailTip.a(-1.5f, -4.5f, 9.0f, 3, 4, 7);
        this.setRotation(tailTip, -1.40215f, 0.0f, 0.0f);
        this.horse.a(tailTip);
        this.horse.f = 0.5f;
        this.dragon = new ModelDragonTail(base);
        this.a(this.dragon);
        this.squirrel = new ModelSquirrelTail(base);
        this.a(this.squirrel);
    }

    public void setData(EntityCustomNpc entity) {
        this.entity = entity;
        this.initData(entity);
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, nn entity) {
        this.g = ls.b((float)(par1 * 0.6662f)) * 0.3f * par2;
        this.f = ls.a((float)(par3 * 0.067f)) * 0.05f;
        if (this.entity.legParts.type == 2) {
            this.d = 13.0f;
            this.e = 14.0f * this.entity.legs.scaleZ;
            if (this.base.isSleeping(entity) || this.entity.currentAnimation == EnumAnimation.CRAWLING) {
                this.d = 12.0f + 16.0f * this.entity.legs.scaleZ;
                this.e = this.entity.legs.scaleY;
                this.f = -0.7853982f;
            }
        } else if (this.entity.legParts.type == 3) {
            this.d = 8.6f;
            this.e = 19.0f * this.entity.legs.scaleZ;
        } else {
            this.d = 11.0f;
            this.e = -1.0f;
        }
        this.e += this.base.bipedRightLeg.e + 0.5f;
    }

    public void setLivingAnimations(ModelPartData data, of entity, float par2, float par3, float par4) {
    }

    public void initData(EntityCustomNpc data) {
        ModelPartData config = data.getPartData("tail");
        if (config == null) {
            this.k = true;
        } else {
            this.color = config.color;
            this.k = false;
            this.tail.k = config.type != 0;
            this.dragon.k = config.type != 1;
            this.horse.k = config.type != 2;
            this.squirrel.k = config.type != 3;
            this.location = !config.playerTexture ? (bjo)config.getResource() : null;
        }
    }

    @Override
    public void a(float par1) {
        if (!this.k) {
            boolean bo1;
            if (!this.base.isArmor) {
                bim bo;
                if (this.location != null) {
                    bo = atv.w().J();
                    bo.a(this.location);
                    this.base.currentlyPlayerTexture = false;
                } else if (!this.base.currentlyPlayerTexture) {
                    bo = atv.w().J();
                    bo.a((bjo)this.entity.textureLocation);
                    this.base.currentlyPlayerTexture = true;
                }
            }
            boolean bl = bo1 = this.entity.ay <= 0 && this.entity.aB <= 0;
            if (bo1) {
                float red = (float)(this.color >> 16 & 0xFF) / 255.0f;
                float green = (float)(this.color >> 8 & 0xFF) / 255.0f;
                float blue = (float)(this.color & 0xFF) / 255.0f;
                GL11.glColor4f((float)red, (float)green, (float)blue, (float)1.0f);
            }
            super.a(par1);
            if (bo1) {
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
        }
    }
}

