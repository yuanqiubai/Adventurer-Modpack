package net.minecraft.server.dedicated;

import java.util.concurrent.Callable;
import net.minecraft.server.dedicated.DedicatedServer;

class CallableServerType implements Callable {

   // $FF: synthetic field
   final DedicatedServer theDedicatedServer;

   CallableServerType(DedicatedServer par1DedicatedServer) {
      this.theDedicatedServer = par1DedicatedServer;
   }

   public String callServerType() {
      return "Dedicated Server (map_server.txt)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.callServerType();
   }
}
