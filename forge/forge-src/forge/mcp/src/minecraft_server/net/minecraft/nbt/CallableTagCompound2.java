package net.minecraft.nbt;

import java.util.concurrent.Callable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

class CallableTagCompound2 implements Callable {

   // $FF: synthetic field
   final int field_82588_a;
   // $FF: synthetic field
   final NBTTagCompound theNBTTagCompound;

   CallableTagCompound2(NBTTagCompound par1NBTTagCompound, int par2) {
      this.theNBTTagCompound = par1NBTTagCompound;
      this.field_82588_a = par2;
   }

   public String func_82586_a() {
      return NBTBase.NBTTypes[this.field_82588_a];
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_82586_a();
   }
}
