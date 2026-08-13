/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.adventurer_engine.client.entity.render;

import com.adventurer_engine.client.entity.model.RCsModelManager;
import com.adventurer_engine.client.model.rc.RCsAnimationManager;
import com.adventurer_engine.client.model.rc.RCsModel;
import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GeneralRender
extends Render {
    public static Map<String, RCsModel> baked_models = new HashMap<String, RCsModel>();
    private static final ResourceLocation TEXTURE = new ResourceLocation("adventurer_engine", "textures/entity/fufu.png");
    public RCsModel model = new RCsModel(RCsModelManager.model);

    public void func_76986_a(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!(entity instanceof EntityRCsNPCBase)) {
            return;
        }
        this.func_110777_b(entity);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)x), (float)((float)y), (float)((float)z));
        this.renderRCsNPC((EntityRCsNPCBase)entity, partialTicks);
        GL11.glPopMatrix();
    }

    public void renderRCsNPC(EntityRCsNPCBase entity, float partialTicks) {
        RCsModel model;
        if (entity.model == null) {
            return;
        }
        if (!baked_models.containsKey(entity.model.name)) {
            model = new RCsModel(entity.model);
            baked_models.put(entity.model.name, model);
        } else {
            model = baked_models.get(entity.model.name);
        }
        if (entity.animation == null) {
            entity.animation = new RCsAnimationManager();
        }
        if (entity.animation instanceof RCsAnimationManager) {
            GL11.glPushMatrix();
            GL11.glRotatef((float)entity.A, (float)0.0f, (float)1.0f, (float)0.0f);
            model.render((RCsAnimationManager)entity.animation, partialTicks);
            GL11.glPopMatrix();
        }
    }

    public ResourceLocation func_110775_a(Entity entity) {
        return TEXTURE;
    }
}

