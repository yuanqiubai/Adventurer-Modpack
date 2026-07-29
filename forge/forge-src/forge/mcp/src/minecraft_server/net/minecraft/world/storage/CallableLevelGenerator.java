package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelGenerator implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelGenerator(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelGeneratorInfo() {
      return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[]{Integer.valueOf(WorldInfo.getTerrainTypeOfWorld(this.worldInfoInstance).getWorldTypeID()), WorldInfo.getTerrainTypeOfWorld(this.worldInfoInstance).getWorldTypeName(), Integer.valueOf(WorldInfo.getTerrainTypeOfWorld(this.worldInfoInstance).getGeneratorVersion()), Boolean.valueOf(WorldInfo.getMapFeaturesEnabled(this.worldInfoInstance))});
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelGeneratorInfo();
   }
}
