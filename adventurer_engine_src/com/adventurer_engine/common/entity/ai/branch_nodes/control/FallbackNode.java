/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.common.entity.ai.branch_nodes.control;

import com.adventurer_engine.common.entity.ai.AIObject;
import com.adventurer_engine.common.entity.ai.NodeBase;
import com.adventurer_engine.common.entity.ai.NodeStatus;
import com.adventurer_engine.common.entity.ai.branch_nodes.BranchNode;
import com.adventurer_engine.common.entity.ai.branch_nodes.ControlNode;

public class FallbackNode
extends ControlNode {
    private int currentIdx = 0;
    public static final String INTERNAL_NAME = "fallback";

    public FallbackNode(AIObject ai) {
        super(ai);
    }

    @Override
    public NodeStatus onChildReportRUNNING() {
        return NodeStatus.RUNNING;
    }

    @Override
    public NodeStatus onChildReportSUCCESS() {
        BranchNode parent = this.getParent();
        parent.actionTick(NodeStatus.SUCCESS);
        return NodeStatus.SUCCESS;
    }

    @Override
    public NodeStatus onChildReportFAILURE() {
        if (++this.currentIdx >= this.child.size()) {
            this.currentIdx = 0;
            BranchNode parent = this.getParent();
            parent.actionTick(NodeStatus.FAILURE);
            return NodeStatus.FAILURE;
        }
        return this.executeChildNode(this.currentIdx);
    }

    @Override
    public NodeStatus onParentSendInit() {
        if (this.child.isEmpty()) {
            BranchNode parent = this.getParent();
            parent.actionTick(NodeStatus.FAILURE);
            return NodeStatus.FAILURE;
        }
        return this.executeChildNode(this.currentIdx);
    }

    @Override
    public void reset() {
        super.reset();
        this.currentIdx = 0;
    }

    @Override
    public String internalName() {
        return INTERNAL_NAME;
    }

    @Override
    public NodeBase copy(AIObject ai) {
        FallbackNode node = new FallbackNode(ai);
        node.child = this.copyChild(ai);
        return node;
    }
}

