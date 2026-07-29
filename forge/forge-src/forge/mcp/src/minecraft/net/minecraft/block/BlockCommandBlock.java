package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.world.World;

public class BlockCommandBlock extends BlockContainer {

   public BlockCommandBlock(int par1) {
      super(par1, Material.iron);
   }

   public TileEntity createNewTileEntity(World par1World) {
      return new TileEntityCommandBlock();
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(!par1World.isRemote) {
         boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
         int var7 = par1World.getBlockMetadata(par2, par3, par4);
         boolean var8 = (var7 & 1) != 0;
         if(var6 && !var8) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 | 1, 4);
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
         } else if(!var6 && var8) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 & -2, 4);
         }
      }

   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
      if(var6 != null && var6 instanceof TileEntityCommandBlock) {
         TileEntityCommandBlock var7 = (TileEntityCommandBlock)var6;
         var7.setSignalStrength(var7.executeCommandOnPowered(par1World));
         par1World.func_96440_m(par2, par3, par4, this.blockID);
      }

   }

   public int tickRate(World par1World) {
      return 1;
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      TileEntityCommandBlock var10 = (TileEntityCommandBlock)par1World.getBlockTileEntity(par2, par3, par4);
      if(var10 != null) {
         par5EntityPlayer.displayGUIEditSign(var10);
      }

      return true;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
      TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
      return var6 != null && var6 instanceof TileEntityCommandBlock?((TileEntityCommandBlock)var6).getSignalStrength():0;
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      TileEntityCommandBlock var7 = (TileEntityCommandBlock)par1World.getBlockTileEntity(par2, par3, par4);
      if(par6ItemStack.hasDisplayName()) {
         var7.setCommandSenderName(par6ItemStack.getDisplayName());
      }

   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }
}
