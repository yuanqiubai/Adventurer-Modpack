/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cm
 *  com.adventurer_engine.util.Tools
 *  cpw.mods.fml.common.network.IPacketHandler
 *  cpw.mods.fml.common.network.Player
 *  ea
 *  jv
 *  net.minecraft.server.MinecraftServer
 */
package noppes.network;

import com.adventurer_engine.util.Tools;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.util.Set;
import net.minecraft.server.MinecraftServer;
import noppes.common.config.CustomNPCConfig;

public class NPCPacketHandlerServer
implements IPacketHandler {
    public static final String NPC_SERVER_CHANNEL = "CNPCs Server";

    public void onPacketData(cm manager, ea packet, Player player) {
        if (!NPC_SERVER_CHANNEL.equals(packet.a)) {
            return;
        }
        Set set = MinecraftServer.F().af().i();
        jv playerMP = (jv)player;
        if (CustomNPCConfig.OpsOnly && !set.contains(playerMP.bu.toLowerCase())) {
            MinecraftServer.F().f(playerMP.bu + ": tried to use custom npcs without being an op");
        } else {
            Tools.handlePacket((cm)manager, (ea)packet, (Player)player);
        }
    }
}

