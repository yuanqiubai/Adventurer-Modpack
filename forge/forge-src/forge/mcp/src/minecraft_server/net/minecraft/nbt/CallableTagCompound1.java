package net.minecraft.nbt;

import java.util.concurrent.Callable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

class CallableTagCompound1 implements Callable {

   // $FF: synthetic field
   final String field_82585_a;
   // $FF: synthetic field
   final NBTTagCompound theNBTTagCompound;

   CallableTagCompound1(NBTTagCompound par1NBTTagCompound, String par2Str) {
      this.theNBTTagCompound = par1NBTTagCompound;
      this.field_82585_a = par2Str;
   }

   public String func_82583_a() {
      return NBTBase.NBTTypes[((NBTBase)NBTTagCompound.getTagMap(this.theNBTTagCompound).get(this.field_82585_a)).getId()];
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_82583_a();
   }
}
