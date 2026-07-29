package net.minecraft.client.audio;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolURLConnection;

class SoundPoolProtocolHandler extends URLStreamHandler {

   // $FF: synthetic field
   final SoundPool field_110658_a;


   SoundPoolProtocolHandler(SoundPool p_i1327_1_) {
      this.field_110658_a = p_i1327_1_;
   }

   protected URLConnection openConnection(URL p_openConnection_1_) {
      return new SoundPoolURLConnection(this.field_110658_a, p_openConnection_1_, (SoundPoolProtocolHandler)null);
   }
}
