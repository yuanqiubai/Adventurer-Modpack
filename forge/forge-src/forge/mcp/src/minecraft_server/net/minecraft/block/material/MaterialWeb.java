package net.minecraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

final class MaterialWeb extends Material {

   MaterialWeb(MapColor par1MapColor) {
      super(par1MapColor);
   }

   public boolean blocksMovement() {
      return false;
   }
}
