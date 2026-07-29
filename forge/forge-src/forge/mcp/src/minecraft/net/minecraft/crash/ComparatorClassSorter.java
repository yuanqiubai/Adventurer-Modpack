package net.minecraft.crash;

import java.util.Comparator;
import net.minecraft.crash.CallableSuspiciousClasses;

class ComparatorClassSorter implements Comparator {

   // $FF: synthetic field
   final CallableSuspiciousClasses theSuspiciousClasses;

   ComparatorClassSorter(CallableSuspiciousClasses par1CallableSuspiciousClasses) {
      this.theSuspiciousClasses = par1CallableSuspiciousClasses;
   }

   public int func_85081_a(Class par1Class, Class par2Class) {
      String var3 = par1Class.getPackage() == null?"":par1Class.getPackage().getName();
      String var4 = par2Class.getPackage() == null?"":par2Class.getPackage().getName();
      return var3.compareTo(var4);
   }

   // $FF: synthetic method
   public int compare(Object par1Obj, Object par2Obj) {
      return this.func_85081_a((Class)par1Obj, (Class)par2Obj);
   }
}
