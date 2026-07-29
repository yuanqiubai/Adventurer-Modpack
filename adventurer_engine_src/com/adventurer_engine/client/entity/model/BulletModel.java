/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bcu
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.entity.model;

import org.lwjgl.opengl.GL11;

public class BulletModel
extends bbo {
    public bcu model;

    public BulletModel() {
        this.u = 16;
        this.t = 16;
        this.model = new bcu((bbo)this, 0, 0);
        this.model.a(-0.5f, -1.5f, -0.5f, 1, 3, 1);
    }

    public void a(nn entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.model.a(f6);
    }
}

