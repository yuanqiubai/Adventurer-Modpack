package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockMelon extends Block {

   protected BlockMelon(int par1) {
      super(par1, Material.pumpkin);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.melon.itemID;
   }

   public int quantityDropped(Random par1Random) {
      return 3 + par1Random.nextInt(5);
   }

   public int quantityDroppedWithBonus(int par1, Random par2Random) {
      int var3 = this.quantityDropped(par2Random) + par2Random.nextInt(1 + par1);
      if(var3 > 9) {
         var3 = 9;
      }

      return var3;
   }
}
