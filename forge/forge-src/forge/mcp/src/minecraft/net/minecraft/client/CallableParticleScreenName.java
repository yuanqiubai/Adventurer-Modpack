package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

class CallableParticleScreenName implements Callable {

   // $FF: synthetic field
   final Minecraft theMinecraft;

   CallableParticleScreenName(Minecraft par1Minecraft) {
      this.theMinecraft = par1Minecraft;
   }

   public String callParticleScreenName() {
      return this.theMinecraft.currentScreen.getClass().getCanonicalName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callParticleScreenName();
   }
}
