package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

class CallableLaunchedVersion implements Callable {

   // $FF: synthetic field
   final Minecraft mc;

   CallableLaunchedVersion(Minecraft par1Minecraft) {
      this.mc = par1Minecraft;
   }

   public String getLWJGLVersion() {
      return Minecraft.getLaunchedVersion(this.mc);
   }

   // $FF: synthetic method
   public Object call() {
      return this.getLWJGLVersion();
   }
}
