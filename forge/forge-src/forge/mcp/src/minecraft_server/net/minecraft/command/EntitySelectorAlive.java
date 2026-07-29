package net.minecraft.command;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

final class EntitySelectorAlive implements IEntitySelector {

   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity.isEntityAlive();
   }
}
