package net.minecraft.stats;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;

final class StatTypeFloat implements IStatType {

   public String format(int par1) {
      return StatBase.getDecimalFormat().format((double)par1 * 0.1D);
   }
}
