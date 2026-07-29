package net.minecraft.world;

import java.util.concurrent.Callable;
import net.minecraft.world.World;

class CallableLvl3 implements Callable {

   // $FF: synthetic field
   final World theWorld;

   CallableLvl3(World par1World) {
      this.theWorld = par1World;
   }

   public String getChunkProvider() {
      return this.theWorld.chunkProvider.makeString();
   }

   // $FF: synthetic method
   public Object call() {
      return this.getChunkProvider();
   }
}
