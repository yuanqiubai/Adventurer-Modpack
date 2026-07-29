package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCoal extends Item {

   public ItemCoal(int par1) {
      super(par1);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return par1ItemStack.getItemDamage() == 1?"item.charcoal":"item.coal";
   }
}
