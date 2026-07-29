package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockBookshelf extends Block {

   public BlockBookshelf(int par1) {
      super(par1, Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int quantityDropped(Random par1Random) {
      return 3;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.book.itemID;
   }
}
