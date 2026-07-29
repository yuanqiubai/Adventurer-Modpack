package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderMagmaCube extends RenderLiving {

   private static final ResourceLocation magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");

   public RenderMagmaCube() {
      super(new ModelMagmaCube(), 0.25F);
   }

   protected ResourceLocation getMagmaCubeTextures(EntityMagmaCube par1EntityMagmaCube) {
      return magmaCubeTextures;
   }

   protected void scaleMagmaCube(EntityMagmaCube par1EntityMagmaCube, float par2) {
      int var3 = par1EntityMagmaCube.getSlimeSize();
      float var4 = (par1EntityMagmaCube.prevSquishFactor + (par1EntityMagmaCube.squishFactor - par1EntityMagmaCube.prevSquishFactor) * par2) / ((float)var3 * 0.5F + 1.0F);
      float var5 = 1.0F / (var4 + 1.0F);
      float var6 = (float)var3;
      GL11.glScalef(var5 * var6, 1.0F / var5 * var6, var5 * var6);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.scaleMagmaCube((EntityMagmaCube)par1EntityLivingBase, par2);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getMagmaCubeTextures((EntityMagmaCube)par1Entity);
   }

}
