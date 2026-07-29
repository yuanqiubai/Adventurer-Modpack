package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockWoodSlab extends BlockHalfSlab {

   public static final String[] woodType = new String[]{"oak", "spruce", "birch", "jungle"};

   public BlockWoodSlab(int par1, boolean par2) {
      super(par1, par2, Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.woodSingleSlab.blockID;
   }

   protected ItemStack createStackedBlock(int par1) {
      return new ItemStack(Block.woodSingleSlab.blockID, 2, par1 & 7);
   }

   public String getFullSlabName(int par1) {
      if(par1 < 0 || par1 >= woodType.length) {
         par1 = 0;
      }

      return super.getUnlocalizedName() + "." + woodType[par1];
   }

}
