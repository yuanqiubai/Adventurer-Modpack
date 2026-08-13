/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bgm
 *  bje
 *  cpw.mods.fml.client.registry.ClientRegistry
 *  cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
 *  cpw.mods.fml.client.registry.RenderingRegistry
 */
package noppes.client.model;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import noppes.client.model.ModelDwarfFemale;
import noppes.client.model.ModelDwarfMale;
import noppes.client.model.ModelElfFemale;
import noppes.client.model.ModelElfMale;
import noppes.client.model.ModelEnderChibi;
import noppes.client.model.ModelFurryFemale;
import noppes.client.model.ModelFurryMale;
import noppes.client.model.ModelNPCEnderman;
import noppes.client.model.ModelNPCFemale;
import noppes.client.model.ModelNPCGolem;
import noppes.client.model.ModelNPCMale;
import noppes.client.model.ModelNagaFemale;
import noppes.client.model.ModelNagaMale;
import noppes.client.model.ModelNpcCrystal;
import noppes.client.model.ModelNpcDragon;
import noppes.client.model.ModelNpcSkeleton;
import noppes.client.model.ModelNpcSlime;
import noppes.client.model.ModelOrcFemale;
import noppes.client.model.ModelOrcMale;
import noppes.client.model.ModelZombieFemale;
import noppes.client.model.ModelZombieMale;
import noppes.client.renderer.BlockCarpentryBenchRenderer;
import noppes.client.renderer.BlockMailboxRenderer;
import noppes.client.renderer.RenderCustomNpc;
import noppes.client.renderer.RenderNPCHumanFemale;
import noppes.client.renderer.RenderNPCHumanMale;
import noppes.client.renderer.RenderNPCPony;
import noppes.client.renderer.RenderNpcCrystal;
import noppes.client.renderer.RenderNpcDragon;
import noppes.client.renderer.RenderNpcSlime;
import noppes.client.renderer.RenderNpcVillager;
import noppes.client.renderer.RenderProjectile;
import noppes.common.blocks.BlockMailbox;
import noppes.common.blocks.TileBlockAnvil;
import noppes.common.blocks.TileMailbox;
import noppes.common.entity.EntityCustomNpc;
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
import noppes.common.objcenter.CustomItems;

public class EntityRenderLoader {
    public static void loadEntityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCHumanMale.class, (bgm)new RenderNPCHumanMale(new ModelNPCMale(0.0f), new ModelNPCMale(1.0f), new ModelNPCMale(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCElfMale.class, (bgm)new RenderNPCHumanMale(new ModelElfMale(0.0f), new ModelElfMale(1.0f), new ModelElfMale(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCOrcMale.class, (bgm)new RenderNPCHumanMale(new ModelOrcMale(0.0f), new ModelOrcMale(1.0f), new ModelOrcMale(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcMonsterMale.class, (bgm)new RenderNPCHumanMale(new ModelZombieMale(0.0f), new ModelZombieMale(1.0f), new ModelZombieMale(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcSkeleton.class, (bgm)new RenderNPCHumanMale(new ModelNpcSkeleton(0.0f), new ModelNpcSkeleton(1.0f), new ModelNpcSkeleton(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCDwarfMale.class, (bgm)new RenderNPCHumanMale(new ModelDwarfMale(0.0f), new ModelDwarfMale(0.6f), new ModelDwarfMale(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcNagaMale.class, (bgm)new RenderNPCHumanMale(new ModelNagaMale(64, 64, 0.0f), new ModelNagaMale(64, 32, 1.0f), new ModelNagaMale(64, 32, 0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcEnderchibi.class, (bgm)new RenderNPCHumanMale(new ModelEnderChibi(0.0f), new ModelEnderChibi(0.6f), new ModelEnderChibi(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCEnderman.class, (bgm)new RenderNPCHumanMale(new ModelNPCEnderman(0.0f), new ModelNPCEnderman(0.6f), new ModelNPCEnderman(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCGolem.class, (bgm)new RenderNPCHumanMale(new ModelNPCGolem(0.0f), new ModelNPCGolem(1.0f), new ModelNPCGolem(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCHumanFemale.class, (bgm)new RenderNPCHumanFemale(new ModelNPCFemale(0.0f), new ModelNPCFemale(0.6f), new ModelNPCFemale(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCElfFemale.class, (bgm)new RenderNPCHumanFemale(new ModelElfFemale(0.0f), new ModelElfFemale(0.6f), new ModelElfFemale(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCOrcFemale.class, (bgm)new RenderNPCHumanFemale(new ModelOrcFemale(0.0f), new ModelOrcFemale(0.6f), new ModelOrcFemale(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCDwarfFemale.class, (bgm)new RenderNPCHumanFemale(new ModelDwarfFemale(0.0f), new ModelDwarfFemale(0.6f), new ModelDwarfFemale(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcMonsterFemale.class, (bgm)new RenderNPCHumanFemale(new ModelZombieFemale(0.0f), new ModelZombieFemale(0.6f), new ModelZombieFemale(0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcNagaFemale.class, (bgm)new RenderNPCHumanFemale(new ModelNagaFemale(64, 64, 0.0f), new ModelNagaFemale(64, 32, 0.6f), new ModelNagaFemale(64, 32, 0.3f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCFurryMale.class, (bgm)new RenderNPCHumanMale(new ModelFurryMale(64, 64, 0.0f), new ModelNPCMale(64, 32, 1.0f), new ModelNPCMale(64, 32, 0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCFurryFemale.class, (bgm)new RenderNPCHumanFemale(new ModelFurryFemale(64, 64, 0.0f), new ModelNPCFemale(64, 32, 1.0f), new ModelNPCFemale(64, 32, 0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCVillager.class, (bgm)new RenderNpcVillager());
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCPony.class, (bgm)new RenderNPCPony());
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcCrystal.class, (bgm)new RenderNpcCrystal(new ModelNpcCrystal(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcDragon.class, (bgm)new RenderNpcDragon(new ModelNpcDragon(0.0f), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcSlime.class, (bgm)new RenderNpcSlime(new ModelNpcSlime(16), new ModelNpcSlime(0), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, (bgm)new RenderProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityCustomNpc.class, (bgm)new RenderCustomNpc());
    }

    public static void loadTileEntityRender() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileBlockAnvil.class, (bje)new BlockCarpentryBenchRenderer());
        BlockMailboxRenderer mailbox = new BlockMailboxRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TileMailbox.class, (bje)mailbox);
        ((BlockMailbox)CustomItems.mailbox).renderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)mailbox);
    }
}

