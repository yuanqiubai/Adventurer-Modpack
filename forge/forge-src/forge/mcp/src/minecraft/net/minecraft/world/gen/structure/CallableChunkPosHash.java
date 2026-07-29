package net.minecraft.world.gen.structure;

import java.util.concurrent.Callable;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.gen.structure.MapGenStructure;

class CallableChunkPosHash implements Callable {

   // $FF: synthetic field
   final int field_85165_a;
   // $FF: synthetic field
   final int field_85163_b;
   // $FF: synthetic field
   final MapGenStructure theMapStructureGenerator;

   CallableChunkPosHash(MapGenStructure par1MapGenStructure, int par2, int par3) {
      this.theMapStructureGenerator = par1MapGenStructure;
      this.field_85165_a = par2;
      this.field_85163_b = par3;
   }

   public String callChunkPositionHash() {
      return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.field_85165_a, this.field_85163_b));
   }

   // $FF: synthetic method
   public Object call() {
      return this.callChunkPositionHash();
   }
}
