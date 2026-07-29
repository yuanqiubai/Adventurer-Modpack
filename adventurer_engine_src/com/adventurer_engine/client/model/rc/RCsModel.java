/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.client.model.rc;

import com.adventurer_engine.client.model.rc.RCsAnimationManager;
import com.adventurer_engine.client.model.rc.RCsModelBone;
import com.adventurer_engine.util.blockbench.BBModel;
import com.adventurer_engine.util.blockbench.BBModelOutliner;
import java.util.LinkedList;
import java.util.List;

public class RCsModel {
    public List<RCsModelBone> outliners;
    public String name;
    public int textureX;
    public int textureY;

    public RCsModel(BBModel bb) {
        this.name = bb.name;
        this.textureX = bb.texture.sizeX;
        this.textureY = bb.texture.sizeY;
        this.outliners = new LinkedList<RCsModelBone>();
        for (BBModelOutliner outliner : bb.outliners) {
            this.outliners.add(new RCsModelBone(outliner, this));
        }
    }

    public void render(RCsAnimationManager animation, float partialTicks) {
        for (RCsModelBone bone : this.outliners) {
            bone.render(0.0625f, animation, partialTicks);
        }
    }
}

