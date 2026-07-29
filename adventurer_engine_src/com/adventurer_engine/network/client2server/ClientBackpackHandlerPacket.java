/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bkb
 *  by
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  uf
 *  ye
 */
package com.adventurer_engine.network.client2server;

import com.adventurer_engine.AdventurerEngine;
import com.adventurer_engine.common.data.globaldata.IGlobalData;
import com.adventurer_engine.common.gui_handler.BackpackInventory;
import com.adventurer_engine.common.items.ItemLoader;
import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.network.server2client.SendBackpackData;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class ClientBackpackHandlerPacket {

    public static class AcknowledgeReceiveBackpackData
    extends RCsPacket {
        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            if (side.isClient()) {
                return;
            }
            player.openGui((Object)AdventurerEngine.instance, 1, player.q, player.bZ, player.ca, player.cb);
        }
    }

    public static class RequestOpenBackpack
    extends RCsPacket {
        @Override
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        @Override
        public void read(ByteArrayDataInput in) throws IOException {
        }

        @Override
        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            by nbt;
            if (side.isClient()) {
                return;
            }
            ye item = player.aZ();
            if (item.d != ItemLoader.itemBackpack.cv) {
                player.a(bkb.a((String)"adv.warn.not_the_backpack"));
                return;
            }
            if (!item.p()) {
                item.d(new by());
            }
            if (!(nbt = item.q()).b("backpackName")) {
                String newName = Tools.randomString();
                IGlobalData.BACKPACK_INVENTORY_DATA.allocateBackpackInventory(newName, player);
                nbt.a("backpackName", newName);
            }
            BackpackInventory inv = IGlobalData.BACKPACK_INVENTORY_DATA.getBackpackInventory(nbt.i("backpackName"), player);
            by invData = new by();
            inv.writeToNBT(invData);
            PacketDispatcher.sendPacketToPlayer((ey)new SendBackpackData(invData).makePacket(), (Player)((Player)player));
        }
    }
}

