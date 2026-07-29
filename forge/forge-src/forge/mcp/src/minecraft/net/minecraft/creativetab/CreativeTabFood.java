package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

final class CreativeTabFood extends CreativeTabs {

   CreativeTabFood(int par1, String par2Str) {
      super(par1, par2Str);
   }

   public int getTabIconItemIndex() {
      return Item.axeIron.itemID;
   }
}
