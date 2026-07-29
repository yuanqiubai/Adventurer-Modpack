package net.minecraft.crash;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class CallableJavaInfo2 implements Callable {

   // $FF: synthetic field
   final CrashReport theCrashReport;

   CallableJavaInfo2(CrashReport par1CrashReport) {
      this.theCrashReport = par1CrashReport;
   }

   public String getJavaVMInfoAsString() {
      return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
   }

   // $FF: synthetic method
   public Object call() {
      return this.getJavaVMInfoAsString();
   }
}
