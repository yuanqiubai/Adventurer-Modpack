/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  akc
 *  amw
 *  asp
 *  by
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  ey
 *  of
 *  uf
 *  ye
 */
package noppes.common.blocks;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import noppes.CustomNpcs;
import noppes.common.blocks.TileWaypoint;
import noppes.common.constants.EnumGuiType;
import noppes.common.objcenter.CustomItems;
import noppes.network.client.WaypointSavePacket;

public class BlockWaypoint
extends amw {
    public BlockWaypoint(int par1) {
        super(par1, akc.e);
        this.a(CustomItems.tab);
    }

    public boolean a(abw par1World, int i, int j, int k, uf player, int par6, float par7, float par8, float par9) {
        if (par1World.I) {
            return false;
        }
        ye currentItem = player.bn.h();
        if (CustomNpcs.activeWand(currentItem)) {
            asp tile = par1World.r(i, j, k);
            by compound = new by();
            tile.b(compound);
            PacketDispatcher.sendPacketToPlayer((ey)new WaypointSavePacket(compound).makePacket("CNPCs Client"), (Player)((Player)player));
            return true;
        }
        return false;
    }

    public void a(abw world, int i, int j, int k, of entityliving, ye item) {
        if (entityliving instanceof uf && world.I) {
            CustomNpcs.proxy.openGui(i, j, k, EnumGuiType.Waypoint, (uf)entityliving);
        }
    }

    public asp b(abw world) {
        return new TileWaypoint();
    }
}

