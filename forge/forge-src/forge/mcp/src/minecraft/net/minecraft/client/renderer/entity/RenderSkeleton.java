package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSkeleton extends RenderBiped {

   private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public RenderSkeleton() {
      super(new ModelSkeleton(), 0.5F);
   }

   protected void scaleSkeleton(EntitySkeleton par1EntitySkeleton, float par2) {
      if(par1EntitySkeleton.getSkeletonType() == 1) {
         GL11.glScalef(1.2F, 1.2F, 1.2F);
      }

   }

   protected void func_82422_c() {
      GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
   }

   protected ResourceLocation func_110860_a(EntitySkeleton par1EntitySkeleton) {
      return par1EntitySkeleton.getSkeletonType() == 1?witherSkeletonTextures:skeletonTextures;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving) {
      return this.func_110860_a((EntitySkeleton)par1EntityLiving);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.scaleSkeleton((EntitySkeleton)par1EntityLivingBase, par2);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.func_110860_a((EntitySkeleton)par1Entity);
   }

}
