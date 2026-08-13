/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bej
 *  ls
 *  of
 *  rz
 */
package noppes.common.entity.model_entity;

public class EntityElementalStaffFX
extends bej {
    double x;
    double y;
    double z;
    of player;

    public EntityElementalStaffFX(of player, double d, double d1, double d2, double f1, double f2, double f3, int color) {
        super(player.q, player.u + d, player.v + d1, player.w + d2, f1, f2, f3);
        this.player = player;
        this.x = d;
        this.y = d1;
        this.z = d2;
        float[] colors = color <= 15 ? rz.bp[color] : new float[]{(float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f};
        this.j = colors[0];
        this.au = colors[1];
        this.av = colors[2];
        this.g = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.Z = false;
    }

    public void l_() {
        if (this.player.M) {
            this.x();
        } else {
            float var1;
            this.r = this.u;
            this.s = this.v;
            this.t = this.w;
            float var2 = var1 = (float)this.f / (float)this.g;
            var1 = -var1 + var1 * var1 * 2.0f;
            var1 = 1.0f - var1;
            double dx = -ls.a((float)((float)((double)(this.player.A / 180.0f) * Math.PI))) * ls.b((float)((float)((double)(this.player.B / 180.0f) * Math.PI)));
            double dz2 = ls.b((float)((float)((double)(this.player.A / 180.0f) * Math.PI))) * ls.b((float)((float)((double)(this.player.B / 180.0f) * Math.PI)));
            this.u = this.player.u + this.x + dx + this.x * (double)var1;
            this.v = this.player.v + this.y + this.y * (double)var1 + (double)(1.0f - var2) - (double)(this.player.B / 40.0f);
            this.w = this.player.w + this.z + dz2 + this.z * (double)var1;
            if (this.f++ >= this.g) {
                this.x();
            }
        }
    }

    public void x() {
        super.x();
    }
}

