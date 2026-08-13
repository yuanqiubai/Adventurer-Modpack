/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abm
 *  abw
 *  aqz
 *  by
 *  cg
 *  cl
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  jv
 *  net.minecraftforge.common.DimensionManager
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.ForgeSubscribe
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  net.minecraftforge.event.entity.player.EntityInteractEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  nn
 *  nt
 *  of
 *  ub
 *  uf
 *  ye
 */
package noppes.common.events;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import noppes.CustomNpcs;
import noppes.client.controllers.CloneController;
import noppes.common.constants.EnumGuiType;
import noppes.common.constants.EnumQuestType;
import noppes.common.controllers.BankController;
import noppes.common.controllers.DialogController;
import noppes.common.controllers.FactionController;
import noppes.common.controllers.GlobalDataController;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.QuestController;
import noppes.common.controllers.QuestData;
import noppes.common.controllers.RecipeCarpentry;
import noppes.common.controllers.RecipeController;
import noppes.common.controllers.TransportController;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.items.ItemExcalibur;
import noppes.common.items.ItemShield;
import noppes.common.objcenter.CustomItems;
import noppes.common.permissions.CustomNpcsPermissions;
import noppes.common.quests.QuestKill;
import noppes.network.client.MerchantAddPacket;
import noppes.network.client.PlayMusicPacket;
import noppes.network.client.SyncRecipesPacket;
import noppes.util.NoppesUtilServer;

public class EventLoader {
    public static ub Merchant;

    public EventLoader() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @ForgeSubscribe
    public void onPlayerInteract(PlayerInteractEvent event) {
        uf player = event.entityPlayer;
        int blockId = player.q.a(event.x, event.y, event.z);
        if (blockId == aqz.aD.cF && !player.q.I) {
            RecipeController controller = RecipeController.instance;
            cg list = new cg();
            for (RecipeCarpentry recipe : controller.globalRecipes.values()) {
                list.a((cl)recipe.writeNBT());
            }
            by nbt = new by();
            nbt.a("recipes", (cl)list);
            PacketDispatcher.sendPacketToPlayer((ey)new SyncRecipesPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    @ForgeSubscribe
    public void onEntityInteract(EntityInteractEvent event) {
        ye item = event.entityPlayer.by();
        if (item != null) {
            boolean isRemote = event.entityPlayer.q.I;
            boolean npcInteracted = event.target instanceof EntityNPCInterface;
            if (CustomNpcs.activeWand(item) && npcInteracted && !isRemote) {
                if (!CustomNpcsPermissions.Instance.hasPermission(event.entityPlayer.bu, "customnpcs.npc.edit")) {
                    return;
                }
                event.setCanceled(true);
                NoppesUtilServer.sendOpenGui(event.entityPlayer, EnumGuiType.MainMenuDisplay, (EntityNPCInterface)event.target);
            } else if (CustomNpcs.activeCloner(item)) {
                CloneController.toClone = event.target;
                CustomNpcs.proxy.openGui(0, 0, 0, EnumGuiType.MobSpawnerAdd, event.entityPlayer);
            } else if (CustomNpcs.activeWand(item) && event.target instanceof ub) {
                event.setCanceled(true);
                Merchant = (ub)event.target;
                if (!isRemote) {
                    jv player = (jv)event.entityPlayer;
                    player.openGui((Object)CustomNpcs.instance, EnumGuiType.MerchantAdd.ordinal(), player.q, 0, 0, 0);
                    abm merchantrecipelist = Merchant.b((uf)player);
                    if (merchantrecipelist != null) {
                        PacketDispatcher.sendPacketToPlayer((ey)new MerchantAddPacket(merchantrecipelist).makePacket("CNPCs Client"), (Player)((Player)player));
                    }
                }
            }
        }
    }

    @ForgeSubscribe
    public void onPlayerHurt(LivingHurtEvent event) {
        ye item;
        if (!(event.entityLiving instanceof uf)) {
            return;
        }
        uf player = (uf)event.entityLiving;
        if (!event.source.e() && !event.source.m() && player.bv() && (item = player.by()) != null && item.b() instanceof ItemShield) {
            float damage = (float)item.k() + event.ammount;
            item.a((int)event.ammount, (of)player);
            if (damage > (float)item.l()) {
                event.ammount = damage - (float)item.l();
            } else {
                event.ammount = 0.0f;
                event.setCanceled(true);
            }
        }
    }

    @ForgeSubscribe
    public void onWorldLoad(WorldEvent.Load event) {
        abw world = event.world;
        if (!world.I && DimensionManager.getWorld((int)0) == world) {
            new PlayerDataController();
            new QuestController();
            new DialogController();
            new BankController();
            new RecipeController();
            new FactionController();
            new TransportController();
            new GlobalDataController();
        }
    }

    @ForgeSubscribe
    public void onLivingDeath(LivingDeathEvent event) {
        if (!event.entityLiving.q.I) {
            this.entityDied(event.entityLiving, event);
        }
    }

    private void entityDied(of entity, LivingDeathEvent hurt) {
        if (hurt.source.i() != null && hurt.source.i() instanceof uf) {
            uf player = (uf)hurt.source.i();
            this.doExcalibur(player, entity);
            this.doQuest(player, entity);
            if (entity instanceof EntityNPCInterface) {
                this.doFactionPoints(player, (EntityNPCInterface)entity);
            }
        }
        if (entity instanceof uf) {
            PlayerDataController.instance.getPlayerData((uf)entity).saveNBTData(null);
        }
    }

    private void doExcalibur(uf player, of entity) {
        ye item = player.by();
        if (item != null && item.b() == CustomItems.excalibur) {
            PacketDispatcher.sendPacketToPlayer((ey)new PlayMusicPacket("Failboat103 - Excalibuuur").makePacket("CNPCs Client"), (Player)((Player)player));
            player.a("<" + item.b().v() + "> " + ItemExcalibur.quotes[player.aD().nextInt(ItemExcalibur.quotes.length)]);
        }
    }

    private void doFactionPoints(uf player, EntityNPCInterface npc) {
        npc.advanced.factions.addPoints(player);
    }

    private void doQuest(uf player, of entity) {
        PlayerQuestData playerData = PlayerDataController.instance.getPlayerData((uf)player).questData;
        boolean change = false;
        String entityName = nt.b((nn)entity);
        for (QuestData data : playerData.activeQuests.values()) {
            HashMap<String, Integer> killed;
            if (data.quest.type != EnumQuestType.Kill) continue;
            String name = entityName;
            QuestKill quest = (QuestKill)data.quest.questInterface;
            if (quest.targets.containsKey(entity.an())) {
                name = entity.an();
            } else if (!quest.targets.containsKey(entityName)) continue;
            if ((killed = quest.getKilled(data)).containsKey(name) && killed.get(name) >= quest.targets.get(name)) continue;
            int amount = 0;
            if (killed.containsKey(name)) {
                amount = killed.get(name);
            }
            killed.put(name, amount + 1);
            quest.setKilled(data, killed);
            change = true;
        }
        if (change) {
            playerData.checkQuestCompletion(player, EnumQuestType.Kill);
        }
    }
}

