package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockQuartz extends Block {

   public static final String[] quartzBlockTypes = new String[]{"default", "chiseled", "lines"};
   private static final String[] quartzBlockTextureTypes = new String[]{"side", "chiseled", "lines", null, null};

   public BlockQuartz(int par1) {
      super(par1, Material.rock);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
      if(par9 == 2) {
         switch(par5) {
         case 0:
         case 1:
            par9 = 2;
            break;
         case 2:
         case 3:
            par9 = 4;
            break;
         case 4:
         case 5:
            par9 = 3;
         }
      }

      return par9;
   }

   public int damageDropped(int par1) {
      return par1 != 3 && par1 != 4?par1:2;
   }

   protected ItemStack createStackedBlock(int par1) {
      return par1 != 3 && par1 != 4?super.createStackedBlock(par1):new ItemStack(this.blockID, 1, 2);
   }

   public int getRenderType() {
      return 39;
   }

}
