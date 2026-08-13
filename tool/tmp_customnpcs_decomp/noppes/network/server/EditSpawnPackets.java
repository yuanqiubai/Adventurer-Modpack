/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abn
 *  abo
 *  abw
 *  aqz
 *  asj
 *  by
 *  com.adventurer_engine.network.RCsPacket
 *  com.adventurer_engine.network.RCsPacket$ProtocolException
 *  com.adventurer_engine.util.Tools
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cv
 *  nn
 *  nt
 *  uf
 */
package noppes.network.server;

import com.adventurer_engine.network.RCsPacket;
import com.adventurer_engine.util.Tools;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import java.io.IOException;
import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtilServer;

public class EditSpawnPackets {

    public static class MobSpawnerPacket
    extends RCsPacket {
        private int x;
        private int y;
        private int z;
        by nbt;

        public MobSpawnerPacket(int x, int y, int z2, by nbt) {
            this.x = x;
            this.y = y;
            this.z = z2;
            this.nbt = nbt;
        }

        public MobSpawnerPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.x);
            out.writeInt(this.y);
            out.writeInt(this.z);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.x = in.readInt();
            this.y = in.readInt();
            this.z = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            if (this.nbt.i("id").equalsIgnoreCase("entityhorse")) {
                player.a(cv.d((String)"Currently you cant create horse spawner, its a minecraft bug"));
                return;
            }
            player.q.c(this.x, this.y, this.z, aqz.ax.cF);
            asj tile = (asj)player.q.r(this.x, this.y, this.z);
            abn logic = tile.a();
            logic.a(new abo(logic, this.nbt, this.nbt.i("id")));
        }
    }

    public static class SpawnNpcPacket
    extends RCsPacket {
        private int posX;
        private int posY;
        private int posZ;
        by nbt;

        public SpawnNpcPacket(int posX, int posY, int posZ, by nbt) {
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.nbt = nbt;
        }

        public SpawnNpcPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            out.writeInt(this.posX);
            out.writeInt(this.posY);
            out.writeInt(this.posZ);
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.posX = in.readInt();
            this.posY = in.readInt();
            this.posZ = in.readInt();
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            EntityNPCInterface npc = (EntityNPCInterface)nt.a((by)this.nbt, (abw)player.q);
            npc.startPos = new int[]{this.posX, this.posY, this.posZ};
            npc.b((float)this.posX + 0.5f, npc.getStartYPos(), (float)this.posZ + 0.5f);
            npc.g(npc.aT());
            player.q.d((nn)npc);
            NoppesUtilServer.setEditingNpc(player, npc);
        }
    }

    public static class SpawnMobPacket
    extends RCsPacket {
        private by nbt;

        public SpawnMobPacket(by nbt) {
            this.nbt = nbt;
        }

        public SpawnMobPacket() {
        }

        public void write(ByteArrayDataOutput out) throws IOException {
            Tools.writeNBT((ByteArrayDataOutput)out, (by)this.nbt);
        }

        public void read(ByteArrayDataInput in) throws IOException {
            this.nbt = Tools.readNBT((ByteArrayDataInput)in);
        }

        public void execute(uf player, Side side) throws RCsPacket.ProtocolException {
            player.q.d(nt.a((by)this.nbt, (abw)player.q));
        }
    }
}

