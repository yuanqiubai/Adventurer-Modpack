/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 */
package noppes.common.entity.model_entity;

import noppes.common.entity.EntityNPCInterface;

public class EntityNpcSlime
extends EntityNPCInterface {
    public EntityNpcSlime(abw world) {
        super(world);
        this.scaleX = 2.0f;
        this.scaleY = 2.0f;
        this.scaleZ = 2.0f;
        this.labelOffset = -1.4f;
        this.display.texture = "customnpcs:textures/entity/slime/Slime.png";
    }

    @Override
    public void updateHitbox() {
        this.O = 0.8f;
        this.P = 0.8f;
        this.O = this.O / 5.0f * (float)this.display.modelSize;
        this.P = this.P / 5.0f * (float)this.display.modelSize;
    }
}

