package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSheep extends RenderLiving {

   private static final ResourceLocation sheepTextures = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");

   public RenderSheep(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
      super(par1ModelBase, par3);
      this.setRenderPassModel(par2ModelBase);
   }

   protected int setWoolColorAndRender(EntitySheep par1EntitySheep, int par2, float par3) {
      if(par2 == 0 && !par1EntitySheep.getSheared()) {
         this.bindTexture(sheepTextures);
         float var4 = 1.0F;
         int var5 = par1EntitySheep.getFleeceColor();
         GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
         return 1;
      } else {
         return -1;
      }
   }

   protected ResourceLocation func_110883_a(EntitySheep par1EntitySheep) {
      return shearedSheepTextures;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.setWoolColorAndRender((EntitySheep)par1EntityLivingBase, par2, par3);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.func_110883_a((EntitySheep)par1Entity);
   }

}
