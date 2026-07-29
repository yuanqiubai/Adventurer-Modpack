package net.minecraft.server;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableServerMemoryStats implements Callable {

   // $FF: synthetic field
   final MinecraftServer mcServer;

   public CallableServerMemoryStats(MinecraftServer par1MinecraftServer) {
      this.mcServer = par1MinecraftServer;
   }

   public String callServerMemoryStats() {
      return MinecraftServer.getServerConfigurationManager(this.mcServer).getCurrentPlayerCount() + " / " + MinecraftServer.getServerConfigurationManager(this.mcServer).getMaxPlayers() + "; " + MinecraftServer.getServerConfigurationManager(this.mcServer).playerEntityList;
   }

   // $FF: synthetic method
   public Object call() {
      return this.callServerMemoryStats();
   }
}
