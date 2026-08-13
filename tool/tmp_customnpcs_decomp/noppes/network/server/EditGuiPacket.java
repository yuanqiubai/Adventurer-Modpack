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
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.constants.EnumGuiType;
import noppes.util.NoppesUtilServer;

public class EditGuiPacket
extends RCsPacket {
    private int guiId;
    private int x;
    private int y;
    private int z;

    public EditGuiPacket(int guiId, int x, int y, int z2) {
        this.guiId = guiId;
        this.x = x;
        this.y = y;
        this.z = z2;
    }

    public EditGuiPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        out.writeInt(this.guiId);
        out.writeInt(this.x);
        out.writeInt(this.y);
        out.writeInt(this.z);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.guiId = in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        EnumGuiType gui = EnumGuiType.values()[this.guiId];
        NoppesUtilServer.sendOpenGui(player, gui, NoppesUtilServer.getEditingNpc(player), this.x, this.y, this.z);
    }
}

