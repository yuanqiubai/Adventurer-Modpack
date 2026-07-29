/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  by
 *  of
 */
package com.adventurer_engine.common.entity.projectiles;

import com.adventurer_engine.common.entity.projectiles.EntityBulletGeneral;

public class EntityBulletModelItem
extends EntityBulletGeneral {
    public int renderItemID = 0;

    public EntityBulletModelItem(abw world) {
        super(world);
    }

    public EntityBulletModelItem(abw world, double x, double y, double z2) {
        super(world, x, y, z2);
    }

    public EntityBulletModelItem(abw par1World, of par2EntityLivingBase, float speed, float accuracy) {
        super(par1World, par2EntityLivingBase, speed, accuracy);
    }

    public void setRenderItemID(int id) {
        this.renderItemID = id;
    }

    @Override
    public void a() {
        super.a();
        this.ah.a(16, (Object)new Integer(this.renderItemID));
    }

    @Override
    public void a(by nbt) {
        super.a(nbt);
        this.renderItemID = nbt.e("renderItemID");
    }

    @Override
    public void l_() {
        super.l_();
        if (!this.q.I) {
            this.ah.b(16, (Object)new Integer(this.renderItemID));
        } else if (this.renderItemID == 0) {
            this.renderItemID = this.ah.c(16);
        }
    }

    @Override
    public void b(by nbt) {
        super.b(nbt);
        nbt.a("renderItemID", this.renderItemID);
    }
}

