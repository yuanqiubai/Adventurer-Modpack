package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;
import net.minecraft.client.multiplayer.WorldClient;

class CallableMPL1 implements Callable {

   // $FF: synthetic field
   final WorldClient theWorldClient;

   CallableMPL1(WorldClient par1WorldClient) {
      this.theWorldClient = par1WorldClient;
   }

   public String getEntityCountAndList() {
      return WorldClient.getEntityList(this.theWorldClient).size() + " total; " + WorldClient.getEntityList(this.theWorldClient).toString();
   }

   // $FF: synthetic method
   public Object call() {
      return this.getEntityCountAndList();
   }
}
