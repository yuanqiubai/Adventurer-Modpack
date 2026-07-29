package net.minecraft.network;

import java.util.concurrent.Callable;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet;

class CallablePacketClass implements Callable {

   // $FF: synthetic field
   final Packet thePacket;
   // $FF: synthetic field
   final NetServerHandler theNetServerHandler;

   CallablePacketClass(NetServerHandler par1NetServerHandler, Packet par2Packet) {
      this.theNetServerHandler = par1NetServerHandler;
      this.thePacket = par2Packet;
   }

   public String getPacketClass() {
      return this.thePacket.getClass().getCanonicalName();
   }

   // $FF: synthetic method
   public Object call() {
      return this.getPacketClass();
   }
}
