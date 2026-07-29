/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bgm
 *  cpw.mods.fml.client.registry.RenderingRegistry
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.MinecraftForgeClient
 *  yc
 */
package com.adventurer_engine.client;

import com.adventurer_engine.client.entity.render.ArrowLikeRender;
import com.adventurer_engine.client.entity.render.BulletRender;
import com.adventurer_engine.client.entity.render.FlyItemRender;
import com.adventurer_engine.client.entity.render.GeneralRender;
import com.adventurer_engine.client.model.ItemModelLoader;
import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelArrow;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelBullet;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelItem;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.common.items.blank.Full3DItem;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class RenderLoader {
    public static void registerEntityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBulletModelBullet.class, (bgm)new BulletRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityBulletModelArrow.class, (bgm)new ArrowLikeRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityBulletModelItem.class, (bgm)new FlyItemRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityRCsNPCBase.class, (bgm)new GeneralRender());
    }

    public static void registerItemRender() {
        ItemModelLoader modelLoader = new ItemModelLoader();
        for (yc i : ItemLoader.item) {
            if (!(i instanceof Full3DItem) || ((Full3DItem)i).getModelName() == null) continue;
            MinecraftForgeClient.registerItemRenderer((int)i.cv, (IItemRenderer)modelLoader);
        }
    }
}

