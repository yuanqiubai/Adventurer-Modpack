package net.minecraft.entity.passive;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;

final class EntityHorseBredSelector implements IEntitySelector {

   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity instanceof EntityHorse && ((EntityHorse)par1Entity).func_110205_ce();
   }
}
