package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet205ClientCommand extends Packet {

   public int forceRespawn;

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.forceRespawn = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.forceRespawn & 255);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleClientCommand(this);
   }

   public int getPacketSize() {
      return 1;
   }
}
