package net.minecraft.stats;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;

final class StatTypeSimple implements IStatType {

   public String format(int par1) {
      return StatBase.getNumberFormat().format((long)par1);
   }
}
