package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLeashKnot extends Render {

   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
   private ModelLeashKnot leashKnotModel = new ModelLeashKnot();

   public void func_110799_a(EntityLeashKnot par1EntityLeashKnot, double par2, double par4, double par6, float par8, float par9) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)par2, (float)par4, (float)par6);
      float var10 = 0.0625F;
      GL11.glEnable('\u803a');
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glEnable(3008);
      this.bindEntityTexture(par1EntityLeashKnot);
      this.leashKnotModel.render(par1EntityLeashKnot, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
      GL11.glPopMatrix();
   }

   protected ResourceLocation getLeashKnotTextures(EntityLeashKnot par1EntityLeashKnot) {
      return leashKnotTextures;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getLeashKnotTextures((EntityLeashKnot)par1Entity);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.func_110799_a((EntityLeashKnot)par1Entity, par2, par4, par6, par8, par9);
   }

}
