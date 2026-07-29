package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

class CallableTexturePack implements Callable {

   // $FF: synthetic field
   final Minecraft theMinecraft;

   CallableTexturePack(Minecraft par1Minecraft) {
      this.theMinecraft = par1Minecraft;
   }

   public String callTexturePack() {
      return this.theMinecraft.gameSettings.skin;
   }

   // $FF: synthetic method
   public Object call() {
      return this.callTexturePack();
   }
}
