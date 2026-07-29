package net.minecraft.entity;

import java.util.concurrent.Callable;
import net.minecraft.entity.Entity;

class CallableEntityName implements Callable {

   // $FF: synthetic field
   final Entity theEntity;

   CallableEntityName(Entity par1Entity) {
      this.theEntity = par1Entity;
   }

   public String callEntityName() {
      return this.theEntity.getEntityName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.callEntityName();
   }
}
