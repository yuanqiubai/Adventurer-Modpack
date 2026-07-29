package net.minecraft.entity.player;

import java.util.concurrent.Callable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

class CallableItemName implements Callable {

   // $FF: synthetic field
   final ItemStack theItemStack;
   // $FF: synthetic field
   final InventoryPlayer playerInventory;

   CallableItemName(InventoryPlayer par1InventoryPlayer, ItemStack par2ItemStack) {
      this.playerInventory = par1InventoryPlayer;
      this.theItemStack = par2ItemStack;
   }

   public String callItemDisplayName() {
      return this.theItemStack.getDisplayName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callItemDisplayName();
   }
}
