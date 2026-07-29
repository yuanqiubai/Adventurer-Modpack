package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndPortal extends BlockContainer {

   public static boolean bossDefeated;

   protected BlockEndPortal(int par1, Material par2Material) {
      super(par1, par2Material);
      this.setLightValue(1.0F);
   }

   public TileEntity createNewTileEntity(World par1World) {
      return new TileEntityEndPortal();
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      float var5 = 0.0625F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {}

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
      if(par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && !par1World.isRemote) {
         par5Entity.travelToDimension(1);
      }

   }

   public int getRenderType() {
      return -1;
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      if(!bossDefeated) {
         if(par1World.provider.dimensionId != 0) {
            par1World.setBlockToAir(par2, par3, par4);
         }

      }
   }
}
