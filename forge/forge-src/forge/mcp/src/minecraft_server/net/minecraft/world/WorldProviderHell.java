package net.minecraft.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;

public class WorldProviderHell extends WorldProvider {

   public void registerWorldChunkManager() {
      this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 1.0F, 0.0F);
      this.isHellWorld = true;
      this.hasNoSky = true;
      this.dimensionId = -1;
   }

   protected void generateLightBrightnessTable() {
      float var1 = 0.1F;

      for(int var2 = 0; var2 <= 15; ++var2) {
         float var3 = 1.0F - (float)var2 / 15.0F;
         this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
      }

   }

   public IChunkProvider createChunkGenerator() {
      return new ChunkProviderHell(this.worldObj, this.worldObj.getSeed());
   }

   public boolean isSurfaceWorld() {
      return false;
   }

   public boolean canCoordinateBeSpawn(int par1, int par2) {
      return false;
   }

   public float calculateCelestialAngle(long par1, float par3) {
      return 0.5F;
   }

   public boolean canRespawnHere() {
      return false;
   }

   public String getDimensionName() {
      return "Nether";
   }
}
