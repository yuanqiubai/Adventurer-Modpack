package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

class CallableLWJGLVersion implements Callable {

   // $FF: synthetic field
   final Minecraft mc;

   CallableLWJGLVersion(Minecraft par1Minecraft) {
      this.mc = par1Minecraft;
   }

   public String getType() {
      return Sys.getVersion();
   }

   // $FF: synthetic method
   public Object call() {
      return this.getType();
   }
}
