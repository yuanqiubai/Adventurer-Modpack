/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  acf
 *  aqz
 *  asx
 *  ata
 *  atb
 *  atc
 *  by
 *  cb
 *  cg
 *  cl
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ls
 *  nb
 *  ni
 *  nj
 *  nn
 *  of
 *  ss
 *  tg
 *  uf
 *  un
 *  yc
 *  ye
 *  zh
 */
package noppes.common.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import noppes.common.constants.EnumParticleType;
import noppes.common.constants.EnumPotionType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.data.DataStats;

public class EntityProjectile
extends nn
implements un {
    public int throwableShake = 0;
    public int arrowShake = 0;
    public boolean canBePickedUp = false;
    public boolean destroyedOnEntityHit = true;
    public ss entityitem;
    public int ticksInAir = 0;
    public float damage = 5.0f;
    public int punch = 0;
    public boolean accelerate = false;
    public boolean explosive = false;
    public int explosiveRadius = 0;
    public EnumPotionType effect = EnumPotionType.None;
    public int duration = 5;
    public int amplify = 0;
    protected boolean inGround = false;
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private int inData = 0;
    private of thrower;
    private EntityNPCInterface npc;
    private String throwerName = null;
    private int ticksInGround;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;

    public EntityProjectile(abw par1World) {
        super(par1World);
        this.a(0.25f, 0.25f);
    }

    public EntityProjectile(abw par1World, of par2EntityLiving, ye item, boolean isNPC) {
        super(par1World);
        this.thrower = par2EntityLiving;
        this.setThrownItem(item);
        this.ah.b(27, (Object)((byte)(this.getItemId() == yc.n.cv ? 1 : 0)));
        this.a(this.ah.c(23) / 10, this.ah.c(23) / 10);
        this.b(par2EntityLiving.u, par2EntityLiving.v + (double)par2EntityLiving.f(), par2EntityLiving.w, par2EntityLiving.A, par2EntityLiving.B);
        this.u -= (double)(ls.b((float)(this.A / 180.0f * (float)Math.PI)) * 0.16f);
        this.v -= 0.3;
        this.w -= (double)(ls.a((float)(this.A / 180.0f * (float)Math.PI)) * 0.16f);
        this.b(this.u, this.v, this.w);
        this.N = 0.0f;
        if (isNPC) {
            this.npc = (EntityNPCInterface)this.thrower;
            this.getStatProperties(this.npc.stats);
        }
    }

    protected void a() {
        this.ah.a(21, 5);
        this.ah.a(22, (Object)"");
        this.ah.a(23, (Object)5);
        this.ah.a(24, (Object)0);
        this.ah.a(25, (Object)10);
        this.ah.a(26, (Object)0);
        this.ah.a(27, (Object)0);
        this.ah.a(28, (Object)0);
        this.ah.a(29, (Object)0);
        this.ah.a(30, (Object)0);
    }

    @SideOnly(value=Side.CLIENT)
    public boolean a(double par1) {
        double d1 = this.E.b() * 4.0;
        return par1 < (d1 *= 64.0) * d1;
    }

    public void setThrownItem(ye item) {
        this.ah.b(21, (Object)item);
    }

    public void c(double par1, double par3, double par5, float par7, float par8) {
        float f2 = ls.a((double)(par1 * par1 + par3 * par3 + par5 * par5));
        float f3 = ls.a((double)(par1 * par1 + par5 * par5));
        float yaw = (float)(Math.atan2(par1, par5) * 180.0 / Math.PI);
        float pitch = this.hasGravity() ? par7 : (float)(Math.atan2(par3, f3) * 180.0 / Math.PI);
        this.C = this.A = yaw;
        this.D = this.B = pitch;
        this.x = ls.a((float)(yaw / 180.0f * (float)Math.PI)) * ls.b((float)(pitch / 180.0f * (float)Math.PI));
        this.z = ls.b((float)(yaw / 180.0f * (float)Math.PI)) * ls.b((float)(pitch / 180.0f * (float)Math.PI));
        this.y = ls.a((float)((pitch + 1.0f) / 180.0f * (float)Math.PI));
        this.x += this.ab.nextGaussian() * (double)0.0075f * (double)par8;
        this.z += this.ab.nextGaussian() * (double)0.0075f * (double)par8;
        this.y += this.ab.nextGaussian() * (double)0.0075f * (double)par8;
        this.x *= (double)this.getSpeed();
        this.z *= (double)this.getSpeed();
        this.y *= (double)this.getSpeed();
        this.accelerationX = par1 / (double)f2 * 0.1;
        this.accelerationY = par3 / (double)f2 * 0.1;
        this.accelerationZ = par5 / (double)f2 * 0.1;
        this.ticksInGround = 0;
    }

    public float getAngleForXYZ(double varX, double varY, double varZ, double horiDist, boolean arc) {
        float g = this.getGravityVelocity();
        float var1 = this.getSpeed() * this.getSpeed();
        double var2 = (double)g * horiDist;
        double var3 = (double)g * horiDist * horiDist + 2.0 * varY * (double)var1;
        double var4 = (double)(var1 * var1) - (double)g * var3;
        if (var4 < 0.0) {
            return 30.0f;
        }
        float var6 = arc ? var1 + ls.a((double)var4) : var1 - ls.a((double)var4);
        float var7 = (float)(Math.atan2(var6, var2) * 180.0 / Math.PI);
        return var7;
    }

    public void shoot(float speed) {
        double varX = -ls.a((float)(this.A / 180.0f * (float)Math.PI)) * ls.b((float)(this.B / 180.0f * (float)Math.PI));
        double varZ = ls.b((float)(this.A / 180.0f * (float)Math.PI)) * ls.b((float)(this.B / 180.0f * (float)Math.PI));
        double varY = -ls.a((float)(this.B / 180.0f * (float)Math.PI));
        this.c(varX, varY, varZ, -this.B, speed);
    }

    @SideOnly(value=Side.CLIENT)
    public void h(double par1, double par3, double par5) {
        this.x = par1;
        this.y = par3;
        this.z = par5;
        if (this.D == 0.0f && this.C == 0.0f) {
            float f = ls.a((double)(par1 * par1 + par5 * par5));
            this.C = this.A = (float)(Math.atan2(par1, par5) * 180.0 / Math.PI);
            this.D = this.B = (float)(Math.atan2(par3, f) * 180.0 / Math.PI);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void a(double par1, double par3, double par5, float par7, float par8, int par9) {
        if (!this.q.I || !this.inGround) {
            this.b(par1, par3, par5);
            this.b(par7, par8);
        }
    }

    protected float getGravityVelocity() {
        return 0.03f;
    }

    public void l_() {
        super.l_();
        if (this.D == 0.0f && this.C == 0.0f) {
            float i = ls.a((double)(this.x * this.x + this.z * this.z));
            this.C = this.A = (float)(Math.atan2(this.x, this.z) * 180.0 / Math.PI);
            this.D = this.B = (float)(Math.atan2(this.y, i) * 180.0 / Math.PI);
            if (this.isRotating()) {
                this.B -= 20.0f;
            }
        }
        if (this.effect == EnumPotionType.Fire && !this.inGround) {
            this.d(1);
        }
        int var18 = this.q.a(this.xTile, this.yTile, this.zTile);
        int j = this.q.h(this.xTile, this.yTile, this.zTile);
        if ((this.isArrow() || this.sticksToWalls()) && var18 > 0) {
            aqz.s[var18].a((acf)this.q, this.xTile, this.yTile, this.zTile);
            asx vec3 = aqz.s[var18].b(this.q, this.xTile, this.yTile, this.zTile);
            if (vec3 != null && vec3.a(this.q.V().a(this.u, this.v, this.w))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            if (var18 == this.inTile && j == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.x();
                }
            } else {
                this.inGround = false;
                this.x *= (double)(this.ab.nextFloat() * 0.2f);
                this.y *= (double)(this.ab.nextFloat() * 0.2f);
                this.z *= (double)(this.ab.nextFloat() * 0.2f);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            ++this.ticksInAir;
            if (this.ticksInAir == 1200) {
                this.x();
            }
            atc var19 = this.q.V().a(this.u, this.v, this.w);
            atc vec31 = this.q.V().a(this.u + this.x, this.v + this.y, this.w + this.z);
            ata movingobjectposition = this.q.a(var19, vec31, false, true);
            var19 = this.q.V().a(this.u, this.v, this.w);
            vec31 = this.q.V().a(this.u + this.x, this.v + this.y, this.w + this.z);
            if (movingobjectposition != null) {
                vec31 = this.q.V().a(movingobjectposition.f.c, movingobjectposition.f.d, movingobjectposition.f.e);
            }
            if (!this.q.I) {
                nn theEntity = null;
                List f = this.q.b((nn)this, this.E.a(this.x, this.y, this.z).b(1.0, 1.0, 1.0));
                double f2 = 0.0;
                of k = this.getThrower();
                for (int f4 = 0; f4 < f.size(); ++f4) {
                    double d1;
                    float f1;
                    asx axisalignedbb;
                    ata movingobjectposition1;
                    nn entity1 = (nn)f.get(f4);
                    if (!entity1.L() || entity1 == this.thrower && this.ticksInAir < 5 || (movingobjectposition1 = (axisalignedbb = entity1.E.b((double)(f1 = 0.3f), (double)f1, (double)f1)).a(var19, vec31)) == null || !((d1 = var19.d(movingobjectposition1.f)) < f2) && f2 != 0.0) continue;
                    theEntity = entity1;
                    f2 = d1;
                }
                if (theEntity != null) {
                    movingobjectposition = new ata(theEntity);
                }
                if (movingobjectposition != null && movingobjectposition.g != null && movingobjectposition.g instanceof uf) {
                    uf var25 = (uf)movingobjectposition.g;
                    if (this.thrower instanceof EntityNPCInterface && this.npc.getFaction().isFriendlyToPlayer(var25)) {
                        movingobjectposition = null;
                    }
                }
            }
            if (movingobjectposition != null) {
                if (movingobjectposition.a == atb.a && this.q.a(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d) == aqz.bj.cF) {
                    this.ab();
                } else {
                    this.ah.b(29, (Object)0);
                    this.onImpact(movingobjectposition);
                }
            }
            this.u += this.x;
            this.v += this.y;
            this.w += this.z;
            float var20 = ls.a((double)(this.x * this.x + this.z * this.z));
            this.A = (float)(Math.atan2(this.x, this.z) * 180.0 / Math.PI);
            this.B = (float)(Math.atan2(this.y, var20) * 180.0 / Math.PI);
            while (this.B - this.D < -180.0f) {
                this.D -= 360.0f;
            }
            while (this.B - this.D >= 180.0f) {
                this.D += 360.0f;
            }
            while (this.A - this.C < -180.0f) {
                this.C -= 360.0f;
            }
            while (this.A - this.C >= 180.0f) {
                this.C += 360.0f;
            }
            float var21 = this.isArrow() ? 0.0f : 225.0f;
            this.B = this.D + (this.B - this.D) + var21 * 0.2f;
            this.A = this.C + (this.A - this.C) * 0.2f;
            if (this.isRotating()) {
                int var22 = this.isBlock() ? 10 : 20;
                this.B -= (float)(this.ticksInAir * var22) * this.getSpeed();
            }
            float var23 = this.getMotionFactor();
            float f3 = this.getGravityVelocity();
            if (this.H()) {
                for (int var24 = 0; var24 < 4; ++var24) {
                    float var26 = 0.25f;
                    this.q.a("bubble", this.u - this.x * (double)var26, this.v - this.y * (double)var26, this.w - this.z * (double)var26, this.x, this.y, this.z);
                }
                var23 = 0.8f;
            }
            this.x *= (double)var23;
            this.y *= (double)var23;
            this.z *= (double)var23;
            if (this.hasGravity()) {
                this.y -= (double)f3;
            }
            if (this.accelerate) {
                this.x += this.accelerationX;
                this.y += this.accelerationY;
                this.z += this.accelerationZ;
            }
            if (!this.ah.e(22).equals("")) {
                this.q.a(this.ah.e(22), this.u, this.v, this.w, 0.0, 0.0, 0.0);
            }
            this.b(this.u, this.v, this.w);
            this.D();
        }
    }

    public boolean isBlock() {
        ye item = this.getItemDisplay();
        return item != null && item.b() instanceof zh;
    }

    private int getItemId() {
        ye item = this.getItemDisplay();
        return item == null ? 0 : item.d;
    }

    protected float getMotionFactor() {
        return this.accelerate ? 0.95f : 1.0f;
    }

    protected void onImpact(ata movingobjectposition) {
        int var12;
        int var13;
        float axisalignedbb;
        if (movingobjectposition.g != null) {
            axisalignedbb = this.damage;
            if (axisalignedbb == 0.0f) {
                axisalignedbb = 0.001f;
            }
            if (movingobjectposition.g.a(nb.a((nn)this, (nn)this.getThrower()), axisalignedbb)) {
                float var14;
                if (movingobjectposition.g instanceof of && (this.isArrow() || this.sticksToWalls())) {
                    of list1 = (of)movingobjectposition.g;
                    if (!this.q.I) {
                        list1.m(list1.aU() + 1);
                    }
                    if (this.destroyedOnEntityHit && !(movingobjectposition.g instanceof tg)) {
                        this.x();
                    }
                }
                if (this.isBlock()) {
                    this.q.e(2001, (int)movingobjectposition.g.u, (int)movingobjectposition.g.v, (int)movingobjectposition.g.w, this.getItemId());
                } else if (!this.isArrow() && !this.sticksToWalls()) {
                    for (var13 = 0; var13 < 8; ++var13) {
                        this.q.a("iconcrack_" + this.getItemId(), this.u, this.v, this.w, this.ab.nextGaussian() * 0.15, this.ab.nextGaussian() * 0.2, this.ab.nextGaussian() * 0.15);
                    }
                }
                if (this.punch > 0 && (var14 = ls.a((double)(this.x * this.x + this.z * this.z))) > 0.0f) {
                    movingobjectposition.g.g(this.x * (double)this.punch * (double)0.6f / (double)var14, 0.1, this.z * (double)this.punch * (double)0.6f / (double)var14);
                }
                if (this.effect != EnumPotionType.None && movingobjectposition.g instanceof of) {
                    if (this.effect != EnumPotionType.Fire) {
                        var13 = this.getPotionEffect(this.effect);
                        ((of)movingobjectposition.g).c(new nj(var13, this.duration * 20, this.amplify));
                    } else {
                        movingobjectposition.g.d(this.duration);
                    }
                }
            } else if (this.hasGravity() && (this.isArrow() || this.sticksToWalls())) {
                this.x *= (double)-0.1f;
                this.y *= (double)-0.1f;
                this.z *= (double)-0.1f;
                this.A += 180.0f;
                this.C += 180.0f;
                this.ticksInAir = 0;
            }
        } else if (!this.isArrow() && !this.sticksToWalls()) {
            if (this.isBlock()) {
                this.q.e(2001, movingobjectposition.b, movingobjectposition.c, movingobjectposition.d, this.getItemId());
            } else {
                for (var12 = 0; var12 < 8; ++var12) {
                    this.q.a("iconcrack_" + this.getItemId(), this.u, this.v, this.w, this.ab.nextGaussian() * 0.15, this.ab.nextGaussian() * 0.2, this.ab.nextGaussian() * 0.15);
                }
            }
        } else {
            this.xTile = movingobjectposition.b;
            this.yTile = movingobjectposition.c;
            this.zTile = movingobjectposition.d;
            this.inTile = this.q.a(this.xTile, this.yTile, this.zTile);
            this.inData = this.q.h(this.xTile, this.yTile, this.zTile);
            this.x = (float)(movingobjectposition.f.c - this.u);
            this.y = (float)(movingobjectposition.f.d - this.v);
            this.z = (float)(movingobjectposition.f.e - this.w);
            axisalignedbb = ls.a((double)(this.x * this.x + this.y * this.y + this.z * this.z));
            this.u -= this.x / (double)axisalignedbb * (double)0.05f;
            this.v -= this.y / (double)axisalignedbb * (double)0.05f;
            this.w -= this.z / (double)axisalignedbb * (double)0.05f;
            this.inGround = true;
            if (this.isArrow()) {
                this.a("random.bowhit", 1.0f, 1.2f / (this.ab.nextFloat() * 0.2f + 0.9f));
            } else {
                this.a("random.break", 1.0f, 1.2f / (this.ab.nextFloat() * 0.2f + 0.9f));
            }
            this.arrowShake = 7;
            if (!this.hasGravity()) {
                this.ah.b(26, (Object)1);
            }
            if (this.inTile != 0) {
                aqz.s[this.inTile].a(this.q, this.xTile, this.yTile, this.zTile, (nn)this);
            }
        }
        if (this.explosive) {
            if (this.explosiveRadius == 0 && this.effect != EnumPotionType.None) {
                if (this.effect == EnumPotionType.Fire) {
                    var12 = movingobjectposition.b;
                    var13 = movingobjectposition.c;
                    int iterator = movingobjectposition.d;
                    switch (movingobjectposition.e) {
                        case 0: {
                            --var13;
                            break;
                        }
                        case 1: {
                            ++var13;
                            break;
                        }
                        case 2: {
                            --iterator;
                            break;
                        }
                        case 3: {
                            ++iterator;
                            break;
                        }
                        case 4: {
                            --var12;
                            break;
                        }
                        case 5: {
                            ++var12;
                        }
                    }
                    if (this.q.c(var12, var13, iterator)) {
                        this.q.c(var12, var13, iterator, aqz.aw.cF);
                    }
                } else {
                    asx var15 = this.E.b(4.0, 2.0, 4.0);
                    List var17 = this.q.a(of.class, var15);
                    if (var17 != null && !var17.isEmpty()) {
                        for (of entitylivingbase : var17) {
                            int i;
                            double d0 = this.e((nn)entitylivingbase);
                            if (!(d0 < 16.0)) continue;
                            double d1 = 1.0 - Math.sqrt(d0) / 4.0;
                            if (entitylivingbase == movingobjectposition.g) {
                                d1 = 1.0;
                            }
                            if (ni.a[i = this.getPotionEffect(this.effect)].b()) {
                                ni.a[i].a(this.getThrower(), entitylivingbase, this.amplify, d1);
                                continue;
                            }
                            int j = (int)(d1 * (double)this.duration + 0.5);
                            if (j <= 20) continue;
                            entitylivingbase.c(new nj(i, j, this.amplify));
                        }
                    }
                    this.q.e(2002, (int)Math.round(this.u), (int)Math.round(this.v), (int)Math.round(this.w), this.getPotionColor(this.effect));
                }
            } else {
                this.q.a(null, this.u, this.v, this.w, (float)this.explosiveRadius, this.effect == EnumPotionType.Fire, this.q.O().b("mobGriefing"));
                if (this.explosiveRadius != 0 && (this.isArrow() || this.sticksToWalls())) {
                    this.x();
                }
            }
        }
        if (!(this.q.I || this.isArrow() || this.sticksToWalls())) {
            this.x();
        }
    }

    public void b(by par1NBTTagCompound) {
        par1NBTTagCompound.a("xTile", (short)this.xTile);
        par1NBTTagCompound.a("yTile", (short)this.yTile);
        par1NBTTagCompound.a("zTile", (short)this.zTile);
        par1NBTTagCompound.a("inTile", (byte)this.inTile);
        par1NBTTagCompound.a("inData", (byte)this.inData);
        par1NBTTagCompound.a("shake", (byte)this.throwableShake);
        par1NBTTagCompound.a("inGround", (byte)(this.inGround ? 1 : 0));
        par1NBTTagCompound.a("isArrow", (byte)(this.isArrow() ? 1 : 0));
        par1NBTTagCompound.a("direction", (cl)this.a(new double[]{this.x, this.y, this.z}));
        par1NBTTagCompound.a("canBePickedUp", this.canBePickedUp);
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof uf) {
            this.throwerName = this.thrower.an();
        }
        par1NBTTagCompound.a("ownerName", this.throwerName == null ? "" : this.throwerName);
        if (this.getItemDisplay() != null) {
            par1NBTTagCompound.a("Item", this.getItemDisplay().b(new by()));
        }
        par1NBTTagCompound.a("damagev2", this.damage);
        par1NBTTagCompound.a("punch", this.punch);
        par1NBTTagCompound.a("size", this.ah.c(23));
        par1NBTTagCompound.a("velocity", this.ah.c(25));
        par1NBTTagCompound.a("explosiveRadius", this.explosiveRadius);
        par1NBTTagCompound.a("effectDuration", this.duration);
        par1NBTTagCompound.a("gravity", this.hasGravity());
        par1NBTTagCompound.a("accelerate", this.accelerate);
        par1NBTTagCompound.a("glows", this.ah.a(24));
        par1NBTTagCompound.a("explosive", this.explosive);
        par1NBTTagCompound.a("PotionEffect", this.effect.ordinal());
        par1NBTTagCompound.a("trail", this.ah.e(22));
        par1NBTTagCompound.a("Render3D", this.ah.a(28));
        par1NBTTagCompound.a("Spins", this.ah.a(29));
        par1NBTTagCompound.a("Sticks", this.ah.a(30));
    }

    public void a(by par1NBTTagCompound) {
        by var21;
        ye item;
        this.xTile = par1NBTTagCompound.d("xTile");
        this.yTile = par1NBTTagCompound.d("yTile");
        this.zTile = par1NBTTagCompound.d("zTile");
        this.inTile = par1NBTTagCompound.c("inTile") & 0xFF;
        this.inData = par1NBTTagCompound.c("inData") & 0xFF;
        this.throwableShake = par1NBTTagCompound.c("shake") & 0xFF;
        this.inGround = par1NBTTagCompound.c("inGround") == 1;
        this.ah.b(27, (Object)par1NBTTagCompound.c("isArrow"));
        this.throwerName = par1NBTTagCompound.i("ownerName");
        this.canBePickedUp = par1NBTTagCompound.n("canBePickedUp");
        this.damage = par1NBTTagCompound.g("damagev2");
        this.punch = par1NBTTagCompound.e("punch");
        this.explosiveRadius = par1NBTTagCompound.e("explosiveRadius");
        this.duration = par1NBTTagCompound.e("effectDuration");
        this.accelerate = par1NBTTagCompound.n("accelerate");
        this.explosive = par1NBTTagCompound.n("explosive");
        this.effect = EnumPotionType.values()[par1NBTTagCompound.e("PotionEffect") % EnumPotionType.values().length];
        this.ah.b(22, (Object)par1NBTTagCompound.i("trail"));
        this.ah.b(23, (Object)par1NBTTagCompound.e("size"));
        this.ah.b(24, (Object)((byte)(par1NBTTagCompound.n("glows") ? 1 : 0)));
        this.ah.b(25, (Object)par1NBTTagCompound.e("velocity"));
        this.ah.b(26, (Object)((byte)(par1NBTTagCompound.n("gravity") ? 1 : 0)));
        this.ah.b(28, (Object)((byte)(par1NBTTagCompound.n("Render3D") ? 1 : 0)));
        this.ah.b(29, (Object)((byte)(par1NBTTagCompound.n("Spins") ? 1 : 0)));
        this.ah.b(30, (Object)((byte)(par1NBTTagCompound.n("Sticks") ? 1 : 0)));
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
        if (par1NBTTagCompound.b("direction")) {
            cg var2 = par1NBTTagCompound.m("direction");
            this.x = ((cb)var2.b((int)0)).a;
            this.y = ((cb)var2.b((int)1)).a;
            this.z = ((cb)var2.b((int)2)).a;
        }
        if ((item = ye.a((by)(var21 = par1NBTTagCompound.l("Item")))) == null) {
            this.x();
        } else {
            this.ah.b(21, (Object)item);
        }
    }

    public of getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.q.a(this.throwerName);
        }
        return this.thrower;
    }

    private int getPotionEffect(EnumPotionType p) {
        switch (p) {
            case Poison: {
                return ni.u.H;
            }
            case Hunger: {
                return ni.s.H;
            }
            case Weakness: {
                return ni.t.H;
            }
            case Slowness: {
                return ni.d.H;
            }
            case Nausea: {
                return ni.k.H;
            }
            case Blindness: {
                return ni.q.H;
            }
            case Wither: {
                return ni.v.H;
            }
        }
        return 0;
    }

    private int getPotionColor(EnumPotionType p) {
        switch (p) {
            case Poison: {
                return 32660;
            }
            case Hunger: {
                return 32660;
            }
            case Weakness: {
                return 32696;
            }
            case Slowness: {
                return 32698;
            }
            case Nausea: {
                return 32732;
            }
            case Blindness: {
                return ni.q.H;
            }
            case Wither: {
                return 32732;
            }
        }
        return 0;
    }

    public void getStatProperties(DataStats stats) {
        this.damage = stats.pDamage;
        this.punch = stats.pImpact;
        this.accelerate = stats.pXlr8;
        this.explosive = stats.pExplode;
        this.explosiveRadius = stats.pArea;
        this.effect = stats.pEffect;
        this.duration = stats.pDur;
        this.amplify = stats.pEffAmp;
        this.setParticleEffect(stats.pTrail);
        this.ah.b(23, (Object)stats.pSize);
        this.ah.b(24, (Object)((byte)(stats.pGlows ? 1 : 0)));
        this.setSpeed(stats.pSpeed);
        this.setHasGravity(stats.pPhysics);
        this.setIs3D(stats.pRender3D);
        this.setRotating(stats.pSpin);
        this.setStickInWall(stats.pStick);
    }

    public void setParticleEffect(EnumParticleType type) {
        this.ah.b(22, (Object)type.particleName);
    }

    public void setHasGravity(boolean bo) {
        this.ah.b(26, (Object)((byte)(bo ? 1 : 0)));
    }

    public void setIs3D(boolean bo) {
        this.ah.b(28, (Object)((byte)(bo ? 1 : 0)));
    }

    public void setStickInWall(boolean bo) {
        this.ah.b(30, (Object)((byte)(bo ? 1 : 0)));
    }

    public ye getItemDisplay() {
        return this.ah.f(21);
    }

    public float d(float par1) {
        return this.ah.a(24) == 1 ? 1.0f : super.d(par1);
    }

    @SideOnly(value=Side.CLIENT)
    public int c(float par1) {
        return this.ah.a(24) == 1 ? 0xF000F0 : super.c(par1);
    }

    public boolean hasGravity() {
        return this.ah.a(26) == 1;
    }

    public float getSpeed() {
        return (float)this.ah.c(25) / 10.0f;
    }

    public void setSpeed(int speed) {
        this.ah.b(25, (Object)speed);
    }

    public boolean isArrow() {
        return this.ah.a(27) == 1;
    }

    public boolean isRotating() {
        return this.ah.a(29) == 1;
    }

    public void setRotating(boolean bo) {
        this.ah.b(29, (Object)((byte)(bo ? 1 : 0)));
    }

    public boolean glows() {
        return this.ah.a(24) == 1;
    }

    public boolean is3D() {
        return this.ah.a(28) == 1 || this.isBlock();
    }

    public boolean sticksToWalls() {
        return this.is3D() && this.ah.a(30) == 1;
    }

    public void b_(uf par1EntityPlayer) {
        if (!this.q.I && this.canBePickedUp && this.inGround && this.arrowShake <= 0 && par1EntityPlayer.bn.a(this.getItemDisplay())) {
            this.inGround = false;
            this.a("random.pop", 0.2f, ((this.ab.nextFloat() - this.ab.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            par1EntityPlayer.a((nn)this, 1);
            this.x();
        }
    }

    protected boolean e_() {
        return false;
    }
}

