/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.common.entity.ai.task;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;

public abstract class TaskBase
extends NodeBase {
    protected int tick = 0;

    public TaskBase(AIObject ai) {
        super(ai);
    }

    @Override
    public by writeNBT() {
        by nbt = new by();
        nbt.a("type", this.internalName());
        return nbt;
    }

    @Override
    public void readNBT(by nbt) {
        assert (nbt.i("type").equals(this.internalName()));
    }

    @Override
    public NodeStatus actionTick(NodeStatus childStatus) {
        NodeStatus tmp = this.onTick();
        ++this.tick;
        return tmp;
    }

    public void init() {
        this.tick = 0;
        this.customInit();
    }

    public abstract void customInit();

    public abstract NodeStatus onTick();
}

