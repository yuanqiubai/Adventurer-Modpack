package net.minecraft.command;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;

final class EntitySelectorInventory implements IEntitySelector {

   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity instanceof IInventory && par1Entity.isEntityAlive();
   }
}
