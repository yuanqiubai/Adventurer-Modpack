package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockLockedChest extends Block {

   protected BlockLockedChest(int par1) {
      super(par1, Material.wood);
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return true;
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      par1World.setBlockToAir(par2, par3, par4);
   }
}
