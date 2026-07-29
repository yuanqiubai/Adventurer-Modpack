package net.minecraft.tileentity;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDaylightDetector extends TileEntity {

   public void updateEntity() {
      if(this.worldObj != null && !this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 20L == 0L) {
         this.blockType = this.getBlockType();
         if(this.blockType != null && this.blockType instanceof BlockDaylightDetector) {
            ((BlockDaylightDetector)this.blockType).updateLightLevel(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
         }
      }

   }
}
