package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMushroomCap extends Block {

   private static final String[] field_94429_a = new String[]{"skin_brown", "skin_red"};
   private final int mushroomType;

   public BlockMushroomCap(int par1, Material par2Material, int par3) {
      super(par1, par2Material);
      this.mushroomType = par3;
   }

   public int quantityDropped(Random par1Random) {
      int var2 = par1Random.nextInt(10) - 7;
      if(var2 < 0) {
         var2 = 0;
      }

      return var2;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.mushroomBrown.blockID + this.mushroomType;
   }

}
