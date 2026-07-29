package net.minecraft.server.dedicated;

import java.util.concurrent.Callable;
import net.minecraft.server.dedicated.DedicatedServer;

class CallableType implements Callable {

   // $FF: synthetic field
   final DedicatedServer theDecitatedServer;

   CallableType(DedicatedServer par1DedicatedServer) {
      this.theDecitatedServer = par1DedicatedServer;
   }

   public String getType() {
      String var1 = this.theDecitatedServer.getServerModName();
      return !var1.equals("vanilla")?"Definitely; Server brand changed to \'" + var1 + "\'":"Unknown (can\'t tell)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.getType();
   }
}
