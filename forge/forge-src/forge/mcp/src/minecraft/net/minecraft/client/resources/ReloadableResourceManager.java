package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;

public interface ReloadableResourceManager extends ResourceManager {

   void reloadResources(List var1);

   void registerReloadListener(ResourceManagerReloadListener var1);
}
