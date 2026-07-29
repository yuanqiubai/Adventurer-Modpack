package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet14BlockDig extends Packet {

   public int xPosition;
   public int yPosition;
   public int zPosition;
   public int face;
   public int status;

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.status = par1DataInput.readUnsignedByte();
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readUnsignedByte();
      this.zPosition = par1DataInput.readInt();
      this.face = par1DataInput.readUnsignedByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.write(this.status);
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.write(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
      par1DataOutput.write(this.face);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleBlockDig(this);
   }

   public int getPacketSize() {
      return 11;
   }
}
