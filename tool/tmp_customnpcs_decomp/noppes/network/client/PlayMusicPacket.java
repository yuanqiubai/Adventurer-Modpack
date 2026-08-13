/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
import noppes.client.controllers.MusicController;

public class PlayMusicPacket
extends RCsPacket {
    private String music;

    public PlayMusicPacket(String music) {
        this.music = music;
    }

    public PlayMusicPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeUTF(this.music);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.music = in.readUTF();
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        MusicController.Instance.playMusic("customnpcs:" + this.music);
    }
}

