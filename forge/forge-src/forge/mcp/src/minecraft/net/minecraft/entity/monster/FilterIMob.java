package net.minecraft.entity.monster;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;

final class FilterIMob implements IEntitySelector {

   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity instanceof IMob;
   }
}
