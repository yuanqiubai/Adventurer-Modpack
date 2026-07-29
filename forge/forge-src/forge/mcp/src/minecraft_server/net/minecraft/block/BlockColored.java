package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockColored extends Block {

   public BlockColored(int par1, Material par2Material) {
      super(par1, par2Material);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public static int getBlockFromDye(int par0) {
      return ~par0 & 15;
   }

   public static int getDyeFromBlock(int par0) {
      return ~par0 & 15;
   }
}
