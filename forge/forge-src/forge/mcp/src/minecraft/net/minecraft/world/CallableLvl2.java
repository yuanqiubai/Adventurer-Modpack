package net.minecraft.world;

import java.util.concurrent.Callable;
import net.minecraft.world.World;

class CallableLvl2 implements Callable {

   // $FF: synthetic field
   final World theWorld;

   CallableLvl2(World par1World) {
      this.theWorld = par1World;
   }

   public String getPlayerEntities() {
      return this.theWorld.playerEntities.size() + " total; " + this.theWorld.playerEntities.toString();
   }

   // $FF: synthetic method
   public Object call() {
      return this.getPlayerEntities();
   }
}
