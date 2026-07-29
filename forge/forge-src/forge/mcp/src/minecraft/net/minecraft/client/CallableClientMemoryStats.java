package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

class CallableClientMemoryStats implements Callable {

   // $FF: synthetic field
   final Minecraft theMinecraft;

   CallableClientMemoryStats(Minecraft par1Minecraft) {
      this.theMinecraft = par1Minecraft;
   }

   public String callClientMemoryStats() {
      return this.theMinecraft.mcProfiler.profilingEnabled?this.theMinecraft.mcProfiler.getNameOfLastSection():"N/A (disabled)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.callClientMemoryStats();
   }
}
