package net.minecraft.enchantment;

import net.minecraft.enchantment.Empty3;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.IEnchantmentModifier;
import net.minecraft.util.DamageSource;

final class EnchantmentModifierDamage implements IEnchantmentModifier {

   public int damageModifier;
   public DamageSource source;

   private EnchantmentModifierDamage() {}

   public void calculateModifier(Enchantment par1Enchantment, int par2) {
      this.damageModifier += par1Enchantment.calcModifierDamage(par2, this.source);
   }

   // $FF: synthetic method
   EnchantmentModifierDamage(Empty3 par1Empty3) {
      this();
   }
}
