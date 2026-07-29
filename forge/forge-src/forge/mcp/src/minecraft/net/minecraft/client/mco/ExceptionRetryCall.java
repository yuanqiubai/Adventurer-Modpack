package net.minecraft.client.mco;

import net.minecraft.client.mco.ExceptionMcoService;

public class ExceptionRetryCall extends ExceptionMcoService {

   public final int field_96393_c;

   public ExceptionRetryCall(int par1) {
      super(503, "Retry operation", -1);
      this.field_96393_c = par1;
   }
}
