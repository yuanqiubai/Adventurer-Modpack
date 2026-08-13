/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  of
 *  uf
 *  ye
 */
package noppes.common.entity.model_entity;

import noppes.common.entity.EntityProjectile;

public class EntityMagicProjectile
extends EntityProjectile {
    private final uf player;
    private final ye equiped;

    public EntityMagicProjectile(abw par1World, uf player, ye item, boolean isNPC) {
        super(par1World, (of)player, item, isNPC);
        this.player = player;
        this.equiped = player.bn.h();
    }

    @Override
    public void l_() {
        if (this.player.bn.h() != this.equiped) {
            this.x();
        }
        super.l_();
    }
}

