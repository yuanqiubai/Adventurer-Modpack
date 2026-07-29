package atwandaddon.client;

import atwandaddon.ATcraftAddon;
import atwandaddon.CommonProxy;
import atwandaddon.entity.EntityFlameBall;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;

/**
 * 客户端代理
 * 继承 CommonProxy，重写 registerRenderers() 以注册实体渲染器。
 * 由 @SidedProxy 在客户端自动注入，服务端使用 CommonProxy 的空实现。
 *
 * 当前注册：
 * - EntityFlameBall → RenderSnowball(ammoOrb)：火焰弹渲染为弹药宝珠的纹理小球
 *
 * 职责边界：
 * - 仅负责客户端渲染注册，不包含业务逻辑
 * - 新增弹射物时在此处添加对应的 RenderingRegistry 注册
 */
public class ClientProxy extends CommonProxy {

    /**
     * 注册实体渲染器
     * 将火焰弹实体绑定到 RenderSnowball 渲染器，使用弹药宝珠(ammoOrb)的纹理。
     * 调用时机：ATcraftAddon.preInit() 中通过 proxy.registerRenderers() 调用。
     */
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(
            EntityFlameBall.class,
            new RenderSnowball(ATcraftAddon.ammoOrb)
        );
    }
}
