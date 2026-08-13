/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  oj
 */
package noppes.common.entity.data;

import noppes.common.constants.EnumParticleType;
import noppes.common.constants.EnumPotionType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.data.Resistances;

public class DataStats {
    public int attackStrength = 5;
    public int attackSpeed = 20;
    public int attackRange = 2;
    public int knockback = 0;
    public int fireDelay = 20;
    public int delayVariance = 40;
    public int rangedRange = 15;
    public int fireRate = 5;
    public int burstCount = 1;
    public int accuracy = 90;
    public int moveSpeed = 6;
    public int aggroRange = 16;
    public EnumPotionType potionType = EnumPotionType.None;
    public int potionDuration = 5;
    public int potionAmp = 0;
    public int maxHealth = 20;
    public int respawnTime = 20;
    public int spawnCycle = 0;
    public boolean hideKilledBody = false;
    public Resistances resistances = new Resistances();
    public boolean canDrown = true;
    public boolean burnInSun = false;
    public boolean noFallDamage = false;
    public boolean healthRegen = true;
    public int pDamage = 4;
    public int pImpact = 0;
    public int pSize = 5;
    public int pSpeed = 10;
    public int pArea = 0;
    public int pDur = 5;
    public boolean pPhysics = true;
    public boolean pXlr8 = false;
    public boolean pGlows = false;
    public boolean pExplode = false;
    public boolean pRender3D = false;
    public boolean pSpin = false;
    public boolean pStick = false;
    public EnumPotionType pEffect = EnumPotionType.None;
    public EnumParticleType pTrail = EnumParticleType.None;
    public int pEffAmp = 0;
    public String fireSound = "random.bow";
    public oj creatureType = oj.a;
    public boolean immuneToFire = false;
    private final EntityNPCInterface npc;

    public DataStats(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public by writeToNBT(by compound) {
        compound.a("Resistances", this.resistances.writeToNBT());
        compound.a("MaxHealth", this.maxHealth);
        compound.a("AggroRange", this.aggroRange);
        compound.a("HideBodyWhenKilled", this.hideKilledBody);
        compound.a("RespawnTime", this.respawnTime);
        compound.a("SpawnCycle", this.spawnCycle);
        compound.a("CreatureType", this.creatureType.ordinal());
        compound.a("MoveSpeed", this.moveSpeed);
        compound.a("HealthRegen", this.healthRegen);
        compound.a("AttackStrenght", this.attackStrength);
        compound.a("AttackRange", this.attackRange);
        compound.a("AttackSpeed", this.attackSpeed);
        compound.a("KnockBack", this.knockback);
        compound.a("PotionEffect", this.potionType.ordinal());
        compound.a("PotionDuration", this.potionDuration);
        compound.a("PotionAmp", this.potionAmp);
        compound.a("MaxFiringRange", this.rangedRange);
        compound.a("FireRate", this.fireRate);
        compound.a("FiringDelay", this.fireDelay);
        compound.a("DelayVariance", this.delayVariance);
        compound.a("BurstCount", this.burstCount);
        compound.a("Accuracy", this.accuracy);
        compound.a("pDamage", this.pDamage);
        compound.a("pImpact", this.pImpact);
        compound.a("pSize", this.pSize);
        compound.a("pSpeed", this.pSpeed);
        compound.a("pArea", this.pArea);
        compound.a("pDur", this.pDur);
        compound.a("pPhysics", this.pPhysics);
        compound.a("pXlr8", this.pXlr8);
        compound.a("pGlows", this.pGlows);
        compound.a("pExplode", this.pExplode);
        compound.a("pRender3D", this.pRender3D);
        compound.a("pSpin", this.pSpin);
        compound.a("pStick", this.pStick);
        compound.a("pEffect", this.pEffect.ordinal());
        compound.a("pTrail", this.pTrail.ordinal());
        compound.a("pEffAmp", this.pEffAmp);
        compound.a("FiringSound", this.fireSound);
        compound.a("ImmuneToFire", this.immuneToFire);
        compound.a("CanDrown", this.canDrown);
        compound.a("BurnInSun", this.burnInSun);
        compound.a("NoFallDamage", this.noFallDamage);
        return compound;
    }

    public void readToNBT(by compound) {
        this.resistances.readToNBT(compound.l("Resistances"));
        this.maxHealth = compound.e("MaxHealth");
        this.hideKilledBody = compound.n("HideBodyWhenKilled");
        this.aggroRange = compound.e("AggroRange");
        this.respawnTime = compound.e("RespawnTime");
        this.spawnCycle = compound.e("SpawnCycle");
        this.creatureType = oj.values()[compound.e("CreatureType") % EnumPotionType.values().length];
        this.moveSpeed = compound.e("MoveSpeed");
        this.healthRegen = compound.n("HealthRegen");
        this.attackStrength = compound.e("AttackStrenght");
        this.attackSpeed = compound.e("AttackSpeed");
        this.attackRange = compound.e("AttackRange");
        this.knockback = compound.e("KnockBack");
        this.potionType = EnumPotionType.values()[compound.e("PotionEffect") % EnumPotionType.values().length];
        this.potionDuration = compound.e("PotionDuration");
        this.potionAmp = compound.e("PotionAmp");
        this.rangedRange = compound.e("MaxFiringRange");
        this.fireRate = compound.e("FireRate");
        this.fireDelay = compound.e("FiringDelay");
        this.delayVariance = compound.e("DelayVariance");
        this.burstCount = compound.e("BurstCount");
        this.accuracy = compound.e("Accuracy");
        this.pDamage = compound.e("pDamage");
        this.pImpact = compound.e("pImpact");
        this.pSize = compound.e("pSize");
        this.pSpeed = compound.e("pSpeed");
        this.pArea = compound.e("pArea");
        this.pDur = compound.e("pDur");
        this.pPhysics = compound.n("pPhysics");
        this.pXlr8 = compound.n("pXlr8");
        this.pGlows = compound.n("pGlows");
        this.pExplode = compound.n("pExplode");
        this.pRender3D = compound.n("pRender3D");
        this.pSpin = compound.n("pSpin");
        this.pStick = compound.n("pStick");
        this.pEffect = EnumPotionType.values()[compound.e("pEffect") % EnumPotionType.values().length];
        this.pTrail = EnumParticleType.values()[compound.e("pTrail") % EnumParticleType.values().length];
        this.pEffAmp = compound.e("pEffAmp");
        this.fireSound = compound.i("FiringSound");
        this.immuneToFire = compound.n("ImmuneToFire");
        this.canDrown = compound.n("CanDrown");
        this.burnInSun = compound.n("BurnInSun");
        this.noFallDamage = compound.n("NoFallDamage");
        this.npc.setImmuneToFire(this.immuneToFire);
        this.npc.updateHitbox();
    }
}

