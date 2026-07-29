package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCaveSpider extends RenderSpider {

   private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");

   public RenderCaveSpider() {
      this.shadowSize *= 0.7F;
   }

   protected void scaleSpider(EntityCaveSpider par1EntityCaveSpider, float par2) {
      GL11.glScalef(0.7F, 0.7F, 0.7F);
   }

   protected ResourceLocation getCaveSpiderTextures(EntityCaveSpider par1EntityCaveSpider) {
      return caveSpiderTextures;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getSpiderTextures(EntitySpider par1EntitySpider) {
      return this.getCaveSpiderTextures((EntityCaveSpider)par1EntitySpider);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.scaleSpider((EntityCaveSpider)par1EntityLivingBase, par2);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getCaveSpiderTextures((EntityCaveSpider)par1Entity);
   }

}
