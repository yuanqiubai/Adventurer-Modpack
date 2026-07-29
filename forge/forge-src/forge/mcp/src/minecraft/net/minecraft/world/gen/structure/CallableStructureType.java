package net.minecraft.world.gen.structure;

import java.util.concurrent.Callable;
import net.minecraft.world.gen.structure.MapGenStructure;

class CallableStructureType implements Callable {

   // $FF: synthetic field
   final MapGenStructure theMapStructureGenerator;

   CallableStructureType(MapGenStructure par1MapGenStructure) {
      this.theMapStructureGenerator = par1MapGenStructure;
   }

   public String callStructureType() {
      return this.theMapStructureGenerator.getClass().getCanonicalName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callStructureType();
   }
}
