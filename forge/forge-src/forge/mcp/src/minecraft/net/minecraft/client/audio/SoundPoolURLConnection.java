package net.minecraft.client.audio;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolProtocolHandler;
import net.minecraft.util.ResourceLocation;

class SoundPoolURLConnection extends URLConnection {

   private final ResourceLocation field_110659_b;
   // $FF: synthetic field
   final SoundPool theSoundPool;

   private SoundPoolURLConnection(SoundPool par1SoundPool, URL par2URL) {
      super(par2URL);
      this.theSoundPool = par1SoundPool;
      this.field_110659_b = new ResourceLocation(par2URL.getPath());
   }

   public void connect() {}

   public InputStream getInputStream() throws IOException {
      return SoundPool.func_110655_a(this.theSoundPool).getResource(this.field_110659_b).getInputStream();
   }

   // $FF: synthetic method
   SoundPoolURLConnection(SoundPool par1SoundPool, URL par2URL, SoundPoolProtocolHandler par3SoundPoolProtocolHandler) {
      this(par1SoundPool, par2URL);
   }
}
