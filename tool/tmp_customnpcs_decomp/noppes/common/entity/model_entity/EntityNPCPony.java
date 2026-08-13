/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 */
package noppes.common.entity.model_entity;

import noppes.common.entity.EntityNPCInterface;

public class EntityNPCPony
extends EntityNPCInterface {
    public boolean isPegasus = false;
    public boolean isUnicorn = false;
    public boolean isFlying = false;

    public EntityNPCPony(abw world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/ponies/MineLP Derpy Hooves.png";
    }
}

