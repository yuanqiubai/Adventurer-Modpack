package net.minecraft.item;

import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLeaves extends ItemBlock {

   public ItemLeaves(int par1) {
      super(par1);
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public int getMetadata(int par1) {
      return par1 | 4;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      int var2 = par1ItemStack.getItemDamage();
      if(var2 < 0 || var2 >= BlockLeaves.LEAF_TYPES.length) {
         var2 = 0;
      }

      return super.getUnlocalizedName() + "." + BlockLeaves.LEAF_TYPES[var2];
   }
}
