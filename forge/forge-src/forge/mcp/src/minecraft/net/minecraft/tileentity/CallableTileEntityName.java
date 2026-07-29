package net.minecraft.tileentity;

import java.util.concurrent.Callable;
import net.minecraft.tileentity.TileEntity;

class CallableTileEntityName implements Callable {

   // $FF: synthetic field
   final TileEntity theTileEntity;

   CallableTileEntityName(TileEntity par1TileEntity) {
      this.theTileEntity = par1TileEntity;
   }

   public String callTileEntityName() {
      return (String)TileEntity.getClassToNameMap().get(this.theTileEntity.getClass()) + " // " + this.theTileEntity.getClass().getCanonicalName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callTileEntityName();
   }
}
