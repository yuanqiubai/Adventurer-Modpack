package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSwamp extends BiomeGenBase {

   protected BiomeGenSwamp(int par1) {
      super(par1);
      this.theBiomeDecorator.treesPerChunk = 2;
      this.theBiomeDecorator.flowersPerChunk = -999;
      this.theBiomeDecorator.deadBushPerChunk = 1;
      this.theBiomeDecorator.mushroomsPerChunk = 8;
      this.theBiomeDecorator.reedsPerChunk = 10;
      this.theBiomeDecorator.clayPerChunk = 1;
      this.theBiomeDecorator.waterlilyPerChunk = 4;
      this.waterColorMultiplier = 14745518;
      this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
   }

   public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
      return this.worldGeneratorSwamp;
   }
}
