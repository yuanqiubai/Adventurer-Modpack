package net.minecraft.world.gen.structure;

import java.util.concurrent.Callable;
import net.minecraft.world.gen.structure.MapGenStructure;

class CallableIsFeatureChunk implements Callable {

   // $FF: synthetic field
   final int field_85169_a;
   // $FF: synthetic field
   final int field_85167_b;
   // $FF: synthetic field
   final MapGenStructure theMapStructureGenerator;

   CallableIsFeatureChunk(MapGenStructure par1MapGenStructure, int par2, int par3) {
      this.theMapStructureGenerator = par1MapGenStructure;
      this.field_85169_a = par2;
      this.field_85167_b = par3;
   }

   public String func_85166_a() {
      return this.theMapStructureGenerator.canSpawnStructureAtCoords(this.field_85169_a, this.field_85167_b)?"True":"False";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_85166_a();
   }
}
