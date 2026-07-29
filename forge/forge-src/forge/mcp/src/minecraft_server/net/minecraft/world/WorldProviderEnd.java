package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd extends WorldProvider {

   public void registerWorldChunkManager() {
      this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5F, 0.0F);
      this.dimensionId = 1;
      this.hasNoSky = true;
   }

   public IChunkProvider createChunkGenerator() {
      return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
   }

   public float calculateCelestialAngle(long par1, float par3) {
      return 0.0F;
   }

   public boolean canRespawnHere() {
      return false;
   }

   public boolean isSurfaceWorld() {
      return false;
   }

   public boolean canCoordinateBeSpawn(int par1, int par2) {
      int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
      return var3 == 0?false:Block.blocksList[var3].blockMaterial.blocksMovement();
   }

   public ChunkCoordinates getEntrancePortalLocation() {
      return new ChunkCoordinates(100, 50, 0);
   }

   public int getAverageGroundLevel() {
      return 50;
   }

   public String getDimensionName() {
      return "The End";
   }
}
