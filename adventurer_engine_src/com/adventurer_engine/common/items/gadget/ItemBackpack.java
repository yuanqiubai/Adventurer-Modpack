/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  bkb
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ey
 *  ms
 *  mt
 *  uf
 *  yc
 *  ye
 */
package com.adventurer_engine.common.items.gadget;

import com.adventurer_engine.common.data.globaldata.IGlobalData;
import com.adventurer_engine.common.items.TabsLoader;
import com.adventurer_engine.network.client2server.ClientBackpackHandlerPacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

public class ItemBackpack
extends yc {
    public static final String BACKPACK_TAG_NAME = "backpackName";

    public ItemBackpack() {
        super(12300);
        this.d(1);
        this.a(TabsLoader.GADGETS);
        this.b("rcs_backpack");
    }

    public ye a(ye item, abw world, uf player) {
        if (world.I) {
            PacketDispatcher.sendPacketToServer((ey)new ClientBackpackHandlerPacket.RequestOpenBackpack().makePacket());
        }
        return item;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt register) {
        this.cz = register.a("adventurer_engine:backpack");
    }

    @SideOnly(value=Side.CLIENT)
    public ms h(ye item) {
        return this.cz;
    }

    public void a(ye itemStack, uf player, List list, boolean par4) {
        if (itemStack.p() && itemStack.q().b(BACKPACK_TAG_NAME)) {
            list.add(String.format(bkb.a((String)"adv.backpack.owner"), IGlobalData.BACKPACK_INVENTORY_DATA.getBackpackOwnerName(itemStack.q().i(BACKPACK_TAG_NAME))));
        }
    }
}

