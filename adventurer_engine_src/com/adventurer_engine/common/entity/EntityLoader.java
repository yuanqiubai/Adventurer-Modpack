/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.EntityRegistry
 */
package com.adventurer_engine.common.entity;

import com.adventurer_engine.AdventurerEngine;
import com.adventurer_engine.common.entity.neo_npc.EntityRCsNPCBase;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelArrow;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelBullet;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelItem;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityLoader {
    public static void registerEntity() {
        EntityRegistry.registerModEntity(EntityBulletModelItem.class, (String)"rcsbullet_model_item", (int)114514, (Object)AdventurerEngine.instance, (int)64, (int)1, (boolean)true);
        EntityRegistry.registerModEntity(EntityBulletModelArrow.class, (String)"rcsbullet_model_arrow", (int)114515, (Object)AdventurerEngine.instance, (int)64, (int)1, (boolean)true);
        EntityRegistry.registerModEntity(EntityBulletModelBullet.class, (String)"rcsbullet_model_bullet", (int)114516, (Object)AdventurerEngine.instance, (int)64, (int)1, (boolean)true);
        EntityRegistry.registerModEntity(EntityRCsNPCBase.class, (String)"rcs_npc", (int)114517, (Object)AdventurerEngine.instance, (int)64, (int)1, (boolean)true);
    }
}

