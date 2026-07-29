package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelGamemode implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelGamemode(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelGameModeInfo() {
      return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[]{WorldInfo.getGameType(this.worldInfoInstance).getName(), Integer.valueOf(WorldInfo.getGameType(this.worldInfoInstance).getID()), Boolean.valueOf(WorldInfo.func_85117_p(this.worldInfoInstance)), Boolean.valueOf(WorldInfo.func_85131_q(this.worldInfoInstance))});
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelGameModeInfo();
   }
}
