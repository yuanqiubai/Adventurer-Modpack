package net.minecraft.world.storage;

import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.storage.ISaveHandler;

public interface ISaveFormat {

   ISaveHandler getSaveLoader(String var1, boolean var2);

   void flushCache();

   boolean deleteWorldDirectory(String var1);

   boolean isOldMapFormat(String var1);

   boolean convertMapFormat(String var1, IProgressUpdate var2);
}
