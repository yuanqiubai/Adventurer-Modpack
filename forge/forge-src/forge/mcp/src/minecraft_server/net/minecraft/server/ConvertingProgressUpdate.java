package net.minecraft.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;

public class ConvertingProgressUpdate implements IProgressUpdate {

   private long field_96245_b;
   // $FF: synthetic field
   final MinecraftServer mcServer;

   public ConvertingProgressUpdate(MinecraftServer par1MinecraftServer) {
      this.mcServer = par1MinecraftServer;
      this.field_96245_b = MinecraftServer.getSystemTimeMillis();
   }

   public void displayProgressMessage(String par1Str) {}

   public void setLoadingProgress(int par1) {
      if(MinecraftServer.getSystemTimeMillis() - this.field_96245_b >= 1000L) {
         this.field_96245_b = MinecraftServer.getSystemTimeMillis();
         this.mcServer.getLogAgent().logInfo("Converting... " + par1 + "%");
      }

   }

   public void resetProgresAndWorkingMessage(String par1Str) {}
}
