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
 *  uf
 */
package noppes.network.client;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.util.NoppesUtil;

public class QuestCompletionPacket
extends RCsPacket {
    private by nbt;

    public QuestCompletionPacket(by nbt) {
        this.nbt = nbt;
    }

    public QuestCompletionPacket() {
    }

    public void write(ByteArrayDataOutput out) throws IOException {
        Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
    }

    public void read(ByteArrayDataInput in) throws IOException {
        this.nbt = Tools.readNBT((ByteArrayDataInput)in);
    }

    public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
        NoppesUtil.guiQuestCompletion(player, this.nbt);
    }
}

