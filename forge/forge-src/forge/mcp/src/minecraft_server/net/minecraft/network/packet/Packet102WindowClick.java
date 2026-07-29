package net.minecraft.network.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class Packet102WindowClick extends Packet {

   public int window_Id;
   public int inventorySlot;
   public int mouseClick;
   public short action;
   public ItemStack itemStack;
   public int holdingShift;

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleWindowClick(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.window_Id = par1DataInput.readByte();
      this.inventorySlot = par1DataInput.readShort();
      this.mouseClick = par1DataInput.readByte();
      this.action = par1DataInput.readShort();
      this.holdingShift = par1DataInput.readByte();
      this.itemStack = readItemStack(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.window_Id);
      par1DataOutput.writeShort(this.inventorySlot);
      par1DataOutput.writeByte(this.mouseClick);
      par1DataOutput.writeShort(this.action);
      par1DataOutput.writeByte(this.holdingShift);
      writeItemStack(this.itemStack, par1DataOutput);
   }

   public int getPacketSize() {
      return 11;
   }
}
