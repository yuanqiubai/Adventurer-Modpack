package net.minecraft.crash;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class CallableMinecraftVersion implements Callable {

   // $FF: synthetic field
   final CrashReport theCrashReport;

   CallableMinecraftVersion(CrashReport par1CrashReport) {
      this.theCrashReport = par1CrashReport;
   }

   public String minecraftVersion() {
      return "1.6.4";
   }

   // $FF: synthetic method
   public Object call() {
      return this.minecraftVersion();
   }
}
