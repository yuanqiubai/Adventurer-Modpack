package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.Packet10Flying;

public class Packet11PlayerPosition extends Packet10Flying {

   public Packet11PlayerPosition() {
      this.moving = true;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.xPosition = par1DataInput.readDouble();
      this.yPosition = par1DataInput.readDouble();
      this.stance = par1DataInput.readDouble();
      this.zPosition = par1DataInput.readDouble();
      super.readPacketData(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeDouble(this.xPosition);
      par1DataOutput.writeDouble(this.yPosition);
      par1DataOutput.writeDouble(this.stance);
      par1DataOutput.writeDouble(this.zPosition);
      super.writePacketData(par1DataOutput);
   }

   public int getPacketSize() {
      return 33;
   }
}
