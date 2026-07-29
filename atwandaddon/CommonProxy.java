package atwandaddon;

/**
 * 通用代理(服务端 + 客户端基类)
 * 提供 registerRenderers() 空实现。
 * 服务端不需要渲染注册，因此此处为空方法体。
 * 客户端通过 ClientProxy 重写此方法，注册实体渲染器。
 *
 * 职责边界：
 * - 仅作为代理基类，定义客户端和服务端共享的接口
 * - 实际渲染注册由 ClientProxy 子类完成(通过 @SidedProxy 自动注入)
 */
public class CommonProxy {

    /**
     * 注册渲染器
     * 服务端空实现 —— 服务端不处理渲染。
     * 客户端由 ClientProxy 重写，注册 EntityFlameBall 的 RenderSnowball。
     */
    public void registerRenderers() {
        // 服务端无需渲染，留空
    }
}
