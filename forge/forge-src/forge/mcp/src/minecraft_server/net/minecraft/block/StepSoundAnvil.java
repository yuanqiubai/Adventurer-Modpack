package net.minecraft.block;

import net.minecraft.block.StepSound;

final class StepSoundAnvil extends StepSound {

   StepSoundAnvil(String par1Str, float par2, float par3) {
      super(par1Str, par2, par3);
   }

   public String getBreakSound() {
      return "dig.stone";
   }

   public String getPlaceSound() {
      return "random.anvil_land";
   }
}
