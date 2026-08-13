/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.EntityRegistry
 *  nn
 */
package noppes.common.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import noppes.CustomNpcs;
import noppes.common.constants.EnumModelType;
import noppes.common.entity.EntityProjectile;
import noppes.common.entity.model_entity.EntityNPCDwarfFemale;
import noppes.common.entity.model_entity.EntityNPCDwarfMale;
import noppes.common.entity.model_entity.EntityNPCElfFemale;
import noppes.common.entity.model_entity.EntityNPCElfMale;
import noppes.common.entity.model_entity.EntityNPCEnderman;
import noppes.common.entity.model_entity.EntityNPCFurryFemale;
import noppes.common.entity.model_entity.EntityNPCFurryMale;
import noppes.common.entity.model_entity.EntityNPCGolem;
import noppes.common.entity.model_entity.EntityNPCHumanFemale;
import noppes.common.entity.model_entity.EntityNPCHumanMale;
import noppes.common.entity.model_entity.EntityNPCOrcFemale;
import noppes.common.entity.model_entity.EntityNPCOrcMale;
import noppes.common.entity.model_entity.EntityNPCPony;
import noppes.common.entity.model_entity.EntityNPCVillager;
import noppes.common.entity.model_entity.EntityNpcCrystal;
import noppes.common.entity.model_entity.EntityNpcDragon;
import noppes.common.entity.model_entity.EntityNpcEnderchibi;
import noppes.common.entity.model_entity.EntityNpcMonsterFemale;
import noppes.common.entity.model_entity.EntityNpcMonsterMale;
import noppes.common.entity.model_entity.EntityNpcNagaFemale;
import noppes.common.entity.model_entity.EntityNpcNagaMale;
import noppes.common.entity.model_entity.EntityNpcSkeleton;
import noppes.common.entity.model_entity.EntityNpcSlime;
import noppes.util.Tools;

public class EntityLoader {
    public static void loadEntity() {
        EntityLoader.registerNpc(EntityNPCHumanMale.class, EnumModelType.HumanMale.entityName, EnumModelType.HumanMale.id);
        EntityLoader.registerNpc(EntityNPCVillager.class, "npcvillager", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCPony.class, "npcpony", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCHumanFemale.class, "npchumanfemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCDwarfMale.class, "npcdwarfmale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCFurryMale.class, "npcfurrymale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcMonsterMale.class, "npczombiemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcMonsterFemale.class, "npczombiefemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcSkeleton.class, "npcskeleton", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCDwarfFemale.class, "npcdwarffemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCFurryFemale.class, "npcfurryfemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCOrcMale.class, "npcorcfmale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCOrcFemale.class, "npcorcfemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCElfMale.class, "npcelfmale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCElfFemale.class, "npcelffemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcCrystal.class, "npccrystal", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcEnderchibi.class, "npcenderchibi", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcNagaMale.class, "npcnagamale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcNagaFemale.class, "npcnagafemale", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcSlime.class, "NpcSlime", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNpcDragon.class, "NpcDragon", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCEnderman.class, "npcEnderman", Tools.getEntityId());
        EntityLoader.registerNpc(EntityNPCGolem.class, "npcGolem", Tools.getEntityId());
        int throw_id = Tools.getEntityId();
        EntityRegistry.registerGlobalEntityID(EntityProjectile.class, (String)"throwableitem", (int)throw_id);
        EntityRegistry.registerModEntity(EntityProjectile.class, (String)"throwableitem", (int)throw_id, (Object)CustomNpcs.instance, (int)64, (int)3, (boolean)true);
    }

    private static void registerNpc(Class<? extends nn> cl2, String name, int id) {
        EntityRegistry.registerGlobalEntityID(cl2, (String)name, (int)id);
        EntityRegistry.registerModEntity(cl2, (String)name, (int)id, (Object)CustomNpcs.instance, (int)80, (int)3, (boolean)true);
    }
}

