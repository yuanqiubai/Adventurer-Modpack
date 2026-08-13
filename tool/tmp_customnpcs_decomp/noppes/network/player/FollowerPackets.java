/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  jv
 *  uf
 */
package noppes.network.player;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtilPlayer;
import noppes.util.NoppesUtilServer;

public class FollowerPackets {

    public static class FollowerStatePacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (npc == null) {
                return;
            }
            NoppesUtilPlayer.changeFollowerState((jv)player, npc);
        }
    }

    public static class FollowerHirePacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (npc == null) {
                return;
            }
            NoppesUtilPlayer.hireFollower((jv)player, npc);
        }
    }

    public static class FollowerExtendPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (npc == null) {
                return;
            }
            NoppesUtilPlayer.extendFollower((jv)player, npc);
        }
    }
}

