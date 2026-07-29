package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet7UseEntity extends Packet {

   public int playerEntityId;
   public int targetEntity;
   public int isLeftClick;

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.playerEntityId = par1DataInput.readInt();
      this.targetEntity = par1DataInput.readInt();
      this.isLeftClick = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.playerEntityId);
      par1DataOutput.writeInt(this.targetEntity);
      par1DataOutput.writeByte(this.isLeftClick);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleUseEntity(this);
   }

   public int getPacketSize() {
      return 9;
   }
}
