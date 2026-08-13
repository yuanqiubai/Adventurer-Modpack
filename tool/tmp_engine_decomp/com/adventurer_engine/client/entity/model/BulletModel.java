/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class BulletModel
extends ModelBase {
    public ModelRenderer model;

    public BulletModel() {
        this.field_78089_u = 16;
        this.field_78090_t = 16;
        this.model = new ModelRenderer((ModelBase)this, 0, 0);
        this.model.func_78789_a(-0.5f, -1.5f, -0.5f, 1, 3, 1);
    }

    public void func_78088_a(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.model.func_78785_a(f6);
    }
}

