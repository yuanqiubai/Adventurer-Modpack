/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  acf
 *  og
 *  pm
 */
package noppes.common.entity.ai;

public class EntityAIBustDoor
extends pm {
    private int breakingTime;
    private int field_75358_j = -1;

    public EntityAIBustDoor(og par1EntityLiving) {
        super(par1EntityLiving);
    }

    public boolean a() {
        return super.a() && this.a.q.O().b("mobGriefing") && !this.e.b_((acf)this.a.q, this.b, this.c, this.d);
    }

    public void c() {
        super.c();
        this.breakingTime = 0;
    }

    public boolean b() {
        double var1 = this.a.e((double)this.b, (double)this.c, (double)this.d);
        return this.breakingTime <= 240 && !this.e.b_((acf)this.a.q, this.b, this.c, this.d) && var1 < 4.0;
    }

    public void d() {
        super.d();
        this.a.q.f(this.a.k, this.b, this.c, this.d, -1);
    }

    public void e() {
        super.e();
        if (this.a.aD().nextInt(20) == 0) {
            this.a.q.e(1010, this.b, this.c, this.d, 0);
            this.a.aV();
        }
        ++this.breakingTime;
        int var1 = (int)((float)this.breakingTime / 240.0f * 10.0f);
        if (var1 != this.field_75358_j) {
            this.a.q.f(this.a.k, this.b, this.c, this.d, var1);
            this.field_75358_j = var1;
        }
        if (this.breakingTime == 240) {
            this.a.q.i(this.b, this.c, this.d);
            this.a.q.e(1012, this.b, this.c, this.d, 0);
            this.a.q.e(2001, this.b, this.c, this.d, this.e.cF);
        }
    }
}

