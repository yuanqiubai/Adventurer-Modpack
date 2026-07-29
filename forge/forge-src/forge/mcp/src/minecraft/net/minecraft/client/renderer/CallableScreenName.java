package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import net.minecraft.client.renderer.EntityRenderer;

class CallableScreenName implements Callable {

   // $FF: synthetic field
   final EntityRenderer entityRender;

   CallableScreenName(EntityRenderer par1EntityRenderer) {
      this.entityRender = par1EntityRenderer;
   }

   public String callScreenName() {
      return EntityRenderer.getRendererMinecraft(this.entityRender).currentScreen.getClass().getCanonicalName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callScreenName();
   }
}
