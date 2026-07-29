package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

final class CreativeTabDeco extends CreativeTabs {

   CreativeTabDeco(int par1, String par2Str) {
      super(par1, par2Str);
   }

   public int getTabIconItemIndex() {
      return Item.redstone.itemID;
   }
}
