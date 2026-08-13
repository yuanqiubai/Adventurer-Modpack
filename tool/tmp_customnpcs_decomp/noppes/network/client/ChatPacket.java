/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bu
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;

public class ChatPacket
extends RCsPacket {
    private String[] messages;

    public ChatPacket(String[] messages) {
        this.messages = messages;
    }

    public ChatPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.messages.length);
        for (String message : this.messages) {
            out.writeUTF(message);
        }
    }

    public void read(ByteArrayDataInput in) throws IOException {
        int len = in.readInt();
        this.messages = new String[len];
        for (int i = 0; i < len; ++i) {
            this.messages[i] = in.readUTF();
        }
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        StringBuilder merchantrecipeList = new StringBuilder();
        for (String i : this.messages) {
            merchantrecipeList.append(bu.a((String)i));
        }
        player.a(merchantrecipeList.toString());
    }
}

