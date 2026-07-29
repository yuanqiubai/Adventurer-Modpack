package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBreakable extends Block {

   private boolean localFlag;
   private String breakableBlockIcon;

   protected BlockBreakable(int par1, String par2Str, Material par3Material, boolean par4) {
      super(par1, par3Material);
      this.localFlag = par4;
      this.breakableBlockIcon = par2Str;
   }

   public boolean isOpaqueCube() {
      return false;
   }
}
