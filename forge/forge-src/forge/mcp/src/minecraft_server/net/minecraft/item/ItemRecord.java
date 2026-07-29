package net.minecraft.item;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRecord extends Item {

   private static final Map records = new HashMap();
   public final String recordName;

   protected ItemRecord(int par1, String par2Str) {
      super(par1);
      this.recordName = par2Str;
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabMisc);
      records.put(par2Str, this);
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par3World.getBlockId(par4, par5, par6) == Block.jukebox.blockID && par3World.getBlockMetadata(par4, par5, par6) == 0) {
         if(par3World.isRemote) {
            return true;
         } else {
            ((BlockJukeBox)Block.jukebox).insertRecord(par3World, par4, par5, par6, par1ItemStack);
            par3World.playAuxSFXAtEntity((EntityPlayer)null, 1005, par4, par5, par6, this.itemID);
            --par1ItemStack.stackSize;
            return true;
         }
      } else {
         return false;
      }
   }

}
