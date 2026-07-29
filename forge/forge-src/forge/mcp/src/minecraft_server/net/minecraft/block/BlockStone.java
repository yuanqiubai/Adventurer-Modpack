package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockStone extends Block {

   public BlockStone(int par1) {
      super(par1, Material.rock);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.cobblestone.blockID;
   }
}
