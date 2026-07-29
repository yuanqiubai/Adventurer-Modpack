package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;

public abstract class AbstractTexture implements TextureObject {

   protected int field_110553_a = -1;


   public int func_110552_b() {
      if(this.field_110553_a == -1) {
         this.field_110553_a = TextureUtil.func_110996_a();
      }

      return this.field_110553_a;
   }
}
