package net.minecraft.server.dedicated;

import net.minecraft.server.dedicated.DedicatedServer;

class DedicatedServerSleepThread extends Thread {

   // $FF: synthetic field
   final DedicatedServer theDecitatedServer;

   DedicatedServerSleepThread(DedicatedServer par1DedicatedServer) {
      this.theDecitatedServer = par1DedicatedServer;
      this.setDaemon(true);
      this.start();
   }

   public void run() {
      while(true) {
         try {
            while(true) {
               Thread.sleep(2147483647L);
            }
         } catch (InterruptedException var2) {
            ;
         }
      }
   }
}
