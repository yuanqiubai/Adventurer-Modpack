package net.minecraft.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.world.World;

final class DispenserBehaviorSnowball extends BehaviorProjectileDispense {

   protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
      return new EntitySnowball(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
   }
}
