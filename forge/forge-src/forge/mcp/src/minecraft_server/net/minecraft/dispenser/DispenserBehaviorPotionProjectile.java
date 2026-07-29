package net.minecraft.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.DispenserBehaviorPotion;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

class DispenserBehaviorPotionProjectile extends BehaviorProjectileDispense {

   // $FF: synthetic field
   final ItemStack potionItemStack;
   // $FF: synthetic field
   final DispenserBehaviorPotion dispenserPotionBehavior;

   DispenserBehaviorPotionProjectile(DispenserBehaviorPotion par1DispenserBehaviorPotion, ItemStack par2ItemStack) {
      this.dispenserPotionBehavior = par1DispenserBehaviorPotion;
      this.potionItemStack = par2ItemStack;
   }

   protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
      return new EntityPotion(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ(), this.potionItemStack.copy());
   }

   protected float func_82498_a() {
      return super.func_82498_a() * 0.5F;
   }

   protected float func_82500_b() {
      return super.func_82500_b() * 1.25F;
   }
}
