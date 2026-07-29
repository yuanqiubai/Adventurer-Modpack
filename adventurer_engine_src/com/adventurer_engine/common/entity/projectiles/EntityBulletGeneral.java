/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  acf
 *  aqz
 *  asx
 *  ata
 *  atc
 *  by
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ls
 *  nb
 *  nc
 *  nn
 *  of
 *  uf
 *  un
 */
package com.adventurer_engine.common.entity.projectiles;

import com.adventurer_engine.common.PlayerTools;
import com.adventurer_engine.common.gadgets.DamagePacket;
import com.adventurer_engine.common.items.blank.ranged_weapon.BasicAmmo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

public abstract class EntityBulletGeneral
extends nn
implements un {
    private String hitSound = null;
    private int ticksInGround;
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private boolean inGround;
    private int inTile;
    private boolean gravity = true;
    public boolean noeff = false;
    private boolean penetrate = false;
    private double iceDamage = 0.0;
    private int inData;
    private int ticksInAir;
    private nn shootingEntity;
    private double damage = 2.0;
    private BasicAmmo ammo;
    private DamagePacket dmgPack;
    private float knockbackStrength = 0.0f;
    private double maxRange;
    public double beginX;
    public double beginZ;
    private static final float sizeX = 0.4f;
    private static final float sizeY = 0.4f;

    public void setNoeff(boolean noeff) {
        this.noeff = noeff;
    }

    public void setPenetrate(boolean penetrate) {
        this.penetrate = penetrate;
    }

    public void setIceDamage(double iceDamage) {
        this.iceDamage = iceDamage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public EntityBulletGeneral(abw world) {
        super(world);
        this.l = 10.0;
        this.a(0.4f, 0.4f);
    }

    public EntityBulletGeneral(abw world, double x, double y, double z2) {
        super(world);
        this.l = 10.0;
        this.a(0.4f, 0.4f);
        this.b(x, y, z2);
        this.N = 0.0f;
    }

    public EntityBulletGeneral(abw par1World, of par2EntityLivingBase, float speed, float accuracy) {
        super(par1World);
        this.l = 10.0;
        this.shootingEntity = par2EntityLivingBase;
        this.a(0.4f, 0.4f);
        this.b(par2EntityLivingBase.u, par2EntityLivingBase.v + (double)par2EntityLivingBase.f(), par2EntityLivingBase.w, par2EntityLivingBase.A, par2EntityLivingBase.B);
        this.u -= (double)(ls.b((float)(this.A / 180.0f * (float)Math.PI)) * 0.16f);
        if (this.gravity) {
            this.v -= (double)0.1f;
        }
        this.w -= (double)(ls.a((float)(this.A / 180.0f * (float)Math.PI)) * 0.16f);
        this.b(this.u, this.v, this.w);
        this.beginX = this.u;
        this.beginZ = this.w;
        this.N = 0.0f;
        this.x = -ls.a((float)(this.A / 180.0f * (float)Math.PI)) * ls.b((float)(this.B / 180.0f * (float)Math.PI));
        this.z = ls.b((float)(this.A / 180.0f * (float)Math.PI)) * ls.b((float)(this.B / 180.0f * (float)Math.PI));
        this.y = -ls.a((float)(this.B / 180.0f * (float)Math.PI));
        if ((double)accuracy < 1.0) {
            double[] nv = PlayerTools.disturbVector(this.x, this.y, this.z, accuracy);
            this.x = nv[0];
            this.y = nv[1];
            this.z = nv[2];
        }
        this.c(this.x, this.y, this.z, speed * 1.5f, 1.0f);
    }

    public void l_() {
        int currentBlockID;
        double deltaZ;
        double deltaX;
        super.l_();
        if (this.maxRange != 0.0 && (deltaX = this.u - this.beginX) * deltaX + (deltaZ = this.w - this.beginZ) * deltaZ > this.maxRange) {
            this.x();
        }
        if (this.D == 0.0f && this.C == 0.0f) {
            float speed_vec_len = ls.a((double)(this.x * this.x + this.z * this.z));
            this.C = this.A = (float)(Math.atan2(this.x, this.z) * 180.0 / Math.PI);
            this.D = this.B = (float)(Math.atan2(this.y, speed_vec_len) * 180.0 / Math.PI);
        }
        if ((currentBlockID = this.q.a(this.xTile, this.yTile, this.zTile)) > 0) {
            aqz.s[currentBlockID].a((acf)this.q, this.xTile, this.yTile, this.zTile);
            asx axisalignedbb = aqz.s[currentBlockID].b(this.q, this.xTile, this.yTile, this.zTile);
            if (axisalignedbb != null && axisalignedbb.a(this.q.V().a(this.u, this.v, this.w))) {
                this.inGround = true;
            }
        }
        if (this.inGround) {
            int currentBlockId = this.q.a(this.xTile, this.yTile, this.zTile);
            int currentBlockData = this.q.h(this.xTile, this.yTile, this.zTile);
            if (currentBlockId == this.inTile && currentBlockData == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround >= 10) {
                    this.x();
                }
            } else {
                this.inGround = false;
                this.x *= (double)(this.ab.nextFloat() * 0.2f);
                if (this.gravity) {
                    this.y *= (double)(this.ab.nextFloat() * 0.2f);
                }
                this.z *= (double)(this.ab.nextFloat() * 0.2f);
                this.ticksInGround = 0;
                this.ticksInAir = this.penetrate ? 5 : 0;
            }
        } else {
            float f1;
            ++this.ticksInAir;
            atc vec3 = this.q.V().a(this.u, this.v, this.w);
            atc vec31 = this.q.V().a(this.u + this.x, this.v + this.y, this.w + this.z);
            ata bullet = this.q.a(vec3, vec31, false, true);
            vec3 = this.q.V().a(this.u, this.v, this.w);
            vec31 = this.q.V().a(this.u + this.x, this.v + this.y, this.w + this.z);
            if (bullet != null) {
                vec31 = this.q.V().a(bullet.f.c, bullet.f.d, bullet.f.e);
            }
            List aabbEntityList = this.q.b((nn)this, this.E.a(this.x, this.y, this.z).b(0.2, 0.2, 0.2));
            double lastDistance = 0.0;
            nn wounded = null;
            for (nn tmp : aabbEntityList) {
                if (!tmp.L() || tmp == this.shootingEntity && this.ticksInAir < 5) continue;
                f1 = 0.3f;
                asx aabb = tmp.E.b((double)f1, (double)f1, (double)f1);
                ata position = aabb.a(vec3, vec31);
                if (position != null) {
                    double distance = vec3.d(position.f);
                    if (!(distance < lastDistance) && lastDistance != 0.0) continue;
                    wounded = tmp;
                    lastDistance = distance;
                    continue;
                }
                if (!this.penetrate) continue;
                wounded = tmp instanceof uf ? null : tmp;
            }
            if (wounded != null) {
                bullet = new ata(wounded);
            }
            if (bullet != null && bullet.g instanceof uf) {
                uf entityplayer = (uf)bullet.g;
                if (entityplayer.bG.a || this.shootingEntity instanceof uf && !((uf)this.shootingEntity).a(entityplayer)) {
                    bullet = null;
                }
            }
            if (bullet != null) {
                if (bullet.g != null) {
                    nb dmg = this.shootingEntity == null ? nb.a((nn)this, (nn)this) : nb.a((nn)this, (nn)this.shootingEntity);
                    if (this.af()) {
                        bullet.g.d(5);
                    }
                    boolean flag = false;
                    if (bullet.g instanceof of && this.shootingEntity != null) {
                        if (this.dmgPack != null) {
                            flag = this.dmgPack.causeDamage((uf)this.shootingEntity, (of)bullet.g, (float)this.damage, (float)this.iceDamage, dmg);
                        } else {
                            if (this.damage > 0.0) {
                                flag = bullet.g.a(dmg, (float)this.damage);
                                bullet.g.af = 0;
                            }
                            if (this.iceDamage > 0.0) {
                                flag = bullet.g.a((nb)new nc("ice_damage", this.shootingEntity), (float)this.iceDamage) || flag;
                                bullet.g.af = 0;
                            }
                        }
                    }
                    if (flag) {
                        if (bullet.g instanceof of) {
                            float f3;
                            of living = (of)bullet.g;
                            if (!this.q.I && !this.penetrate) {
                                living.m(living.aU() + 1);
                            }
                            if (this.knockbackStrength > 0.0f && !this.noeff && (f3 = ls.a((double)(this.x * this.x + this.z * this.z))) > 0.0f) {
                                bullet.g.g(this.x * (double)this.knockbackStrength * (double)0.6f / (double)f3, 0.1, this.z * (double)this.knockbackStrength * (double)0.6f / (double)f3);
                            }
                        }
                        this.a(this.hitSound, 1.0f, 1.2f / (this.ab.nextFloat() * 0.2f + 0.9f));
                        if (!this.penetrate) {
                            this.x();
                        }
                    } else {
                        this.x *= (double)-0.1f;
                        if (this.gravity) {
                            this.y *= (double)-0.1f;
                        }
                        this.z *= (double)-0.1f;
                        this.A += 180.0f;
                        this.C += 180.0f;
                        this.ticksInAir = this.penetrate ? 5 : 0;
                    }
                } else {
                    this.xTile = bullet.b;
                    this.yTile = bullet.c;
                    this.zTile = bullet.d;
                    this.inTile = this.q.a(this.xTile, this.yTile, this.zTile);
                    this.inData = this.q.h(this.xTile, this.yTile, this.zTile);
                    this.x = (float)(bullet.f.c - this.u);
                    if (this.gravity) {
                        this.y = (float)(bullet.f.d - this.v);
                    }
                    this.z = (float)(bullet.f.e - this.w);
                    float f2 = ls.a((double)(this.x * this.x + this.y * this.y + this.z * this.z));
                    this.u -= this.x / (double)f2 * (double)0.05f;
                    if (this.gravity) {
                        this.v -= this.y / (double)f2 * (double)0.05f;
                    }
                    this.w -= this.z / (double)f2 * (double)0.05f;
                    this.a(this.hitSound, 1.0f, 1.2f / (this.ab.nextFloat() * 0.2f + 0.9f));
                    this.inGround = true;
                    if (this.inTile != 0) {
                        aqz.s[this.inTile].a(this.q, this.xTile, this.yTile, this.zTile, (nn)this);
                    }
                }
            }
            this.u += this.x;
            this.v += this.y;
            this.w += this.z;
            float f2 = ls.a((double)(this.x * this.x + this.z * this.z));
            this.A = (float)(Math.atan2(this.x, this.z) * 180.0 / Math.PI);
            this.B = (float)(Math.atan2(this.y, f2) * 180.0 / Math.PI);
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
            this.B = this.D + (this.B - this.D) * 0.2f;
            this.A = this.C + (this.A - this.C) * 0.2f;
            float f4 = 0.99f;
            f1 = 0.05f;
            if (this.H()) {
                for (int j1 = 0; j1 < 4; ++j1) {
                    float f3 = 0.25f;
                    this.q.a("bubble", this.u - this.x * (double)f3, this.v - this.y * (double)f3, this.w - this.z * (double)f3, this.x, this.y, this.z);
                }
                f4 = 0.8f;
            }
            this.x *= (double)f4;
            if (this.gravity) {
                this.y *= (double)f4;
            }
            this.z *= (double)f4;
            if (this.gravity) {
                this.y -= (double)f1;
            }
            this.b(this.u, this.v, this.w);
            this.D();
        }
    }

    public void setKnockbackStrength(float par1) {
        this.knockbackStrength = par1;
    }

    protected void a() {
    }

    protected void a(by nbt) {
        this.xTile = nbt.d("xTile");
        this.yTile = nbt.d("yTile");
        this.zTile = nbt.d("zTile");
        this.inTile = nbt.c("inTile") & 0xFF;
        this.inData = nbt.c("inData") & 0xFF;
        boolean bl = this.inGround = nbt.c("inGround") == 1;
        if (nbt.b("damage")) {
            this.damage = nbt.h("damage");
        }
    }

    protected void b(by nbt) {
        nbt.a("xTile", (short)this.xTile);
        nbt.a("yTile", (short)this.yTile);
        nbt.a("zTile", (short)this.zTile);
        nbt.a("inTile", (byte)this.inTile);
        nbt.a("inData", (byte)this.inData);
        nbt.a("inGround", (byte)(this.inGround ? 1 : 0));
        nbt.a("damage", this.damage);
    }

    public void c(double x, double y, double z2, float speed, float flu) {
        float vec_len = ls.a((double)(x * x + y * y + z2 * z2));
        x /= (double)vec_len;
        y /= (double)vec_len;
        z2 /= (double)vec_len;
        x += this.ab.nextGaussian() * (double)(this.ab.nextBoolean() ? -1 : 1) * (double)0.0075f * (double)flu;
        y += this.ab.nextGaussian() * (double)(this.ab.nextBoolean() ? -1 : 1) * (double)0.0075f * (double)flu;
        z2 += this.ab.nextGaussian() * (double)(this.ab.nextBoolean() ? -1 : 1) * (double)0.0075f * (double)flu;
        this.x = x *= (double)speed;
        this.y = y *= (double)speed;
        this.z = z2 *= (double)speed;
        float level_vec_len = ls.a((double)(x * x + z2 * z2));
        this.C = this.A = (float)(Math.atan2(x, z2) * 180.0 / Math.PI);
        this.D = this.B = (float)(Math.atan2(y, level_vec_len) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    public void setAmmo(BasicAmmo ammo) {
        this.ammo = ammo;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    public void setMaxRange(double maxRange) {
        this.maxRange = maxRange * maxRange;
    }

    public void setDamagePacket(DamagePacket packet) {
        this.dmgPack = packet;
    }

    public boolean e_() {
        return false;
    }

    public boolean aq() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(double x, double y, double z2, float yaw, float pitch, int par9) {
        this.b(x, y, z2);
        this.b(yaw, pitch);
    }

    @SideOnly(value=Side.CLIENT)
    public void h(double x, double y, double z2) {
        this.x = x;
        this.y = y;
        this.z = z2;
        if (this.D == 0.0f && this.C == 0.0f) {
            float level_vec_len = ls.a((double)(x * x + z2 * z2));
            this.C = this.A = (float)(Math.atan2(x, z2) * 180.0 / Math.PI);
            this.D = this.B = (float)(Math.atan2(y, level_vec_len) * 180.0 / Math.PI);
            this.D = this.B;
            this.C = this.A;
            this.b(this.u, this.v, this.w, this.A, this.B);
            this.ticksInGround = 0;
        }
    }

    @SideOnly(value=Side.CLIENT)
    public float S() {
        return 0.0f;
    }
}

