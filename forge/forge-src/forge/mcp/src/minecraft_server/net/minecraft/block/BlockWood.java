package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWood extends Block {

   public static final String[] woodType = new String[]{"oak", "spruce", "birch", "jungle"};

   public BlockWood(int par1) {
      super(par1, Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(int par1) {
      return par1;
   }

}
