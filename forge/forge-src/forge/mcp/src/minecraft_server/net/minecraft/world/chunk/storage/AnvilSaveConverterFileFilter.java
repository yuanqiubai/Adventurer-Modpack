package net.minecraft.world.chunk.storage;

import java.io.File;
import java.io.FilenameFilter;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;

class AnvilSaveConverterFileFilter implements FilenameFilter {

   // $FF: synthetic field
   final AnvilSaveConverter parent;

   AnvilSaveConverterFileFilter(AnvilSaveConverter par1AnvilSaveConverter) {
      this.parent = par1AnvilSaveConverter;
   }

   public boolean accept(File par1File, String par2Str) {
      return par2Str.endsWith(".mcr");
   }
}
