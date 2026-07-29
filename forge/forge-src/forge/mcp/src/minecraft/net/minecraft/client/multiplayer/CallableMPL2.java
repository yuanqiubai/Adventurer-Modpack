package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;
import net.minecraft.client.multiplayer.WorldClient;

class CallableMPL2 implements Callable {

   // $FF: synthetic field
   final WorldClient theWorldClient;

   CallableMPL2(WorldClient par1WorldClient) {
      this.theWorldClient = par1WorldClient;
   }

   public String getEntitySpawnQueueCountAndList() {
      return WorldClient.getEntitySpawnQueue(this.theWorldClient).size() + " total; " + WorldClient.getEntitySpawnQueue(this.theWorldClient).toString();
   }

   // $FF: synthetic method
   public Object call() {
      return this.getEntitySpawnQueueCountAndList();
   }
}
