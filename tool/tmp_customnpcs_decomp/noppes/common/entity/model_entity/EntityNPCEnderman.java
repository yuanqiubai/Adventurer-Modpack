/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 */
package noppes.common.entity.model_entity;

import noppes.common.constants.EnumAnimation;
import noppes.common.entity.model_entity.EntityNpcEnderchibi;

public class EntityNPCEnderman
extends EntityNpcEnderchibi {
    public EntityNPCEnderman(abw world) {
        super(world);
        this.labelOffset = 1.0f;
        this.display.texture = "customnpcs:textures/entity/enderman/enderman.png";
        this.display.glowTexture = "customnpcs:textures/overlays/ender_eyes.png";
        this.O = 0.6f;
        this.P = 2.9f;
    }

    @Override
    public void updateHitbox() {
        if (this.currentAnimation == EnumAnimation.LYING) {
            this.P = 0.2f;
            this.O = 0.2f;
        } else if (this.currentAnimation == EnumAnimation.SITTING) {
            this.O = 0.6f;
            this.P = 2.3f;
        } else {
            this.O = 0.6f;
            this.P = 2.9f;
        }
        this.O = this.O / 5.0f * (float)this.display.modelSize;
        this.P = this.P / 5.0f * (float)this.display.modelSize;
    }
}

