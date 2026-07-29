package net.minecraft.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

class EntityAITaskEntry {

   public EntityAIBase action;
   public int priority;
   // $FF: synthetic field
   final EntityAITasks tasks;

   public EntityAITaskEntry(EntityAITasks par1EntityAITasks, int par2, EntityAIBase par3EntityAIBase) {
      this.tasks = par1EntityAITasks;
      this.priority = par2;
      this.action = par3EntityAIBase;
   }
}
