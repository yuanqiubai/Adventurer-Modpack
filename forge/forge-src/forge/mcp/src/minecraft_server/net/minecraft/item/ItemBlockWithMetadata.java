package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockWithMetadata extends ItemBlock {

   private Block theBlock;

   public ItemBlockWithMetadata(int par1, Block par2Block) {
      super(par1);
      this.theBlock = par2Block;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public int getMetadata(int par1) {
      return par1;
   }
}
