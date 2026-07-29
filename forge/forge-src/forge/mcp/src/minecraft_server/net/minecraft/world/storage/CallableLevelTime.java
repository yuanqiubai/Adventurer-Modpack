package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelTime implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelTime(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelTime() {
      return String.format("%d game time, %d day time", new Object[]{Long.valueOf(WorldInfo.func_85126_g(this.worldInfoInstance)), Long.valueOf(WorldInfo.getWorldTime(this.worldInfoInstance))});
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelTime();
   }
}
