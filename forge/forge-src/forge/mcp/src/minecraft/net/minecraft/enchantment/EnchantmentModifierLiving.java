package net.minecraft.enchantment;

import net.minecraft.enchantment.Empty3;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.IEnchantmentModifier;
import net.minecraft.entity.EntityLivingBase;

final class EnchantmentModifierLiving implements IEnchantmentModifier {

   public float livingModifier;
   public EntityLivingBase entityLiving;

   private EnchantmentModifierLiving() {}

   public void calculateModifier(Enchantment par1Enchantment, int par2) {
      this.livingModifier += par1Enchantment.calcModifierLiving(par2, this.entityLiving);
   }

   // $FF: synthetic method
   EnchantmentModifierLiving(Empty3 par1Empty3) {
      this();
   }
}
