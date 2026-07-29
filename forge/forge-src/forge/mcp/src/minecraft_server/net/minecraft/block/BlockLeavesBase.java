package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockLeavesBase extends Block {

   protected boolean graphicsLevel;

   protected BlockLeavesBase(int par1, Material par2Material, boolean par3) {
      super(par1, par2Material);
      this.graphicsLevel = par3;
   }

   public boolean isOpaqueCube() {
      return false;
   }
}
