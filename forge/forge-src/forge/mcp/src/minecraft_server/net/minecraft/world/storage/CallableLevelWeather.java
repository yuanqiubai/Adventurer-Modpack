package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.world.storage.WorldInfo;

class CallableLevelWeather implements Callable {

   // $FF: synthetic field
   final WorldInfo worldInfoInstance;

   CallableLevelWeather(WorldInfo par1WorldInfo) {
      this.worldInfoInstance = par1WorldInfo;
   }

   public String callLevelWeatherInfo() {
      return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[]{Integer.valueOf(WorldInfo.getRainTime(this.worldInfoInstance)), Boolean.valueOf(WorldInfo.getRaining(this.worldInfoInstance)), Integer.valueOf(WorldInfo.getThunderTime(this.worldInfoInstance)), Boolean.valueOf(WorldInfo.getThundering(this.worldInfoInstance))});
   }

   // $FF: synthetic method
   public Object call() {
      return this.callLevelWeatherInfo();
   }
}
