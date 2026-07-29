package net.minecraft.entity;

import java.util.concurrent.Callable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

class CallableEntityType implements Callable {

   // $FF: synthetic field
   final Entity theEntity;

   CallableEntityType(Entity par1Entity) {
      this.theEntity = par1Entity;
   }

   public String callEntityType() {
      return EntityList.getEntityString(this.theEntity) + " (" + this.theEntity.getClass().getCanonicalName() + ")";
   }

   // $FF: synthetic method
   public Object call() {
      return this.callEntityType();
   }
}
