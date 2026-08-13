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

public class PlaySoundPacket
extends RCsPacket {
    private String sound;
    private float posX;
    private float posY;
    private float posZ;

    public PlaySoundPacket(String sound, float posX, float posY, float posZ) {
        this.sound = sound;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public PlaySoundPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeUTF(this.sound);
        out.writeFloat(this.posX);
        out.writeFloat(this.posY);
        out.writeFloat(this.posZ);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.sound = in.readUTF();
        this.posX = in.readFloat();
        this.posY = in.readFloat();
        this.posZ = in.readFloat();
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        MusicController.Instance.playSound(this.sound, this.posX, this.posY, this.posZ);
    }
}

