package net.minecraft.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockHay extends BlockRotatedPillar {

   public BlockHay(int par1) {
      super(par1, Material.grass);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int getRenderType() {
      return 31;
   }
}
