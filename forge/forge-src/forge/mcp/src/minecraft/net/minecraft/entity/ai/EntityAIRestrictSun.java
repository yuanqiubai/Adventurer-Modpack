package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIRestrictSun extends EntityAIBase {

   private EntityCreature theEntity;

   public EntityAIRestrictSun(EntityCreature par1EntityCreature) {
      this.theEntity = par1EntityCreature;
   }

   public boolean shouldExecute() {
      return this.theEntity.worldObj.isDaytime();
   }

   public void startExecuting() {
      this.theEntity.getNavigator().setAvoidSun(true);
   }

   public void resetTask() {
      this.theEntity.getNavigator().setAvoidSun(false);
   }
}
