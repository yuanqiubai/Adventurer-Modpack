/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 */
package noppes.common.entity.model_entity;

import noppes.common.constants.EnumAnimation;
import noppes.common.entity.EntityNPCInterface;

public class EntityNPCGolem
extends EntityNPCInterface {
    public EntityNPCGolem(abw world) {
        super(world);
        this.labelOffset = 0.5f;
        this.display.texture = "customnpcs:textures/entity/golem/Iron Golem.png";
    }

    @Override
    public void updateHitbox() {
        if (this.currentAnimation == EnumAnimation.LYING) {
            this.P = 0.5f;
            this.O = 0.5f;
        } else if (this.currentAnimation == EnumAnimation.SITTING) {
            this.O = 1.4f;
            this.P = 2.3f;
        } else {
            this.O = 1.4f;
            this.P = 2.9f;
        }
        this.O = this.O / 5.0f * (float)this.display.modelSize;
        this.P = this.P / 5.0f * (float)this.display.modelSize;
    }
}

