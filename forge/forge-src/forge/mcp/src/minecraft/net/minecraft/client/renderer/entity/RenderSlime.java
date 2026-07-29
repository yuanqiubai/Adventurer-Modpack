package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSlime extends RenderLiving {

   private static final ResourceLocation slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
   private ModelBase scaleAmount;

   public RenderSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
      super(par1ModelBase, par3);
      this.scaleAmount = par2ModelBase;
   }

   protected int shouldSlimeRenderPass(EntitySlime par1EntitySlime, int par2, float par3) {
      if(par1EntitySlime.isInvisible()) {
         return 0;
      } else if(par2 == 0) {
         this.setRenderPassModel(this.scaleAmount);
         GL11.glEnable(2977);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         return 1;
      } else {
         if(par2 == 1) {
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }

         return -1;
      }
   }

   protected void scaleSlime(EntitySlime par1EntitySlime, float par2) {
      float var3 = (float)par1EntitySlime.getSlimeSize();
      float var4 = (par1EntitySlime.prevSquishFactor + (par1EntitySlime.squishFactor - par1EntitySlime.prevSquishFactor) * par2) / (var3 * 0.5F + 1.0F);
      float var5 = 1.0F / (var4 + 1.0F);
      GL11.glScalef(var5 * var3, 1.0F / var5 * var3, var5 * var3);
   }

   protected ResourceLocation getSlimeTextures(EntitySlime par1EntitySlime) {
      return slimeTextures;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.scaleSlime((EntitySlime)par1EntityLivingBase, par2);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.shouldSlimeRenderPass((EntitySlime)par1EntityLivingBase, par2, par3);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getSlimeTextures((EntitySlime)par1Entity);
   }

}
