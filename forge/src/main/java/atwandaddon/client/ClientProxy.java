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
        // ===== 旧代码 (保留以作参考) =====
        // 覆盖引擎默认渲染器，弹幕放大至 1.5x
        // RenderingRegistry.registerEntityRenderingHandler(
        //     EntityBulletModelBullet.class, new LargeBulletRender());
        // ===== 新代码 =====
        // SnowfieldExplorer 弹幕已改为 EntityBulletModelItem (item_26000)，
        // 由引擎 FlyItemRender 渲染为 2D 物品图标，无需 3D 模型渲染器

        // 注册自定义狙击弹丸的 FlyItemRender 渲染器
        // EntitySniperBullet extends EntityBulletModelItem，可复用引擎 2D 图标渲染
        RenderingRegistry.registerEntityRenderingHandler(
            EntitySniperBullet.class, new FlyItemRender());
    }
}
