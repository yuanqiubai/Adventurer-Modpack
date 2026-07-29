/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  abw
 *  of
 *  ye
 */
package com.adventurer_engine.common.entity.neo_npc;

import com.adventurer_engine.client.entity.model.RCsModelManager;
import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.branch_nodes.control.SequenceNode;
import com.adventurer_engine.common.entity.ai.task.TaskAnimator;
import com.adventurer_engine.common.entity.neo_npc.AnimationController;
import com.adventurer_engine.common.entity.neo_npc.NPCTempDataTable;
import com.adventurer_engine.util.blockbench.BBModel;

public class EntityRCsNPCBase
extends of {
    public AnimationController animation;
    public NPCTempDataTable tempData = new NPCTempDataTable();
    public BBModel model = RCsModelManager.model;
    public AIObject ai = new AIObject(this);
    public long age = 0L;

    public EntityRCsNPCBase(abw world) {
        super(world);
        SequenceNode seqNode = new SequenceNode(this.ai);
        seqNode.addNode(new TaskAnimator("ride_pig", this.ai));
        seqNode.addNode(new TaskAnimator("run", this.ai));
        seqNode.addNode(new TaskAnimator("run", this.ai));
        seqNode.addNode(new TaskAnimator("run", this.ai));
        seqNode.addNode(new TaskAnimator("run", this.ai));
        seqNode.addNode(new TaskAnimator("sneak", this.ai));
        seqNode.addNode(new TaskAnimator("sneak", this.ai));
        this.ai.addNode(seqNode);
    }

    public ye aZ() {
        return null;
    }

    public ye n(int i) {
        return null;
    }

    public void c(int i, ye itemstack) {
    }

    public ye[] ae() {
        return new ye[0];
    }

    public void l_() {
        super.l_();
        ++this.age;
        if (!this.q.I && this.ai != null) {
            this.ai.onTick();
        }
        if (this.animation != null) {
            this.animation.onUpdate();
        }
        this.A += 5.0f;
    }

    public boolean setAnimation(String name) {
        if (!this.q.I && this.animation == null) {
            this.animation = new AnimationController();
        }
        return this.animation.launchAnimation(name, this);
    }
}

