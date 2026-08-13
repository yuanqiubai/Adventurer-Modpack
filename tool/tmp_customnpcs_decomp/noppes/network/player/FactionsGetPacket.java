/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  uf
 */
package noppes.network.player;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerFactionData;
import noppes.network.client.GuiPackets;

public class FactionsGetPacket
extends RCsPacket {
    public void write(ByteArrayDataOutput out) throws IOException {
    }

    public void read(ByteArrayDataInput in) throws IOException {
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        PlayerFactionData factionData = PlayerDataController.instance.getPlayerData((uf)player).factionData;
        PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(factionData.getPlayerGuiData()).makePacket("CNPCs Client"), (Player)((Player)player));
    }
}

