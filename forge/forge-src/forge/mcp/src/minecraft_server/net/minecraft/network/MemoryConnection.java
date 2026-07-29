package net.minecraft.network;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import net.minecraft.logging.ILogAgent;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class MemoryConnection implements INetworkManager {

   private static final SocketAddress mySocketAddress = new InetSocketAddress("127.0.0.1", 0);
   private List readPacketCache;
   private ILogAgent field_98214_c;
   private MemoryConnection pairedConnection;
   private NetHandler myNetHandler;
   private boolean shuttingDown;
   private String shutdownReason;
   private Object[] field_74439_g;

   public void setNetHandler(NetHandler par1NetHandler) {
      this.myNetHandler = par1NetHandler;
   }

   public void addToSendQueue(Packet par1Packet) {
      if(!this.shuttingDown) {
         this.pairedConnection.processOrCachePacket(par1Packet);
      }
   }

   public void wakeThreads() {}

   public void processReadPackets() {
      int var1 = 2500;

      while(var1-- >= 0 && !this.readPacketCache.isEmpty()) {
         Packet var2 = (Packet)this.readPacketCache.remove(0);
         var2.processPacket(this.myNetHandler);
      }

      if(this.readPacketCache.size() > var1) {
         this.field_98214_c.logWarning("Memory connection overburdened; after processing 2500 packets, we still have " + this.readPacketCache.size() + " to go!");
      }

      if(this.shuttingDown && this.readPacketCache.isEmpty()) {
         this.myNetHandler.handleErrorMessage(this.shutdownReason, this.field_74439_g);
      }

   }

   public SocketAddress getSocketAddress() {
      return mySocketAddress;
   }

   public void serverShutdown() {
      this.shuttingDown = true;
   }

   public void networkShutdown(String par1Str, Object ... par2ArrayOfObj) {
      this.shuttingDown = true;
      this.shutdownReason = par1Str;
      this.field_74439_g = par2ArrayOfObj;
   }

   public int packetSize() {
      return 0;
   }

   public void processOrCachePacket(Packet par1Packet) {
      if(par1Packet.canProcessAsync() && this.myNetHandler.canProcessPacketsAsync()) {
         par1Packet.processPacket(this.myNetHandler);
      } else {
         this.readPacketCache.add(par1Packet);
      }

   }

}
