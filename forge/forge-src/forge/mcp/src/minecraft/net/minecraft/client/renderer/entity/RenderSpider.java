package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSpider extends RenderLiving {

   private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
   private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");

   public RenderSpider() {
      super(new ModelSpider(), 1.0F);
      this.setRenderPassModel(new ModelSpider());
   }

   protected float setSpiderDeathMaxRotation(EntitySpider par1EntitySpider) {
      return 180.0F;
   }

   protected int setSpiderEyeBrightness(EntitySpider par1EntitySpider, int par2, float par3) {
      if(par2 != 0) {
         return -1;
      } else {
         this.bindTexture(spiderEyesTextures);
         float var4 = 1.0F;
         GL11.glEnable(3042);
         GL11.glDisable(3008);
         GL11.glBlendFunc(1, 1);
         if(par1EntitySpider.isInvisible()) {
            GL11.glDepthMask(false);
         } else {
            GL11.glDepthMask(true);
         }

         char var5 = '\uf0f0';
         int var6 = var5 % 65536;
         int var7 = var5 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
         return 1;
      }
   }

   protected ResourceLocation getSpiderTextures(EntitySpider par1EntitySpider) {
      return spiderTextures;
   }

   // $FF: synthetic method
   protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase) {
      return this.setSpiderDeathMaxRotation((EntitySpider)par1EntityLivingBase);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.setSpiderEyeBrightness((EntitySpider)par1EntityLivingBase, par2, par3);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getSpiderTextures((EntitySpider)par1Entity);
   }

}
