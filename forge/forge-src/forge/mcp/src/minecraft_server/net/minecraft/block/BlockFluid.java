package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockFluid extends Block {

   protected BlockFluid(int par1, Material par2Material) {
      super(par1, par2Material);
      float var3 = 0.0F;
      float var4 = 0.0F;
      this.setBlockBounds(0.0F + var4, 0.0F + var3, 0.0F + var4, 1.0F + var4, 1.0F + var3, 1.0F + var4);
      this.setTickRandomly(true);
   }

   public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return this.blockMaterial != Material.lava;
   }

   public static float getFluidHeightPercent(int par0) {
      if(par0 >= 8) {
         par0 = 0;
      }

      return (float)(par0 + 1) / 9.0F;
   }

   protected int getFlowDecay(World par1World, int par2, int par3, int par4) {
      return par1World.getBlockMaterial(par2, par3, par4) == this.blockMaterial?par1World.getBlockMetadata(par2, par3, par4):-1;
   }

   protected int getEffectiveFlowDecay(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      if(par1IBlockAccess.getBlockMaterial(par2, par3, par4) != this.blockMaterial) {
         return -1;
      } else {
         int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
         if(var5 >= 8) {
            var5 = 0;
         }

         return var5;
      }
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean canCollideCheck(int par1, boolean par2) {
      return par2 && par1 == 0;
   }

   public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
      return var6 == this.blockMaterial?false:(par5 == 1?true:(var6 == Material.ice?false:super.isBlockSolid(par1IBlockAccess, par2, par3, par4, par5)));
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return null;
   }

   public int getRenderType() {
      return 4;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return 0;
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   private Vec3 getFlowVector(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      Vec3 var5 = par1IBlockAccess.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
      int var6 = this.getEffectiveFlowDecay(par1IBlockAccess, par2, par3, par4);

      for(int var7 = 0; var7 < 4; ++var7) {
         int var8 = par2;
         int var10 = par4;
         if(var7 == 0) {
            var8 = par2 - 1;
         }

         if(var7 == 1) {
            var10 = par4 - 1;
         }

         if(var7 == 2) {
            ++var8;
         }

         if(var7 == 3) {
            ++var10;
         }

         int var11 = this.getEffectiveFlowDecay(par1IBlockAccess, var8, par3, var10);
         int var12;
         if(var11 < 0) {
            if(!par1IBlockAccess.getBlockMaterial(var8, par3, var10).blocksMovement()) {
               var11 = this.getEffectiveFlowDecay(par1IBlockAccess, var8, par3 - 1, var10);
               if(var11 >= 0) {
                  var12 = var11 - (var6 - 8);
                  var5 = var5.addVector((double)((var8 - par2) * var12), (double)((par3 - par3) * var12), (double)((var10 - par4) * var12));
               }
            }
         } else if(var11 >= 0) {
            var12 = var11 - var6;
            var5 = var5.addVector((double)((var8 - par2) * var12), (double)((par3 - par3) * var12), (double)((var10 - par4) * var12));
         }
      }

      if(par1IBlockAccess.getBlockMetadata(par2, par3, par4) >= 8) {
         boolean var13 = false;
         if(var13 || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 - 1, 2)) {
            var13 = true;
         }

         if(var13 || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 + 1, 3)) {
            var13 = true;
         }

         if(var13 || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3, par4, 4)) {
            var13 = true;
         }

         if(var13 || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3, par4, 5)) {
            var13 = true;
         }

         if(var13 || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 - 1, 2)) {
            var13 = true;
         }

         if(var13 || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 + 1, 3)) {
            var13 = true;
         }

         if(var13 || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3 + 1, par4, 4)) {
            var13 = true;
         }

         if(var13 || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3 + 1, par4, 5)) {
            var13 = true;
         }

         if(var13) {
            var5 = var5.normalize().addVector(0.0D, -6.0D, 0.0D);
         }
      }

      var5 = var5.normalize();
      return var5;
   }

   public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3) {
      Vec3 var7 = this.getFlowVector(par1World, par2, par3, par4);
      par6Vec3.xCoord += var7.xCoord;
      par6Vec3.yCoord += var7.yCoord;
      par6Vec3.zCoord += var7.zCoord;
   }

   public int tickRate(World par1World) {
      return this.blockMaterial == Material.water?5:(this.blockMaterial == Material.lava?(par1World.provider.hasNoSky?10:30):0);
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      this.checkForHarden(par1World, par2, par3, par4);
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      this.checkForHarden(par1World, par2, par3, par4);
   }

   private void checkForHarden(World par1World, int par2, int par3, int par4) {
      if(par1World.getBlockId(par2, par3, par4) == this.blockID) {
         if(this.blockMaterial == Material.lava) {
            boolean var5 = false;
            if(var5 || par1World.getBlockMaterial(par2, par3, par4 - 1) == Material.water) {
               var5 = true;
            }

            if(var5 || par1World.getBlockMaterial(par2, par3, par4 + 1) == Material.water) {
               var5 = true;
            }

            if(var5 || par1World.getBlockMaterial(par2 - 1, par3, par4) == Material.water) {
               var5 = true;
            }

            if(var5 || par1World.getBlockMaterial(par2 + 1, par3, par4) == Material.water) {
               var5 = true;
            }

            if(var5 || par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.water) {
               var5 = true;
            }

            if(var5) {
               int var6 = par1World.getBlockMetadata(par2, par3, par4);
               if(var6 == 0) {
                  par1World.setBlock(par2, par3, par4, Block.obsidian.blockID);
               } else if(var6 <= 4) {
                  par1World.setBlock(par2, par3, par4, Block.cobblestone.blockID);
               }

               this.triggerLavaMixEffects(par1World, par2, par3, par4);
            }
         }

      }
   }

   protected void triggerLavaMixEffects(World par1World, int par2, int par3, int par4) {
      par1World.playSoundEffect((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

      for(int var5 = 0; var5 < 8; ++var5) {
         par1World.spawnParticle("largesmoke", (double)par2 + Math.random(), (double)par3 + 1.2D, (double)par4 + Math.random(), 0.0D, 0.0D, 0.0D);
      }

   }
}
