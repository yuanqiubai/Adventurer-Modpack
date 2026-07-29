package net.minecraft.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;

public class BlockCarrot extends BlockCrops {

   public BlockCarrot(int par1) {
      super(par1);
   }

   protected int getSeedItem() {
      return Item.carrot.itemID;
   }

   protected int getCropItem() {
      return Item.carrot.itemID;
   }
}
