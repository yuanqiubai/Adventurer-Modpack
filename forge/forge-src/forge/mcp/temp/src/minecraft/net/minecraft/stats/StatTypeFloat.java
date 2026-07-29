package net.minecraft.stats;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;

final class StatTypeFloat implements IStatType {

   public String func_75843_a(int p_75843_1_) {
      return StatBase.func_75969_k().format((double)p_75843_1_ * 0.1D);
   }
}
