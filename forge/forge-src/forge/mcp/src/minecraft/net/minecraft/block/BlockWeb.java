package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockWeb extends Block {

   public BlockWeb(int par1) {
      super(par1, Material.web);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
      par5Entity.setInWeb();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return null;
   }

   public int getRenderType() {
      return 1;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.silk.itemID;
   }

   protected boolean canSilkHarvest() {
      return true;
   }
}
