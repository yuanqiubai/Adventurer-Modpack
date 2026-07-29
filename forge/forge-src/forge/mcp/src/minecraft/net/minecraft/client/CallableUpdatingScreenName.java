package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

class CallableUpdatingScreenName implements Callable {

   // $FF: synthetic field
   final Minecraft theMinecraft;

   CallableUpdatingScreenName(Minecraft par1Minecraft) {
      this.theMinecraft = par1Minecraft;
   }

   public String callUpdatingScreenName() {
      return this.theMinecraft.currentScreen.getClass().getCanonicalName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callUpdatingScreenName();
   }
}
