/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  acf
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
import noppes.common.blocks.TileRedstoneBlock;
import noppes.common.constants.EnumGuiType;
import noppes.common.objcenter.CustomItems;
import noppes.network.client.RedstoneBlockSavePacket;

public class BlockNpcRedstone
extends amw {
    public BlockNpcRedstone(int par1) {
        super(par1, akc.q);
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
            PacketDispatcher.sendPacketToPlayer((ey)new RedstoneBlockSavePacket(compound).makePacket("CNPCs Client"), (Player)((Player)player));
            return true;
        }
        return false;
    }

    public void a(abw par1World, int par2, int par3, int par4) {
        par1World.f(par2, par3, par4, this.cF);
        par1World.f(par2, par3 - 1, par4, this.cF);
        par1World.f(par2, par3 + 1, par4, this.cF);
        par1World.f(par2 - 1, par3, par4, this.cF);
        par1World.f(par2 + 1, par3, par4, this.cF);
        par1World.f(par2, par3, par4 - 1, this.cF);
        par1World.f(par2, par3, par4 + 1, this.cF);
    }

    public void a(abw world, int i, int j, int k, of entityliving, ye item) {
        if (entityliving instanceof uf && world.I) {
            CustomNpcs.proxy.openGui(i, j, k, EnumGuiType.RedstoneBlock, (uf)entityliving);
        }
    }

    public void g(abw par1World, int par2, int par3, int par4, int par5) {
        this.a(par1World, par2, par3, par4);
    }

    public int c(acf par1IBlockAccess, int par2, int par3, int par4) {
        return this.isActivated(par1IBlockAccess, par2, par3, par4) > 0 ? 16739176 : super.c(par1IBlockAccess, par2, par3, par4);
    }

    public int c(acf par1World, int par2, int par3, int par4, int par5) {
        return this.isActivated(par1World, par2, par3, par4);
    }

    public int b(acf par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return this.isActivated(par1IBlockAccess, par2, par3, par4);
    }

    public boolean f() {
        return true;
    }

    public asp b(abw var1) {
        return new TileRedstoneBlock();
    }

    public int isActivated(acf par1IBlockAccess, int par2, int par3, int par4) {
        return par1IBlockAccess.h(par2, par3, par4) == 1 ? 15 : 0;
    }
}

