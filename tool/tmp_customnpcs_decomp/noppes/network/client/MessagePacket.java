/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bu
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  ko
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.client.quest.QuestAchievement;

public class MessagePacket
extends RCsPacket {
    public static final byte MESSAGE_QUEST_COMPLETE = 0;
    public static final byte MESSAGE_QUEST_NEW = 1;
    private byte status;
    private String title;

    public MessagePacket(byte status, String title) {
        this.status = status;
        this.title = title;
    }

    public MessagePacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeByte((int)this.status);
        out.writeUTF(this.title);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.status = in.readByte();
        this.title = in.readUTF();
    }

    private String getStatus() {
        switch (this.status) {
            case 0: {
                return "quest.completed";
            }
            case 1: {
                return "quest.newquest";
            }
        }
        return "quest.unknown";
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        StringBuilder merchantrecipeList = new StringBuilder(bu.a((String)this.getStatus()));
        QuestAchievement compound = new QuestAchievement(this.title, merchantrecipeList.toString());
        atv.w().q.a((ko)compound);
        atv.w().q.e = compound.e();
    }
}

