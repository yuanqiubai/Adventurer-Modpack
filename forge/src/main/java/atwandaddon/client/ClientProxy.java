package atwandaddon.client;

import atwandaddon.CommonProxy;
import atwandaddon.client.render.LargeBulletRender;
import atwandaddon.entity.EntitySniperBullet;
import com.adventurer_engine.client.entity.render.FlyItemRender;
import com.adventurer_engine.common.entity.projectiles.EntityBulletModelBullet;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(
            EntitySniperBullet.class, new FlyItemRender());
    }
}
