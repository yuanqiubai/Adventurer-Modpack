package net.minecraft.dispenser;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.DispenserBehaviorPotionProjectile;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

final class DispenserBehaviorPotion implements IBehaviorDispenseItem {

   private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();

   public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
      return ItemPotion.isSplash(par2ItemStack.getItemDamage())?(new DispenserBehaviorPotionProjectile(this, par2ItemStack)).dispense(par1IBlockSource, par2ItemStack):this.defaultDispenserItemBehavior.dispense(par1IBlockSource, par2ItemStack);
   }
}
