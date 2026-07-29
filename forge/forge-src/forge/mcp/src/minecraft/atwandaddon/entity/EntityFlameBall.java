package atwandaddon.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFlameBall extends EntityThrowable {

    private float damage = 8.0F;

    public EntityFlameBall(World _world) {
        super(_world);
    }

    public EntityFlameBall(World _world, EntityLivingBase _thrower) {
        super(_world, _thrower);
    }

    public EntityFlameBall(World _world, double _x, double _y, double _z) {
        super(_world, _x, _y, _z);
    }

    public void setDamage(float _damage) {
        this.damage = _damage;
    }

    @Override
    protected void onImpact(MovingObjectPosition _pos) {
        if (!this.worldObj.isRemote) {
            if (_pos.entityHit != null) {
                _pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.damage);
                _pos.entityHit.setFire(4);
            }

            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, true, true);
            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.01F;
    }
}
