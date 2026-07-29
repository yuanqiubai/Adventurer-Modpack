package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class WorldProvider {

   public static final float[] moonPhaseFactors = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   public World worldObj;
   public WorldType terrainType;
   public String field_82913_c;
   public WorldChunkManager worldChunkMgr;
   public boolean isHellWorld;
   public boolean hasNoSky;
   public float[] lightBrightnessTable = new float[16];
   public int dimensionId;
   private float[] colorsSunriseSunset = new float[4];

   public final void registerWorld(World par1World) {
      this.worldObj = par1World;
      this.terrainType = par1World.getWorldInfo().getTerrainType();
      this.field_82913_c = par1World.getWorldInfo().getGeneratorOptions();
      this.registerWorldChunkManager();
      this.generateLightBrightnessTable();
   }

   protected void generateLightBrightnessTable() {
      float var1 = 0.0F;

      for(int var2 = 0; var2 <= 15; ++var2) {
         float var3 = 1.0F - (float)var2 / 15.0F;
         this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
      }

   }

   protected void registerWorldChunkManager() {
      if(this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT) {
         FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions());
         this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.biomeList[var1.getBiome()], 0.5F, 0.5F);
      } else {
         this.worldChunkMgr = new WorldChunkManager(this.worldObj);
      }

   }

   public IChunkProvider createChunkGenerator() {
      return (IChunkProvider)(this.terrainType == WorldType.FLAT?new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.field_82913_c):new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled()));
   }

   public boolean canCoordinateBeSpawn(int par1, int par2) {
      int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
      return var3 == Block.grass.blockID;
   }

   public float calculateCelestialAngle(long par1, float par3) {
      int var4 = (int)(par1 % 24000L);
      float var5 = ((float)var4 + par3) / 24000.0F - 0.25F;
      if(var5 < 0.0F) {
         ++var5;
      }

      if(var5 > 1.0F) {
         --var5;
      }

      float var6 = var5;
      var5 = 1.0F - (float)((Math.cos((double)var5 * 3.141592653589793D) + 1.0D) / 2.0D);
      var5 = var6 + (var5 - var6) / 3.0F;
      return var5;
   }

   public int getMoonPhase(long par1) {
      return (int)(par1 / 24000L) % 8;
   }

   public boolean isSurfaceWorld() {
      return true;
   }

   public boolean canRespawnHere() {
      return true;
   }

   public static WorldProvider getProviderForDimension(int par0) {
      return (WorldProvider)(par0 == -1?new WorldProviderHell():(par0 == 0?new WorldProviderSurface():(par0 == 1?new WorldProviderEnd():null)));
   }

   public ChunkCoordinates getEntrancePortalLocation() {
      return null;
   }

   public int getAverageGroundLevel() {
      return this.terrainType == WorldType.FLAT?4:64;
   }

   public abstract String getDimensionName();

}
