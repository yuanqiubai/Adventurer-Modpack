/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  ls
 */
package noppes.common.entity.model_entity;

import noppes.common.entity.EntityNPCInterface;

public class EntityNpcDragon
extends EntityNPCInterface {
    public double[][] field_40162_d = new double[64][3];
    public int field_40164_e = -1;
    public float field_40173_aw = 0.0f;
    public float field_40172_ax = 0.0f;
    public int field_40178_aA = 0;
    public boolean isFlying = false;
    private boolean exploded = false;

    public EntityNpcDragon(abw world) {
        super(world);
        this.scaleX = 0.4f;
        this.scaleY = 0.4f;
        this.scaleZ = 0.4f;
        this.labelOffset = 1.0f;
        this.display.texture = "customnpcs:textures/entity/dragon/BlackDragon.png";
    }

    public double Y() {
        return 1.1;
    }

    public double[] func_40160_a(int i, float f) {
        double d1;
        f = 1.0f - f;
        int j = this.field_40164_e - i & 0x3F;
        int k = this.field_40164_e - i - 1 & 0x3F;
        double[] ad2 = new double[3];
        double d = this.field_40162_d[j][0];
        for (d1 = this.field_40162_d[k][0] - d; d1 < -180.0; d1 += 360.0) {
        }
        while (d1 >= 180.0) {
            d1 -= 360.0;
        }
        ad2[0] = d + d1 * (double)f;
        d = this.field_40162_d[j][1];
        d1 = this.field_40162_d[k][1] - d;
        ad2[1] = d + d1 * (double)f;
        ad2[2] = this.field_40162_d[j][2] + (this.field_40162_d[k][2] - this.field_40162_d[j][2]) * (double)f;
        return ad2;
    }

    @Override
    public void c() {
        this.field_40173_aw = this.field_40172_ax;
        if (this.q.I && this.aN() <= 0.0f) {
            if (!this.exploded) {
                this.exploded = true;
                float f1 = (this.ab.nextFloat() - 0.5f) * 8.0f;
                float f2 = (this.ab.nextFloat() - 0.5f) * 4.0f;
                float f4 = (this.ab.nextFloat() - 0.5f) * 8.0f;
                this.q.a("largeexplode", this.u + (double)f1, this.v + 2.0 + (double)f2, this.w + (double)f4, 0.0, 0.0, 0.0);
            }
        } else {
            this.exploded = false;
            float f1 = 0.2f / (ls.a((double)(this.x * this.x + this.z * this.z)) * 10.0f + 1.0f);
            f1 = 0.045f;
            this.field_40172_ax += (f1 *= (float)Math.pow(2.0, this.y)) * 0.5f;
        }
        super.c();
    }

    @Override
    public void updateHitbox() {
        this.O = 1.8f;
        this.P = 1.4f;
        this.O = this.O / 5.0f * (float)this.display.modelSize;
        this.P = this.P / 5.0f * (float)this.display.modelSize;
    }
}

