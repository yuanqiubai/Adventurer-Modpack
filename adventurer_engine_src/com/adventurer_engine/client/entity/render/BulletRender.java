/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bbo
 *  bgm
 *  bjo
 *  nn
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.entity.render;

import com.adventurer_engine.client.entity.model.BulletModel;
import com.adventurer_engine.common.entity.projectiles.EntityBulletGeneral;
import org.lwjgl.opengl.GL11;

public class BulletRender
extends bgm {
    public static bbo model = new BulletModel();
    private static final bjo TEXTURE = new bjo("adventurer_engine", "textures/entity/bullet.png");

    public void renderBullet(EntityBulletGeneral bullet, double x, double y, double z, float yaw, float pitch) {
        this.b(bullet);
        GL11.glPushMatrix();
        GL11.glEnable((int)32826);
        GL11.glTranslatef((float)((float)x), (float)((float)y), (float)((float)z));
        GL11.glRotatef((float)yaw, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(90.0f - bullet.D - (bullet.B - bullet.D) * pitch), (float)1.0f, (float)0.0f, (float)0.0f);
        model.a((nn)bullet, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    public void a(nn entity, double x, double y, double z, float yaw, float pitch) {
        this.renderBullet((EntityBulletGeneral)entity, x, y, z, yaw, pitch);
    }

    protected bjo a(nn entity) {
        return TEXTURE;
    }
}

