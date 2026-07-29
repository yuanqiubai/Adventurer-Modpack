package net.minecraft.client.gui;

public class GuiPlayerInfo {

   public final String name;
   private final String nameinLowerCase;
   public int responseTime;

   public GuiPlayerInfo(String par1Str) {
      this.name = par1Str;
      this.nameinLowerCase = par1Str.toLowerCase();
   }
}
