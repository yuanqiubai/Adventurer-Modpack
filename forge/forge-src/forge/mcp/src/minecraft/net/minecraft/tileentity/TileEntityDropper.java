package net.minecraft.tileentity;

import net.minecraft.tileentity.TileEntityDispenser;

public class TileEntityDropper extends TileEntityDispenser {

   public String getInvName() {
      return this.isInvNameLocalized()?this.customName:"container.dropper";
   }
}
