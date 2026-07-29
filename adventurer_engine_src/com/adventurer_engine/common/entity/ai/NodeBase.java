/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.common.entity.ai;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.BranchNode;

public abstract class NodeBase {
    public AIObject ai;
    private BranchNode parent;

    public BranchNode getParent() {
        return this.parent;
    }

    public void setParent(BranchNode node) {
        this.parent = node;
    }

    public abstract NodeStatus actionTick(NodeStatus var1);

    public abstract void reset();

    public abstract void readNBT(by var1);

    public abstract by writeNBT();

    public abstract String internalName();

    public abstract NodeBase copy(AIObject var1);

    public NodeBase(AIObject ai) {
        this.ai = ai;
    }
}

