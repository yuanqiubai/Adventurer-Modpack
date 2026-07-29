/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraftforge.common.DimensionManager
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.ForgeSubscribe
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.event.world.WorldEvent$Save
 *  ni
 *  noppes.common.entity.EntityNPCInterface
 *  of
 *  or
 *  os
 *  ot
 *  ov
 *  oy
 *  tp
 *  uf
 *  ye
 */
package com.adventurer_engine.common.event;

import com.adventurer_engine.common.CommonConfig;
import com.adventurer_engine.common.data.globaldata.IGlobalData;
import com.adventurer_engine.common.event.RCsTickHandler;
import com.adventurer_engine.common.gadgets.AdditionBombs;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.common.potion.PotionLoader;
import java.util.Random;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.WorldEvent;
import noppes.common.entity.EntityNPCInterface;

public class EventLoader {
    public static final or IcePower = new oy("ice_power", 1.0, 0.0, Double.MAX_VALUE);
    public static final or BowPower = new oy("bow_power", 1.0, 0.0, Double.MAX_VALUE);
    private static final Random rand = new Random();
    private static final UUID GREEN_MAX_HEAL_UUID = UUID.fromString("f3f5f6f7-8f9f-afbf-cfcf-dfdfefeff0f1");
    private static final UUID NPC_MAX_HEALTH = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

    public EventLoader() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @ForgeSubscribe
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        EventLoader.onUpdate_HandleAdditionBomb(event);
        EventLoader.onUpdate_ModifyNPCHealth(event);
        EventLoader.onUpdate_UpdatePlayerStatus(event);
        EventLoader.onUpdate_RefreshUseDuration(event);
        EventLoader.onUpdate_ApplyGreenRingAndDuring(event);
    }

    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        EventLoader.onEntityJoinWorld_AddCustomAttribute(event);
        EventLoader.onEntityJoinWorld_FixNPC(event);
    }

    @ForgeSubscribe
    public void onLivingHurt(LivingHurtEvent event) {
        of attacker;
        if (event.entity.q.I) {
            return;
        }
        of wounded = event.entityLiving;
        of of2 = attacker = event.source.i() instanceof of ? (of)event.source.i() : null;
        if (wounded == null) {
            return;
        }
        if (wounded instanceof uf) {
            EventLoader.onLivingHurt_PlayerDurability(event, (uf)wounded);
        }
        EventLoader.onLivingHurt_ApplyEasyHurt(event, wounded);
        EventLoader.onLivingHurt_ApplyDefend(event, wounded);
        if (attacker != null) {
            EventLoader.onLivingHurt_ApplyForesight(event, attacker);
            EventLoader.onLivingHurt_ApplyResentment(event, attacker);
            EventLoader.onLivingHurt_ApplyGreenRingIns(event, attacker);
            EventLoader.onLivingHurt_ApplyBowPower(event, attacker);
        }
    }

    private static void onUpdate_HandleAdditionBomb(LivingEvent.LivingUpdateEvent event) {
        if (!event.entityLiving.q.I) {
            AdditionBombs.onUpdate(event);
        }
    }

    private static void onUpdate_RefreshUseDuration(LivingEvent.LivingUpdateEvent event) {
        if (!(event.entity instanceof uf)) {
            return;
        }
        uf player = (uf)event.entity;
        if (player.aZ() != null) {
            player.aZ().use_duration = player.bs();
        }
    }

    private static void onUpdate_ApplyGreenRingAndDuring(LivingEvent.LivingUpdateEvent event) {
        if (event.entity.q.I || !(event.entity instanceof uf)) {
            return;
        }
        uf player = (uf)event.entity;
        boolean flag = false;
        ye greenRing = null;
        for (ye item : player.bn.a) {
            if (item == null) continue;
            if (!flag && item.d == ItemLoader.itemDuring.cv) {
                flag = true;
            }
            if (greenRing != null || item.d != ItemLoader.itemGreenRing.cv) continue;
            greenRing = item;
        }
        if (player.dur_delay == 0) {
            player.dur_delay = CommonConfig.resumeRate;
            if (flag && !player.a(ni.s)) {
                player.bI().a(Math.min(player.bI().a() + 1, 20));
            }
            os attr = player.a(tp.a);
            if (greenRing != null && greenRing.p() && greenRing.q().b("amount")) {
                int amount = greenRing.q().e("amount");
                if (amount > 0) {
                    if (attr.a(GREEN_MAX_HEAL_UUID) == null) {
                        ot mod = new ot(GREEN_MAX_HEAL_UUID, "greenRing", (double)(amount * 2), 0);
                        attr.a(mod);
                    } else {
                        ot oldMod = attr.a(GREEN_MAX_HEAL_UUID);
                        if (oldMod.d() != (double)(amount * 2)) {
                            attr.b(oldMod);
                            ot mod = new ot(GREEN_MAX_HEAL_UUID, "greenRing", (double)(amount * 2), 0);
                            attr.a(mod);
                        }
                    }
                } else if (attr.a(GREEN_MAX_HEAL_UUID) != null) {
                    attr.b(attr.a(GREEN_MAX_HEAL_UUID));
                }
            } else if (attr.a(GREEN_MAX_HEAL_UUID) != null) {
                attr.b(attr.a(GREEN_MAX_HEAL_UUID));
            }
        }
    }

    private static void onUpdate_UpdatePlayerStatus(LivingEvent.LivingUpdateEvent event) {
        if (event.entity.q.I || !(event.entity instanceof uf)) {
            return;
        }
        uf player = (uf)event.entity;
        player.shoot_delay = player.shoot_delay > 0 ? --player.shoot_delay : 0;
        player.dur_delay = player.dur_delay > 0 ? --player.dur_delay : 0;
    }

    private static void onUpdate_ModifyNPCHealth(LivingEvent.LivingUpdateEvent event) {
        if (!CommonConfig.enable_npc_hpm || event.entity.q.I || RCsTickHandler.healNPCbuf != 0 || !(event.entity instanceof EntityNPCInterface)) {
            return;
        }
        EntityNPCInterface npc = (EntityNPCInterface)event.entity;
        os maxHealthAttr = npc.a(tp.a);
        if (maxHealthAttr == null) {
            return;
        }
        int playerAmount = Math.min(MinecraftServer.F().A(), CommonConfig.npc_hpm_maxplayer) - 1;
        double ratio = (double)playerAmount * CommonConfig.npc_hpm_ratio;
        ot mod = maxHealthAttr.a(NPC_MAX_HEALTH);
        double originHealthRatio = npc.aN() / npc.aT();
        if (mod != null) {
            if (mod.d() == ratio) {
                return;
            }
            maxHealthAttr.b(mod);
        }
        maxHealthAttr.a(new ot(NPC_MAX_HEALTH, "nm", ratio, 1));
        npc.g((float)((double)npc.aT() * originHealthRatio));
    }

    private static void onEntityJoinWorld_AddCustomAttribute(EntityJoinWorldEvent event) {
        if (event.entity.q.I || !(event.entity instanceof uf)) {
            return;
        }
        uf entity = (uf)event.entity;
        ov am = entity.aX();
        if (am.a(IcePower) == null) {
            am.b(IcePower);
        }
        if (am.a(BowPower) == null) {
            am.b(BowPower);
        }
    }

    private static void onEntityJoinWorld_FixNPC(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityNPCInterface) {
            ((EntityNPCInterface)event.entity).updateHitbox();
            event.entity.b(event.entity.u, event.entity.v, event.entity.w);
        }
    }

    private static void onLivingHurt_ApplyEasyHurt(LivingHurtEvent event, of wounded) {
        if (wounded.a((ni)PotionLoader.easyHurt)) {
            PotionLoader.easyHurt.onHurt(event, wounded.b((ni)PotionLoader.easyHurt).c() + 1);
        }
    }

    private static void onLivingHurt_ApplyDefend(LivingHurtEvent event, of wounded) {
        if (wounded.a((ni)PotionLoader.defend)) {
            PotionLoader.defend.onHurt(event);
        }
    }

    private static void onLivingHurt_ApplyForesight(LivingHurtEvent event, of attacker) {
        if (attacker.a((ni)PotionLoader.foresight)) {
            PotionLoader.foresight.onHurt(event, attacker.b((ni)PotionLoader.foresight).c() + 1);
        }
    }

    private static void onLivingHurt_ApplyResentment(LivingHurtEvent event, of attacker) {
        if (attacker.a((ni)PotionLoader.resentment)) {
            PotionLoader.resentment.onHurt(event, attacker.b((ni)PotionLoader.resentment).c() + 1);
        }
    }

    private static void onLivingHurt_ApplyGreenRingIns(LivingHurtEvent event, of attacker) {
        if (!(attacker instanceof uf)) {
            return;
        }
        uf player = (uf)attacker;
        ye greenRing = null;
        for (ye i : player.bn.a) {
            if (i == null || i.d != ItemLoader.itemGreenRing.cv) continue;
            greenRing = i;
            break;
        }
        if (greenRing != null && (!greenRing.p() || greenRing.q().b("amount") && greenRing.q().e("amount") == 0)) {
            event.ammount *= 1.1f;
        }
    }

    private static void onLivingHurt_ApplyBowPower(LivingHurtEvent event, of attacker) {
        if (!(attacker instanceof uf)) {
            return;
        }
        String type = event.source.o;
        if (type.equals("thrown") || type.equals("arrow")) {
            uf player = (uf)attacker;
            os bowPower = player.a(BowPower);
            if (bowPower == null) {
                return;
            }
            event.ammount *= (float)bowPower.e();
        } else if (type.equals("ice_damage")) {
            uf player = (uf)attacker;
            os bowPower = player.a(IcePower);
            if (bowPower == null) {
                return;
            }
            event.ammount *= (float)bowPower.e();
        }
    }

    private static void onLivingHurt_PlayerDurability(LivingHurtEvent event, uf player) {
    }

    @ForgeSubscribe
    public void onWorldStart(WorldEvent.Load event) {
        if (event.world.I) {
            return;
        }
        if (DimensionManager.getWorld((int)0) != event.world) {
            return;
        }
        IGlobalData.initAll();
        RCsTickHandler.lastWorldTime = -1L;
    }

    @ForgeSubscribe
    public void onWorldSave(WorldEvent.Save event) {
        if (event.world.I) {
            return;
        }
        IGlobalData.saveAll();
    }
}

