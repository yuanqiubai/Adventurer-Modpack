package net.minecraft.server.integrated;

import java.util.concurrent.Callable;
import net.minecraft.server.integrated.IntegratedServer;

class CallableType3 implements Callable {

   // $FF: synthetic field
   final IntegratedServer theIntegratedServer;

   CallableType3(IntegratedServer par1IntegratedServer) {
      this.theIntegratedServer = par1IntegratedServer;
   }

   public String getType() {
      return "Integrated Server (map_client.txt)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.getType();
   }
}
