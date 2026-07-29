package atwandaddon.client;

import atwandaddon.ATcraftAddon;
import atwandaddon.CommonProxy;
import atwandaddon.entity.EntityFlameBall;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(
            EntityFlameBall.class,
            new RenderSnowball(ATcraftAddon.ammoOrb)
        );
    }
}
