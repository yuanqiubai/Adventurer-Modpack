/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  avi
 *  bbo
 *  beu
 *  bez
 *  bfq
 *  bhb
 *  bhe
 *  bjo
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.Event
 *  nn
 *  of
 *  og
 *  org.lwjgl.opengl.GL11
 *  sg
 */
package noppes.client.renderer;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import noppes.common.constants.EnumAnimation;
import noppes.common.constants.EnumJobType;
import noppes.common.constants.EnumMovingType;
import noppes.common.constants.EnumStandingType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.JobBoss;
import org.lwjgl.opengl.GL11;

public class RenderNPCInterface
extends bhe {
    public RenderNPCInterface(bbo model, float f) {
        super(model, f);
    }

    protected void renderName(EntityNPCInterface npc, double d, double d1, double d2) {
        if (atv.r() && npc != this.b.h && npc.display.showName()) {
            float f3;
            float f2 = npc.d((nn)this.b.h);
            float f = f3 = npc.ah() ? 32.0f : 64.0f;
            if (f2 < f3) {
                String s = npc.an();
                float yoffset = 2.0f + npc.labelOffset;
                if (npc.ai.movingType == EnumMovingType.Standing) {
                    if (!npc.bh() && !npc.isKilled()) {
                        if (npc.ag()) {
                            yoffset *= 0.75f;
                        }
                    } else {
                        yoffset = 0.5f;
                    }
                }
                yoffset = (float)((double)yoffset * Math.pow((float)npc.display.modelSize / 5.0f, 1.1));
                yoffset = (float)((double)yoffset * ((double)npc.scaleY + 0.06));
                this.renderLivingLabel(npc, s, d, d1 - 2.0 + (double)(yoffset += npc.currentAnimation == EnumAnimation.NONE ? npc.ai.bodyOffsetY / 10.0f - 0.5f : 0.0f), d2, 64);
            }
        }
    }

    protected void renderLivingLabel(EntityNPCInterface npc, String s, double d, double d1, double d2, int i) {
        float f = npc.d((nn)this.b.h);
        if (f <= (float)i) {
            avi fontrenderer = this.a();
            float f1 = 1.6f * (float)npc.display.modelSize / 5.0f;
            float f2 = 0.01666667f * f1;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)d + 0.0f), (float)((float)d1 + 2.3f), (float)((float)d2));
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-this.b.j), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.b.k, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)(-f2), (float)(-f2), (float)f2);
            GL11.glDisable((int)2896);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            bfq tessellator = bfq.a;
            GL11.glDisable((int)3553);
            tessellator.b();
            int j = fontrenderer.a(s) / 2;
            tessellator.a(0.0f, 0.0f, 0.0f, 0.25f);
            tessellator.a((double)(-j - 1), -1.0, 0.0);
            tessellator.a((double)(-j - 1), 8.0, 0.0);
            tessellator.a((double)(j + 1), 8.0, 0.0);
            tessellator.a((double)(j + 1), -1.0, 0.0);
            tessellator.a();
            GL11.glEnable((int)3553);
            GL11.glDepthMask((boolean)true);
            int color = npc.getFaction().color;
            fontrenderer.b(s, -fontrenderer.a(s) / 2, 0, color);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
        }
    }

    protected void renderPlayerScale(EntityNPCInterface npc, float f) {
        float red = (float)(npc.display.skinColor >> 16 & 0xFF) / 255.0f;
        float green = (float)(npc.display.skinColor >> 8 & 0xFF) / 255.0f;
        float blue = (float)(npc.display.skinColor & 0xFF) / 255.0f;
        GL11.glColor3f((float)red, (float)green, (float)blue);
        GL11.glScalef((float)(npc.scaleX / 5.0f * (float)npc.display.modelSize), (float)(npc.scaleY / 5.0f * (float)npc.display.modelSize), (float)(npc.scaleZ / 5.0f * (float)npc.display.modelSize));
    }

    protected void renderPlayerSleep(EntityNPCInterface npc, double d, double d1, double d2) {
        this.d = (float)npc.display.modelSize / 10.0f;
        float xOffset = 0.0f;
        float yOffset = npc.currentAnimation == EnumAnimation.NONE ? npc.ai.bodyOffsetY / 10.0f - 0.5f : 0.0f;
        float zOffset = 0.0f;
        if (!npc.isKilled() && !npc.isWalking()) {
            if (npc.bh()) {
                xOffset = (float)(-Math.cos(Math.toRadians(180 - npc.ai.orientation)));
                zOffset = (float)(-Math.sin(Math.toRadians(npc.ai.orientation)));
                yOffset += 0.14f;
            } else if (npc.ag()) {
                yOffset -= 0.5f;
            }
        }
        this.renderLiving(npc, d, d1, d2, xOffset, yOffset, zOffset);
    }

    private void renderLiving(EntityNPCInterface npc, double d, double d1, double d2, float xoffset, float yoffset, float zoffset) {
        xoffset = xoffset / 5.0f * (float)npc.display.modelSize;
        yoffset = yoffset / 5.0f * (float)npc.display.modelSize;
        zoffset = zoffset / 5.0f * (float)npc.display.modelSize;
        super.a((of)npc, d + (double)xoffset, d1 + (double)yoffset, d2 + (double)zoffset);
    }

    protected void a(of entity, float f, float f1, float f2) {
        EntityNPCInterface npc = (EntityNPCInterface)entity;
        if (npc.T() && npc.bh()) {
            GL11.glRotatef((float)npc.ai.orientation, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.a((of)npc), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        } else {
            super.a((of)npc, f, f1, f2);
        }
    }

    protected void b(of par1EntityLivingBase, double par2, double par4, double par6) {
        if (!MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Specials.Pre(par1EntityLivingBase, (bhb)this))) {
            this.renderName((EntityNPCInterface)par1EntityLivingBase, par2, par4, par6);
        }
    }

    protected void a(of entityliving, float f) {
        this.renderPlayerScale((EntityNPCInterface)entityliving, f);
    }

    public void a(og entityliving, double d, double d1, double d2, float f, float f1) {
        EntityNPCInterface npc = (EntityNPCInterface)entityliving;
        if (!npc.isKilled() || !npc.stats.hideKilledBody || npc.aB <= 10) {
            if (npc.advanced.job == EnumJobType.Boss && !npc.isKilled() && npc.aB <= 10 && !((JobBoss)npc.jobInterface).hideName) {
                bez.a((sg)npc, (boolean)true);
            }
            if (npc.ai.standingType == EnumStandingType.HeadRotation && !npc.isWalking()) {
                npc.aO = npc.aN = (float)npc.ai.orientation;
            }
            super.a(entityliving, d, d1, d2, f, f1);
        }
    }

    protected void a(of entityliving, float par2, float par3, float par4, float par5, float par6, float par7) {
        super.a(entityliving, par2, par3, par4, par5, par6, par7);
        EntityNPCInterface npc = (EntityNPCInterface)entityliving;
        if (!npc.display.glowTexture.isEmpty()) {
            GL11.glDepthFunc((int)515);
            if (npc.textureGlowLocation == null) {
                npc.textureGlowLocation = new bjo(npc.display.glowTexture);
            }
            this.a((bjo)npc.textureGlowLocation);
            float f1 = 1.0f;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)1, (int)1);
            GL11.glDisable((int)2896);
            if (npc.aj()) {
                GL11.glDepthMask((boolean)false);
            } else {
                GL11.glDepthMask((boolean)true);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPushMatrix();
            GL11.glScalef((float)1.001f, (float)1.001f, (float)1.001f);
            this.i.a((nn)entityliving, par2, par3, par4, par5, par6, par7);
            GL11.glPopMatrix();
            GL11.glEnable((int)2896);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)f1);
            GL11.glDepthFunc((int)515);
            GL11.glDisable((int)3042);
        }
    }

    protected float b(of par1EntityLiving, float par2) {
        EntityNPCInterface npc = (EntityNPCInterface)par1EntityLiving;
        return !npc.isKilled() && !npc.display.NoLivingAnimation ? super.b(par1EntityLiving, par2) : 0.0f;
    }

    protected void a(of entityliving, double d, double d1, double d2) {
        this.renderPlayerSleep((EntityNPCInterface)entityliving, d, d1, d2);
    }

    public bjo a(nn entity) {
        EntityNPCInterface npc = (EntityNPCInterface)entity;
        if (npc.textureLocation == null) {
            if (npc.display.usingSkinUrl) {
                bjo location = beu.f((String)npc.display.skinUsername);
                beu.a((bjo)location, (String)npc.display.skinUsername);
                npc.textureLocation = location;
            } else {
                npc.textureLocation = new bjo(npc.display.texture);
            }
        }
        return (bjo)npc.textureLocation;
    }
}

