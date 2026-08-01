package atwandaddon;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * 存根数据包处理器 — 仅用于满足 @NetworkMod 的 packetHandler 要求。
 *
 * <p>本 mod 不发送自定义网络包，所有客户端/服务端通信均由引擎 (adventurer_engine)
 * 的标准 EntitySpawnPacket 和物品网络同步处理。此处理器仅在 @NetworkMod 注解中
 * 作为必要参数注册，从不实际接收数据包。</p>
 */
public class ATcraftPacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager _manager, Packet250CustomPayload _packet, Player _player)
    {
        // 存根 — 本 mod 不处理自定义网络包
        // 若此处被调用，说明有意外数据包到达，忽略即可
    }
}
