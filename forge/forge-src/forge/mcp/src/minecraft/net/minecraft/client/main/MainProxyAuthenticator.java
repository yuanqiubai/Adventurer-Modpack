package net.minecraft.client.main;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public final class MainProxyAuthenticator extends Authenticator {

   // $FF: synthetic field
   final String field_111237_a;
   // $FF: synthetic field
   final String field_111236_b;

   public MainProxyAuthenticator(String par1Str, String par2Str) {
      this.field_111237_a = par1Str;
      this.field_111236_b = par2Str;
   }

   protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(this.field_111237_a, this.field_111236_b.toCharArray());
   }
}
