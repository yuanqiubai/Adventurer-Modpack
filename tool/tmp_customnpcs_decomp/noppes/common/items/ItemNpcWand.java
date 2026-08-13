/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  mt
 *  net.minecraft.server.MinecraftServer
 *  nn
 *  nt
 *  uf
 *  yc
 *  ye
 */
package noppes.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.server.MinecraftServer;
import noppes.CustomNpcs;
import noppes.common.config.CustomNPCConfig;
import noppes.common.constants.EnumGuiType;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.entity.model_entity.EntityNPCHumanMale;
import noppes.common.objcenter.CustomItems;
import noppes.common.permissions.CustomNpcsPermissions;
import noppes.util.NoppesUtilServer;

public class ItemNpcWand
extends yc {
    public ItemNpcWand(int i) {
        super(i - 26700 + CustomNPCConfig.ItemStartId);
        this.cw = 1;
        this.a(CustomItems.tab);
    }

    public ye a(ye par1ItemStack, abw par2World, uf par3EntityPlayer) {
        if (!par2World.I) {
            return par1ItemStack;
        }
        CustomNpcs.proxy.openGui((EntityNPCInterface)null, EnumGuiType.NpcRemote);
        return par1ItemStack;
    }

    public boolean a(ye par1ItemStack, uf player, abw par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par3World.I) {
            return true;
        }
        if (CustomNPCConfig.OpsOnly && !MinecraftServer.F().af().i().contains(player.bu.toLowerCase())) {
            MinecraftServer.F().f(player.bu + ": tried to use custom npcs without being an op");
        } else if (CustomNpcsPermissions.Instance.hasPermission(player.bu, "customnpcs.npc.create")) {
            EntityNPCHumanMale npc = (EntityNPCHumanMale)nt.a((String)"npchumanmale", (abw)par3World);
            npc.startPos = new int[]{par4, par5, par6};
            npc.b((float)par4 + 0.5f, npc.getStartYPos(), (float)par6 + 0.5f, player.A, player.B);
            par3World.d((nn)npc);
            npc.g(npc.aT());
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.MainMenuDisplay, npc);
        }
        return true;
    }

    public int a(ye par1ItemStack, int par2) {
        return 9127187;
    }

    public boolean b() {
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void a(mt par1IconRegister) {
        this.cz = yc.R.b_(0);
    }

    public yc b(String name) {
        GameRegistry.registerItem((yc)this, (String)name);
        return super.b(name);
    }
}

