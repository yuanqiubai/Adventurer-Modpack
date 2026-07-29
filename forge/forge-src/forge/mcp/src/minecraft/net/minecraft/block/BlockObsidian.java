package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;

public class BlockObsidian extends BlockStone {

   public BlockObsidian(int par1) {
      super(par1);
   }

   public int quantityDropped(Random par1Random) {
      return 1;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.obsidian.blockID;
   }
}
