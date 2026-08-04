package atwandaddon.buff.bleed;

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * 流血系统 tick 驱动器。
 *
 * <p>通过 {@link ITickHandler} 接口注册到 FML 服务端 tick，
 * 每 tick 遍历所有活跃实体的流血栈并推进。</p>
 *
 * <p>职责边界：仅负责 tick 事件监听和分发，不包含任何流血逻辑。</p>
 */
public class BleedTickHandler implements ITickHandler
{
    /** 单例 */
    public static final BleedTickHandler INSTANCE = new BleedTickHandler();

    private BleedTickHandler() {}

    /**
     * 注册到 FML 服务端 tick。
     * 应在 {@code ATcraftAddon.preInit()} 中调用。
     */
    public static void register()
    {
        cpw.mods.fml.common.registry.TickRegistry.registerTickHandler(
            INSTANCE, cpw.mods.fml.relauncher.Side.SERVER);
    }

    @Override
    public void tickStart(EnumSet<TickType> _type, Object... _tickData) {}

    /**
     * 每个服务端 tick 结束时，遍历所有加载的实体并推进流血栈。
     */
    @Override
    public void tickEnd(EnumSet<TickType> _type, Object... _tickData)
    {
        // 遍历所有维度的所有加载实体
        net.minecraft.world.WorldServer[] worlds =
            net.minecraft.server.MinecraftServer.getServer().worldServers;

        for (net.minecraft.world.WorldServer world : worlds)
        {
            if (world == null) continue;

            java.util.List entityList = world.getLoadedEntityList();
            for (Object obj : entityList)
            {
                if (obj instanceof EntityLivingBase)
                {
                    BleedManager.tickBleeds((EntityLivingBase)obj);
                }
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    public String getLabel()
    {
        return "ATcraft-BleedTick";
    }
}
