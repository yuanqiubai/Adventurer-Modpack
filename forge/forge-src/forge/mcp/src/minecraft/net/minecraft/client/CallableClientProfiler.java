package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

class CallableClientProfiler implements Callable {

   // $FF: synthetic field
   final Minecraft theMinecraft;

   CallableClientProfiler(Minecraft par1Minecraft) {
      this.theMinecraft = par1Minecraft;
   }

   public String callClientProfilerInfo() {
      return Minecraft.func_142024_b(this.theMinecraft).getCurrentLanguage().toString();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callClientProfilerInfo();
   }
}
