package net.minecraft.crash;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class CallableJavaInfo implements Callable {

   // $FF: synthetic field
   final CrashReport theCrashReport;

   CallableJavaInfo(CrashReport par1CrashReport) {
      this.theCrashReport = par1CrashReport;
   }

   public String getJavaInfoAsString() {
      return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
   }

   // $FF: synthetic method
   public Object call() {
      return this.getJavaInfoAsString();
   }
}
