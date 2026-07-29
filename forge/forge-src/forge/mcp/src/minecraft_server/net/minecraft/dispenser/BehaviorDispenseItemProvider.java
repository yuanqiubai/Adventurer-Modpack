package net.minecraft.dispenser;

import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

final class BehaviorDispenseItemProvider implements IBehaviorDispenseItem {

   public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
      return par2ItemStack;
   }
}
