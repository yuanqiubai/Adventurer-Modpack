/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bdd
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
import java.util.Random;

public class ParticlePacket
extends RCsPacket {
    private double posX;
    private double posY;
    private double posZ;
    private float height;
    private float width;
    private float yOffset;
    private String particleName;

    public ParticlePacket(double posX, double posY, double posZ, float height, float width, float yOffset, String particleName) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.height = height;
        this.width = width;
        this.yOffset = yOffset;
        this.particleName = particleName;
    }

    public ParticlePacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeDouble(this.posX);
        out.writeDouble(this.posY);
        out.writeDouble(this.posZ);
        out.writeFloat(this.height);
        out.writeFloat(this.width);
        out.writeFloat(this.yOffset);
        out.writeUTF(this.particleName);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.posX = in.readDouble();
        this.posY = in.readDouble();
        this.posZ = in.readDouble();
        this.height = in.readFloat();
        this.width = in.readFloat();
        this.yOffset = in.readFloat();
        this.particleName = in.readUTF();
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        bdd worldObj = atv.w().f;
        Random rand = worldObj.s;
        if (this.particleName.equals("heal")) {
            for (int k = 0; k < 6; ++k) {
                worldObj.a("instantSpell", this.posX + (rand.nextDouble() - 0.5) * (double)this.width, this.posY + rand.nextDouble() * (double)this.height - (double)this.yOffset, this.posZ + (rand.nextDouble() - 0.5) * (double)this.width, 0.0, 0.0, 0.0);
                worldObj.a("spell", this.posX + (rand.nextDouble() - 0.5) * (double)this.width, this.posY + rand.nextDouble() * (double)this.height - (double)this.yOffset, this.posZ + (rand.nextDouble() - 0.5) * (double)this.width, 0.0, 0.0, 0.0);
            }
        }
    }
}

