/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 */
package noppes.common.entity.model_entity;

import noppes.common.entity.EntityNPCInterface;
import noppes.util.NoppesUtil;

public class EntityNpcEnderchibi
extends EntityNPCInterface {
    public EntityNpcEnderchibi(abw world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/enderchibi/MrEnderchibi.png";
    }

    public void l_() {
        super.l_();
        if (this.q.I) {
            NoppesUtil.spawnEnderchibi(this);
        }
    }
}

