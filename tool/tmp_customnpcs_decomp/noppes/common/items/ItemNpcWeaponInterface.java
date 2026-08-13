/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  org.lwjgl.opengl.GL11
 *  yc
 *  yd
 *  zl
 */
package noppes.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import noppes.CustomNpcs;
import noppes.common.config.CustomNPCConfig;
import noppes.common.items.ItemRenderInterface;
import noppes.common.objcenter.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemNpcWeaponInterface
extends zl
implements ItemRenderInterface {
    public ItemNpcWeaponInterface(int par1, yd material) {
        super(par1 - 26700 + CustomNPCConfig.ItemStartId, material);
        this.a(CustomItems.tab);
        CustomNpcs.proxy.registerItem(this.cv);
        this.a(CustomItems.tabWeapon);
    }

    @Override
    public void renderSpecial() {
        GL11.glScalef((float)0.66f, (float)0.66f, (float)0.66f);
        GL11.glTranslatef((float)0.0f, (float)0.3f, (float)0.0f);
    }

    public yc b(String name) {
        GameRegistry.registerItem((yc)this, (String)name);
        return super.b(name);
    }
}

