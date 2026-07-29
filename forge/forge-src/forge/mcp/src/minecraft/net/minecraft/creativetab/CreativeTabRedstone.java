package net.minecraft.creativetab;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

final class CreativeTabRedstone extends CreativeTabs {

   CreativeTabRedstone(int par1, String par2Str) {
      super(par1, par2Str);
   }

   public int getTabIconItemIndex() {
      return Block.railPowered.blockID;
   }
}
