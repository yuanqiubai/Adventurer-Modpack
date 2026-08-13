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
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerQuestData;
import noppes.util.NoppesUtilPlayer;

public class QuestPackets {

    public static class QuestCompletionPacket
    extends RCsPacket {
        private int questId;

        public QuestCompletionPacket(int questId) {
            this.questId = questId;
        }

        public QuestCompletionPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.questId);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.questId = in.readInt();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilPlayer.questCompletion((jv)player, this.questId);
        }
    }

    public static class QuestLogPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            NoppesUtilPlayer.sendQuestLogData((jv)player);
        }
    }

    public static class CheckQuestCompletionPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            PlayerQuestData questData = PlayerDataController.instance.getPlayerData((uf)player).questData;
            questData.checkQuestCompletion(player, null);
        }
    }
}

