/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  ci
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  ey
 *  jv
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
import java.util.Iterator;
import noppes.common.controllers.PlayerDataController;
import noppes.common.controllers.PlayerMail;
import noppes.common.controllers.PlayerMailData;
import noppes.common.controllers.PlayerQuestController;
import noppes.network.client.GuiPackets;
import noppes.util.NoppesUtilServer;

public class MailPackets {

    public static class MailReadPacket
    extends RCsPacket {
        private long time;
        private String sender;

        public MailReadPacket(long time, String sender) {
            this.time = time;
            this.sender = sender;
        }

        public MailReadPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeLong(this.time);
            out.writeUTF(this.sender);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.time = in.readInt();
            this.sender = in.readUTF();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            PlayerMailData mailData = PlayerDataController.instance.getPlayerData((uf)player).mailData;
            Iterator<PlayerMail> i$ = mailData.playermail.iterator();
            while (i$.hasNext()) {
                PlayerMail playerMail;
                PlayerMail mail = playerMail = i$.next();
                if (mail.time != this.time || !mail.sender.equals(this.sender)) continue;
                mail.beenRead = true;
                if (!mail.hasQuest()) continue;
                PlayerQuestController.addActiveQuest(mail.getQuest(), player);
            }
        }
    }

    public static class MailSendPacket
    extends RCsPacket {
        private String username;
        private by message;

        public MailSendPacket(String username, by message) {
            this.username = username;
            this.message = message;
        }

        public MailSendPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeUTF(this.username);
            byte[] data = ci.a((by)this.message);
            out.writeInt(data.length);
            out.write(data);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.username = in.readUTF();
            int length = in.readInt();
            byte[] data = new byte[length];
            in.readFully(data);
            this.message = ci.a((byte[])data);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            String hasPlayer = PlayerDataController.instance.hasPlayer(this.username);
            if (hasPlayer.isEmpty()) {
                NoppesUtilServer.sendGuiError(player, 0);
                return;
            }
            PlayerMail mail2 = new PlayerMail();
            mail2.readNBT(this.message);
            if (mail2.subject.isEmpty()) {
                NoppesUtilServer.sendGuiError(player, 1);
                return;
            }
            PlayerDataController.instance.addPlayerMessage(hasPlayer, mail2);
            by usrNameNBT = new by();
            usrNameNBT.a("username", hasPlayer);
            NoppesUtilServer.sendGuiClose((jv)player, 1, usrNameNBT);
        }
    }

    public static class MailDeletePacket
    extends RCsPacket {
        private long time;
        private String sender;

        public MailDeletePacket(long time, String sender) {
            this.time = time;
            this.sender = sender;
        }

        public MailDeletePacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeLong(this.time);
            out.writeUTF(this.sender);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.time = in.readInt();
            this.sender = in.readUTF();
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            PlayerMailData mailData = PlayerDataController.instance.getPlayerData((uf)player).mailData;
            Iterator<PlayerMail> it = mailData.playermail.iterator();
            while (it.hasNext()) {
                PlayerMail mail = it.next();
                if (mail.time != this.time || !mail.sender.equals(this.sender)) continue;
                it.remove();
            }
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(mailData.writeNBT(new by())).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }

    public static class MailGetPacket
    extends RCsPacket {
        public void write(ByteArrayDataOutput out) throws IOException {
        }

        public void read(ByteArrayDataInput in) throws IOException {
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            PlayerMailData mailData = PlayerDataController.instance.getPlayerData((uf)player).mailData;
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiDataPacket(mailData.writeNBT(new by())).makePacket("CNPCs Client"), (Player)((Player)player));
        }
    }
}

