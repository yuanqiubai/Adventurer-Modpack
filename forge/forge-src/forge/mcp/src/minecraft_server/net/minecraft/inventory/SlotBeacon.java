package net.minecraft.inventory;

import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class SlotBeacon extends Slot {

   // $FF: synthetic field
   final ContainerBeacon beacon;

   public SlotBeacon(ContainerBeacon par1ContainerBeacon, IInventory par2IInventory, int par3, int par4, int par5) {
      super(par2IInventory, par3, par4, par5);
      this.beacon = par1ContainerBeacon;
   }

   public boolean isItemValid(ItemStack par1ItemStack) {
      return par1ItemStack == null?false:par1ItemStack.itemID == Item.emerald.itemID || par1ItemStack.itemID == Item.diamond.itemID || par1ItemStack.itemID == Item.ingotGold.itemID || par1ItemStack.itemID == Item.ingotIron.itemID;
   }

   public int getSlotStackLimit() {
      return 1;
   }
}
