/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cm
 *  cpw.mods.fml.common.network.IPacketHandler
 *  cpw.mods.fml.common.network.Player
 *  ea
 */
package com.adventurer_engine.network;

import com.adventurer_engine.util.Tools;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ADEPacketHandler
implements IPacketHandler {
    public void onPacketData(cm manager, ea packet, Player player) {
        Tools.handlePacket(manager, packet, player);
    }
}

