package net.minecraft.client.resources.data;

import net.minecraft.client.resources.data.MetadataSection;

public class PackMetadataSection implements MetadataSection {

   private final String packDescription;
   private final int packFormat;

   public PackMetadataSection(String par1Str, int par2) {
      this.packDescription = par1Str;
      this.packFormat = par2;
   }

   public String getPackDescription() {
      return this.packDescription;
   }

   public int getPackFormat() {
      return this.packFormat;
   }
}
