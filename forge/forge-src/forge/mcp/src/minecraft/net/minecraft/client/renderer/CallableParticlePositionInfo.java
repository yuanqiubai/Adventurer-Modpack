package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.crash.CrashReportCategory;

class CallableParticlePositionInfo implements Callable {

   // $FF: synthetic field
   final double posX;
   // $FF: synthetic field
   final double posY;
   // $FF: synthetic field
   final double posZ;
   // $FF: synthetic field
   final RenderGlobal globalRenderer;

   CallableParticlePositionInfo(RenderGlobal par1RenderGlobal, double par2, double par4, double par6) {
      this.globalRenderer = par1RenderGlobal;
      this.posX = par2;
      this.posY = par4;
      this.posZ = par6;
   }

   public String callParticlePositionInfo() {
      return CrashReportCategory.func_85074_a(this.posX, this.posY, this.posZ);
   }

   // $FF: synthetic method
   public Object call() {
      return this.callParticlePositionInfo();
   }
}
