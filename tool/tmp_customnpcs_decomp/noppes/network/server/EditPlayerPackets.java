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
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.constants.EnumPlayerData;
import noppes.util.NoppesUtilServer;

public class EditPlayerPackets {

    public static class PlayerDataRemove
    extends RCsPacket {
        private int select;
        private String selectPlayer;
        private int itemId;

        public PlayerDataRemove(int select, String selectPlayer, int itemId) {
            this.select = select;
            this.selectPlayer = selectPlayer;
            this.itemId = itemId;
        }

        public PlayerDataRemove() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.select);
            out.writeUTF(this.selectPlayer);
            out.writeInt(this.itemId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.select = in.readInt();
            this.selectPlayer = in.readUTF();
            this.itemId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilServer.removePlayerData(this.select, this.selectPlayer, this.itemId, (jv)player);
        }
    }

    public static class PlayerDataGet
    extends RCsPacket {
        private int select;
        private String playerName;

        public PlayerDataGet(int select, String playerName) {
            this.select = select;
            this.playerName = playerName;
        }

        public PlayerDataGet() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.select);
            if (this.playerName == null) {
                out.writeUTF("");
            } else {
                out.writeUTF(this.playerName);
            }
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.select = in.readInt();
            this.playerName = in.readUTF();
            if ("".equals(this.playerName)) {
                this.playerName = null;
            }
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            if (EnumPlayerData.values().length <= this.select) {
                return;
            }
            EnumPlayerData dataType = EnumPlayerData.values()[this.select];
            NoppesUtilServer.sendPlayerData(dataType, (jv)player, this.playerName);
        }
    }
}

