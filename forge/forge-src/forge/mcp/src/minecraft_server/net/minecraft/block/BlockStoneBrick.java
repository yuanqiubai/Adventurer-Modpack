package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockStoneBrick extends Block {

   public static final String[] STONE_BRICK_TYPES = new String[]{"default", "mossy", "cracked", "chiseled"};
   public static final String[] field_94407_b = new String[]{null, "mossy", "cracked", "carved"};

   public BlockStoneBrick(int par1) {
      super(par1, Material.rock);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(int par1) {
      return par1;
   }

}
