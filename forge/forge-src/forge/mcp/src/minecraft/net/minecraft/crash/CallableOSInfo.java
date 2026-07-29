package net.minecraft.crash;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class CallableOSInfo implements Callable {

   // $FF: synthetic field
   final CrashReport theCrashReport;

   CallableOSInfo(CrashReport par1CrashReport) {
      this.theCrashReport = par1CrashReport;
   }

   public String getOsAsString() {
      return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
   }

   // $FF: synthetic method
   public Object call() {
      return this.getOsAsString();
   }
}
