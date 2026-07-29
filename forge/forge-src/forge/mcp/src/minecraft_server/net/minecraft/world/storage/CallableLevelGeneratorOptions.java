package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelGeneratorOptions implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelGeneratorOptions(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelGeneratorOptions() {
      return WorldInfo.getWorldGeneratorOptions(this.worldInfoInstance);
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelGeneratorOptions();
   }
}
