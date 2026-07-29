package net.minecraft.crash;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;

final class CallableBlockLocation implements Callable {

   // $FF: synthetic field
   final int blockXCoord;
   // $FF: synthetic field
   final int blockYCoord;
   // $FF: synthetic field
   final int blockZCoord;

   CallableBlockLocation(int par1, int par2, int par3) {
      this.blockXCoord = par1;
      this.blockYCoord = par2;
      this.blockZCoord = par3;
   }

   public String callBlockLocationInfo() {
      return CrashReportCategory.getLocationInfo(this.blockXCoord, this.blockYCoord, this.blockZCoord);
   }

   // $FF: synthetic method
   public Object call() {
      return this.callBlockLocationInfo();
   }
}
