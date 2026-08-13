/*
 * Decompiled with CFR 0.152.
 */
package noppes.common.items;

import noppes.common.items.EnumNpcToolMaterial;
import noppes.common.items.ItemNpcInterface;
import noppes.common.objcenter.CustomItems;

public class ItemBullet
extends ItemNpcInterface {
    private final EnumNpcToolMaterial material;

    public ItemBullet(int par1, EnumNpcToolMaterial material) {
        super(par1);
        this.material = material;
        this.a(CustomItems.tabWeapon);
    }

    public int getBulletDamage() {
        return this.material.getDamageVsEntity();
    }
}

