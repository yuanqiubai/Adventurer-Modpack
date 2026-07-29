package net.minecraft.client.renderer.texture;

import java.util.concurrent.Callable;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;

class TextureManagerINNER1 implements Callable {

   // $FF: synthetic field
   final TextureObject theTextureObject;
   // $FF: synthetic field
   final TextureManager theTextureManager;

   TextureManagerINNER1(TextureManager par1TextureManager, TextureObject par2TextureObject) {
      this.theTextureManager = par1TextureManager;
      this.theTextureObject = par2TextureObject;
   }

   public String func_135060_a() {
      return this.theTextureObject.getClass().getName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_135060_a();
   }
}
