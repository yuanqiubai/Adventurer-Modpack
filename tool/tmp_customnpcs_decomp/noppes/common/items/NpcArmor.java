/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  nn
 *  wh
 *  wj
 *  yc
 *  ye
 */
package noppes.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import noppes.common.config.CustomNPCConfig;
import noppes.common.objcenter.CustomItems;

public class NpcArmor
extends wh {
    private final String texture;

    public NpcArmor(int par1, wj par2EnumArmorMaterial, int par4, String texture) {
        super(par1 - 26700 + CustomNPCConfig.ItemStartId, par2EnumArmorMaterial, 0, par4);
        this.texture = texture;
        this.a(CustomItems.tabArmor);
    }

    public String getArmorTexture(ye stack, nn entity, int slot, int layer) {
        return this.b == 2 ? "customnpcs:textures/armor/" + this.texture + "_2.png" : "customnpcs:textures/armor/" + this.texture + "_1.png";
    }

    public yc b(String name) {
        GameRegistry.registerItem((yc)this, (String)name);
        return super.b(name);
    }
}

