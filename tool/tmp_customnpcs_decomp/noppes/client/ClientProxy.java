/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  ats
 *  atv
 *  awe
 *  beg
 *  bim
 *  bjo
 *  com.adventurer_engine.util.DebugGadgets
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.registry.TickRegistry
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.MinecraftForgeClient
 *  net.minecraftforge.common.MinecraftForge
 *  of
 *  uf
 *  uy
 */
package noppes.client;

import com.adventurer_engine.util.DebugGadgets;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import noppes.client.VersionChecker;
import noppes.client.controllers.MusicController;
import noppes.client.events.ClientKeyHandler;
import noppes.client.events.ClientTickHandler;
import noppes.client.events.TextureLoadEvent;
import noppes.client.gui.GuiMerchantAdd;
import noppes.client.gui.GuiNpcMobSpawner;
import noppes.client.gui.GuiNpcMobSpawnerAdd;
import noppes.client.gui.GuiNpcPather;
import noppes.client.gui.GuiNpcRedstoneBlock;
import noppes.client.gui.GuiNpcRemoteEditor;
import noppes.client.gui.GuiNpcWaypoint;
import noppes.client.gui.global.GuiNPCManageBanks;
import noppes.client.gui.global.GuiNPCManageDialogs;
import noppes.client.gui.global.GuiNPCManageFactions;
import noppes.client.gui.global.GuiNPCManageQuest;
import noppes.client.gui.global.GuiNPCManageTransporters;
import noppes.client.gui.global.GuiNpcManageRecipes;
import noppes.client.gui.global.GuiNpcQuestReward;
import noppes.client.gui.mainmenu.GuiNPCGlobalMainMenu;
import noppes.client.gui.mainmenu.GuiNPCInv;
import noppes.client.gui.mainmenu.GuiNpcAI;
import noppes.client.gui.mainmenu.GuiNpcAdvanced;
import noppes.client.gui.mainmenu.GuiNpcDisplay;
import noppes.client.gui.mainmenu.GuiNpcStats;
import noppes.client.gui.player.GuiMailbox;
import noppes.client.gui.player.GuiMailmanSend;
import noppes.client.gui.player.GuiNPCBankChest;
import noppes.client.gui.player.GuiNPCTrader;
import noppes.client.gui.player.GuiNpcCarpentryBench;
import noppes.client.gui.player.GuiNpcFollower;
import noppes.client.gui.player.GuiNpcFollowerHire;
import noppes.client.gui.player.GuiTransportSelection;
import noppes.client.gui.questtypes.GuiNpcQuestTypeItem;
import noppes.client.gui.roles.GuiNpcBankSetup;
import noppes.client.gui.roles.GuiNpcFollowerSetup;
import noppes.client.gui.roles.GuiNpcItemGiver;
import noppes.client.gui.roles.GuiNpcTraderSetup;
import noppes.client.gui.roles.GuiNpcTransporter;
import noppes.client.gui.util.GuiNPCInterface;
import noppes.client.model.EntityRenderLoader;
import noppes.client.renderer.NpcItemRenderer;
import noppes.common.CommonProxy;
import noppes.common.config.CustomNPCConfig;
import noppes.common.constants.EnumGuiType;
import noppes.common.containers.ContainerCarpentryBench;
import noppes.common.containers.ContainerManageBanks;
import noppes.common.containers.ContainerManageRecipes;
import noppes.common.containers.ContainerNPCBankInterface;
import noppes.common.containers.ContainerNPCFollower;
import noppes.common.containers.ContainerNPCFollowerHire;
import noppes.common.containers.ContainerNPCFollowerSetup;
import noppes.common.containers.ContainerNPCInv;
import noppes.common.containers.ContainerNPCTrader;
import noppes.common.containers.ContainerNPCTraderSetup;
import noppes.common.containers.ContainerNpcItemGiver;
import noppes.common.containers.ContainerNpcQuestReward;
import noppes.common.containers.ContainerNpcQuestTypeItem;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.model_entity.EntityElementalStaffFX;
import noppes.util.NoppesUtil;

public class ClientProxy
extends CommonProxy {
    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);
        new MusicController();
        EntityRenderLoader.loadEntityRender();
        TickRegistry.registerTickHandler((ITickHandler)new ClientTickHandler(), (Side)Side.CLIENT);
        EntityRenderLoader.loadTileEntityRender();
        ClientProxy.registerKeyBinding();
        MinecraftForge.EVENT_BUS.register((Object)new TextureLoadEvent());
        if (CustomNPCConfig.EnableUpdateChecker) {
            VersionChecker var10 = new VersionChecker();
            var10.start();
        }
    }

    public static void bindTexture(bjo location) {
        try {
            if (location == null) {
                return;
            }
            bim ex = atv.w().J();
            ex.a(location);
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    private static void registerKeyBinding() {
        atv mc = atv.w();
        ArrayList<ats> keybindings = new ArrayList<ats>();
        ats[] questBinding = mc.u.W;
        int keys = questBinding.length;
        keybindings.addAll(Arrays.asList(questBinding).subList(0, keys));
        ats var8 = new ats("Quest Log", 38);
        keybindings.add(var8);
        mc.u.W = keybindings.toArray(new ats[keybindings.size()]);
        ats[] var9 = new ats[]{var8};
        TickRegistry.registerTickHandler((ITickHandler)new ClientKeyHandler(var9, new boolean[]{false}), (Side)Side.CLIENT);
    }

    @Override
    public Object getClientGuiElement(int ID, uf player, abw world, int x, int y, int z) {
        if (ID > EnumGuiType.values().length) {
            return null;
        }
        EnumGuiType gui = EnumGuiType.values()[ID];
        EntityNPCInterface npc = NoppesUtil.getLastNpc();
        uy container = this.getContainer(gui, player, x, y, z, npc);
        return this.getGui(npc, gui, container);
    }

    private awe getGui(EntityNPCInterface npc, EnumGuiType gui, uy container) {
        DebugGadgets.debugInfo((String)gui.name());
        switch (gui) {
            case MainMenuDisplay: {
                if (npc != null) {
                    return new GuiNpcDisplay(npc);
                }
                System.out.println("Unable to find spawned npc");
                break;
            }
            case MainMenuStats: {
                return new GuiNpcStats(npc);
            }
            case MainMenuInv: {
                return new GuiNPCInv(npc, (ContainerNPCInv)container);
            }
            case MainMenuAdvanced: {
                return new GuiNpcAdvanced(npc);
            }
            case QuestReward: {
                return new GuiNpcQuestReward(npc, (ContainerNpcQuestReward)container);
            }
            case QuestItem: {
                return new GuiNpcQuestTypeItem(npc, (ContainerNpcQuestTypeItem)container);
            }
            case MovingPath: {
                return new GuiNpcPather(npc);
            }
            case ManageFactions: {
                return new GuiNPCManageFactions(npc);
            }
            case ManageTransport: {
                return new GuiNPCManageTransporters(npc);
            }
            case ManageRecipes: {
                return new GuiNpcManageRecipes(npc, (ContainerManageRecipes)container);
            }
            case ManageDialogs: {
                return new GuiNPCManageDialogs(npc);
            }
            case ManageQuests: {
                return new GuiNPCManageQuest(npc);
            }
            case ManageBanks: {
                return new GuiNPCManageBanks(npc, (ContainerManageBanks)container);
            }
            case MainMenuGlobal: {
                return new GuiNPCGlobalMainMenu(npc);
            }
            case MainMenuAI: {
                return new GuiNpcAI(npc);
            }
            case PlayerFollowerHire: {
                return new GuiNpcFollowerHire(npc, (ContainerNPCFollowerHire)container);
            }
            case PlayerFollower: {
                return new GuiNpcFollower(npc, (ContainerNPCFollower)container);
            }
            case PlayerTrader: {
                return new GuiNPCTrader(npc, (ContainerNPCTrader)container);
            }
            case PlayerBankSmall: 
            case PlayerBankUnlock: 
            case PlayerBankUprade: 
            case PlayerBankLarge: {
                return new GuiNPCBankChest(npc, (ContainerNPCBankInterface)container);
            }
            case PlayerTransporter: {
                return new GuiTransportSelection(npc);
            }
            case PlayerAnvil: {
                return new GuiNpcCarpentryBench((ContainerCarpentryBench)container);
            }
            case SetupFollower: {
                return new GuiNpcFollowerSetup(npc, (ContainerNPCFollowerSetup)container);
            }
            case SetupItemGiver: {
                return new GuiNpcItemGiver(npc, (ContainerNpcItemGiver)container);
            }
            case SetupTrader: {
                return new GuiNpcTraderSetup(npc, (ContainerNPCTraderSetup)container);
            }
            case SetupTransporter: {
                return new GuiNpcTransporter(npc);
            }
            case SetupBank: {
                return new GuiNpcBankSetup(npc);
            }
            case PlayerMailman: {
                return new GuiMailmanSend();
            }
            case PlayerMailbox: {
                return new GuiMailbox();
            }
            case MerchantAdd: {
                return new GuiMerchantAdd();
            }
            case NpcRemote: {
                if (atv.w().n != null) break;
                return new GuiNpcRemoteEditor();
            }
        }
        return null;
    }

    @Override
    public void openGui(int i, int j, int k, EnumGuiType gui, uf player) {
        atv minecraft = atv.w();
        if (minecraft.h == player) {
            GuiNPCInterface guiscreen = null;
            if (gui == EnumGuiType.RedstoneBlock) {
                guiscreen = new GuiNpcRedstoneBlock(i, j, k);
            }
            if (gui == EnumGuiType.MobSpawner) {
                guiscreen = new GuiNpcMobSpawner(i, j, k);
            }
            if (gui == EnumGuiType.MobSpawnerAdd) {
                guiscreen = new GuiNpcMobSpawnerAdd();
            }
            if (gui == EnumGuiType.Waypoint) {
                guiscreen = new GuiNpcWaypoint(i, j, k);
            }
            if (guiscreen != null) {
                minecraft.a((awe)guiscreen);
            }
        }
    }

    @Override
    public void openGui(EntityNPCInterface npc, EnumGuiType gui) {
        atv minecraft = atv.w();
        uy container = this.getContainer(gui, (uf)minecraft.h, 0, 0, 0, npc);
        awe guiscreen = this.getGui(npc, gui, container);
        if (guiscreen != null) {
            minecraft.a(guiscreen);
        }
    }

    @Override
    public void openGui(uf player, Object guiscreen) {
        atv minecraft = atv.w();
        if (player.q.I && guiscreen instanceof awe) {
            minecraft.a((awe)guiscreen);
        }
    }

    @Override
    public void spawnParticle(of player, String string, Object ... ob) {
        if (string.equals("Spell")) {
            int color = (Integer)ob[0];
            int number = (Integer)ob[1];
            for (int i = 0; i < number; ++i) {
                Random rand = player.q.s;
                double x = (rand.nextDouble() - 0.5) * (double)player.O;
                double y = player.f();
                double z = (rand.nextDouble() - 0.5) * (double)player.O;
                double f = (rand.nextDouble() - 0.5) * 2.0;
                double f1 = -rand.nextDouble();
                double f2 = (rand.nextDouble() - 0.5) * 2.0;
                atv.w().k.a((beg)new EntityElementalStaffFX(player, x, y, z, f, f1, f2, color));
            }
        }
    }

    @Override
    public boolean hasClient() {
        return true;
    }

    @Override
    public uf getPlayer() {
        return atv.w().h;
    }

    @Override
    public void registerItem(int itemID) {
        MinecraftForgeClient.registerItemRenderer((int)itemID, (IItemRenderer)new NpcItemRenderer());
    }

    @Override
    public Object loadResource(String texture) {
        return new bjo(texture);
    }
}

