package net.minecraft.world;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;

public class PortalPosition extends ChunkCoordinates {

   public long lastUpdateTime;
   // $FF: synthetic field
   final Teleporter teleporterInstance;

   public PortalPosition(Teleporter par1Teleporter, int par2, int par3, int par4, long par5) {
      super(par2, par3, par4);
      this.teleporterInstance = par1Teleporter;
      this.lastUpdateTime = par5;
   }
}
