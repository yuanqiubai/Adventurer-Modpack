/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 */
package noppes.common.entity.model_entity;

import noppes.common.entity.EntityNPCInterface;

public class EntityNpcCrystal
extends EntityNPCInterface {
    public int innerRotation;

    public EntityNpcCrystal(abw world) {
        super(world);
        this.innerRotation = this.ab.nextInt(100000);
        this.scaleX = 0.7f;
        this.scaleY = 0.7f;
        this.scaleZ = 0.7f;
        this.labelOffset = 0.8f;
        this.display.texture = "customnpcs:textures/entity/crystal/EnderCrystal.png";
    }

    public void l_() {
        ++this.innerRotation;
        super.l_();
    }
}

