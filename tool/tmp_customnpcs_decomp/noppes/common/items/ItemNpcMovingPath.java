/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  mt
 *  nn
 *  uf
 *  yc
 *  ye
 */
package noppes.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import noppes.common.config.CustomNPCConfig;
import noppes.common.constants.EnumGuiType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.objcenter.CustomItems;
import noppes.util.NoppesUtilServer;

public class ItemNpcMovingPath
extends yc {
    public ItemNpcMovingPath(int i) {
        super(i - 26700 + CustomNPCConfig.ItemStartId);
        this.cw = 1;
        this.a(CustomItems.tab);
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        EntityNPCInterface npc = this.getNpc(par1ItemStack, par2World);
        if (npc != null) {
            NoppesUtilServer.sendOpenGui(par3EntityPlayer, EnumGuiType.MovingPath, npc);
        }
        return par1ItemStack;
    }

    public boolean a(ye par1ItemStack, uf player, abw par3World, int x, int y, int z2, int par7, float par8, float par9, float par10) {
        EntityNPCInterface npc = this.getNpc(par1ItemStack, par3World);
        if (npc == null) {
            return true;
        }
        List<int[]> list = npc.ai.getMovingPath();
        list.add(new int[]{x, y, z2});
        player.a("Added point x:" + x + " y:" + y + " z:" + z2 + " to npc " + npc.an());
        return true;
    }

    private EntityNPCInterface getNpc(ye item, abw world) {
        if (!world.I && item.e != null) {
            nn entity = world.a(item.e.e("NPCID"));
            return entity != null && entity instanceof EntityNPCInterface ? (EntityNPCInterface)entity : null;
        }
        return null;
    }

    public int a(ye par1ItemStack, int par2) {
        return 9127187;
    }

    public boolean b() {
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt par1IconRegister) {
        this.cz = yc.s.b_(0);
    }

    public yc b(String name) {
        GameRegistry.registerItem((yc)this, (String)name);
        return super.b(name);
    }
}

