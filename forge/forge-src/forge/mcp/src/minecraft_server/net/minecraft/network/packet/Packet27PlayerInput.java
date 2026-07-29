package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet27PlayerInput extends Packet {

   private float field_111017_a;
   private float field_111015_b;
   private boolean field_111016_c;
   private boolean field_111014_d;

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.field_111017_a = par1DataInput.readFloat();
      this.field_111015_b = par1DataInput.readFloat();
      this.field_111016_c = par1DataInput.readBoolean();
      this.field_111014_d = par1DataInput.readBoolean();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeFloat(this.field_111017_a);
      par1DataOutput.writeFloat(this.field_111015_b);
      par1DataOutput.writeBoolean(this.field_111016_c);
      par1DataOutput.writeBoolean(this.field_111014_d);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.func_110774_a(this);
   }

   public int getPacketSize() {
      return 10;
   }

   public float func_111010_d() {
      return this.field_111017_a;
   }

   public float func_111012_f() {
      return this.field_111015_b;
   }

   public boolean func_111013_g() {
      return this.field_111016_c;
   }

   public boolean func_111011_h() {
      return this.field_111014_d;
   }
}
