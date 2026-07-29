package net.minecraft.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

final class DispenserBehaviorArrow extends BehaviorProjectileDispense {

   protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
      EntityArrow var3 = new EntityArrow(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
      var3.canBePickedUp = 1;
      return var3;
   }
}
