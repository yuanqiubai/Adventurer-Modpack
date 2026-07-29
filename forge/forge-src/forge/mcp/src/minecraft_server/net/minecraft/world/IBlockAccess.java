package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;

public interface IBlockAccess {

   int getBlockId(int var1, int var2, int var3);

   TileEntity getBlockTileEntity(int var1, int var2, int var3);

   int getBlockMetadata(int var1, int var2, int var3);

   Material getBlockMaterial(int var1, int var2, int var3);

   boolean isBlockNormalCube(int var1, int var2, int var3);

   Vec3Pool getWorldVec3Pool();

   int isBlockProvidingPowerTo(int var1, int var2, int var3, int var4);
}
