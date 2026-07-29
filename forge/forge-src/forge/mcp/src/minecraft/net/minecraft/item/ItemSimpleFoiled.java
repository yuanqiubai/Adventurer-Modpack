package net.minecraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSimpleFoiled extends Item {

   public ItemSimpleFoiled(int par1) {
      super(par1);
   }

   public boolean hasEffect(ItemStack par1ItemStack) {
      return true;
   }
}
