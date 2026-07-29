package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGiantZombie extends RenderLiving {

   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
   private float scale;

   public RenderGiantZombie(ModelBase par1ModelBase, float par2, float par3) {
      super(par1ModelBase, par2 * par3);
      this.scale = par3;
   }

   protected void preRenderScale(EntityGiantZombie par1EntityGiantZombie, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   protected ResourceLocation getZombieTextures(EntityGiantZombie par1EntityGiantZombie) {
      return zombieTextures;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.preRenderScale((EntityGiantZombie)par1EntityLivingBase, par2);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getZombieTextures((EntityGiantZombie)par1Entity);
   }

}
