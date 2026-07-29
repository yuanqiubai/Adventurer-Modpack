package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.item.ItemMultiTextureTile;

public class ItemAnvilBlock extends ItemMultiTextureTile {

   public ItemAnvilBlock(Block par1Block) {
      super(par1Block.blockID - 256, par1Block, BlockAnvil.statuses);
   }

   public int getMetadata(int par1) {
      return par1 << 2;
   }
}
