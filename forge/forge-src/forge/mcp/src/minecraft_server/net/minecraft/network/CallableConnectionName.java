package net.minecraft.network;

import java.util.concurrent.Callable;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.NetworkListenThread;

class CallableConnectionName implements Callable {

   // $FF: synthetic field
   final NetServerHandler field_111201_a;
   // $FF: synthetic field
   final NetworkListenThread field_111200_b;

   CallableConnectionName(NetworkListenThread par1NetworkListenThread, NetServerHandler par2NetServerHandler) {
      this.field_111200_b = par1NetworkListenThread;
      this.field_111201_a = par2NetServerHandler;
   }

   public String func_111199_a() {
      return this.field_111201_a.toString();
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_111199_a();
   }
}
