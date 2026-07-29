package net.minecraft.client.audio;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolURLConnection;

class SoundPoolProtocolHandler extends URLStreamHandler {

   // $FF: synthetic field
   final SoundPool theSoundPool;

   SoundPoolProtocolHandler(SoundPool par1SoundPool) {
      this.theSoundPool = par1SoundPool;
   }

   protected URLConnection openConnection(URL par1URL) {
      return new SoundPoolURLConnection(this.theSoundPool, par1URL, (SoundPoolProtocolHandler)null);
   }
}
