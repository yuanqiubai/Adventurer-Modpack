/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abm
 *  abn
 *  abo
 *  aqz
 *  arz
 *  asj
 *  asp
 *  by
 *  ci
 *  com.adventurer_engine.util.DebugGadgets
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cv
 *  ea
 *  ey
 *  js
 *  jv
 *  ls
 *  net.minecraft.server.MinecraftServer
 *  nn
 *  uf
 */
package noppes.util;

import com.adventurer_engine.util.DebugGadgets;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import net.minecraft.server.MinecraftServer;
import noppes.CustomNpcs;
import noppes.common.constants.EnumGuiType;
import noppes.common.constants.EnumPacketType;
import noppes.common.constants.EnumPlayerData;
import noppes.common.constants.EnumRoleType;
import noppes.common.containers.ContainerManageBanks;
import noppes.common.containers.ContainerManageRecipes;
import noppes.common.controllers.Bank;
import noppes.common.controllers.BankController;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogCategory;
import noppes.common.controllers.DialogController;
import noppes.common.controllers.DialogOption;
import noppes.common.controllers.Faction;
import noppes.common.controllers.FactionController;
import noppes.common.controllers.PlayerBankData;
import noppes.common.controllers.PlayerData;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerDialogData;
import noppes.common.controllers.PlayerFactionData;
import noppes.common.controllers.PlayerQuestController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.PlayerTransportData;
import noppes.common.controllers.Quest;
import noppes.common.controllers.QuestCategory;
import noppes.common.controllers.QuestController;
import noppes.common.controllers.RecipeCarpentry;
import noppes.common.controllers.RecipeController;
import noppes.common.controllers.TransportCategory;
import noppes.common.controllers.TransportController;
import noppes.common.controllers.TransportLocation;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.roles.RoleTransporter;
import noppes.network.client.DeletePacket;
import noppes.network.client.DialogPacket;
import noppes.network.client.EditingNpcPacket;
import noppes.network.client.GuiPackets;
import noppes.network.client.ParticlePacket;
import noppes.network.client.SaveRolePacket;
import noppes.network.client.ScrollPackets;

public class NoppesUtilServer {
    private static final HashMap<String, EntityNPCInterface> selectedNpcs = new HashMap();
    private static final HashMap<String, Quest> editingQuests = new HashMap();

    public static byte[] CompoundToBytes(by compound, EnumPacketType type) {
        try {
            ByteArrayOutputStream e = new ByteArrayOutputStream();
            DataOutputStream out = NoppesUtilServer.getDataOutputStream(e);
            out.writeInt(type.ordinal());
            if (compound != null) {
                ci.a((by)compound, (DataOutput)out);
            }
            out.close();
            return e.toByteArray();
        }
        catch (IOException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static void setEditingNpc(uf player, EntityNPCInterface npc) {
        selectedNpcs.put(player.bu, npc);
        if (npc != null) {
            PacketDispatcher.sendPacketToPlayer((ey)new EditingNpcPacket(npc.k).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static EntityNPCInterface getEditingNpc(uf player) {
        return selectedNpcs.get(player.bu);
    }

    public static void setEditingQuest(uf player, Quest quest) {
        editingQuests.put(player.bu, quest);
    }

    public static Quest getEditingQuest(uf player) {
        return editingQuests.get(player.bu);
    }

    private static void sendRoleData(uf player, EntityNPCInterface npc) {
        if (npc.advanced.role != EnumRoleType.None) {
            by comp = new by();
            npc.roleInterface.writeEntityToNBT(comp);
            comp.a("EntityId", npc.k);
            comp.a("Role", npc.advanced.role.ordinal());
            PacketDispatcher.sendPacketToPlayer((ey)new SaveRolePacket(comp).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static void sendFactionDataAll(jv player) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Faction faction : FactionController.getInstance().factions.values()) {
            map.put(faction.name, faction.id);
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void sendBankDataAll(jv player) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Bank bank : BankController.getInstance().banks.values()) {
            map.put(bank.name, bank.id);
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void openDialog(uf player, EntityNPCInterface npc, Dialog dia) {
        Dialog dialog = dia.copy(player);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PacketDispatcher.sendPacketToPlayer((ey)new DialogPacket(npc.k, dialog.writeToNBT(new by())).makePacket("CNPCs Client"), (Player)((Player)player));
        dia.factionOptions.addPoints(player);
        if (dialog.hasQuest()) {
            PlayerQuestController.addActiveQuest(dialog.getQuest(), player);
        }
        if (!dialog.command.isEmpty()) {
            String data1 = dialog.command.replaceAll("@dp", player.bu);
            arz tile = new arz();
            tile.k = npc.q;
            tile.a(data1);
            tile.b("@" + npc.c_());
            tile.l = ls.c((double)npc.u);
            tile.m = ls.c((double)npc.v);
            tile.n = ls.c((double)npc.w);
            tile.a(npc.q);
        }
        if (dialog.mail.isValid()) {
            PlayerDataController.instance.addPlayerMessage(player.bu, dialog.mail);
        }
        PlayerDialogData data2 = PlayerDataController.instance.getPlayerData((uf)player).dialogData;
        data2.dialogsRead.add(dialog.id);
        NoppesUtilServer.setEditingNpc(player, npc);
    }

    public static DataOutputStream getDataOutputStream(ByteArrayOutputStream stream) throws IOException {
        return new DataOutputStream(new GZIPOutputStream(stream));
    }

    public static void sendOpenGui(uf player, EnumGuiType gui, EntityNPCInterface npc) {
        NoppesUtilServer.sendOpenGui(player, gui, npc, 0, 0, 0);
    }

    public static void sendOpenGui(uf player, EnumGuiType gui, EntityNPCInterface npc, int i, int j, int k) {
        if (player instanceof jv) {
            NoppesUtilServer.setEditingNpc(player, npc);
            NoppesUtilServer.sendExtraData(player, npc, gui, i, j, k);
            if (CustomNpcs.proxy.getServerGuiElement(gui.ordinal(), player, player.q, i, j, k) != null) {
                player.openGui((Object)CustomNpcs.instance, gui.ordinal(), player.q, i, j, k);
            } else {
                PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiPacket(gui.ordinal()).makePacket("CNPCs Client"), (Player)((Player)player));
                ArrayList<String> scrollDataStrings = NoppesUtilServer.getScrollData(player, gui, npc);
                if (scrollDataStrings != null && !scrollDataStrings.isEmpty()) {
                    PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollListPacket(scrollDataStrings).makePacket("CNPCs Client"), (Player)((Player)player));
                }
            }
        }
    }

    private static void sendExtraData(uf player, EntityNPCInterface npc, EnumGuiType gui, int i, int j, int k) {
        if (gui == EnumGuiType.PlayerFollower || gui == EnumGuiType.PlayerFollowerHire || gui == EnumGuiType.PlayerTrader || gui == EnumGuiType.PlayerTransporter) {
            NoppesUtilServer.sendRoleData(player, npc);
        }
    }

    private static ArrayList<String> getScrollData(uf player, EnumGuiType gui, EntityNPCInterface npc) {
        if (gui == EnumGuiType.PlayerTransporter) {
            RoleTransporter role = (RoleTransporter)npc.roleInterface;
            ArrayList<String> list = new ArrayList<String>();
            TransportLocation location = role.getLocation();
            String name = role.getLocation().name;
            for (TransportLocation transportLocation : location.category.getDefaultLocations()) {
                if (list.contains(transportLocation.name)) continue;
                list.add(transportLocation.name);
            }
            PlayerTransportData playerdata1 = PlayerDataController.instance.getPlayerData((uf)player).transportData;
            for (Integer integer : playerdata1.transports) {
                int i = integer;
                TransportLocation loc = TransportController.getInstance().getTransport(i);
                if (loc == null || !location.category.locations.containsKey(loc.id) || list.contains(loc.name)) continue;
                list.add(loc.name);
            }
            list.remove(name);
            return list;
        }
        return null;
    }

    public static void spawnParticle(nn entity, String particle, int dimension) {
        ParticlePacket packet = new ParticlePacket(entity.u, entity.v, entity.w, entity.P, entity.O, entity.N, particle);
        PacketDispatcher.sendPacketToAllAround((double)entity.u, (double)entity.v, (double)entity.w, (double)60.0, (int)dimension, (ey)packet.makePacket("CNPCs Client"));
    }

    public static void deleteNpc(EntityNPCInterface npc, uf player) {
        DeletePacket pkt = new DeletePacket(npc.k);
        PacketDispatcher.sendPacketToAllAround((double)npc.u, (double)npc.v, (double)npc.w, (double)256.0, (int)player.ar, (ey)pkt.makePacket("CNPCs Client"));
    }

    public static void createMobSpawner(DataInputStream dis, uf player) {
        try {
            int e = dis.readInt();
            int y = dis.readInt();
            int z2 = dis.readInt();
            by comp = ci.a((DataInput)dis);
            if (comp.i("id").equalsIgnoreCase("entityhorse")) {
                player.a(cv.d((String)"Currently you cant create horse spawner, its a minecraft bug"));
                return;
            }
            player.q.c(e, y, z2, aqz.ax.cF);
            asj tile = (asj)player.q.r(e, y, z2);
            abn logic = tile.a();
            logic.a(new abo(logic, comp, comp.i("id")));
        }
        catch (IOException var8) {
            var8.printStackTrace();
        }
    }

    public static void sendDialogCategoryData(jv player) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (DialogCategory category : DialogController.instance.categories.values()) {
            map.put(category.title, category.id);
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void sendQuestCategoryData(jv player) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (QuestCategory category : QuestController.instance.categories.values()) {
            map.put(category.title, category.id);
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void sendPlayerData(EnumPlayerData type, jv player, String name) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        if (type == EnumPlayerData.Players) {
            File toSend = PlayerDataController.instance.getSaveDir();
            for (File faction : toSend.listFiles()) {
                if (faction.isDirectory() || !faction.getName().endsWith(".dat")) continue;
                map.put(faction.getName().substring(0, faction.getName().length() - 4), 0);
            }
        } else {
            PlayerData var9 = new PlayerData(name);
            if (type == EnumPlayerData.Dialog) {
                PlayerDialogData var11 = var9.dialogData;
                for (int factionId : var11.dialogsRead) {
                    Dialog var20 = DialogController.instance.dialogs.get(factionId);
                    if (var20 == null) continue;
                    map.put(var20.category.title + ": " + var20.title, factionId);
                }
            } else if (type == EnumPlayerData.Quest) {
                Quest var18;
                PlayerQuestData var13 = var9.questData;
                for (int factionId : var13.activeQuests.keySet()) {
                    var18 = QuestController.instance.quests.get(factionId);
                    if (var18 == null) continue;
                    map.put(var18.category.title + ": " + var18.title + "(Active quest)", factionId);
                }
                for (int factionId : var13.finishedQuests.keySet()) {
                    var18 = QuestController.instance.quests.get(factionId);
                    if (var18 == null) continue;
                    map.put(var18.category.title + ": " + var18.title + "(Finished quest)", factionId);
                }
            } else if (type == EnumPlayerData.Transport) {
                PlayerTransportData var12 = var9.transportData;
                for (int factionId : var12.transports) {
                    TransportLocation var21 = TransportController.getInstance().getTransport(factionId);
                    if (var21 == null) continue;
                    map.put(var21.category.title + ": " + var21.name, factionId);
                }
            } else if (type == EnumPlayerData.Bank) {
                PlayerBankData var15 = var9.bankData;
                for (int factionId : var15.banks.keySet()) {
                    Bank var23 = BankController.getInstance().banks.get(factionId);
                    if (var23 == null) continue;
                    map.put(var23.name, factionId);
                }
            } else if (type == EnumPlayerData.Factions) {
                PlayerFactionData var14 = var9.factionData;
                for (int factionId : var14.factionData.keySet()) {
                    Faction var22 = FactionController.getInstance().factions.get(factionId);
                    if (var22 == null) continue;
                    map.put(var22.name + "(" + var14.getFactionPoints(factionId) + ")", factionId);
                }
            }
        }
        HashMap<String, Integer> subMap = new HashMap<String, Integer>();
        for (String str : map.keySet()) {
            if (subMap.size() == 1000) {
                PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(subMap).makePacket("CNPCs Client"), (Player)((Player)player));
                subMap = new HashMap();
            }
            subMap.put(str, (Integer)map.get(str));
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(subMap).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void removePlayerData(int id, String name, int itemId, jv player) {
        if (EnumPlayerData.values().length > id) {
            EnumPlayerData type = EnumPlayerData.values()[id];
            jv pl = MinecraftServer.F().af().f(name);
            PlayerData playerdata = pl == null ? new PlayerData(name) : PlayerDataController.instance.getPlayerData((uf)pl);
            switch (type) {
                case Players: {
                    DebugGadgets.debugInfo((String)("Delete Player Data: " + name));
                    File data = new File(PlayerDataController.instance.getSaveDir(), name + ".dat");
                    if (data.exists() && data.delete()) {
                        DebugGadgets.debugInfo((String)("Deleted: " + name + ".dat"));
                    }
                    if ((data = new File(PlayerDataController.instance.getSaveDir(), name + ".dat_old")).exists() && data.delete()) {
                        DebugGadgets.debugInfo((String)("Deleted: " + name + ".dat_old"));
                    }
                    if (pl == null) break;
                    playerdata.readNBT(new by());
                    NoppesUtilServer.sendPlayerData(type, player, name);
                    playerdata.saveNBTData(null);
                    break;
                }
                case Quest: {
                    PlayerQuestData data2 = playerdata.questData;
                    data2.activeQuests.remove(itemId);
                    data2.finishedQuests.remove(itemId);
                    playerdata.saveNBTData(null);
                    break;
                }
                case Dialog: {
                    PlayerDialogData data1 = playerdata.dialogData;
                    data1.dialogsRead.remove(itemId);
                    playerdata.saveNBTData(null);
                    break;
                }
                case Transport: {
                    PlayerTransportData data4 = playerdata.transportData;
                    data4.transports.remove(itemId);
                    playerdata.saveNBTData(null);
                    break;
                }
                case Bank: {
                    PlayerBankData data3 = playerdata.bankData;
                    data3.banks.remove(itemId);
                    playerdata.saveNBTData(null);
                    break;
                }
                case Factions: {
                    PlayerFactionData data5 = playerdata.factionData;
                    data5.factionData.remove(itemId);
                    playerdata.saveNBTData(null);
                }
            }
            NoppesUtilServer.sendPlayerData(type, player, name);
        }
    }

    public static void sendRecipeData(jv player, int size) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        if (size == 3) {
            Iterator<RecipeCarpentry> i$ = RecipeController.instance.globalRecipes.values().iterator();
            while (i$.hasNext()) {
                RecipeCarpentry recipeCarpentry;
                RecipeCarpentry recipe = recipeCarpentry = i$.next();
                map.put(recipe.name, recipe.id);
            }
        } else {
            Iterator<RecipeCarpentry> i$ = RecipeController.instance.anvilRecipes.values().iterator();
            while (i$.hasNext()) {
                RecipeCarpentry recipeCarpentry;
                RecipeCarpentry recipe = recipeCarpentry = i$.next();
                map.put(recipe.name, recipe.id);
            }
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void sendDialogData(jv player, DialogCategory category) {
        if (category != null) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (Dialog dialog : category.dialogs.values()) {
                map.put(dialog.title, dialog.id);
            }
            PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static void sendQuestData(jv player, QuestCategory category) {
        if (category != null) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (Quest quest : category.quests.values()) {
                map.put(quest.title, quest.id);
            }
            PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static void sendTransportCategoryData(jv player) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (TransportCategory category : TransportController.getInstance().categories.values()) {
            map.put(category.title, category.id);
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void sendTransportData(jv player, int categoryid) {
        TransportCategory category = TransportController.getInstance().categories.get(categoryid);
        if (category != null) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (TransportLocation transport : category.locations.values()) {
                map.put(transport.name, transport.id);
            }
            PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static void sendData(uf player, EnumPacketType enu, Object ... obs) {
        PacketDispatcher.sendPacketToPlayer((ey)NoppesUtilServer.getPacket(enu, obs), (Player)((Player)player));
    }

    public static void sendDataToAll(nn entity, EnumPacketType enu, Object ... obs) {
        ea packet = NoppesUtilServer.getPacket(enu, obs);
        ((js)entity.q).q().b(entity, (ey)packet);
    }

    public static ea getPacket(EnumPacketType enu, Object ... obs) {
        try {
            ByteArrayOutputStream e = new ByteArrayOutputStream();
            DataOutputStream out = NoppesUtilServer.getDataOutputStream(e);
            out.writeInt(enu.ordinal());
            for (Object ob : obs) {
                if (ob == null) continue;
                if (ob instanceof Map) {
                    Map map = (Map)ob;
                    for (Object o : map.keySet()) {
                        String key = (String)o;
                        int value = (Integer)map.get(key);
                        out.writeInt(value);
                        out.writeUTF(key);
                    }
                    continue;
                }
                if (ob instanceof Enum) {
                    out.writeInt(((Enum)ob).ordinal());
                    continue;
                }
                if (ob instanceof Double) {
                    out.writeDouble((Double)ob);
                    continue;
                }
                if (ob instanceof Float) {
                    out.writeFloat(((Float)ob).floatValue());
                    continue;
                }
                if (ob instanceof Integer) {
                    out.writeInt((Integer)ob);
                    continue;
                }
                if (ob instanceof String) {
                    out.writeUTF((String)ob);
                    continue;
                }
                if (ob instanceof by) {
                    ci.a((by)((by)ob), (DataOutput)out);
                    continue;
                }
                if (!(ob instanceof abm)) continue;
                ((abm)ob).a(out);
            }
            out.close();
            e.close();
            return new ea("CNPCs Client", e.toByteArray());
        }
        catch (IOException var12) {
            var12.printStackTrace();
            return null;
        }
    }

    public static void sendNpcDialogs(uf player) {
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
        if (npc != null) {
            for (Integer integer : npc.dialogs.keySet()) {
                int pos = integer;
                DialogOption option = npc.dialogs.get(pos);
                if (option == null || !option.hasDialog()) continue;
                by compound = option.writeNBT();
                compound.a("Position", pos);
                PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(compound).makePacket("CNPCs Client"), (Player)((Player)player));
            }
        }
    }

    public static DialogOption setNpcDialog(int slot, int dialogId, uf player) throws IOException {
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
        if (npc == null) {
            return null;
        }
        if (!npc.dialogs.containsKey(slot)) {
            npc.dialogs.put(slot, new DialogOption());
        }
        DialogOption option = npc.dialogs.get(slot);
        option.dialogId = dialogId;
        if (option.hasDialog()) {
            option.title = option.getDialog().title;
        }
        return option;
    }

    public static void saveTileEntity(jv player, by compound) {
        int z2;
        int y;
        int x = compound.e("x");
        asp tile = player.q.r(x, y = compound.e("y"), z2 = compound.e("z"));
        if (tile != null) {
            tile.a(compound);
        }
    }

    public static void setRecipeGui(jv player, RecipeCarpentry recipe) {
        if (recipe != null && player.bp instanceof ContainerManageRecipes) {
            ContainerManageRecipes container = (ContainerManageRecipes)player.bp;
            container.setRecipe(recipe);
            by nbt = recipe.writeNBT();
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static void sendBank(jv player, Bank bank) {
        by compound = new by();
        bank.writeEntityToNBT(compound);
        PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(compound).makePacket("CNPCs Client"), (Player)((Player)player));
        if (player.bp instanceof ContainerManageBanks) {
            ((ContainerManageBanks)player.bp).setBank(bank);
        }
        player.a(player.bp, player.bp.a());
    }

    public static void sendNearbyNpcs(jv player) {
        List npcs = player.q.a(EntityNPCInterface.class, player.E.b(64.0, 64.0, 64.0));
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (EntityNPCInterface o : npcs) {
            if (o.M) continue;
            float distance = player.d((nn)o);
            DecimalFormat df = new DecimalFormat("#.#");
            String s = df.format(distance);
            if (distance < 10.0f) {
                s = "0" + s;
            }
            map.put(s + " : " + o.display.name, o.k);
        }
        PacketDispatcher.sendPacketToPlayer((ey)new ScrollPackets.ScrollDataPacket(map).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void sendGuiError(uf player, int i) {
        PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiErrorPacket(i, new by()).makePacket("CNPCs Client"), (Player)((Player)player));
    }

    public static void sendGuiClose(jv player, int i, by comp) {
        PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiClosePacket(i, comp).makePacket("CNPCs Client"), (Player)((Player)player));
    }
}

