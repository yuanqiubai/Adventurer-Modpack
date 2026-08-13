/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  asp
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.CustomNpcs;
import noppes.common.constants.EnumGuiType;

public class WaypointSavePacket
extends RCsPacket {
    private by nbt;

    public WaypointSavePacket(by nbt) {
        this.nbt = nbt;
    }

    public WaypointSavePacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.nbt = Tools.readNBT((ByteArrayDataInput)in);
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        int x = this.nbt.e("x");
        int y = this.nbt.e("y");
        int z2 = this.nbt.e("z");
        asp tile = player.q.r(x, y, z2);
        tile.a(this.nbt);
        CustomNpcs.proxy.openGui(x, y, z2, EnumGuiType.Waypoint, player);
    }
}

