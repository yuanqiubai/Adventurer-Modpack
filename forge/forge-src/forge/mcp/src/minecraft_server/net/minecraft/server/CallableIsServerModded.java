package net.minecraft.server;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableIsServerModded implements Callable {

   // $FF: synthetic field
   final MinecraftServer mcServer;

   public CallableIsServerModded(MinecraftServer par1MinecraftServer) {
      this.mcServer = par1MinecraftServer;
   }

   public String func_96558_a() {
      return this.mcServer.theProfiler.profilingEnabled?this.mcServer.theProfiler.getNameOfLastSection():"N/A (disabled)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_96558_a();
   }
}
