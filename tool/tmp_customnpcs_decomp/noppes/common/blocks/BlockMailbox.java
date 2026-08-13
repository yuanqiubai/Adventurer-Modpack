/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  akc
 *  amw
 *  aqz
 *  asp
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  ey
 *  ls
 *  ms
 *  of
 *  uf
 *  ww
 *  ye
 */
package noppes.common.blocks;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import noppes.common.blocks.TileMailbox;
import noppes.common.constants.EnumGuiType;
import noppes.common.objcenter.CustomItems;
import noppes.network.client.GuiPackets;

public class BlockMailbox
extends amw {
    public int renderId = -1;

    public BlockMailbox(int par1) {
        super(par1, akc.e);
        this.a(CustomItems.tab);
    }

    public void a(int par1, ww par2CreativeTabs, List par3List) {
        par3List.add(new ye(par1, 1, 0));
        par3List.add(new ye(par1, 1, 1));
    }

    public boolean a(abw par1World, int i, int j, int k, uf player, int par6, float par7, float par8, float par9) {
        if (!par1World.I) {
            PacketDispatcher.sendPacketToPlayer((ey)new GuiPackets.GuiPacket(EnumGuiType.PlayerMailbox.ordinal()).makePacket("CNPCs Client"), (Player)((Player)player));
        }
        return true;
    }

    public asp b(abw world) {
        return new TileMailbox();
    }

    public ArrayList<ye> getBlockDropped(abw world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ye> ret = new ArrayList<ye>();
        ret.add(new ye((aqz)this, 1, this.h(world, x, y, z)));
        return ret;
    }

    public int a(int par1) {
        return par1 >> 2;
    }

    public void a(abw par1World, int par2, int par3, int par4, of par5EntityLivingBase, ye par6ItemStack) {
        int l = ls.c((double)((double)(par5EntityLivingBase.A * 4.0f / 360.0f) + 0.5)) & 3;
        par1World.b(par2, par3, par4, (l %= 4) | par6ItemStack.k() << 2, 2);
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public int d() {
        return this.renderId;
    }

    @SideOnly(value=Side.CLIENT)
    public ms a(int par1, int par2) {
        return aqz.bh.m(par1);
    }
}

