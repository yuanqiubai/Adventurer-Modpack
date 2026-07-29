package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelDimension implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelDimension(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelDimension() {
      return String.valueOf(WorldInfo.func_85122_i(this.worldInfoInstance));
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelDimension();
   }
}
