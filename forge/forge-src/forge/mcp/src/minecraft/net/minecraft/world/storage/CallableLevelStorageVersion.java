package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelStorageVersion implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelStorageVersion(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelStorageFormat() {
      String var1 = "Unknown?";

      try {
         switch(WorldInfo.getSaveVersion(this.worldInfoInstance)) {
         case 19132:
            var1 = "McRegion";
            break;
         case 19133:
            var1 = "Anvil";
         }
      } catch (Throwable var3) {
         ;
      }

      return String.format("0x%05X - %s", new Object[]{Integer.valueOf(WorldInfo.getSaveVersion(this.worldInfoInstance)), var1});
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelStorageFormat();
   }
}
