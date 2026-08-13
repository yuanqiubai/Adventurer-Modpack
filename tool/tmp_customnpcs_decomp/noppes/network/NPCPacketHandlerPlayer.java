/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cm
 *  com.adventurer_engine.util.Tools
 *  cpw.mods.fml.common.network.IPacketHandler
 *  cpw.mods.fml.common.network.Player
 *  ea
 */
package noppes.network;

import com.adventurer_engine.util.Tools;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class NPCPacketHandlerPlayer
implements IPacketHandler {
    public static final String NPC_PLAYER_CHANNEL = "CNPCs Player";

    public void onPacketData(cm manager, ea packet, Player player) {
        if (!NPC_PLAYER_CHANNEL.equals(packet.a)) {
            return;
        }
        Tools.handlePacket((cm)manager, (ea)packet, (Player)player);
    }
}

