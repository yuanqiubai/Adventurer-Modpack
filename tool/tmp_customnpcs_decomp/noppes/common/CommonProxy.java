/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abk
 *  abw
 *  atv
 *  com.adventurer_engine.network.RCsPacket
 *  cpw.mods.fml.common.IPickupNotifier
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.network.IGuiHandler
 *  cpw.mods.fml.common.network.NetworkRegistry
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.server.MinecraftServer
 *  of
 *  uf
 *  uy
 */
package noppes.common;

import com.adventurer_engine.network.RCsPacket;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import net.minecraft.server.MinecraftServer;
import noppes.CustomNpcs;
import noppes.common.config.ConfigLoader;
import noppes.common.config.CustomNPCConfig;
import noppes.common.constants.EnumGuiType;
import noppes.common.containers.ContainerCarpentryBench;
import noppes.common.containers.ContainerManageBanks;
import noppes.common.containers.ContainerManageRecipes;
import noppes.common.containers.ContainerMerchantAdd;
import noppes.common.containers.ContainerNPCBankLarge;
import noppes.common.containers.ContainerNPCBankSmall;
import noppes.common.containers.ContainerNPCBankUnlock;
import noppes.common.containers.ContainerNPCBankUpgrade;
import noppes.common.containers.ContainerNPCFollower;
import noppes.common.containers.ContainerNPCFollowerHire;
import noppes.common.containers.ContainerNPCFollowerSetup;
import noppes.common.containers.ContainerNPCInv;
import noppes.common.containers.ContainerNPCTrader;
import noppes.common.containers.ContainerNPCTraderSetup;
import noppes.common.containers.ContainerNpcItemGiver;
import noppes.common.containers.ContainerNpcQuestReward;
import noppes.common.containers.ContainerNpcQuestTypeItem;
import noppes.common.entity.EntityLoader;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.events.EventLoader;
import noppes.common.events.PickupHandler;
import noppes.common.objcenter.CustomItems;
import noppes.util.NoppesUtilServer;

public class CommonProxy
implements IGuiHandler {
    public boolean newVersionAvailable = false;
    public int revision = 4;

    public void onInit(FMLInitializationEvent event) {
        RCsPacket.addPackagePath((String)"noppes.network.client");
        RCsPacket.addPackagePath((String)"noppes.network.player");
        RCsPacket.addPackagePath((String)"noppes.network.server");
        MinecraftServer server = MinecraftServer.F();
        String dir = "";
        dir = server != null ? new File(".").getAbsolutePath() : atv.w().x.getAbsolutePath();
        CustomNpcs.Dir = new File(dir, "customnpcs");
        CustomNpcs.Dir.mkdir();
        ConfigLoader configLoader = new ConfigLoader(this.getClass(), new File(dir, "config"), "CustomNpcs");
        configLoader.loadConfig();
        if (CustomNPCConfig.NpcNavRange < 16) {
            CustomNPCConfig.NpcNavRange = 16;
        }
        CustomItems.load();
        GameRegistry.registerPickupHandler((IPickupNotifier)new PickupHandler());
        NetworkRegistry.instance().registerGuiHandler((Object)this, (IGuiHandler)CustomNpcs.proxy);
        new EventLoader();
        EntityLoader.loadEntity();
    }

    public Object getServerGuiElement(int ID, uf player, abw world, int x, int y, int z2) {
        if (ID > EnumGuiType.values().length) {
            return null;
        }
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
        EnumGuiType gui = EnumGuiType.values()[ID];
        return this.getContainer(gui, player, x, y, z2, npc);
    }

    public uy getContainer(EnumGuiType gui, uf player, int x, int y, int z2, EntityNPCInterface npc) {
        switch (gui) {
            case MainMenuInv: {
                return new ContainerNPCInv(npc, player);
            }
            case PlayerBankSmall: {
                return new ContainerNPCBankSmall(player, x, y);
            }
            case PlayerBankUnlock: {
                return new ContainerNPCBankUnlock(player, x, y);
            }
            case PlayerBankUprade: {
                return new ContainerNPCBankUpgrade(player, x, y);
            }
            case PlayerBankLarge: {
                return new ContainerNPCBankLarge(player, x, y);
            }
            case PlayerFollowerHire: {
                return new ContainerNPCFollowerHire(npc, player);
            }
            case PlayerFollower: {
                return new ContainerNPCFollower(npc, player);
            }
            case PlayerTrader: {
                return new ContainerNPCTrader(npc, player);
            }
            case PlayerAnvil: {
                return new ContainerCarpentryBench(player.bn, player.q, x, y, z2);
            }
            case SetupItemGiver: {
                return new ContainerNpcItemGiver(npc, player);
            }
            case SetupTrader: {
                return new ContainerNPCTraderSetup(npc, player);
            }
            case SetupFollower: {
                return new ContainerNPCFollowerSetup(npc, player);
            }
            case QuestReward: {
                return new ContainerNpcQuestReward(player);
            }
            case QuestItem: {
                return new ContainerNpcQuestTypeItem(player);
            }
            case ManageRecipes: {
                return new ContainerManageRecipes(player, x);
            }
            case ManageBanks: {
                return new ContainerManageBanks(player);
            }
            case MerchantAdd: {
                return new ContainerMerchantAdd(player.bn, (abk)EventLoader.Merchant, player.q);
            }
        }
        return null;
    }

    public Object getClientGuiElement(int ID, uf player, abw world, int x, int y, int z2) {
        return null;
    }

    public void openGui(EntityNPCInterface npc, EnumGuiType gui) {
    }

    public void openGui(int i, int j, int k, EnumGuiType gui, uf player) {
    }

    public void openGui(uf player, Object guiscreen) {
    }

    public void spawnParticle(of player, String string, Object ... ob) {
    }

    public boolean hasClient() {
        return false;
    }

    public uf getPlayer() {
        return null;
    }

    public void registerItem(int itemID) {
    }

    public Object loadResource(String texture) {
        return null;
    }
}

