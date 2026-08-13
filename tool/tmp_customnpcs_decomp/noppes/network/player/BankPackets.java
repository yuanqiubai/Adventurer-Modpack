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
import noppes.common.controllers.BankData;
import noppes.common.controllers.PlayerDataController;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtilPlayer;
import noppes.util.NoppesUtilServer;

public class BankPackets {

    public static class BankSlotOpenPacket
    extends RCsPacket {
        private int mail;
        private int username;

        public BankSlotOpenPacket(int mail, int username) {
            this.mail = mail;
            this.username = username;
        }

        public BankSlotOpenPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.mail);
            out.writeInt(this.username);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.mail = in.readInt();
            this.username = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (npc == null) {
                return;
            }
            BankData data = PlayerDataController.instance.getBankData(player, this.username).getBankOrDefault(this.username);
            data.openBankGui(player, npc, this.username, this.mail);
        }
    }

    public static class BankUnlockPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            if (side.isClient()) {
                return;
            }
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (npc == null) {
                return;
            }
            NoppesUtilPlayer.bankUnlock((jv)player, npc);
        }
    }

    public static class BankUpgradePacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            if (side.isClient()) {
                return;
            }
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if (npc == null) {
                return;
            }
            NoppesUtilPlayer.bankUpgrade((jv)player, npc);
        }
    }
}

