/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.common.entity.ai.branch_nodes;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeFactory;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.BranchNode;
import com.adventurer_engine.common.entity.ai.task.TaskBase;

public abstract class DecoratorNode
extends BranchNode {
    protected NodeBase child;

    public DecoratorNode(AIObject ai) {
        super(ai);
    }

    @Override
    public void reset() {
        this.child.reset();
    }

    protected void executeChild() {
        if (this.child instanceof TaskBase) {
            this.ai.addTask((TaskBase)this.child);
        } else {
            this.child.actionTick(NodeStatus.INIT);
        }
    }

    protected void reportParent(NodeStatus status) {
        BranchNode parent = this.getParent();
        parent.actionTick(status);
    }

    @Override
    public by writeNBT() {
        by nbt = new by();
        nbt.a("type", this.internalName());
        nbt.a("child", this.child.writeNBT());
        return nbt;
    }

    @Override
    public void readNBT(by nbt) {
        assert (nbt.i("type").equals(this.internalName()));
        this.child = NodeFactory.createNode(nbt.l("child"));
    }
}

