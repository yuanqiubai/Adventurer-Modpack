package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelSeed implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelSeed(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelSeed() {
      return String.valueOf(this.worldInfoInstance.getSeed());
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelSeed();
   }
}
