package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

public class RenderSilverfish extends RenderLiving {

   private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");

   public RenderSilverfish() {
      super(new ModelSilverfish(), 0.3F);
   }

   protected float getSilverfishDeathRotation(EntitySilverfish par1EntitySilverfish) {
      return 180.0F;
   }

   public void renderSilverfish(EntitySilverfish par1EntitySilverfish, double par2, double par4, double par6, float par8, float par9) {
      super.doRenderLiving(par1EntitySilverfish, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getSilverfishTextures(EntitySilverfish par1EntitySilverfish) {
      return silverfishTextures;
   }

   protected int shouldSilverfishRenderPass(EntitySilverfish par1EntitySilverfish, int par2, float par3) {
      return -1;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.renderSilverfish((EntitySilverfish)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase) {
      return this.getSilverfishDeathRotation((EntitySilverfish)par1EntityLivingBase);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.shouldSilverfishRenderPass((EntitySilverfish)par1EntityLivingBase, par2, par3);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.renderSilverfish((EntitySilverfish)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   // $FF: synthetic method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getSilverfishTextures((EntitySilverfish)par1Entity);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderSilverfish((EntitySilverfish)par1Entity, par2, par4, par6, par8, par9);
   }

}
