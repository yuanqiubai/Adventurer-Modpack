package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockStep extends BlockHalfSlab {

   public static final String[] blockStepTypes = new String[]{"stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz"};

   public BlockStep(int par1, boolean par2) {
      super(par1, par2, Material.rock);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.stoneSingleSlab.blockID;
   }

   protected ItemStack createStackedBlock(int par1) {
      return new ItemStack(Block.stoneSingleSlab.blockID, 2, par1 & 7);
   }

   public String getFullSlabName(int par1) {
      if(par1 < 0 || par1 >= blockStepTypes.length) {
         par1 = 0;
      }

      return super.getUnlocalizedName() + "." + blockStepTypes[par1];
   }

}
