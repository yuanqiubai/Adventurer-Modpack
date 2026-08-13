/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  cg
 *  cl
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  uf
 *  uy
 *  ye
 */
package noppes.common.controllers;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import noppes.common.constants.EnumGuiType;
import noppes.common.containers.ContainerNPCBankInterface;
import noppes.common.containers.NpcMiscInventory;
import noppes.common.controllers.Bank;
import noppes.common.controllers.BankController;
import noppes.common.entity.EntityNPCInterface;
import noppes.network.client.GuiPackets;
import noppes.util.NBTTags;
import noppes.util.NoppesUtilServer;

public class BankData {
    public HashMap<Integer, NpcMiscInventory> itemSlots = new HashMap();
    public HashMap<Integer, Boolean> upgradedSlots = new HashMap();
    public int unlockedSlots = 0;
    public int bankId = -1;

    public BankData() {
        for (int i = 0; i < 6; ++i) {
            this.itemSlots.put(i, new NpcMiscInventory(54));
            this.upgradedSlots.put(i, false);
        }
    }

    public void readNBT(by nbttagcompound) {
        this.bankId = nbttagcompound.e("DataBankId");
        this.unlockedSlots = nbttagcompound.e("UnlockedSlots");
        this.itemSlots = this.getItemSlots(nbttagcompound.m("BankInv"));
        this.upgradedSlots = NBTTags.getBooleanList(nbttagcompound.m("UpdatedSlots"));
    }

    private HashMap<Integer, NpcMiscInventory> getItemSlots(cg tagList) {
        HashMap<Integer, NpcMiscInventory> list = new HashMap<Integer, NpcMiscInventory>();
        for (int i = 0; i < tagList.c(); ++i) {
            by nbttagcompound = (by)tagList.b(i);
            int slot = nbttagcompound.e("Slot");
            NpcMiscInventory inv = new NpcMiscInventory(54);
            inv.setFromNBT(nbttagcompound.l("BankItems"));
            list.put(slot, inv);
        }
        return list;
    }

    public void writeNBT(by nbttagcompound) {
        nbttagcompound.a("DataBankId", this.bankId);
        nbttagcompound.a("UnlockedSlots", this.unlockedSlots);
        nbttagcompound.a("UpdatedSlots", (cl)NBTTags.nbtBooleanList(this.upgradedSlots));
        nbttagcompound.a("BankInv", (cl)this.nbtItemSlots(this.itemSlots));
    }

    private cg nbtItemSlots(HashMap<Integer, NpcMiscInventory> items) {
        cg list = new cg();
        for (int slot : items.keySet()) {
            by nbttagcompound = new by();
            nbttagcompound.a("Slot", slot);
            nbttagcompound.a("BankItems", items.get(slot).getToNBT());
            list.a((cl)nbttagcompound);
        }
        return list;
    }

    public boolean isUpgraded(Bank bank, int slot) {
        return bank.isUpgraded(slot) || bank.canBeUpgraded(slot) && this.upgradedSlots.get(slot) != false;
    }

    public void openBankGui(uf player, EntityNPCInterface npc, int bankId, int slot) {
        Bank bank = BankController.getInstance().banks.get(bankId);
        if (bank == null) {
            bank = BankController.getInstance().banks.values().iterator().next();
        }
        if (bank.getMaxSlots() > slot) {
            if (bank.startSlots > this.unlockedSlots) {
                this.unlockedSlots = bank.startSlots;
            }
            ye currency = null;
            if (this.unlockedSlots <= slot) {
                currency = bank.currencyInventory.a(slot);
                NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUnlock, npc, slot, bank.id, 0);
            } else if (this.isUpgraded(bank, slot)) {
                NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankLarge, npc, slot, bank.id, 0);
            } else if (bank.canBeUpgraded(slot)) {
                currency = bank.upgradeInventory.a(slot);
                NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUprade, npc, slot, bank.id, 0);
            } else {
                NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankSmall, npc, slot, bank.id, 0);
            }
            by compound = new by();
            compound.a("MaxSlots", bank.getMaxSlots());
            compound.a("UnlockedSlots", this.unlockedSlots);
            if (currency != null) {
                compound.a("Currency", currency.b(new by()));
                ContainerNPCBankInterface container = this.getContainer(player);
                container.currency.item = currency;
            }
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(compound).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    private ContainerNPCBankInterface getContainer(uf player) {
        uy con = player.bp;
        return con != null && con instanceof ContainerNPCBankInterface ? (ContainerNPCBankInterface)con : null;
    }
}

