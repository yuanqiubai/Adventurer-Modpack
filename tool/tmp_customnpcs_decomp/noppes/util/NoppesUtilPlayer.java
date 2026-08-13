/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  acj
 *  arz
 *  by
 *  ci
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ea
 *  ey
 *  js
 *  jv
 *  ls
 *  mo
 *  net.minecraft.server.MinecraftServer
 *  nn
 *  ss
 *  uf
 *  uy
 *  ye
 */
package noppes.util;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.MinecraftServer;
import noppes.common.constants.EnumOptionType;
import noppes.common.constants.EnumPlayerPacket;
import noppes.common.constants.EnumRoleType;
import noppes.common.containers.ContainerNPCBankInterface;
import noppes.common.containers.ContainerNPCFollower;
import noppes.common.containers.ContainerNPCFollowerHire;
import noppes.common.controllers.Bank;
import noppes.common.controllers.BankController;
import noppes.common.controllers.BankData;
import noppes.common.controllers.Dialog;
import noppes.common.controllers.DialogController;
import noppes.common.controllers.DialogOption;
import noppes.common.controllers.Line;
import noppes.common.controllers.PlayerBankData;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestController;
import noppes.common.controllers.PlayerQuestData;
import noppes.common.controllers.PlayerTransportData;
import noppes.common.controllers.QuestData;
import noppes.common.controllers.TransportController;
import noppes.common.controllers.TransportLocation;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.quests.QuestLogData;
import noppes.common.roles.RoleFollower;
import noppes.network.client.GuiPackets;
import noppes.util.NoppesUtil;
import noppes.util.NoppesUtilServer;
import noppes.util.Tools;

public class NoppesUtilPlayer {
    public static void changeFollowerState(jv player, EntityNPCInterface npc) {
        RoleFollower role;
        uf owner;
        if (npc.advanced.role == EnumRoleType.Follower && (owner = (role = (RoleFollower)npc.roleInterface).getOwner()) != null && owner.bu.equals(player.bu)) {
            role.isFollowing = !role.isFollowing;
        }
    }

    public static void hireFollower(jv player, EntityNPCInterface npc) {
        uy con;
        if (npc.advanced.role == EnumRoleType.Follower && (con = player.bp) != null && con instanceof ContainerNPCFollowerHire) {
            ContainerNPCFollowerHire container = (ContainerNPCFollowerHire)con;
            RoleFollower role = (RoleFollower)npc.roleInterface;
            NoppesUtilPlayer.followerBuy(role, (mo)container.currencyMatrix, player, npc);
        }
    }

    public static void extendFollower(jv player, EntityNPCInterface npc) {
        uy con;
        if (npc.advanced.role == EnumRoleType.Follower && (con = player.bp) != null && con instanceof ContainerNPCFollower) {
            ContainerNPCFollower container = (ContainerNPCFollower)con;
            RoleFollower role = (RoleFollower)npc.roleInterface;
            NoppesUtilPlayer.followerBuy(role, container.currencyMatrix, player, npc);
        }
    }

    public static void transport(jv player, EntityNPCInterface npc, String location) {
        TransportLocation loc = TransportController.getInstance().getTransport(location);
        PlayerTransportData playerdata = PlayerDataController.instance.getPlayerData((uf)player).transportData;
        if (loc != null && (loc.isDefault() || playerdata.transports.contains(loc.id))) {
            if (player.ar != loc.dimension) {
                System.out.println(loc.dimension + " transfering");
                int dim = player.ar;
                MinecraftServer server = MinecraftServer.F();
                js wor = server.a(player.ar);
                player.a.a(loc.posX, loc.posY, loc.posZ, player.A, player.B);
                server.af().transferPlayerToDimension(player, loc.dimension, new acj(wor){

                    public void a(nn par1Entity, double par2, double par4, double par6, float par8) {
                    }
                });
                if (dim == 1 && player.T()) {
                    player.b(loc.posX, loc.posY, loc.posZ, player.A, player.B);
                    wor.d((nn)player);
                }
                player.q.a((nn)player, false);
            } else {
                player.a.a(loc.posX, loc.posY, loc.posZ, player.A, player.B);
                player.q.a((nn)player, false);
            }
        }
    }

    private static void followerBuy(RoleFollower role, mo currencyInv, jv player, EntityNPCInterface npc) {
        ye currency = currencyInv.a(0);
        if (currency == null) {
            return;
        }
        int maxMatchItemDays = 0;
        int maxMatchItemAmount = 1;
        for (Integer days : role.inventory.items.keySet()) {
            int hireAmount;
            ye hireItem = role.inventory.items.get(days);
            if (hireItem == null || !NoppesUtilPlayer.compareItems(hireItem, currency, false) || hireItem.b > currency.b || hireItem.h() && hireItem.k() != currency.k() || !role.rates.containsKey(days) || !((double)maxMatchItemDays / (double)maxMatchItemAmount < (double)(hireAmount = role.rates.get(days).intValue()) / (double)Math.max(1, hireItem.b))) continue;
            maxMatchItemDays = hireAmount;
            maxMatchItemAmount = Math.max(1, hireItem.b);
        }
        if (maxMatchItemDays == 0) {
            ss itemEntity = new ss(role.npc.q, role.npc.u, role.npc.v, role.npc.w, currency);
            role.npc.q.d((nn)itemEntity);
        } else {
            int currencyItemAmount = currency.b;
            int days = currencyItemAmount / maxMatchItemAmount * maxMatchItemDays;
            if (days == 0) {
                return;
            }
            if ((currencyItemAmount %= maxMatchItemAmount) == 0) {
                currencyInv.a(0, null);
            } else {
                currency.a(currency.b - currencyItemAmount);
                ss itemEntity = new ss(role.npc.q, role.npc.u, role.npc.v, role.npc.w, currency);
                role.npc.q.d((nn)itemEntity);
            }
            npc.say((uf)player, new Line(role.dialogHire.replaceAll("\\{days\\}", String.valueOf(days))));
            role.setOwner(player.bu);
            role.addDays(days);
        }
    }

    public static void bankUpgrade(jv player, EntityNPCInterface npc) {
        uy con;
        if (npc.advanced.role == EnumRoleType.Bank && (con = player.bp) != null && con instanceof ContainerNPCBankInterface) {
            ContainerNPCBankInterface container = (ContainerNPCBankInterface)con;
            Bank bank = BankController.getInstance().getBank(container.bankid);
            ye item = bank.upgradeInventory.a(container.slot);
            if (item != null) {
                int price = item.b;
                ye currency = container.currencyMatrix.a(0);
                if (currency != null && price <= currency.b) {
                    if (currency.b - price == 0) {
                        container.currencyMatrix.a(0, null);
                    } else {
                        currency.a(price);
                    }
                    PlayerBankData data = PlayerDataController.instance.getBankData((uf)player, bank.id);
                    BankData bankData = data.getBank(bank.id);
                    bankData.upgradedSlots.put(container.slot, true);
                    bankData.openBankGui((uf)player, npc, bank.id, container.slot);
                }
            }
        }
    }

    public static void bankUnlock(jv player, EntityNPCInterface npc) {
        uy con;
        if (npc.advanced.role == EnumRoleType.Bank && (con = player.bp) != null && con instanceof ContainerNPCBankInterface) {
            ContainerNPCBankInterface container = (ContainerNPCBankInterface)con;
            Bank bank = BankController.getInstance().getBank(container.bankid);
            ye item = bank.currencyInventory.a(container.slot);
            if (item != null) {
                int price = item.b;
                ye currency = container.currencyMatrix.a(0);
                if (currency != null && price <= currency.b) {
                    if (currency.b - price == 0) {
                        container.currencyMatrix.a(0, null);
                    } else {
                        currency.a(price);
                    }
                    PlayerBankData data = PlayerDataController.instance.getBankData((uf)player, bank.id);
                    BankData bankData = data.getBank(bank.id);
                    if (bankData.unlockedSlots + 1 <= bank.maxSlots) {
                        ++bankData.unlockedSlots;
                    }
                    bankData.openBankGui((uf)player, npc, bank.id, container.slot);
                }
            }
        }
    }

    public static void sendData(EnumPlayerPacket enu, Object ... obs) {
        try {
            ByteArrayOutputStream e = new ByteArrayOutputStream();
            DataOutputStream out = NoppesUtil.getDataOutputStream(e);
            out.writeInt(enu.ordinal());
            for (Object ob : obs) {
                if (ob instanceof Integer) {
                    out.writeInt((Integer)ob);
                    continue;
                }
                if (ob instanceof String) {
                    out.writeUTF((String)ob);
                    continue;
                }
                if (ob instanceof Long) {
                    out.writeLong((Long)ob);
                    continue;
                }
                if (!(ob instanceof by)) continue;
                ci.a((by)((by)ob), (DataOutput)out);
            }
            out.close();
            PacketDispatcher.sendPacketToServer((ey)new ea("CNPCs Player", e.toByteArray()));
        }
        catch (IOException var8) {
            var8.printStackTrace();
        }
    }

    public static void dialogSelected(int dialogId, int optionId, uf player, EntityNPCInterface npc) {
        DialogOption option;
        Dialog dialog = DialogController.instance.dialogs.get(dialogId);
        if (dialog != null && (dialog.hasDialogs(player) || dialog.hasOtherOptions()) && (option = dialog.options.get(optionId)) != null && (option.optionType != EnumOptionType.DialogOption || option.isAvailable(player) && option.hasDialog()) && option.optionType != EnumOptionType.Disabled) {
            if (option.optionType == EnumOptionType.RoleOption) {
                if (npc.roleInterface != null) {
                    npc.roleInterface.interact(player);
                }
            } else if (option.optionType == EnumOptionType.DialogOption) {
                NoppesUtilServer.openDialog(player, npc, option.getDialog());
            } else if (option.optionType == EnumOptionType.CommandBlock) {
                String command = option.command.replaceAll("@dp", player.bu);
                arz tile = new arz();
                tile.k = npc.q;
                tile.a(command);
                tile.b("@" + npc.c_());
                tile.l = ls.c((double)npc.u);
                tile.m = ls.c((double)npc.v);
                tile.n = ls.c((double)npc.w);
                tile.a(npc.q);
            }
        }
    }

    public static void sendQuestLogData(jv player) {
        if (PlayerQuestController.hasActiveQuests((uf)player)) {
            QuestLogData data = new QuestLogData();
            data.setData((uf)player);
            by nbt = data.writeNBT();
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(nbt).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static void questCompletion(jv player, int questId) {
        PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((uf)player).questData;
        QuestData data = playerdata.activeQuests.get(questId);
        if (data != null && data.quest.questInterface.isCompleted((uf)player)) {
            data.quest.questInterface.handleComplete((uf)player);
            if (data.quest.rewardExp > 0) {
                player.q.a((nn)player, "random.orb", 0.1f, 0.5f * ((player.q.s.nextFloat() - player.q.s.nextFloat()) * 0.7f + 1.8f));
                player.s(data.quest.rewardExp);
            }
            data.quest.factionOptions.addPoints((uf)player);
            if (data.quest.mail.isValid()) {
                PlayerDataController.instance.addPlayerMessage(player.bu, data.quest.mail);
            }
            for (ye item : data.quest.rewardItems.items.values()) {
                Tools.GivePlayerItem((nn)player, (uf)player, item);
            }
            PlayerQuestController.setQuestFinished(data.quest, (uf)player);
            if (data.quest.hasNewQuest()) {
                PlayerQuestController.addActiveQuest(data.quest.getNextQuest(), (uf)player);
            }
        }
    }

    public static boolean compareItems(ye a2, ye b, boolean ignoreDamage) {
        if (a2 == null || b == null) {
            return a2 == null == (b == null);
        }
        if (a2.d != b.d) {
            return false;
        }
        if (!ignoreDamage && a2.k() != b.k()) {
            return false;
        }
        if (a2.e == null || b.e == null) {
            return a2.e == null == (b.e == null);
        }
        return a2.e.equals((Object)b.e);
    }
}

