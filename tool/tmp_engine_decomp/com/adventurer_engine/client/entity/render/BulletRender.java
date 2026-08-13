/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.entity.render;

import com.adventurer_engine.client.entity.model.BulletModel;
import com.adventurer_engine.common.entity.projectiles.EntityBulletGeneral;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BulletRender
extends Render {
    public static ModelBase model = new BulletModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation("adventurer_engine", "textures/entity/bullet.png");

    public void renderBullet(EntityBulletGeneral bullet, double x, double y, double z, float yaw, float pitch) {
        this.func_110777_b(bullet);
        GL11.glPushMatrix();
        GL11.glEnable((int)32826);
        GL11.glTranslatef((float)((float)x), (float)((float)y), (float)((float)z));
        GL11.glRotatef((float)yaw, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(90.0f - bullet.field_70127_C - (bullet.field_70125_A - bullet.field_70127_C) * pitch), (float)1.0f, (float)0.0f, (float)0.0f);
        model.func_78088_a((Entity)bullet, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    public void func_76986_a(Entity entity, double x, double y, double z, float yaw, float pitch) {
        this.renderBullet((EntityBulletGeneral)entity, x, y, z, yaw, pitch);
    }

    protected ResourceLocation func_110775_a(Entity entity) {
        return TEXTURE;
    }
}

