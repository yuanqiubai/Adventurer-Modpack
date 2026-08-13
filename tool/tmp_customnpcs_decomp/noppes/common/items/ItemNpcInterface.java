/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  of
 *  org.lwjgl.opengl.GL11
 *  yc
 *  ye
 */
package noppes.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import noppes.CustomNpcs;
import noppes.common.config.CustomNPCConfig;
import noppes.common.items.EnumNpcToolMaterial;
import noppes.common.items.ItemRenderInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemNpcInterface
extends yc
implements ItemRenderInterface {
    public EnumNpcToolMaterial toolMaterial;

    public ItemNpcInterface(int par1) {
        super(par1 - 26700 + CustomNPCConfig.ItemStartId);
        this.a(CustomItems.tab);
        CustomNpcs.proxy.registerItem(this.cv);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.66f, (float)0.66f, (float)0.66f);
        GL11.glTranslatef((float)0.0f, (float)0.3f, (float)0.0f);
    }

    public int c() {
        return super.c();
    }

    public yc b(String name) {
        GameRegistry.registerItem((yc)this, (String)name);
        return super.b(name);
    }

    public boolean a(ye par1ItemStack, of par2EntityLiving, of par3EntityLiving) {
        if (par2EntityLiving.aN() <= 0.0f) {
            return false;
        }
        par1ItemStack.a(1, par3EntityLiving);
        return true;
    }
}

