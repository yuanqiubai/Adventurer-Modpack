/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 */
package noppes.common.entity.model_entity;

import noppes.common.entity.EntityNPCInterface;

public class EntityNPCDwarfMale
extends EntityNPCInterface {
    public EntityNPCDwarfMale(abw world) {
        super(world);
        this.scaleZ = 0.85f;
        this.scaleX = 0.85f;
        this.scaleY = 0.6875f;
        this.display.texture = "customnpcs:textures/entity/dwarfmale/Simon.png";
    }
}

