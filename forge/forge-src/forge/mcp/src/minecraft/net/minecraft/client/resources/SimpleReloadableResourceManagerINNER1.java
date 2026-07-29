package net.minecraft.client.resources;

import com.google.common.base.Function;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;

class SimpleReloadableResourceManagerINNER1 implements Function {

   // $FF: synthetic field
   final SimpleReloadableResourceManager theSimpleReloadableResourceManager;

   SimpleReloadableResourceManagerINNER1(SimpleReloadableResourceManager par1SimpleReloadableResourceManager) {
      this.theSimpleReloadableResourceManager = par1SimpleReloadableResourceManager;
   }

   public String apply(ResourcePack par1ResourcePack) {
      return par1ResourcePack.getPackName();
   }

   // $FF: synthetic method
   public Object apply(Object par1Obj) {
      return this.apply((ResourcePack)par1Obj);
   }
}
