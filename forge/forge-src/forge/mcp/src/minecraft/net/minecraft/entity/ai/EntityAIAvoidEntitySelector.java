package net.minecraft.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

class EntityAIAvoidEntitySelector implements IEntitySelector {

   // $FF: synthetic field
   final EntityAIAvoidEntity entityAvoiderAI;

   EntityAIAvoidEntitySelector(EntityAIAvoidEntity par1EntityAIAvoidEntity) {
      this.entityAvoiderAI = par1EntityAIAvoidEntity;
   }

   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity.isEntityAlive() && EntityAIAvoidEntity.func_98217_a(this.entityAvoiderAI).getEntitySenses().canSee(par1Entity);
   }
}
