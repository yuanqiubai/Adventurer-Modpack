package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelSpawnLocation implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelSpawnLocation(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelSpawnLocation() {
      return CrashReportCategory.getLocationInfo(WorldInfo.getSpawnXCoordinate(this.worldInfoInstance), WorldInfo.getSpawnYCoordinate(this.worldInfoInstance), WorldInfo.getSpawnZCoordinate(this.worldInfoInstance));
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelSpawnLocation();
   }
}
