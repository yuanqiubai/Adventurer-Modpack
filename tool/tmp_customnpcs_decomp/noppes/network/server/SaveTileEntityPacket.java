/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  jv
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.util.NoppesUtilServer;

public class SaveTileEntityPacket
extends RCsPacket {
    private by nbt;

    public SaveTileEntityPacket(by nbt) {
        this.nbt = nbt;
    }

    public SaveTileEntityPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.nbt = Tools.readNBT((ByteArrayDataInput)in);
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        NoppesUtilServer.saveTileEntity((jv)player, this.nbt);
    }
}

